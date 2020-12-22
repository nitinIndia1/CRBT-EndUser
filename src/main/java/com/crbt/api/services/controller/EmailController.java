package com.crbt.api.services.controller;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.crbt.api.services.bean.SendEmailBean;

@RestController
public class EmailController {

	private static final Logger logger = LoggerFactory.getLogger( EmailController.class );
	
	@RequestMapping( value = "/sendEmail", method = RequestMethod.POST )
	public void sendEmail( @RequestBody final SendEmailBean email ) {
		logger.info("Received request to send email with credentials: {}", email);
		String msg="<div style='color:black;'>"
				+ "<b>NAME: </b>"+email.getName()+"<br/>"
				+ "<b>MOBILE: </b>"+email.getMobile()+"<br/>"
				+ "<b>EMAIL: </b>"+email.getEmail()+"<br/>"
				+ "<b>QUERY TYPE: </b>"+email.getQueryType()+"<br/>"
				+ "<b>MESSAGE: </b>"+email.getBody()+"\n\n"
				+ ""
		
			+ "</div>";
		
		Mail( email, msg );
	}
	
	public void Mail( SendEmailBean email, String message )
	{
		final String username="info@marhaba.com.ly";
		final String password="RVxIMWJvlk3yOvbIGSx9";
		 String host = "smtp.gmail.com";
	     Properties props = new Properties();
	     //props.put("mail.transport.protocol", "smtp");
	     props.put("mail.smtp.auth", "true");
	     props.put("mail.smtp.starttls.enable", "false");
	     props.put("mail.smtp.host", host);
	     props.put("mail.smtp.port", "465");
	     props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	     props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
         Session session = Session.getInstance(props,new Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
	    	return new PasswordAuthentication(username,password);
			}   
		});
		    try
	        {
	          MimeMessage msg = new MimeMessage(session);
	          msg.setFrom(new InternetAddress(username, "No-Reply"));
	          msg.setReplyTo(InternetAddress.parse(email.getEmail(), false));
	          msg.setContent(message, "text/HTML;charset=utf-8");
	          msg.setSubject( email.getSubject(), "UTF-8");
	          msg.setSentDate(new Date());
	          msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse("techsupport@rebiana.ly", false));
	          msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse("a.abukraa@rebiana.ly", false));
	          msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("info@marhaba.com.ly", false));
	          logger.info("Message is ready");
	          Transport.send(msg);
	          logger.info("EMail Sent Successfully!!");
	        }
	        catch (Exception e) {
	          e.printStackTrace();
	        }	    
		    
	}
}
