package com.example.dell.booking_cyber.Model;

import java.security.NoSuchProviderException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class GMailSender {
    private String ToMail;
    private String Subject;
    private String Content;

    public GMailSender(String toMail, String subject, String content) {
        ToMail = toMail;
        Subject = subject;
        Content = content;
    }

    public boolean sendMail() throws NoSuchProviderException{
        boolean IsSuccess = true;
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.debug", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("cyberbooking3@gmail.com", "noragamiyato");
                    }
                });
        try {
            Transport transport = session.getTransport();
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("hoangdvse62687@fpt.edu.vn"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(ToMail));
            message.setSubject(Subject);
            message.setContent(Content, "text/html; charset=utf-8");
            //message.setText(Content);

            transport.connect();
            Transport.send(message);
            transport.close();

            System.out.println("Done");

        } catch (MessagingException e) {
            IsSuccess = false;
        }
        return IsSuccess;
    }
}
