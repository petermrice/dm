<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.pmrice.dm.model.*, com.pmrice.dm.util.*, java.util.*"%>
<!DOCTYPE html>
<%  
	List<Donor> donors = (List<Donor>)request.getAttribute("donor_list");
	int donorId = (int)request.getAttribute("donor_id"); 
	Donor donor = new Donor(0, "New Donor");
	if (donorId > 0) donor = Donor.get(donorId);
%>
<html>
<head>
	<script>
	function validateDonorForm() {
		var x = document.forms["edit_form"]["lastname"].value;
		if (x == "") {
			alert("Lastname must be filled out");
			return false;
		}
	}
	
	function warnOnDeletion() {
		alert("This action will delete the data from the system.\nUse with care!")
	}


	</script>
	<style>
	
		ul {
			overflow:hidden; 
			overflow-y:scroll;
			list-style-type: none;
			margin: 0;
			padding: 0''}
	
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
				
		#donorBlock {
			width:400px;
			height:500px;
			border-style:none;
			margin:5px;
			title="View, Edit or Create a Donor";}
							
	</style>
<meta charset="UTF-8">
<title>Donor Manager - Donor</title>
</head>
<body>
	<h2>View and Edit Donor Information</h2>

<div id="donorBlock" >
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
			<tr><td>Telephone</td><td><input type="text" class="textinput" name="telephone" value="<%=donor.getTelephone()%>"></td></tr>
			<tr><td>EMail</td><td><input type="email" class="textinput" name="email" value="<%=donor.getEmail()%>"></td></tr>
			<tr><td>Notes</td><td><textarea rows="3" cols="30" name="notes"> <%=donor.getNotes()%></textarea></td></tr>
			<tr><td><input type="hidden" name="donor_id" value="<%=donor.getId()%>">
					<input type="hidden" name="action" value="save_donor">
					<input id="donorSave" type="submit" value="SAVE"></td></tr>
		</table>
	</form>
	<ul>
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
	</td>
	<td>
		<table><tr><td>
		<form action="main" method="post" onSubmit="return warnOnDeletion()">
	 		<input type="submit" value="Delete this Donor" class="menubutton" onmouseover="warnOnDeletion()">
	 		<input type="hidden" name="donor_id" value="<%=donor.getId()%>">
	 		<input type="hidden" name="action" value="delete_donor">
		</form>
		<form action="main" method="post">
	 		<input type="submit" value="Donations for this Donor" class="menubutton">
	 		<input type="hidden" name="action" value="show_donation">
	 		<input type="hidden" name="donor_id" value="<%=donor.getId()%>">
	 		<input type="hidden" name="donation_id" value="0">
		</form>
		</td><td>
		<form action="main" method="post">
	 		<input type="submit" value="Logout" id="logout" class="menubutton">
	 		<input type="hidden" name="action" value="logout">
		</form>
		</td><td>
		</td></tr></table>
	</td>
</tr></table>	
</div>

</body>
</html>