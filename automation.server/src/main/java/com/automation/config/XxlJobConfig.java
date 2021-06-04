package com.automation.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * xxl-job config
 * @author xuxueli 2017-04-28
 */
@Configuration
public class XxlJobConfig {

    private Logger logger = LoggerFactory.getLogger(XxlJobConfig.class);

    @Value("${xxl.job.admin.address}")
    private String adminAddresses;

    @Value("${xxl.job.executor.appname}")
    private String appName;

    @Value("${xxl.job.executor.ip}")
    private String ip;

    @Value("${xxl.job.executor.port}")
    private int port;

    @Value("${xxl.job.accessToken}")
    private String accessToken;

    @Value("${xxl.job.executor.logpath}")
    private String logPath;

    @Value("${xxl.job.executor.logretentiondays}")
    private int logRetentionDays;

    //使用环境
    @Value("${spring.profiles.active}")
    private String useEnvironmental;

    @Bean("XxlJobConfig")
    public XxlJobSpringExecutor xxlJobExecutor() {
        if ("publish".equals(useEnvironmental)) {           // xxl 定时任务在启动时很耗费内存，同时也可能存在数据库连接不上等问题导致远程服务断裂，故只允许线上服务器内部开启xxl定时任务
            XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
            xxlJobSpringExecutor.setAdminAddresses(adminAddresses);
            xxlJobSpringExecutor.setAppName(appName);
            xxlJobSpringExecutor.setIp(ip);
            xxlJobSpringExecutor.setPort(port);
            xxlJobSpringExecutor.setAccessToken(accessToken);
            xxlJobSpringExecutor.setLogPath(logPath);
            xxlJobSpringExecutor.setLogRetentionDays(logRetentionDays);
            return xxlJobSpringExecutor;
        } else {
            return null;
        }
    }

    /**
     * 针对多网卡、容器内部署等情况，可借助 "spring-cloud-commons" 提供的 "InetUtils" 组件灵活定制注册IP；
     *
     *      1、引入依赖：
     *          <dependency>
     *             <groupId>org.springframework.cloud</groupId>
     *             <artifactId>spring-cloud-commons</artifactId>
     *             <version>${version}</version>
     *         </dependency>
     *
     *      2、配置文件，或者容器启动变量
     *          spring.cloud.inetutils.preferred-networks: 'xxx.xxx.xxx.'
     *
     *      3、获取IP
     *          String ip_ = inetUtils.findFirstNonLoopbackHostInfo().getIpAddress();
     */


}