package com.pickme.anomality.services;


import com.pickme.anomality.BankresponseApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import java.util.Properties;

@Component
@PropertySources({
        @PropertySource(value = {"classpath:application.properties"}),
        @PropertySource(value = "file:/opt/bank-settlements/config/application.properties", ignoreResourceNotFound = true)
})
public class SendEmail {


@Value("${mail.username}")
private String username;

@Value("${mail.password}")
private String password;

    @Value("${mail.smtp.auth}")
private String auth;

    @Value("${mail.smtp.starttls.enable}")
private String starttls;

    @Value("${mail.smtp.host}")
private String host;

    @Value("${mail.smtp.port}")
private String port;

    @Value("${mail.toaddress}")
private String toAddress;

    @Value("${mail.subject}")
private String subject;


    @Value("${mail.message.allfailed}")
private String body_message_allfailed;

    @Value("${mail.message.receivedfailed}")
    private String body_message_allreceivedfailed;

    @Value("${mail.message.updatedfailed}")
    private String body_message_allupdatedfailed;

    @Value(("${time.zone}"))
    private String timeZone;

    @Value("${date.format}")
    private String dateFormat;


    private  String body_message;

    private long currentTime;

    private long specifiedTime;




    public void sendingEmail(int mode, long currentTime , long specifiedTime){


        this.currentTime = currentTime;
        this.specifiedTime = specifiedTime;

        Properties props = new Properties();
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.starttls.enable", starttls);
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        try {
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });



            Message message = new MimeMessage(session);
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(toAddress));
            message.setSubject(subject);

         /** These header information are automatically added to the mail so now it would be removed **/
            message.removeHeader("MIME-Version");
            message.removeHeader("Content-Type");
            message.removeHeader("Content-Transfer-Encoding");


            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            sdf.setTimeZone(TimeZone.getTimeZone(timeZone));

            if(mode ==1){
                body_message = body_message_allfailed;
            }

            else if(mode == 2){
                body_message = body_message_allreceivedfailed;
            }

            else if(mode == 3 ){
                body_message = body_message_allupdatedfailed;
            }
            message.setText(body_message + "\nFailure time is between "+ sdf.format(this.specifiedTime) +" and "+sdf.format(this.currentTime));

            Transport.send(message);

            BankresponseApplication.getLogger().info("Done Sending Mail to "+toAddress+" from "+username);

        } catch (Exception e) {
            BankresponseApplication.getLogger().error(e.getMessage());
            throw new RuntimeException(e);

        }
    }
    }

