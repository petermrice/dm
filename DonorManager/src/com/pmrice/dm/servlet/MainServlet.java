package com.pmrice.dm.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pmrice.dm.model.Donor;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet(
		description = "The one servlet for the Donor Manager app", 
		urlPatterns = { 
				"/MainServlet", 
				"/dm"
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
		case "show_donors":	
			request.getRequestDispatcher("/donors.jsp").forward(request, response);
			break;
		case "donor":	
			request.setAttribute("donor", new Donor());
			request.getRequestDispatcher("/donor.jsp").forward(request, response);
			break;
		case "show_information":  
			request.getRequestDispatcher("/info.jsp").forward(request, response);
			break;
		case "show_mail":	
			request.getRequestDispatcher("/mail.jsp").forward(request, response);
			break;
		}
	}
	
	private void loginAction(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException {
		String userid = request.getParameter("userid");
		String password = request.getParameter("password");
		if (login(userid, password)) {
			request.getRequestDispatcher("/donors.jsp").forward(request, response);
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
