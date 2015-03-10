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

@WebServlet("/BookReg")
public class BookReg extends HttpServlet {
	private static final long serialVersionUID = 1L;

	String str_id_books;
	String str_book_title;
	String str_book_author;
	String str_book_flow;

	//metod za generirane na stranicata za reg na knigi
	protected void stage1(HttpServletRequest request,
			HttpServletResponse response, String Msg) throws ServletException,
			IOException {
		//opcia za kirilica
		response.setContentType("text/html");
		response.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out = response.getWriter();

		String select = request.getParameter("id1");

		out.println("<html>");
		out.println("<head>");
		out.println("<title>Library</title>");

		out.println("</head>");
		out.println("<body background='/Library/image/background.jpg'>");
		out.println("<center>");

		String strBook_title = null, strBook_author = null;
		int strbook_flow = 0;

		if (select == null) {
			strBook_title = "";
			strBook_author = "";
			strbook_flow = 0;
			out.println("<form action=/Library/BookReg method=get>");
		} else {
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
				con = (Connection) DriverManager.getConnection(url, user,
						password);

			} catch (SQLException e) {
				out.println("Problem with the connection driver");

			}
			//zarezdane na zaqvka kam bazata
			try {
				stmt = (Statement) con.createStatement();

			} catch (SQLException e) {
				out.println("Something went wrong with the Statement");
			}

			ResultSet rs = null;
			try {
				//zaqvka kam bazata
				rs = stmt.executeQuery("SELECT * FROM books Where idBooks="
						+ select);

				strBook_title = "";
				strBook_author = "";
				strbook_flow = 0;
				//zapisvane na info ot bazata
				while (rs.next()) {
					strBook_title = rs.getString("Name");
					strBook_author = rs.getString("Author");
					strbook_flow = rs.getInt("Flow");
					break;
				}
				rs.close();
				stmt.close();
				con.close();
			} catch (SQLException e) {
				out.println("There is a problem with the execution Query");
			}

			out.println("<form action=/Library/BookReg method=get>");
			out.println("<input type='hidden' name='id1' value='" + select
					+ "'>");
		}
		//uslovie za izkarvane na error message
		if (!(Msg.equals(""))) {
			out.println("<br><strong><font color='red'>" + Msg
					+ "</strong></font><br>");
		}
		out.println("<table cols=1>");
		out.println("<tr>");
		out.println("<td ALIGN=CENTER BGCOLOR=#DCDCDC><strong>Register</strong></td>");
		out.println("</tr>");
		out.println("<tr><td><table cols=4 width=90%></td></tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left>*Book Title:</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left><input type=text name='book_title' value='"
				+ strBook_title + "'></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left>*Author:</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left><input type=text name='book_author' value='"
				+ strBook_author + "'></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left>*Stock:</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td LIGN=left><input type=text name='book_flow' value='"
				+ strbook_flow + "'></td>");
		out.println("</tr>");
		out.println("<tr><td><br></td></tr>");
		out.println("<tr><td style='width: 33%; padding-left:20px' ALIGN=left><button type=submit style='width:80%; height:29px'>Save</button></form></td></tr>");
		out.println("<tr><td style='width: 33%; padding-left:20px' ALIGN=left><form action=/Library/MainForm method=get><button type=submit style='width:80%; height:29px'>Back</button></form></td></tr>");
		out.println("<tr><td style='width: 33%; padding-left:20px' ALIGN=left><form action=/Library/Login method=get><button type=submit style='width:80%; height:29px' >Exit</button></form></td></tr>");
		out.println("</table>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");

