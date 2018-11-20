<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.pmrice.dm.model.*, com.pmrice.dm.util.*, java.util.*, java.sql.*"%>
<!DOCTYPE html>
<%  
	if (session == null || session.getAttribute("activeUser") == null){
		request.setAttribute("message", "Your session has expired. Please log in again.");
		request.getRequestDispatcher("/login.jsp").forward(request, response);
	}
	boolean userIsAdmin = ((User)request.getSession().getAttribute("activeUser")).isAdmin();

	List<User> users = User.getUsers((Connection)session.getAttribute("connection"));
	User user = null;
	Object usero = request.getAttribute("userid"); 
	if (usero == null) user = new User("","",false);
	else user = User.get((Connection)session.getAttribute("connection"),(String)usero);
	Object m = request.getAttribute("message");
	String message = (m != null) ? (String)m : "";
	String userid = user.getUserid();
	String password = user.getPassword();
	boolean admin = user.isAdmin();
	
	MailConfig mconfig = MailConfig.get((Connection)session.getAttribute("connection"));
%>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="dm.css">
	<script>
	function validateUserForm() {
		var x = document.forms["edit_form"]["userid"].value;
		if (x == "") {
			alert("userid must not be blank");
			return false;
		}
		x = document.forms["edit_form"]["password"].value;
		if (x == "") {
			alert("password must not be blank");
			return false;
		}
	}
	function checkMessage() {
		var x = <%= message%>;
		if (x != "") alert(x);
	}
	</script>
<meta charset="UTF-8">
<title>Donor Manager - Admin</title>
</head>
<body onload="checkMessage()">
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
<div class="container">
<table>
<tr><td>
<div id="userBlock" style="width: 300px; height:500px">
	<h2>User Manager</h2>
	<form name="edit_form" action="main" method="post" onsubmit="return validateUserForm()">
		<table>
			<tr><td>Userid</td><td><input type="text" name="userid" value="<%=userid %>"/></td></tr>
			<tr><td>Password</td><td><input type="text" name="password" value="<%=password %>"></td></tr>
			<tr><td>Admin</td><td><input type="checkbox" name="admin" value="Admin" <%= admin ? "checked" : "" %>/></td></tr>
			<tr><td><input type="hidden" name="action" value="save_user">
					<input id="userSave" type="submit" value="SAVE" class="menubutton"></td></tr>
		</table>
	</form>
	
	<form action="main" method="post" >
 		<input type="submit" value="Delete this User" id="donationDelete" class="deletebutton" onmouseover="this.style.color='red'" onmouseout="this.style.color='blue'"/>
 		<input type="hidden" name="userid" value="<%=userid%>"/>
 		<input type="hidden" name="action" value="delete_user"/>
	</form>
	
	
	<ul class="list">
	<% for (int i = 0; i < users.size(); i++) { %>
 	<li>
 		<form action="main" method="post">
 		<input type="submit" value="<%=users.get(i).getUserid()%>" style="font-size:100%"/>
 		<input type="hidden" name="action" value="show_user"/>
 		<input type="hidden" name="userid" value="<%=users.get(i).getUserid()%>"/>
 		</form>
 	</li>
	<%}%>
	</ul>
	<br>
</div>
</td><td>
<div id="mailBLock" style="width: 400px; height:500px">
<h2>EMail Setup</h2>
<form action="mail?action=save_mail_data" method="post">
	<table>
	<tr><td>Mail Host URL</td><td><input type="text" name="url" value="<%=mconfig.getUrl() %>"></td></tr>
	<tr><td>SMTP Port</td><td><input type="number" name="port"value="<%=mconfig.getPort() %>"></td></tr>
	<tr><td>Authorized Userid</td><td><input type="text" name="userid" value="<%=mconfig.getUserid() %>"></td></tr>
	<tr><td>Authorized User Password</td><td><input type="text" name="password" value="<%=mconfig.getPassword() %>"></td></tr>
	<tr><td><input type="submit" value="SAVE" class="menubutton"></td><td><input type="hidden" name="action" value="save_mail_data"></td></tr>
	</table>
</form>
</div>
</td></tr></table>
</div>
</body>
</html>