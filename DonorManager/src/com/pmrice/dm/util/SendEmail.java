package com.pmrice.dm.util;

import java.sql.Connection;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.pmrice.dm.model.MailConfig;

public class SendEmail {
	
	public static String host;
	public static String sslTrust;
	public static String userid;
	public static String password;

	public static String send(Connection con, String to, String from, String bcc, String subject, String messageText) {

		try {
			MailConfig config = MailConfig.get(con);
			Properties prop = new Properties();
			prop.setProperty("mail.smtp.auth", "true");
			prop.setProperty("mail.smtp.starttls.enable", "true");
			prop.setProperty("mail.smtp.host", config.getUrl());
			prop.setProperty("mail.smtp.port", Integer.toString(config.getPort()));
			
			Session session = Session.getInstance(prop, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(config.getUserid(), config.getPassword());
				}
			});

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			if (bcc != null && bcc.length() > 0) message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bcc));
			message.setSubject(subject);

			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent(messageText, "text/html");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);
			
			/* To add an attachment:
			 * 
			 * MimeBodyPart attachmentBodyPart = new MimeBodyPart();
			 * attachmentBodyPart.attachFile(new File("path/to/file"));
			 * multipart.addBodyPart(attachmentBodyPart);
			 * 
			 */

			message.setContent(multipart);
			
			Transport transport = session.getTransport();
			
	        // Send the message.
	        try {
	            System.out.println("Sending...");
	            
	            String host = config.getUrl();
	            String userid = config.getUserid();
	            String password = config.getPassword();
	            
	            transport.connect(host, userid, password);
	        	
	            transport.sendMessage(message, message.getAllRecipients());
	            return null; //success
	        } catch (Exception ex) {
	            return ex.getMessage();
	        } finally {
	            // Close and terminate the connection.
	            transport.close();
	        }		

		} catch (Exception e) {
			return "Error: " + e;
		}
	}
}
