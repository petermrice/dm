<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.pmrice.dm.model.*"%>
<!DOCTYPE html>
<% Donor donor = (Donor)request.getAttribute("donor"); %>
<html>
<head>
<meta charset="UTF-8">
<title>Donor Manager - Donor</title>
</head>
<body>
<h1>View, Edit or Create a Donor</h1>
<form action="/dm" method="post">
	<table>
		<tr><td>Name</td><td><input type="text" name="name" value="<%=donor.getName()%>"/></td></tr>
		<tr><td>Last Name</td><td><input type="text" name="name" value="<%=donor.getLastname()%>"/></td></tr>
		<tr><td>Address</td><td><input type="text" name="name" value="<%=donor.getAddress1()%>"/></td></tr>
		<tr><td>Address</td><td><input type="text" name="name" value="<%=donor.getAddress2()%>"/></td></tr>
		<tr><td>City</td><td><input type="text" name="name" value="<%=donor.getCity()%>"/></td></tr>
		<tr><td>State</td><td><input type="text" name="name" value="<%=donor.getState()%>"/></td></tr>
		<tr><td>ZIP</td><td><input type="text" name="name" value="<%=donor.getZip()%>"/></td></tr>
		<tr><td>Telephone</td><td><input type="text" name="name" value="<%=donor.getTelephone()%>"/></td></tr>
		<tr><td>EMail</td><td><input type="email" name="name" value="<%=donor.getEmail()%>"/></td></tr>
		<tr><td>Notes</td><td><input type="text" name="name" value="<%=donor.getNotes()%>"/></td></tr>
	</table>
	<input type="submit" value="Save"/>
</form>
</body>
</html>