package com.pmrice.dm.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pmrice.dm.model.Donation;
import com.pmrice.dm.model.Donor;
import com.pmrice.dm.model.Pledge;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet(
		description = "The servlet for editing donors, donations and pledges", 
		urlPatterns = { 
				"/MainServlet", 
				"/main"
		})
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public MainServlet() {
    	
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if (action == null | action == "") {
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
		}
		switch (action) {
		case "login": 
			loginAction(request, response);	
			break;
		case "donor":	
			forwardToEditDonors(request, response, 0);
			break;
		case "show_information":  
			request.getRequestDispatcher("/info.jsp").forward(request, response);
			break;
		case "show_mail":	
			request.getRequestDispatcher("/mail.jsp").forward(request, response);
			break;
			
		case "save_donation":{
			int donor_id = Integer.parseInt((String)request.getParameter("donor_id"));
			int donation_id = Integer.parseInt((String)request.getParameter("donation_id"));
			Donation donation = new Donation();
			donation.setDonorId(donor_id);
			donation.setId(donation_id);
			donation.setDate((String)request.getParameter("date"));
			donation.setAmount(request.getParameter("amount"));
			donation.setDescription(request.getParameter("description"));
			donation.setNote(request.getParameter("note"));
			if (donation_id == 0) Donation.add(donation);
			else Donation.update(donation);
			forwardToEditDonationsPledges(request, response, donor_id, 0, 0);
			}
			break;
		case "show_donation":{
			int donationId = Integer.parseInt(request.getParameter("donation_id"));
			int donorId = Integer.parseInt(request.getParameter("donor_id"));
			forwardToEditDonationsPledges(request, response, donorId, donationId, 0);}
			break;
		case "delete_donation":{
			int donationId = Integer.parseInt(request.getParameter("donation_id"));
			int donorId = Integer.parseInt(request.getParameter("donor_id"));
			Donation.remove(donationId);
			forwardToEditDonationsPledges(request, response, donorId, 0, 0);	
			}
			break;
			
		case "save_pledge":{
			int donor_id = Integer.parseInt((String)request.getParameter("donor_id"));
			int pledge_id = Integer.parseInt((String)request.getParameter("pledge_id"));
			Pledge pledge = new Pledge();
			pledge.setDonorId(donor_id);
			pledge.setId(pledge_id);
			pledge.setBeginDate((String)request.getParameter("begin_date"));
			pledge.setEndDate((String)request.getParameter("end_date"));
			pledge.setAmount(request.getParameter("amount"));
			pledge.setDescription(request.getParameter("description"));
			pledge.setFulfilled(request.getParameter("fulfilled") != null);
			pledge.setCancelled(request.getParameter("cancelled") != null);
			pledge.setNote(request.getParameter("note"));
			if (pledge_id == 0) Pledge.add(pledge);
			else Pledge.update(pledge);
			forwardToEditDonationsPledges(request, response, donor_id, 0, 0);
			}
			break;
		case "show_pledge":{
			int pledgeId = Integer.parseInt(request.getParameter("pledge_id"));
			int donorId = Integer.parseInt(request.getParameter("donor_id"));
			forwardToEditDonationsPledges(request, response, donorId, 0, pledgeId);
			}
			break;
		case "delete_pledge":{
			int pledgeId = Integer.parseInt(request.getParameter("pledge_id"));
			int donorId = Integer.parseInt(request.getParameter("donor_id"));
			Donation.remove(pledgeId);
			forwardToEditDonationsPledges(request, response, donorId, 0, 0);	
			}
			break;

		case "save_donor":
			int id = Integer.parseInt(request.getParameter("donor_id"));
			Donor donor = new Donor();
			if (id != 0) donor = Donor.get(id);
			donor.setName(request.getParameter("name"));
			donor.setLastname(request.getParameter("lastname"));
			donor.setAddress1(request.getParameter("address1"));
			donor.setAddress2(request.getParameter("address2"));
			donor.setCity(request.getParameter("city"));
			donor.setState(request.getParameter("state"));
			donor.setZip(request.getParameter("zip"));
			donor.setCountry(request.getParameter("country"));
			donor.setTelephone(request.getParameter("telephone"));
			donor.setEmail(request.getParameter("email"));
			donor.setNotes(request.getParameter("notes"));
			Object o = request.getParameter("hidden");
			if (o != null)
			donor.setHidden(o.equals("false") ? false : true);
			if (id != 0) Donor.update(donor);
			else Donor.add(donor);
			request.getSession().setAttribute("donors", Donor.getDonors()); // refresh the list
			forwardToEditDonors(request, response, 0);
			break;
		case "show_donor":{
			int donorId = Integer.parseInt(request.getParameter("donor_id"));
			forwardToEditDonors(request, response, donorId);}
			break;
		case "delete_donor":{
			int donorId = Integer.parseInt(request.getParameter("donor_id"));
			Donor.remove(donorId);
			request.getSession().setAttribute("donors", Donor.getDonors()); // refresh the list
			forwardToEditDonors(request, response, 0);
			break;}
		case "logout":{
			HttpSession session = request.getSession();
			if (session != null) session.invalidate();
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			}
		}
	}
	
	private void forwardToEditDonors(HttpServletRequest request, HttpServletResponse response, int donor_id) throws ServletException, IOException{
		request.setAttribute("donor_id", donor_id);
		request.setAttribute("donor_list", request.getSession().getAttribute("donors"));
		request.getRequestDispatcher("/editDonor.jsp").forward(request, response);
	}
	
	private void forwardToEditDonationsPledges(HttpServletRequest request, HttpServletResponse response, int donor_id, int donation_id, int pledge_id) throws ServletException, IOException{
		request.setAttribute("donor_id", donor_id);
		request.setAttribute("donation_id", donation_id);
		request.setAttribute("pledge_id", pledge_id);
		request.getRequestDispatcher("/editDonationPledge.jsp").forward(request, response);
	}

	/**
	 * Successful login forwards to the edit donor screen.
	 * Failure returns to the login screen with a message.
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void loginAction(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException {
		String userid = request.getParameter("userid");
		String password = request.getParameter("password");
		if (login(userid, password)) {
			HttpSession session = request.getSession(true);
			session.setAttribute("donors", Donor.getDonors());
			forwardToEditDonors(request, response, 0);
		} else {
			request.setAttribute("message", "Invalid login. Try again.");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private boolean login(String userid, String password) {
		if (userid.equals("admin") && password.equals("admin")) return true;
		return false;
	}

}

