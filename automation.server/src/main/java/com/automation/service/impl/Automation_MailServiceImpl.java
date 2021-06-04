package com.automation.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.automation.code.Automation_Code;
import com.automation.dto.MessageDTO;
import com.automation.service.Automation_MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

/**
 * 邮件服务
 */
@Service
public class Automation_MailServiceImpl implements Automation_MailService {

    private static final Logger logger = LoggerFactory.getLogger(Automation_MailServiceImpl.class);

    /**
     * Spring Boot 提供了一个发送邮件的简单抽象，使用的是下面这个接口，这里直接注入即可使用
     */
    @Autowired
    private JavaMailSender mailSender;

    /**
     * 配置文件中我的qq邮箱
     */
    @Value("${spring.mail.from}")
    private String from;

    /**
     * 简单文本邮件
     * @param paramMap
     */
    @Override
    public MessageDTO sendSimpleMail(Map<String, Object> paramMap) {
        logger.info("在【service】中发送文本邮件-sendSimpleMail,请求-paramMap = {}", JSONObject.toJSONString(paramMap));
        MessageDTO messageDTO = new MessageDTO();
        if (paramMap.size() > 0) {
            try {
                String to = paramMap.get("to").toString();
                String subject = paramMap.get("subject").toString();
                String content = paramMap.get("content").toString();

                //创建SimpleMailMessage对象
                SimpleMailMessage message = new SimpleMailMessage();
                //邮件发送人
                message.setFrom(from);
                //邮件接收人
                message.setTo(to);
                //邮件主题
                message.setSubject(subject);
                //邮件内容
                message.setText(content);
                //发送邮件
                mailSender.send(message);
                
                messageDTO.setCode(Automation_Code.SUCCESS.getNo());
                messageDTO.setMessage(Automation_Code.SUCCESS.getMessage());
            } catch (Exception e) {
                logger.error("在【service】中发送文本邮件-sendSimpleMail is error, paramMap : {}", JSONObject.toJSONString(paramMap), " , e : {}", e);
                messageDTO.setCode(Automation_Code.SERVER_INNER_ERROR.getNo());
                messageDTO.setMessage(Automation_Code.SERVER_INNER_ERROR.getMessage());
            }
        } else {
            messageDTO.setCode(Automation_Code.PARAM_IS_NULL.getNo());
            messageDTO.setMessage(Automation_Code.PARAM_IS_NULL.getMessage());
        }
        logger.info("在【service】中发送文本邮件-sendSimpleMail,响应-resultDTO = {}", JSONObject.toJSONString(messageDTO));
        return messageDTO;
    }

    /**
     * html邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    @Override
    public void sendHtmlMail(String to, String subject, String content) {
        //获取MimeMessage对象
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper;
        try {
            messageHelper = new MimeMessageHelper(message, true);
            //邮件发送人
            messageHelper.setFrom(from);
            //邮件接收人
            messageHelper.setTo(to);
            //邮件主题
            message.setSubject(subject);
            //邮件内容，html格式
            messageHelper.setText(content, true);
            //发送
            mailSender.send(message);
            //日志信息
            logger.info("邮件已经发送。");
        } catch (MessagingException e) {
            logger.error("发送邮件时发生异常！", e);
        }
    }

    /**
     * 带附件的邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @param filePath 附件
     */
    @Override
    public void sendAttachmentsMail(String to, String subject, String content, String filePath) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);
            mailSender.send(message);
            //日志信息
            logger.info("邮件已经发送。");
        } catch (MessagingException e) {
            logger.error("发送邮件时发生异常！", e);
        }
    }
}