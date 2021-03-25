package com.volare_automation.springwebshop;

import org.checkerframework.checker.units.qual.A;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class JavaMailConfig {

    public static void sendEmail(String recipient) throws MessagingException {

        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String myEmail = "robertvolaric973@gmail.com";
        String password = "n5%a5ru4Cbbz";

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuth(){
                return new PasswordAuthentication(myEmail, password);
            }

        });

        Message message = prepareMessage(session, myEmail, recipient);

        Transport.send(message);
        System.out.println("Message sent!");
    }

    private static Message prepareMessage(Session session, String myEmail, String recipient){

       try {
           Message message = new MimeMessage(session);
           message.setFrom(new InternetAddress(myEmail));
           message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
           message.setSubject("Hi");
           message.setText("Hi there");
           return message;
       }catch(Exception ex){

        }
        return null;
    }
}
