<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	String to = (String)request.getAttribute("to");
	String from = request.getParameter("from");
	String bcc = request.getParameter("bcc");
	String subject = request.getParameter("subject");
	String messageText = request.getParameter("message_text");
%>
<html>
<head>
<meta charset="UTF-8">
<title>Donation Manager - Email Sent</title>
</head>
<body>
<h1>Success!</h1>
This email was sent:
To: <%=to %>
From: <%=from %>
BCC: <%=bcc %>
Subject: <%=subject %>
Message Text: <%=messageText %>

</body>
</html>