<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.pmrice.dm.model.*, com.pmrice.dm.util.*, java.util.*"%>
<!DOCTYPE html>
<%
	List<Donor> donors = (List<Donor>)request.getSession().getAttribute("donors");
	if (session == null || session.getAttribute("activeUser") == null){
		request.setAttribute("message", "Your session has expired. Please log in again.");
		request.getRequestDispatcher("/login.jsp").forward(request, response);
	}
	boolean userIsAdmin = ((User)request.getSession().getAttribute("activeUser")).isAdmin();
%>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="dm.css">
	<style>
		ul.itemlist {
			overflow:hidden; 
			overflow-y:scroll;
			list-style-type: none;
			margin: 0;
			padding: 0;}		
		h2 {
			text-align: center;	}
	</style>

<meta charset="UTF-8">
<title>Donor Manager - Reports</title>
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
		<li class="topmenu_item"<%=userIsAdmin ? "" : "hidden" %>>
			<a href="main?action=admin" >Admin</a>
		</li>
	</ul>

	<h2 class="title">Reports</h2>

	<div class="container">
	<ul class="itemlist">
		<li class="itemlist-item">
			<form action="reports" method="post" target="_blank">
				<input type="submit" value="O">
				All donations on: <input type="text" name="date" value="<%=Util.today() %>" size="10">
				<input type="hidden" name="action" value="day">
			</form>	
		</li>
		<li class="itemlist-item">	
			<form action="reports" method="post" target="_blank">
				<input type="submit" value="O">
				All donations in <select name="month">
					<option value="01">January</option>
					<option value="02">February</option>
					<option value="03">March</option>
					<option value="04">April</option>
					<option value="05">May</option>
					<option value="06">June</option>
					<option value="07">July</option>
					<option value="08">August</option>
					<option value="09">September</option>
					<option value="10">October</option>
					<option value="11">November</option>
					<option value="12">December</option>
					</select> 
				of <input type="number" name="year" value="<%=Util.year() %>" size="4">
				<input type="hidden" name="action" value="month">
			</form>
		</li>
		<li class="itemlist-item">
			<form action="reports" method="post" target="_blank">
				<input type="submit" value="O">
				All donations in <input type="number" name="year" value="<%=Util.year() %>" size="4">
				<input type="hidden" name="action" value="year">
			</form>
		</li>
		<li class="itemlist-item">
			<form action="reports" method="post" target="_blank">
				<input type="submit" value="O">
				All donations in <input type="number" name="year" value="<%=Integer.toString(Util.year()) %>" size="4">
				by: <select name="donor">
				<%for (Donor donor : donors){ if (donor.getId() == 0) continue;  %>
					<option><%=(donor.getId() + ": " + donor.getName())%></option>
				<%} %></select>
				<input type="hidden" name="action" value="donor">
			</form>
		</li>
		<li class="itemlist-item">
			<form action="reports" method="post" target="_blank">
				<input type="submit" value="O">
				Active Donors 
				<input type="hidden" name="action" value="donors">
			</form>
		</li>
	</ul>
	</div>



</body>
</html>