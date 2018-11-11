<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<link rel="stylesheet" type="text/css" href="dm.css">
<head>
	<link rel="stylesheet" type="text/css" href="dm.css">

<meta charset="UTF-8">
<title>Donor Manager Login</title>
</head>
<body>
	<h2 class="title">Please log in:</h2>

<div class="container">
	<form action="main" method="post" >
		<input type="hidden" name="action" value="login"/>
		<table>
			<tr>
				<td>User ID</td>
				<td><input type="text" name="userid"></td>
			</tr>
			<tr>
				<td>Password</td>
				<td><input type="password" name="password"></td>
			</tr>
		</table>
		<input type="submit" value="Submit" class="menubutton">
	</form>
    <h3><font color="#FF0000"><%String msg = (String)request.getAttribute("message");
      	if (msg != null && msg.length() > 0) out.print(msg);%></font></h3>
</div>
</body>
</html>