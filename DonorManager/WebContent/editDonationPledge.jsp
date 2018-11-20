<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.pmrice.dm.model.*, com.pmrice.dm.util.*, java.util.*, java.sql.*"%>
<!DOCTYPE html>
<%  
	if (session == null || session.getAttribute("activeUser") == null){
		request.setAttribute("message", "Your session has expired. Please log in again.");
		request.getRequestDispatcher("/login.jsp").forward(request, response);
	}
	boolean userIsAdmin = ((User)session.getAttribute("activeUser")).isAdmin();
	Connection con = (Connection)request.getSession().getAttribute("connection");
	int donorId = (int)request.getAttribute("donor_id"); 
	List<Donation> donations = Donation.getForDonor(con, donorId);
	int donationId = (int)request.getAttribute("donation_id");
	Donation donation = new Donation(0, Util.today(), "0.00");
	if (donationId > 0) donation = Donation.get(con, donationId);
	List<Pledge> pledges = Pledge.getPledgeListForDonor(con, donorId);
	int pledgeId = (int)request.getAttribute("pledge_id");
	Pledge pledge = new Pledge(0, "New Pledge", Util.today());
	if (pledgeId > 0) pledge = Pledge.get(con, pledgeId);
%>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="dm.css">
	<script>
	
	function validatePledgeForm() {
		var x = document.forms["pledge_form"]["begin_date"].value;
		if (!Util.isDateValid(x)) {
			alert("Invalid date format: use mm/dd/yyyy");
			return false;
		}
		var x = document.forms["pledge_form"]["end_date"].value;
		if (!Util.isDateValid(x)) {
			alert("Invalid date format: use mm/dd/yyyy");
			return false;
		}
		var y = document.forms["pledge_form"]["amount"].value;
		if (!Currency.validate(x)) {
			alert ("Invalid currency value");
			return false;
		}
	}

	</script>
	<style>
		h2 {
			text-align: center;
			}
	</style>
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
																
		#donationBlock {
			width:400px;
			height:500px;
			border-style:none;
			margin:5px;
			}
																		
		#pledgeBlock {
			width:400px;
			height:500px;
			border-style:none;
			margin:5px;}
	
	</style>
<meta charset="UTF-8">
<title>Donor Manager - Donations and Pledges</title>
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

<div style="30px;text-align: center; font-weight: bold;" >Donations and Pledges for: <%=Donor.get(con, donorId).getName()%></div>

<table><tr><td>
<div id="donationBlock">
	<h4>Donation Details</h4>
	<form name="donation_form" action="main" method="post" onsubmit="validateDonationForm">
		<table>
			<tr><td></td></tr>
			<tr><td>Date</td><td><input type="text" class="textinput" name="date" value="<%=donation.getDate()%>"></td></tr>
			<tr><td>Description</td><td><input type="text" class="textinput" name="description" value="<%=donation.getDescription()%>"></td></tr>
			<tr><td>Amount</td><td><input type="text" class="textinput" name="amount" value="<%=donation.getAmount()%>"></td></tr>
			<tr><td>Note</td><td><textarea rows="3" cols="40" name="note"><%=donation.getNote()%></textarea></td></tr>
			<tr><td><input class="menubutton" type="submit" value="SAVE"/></td><td>
				<input type="hidden" name="donor_id" value="<%=Integer.toString(donorId)%>">
				<input type="hidden" name="donation_id" value="<%=donation.getId()%>">
					<input type="hidden" name="action" value="save_donation"></td></tr>
		</table>
	</form>
	
	<ul class="menu">
	<li>
	<form action="main" method="post" >
 		<input type="submit" value="Delete this Donation" id="donationDelete" class="menubutton" onmouseover="this.style.color='red'" onmouseout="this.style.color='blue'"/>
 		<input type="hidden" name="donor_id" value="<%=Integer.toString(donorId)%>"/>
 		<input type="hidden" name="donation_id" value="<%=Integer.toString(donationId)%>"/>
 		<input type="hidden" name="action" value="delete_donation"/>
	</form>
	</li>
	</ul>
	
	
	<ul class="list">
	<% for (int i = 0; i < donations.size(); i++) { %>
 	<li>
 		<form action="main" method="post">
 		<input type="submit" value="<%=i == 0 ? "New Donation" : donations.get(i).toString()%>" style="font-size:100%" class="list"/>
 		<input type="hidden" name="action" value="show_donation"/>
 		<input type="hidden" name="donor_id" value="<%=Integer.toString(donorId)%>"/>
 		<input type="hidden" name="donation_id" value="<%=Integer.toString(donations.get(i).getId())%>" />
 		</form>
 	</li>
	<%}%>
	</ul>
	
</div>
</td><td>
<div id="pledgeBlock">
	<h4>Pledge Details</h4>
	<form name="pledge_form" action="main" method="post" onsubmit="validatePledgeForm">
		<table>
			<tr><td></td></tr>
			<tr><td>Description</td><td><input type="text" class="textinput" name="description" value="<%=pledge.getDescription()%>"/></td></tr>
			<tr><td>Amount</td><td><input type="text" class="textinput" name="amount" value="<%=pledge.getAmount()%>"/></td></tr>
			<tr><td>Begin Date</td><td><input type="text" class="textinput" name="begin_date" value="<%=pledge.getBeginDate()%>"/></td></tr>
			<tr><td>End Date</td><td><input type="text" class="textinput" name="end_date" value="<%=pledge.getEndDate()%>"/></td></tr>
			<tr><td>Fulfilled</td><td><input type="checkbox" name="fulfilled" value="checked" <%= pledge.isFulfilled() ? "checked" : "" %>/></td></tr>
			<tr><td>Cancelled</td><td><input type="checkbox" name="cancelled" value="checked" <%= pledge.isCancelled() ? "checked" : "" %>/></td></tr>
			<tr><td>Note</td><td><textarea rows="3" cols="40" name="note"><%=pledge.getNote()%></textarea></td></tr>
			<tr><td><input class="menubutton" type="submit" value="SAVE"/></td><td>
				<input type="hidden" name="donor_id" value="<%=Integer.toString(donorId)%>"/>
				<input type="hidden" name="pledge_id" value="<%=pledge.getId()%>"/>
					<input type="hidden" name="action" value="save_pledge"/></td></tr>
		</table>
	</form>
	<ul class="menu">
		<li>
			<form action="main" method="post">
	 		<input type="submit" value="Delete this Pledge" id="pledgeDelete" class="menubutton" onmouseover="this.style.color='red'" onmouseout="this.style.color='blue'"/>
	 		<input type="hidden" name="donor_id" value="<%=Integer.toString(donorId)%>"/>
	 		<input type="hidden" name="pledge_id" value="<%=Integer.toString(pledgeId)%>"/>
	 		<input type="hidden" name="action" value="delete_pledge"/>
			</form>
		</li>
	</ul>
	
	
	<ul class="list">
	<% for (int i = 0; i < pledges.size(); i++) { %>
 	<li>
 		<form action="main" method="post">
 		<input type="submit" value="<%=i == 0 ? "New Pledge" : pledges.get(i).toString()%>" style="font-size:100%"/>
 		<input type="hidden" name="action" value="show_pledge"/>
 		<input type="hidden" name="donor_id" value="<%=Integer.toString(donorId)%>"/>
 		<input type="hidden" name="pledge_id" value="<%=Integer.toString(pledges.get(i).getId())%>" />
 		</form>
 	</li>
	<%}%>
	</ul>
</div></td></tr>
</table>

</body>
</html>