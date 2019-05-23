package com.newMall.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.newMall.domain.MyRedisTokenStore;
import com.newMall.dto.ResultDTO;
import com.newMall.secuity.WX_User;
import com.newMall.service.WX_DicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author caihongwang
 */
@Configuration
@EnableAuthorizationServer
public class Oauth2ServiceConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    private TokenStore tokenStore; //保存令牌数据栈

    @Autowired
    private WX_DicService wxDicService; //保存令牌数据栈

    @Autowired
    private MyRedisTokenStore myRedisTokenStore; //保存令牌数据栈

    //认证管理器
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * token存放位置
     * @return
     */
    @Bean
    RedisTokenStore redisTokenStore(){
        return new RedisTokenStore(redisConnectionFactory);
    }

    @Bean
    public TokenStore tokenStore(RedisConnectionFactory redisConnectionFactory){
        return new RedisTokenStore(redisConnectionFactory); //使用redis存储令牌
    }

    /**
     * 这个方法主要的作用用于控制token的端点等信息
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
        endpoints.tokenStore(myRedisTokenStore);
        endpoints.accessTokenConverter(accessTokenConverter());
    }

    /**
     * 这个方法主要是用于校验注册的第三方客户端的信息，可以存储在数据库中，
     * 默认方式是存储在内存中，如下所示，注释掉的代码即为内存中存储的方式
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        List<Map<String, String>> customMessageAccountList = Lists.newArrayList();
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("dicType", "customMessageAccount");
        ResultDTO resultDTO = wxDicService.getSimpleDicByCondition(paramMap);
        customMessageAccountList = resultDTO.getResultList();
        Map<String, String> secretUserMap1 = Maps.newHashMap();
        secretUserMap1.put("customMessageAccountId", "app");
        secretUserMap1.put("customMessageAccountSecret", "123456");
        secretUserMap1.put("customMessageAccountName", "miniProgram");
        customMessageAccountList.add(secretUserMap1);
        Map<String, String> secretUserMap2 = Maps.newHashMap();
        secretUserMap2.put("customMessageAccountId", "caihongwang");
        secretUserMap2.put("customMessageAccountSecret", "caihongwang");
        secretUserMap2.put("customMessageAccountName", "miniProgram");
        customMessageAccountList.add(secretUserMap2);
        if (customMessageAccountList != null && customMessageAccountList.size() > 0) {
            for (int i = 0; i < customMessageAccountList.size(); i++) {
                if(i==(customMessageAccountList.size()-1)){
                    Map<String, String> secretUserMap = customMessageAccountList.get(i);
                    builder
                            //设置客户端和密码
                            .withClient(secretUserMap.get("customMessageAccountId")).secret(new BCryptPasswordEncoder().encode(secretUserMap.get("customMessageAccountSecret")))
                            //授权域
                            .scopes(secretUserMap.get("customMessageAccountName"))
                            //支持的认证方式
                            .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                            //设置token有效期
                            .accessTokenValiditySeconds(7 * 24 * 3600)
                            //设置refreshToken有效期
                            .refreshTokenValiditySeconds(7 * 24 * 3600)
                    ;
                } else {
                    Map<String, String> secretUserMap = customMessageAccountList.get(i);
                    builder
                            //设置客户端和密码
                            .withClient(secretUserMap.get("customMessageAccountId")).secret(new BCryptPasswordEncoder().encode(secretUserMap.get("customMessageAccountSecret")))
                            //授权域
                            .scopes(secretUserMap.get("customMessageAccountName"))
                            //支持的认证方式
                            .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                            //设置token有效期
                            .accessTokenValiditySeconds(7 * 24 * 3600)
                            //设置refreshToken有效期
                            .refreshTokenValiditySeconds(7 * 24 * 3600)
                            .and()
                    ;
                }
            }
        } else {
            builder
                    //设置客户端和密码
                    .withClient("app").secret(new BCryptPasswordEncoder().encode("123456"))
                    //授权域
                    .scopes("app")
                    //支持的认证方式
                    .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                    //设置token有效期
                    .accessTokenValiditySeconds(7 * 24 * 3600)
                    //设置refreshToken有效期
                    .refreshTokenValiditySeconds(7 * 24 * 3600);
        }
    }

    /**
     * 允许表单验证，浏览器直接发送post请求即可获取tocken
     * @param oauthServer
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
            .tokenKeyAccess("permitAll()")          // 开启/oauth/token_key验证端口无权限访问
            .checkTokenAccess("isAuthenticated()")  // 开启/oauth/check_token验证端口认证权限访问
            .allowFormAuthenticationForClients()
            .authenticationEntryPoint(new AuthExceptionEntryPoint());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter() {
            /**
             * 生成token
             * @param accessToken
             * @param authentication
             * @return
             */
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                DefaultOAuth2AccessToken defaultOAuth2AccessToken = new DefaultOAuth2AccessToken(accessToken);
                // 设置额外用户信息
                WX_User wxCurrentUser = (WX_User) authentication.getPrincipal();
                // 将用户信息添加到token额外信息中
                defaultOAuth2AccessToken.getAdditionalInformation().put("userInfo", wxCurrentUser);
                return super.enhance(defaultOAuth2AccessToken, authentication);
            }
        };
        // 测试用,资源服务使用相同的字符达到一个对称加密的效果,生产时候使用RSA非对称加密方式
        accessTokenConverter.setSigningKey("SigningKey");
        return accessTokenConverter;
    }

    public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {
        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response,
                             AuthenticationException authException)
                throws ServletException {

            Map map = new HashMap();
            map.put("code", "10002");       //auth的token过期
            map.put("message", authException.getMessage());
            map.put("path", request.getServletPath());
            map.put("timestamp", String.valueOf(System.currentTimeMillis()));
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.writeValue(response.getOutputStream(), map);
            } catch (Exception e) {
                throw new ServletException();
            }
        }
    }

}