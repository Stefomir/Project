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

@WebServlet("/BookRegDB")
public class BookRegDB extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public BookRegDB() {
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
		//opcii za kirilica
		response.setContentType("text/html");
		response.setContentType("text/html;charset=UTF-8");

		PrintWriter out = response.getWriter();

		out.println("<html>");
		out.println("<head><title>Library</title></head>");
		out.println("<body background='/Library/image/background.jpg'>");
		out.println("<center><h3><p>RegisteredBooks</p></h3></center>");
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
		//zarezdane na zaqvka
		try {
			stmt = (Statement) con.createStatement();

		} catch (SQLException e) {
			out.println("Something went wrong with the Statement");
		}
		try {

			out.println("<table style=width:100% border=5>");

			out.println("<tr>");
			out.println("<td><b>Function</b></td>");
			out.println("<td><b>BookTitle</b></td>");
			out.println("<td><b>BookAuthor</b></td>");
			out.println("<td><b>Stock</b></td>");
			out.println("<td><b>Taken</b></td>");
			out.println("</tr>");
			ResultSet rs = null;
			//zaqvka kam bazata
			rs = stmt.executeQuery("SELECT * FROM books order by Name,Author");
			//zapazvane na info ot bazat
			while (rs.next()) {
				int idBooks = rs.getInt("idBooks");
				String name = rs.getString("Name");
				String author = rs.getString("Author");
				int flow = rs.getInt("Flow");
				int credit = rs.getInt("Credit");

				out.println("<tr>");
				out.println("<td width=120><a href='/Library/DeleteBook?id1="
						+ idBooks + "'>Delete</a>&nbsp;&nbsp;");
				out.println("<a href=/Library//BookReg?id1=" + idBooks
						+ ">Edit</a>&nbsp;&nbsp;");
				out.println("<td>" + name + "</td>");
				out.println("<td>" + author + "</td>");
				out.println("<td>" + flow + "</td>");
				out.println("<td>" + credit + "</td>");
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

			con.close();
			out.close();

		} catch (SQLException e1) {
			out.println("There is a problem with the execution Query");
		}

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
