package com.volare_automation.springwebshop.service;

import com.lowagie.text.DocumentException;
import com.volare_automation.springwebshop.model.Mail;

import javax.mail.MessagingException;
import java.io.IOException;

public interface EmailServiceInterface {
    public void sendMail(Mail mail) throws MessagingException, IOException, DocumentException;
}