		out.close();

	}
	//metod za redaktirane na zapisite na knigite
	protected void editBook(HttpServletRequest request,
			HttpServletResponse response, String Msg) throws ServletException,
			IOException {
		//opcia za kirilica
		response.setContentType("text/html");
		response.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out = response.getWriter();

		String select = request.getParameter("id1");

		out.println("<html>");
		out.println("<head>");
		out.println("<title>Library</title>");

		out.println("</head>");
		out.println("<body background='/Library/image/background.jpg'>");
		out.println("<center>");
		String strBook_title = null, strBook_author = null;
		int strbook_flow = 0;
		if (select == null) {
			strBook_title = "";
			strBook_author = "";
			strbook_flow = 0;

			out.println("<form action=/Library/BookSave method=get>");
		} else {
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
			//Vrazka s bazata
			try {
				con = (Connection) DriverManager.getConnection(url, user,
						password);

			} catch (SQLException e) {
				out.println("Problem with the connection driver");

			}
			//zarezdane na zaqvka
			try {
				stmt = (Statement) con.createStatement();

			} catch (SQLException e) {
				out.println("Something went wrong with the Statement");
			}

			ResultSet rs = null;
			try {
				//zaqvka kam bazata
				rs = stmt.executeQuery("SELECT * FROM books Where idBooks="
						+ select);

				strBook_title = "";
				strBook_author = "";
				strbook_flow = 0;
				//zapisvane na info ot bazata
				while (rs.next()) {
					strBook_title = rs.getString("Name");
					strBook_author = rs.getString("Author");
					strbook_flow = rs.getInt("Flow");
					break;
				}
				rs.close();
				stmt.close();
				con.close();
			} catch (SQLException e) {
				out.println("There is a problem with the execution Query");
			}

			out.println("<form action=/Library/BookReg method=get>");
			out.println("<input type='hidden' name='id1' value='" + select
					+ "'>");
		}
		//uslovie za izkarvane na error message
		if (!(Msg.equals(""))) {
			out.println("<br><strong><font color='red'>" + Msg
					+ "</strong></font><br>");
		}
		out.println("<table cols=1>");
		out.println("<tr>");
		out.println("<td ALIGN=CENTER BGCOLOR=#DCDCDC><strong>Update</strong></td>");
		out.println("</tr>");
		out.println("<tr><td><table cols=4 width=90%></td></tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left>*Book Title:</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left><input type=text name='book_title' value='"
				+ strBook_title + "'></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left>*Author:</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left><input type=text name='book_author' value='"
				+ strBook_author + "'></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left>*Stock:</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td LIGN=left><input type=text name='book_flow' value='"
				+ strbook_flow + "'></td>");
		out.println("</tr>");
		out.println("<tr><td><br></td></tr>");
		out.println("<tr><td style='width: 33%; padding-left:20px' ALIGN=left>"
				+ "<button type=submit style='width:80%; height:29px'>Save</button></form></td></tr>");
		out.println("<tr><td style='width: 33%; padding-left:20px' ALIGN=left><form action=/Library/MainForm method=get>"
				+ "<button type=submit style='width:80%; height:29px'>Back</button></form></td></tr>");
		out.println("<tr><td style='width: 33%; padding-left:20px' ALIGN=left><form action=/Library/Login method=get>"
				+ "<button type=submit style='width:80%; height:29px' >Exit</button></form></td></tr>");
		out.println("</table>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");

		out.close();
	}
	//metod za zapazvane na informaciata,vavedena ot usera, v bazata
	protected void bookSave(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String url = "jdbc:mysql://localhost/library";
		String user = "stefko";
		String password = "stefko";
		//opcia za kirilica
		response.setContentType("text/html;charset=UTF-8");
		
		java.sql.Connection con = null;
		//izvlichane na parametri ot html
		String select = request.getParameter("id1");
		String bookName = request.getParameter("book_title");
		String bookAuthor = request.getParameter("book_author");
		String bookFlow = request.getParameter("book_flow");
		String bookCredit = request.getParameter("book_credit");
		//proverka dali e vavedeno nesto razlichno ot int
		try {
			Integer.parseInt(bookFlow);
		} catch (Exception e) {
			stage1(request, response, "You must type a holl number for Stock!");
			return;
		}
		//proverka dali e vavedeno chislo,vmesto string
		try {
			Integer.parseInt(bookName);
			Integer.parseInt(bookAuthor);
			stage1(request, response,
					"You can't type numbers in Title or Author!");
			return;

		} catch (Exception e) {
		}
		//proverka dali usera e popalnil poletata
		if ((bookName.equals("")) || bookAuthor.equals("")) {
			stage1(request, response, "Please, fill the lines with *");
			return;
		}
		//zarezdane na draivera za vrazka s bazata
		try {
			Class.forName("com.mysql.jdbc.Driver");

		} catch (ClassNotFoundException e) {
		}
		//vrazka s bazata
		try {
			con = DriverManager.getConnection(url, user, password);

		} catch (SQLException e) {
		
		}

		try {
			java.sql.PreparedStatement ps;

			String checkBT = null;
			PreparedStatement statement;

			if (select == null)
				//zaqvka kam bazata
				statement = (PreparedStatement) con
						.prepareStatement("SELECT * FROM books where Upper(Name)=Upper(?) and Upper(Author)=Upper(?)");
			else
				statement = (PreparedStatement) con
						.prepareStatement("SELECT * FROM books where Upper(Name)=Upper(?) and Upper(Author)=Upper(?) and idBooks<>"
								+ select);
			statement.setString(1, bookName);
			statement.setString(2, bookAuthor);
			ResultSet rs = statement.executeQuery();
			//zapazvane na info ot bazata
			while (rs.next()) {
				checkBT = rs.getString("Name");

				break;
			}
			statement.close();
			//proverka dali takava kniga veche ima registrirana
			if (checkBT != null) {
				stage1(request, response,
						"There is another book with the same Title and Author!");
				return;
			}
			//ako usera ne popalni poleto za kolichesto,to avtomatichno se setva na 1
			if (bookFlow.equals("0"))
				bookFlow = "1";

			if (select == null)
				//zaqvka kam bazata
				ps = con.prepareStatement("INSERT INTO books (Name,Author,Flow,Credit) values(?,?,?,?)");

			else
				ps = con.prepareStatement("Update books Set Name=?,Author=?,Flow=?,Credit=? Where idBooks="
						+ select);

			ps.setString(1, bookName);
			ps.setString(2, bookAuthor);
			ps.setString(3, bookFlow);
			ps.setString(4, bookCredit);
			ps.executeUpdate();

			ps.close();
			con.close();

		} catch (SQLException e1) {
			
		}
	}

	public BookReg() {
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
		str_id_books = request.getParameter("id1");
		str_book_title = request.getParameter("book_title");
		str_book_author = request.getParameter("book_author");
		str_book_flow = request.getParameter("book_flow");
		//usloviq za executvane na metodite
		if (str_id_books != null && str_book_title == null
				&& str_book_author == null) {
			editBook(request, response, "");
		} else {
			if (str_book_title == null && str_book_author == null) {
				stage1(request, response, "");

			} else {
				bookSave(request, response);
				response.sendRedirect("/Library/BookRegDB");
			}
		}

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

}
