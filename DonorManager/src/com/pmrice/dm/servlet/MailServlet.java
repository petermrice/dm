package com.pmrice.dm.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pmrice.dm.model.MailConfig;
import com.pmrice.dm.util.SendEmail;

/**
 * Servlet implementation class MailServlet. Handles mail unit
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
			{
				String selection = request.getParameter("to");
				String to = selection.substring(selection.lastIndexOf(':') + 2).trim();
				request.setAttribute("to", to);
				String err = SendEmail.send(to, from, bcc, subject, messageText);
				if (err != null) {
					String message = "This error ocurred on sending an email to " + selection + ": " + err;
					request.setAttribute("message", message);
					request.getRequestDispatcher("error.jsp").forward(request, response);
				} else {
					request.getRequestDispatcher("emailSuccess.jsp").forward(request, response);
				}
			}
			break;
		case "save_mail_data":
			{	
				MailConfig config = new MailConfig();
				config.setUrl(request.getParameter("url"));
				config.setPort(Integer.parseInt(request.getParameter("port")));
				config.setUserid(request.getParameter("userid"));
				config.setPassword(request.getParameter("password"));
				MailConfig.save(config);
				request.getRequestDispatcher("/admin.jsp").forward(request, response);
			}
			break;
		case "show_mail_data":
			{
				request.getRequestDispatcher("/admin.jsp").forward(request, response);
			}
			break;
		default:
			request.setAttribute("message", "Invalid case label in MailServlet");
			request.getRequestDispatcher("/error.jsp").forward(request, response);	
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
