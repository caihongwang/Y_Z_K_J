package com.oilStationMap.config;

import com.oilStationMap.service.WX_UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 安全配置相关
 *
 * @EnableGlobalMethodSecurity(prePostEnabled = true)
 * 注意：
 * @EnableGlobalMethodSecurity 可以配置多个参数:
 * prePostEnabled :决定Spring Security的前注解是否可用 [@PreAuthorize,@PostAuthorize,..] 此处表明可用
 * secureEnabled : 决定是否Spring Security的保障注解 [@Secured] 是否可用
 * jsr250Enabled ：决定 JSR-250 annotations 注解[@RolesAllowed..] 是否可用.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Autowired
    WX_UserService wxUserService;

    @Autowired
    UserDetailsService userDetailsService;

    /***设置不拦截规则,不许需要进行权限验证*/
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                //静态资源
                "/resourceOfOilStationMap/**",
                //网页内的微信支付
                "/wxCommon/getSignatureAndJsapiTicketAndNonceStrForWxPublicNumber",
                "/wxPay/unifiedOrderPay",
                "/wxPay/toOauthUrlForPaymentPage",
                "/wxPay/getPaymentPage",
                //微信客服
                "/wxCommon/receviceAndSendCustomMessage",
                //管理中心
                "/*/*ForAdmin",
                //更新油价资讯
                "/wxOilStation/getOilPriceFromOilUsdCnyCom",
                //更新油站信息
                "/wxOilStation/updateOilStationHireInfo",
                //车主福利for车用尿素
                "/wxOilStation/dailyCarUreaMessageSend",
                //根据经纬度设置用户位置中心
                "/wxOilStation/setLocaltionByUid",
                //爬虫类请求
                "/wxSpider/**",
                //消息类请求
                "/wxMessage/**",
                //字典类请求
                "/wxDic/**",
                //用户的微信AccessToken请求
                "/wxUser/getWxAccessToken",
                //获取直播房间列表
                "/wxLiveBroadcast/getLiveInfoList",
                //网页静态资源
                "/js/**",
                "/css/**",
                "/images/**",
                "/druid/**"
        );
    }

    /**
     * 密码模式下必须注入的bean authenticationManagerBean
     * 认证是由 AuthenticationManager 来管理的，
     * 但是真正进行认证的是 AuthenticationManager 中定义的AuthenticationProvider。
     * AuthenticationManager 中可以定义有多个 AuthenticationProvider
     */
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 用户认证
     * 验证用户信息与密码
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * 密码加密器
     * @return
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
