package com.pmrice.dm.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pmrice.dm.model.Donor;
import com.pmrice.dm.util.SendEmail;

/**
 * Servlet implementation class MailServlet
 */
@WebServlet(
		description = "Sends emails to donors", 
		urlPatterns = { 
				"/MailServlet", 
				"/mail"
		})
public class MailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MailServlet() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String from = request.getParameter("from");
		String bcc = request.getParameter("bcc");
		String subject = request.getParameter("subject");
		String messageText = request.getParameter("message_text");
		switch (action) {
		case "to_individual":
			String selection = request.getParameter("to");
			String to = selection.substring(selection.lastIndexOf(':') + 2).trim();
			String err = SendEmail.send(to, from, bcc, subject, messageText);
			if (err != null) {
				String message = "This error ocurred on sending an email to " + selection + ": " + err;
				request.setAttribute("message", message);
				request.getRequestDispatcher("error.jsp").forward(request, response);
			} else {
				request.getRequestDispatcher("emailSuccessr.jsp").forward(request, response);
			}
			break;
		
		
		
		}
		
		
		
		
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
