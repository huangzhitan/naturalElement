/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.core.plugs;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.log4j.Logger;

/**
 *
 * @author sam
 */
public class JavaMail {

    String host = ""; //smtp 邮件服务器
    String user = ""; //用户名
    String password = "";//密码
    Logger log = Logger.getLogger(JavaMail.class);
    public void setHost(String host) {
        this.host = host;
    }

    public void setAccount(String user, String password) {
        this.user = user;
        this.password = password;
    }
    /**
     * 
     * @param from 发送者
     * @param to 发送给谁
     * @param subject 标题
     * @param content 内容
     * @throws MessagingException 
     */
    public boolean send(String from, String to, String subject, String content) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        Transport  transport = null;
        try {
            Session mailSession = Session.getDefaultInstance(props);
            mailSession.setDebug(true);
            Message message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(content);
            message.saveChanges();
            transport = mailSession.getTransport("smtp");
            log.debug(host);
            log.debug(user);
            log.debug(password);
            transport.connect(host, user, password);
            transport.sendMessage(message, message.getAllRecipients());
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }finally{
              transport.close();
        }
        return false;

    }

    public static void main(String args[]) throws MessagingException, InterruptedException {
        JavaMail sm = new JavaMail();
        sm.setHost("smtp.sina.com");
        sm.setAccount("邮箱帐号", "密码");
        sm.send("zcl1866@sina.com", "jifeng1866@163.com", "Hi:JiFeng", "http://127.0.0.1/");

    }
}
