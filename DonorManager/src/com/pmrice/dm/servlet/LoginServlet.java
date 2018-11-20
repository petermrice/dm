package com.pmrice.dm.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pmrice.dm.model.Donor;
import com.pmrice.dm.model.User;
import com.pmrice.dm.util.Util;

public class LoginServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String orgid = request.getParameter("orgid");
		Connection con = Util.getConnection(orgid);
		String userid = request.getParameter("userid");
		String password = request.getParameter("password");
		boolean valid = User.isUserValid(con, userid, password);
		if (valid) {
			User user = User.get(con, userid);
			// create a new session
			HttpSession session = request.getSession(true);
			session.setAttribute("orgid", orgid);
			session.setAttribute("connection", con);
			session.setAttribute("activeUser", user);
			session.setAttribute("donors", Donor.getDonors(con));
			request.setAttribute("donor_id", 0);
			request.getRequestDispatcher("/editDonor.jsp").forward(request, response);
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
	

}
