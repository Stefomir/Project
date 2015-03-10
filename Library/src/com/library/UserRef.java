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

@WebServlet("/UserRef")
public class UserRef extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserRef() {
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
		//opciq za kirilica
		response.setContentType("text/html");
		response.setContentType("text/html;charset=UTF-8");

		PrintWriter out = response.getWriter();

		out.println("<html>");
		out.println("<head><title>Library</title></head>");
		out.println("<body background='/Library/image/background.jpg'>");
		out.println("<center><h3><p>RegisteredUsers</p></h3></center>");
		out.println("<form method=get>");
		//zarezdane na draivera za vrazka s bazata
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

			out.println("<table style=width:100% border=5>");

			out.println("<tr>");
			out.println("<td><b>Function</b></td>");
			out.println("<td><b>UserName</b></td>");
			out.println("<td><b>UserFKnumber</b></td>");
			out.println("</tr>");
			ResultSet rs = null;
			//zaqvka kam bazata
			rs = stmt.executeQuery("SELECT * FROM persons order by name");
			//zapazvane na info ot bazata
			while (rs.next()) {
				int idPersons = rs.getInt("idpersons");
				String name = rs.getString("name");
				String fknumber = rs.getString("fknumber");

				out.println("<tr>");
				out.println("<td width=140><a href='/Library/DeleteUser?id11="
						+ idPersons + "'>Delete</a>&nbsp;");
				out.println("<a href=/Library//UserReg?id11=" + idPersons
						+ ">Edit</a>&nbsp;");
				out.println("<a href=/Library//UserHistory?id11=" + idPersons
						+ "&usrname=" + name + "&usrfk=" + fknumber
						+ ">History</a>");

				out.println("<td>" + name + "</td>");
				out.println("<td>" + fknumber + "</td>");
				out.println("</tr>");
			}

			out.println("</table>");
			out.println("<br>");
			out.println("</form>");
			out.println("<form action=/Library/MainForm method=get>"
					+ "<button type=submit style='width:10%; height:29px'>Back</button></form>");
			out.println("<form action=/Library/Login method=get>"
					+ "<button type=submit style='width:10%; height:29px'>Exit</button></form>");
			out.println("</body>");
			out.println("</html>");

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
