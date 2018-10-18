<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, com.pmrice.dm.model.*"%>
<%  
	List<Donor> donors = Donor.getDonors();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Donor Manager - Email</title>
</head>
<body>
<h2>Send Emails</h2>
<form action="mail" target="_blank">
	<table>
		<tr><td>To:</td><td><select name="to">
		<%for (Donor donor : donors){  %>
			<option><%=(donor.getName() + " : " + donor.getEmail()) %></option>
		<%} %></select></td></tr>
		<tr><td>From:</td><td><input type="email" name="from" required></td></tr>
		<tr><td>BCC:</td><td><input type="email" name="bcc"></td></tr>
		<tr><td>Subject:</td><td><input type="text" name="subject" required></td></tr>
		<tr><td>Message:</td><td><textarea rows="20" cols="80" name="from"></textarea></td></tr>
		<tr><td><input type="hidden" name="action" value="to_individual">
		<tr><td><input type="submit" value="Send"></td></tr>
	</table>
</form>
</body>
</html>