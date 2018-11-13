<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.pmrice.dm.model.*, com.pmrice.dm.util.*, java.util.*"%>
<!DOCTYPE html>
<%  
	if (session == null || session.getAttribute("activeUser") == null){
		request.setAttribute("message", "Your session has expired. Please log in again.");
		request.getRequestDispatcher("/login.jsp").forward(request, response);
	}
	boolean userIsAdmin = ((User)request.getSession().getAttribute("activeUser")).isAdmin();
	List<Donor> donors = (List<Donor>)request.getSession().getAttribute("donors");
	int donorId = (int)request.getAttribute("donor_id"); 
	Donor donor = donors.get(0);
	if (donorId > 0) donor = Donor.get(donorId);
%>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="dm.css">

	<script>
	function validateDonorForm() {
		var x = document.forms["edit_form"]["lastname"].value;
		if (x == "") {
			alert("Lastname must be filled out");
			return false;
		}
	}
	function checkDonor() {
		var x = <%=donorId%>;
		if (x == "0") {
			alert("You must select a donor before going to donations.");
			return false;
		}
	}
	</script>
	
	<style>
		ul.list {
			overflow:hidden; 
			overflow-y:scroll;
			list-style-type: none;
			margin: 20px;
			padding: 0;
			}
	
		ul.menu {
			overflow:hidden; 
			overflow-y:scroll;
			list-style-type: none;
			margin: 0;
			padding: 0;}
		
		.textinput {
			width:300px;
		}
		
		#donorSave {
			border:line;
			color:Tomato;
			font-size: 20px;}

		.menubutton {
			border:line;
			color:Blue;
			font-size: 20px;}	
	</style>
	
<meta charset="UTF-8">
<title>Donor Manager - Donor</title>
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
		<li class="topmenu_item">
			<a href="displayHiddenDonors.jsp">Manage Hidden Donors</a>
		</li>
		<li class="topmenu_item">
			<a href="help.html" target="_blank">Help</a>
		</li>

	</ul>

	<h2 class="title">View and Edit Donor Information</h2>

<div id="donorBlock" class="container" >
<table><tr>
	<td>
	<h4>Donor Information</h4>
	<form name="edit_form" action="main" method="post" onsubmit="return validateDonorForm()">
		<table>
			<tr><td>Name</td><td><input type="text" name="name" value="<%=donor.getName()%>"/></td></tr>
			<tr><td>Last Name</td><td><input class="textinput" type="text" name="lastname" value="<%=donor.getLastname()%>"></td></tr>
			<tr><td>Address</td><td><input type="text" class="textinput" name="address1" value="<%=donor.getAddress1()%>"></td></tr>
			<tr><td>Address</td><td><input type="text" class="textinput" name="address2" value="<%=donor.getAddress2()%>"></td></tr>
			<tr><td>City</td><td><input type="text" class="textinput" name="city" value="<%=donor.getCity()%>"></td></tr>
			<tr><td>State</td><td><input type="text" class="textinput" name="state" value="<%=donor.getState()%>"></td></tr>
			<tr><td>ZIP</td><td><input type="text" class="textinput" name="zip" value="<%=donor.getZip()%>"></td></tr>
			<tr><td>Country</td><td><input type="text" class="textinput" name="country" value="<%=donor.getCountry()%>"></td></tr>
			<tr><td>Telephone</td><td><input type="tel" placeholder="000-000-0000" class="textinput" name="telephone" value="<%=donor.getTelephone()%>"></td></tr>
			<tr><td>EMail</td><td><input type="email" class="textinput" name="email" value="<%=donor.getEmail()%>"></td></tr>
			<tr><td>Hide</td><td><input type="checkbox" name="hidden" value="checked" <%=donor.isHidden() ? "checked" : ""%>></td></tr>
			<tr><td>Notes</td><td><textarea rows="3" cols="40" name="notes"><%=donor.getNotes()%></textarea></td></tr>
			<tr><td><input type="hidden" name="donor_id" value="<%=donor.getId()%>">
					<input type="hidden" name="action" value="save_donor">
					<input id="donorSave" type="submit" value="SAVE"></td></tr>
		</table>
	</form>
	</td>
	<td>
		<ul class="list">
		<% for (int i = 0; i < donors.size(); i++) { %>
	 	<li>
	 		<form action="main" method="post">
	 		<input type="submit" value="<%=donors.get(i).toString()%>" style="font-size:100%"/>
	 		<input type="hidden" name="action" value="show_donor"/>
	 		<input type="hidden" name="donor_id" value="<%=Integer.toString(donors.get(i).getId())%>"/>
	 		</form>
	 	</li>
		<%}%>
		</ul>
		<br>
		<ul class="menu">
		<li><form action="main" method="post" onsubmit="return checkDonor()">
	 		<input type="submit" value="Donations for this Donor" class="menubutton">
	 		<input type="hidden" name="action" value="show_donation">
	 		<input type="hidden" name="donor_id" value="<%=donor.getId()%>">
	 		<input type="hidden" name="donation_id" value="0">
			</form>
	 		<h4><font color="#FF0000"><%String msg = (String)request.getAttribute("message");
      	if (msg != null && msg.length() > 0) out.print(msg);%></font></h4>
		</li>

		</ul>
	</td>
</tr></table>	
</div>


</body>
</html>