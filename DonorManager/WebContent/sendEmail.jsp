<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, com.pmrice.dm.model.*, java.sql.*"%>
<%  
	List<Donor> donors = Donor.getDonors((Connection)session.getAttribute("connection"));
	if (session == null || session.getAttribute("activeUser") == null){
		request.setAttribute("message", "Your session has expired. Please log in again.");
		request.getRequestDispatcher("/login.jsp").forward(request, response);
	}
	boolean userIsAdmin = ((User)request.getSession().getAttribute("activeUser")).isAdmin();
%>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="dm.css">
	<style>
		#emailSend {
			border:line;
			color:Tomato;
			font-size: 20px;}
	</style>


<meta charset="UTF-8">
<title>Donor Manager - Email</title>
</head>
<body>
	<ul class="topmenu">
		<li class="topmenu_item">	
			<a href="main?action=show_donor">Donors</a>		
		</li>
		<li class="topmenu_item">
			<a href="main?action=emails">Emails</a>
		</li>
		<li class="topmenu_item">
			<a href="main?action=reports">Reports</a>
		</li>
		<li class="topmenu_item">
			<a href="main?action=logout">Logout</a>
		</li>
		<li class="topmenu_item" <%=userIsAdmin ? "" : "hidden" %>>
			<a href="main?action=admin">Admin</a>
		</li>
	</ul>


<h2 class="title">Send Emails</h2>
<div class="container">
<form action="mail" target="_blank">
	<table>
		<tr><td>To:</td><td><select name="to">
		<%for (Donor donor : donors){ if (donor.getId() == 0) continue;  %>
			<option><%=(donor.getName() + " : " + donor.getEmail()) %></option>
		<%} %></select></td></tr>
		<tr><td>From:</td><td><input type="email" name="from" required></td></tr>
		<tr><td>BCC:</td><td><input type="email" name="bcc"></td></tr>
		<tr><td>Subject:</td><td><input type="text" name="subject" required></td></tr>
		<tr><td>Message:</td><td><textarea rows="20" cols="80" name="message_text"></textarea></td></tr>
		<tr><td><input type="hidden" name="action" value="to_individual">
			<input id="emailSend" type="submit" value="SEND"></td></tr>
	</table>
</form>
</div>
</body>
</html>