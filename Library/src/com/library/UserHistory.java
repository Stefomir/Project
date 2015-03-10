package com.library;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

@WebServlet("/UserHistory")
public class UserHistory extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserHistory() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//sesii
		HttpSession session = request.getSession(true);
		if (session.isNew()) {
			response.sendRedirect("/Library/Login");
			return;
		}

		String userID = new String("Login");
		userID = (String) session.getAttribute(userID);
		if (userID == null) {
			response.sendRedirect("/Library/Login");
			return;
		}

		if (!userID.equals("1")) {
			response.sendRedirect("/Library/Login");
			return;
		}
		//opcia za kirilica
		response.setContentType("text/html");
		response.setContentType("text/html;charset=UTF-8");

		PrintWriter out = response.getWriter();

		out.println("<html>");
		out.println("<head><title>Library</title></head>");
		out.println("<body background='/Library/image/background.jpg'>");
		out.println("<center><h3><p>RegisteredUsers</p></h3></center>");
		//zarezdane na draiver za vrazka s bazata
		try {
			Class.forName("com.mysql.jdbc.Driver");

		} catch (ClassNotFoundException e) {
			out.println("Problem with the connection");
		}

		String url = "jdbc:mysql://localhost/library";
		String user = "stefko";
		String password = "stefko";

		Connection con = null;
		java.sql.Statement stmt = null;
		//vrazka s bazata
		try {
			con = (Connection) DriverManager.getConnection(url, user, password);

		} catch (SQLException e) {
			out.println("Problem with the connection driver");
		}
		//sazdavane na zaqvka
		try {
			stmt = (Statement) con.createStatement();

		} catch (SQLException e) {
			out.println("Something went wrong with the Statement");
		}
		try {
			//izvlichane na parametri ot html
			String idpersons = request.getParameter("id11");
			String usrname = request.getParameter("usrname");
			String usrfk = request.getParameter("usrfk");

			out.println("<center><b>UserName: " + usrname + " UserFkNumber: "
					+ usrfk + "</b></center>");
			out.println("<table style=width:100% border=5>");

			out.println("<tr>");
			out.println("<td><b>BookTitle</b></td>");
			out.println("<td><b>BookAuthor</b></td>");
			out.println("<td><b>DateTaken</b></td>");
			out.println("<td><b>DateReturned</b></td>");
			out.println("</tr>");

			ResultSet rs = null;
			//zaqvka kam bazat
			rs = stmt
					.executeQuery("SELECT db0.idpersons,db0.idbpref,db0.datetaken,db0.datereturned,db1.Name,db1.Author FROM bpref as db0, books as db1"
							+ " where db0.idBooks=db1.idBooks and db0.idpersons ="
							+ idpersons);
			//zapazvane na info ot bazata
			while (rs.next()) {
				String name = rs.getString("Name");
				String author = rs.getString("Author");
				String datetaken = rs.getString("datetaken");
				String datereturned = rs.getString("datereturned");
				//proverka dali knigata e varnata
				if (datereturned == null)
					datereturned = "Not yet returned";

				out.println("<tr>");

				out.println("<td>" + name + "</td>");
				out.println("<td>" + author + "</td>");
				out.println("<td>" + datetaken + "</td>");
				out.println("<td>" + datereturned + "</td>");
				out.println("</tr>");
			}

			out.println("</table>");
			out.println("<br>");

			out.println("<form action=/Library/MainForm method=get>"
					+ "<button type=submit style='width:10%; height:29px'>Back</button></form>");
			out.println("<form action=/Library/Login method=get>"
					+ "<button type=submit style='width:10%; height:29px'>Exit</button></form>");
			out.println("</body>");
			out.println("</html>");

			rs.next();
			stmt.close();
			con.close();
			out.close();
		} catch (SQLException e1) {
			out.println("Syntaxis error with MySql");
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

}
