package com.pmrice.dm.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendEmail {
	
	public static String host;
	public static String sslTrust;
	public static String userid;
	public static String password;
	

	public static String send(String to, String from, String bcc, String subject, String messageText) {
		String host = "localhost";
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		Session session = Session.getDefaultInstance(properties);
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			if (bcc != null && bcc.length() > 0)
				message.addRecipient(Message.RecipientType.BCC, new InternetAddress(bcc));
			message.setSubject(subject);
			message.setText(messageText);
			Transport.send(message);
			return null;
		} catch (MessagingException mex) {
			return mex.toString();
		}
	}

	public static void send2(String to, String from, String bcc, String subject, String messageText) throws Exception {

		Properties prop = new Properties();
		File propfile = new File("mailhost.properties");
		prop.load(new FileInputStream(propfile));

		Session session = Session.getInstance(prop, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(prop.getProperty("userid"), prop.getProperty("password"));
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

		Transport.send(message);
	}
}
