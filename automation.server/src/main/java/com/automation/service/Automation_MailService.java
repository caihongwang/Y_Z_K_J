package com.automation.service;

import com.automation.dto.MessageDTO;

import java.util.Map;

/**
 * 邮件服务
 */
public interface Automation_MailService {

    /**
     * 发送文本邮件
     * @param paramMap
     */
    public MessageDTO sendSimpleMail(Map<String, Object> paramMap);

    /**
     * 发送HTML邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    public void sendHtmlMail(String to, String subject, String content);



    /**
     * 发送带附件的邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @param filePath 附件
     */
    public void sendAttachmentsMail(String to, String subject, String content, String filePath);
}