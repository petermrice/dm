package com.pmrice.dm.util;

	import java.util.*;
	import javax.mail.*;
	import javax.mail.internet.*;

	public class SendEmail {

	   public static String send(String to, String from, String bcc, String subject, String messageText) {    
	      String host = "localhost";
	      Properties properties = System.getProperties();
	      properties.setProperty("mail.smtp.host", host);
	      Session session = Session.getDefaultInstance(properties);
	      try {
	         MimeMessage message = new MimeMessage(session);
	         message.setFrom(new InternetAddress(from));
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
	         message.addRecipient(Message.RecipientType.BCC, new InternetAddress(bcc));
	         message.setSubject(subject);
	         message.setText(messageText);
	         Transport.send(message);
	         return null;
	      } catch (MessagingException mex) {
	         return mex.toString();
	      }
	   }
	}

