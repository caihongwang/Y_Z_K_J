package com.newMall.utils.wxpay;

import com.newMall.code.NewMallCode;
import com.newMall.utils.wxpay.IWXPayDomain;
import com.newMall.utils.wxpay.WXPayConfig;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class WXPayConfigImpl extends WXPayConfig {

    private byte[] certData;
    private static com.newMall.utils.wxpay.WXPayConfigImpl INSTANCE;

    private WXPayConfigImpl() throws Exception {
//        String certPath = "D://CERT/common/apiclient_cert.p12";
//        File file = new File(certPath);
//        InputStream certStream = new FileInputStream(file);
//        this.certData = new byte[(int) file.length()];
//        certStream.read(this.certData);
//        certStream.close();
    }

    public static com.newMall.utils.wxpay.WXPayConfigImpl getInstance() throws Exception {
        if (INSTANCE == null) {
            synchronized (com.newMall.utils.wxpay.WXPayConfigImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new com.newMall.utils.wxpay.WXPayConfigImpl();
                }
            }
        }
        return INSTANCE;
    }

    public String getAppID() {
        return NewMallCode.WX_MINI_PROGRAM_APPID;
    }

    public String getMchID() {
        return NewMallCode.WX_PAY_MCH_ID;
    }

    public String getKey() {
        return NewMallCode.WX_PAY_API_SECRET;
    }

    public InputStream getCertStream() {
        ByteArrayInputStream certBis;
        certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }


    public int getHttpConnectTimeoutMs() {
        return 2000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }

    IWXPayDomain getWXPayDomain() {
        return com.newMall.utils.wxpay.WXPayDomainSimpleImpl.instance();
    }

    public String getPrimaryDomain() {
        return "api.mch.weixin.qq.com";
    }

    public String getAlternateDomain() {
        return "api2.mch.weixin.qq.com";
    }

    @Override
    public int getReportWorkerNum() {
        return 1;
    }

    @Override
    public int getReportBatchSize() {
        return 2;
    }
}
