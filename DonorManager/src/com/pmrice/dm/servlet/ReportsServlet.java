package com.pmrice.dm.servlet;

import java.util.List;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pmrice.dm.util.Currency;
import com.pmrice.dm.util.Util;
import com.pmrice.dm.model.Donor;

/**
 * Servlet implementation class ReportsServlet. Handles all reporting.
 */
public class ReportsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String DONATION_TABLE_STYLE = "<style>table, th, td {border: 1px solid black;border-collapse: collapse;}"
			+ " th, td {padding: 5px;} table {border-spacing: 15px;}</style>";
	private static final String DONATION_TABLE_HEADER = "<tr><th>Donor Name</th><th>Date</th><th>Amount</th><th>Description</th><tr>";
	private static final String DONOR_TABLE_STYLE = "<style></style>";
	private static final String DONOR_TABLE_HEADER = "<tr><th>Last Name</th><th>Name</th><th>Address</th><th>Address</th>" +
		"<th>City</th><th>State</th><th>ZIP</th><th>Country</th><th>Telephone</th><th>Email</th><th>Notes</th><tr>";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		switch (action) {
		case "day": // report on donations for one date
			{	
			String date = request.getParameter("date");
			String text = createDateReport(date);
			response.getWriter().println(text);
			}
			break;
		case "month": // report on donations in a month
			{
			String year = request.getParameter("year");
			String month = request.getParameter("month");
			String text = createMonthReport(month, year);
			response.getWriter().println(text);
			}
			break;
		case "year": // report on donations in a year
			{
			String year = request.getParameter("year");
			String text = createYearReport(year);
			response.getWriter().println(text);
			}
			break;
		case "donor": // donations for one donor in a given year
			{
			String year = request.getParameter("year");
			String donor = request.getParameter("donor");
			String text = createDonorYearReport(donor, year);
			response.getWriter().println(text);
			}
			break;
		case "donors":
			response.getWriter().println(getActiveDonors());
			break;
		default:
			response.getWriter().append("Missing case: '" + action + "'");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private String createDateReport(String date) {
		String sql = "SELECT * FROM donor, donation WHERE donation.donor_id = donor.id AND donation.date = '" + Util.displayToStorage(date) + "';";
		try {
			ResultSet rs = Util.getConnection().createStatement().executeQuery(sql);
			StringBuilder sb = new StringBuilder();
			sb.append("<html><head><title>Donation Report</title></head>");
			sb.append(DONATION_TABLE_STYLE);
			sb.append("<body>");
			sb.append("<h2>Donations on " + date + "</h2>");
			sb.append("<table>").append(DONATION_TABLE_HEADER);
			while (rs.next()) {
				sb.append("<tr><td>").append(rs.getString("donor.name")).append("</td><td>").append(Util.storageToDisplay(rs.getString("donation.date"))).append("</td><td>")
				.append(Currency.getDisplay(rs.getString("donation.amount"),true)).append("</td><td>").append(rs.getString("donation.description")).append("</td></tr>");
			}
			sb.append("</table>").append("<br>Printed on " + Util.today()).append("</body></html>");
			return sb.toString();
		} catch (SQLException e) {
			return e.toString();
		}
	}

	private String createMonthReport(String month, String year) { // month is 01, 02, etc.
		String monthBegin = year + "-" + month + "-01";
		String monthEnd = year + "-" + month + "-31";
		String sql = "SELECT * FROM donor, donation WHERE donation.donor_id = donor.id AND donation.date BETWEEN '" + monthBegin + "' AND '" + monthEnd + "';";
		try {
			ResultSet rs = Util.getConnection().createStatement().executeQuery(sql);
			StringBuilder sb = new StringBuilder();
			sb.append("<html><head><title>Donation Report</title></head>");
			sb.append(DONATION_TABLE_STYLE);
			sb.append("<body>");
			sb.append("<h2>Donations in " + Util.getMonthName(month) + "</h2>");
			sb.append("<table>").append(DONATION_TABLE_HEADER);
			while (rs.next()) {
				sb.append("<tr><td>").append(rs.getString("donor.name")).append("</td><td>").append(Util.storageToDisplay(rs.getString("donation.date"))).append("</td><td>")
				.append(Currency.getDisplay(rs.getString("donation.amount"),true)).append("</td><td>").append(rs.getString("donation.description")).append("</td></tr>");
			}
			sb.append("</table>").append("<br>Printed on " + Util.today()).append("</body></html>");
			return sb.toString();
		} catch (SQLException e) {
			return e.toString();
		}
	}

	private String createYearReport(String year) {
		String yearBegin = year + "-01-01";
		String yearEnd = year + "-12-31";
		String sql = "SELECT * FROM donor, donation WHERE donation.donor_id = donor.id AND donation.date >= '" + yearBegin + 
			"' AND donation.date <= '" + yearEnd + "';";
		try {
			ResultSet rs = Util.getConnection().createStatement().executeQuery(sql);
			StringBuilder sb = new StringBuilder();
			sb.append("<html><head><title>Donation Report</title></head>");
			sb.append(DONATION_TABLE_STYLE);
			sb.append("<body>");
			sb.append("<h2>Donations in the year " + year + "</h2>");
			sb.append("<table>").append(DONATION_TABLE_HEADER);
			while (rs.next()) {
				sb.append("<tr><td>").append(rs.getString("donor.name")).append("</td><td>").append(Util.storageToDisplay(rs.getString("donation.date"))).append("</td><td>")
				.append(Currency.getDisplay(rs.getString("donation.amount"),true)).append("</td><td>").append(rs.getString("donation.description")).append("</td></tr>");
			}
			sb.append("</table>").append("<br>Printed on " + Util.today()).append("</body></html>");
			return sb.toString();
		} catch (SQLException e) {
			return e.toString();
		}
	}

	private String createDonorYearReport(String donor, String year) {
		String yearBegin = year + "-01-01";
		String yearEnd = year + "-12-31";
		String donorId = donor.substring(0, donor.indexOf(':'));
		String donorName = donor.substring(donor.indexOf(':') + 1).trim();
		String sql = "SELECT * FROM donor, donation WHERE donor_id = " + donorId + " AND donation.donor_id = donor.id AND donation.date BETWEEN '" + 
				yearBegin + "' AND '" + yearEnd + "';";
		try {
			ResultSet rs = Util.getConnection().createStatement().executeQuery(sql);
			StringBuilder sb = new StringBuilder();
			sb.append("<html><head><title>Donation Report</title></head>");
			sb.append(DONATION_TABLE_STYLE);
			sb.append("<body>");
			sb.append("<h2>Donations in the year " + year + " by " + donorName + "</h2>");
			sb.append("<table>").append(DONATION_TABLE_HEADER);
			while (rs.next()) {
				sb.append("<tr><td>").append(rs.getString("donor.name")).append("</td><td>").append(Util.storageToDisplay(rs.getString("donation.date"))).append("</td><td>")
				.append(Currency.getDisplay(rs.getString("donation.amount"),true)).append("</td><td>").append(rs.getString("donation.description")).append("</td></tr>");
			}
			sb.append("</table>").append("<br>Printed on " + Util.today()).append("</body></html>");
			return sb.toString();
		} catch (SQLException e) {
			return e.toString();
		}
	}
	
	private String getActiveDonors(){
		List<Donor> list = Donor.getDonors();
		StringBuilder sb = new StringBuilder();
		sb.append("<html><head><title>Active Donors List</title></head>");
		sb.append(DONOR_TABLE_STYLE);
		sb.append("<body>");
		sb.append("<table>").append(DONOR_TABLE_HEADER);
		for (Donor donor : list) {
			if (donor.getId() == 0) continue;
			sb.append("<tr><td>").append(donor.getLastname()).append("</td>")
			.append("<td>").append(donor.getName()).append("</td>")
			.append("<td>").append(donor.getAddress1()).append("</td>")
			.append("<td>").append(donor.getAddress2()).append("</td>")
			.append("<td>").append(donor.getCity()).append("</td>")
			.append("<td>").append(donor.getState()).append("</td>")
			.append("<td>").append(donor.getZip()).append("</td>")
			.append("<td>").append(donor.getCountry()).append("</td>")
			.append("<td>").append(donor.getTelephone()).append("</td>")
			.append("<td>").append(donor.getEmail()).append("</td>")
			.append("<td>").append(donor.getNotes()).append("</td>")
			.append("</tr>");
		}
		sb.append("</table>").append("<br>Printed on " + Util.today()).append("</body></html>");
		return sb.toString();
	}

}
