<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.pmrice.dm.model.*, java.util.*"%>
<!DOCTYPE html>
<% List<Donor> donors =  Donor.getHiddenDonors(); %>
<html>
<head>
	<style>
		#commit {
			border:line;
			color:Tomato;
			font-size: 16px;}		
			
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
	</style>
	<meta charset="UTF-8">
	<title>Manage Hidden Donors</title>
</head>
<body>
	<ul class="topmenu">
		<li class="topmenu_item">	
			<a href="main?action=show_donor">Donors</a>		
		</li>
	</ul>
<h2>Hidden Donor List</h2>
<br>
<ul>
<% for (Donor donor : donors){ %>
	<li><form action="main" >
		<%=donor.getName()%>
		<%=donor.getAddress1()%>
		<input type="checkbox" name="unhide" value="checked">Un-hide
		<input type="checkbox" name="delete" value="checked">Delete
		<input type="hidden" name="donor_id" value="<%=donor.getId()%>">
		<input type="hidden" name="action" value="manage_donor">
		<input type="submit" value="Submit" id="commit">
	</form></li>
<%}%>
</ul>

</body>
</html>