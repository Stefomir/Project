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
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

@WebServlet("/BookReturn")
public class BookReturn extends HttpServlet {
	private static final long serialVersionUID = 1L;

	String str_idbpref;
	String str_id_books;
	String str_id_persons, str_user_fknumber, str_name_student;

	String str_book_title;
	String str_book_author;
	String str_datetaken;
	//metod za zapazvane na info ot varnatata kniga
	protected void SaveReturnBook(HttpServletResponse response)
			throws ServletException, IOException {
		//zarezdane na draivera za vrazka s bazata
		try {
			Class.forName("com.mysql.jdbc.Driver");

		} catch (ClassNotFoundException e) {
		}

		String url = "jdbc:mysql://localhost/library";
		String user = "stefko";
		String password = "stefko";
		//opcia za kirilica
		response.setContentType("text/html;charset=UTF-8");
		
		Connection con = null;
		java.sql.Statement stmt = null;
		//vrazka s bazata
		try {
			con = (Connection) DriverManager.getConnection(url, user, password);

		} catch (SQLException e) {
		}
		//zarezdane na zaqvka
		try {
			stmt = (Statement) con.createStatement();

		} catch (SQLException e) {
		}
		PreparedStatement statement;
		try {
			//zaqvka kam bazata
			statement = (PreparedStatement) con
					.prepareStatement("Update books set Credit=(Credit-1),Flow=(Flow+1) Where idBooks=(Select idBooks From  bpref Where idbpref=?)");
			statement.setString(1, str_idbpref);
			statement.executeUpdate();
			statement.close();
			//zaqvka kam bazata
			statement = (PreparedStatement) con
					.prepareStatement("Update bpref Set datereturned=CURDATE(),istaken='N' Where idbpref=?");
			statement.setString(1, str_idbpref);
			statement.executeUpdate();
			stmt.close();
			statement.close();
			con.close();
			//prepratka kam metod
			State2(response);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	//metod za generirane na stranicata za vrastane na kniga
	protected void State1(HttpServletResponse response, String MsgError)
			throws ServletException, IOException {

		if (str_user_fknumber == null)
			str_user_fknumber = "";
		if (str_name_student == null)
			str_name_student = "";
		PrintWriter out = response.getWriter();
		//opcia za kirilica
		response.setContentType("text/html");
		response.setContentType("text/html;charset=UTF-8");
		
		out.println("<html>");
		out.println("<head><title>Library</title></head>");
		out.println("<body background='/Library/image/background.jpg'>");
		out.println("<center>");
		out.println("<form action=/Library/BookReturn method=get>");
		//uslovie za izkarvane na error message
		if (!(MsgError.equals(""))) {
			out.println("<br><strong><font color='red'>" + MsgError
					+ "</strong></font><br>");
		}
		out.println("<table cols=1>");
		out.println("<tr>");

		out.println("<td ALIGN=CENTER BGCOLOR=#DCDCDC><strong>Book Return</strong></td>");
		out.println("</tr>");
		out.println("<tr><td><table cols=4 width=100%></td></tr>");

		out.println("<tr>");
		out.println("<td ALIGN=left>User FkNumber:</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left><input type=text name='user_fknumber' value='"
				+ str_user_fknumber + "'></td>");
		out.println("</tr>");
		out.println("<tr><td><br></td></tr>");
		out.println("<tr><td style='width: 33%; padding-left:20px' ALIGN=left>"
				+ "<button type=submit style='width:80%; height:29px'>Next</button></form></td></tr>");
		out.println("<tr><td style='width: 33%; padding-left:20px' ALIGN=left><form action=/Library/MainForm method=get>"
				+ "<button type=submit style='width:80%; height:29px'>Back</button></form></td></tr>");
		out.println("<tr><td style='width: 33%; padding-left:20px' ALIGN=left><form action=/Library/Login method=get>"
				+ "<button type=submit style='width:80%; height:29px'>Exit</button></form></td></tr>");
		out.println("</table>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");

		out.close();
	}
	//metod za validirane na info ot state1 i pokazvane na spisaka s vzeti knigi ot tozi user
	protected void State2(HttpServletResponse response)
			throws ServletException, IOException {
		//zarezdane na draivera za vrazka s bazata
		try {
			Class.forName("com.mysql.jdbc.Driver");

		} catch (ClassNotFoundException e) {
		}

		String url = "jdbc:mysql://localhost/library";
		String user = "stefko";
		String password = "stefko";
		//opcia za kirilica
		response.setContentType("text/html;charset=UTF-8");
		
		Connection con = null;
		java.sql.Statement stmt = null;
		//vrazka s bazata
		try {
			con = (Connection) DriverManager.getConnection(url, user, password);

		} catch (SQLException e) {
		}
		//zarezdane na zaqvka
		try {
			stmt = (Statement) con.createStatement();

		} catch (SQLException e) {
		}
		try {

			PreparedStatement statement;
			//zaqvka kam bazata
			statement = (PreparedStatement) con
					.prepareStatement("SELECT idpersons,name FROM persons where fknumber=?");
			statement.setString(1, str_user_fknumber);
			ResultSet rs = statement.executeQuery();

			str_id_persons = null;
			str_name_student = null;
			//zapazvane na info ot bazata
			while (rs.next()) {

				str_id_persons = rs.getString("idpersons");
				str_name_student = rs.getString("name");

				break;
			}
			rs.close();
			statement.close();
			//proverka dali takav klient sastestvuva
			if (str_id_persons == null) {
				con.close();
				State1(response, "Missing client with such info, please try again!");
				return;
			}
			//zaqvka kam bazata
			statement = (PreparedStatement) con
					.prepareStatement("SELECT db0.idbpref,db0.datetaken,db1.Name,db1.Author FROM bpref db0, books db1 "
							+ "where db0.idBooks=db1.idBooks and db0.datereturned is  null and db0.idpersons =? order by db0.datetaken");
			statement.setString(1, str_id_persons);
			rs = statement.executeQuery();

			PrintWriter out = response.getWriter();
			//opcia za kirilica
			response.setContentType("text/html");
			response.setContentType("text/html;charset=UTF-8");
			
			out.println("<html>");
			out.println("<head><title>Library</title></head>");
			out.println("<body background='/Library/image/background.jpg'>");
			out.println("<center>");
			out.println("FkNumber: <strong>" + str_user_fknumber + "</strong> Name: <strong>"
					+ str_name_student + "</strong>");
			out.println("<br>");
			out.println("<table style=width:100% border=5>");

			out.println("<tr>");
			out.println("<td><b>Function</b></td>");
			out.println("<td><b>BookTitle</b></td>");
			out.println("<td><b>BookAuthor</b></td>");
			out.println("<td><b>DateTaken</b></td>");
			out.println("</tr>");
			//zapazvane na info ot bazata i konstruirane na html tablica
			while (rs.next()) {
				str_book_title = rs.getString("Name");
				str_book_author = rs.getString("Author");
				str_datetaken = rs.getString("datetaken");

				out.println("<tr>");
				out.println("<td width=120><a href='/Library/BookReturn?id_bpref="
						+ rs.getString("idbpref")
						+ "&user_fknumber="
						+ str_user_fknumber + "'>ReturnBook</a>&nbsp;&nbsp;");

				out.println("<td>" + str_book_title + "</td>");
				out.println("<td>" + str_book_author + "</td>");
				out.println("<td>" + str_datetaken + "</td>");
				out.println("</tr>");
			}

			out.println("</table>");
			out.println("<br>");
			out.println("</form>");
			out.println("<form action=/Library/MainForm method=get>"
					+ "<button type=submit style='width:10%; height:29px'>Back</button></form>");
			out.println("<form action=/Library/Login method=get"
					+ "><button type=submit style='width:10%; height:29px'>Exit</button></form>");
			out.println("</body>");
			out.println("</html>");

			out.close();
			rs.close();
			stmt.close();
			con.close();

		} catch (SQLException e) {
		}

	}

	public BookReturn() {
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
		response.setContentType("text/html;charset=UTF-8");
		//izvlichane na parametri ot html
		str_idbpref = request.getParameter("id_bpref");
		str_id_persons = request.getParameter("id_persons");
		str_id_books = request.getParameter("id_books");
		str_user_fknumber = request.getParameter("user_fknumber");
		str_book_title = request.getParameter("book_title");
		str_book_author = request.getParameter("book_author");
		str_name_student = request.getParameter("name_student");
		//uslovie za executvane na metodite
		if (str_idbpref != null) {
			SaveReturnBook(response);
			return;
		} else if (str_id_persons == null) {
			if (str_user_fknumber == null) {
				State1(response, "");
			} else {
				State2(response);
			}
		} else
			response.sendRedirect("/Library/BookReturn");

		return;

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

}
