package com.vaccinePortal.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
@Service
@Transactional
public class MailServiceImpl implements MailService{
	@Autowired
	JavaMailSenderImpl mailSender;
	
	

	@Override
	@Async
	public void sendMail(String to, String msg, String subject) throws Exception {
		mailSender.setHost("smtp.gmail.com");
	    mailSender.setPort(587);
	    
	    mailSender.setUsername("sankett123456789@gmail.com");
	    mailSender.setPassword("ombhzuhtjcydgaiq");
	    
	    Properties props = mailSender.getJavaMailProperties();
	    MimeMessage mesg= mailSender.createMimeMessage(); 
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "true");
	    MimeMessageHelper helper = new MimeMessageHelper(mesg, true);
	    helper.setTo(to);

	    // use the true flag to indicate the text included is HTML
	    helper.setText("<html>"+msg+"</body></html>", true);
	    helper.setSubject(subject);

	    mailSender.send(mesg);
	}
	
}
