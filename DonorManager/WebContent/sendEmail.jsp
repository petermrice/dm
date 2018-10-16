<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Donor Manager - Email</title>
</head>
<body>
<h2>Send Emails</h2>
<form action="mail">
	<table>
		<tr><td>From:</td><td><input type="email" name="from"></td></tr>
		<tr><td>BCC:</td><td><input type="email" name="bcc"></td></tr>
		<tr><td>Subject:</td><td><input type="email" name="subject"></td></tr>
		<tr><td>Message:</td><td><textarea rows="20" cols="80" name="from"></textarea></td></tr>
		<tr><td><input type="submit" value="Send"></td></tr>
	</table>
</form>
</body>
</html>