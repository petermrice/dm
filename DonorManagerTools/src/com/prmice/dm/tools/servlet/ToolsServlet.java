package com.prmice.dm.tools.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.prmice.dm.tools.DBCreator;

@WebServlet(name = "DMTools", urlPatterns = {"/tools"})
public class ToolsServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		String orgid = req.getParameter("orgid");
		if (action != null && action.equals("addOrg")) {
			DBCreator.buildDb(orgid);
		}
		req.getRequestDispatcher("/index.jsp").forward(req, resp);
		
	}
	
}
