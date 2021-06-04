package com.automation.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.Charsets;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class HttpsUtil {

    private PoolingHttpClientConnectionManager poolConnManager;

    private SSLConnectionSocketFactory sslsf;

    private static final Logger logger = LoggerFactory.getLogger(HttpsUtil.class);

    @PostConstruct
    public void init() throws KeyManagementException, NoSuchAlgorithmException {
        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                // TODO Auto-generated method stub
                return true;
            }
        };
        //先用不做客户端验证
        this.sslsf = new SSLConnectionSocketFactory(SSLContextUtil.createIgnoreVerifySSL("TLSv1.2"), hostnameVerifier);

        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", this.sslsf)
                .build();

        //初始化连接管理器
        poolConnManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        // Increase max total connection to 200
        poolConnManager.setMaxTotal(500);
        // Increase default max connection per route to 20
        poolConnManager.setDefaultMaxPerRoute(50);
    }

    //获取连接
    public CloseableHttpClient getConnection() {
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(poolConnManager).build();
        return httpClient;
    }

    public String post(String url, List<? extends NameValuePair> param) {
        String returnStr = null;
        CloseableHttpClient client = null;
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            long currentTime = System.currentTimeMillis();
            httpPost.setEntity(new UrlEncodedFormEntity(param, "utf-8"));

            //配置请求的超时设置
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(15000)
                    .setConnectTimeout(15000).setSocketTimeout(15000).build();
            httpPost.setConfig(requestConfig);

            logger.info(currentTime + " 开始发送 请求：url="+url+" , param="+JSONObject.toJSONString(param));
            response = getConnection().execute(httpPost);
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                String resopnse = "";
                if (entity != null) {
                    resopnse = EntityUtils.toString(entity, "utf-8");
                }
                logger.info(currentTime + " 接收响应：url=" + url + " , status=" + status + " , content=" + resopnse);
                return entity != null ? resopnse : null;
            } else {
                HttpEntity entity = response.getEntity();
                logger.info(currentTime + " 接收响应：url=" + url + " , status=" + status + " , resopnse=" + EntityUtils.toString(entity, "utf-8"));
                throw new ClientProtocolException("Unexpected response status=" + status);
            }
        } catch (Exception e) {
            httpPost.abort();
            logger.error("发送请求异常：url="+url+" , param="+JSONObject.toJSONString(param)+" , Exception="+e.getMessage());
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return returnStr;
    }

    public String post(String url, Map<String, String> param) {
        List<BasicNameValuePair> params = new ArrayList<>();
        if (param != null) {
            for (String key : param.keySet()) {
                params.add(new BasicNameValuePair(key, param.get(key)));
            }
        }
        return this.post(url, params);
    }

    public String get(String url, Map<String, String> params) {
        String returnStr = null;
        HttpGet httpGet = new HttpGet();

        try {
            long currentTime = System.currentTimeMillis();

            String requestUrl = "";
            if (params != null && !params.isEmpty()) {
                Map.Entry entry;
                for (Iterator i$ = params.entrySet().iterator(); i$.hasNext(); requestUrl = requestUrl + (String) entry.getKey() + "=" + URLEncoder.encode((String) entry.getValue(), "UTF-8") + "&") {
                    entry = (Map.Entry) i$.next();
                }

                requestUrl = url + "?" + requestUrl.substring(0, requestUrl.length() - 1);
            } else {
                requestUrl = url;
            }
            httpGet.setURI(new URI(requestUrl));
            CloseableHttpResponse response = getConnection().execute(httpGet);
            int status = response.getStatusLine().getStatusCode();

            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                String resopnse = "";
                if (entity != null) {
                    resopnse = EntityUtils.toString(entity, "utf-8");
                }
                logger.info(currentTime + " 接收响应：url" + url + " status=" + status);
                return entity != null ? resopnse : null;
            } else {
                HttpEntity entity = response.getEntity();
                logger.info(currentTime + " 接收响应：url" + url + " status=" + status + " resopnse=" + EntityUtils.toString(entity, "utf-8"));
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        } catch (Exception e) {
            httpGet.abort();
            logger.error("发送请求异常：url {}, \n请求参数: {}", url, JSONObject.toJSONString(params) + " Exception" + e.toString());
        }
        return returnStr;
    }

    public byte[] postForByte(String url, List<? extends NameValuePair> param) {
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            long currentTime = System.currentTimeMillis();
            httpPost.setEntity(new UrlEncodedFormEntity(param, "utf-8"));

            //配置请求的超时设置
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(15000)
                    .setConnectTimeout(15000).setSocketTimeout(15000).build();
            httpPost.setConfig(requestConfig);

            logger.info(currentTime + " 开始发送 请求：url" + url);
            response = getConnection().execute(httpPost);
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                byte[] res = EntityUtils.toByteArray(entity);
                String resopnse = "";
                if (entity != null) {
                    resopnse = EntityUtils.toString(entity, "utf-8");
                }
                logger.info(currentTime + " 接收响应：url" + url + " status=" + status);
                return res;
            } else {
                HttpEntity entity = response.getEntity();
                logger.info(currentTime + " 接收响应：url" + url + " status=" + status + " resopnse=" + EntityUtils.toString(entity, "utf-8"));
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        } catch (Exception e) {
            httpPost.abort();
            logger.error("发送请求异常：url {}, \n请求参数: {}", url, JSONObject.toJSONString(param) + " Exception" + e.toString());
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public String postJson(String url, String contentObj) {
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            long currentTime = System.currentTimeMillis();
            StringEntity s = new StringEntity(contentObj, ContentType.create("application/json", "utf-8"));

            httpPost.setEntity(s);

            //配置请求的超时设置
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(15000)
                    .setConnectTimeout(15000).setSocketTimeout(15000).build();
            httpPost.setConfig(requestConfig);

            logger.info(currentTime + " 开始发送 请求：url" + url);
            response = getConnection().execute(httpPost);
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                String resopnse = "";
                if (entity != null) {
                    resopnse = EntityUtils.toString(entity, "utf-8");
                }
                logger.info(currentTime + " 正常接收响应：url" + url + " status=" + status);
                return entity != null ? resopnse : null;
            } else {
                HttpEntity entity = response.getEntity();
                logger.info(currentTime + " 异常接收响应：url" + url + " status=" + status + " resopnse=" + EntityUtils.toString(entity, "utf-8"));
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        } catch (Exception e) {
            httpPost.abort();
            logger.error("发送请求异常：url {}, \n请求参数: {}", url, JSONObject.toJSONString(contentObj) + " Exception" + e.toString());
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public String postJson(String url, String contentObj, Map<String, String> paramHeader) {
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            long currentTime = System.currentTimeMillis();
            StringEntity s = new StringEntity(contentObj, ContentType.create("application/json", "utf-8"));

            httpPost.setEntity(s);

            for (String key : paramHeader.keySet()) {
                httpPost.setHeader(key, paramHeader.get(key));
            }

            // 配置请求的超时设置
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(15000)
                    .setConnectTimeout(15000).setSocketTimeout(15000).build();
            httpPost.setConfig(requestConfig);

            logger.info(currentTime + " 开始发送 请求：url" + url);
            response = getConnection().execute(httpPost);
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                String resopnse = "";
                if (entity != null) {
                    resopnse = EntityUtils.toString(entity, "utf-8");
                }
                logger.info(currentTime + " 正常接收响应：url" + url + " status=" + status);
                return entity != null ? resopnse : null;
            } else {
                HttpEntity entity = response.getEntity();
                logger.info(currentTime + " 异常接收响应：url" + url + " status=" + status + " resopnse=" + EntityUtils.toString(entity, "utf-8"));
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        } catch (Exception e) {
            httpPost.abort();
            logger.error("发送请求异常：url {}, \n请求参数: {}", url, JSONObject.toJSONString(contentObj) + " Exception" + e.toString());
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    public String postJson(String url, String contentObj, RequestConfig config) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(config);
        CloseableHttpResponse response = null;
        try {
            long currentTime = System.currentTimeMillis();
            StringEntity s = new StringEntity(contentObj, ContentType.create("application/json", "utf-8"));

            // 配置请求的超时设置
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(15000)
                    .setConnectTimeout(15000).setSocketTimeout(15000).build();
            httpPost.setConfig(requestConfig);

            httpPost.setEntity(s);
            logger.info(currentTime + " 开始发送 请求：url" + url);
            response = getConnection().execute(httpPost);
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                String resopnse = "";
                if (entity != null) {
                    resopnse = EntityUtils.toString(entity, "utf-8");
                }
                logger.info(currentTime + " 正常接收响应：url" + url + " status=" + status);
                return entity != null ? resopnse : null;
            } else {
                HttpEntity entity = response.getEntity();
                logger.info(currentTime + " 异常接收响应：url" + url + " status=" + status + " resopnse=" + EntityUtils.toString(entity, "utf-8"));
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        } catch (Exception e) {
            httpPost.abort();
            logger.error("发送请求异常：url {}, \n请求参数: {}", url, JSONObject.toJSONString(contentObj) + " Exception" + e.toString());
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public String post(String url, List<NameValuePair> param, RequestConfig config) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(config);
        CloseableHttpResponse response = null;
        try {
            long currentTime = System.currentTimeMillis();
            httpPost.setEntity(new UrlEncodedFormEntity(param, "utf-8"));

            // 配置请求的超时设置
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(15000)
                    .setConnectTimeout(15000).setSocketTimeout(15000).build();
            httpPost.setConfig(requestConfig);

            logger.info(currentTime + " 开始发送 请求：url" + url);

//        httpPost.setHeader("prepub", "1");//todo 正式环境一定要去掉
            response = getConnection().execute(httpPost);
            int status = response.getStatusLine().getStatusCode();
            logger.info("接收响应：url" + url + " status=" + status);
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                String resopnse = "";
                if (entity != null) {
                    resopnse = EntityUtils.toString(entity, "utf-8");
                }
                logger.info(currentTime + " 接收响应：url" + url + " status=" + status);
                return entity != null ? resopnse : null;
            } else {
                HttpEntity entity = response.getEntity();
                logger.info(currentTime + " 接收响应：url" + url + " status=" + status + " resopnse=" + EntityUtils.toString(entity, "utf-8"));
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        } catch (Exception e) {
            e.printStackTrace();
            httpPost.abort();
            logger.error("发送请求异常：url {}, \n请求参数: {}", url, JSONObject.toJSONString(param) + " Exception" + e.toString());
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36";

}
