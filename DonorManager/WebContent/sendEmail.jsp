<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, com.pmrice.dm.model.*"%>
<%  
	List<Donor> donors = Donor.getDonors();
	if (session == null || session.getAttribute("activeUser") == null){
		request.setAttribute("message", "Your session has expired. Please log in again.");
		request.getRequestDispatcher("/login.jsp").forward(request, response);
	}
	boolean userIsAdmin = ((User)request.getSession().getAttribute("activeUser")).isAdmin();
%>
<!DOCTYPE html>
<html>
<head>
	<style>
		ul.topmenu {
			list-style-type: none;
			margin: 0;
			padding: 0;
			overflow: hidden;
			background-color: #333;
			}
		li.topmenu_item {
		float: left;
			}
		li.topmenu_item a {
			display: block;
			color: white;
			text-align: center;
			padding: 14px 16px;
			text-decoration: none;
			}
		li a:hover {
			background-color: $111;
			}
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


<h2>Send Emails</h2>
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
		<tr><td><input id="emailSend" type="submit" value="SEND"></td></tr>
	</table>
</form>
</body>
</html>