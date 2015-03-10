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

@WebServlet("/BookTake")
public class BookTake extends HttpServlet {
	private static final long serialVersionUID = 1L;

	String str_idbpref, str_name_student;
	String str_id_books;
	String str_id_persons;
	String str_user_fknumber;
	String str_book_title;
	String str_book_author;

	// metod za redaktirane na zapisi ot tablicata
	protected void EditRecordbpref(int idbpref, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		//opcia za kirilica
		response.setContentType("text/html");
		response.setContentType("text/html;charset=UTF-8");

		out.println("<html>");
		out.println("<head><title>Library</title></head>");
		out.println("<body background='/Library/image/background.jpg'>");
		out.println("<center>");
		//Zarezdane na drivera za vrazka za bazata
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
			con = (Connection) DriverManager.getConnection(url, user, password);

		} catch (SQLException e) {
			out.println("Problem with the connection driver");
		}

		try {
			stmt = (Statement) con.createStatement();

		} catch (SQLException e) {
			out.println("Something went wrong with the Statement");
		}
		ResultSet rs = null;

		try {
			//Zaqvka kam bazata
			rs = stmt
					.executeQuery("SELECT db0.*,db1.*,db2.* FROM bpref as db0, books as db1 , persons as db2 "
							+ "where db0.idBooks= db1.idBooks and db0.idpersons= db2.idpersons and db0.idbpref="
							+ idbpref);

			str_user_fknumber = "";
			str_book_title = "";
			str_book_author = "";
			str_id_books = "";
			str_id_persons = "";
			//Izvlichane na info ot tablicata
			while (rs.next()) {
				str_user_fknumber = rs.getString("fknumber");
				str_book_title = rs.getString("Name");
				str_book_author = rs.getString("Author");
				str_id_books = rs.getString("idBooks");
				str_id_persons = rs.getString("idpersons");
				break;
			}

			rs.close();
			stmt.close();
			con.close();

		} catch (SQLException e) {
			out.println("There is a problem with the ExecuteQuery");
		}
		
		out.println("<form action=/Library/BookTake method=get>");
		out.println("<input type='hidden' name='id_books' value='"
				+ str_id_books + "'>");
		out.println("<input type='hidden' name='id_persons' value='"
				+ str_id_persons + "'>");
		out.println("<input type='hidden' name='id_bpref' value='"
				+ str_idbpref + "'>");

		out.println("<table cols=1>");
		out.println("<tr>");

		out.println("<td ALIGN=CENTER BGCOLOR=#DCDCDC><strong>Edit Book Take</strong></td>");
		out.println("</tr>");
		out.println("<tr><td><table cols=4 width=90%></td></tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left>User FkNumber:</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left><input type=text name='user_fknumber' value='"
				+ str_user_fknumber + "'></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left>Book Title:</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left><input type=text name='book_title' value='"
				+ str_book_title + "'></td>");
		out.println("</tr>");

		out.println("<tr>");
		out.println("<td ALIGN=left>Book Author:</td>");
		out.println("</tr>");

		out.println("<tr>");
		out.println("<td ALIGN=left><input type=text name='book_author' value='"
				+ str_book_author + "'></td>");
		out.println("</tr>");
		out.println("<tr><td><br></td></tr>");
		out.println("<tr><td style='width: 33%; padding-left:20px' ALIGN=left>"
				+ "<button type=submit style='width:80%; height:29px'>Submit</button></form></td></tr>");
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
	//Metod za generirane na stranicata za vzimane na knigi
	protected void State1(HttpServletResponse response,HttpServletRequest request, String MsgError)
			throws ServletException, IOException {

		if (str_user_fknumber == null)
			str_user_fknumber = "";
		if (str_name_student == null)
			str_name_student = "";
		PrintWriter out = response.getWriter();
		//opcia za kirilica
		response.setContentType("text/html");
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		out.println("<html>");
		out.println("<head><title>Library</title></head>");
		out.println("<body background='/Library/image/background.jpg'>");
		out.println("<center>");
		out.println("<form action=/Library/BookTake method=get>");
		//uslovie za izkarvane na error message
		if (!(MsgError.equals(""))) {
			out.println("<br><strong><font color='red'>" + MsgError
					+ "</strong></font><br>");
		}
		out.println("<table cols=1>");
		out.println("<tr>");

		out.println("<td ALIGN=CENTER BGCOLOR=#DCDCDC><strong>Book Take</strong></td>");
		out.println("</tr>");
		out.println("<tr><td><table cols=4 width=90%></td></tr>");

		out.println("<tr>");
		out.println("<td ALIGN=left>User Name:</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left><input type=text name='user_name' value='"
				+ str_name_student + "'></td>");
		out.println("</tr>");

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
	//metod za validirane na usera,koito ste vzima kniga i generirane na stranicata za izbor na kniga
	protected void State2(String str_user_fknumber, String str_name_student,
			HttpServletResponse response, HttpServletRequest request, String Msg)
			throws ServletException, IOException {

		//Zarezdane na drivera za vrazka za bazata
		try {
			Class.forName("com.mysql.jdbc.Driver");

		} catch (ClassNotFoundException e) {
		}

		String url = "jdbc:mysql://localhost/library";
		String user = "stefko";
		String password = "stefko";
		//opcia za kirilica
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		Connection con = null;
		java.sql.Statement stmt = null;
		//Vrazka s bazata
		try {
			con = (Connection) DriverManager.getConnection(url, user, password);

		} catch (SQLException e) {
		}
		//Sazdavane na zaqvka
		try {
			stmt = (Statement) con.createStatement();

		} catch (SQLException e) {
		}

		try {
			
			PreparedStatement statement;
			//Zaqvka kam bazata
			statement = (PreparedStatement) con
					.prepareStatement("SELECT * FROM persons where fknumber=?");
			statement.setString(1, str_user_fknumber);
			ResultSet rs = statement.executeQuery();

			str_id_persons = "";
			//zapisvane na info ot bazata v promenlivi
			while (rs.next()) {
				str_id_persons = rs.getString("idpersons");
				str_user_fknumber = rs.getString("fknumber");
				str_name_student = rs.getString("name");
				break;
			}

			rs.close();
			stmt.close();
			con.close();
			//proverka za podavane na prazen argument
			if (str_id_persons == "") {
				State1(response,request, "Missing Client with such Info!");
				return;
			}

		} catch (SQLException e) {
		}
		//koda ste se izpalni samo ako tezi poleta ne sa NULL
		if ((str_user_fknumber != null) && (str_name_student != null)) {
			String usrname, usrfk;
			//izvlichane na parametrite ot html
			usrname = request.getParameter("user_name");
			usrfk = request.getParameter("user_fknumber");
			//proverka za ne sastestvuvast user
			if (!usrname.equals(str_name_student)
					|| !usrfk.equals(str_user_fknumber)) {
				State1(response,request, "Missing client with such info,please try again!");
				return;
			}

			if (str_book_title == null)
				str_book_title = "";
			if (str_book_author == null)
				str_book_author = "";
			PrintWriter out = response.getWriter();
			//opcia za kirilica
			response.setContentType("text/html");
			response.setContentType("text/html;charset=UTF-8");
			request.setCharacterEncoding("UTF-8");
			
			out.println("<html>");
			out.println("<head><title>Library</title></head>");
			out.println("<body background='/Library/image/background.jpg'>");
			out.println("<center>");
			//uslovie za izkarvane na error message
			if (!(Msg.equals(""))) {
				out.println("<br><strong><font color='red'>" + Msg
						+ "</strong></font><br>");
			}
			out.println("FkNumber: <strong>" + str_user_fknumber + "</strong> Name: <strong>"
					+ str_name_student + "</strong>");
			out.println("<form action=/Library/BookTake method=get>");
			out.println("<input type='hidden' name='id_persons' value='"
					+ str_id_persons + "'>");
			out.println("<input type='hidden' name=str_fknumber value='"
					+ str_user_fknumber + "'>");
			out.println("<table cols=1>");
			out.println("<tr>");

			out.println("<td ALIGN=CENTER BGCOLOR=#DCDCDC><strong>Book Take</strong></td>");
			out.println("</tr>");
			out.println("<tr><td><table cols=4 width=90%></td></tr>");
			out.println("<tr>");
			out.println("<td ALIGN=left>Book Title:</td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td ALIGN=left><input type=text name='book_title' value='"
					+ str_book_title + "'></td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td ALIGN=left>Book Author:</td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td ALIGN=left><input type=text name='book_author' value='"
					+ str_book_author + "'></td>");
			out.println("</tr>");
			out.println("<tr><td><br></td></tr>");
			out.println("<tr><td style='width: 33%; padding-left:20px' ALIGN=left><button type=submit style='width:80%; height:29px'>Submit</button></form></td></tr>");
			out.println("<tr><td style='width: 33%; padding-left:20px' ALIGN=left><form action=/Library/MainForm method=get><button type=submit style='width:80%; height:29px'>Back</button></form></td></tr>");
			out.println("<tr><td style='width: 33%; padding-left:20px' ALIGN=left><form action=/Library/Login method=get><button type=submit style='width:80%; height:29px'>Exit</button></form></td></tr>");
			out.println("</table>");
			out.println("</center>");
			out.println("</body>");
			out.println("</html>");

			out.close();
		} else {
			//ako poletata po gore sa null - prepratka kam metod state1 za popalvane na tezi poleta
			State1(response,request, "Please, enter FkNumber!");
		}

	}
	//metod za zapisvane na podadenata ot klienta informacia
	protected void Stage3(String str_book_title, String str_book_author,
			HttpServletResponse response, HttpServletRequest request)
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
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		Connection con = null;
		java.sql.Statement stmt = null;
		//Vrazka s bazata
		try {
			con = (Connection) DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
		}
		//Sazdavane na zaqvka
		try {
			stmt = (Statement) con.createStatement();

		} catch (SQLException e) {
		}

		ResultSet rs = null;

		try {

			PreparedStatement statement;

			int ibookCredit = 0;
			int ibookFlow = 0;
			//Zaqvka kam bazata
			statement = (PreparedStatement) con
					.prepareStatement("SELECT * FROM books where Upper(Name)=Upper('"
							+ str_book_title
							+ "') and Upper(Author)=Upper('"
							+ str_book_author + "')");

			rs = statement.executeQuery();

			str_book_title = "";
			str_book_author = "";
			//zapazvane na info ot bazata
			while (rs.next()) {
				str_book_title = rs.getString("Name");
				str_book_author = rs.getString("Author");
				str_id_books = rs.getString("idBooks");
				ibookCredit = rs.getInt("Credit") + 1;
				ibookFlow = rs.getInt("Flow");
				break;
			}

			String bkname;
			String bkauthor;
			//Izvlichane na parametri ot html
			bkname = request.getParameter("book_title");
			bkauthor = request.getParameter("book_author");
			//proverka za podavane na prazni poleta
			if ((bkname.equals("")) || (bkauthor.equals(""))) {
				State1(response, request,"Please, fill the lines with *");
				return;
			}
			//proverka za ne sastestvuvashta kniga
			if (!bkname.equals(str_book_title)
					|| !bkauthor.equals(str_book_author)) {
				State1(response, request,"There is no such Book registered in the Library!");
				return;
			}
			//proverka za nalichnost na knigata
			if(ibookFlow <= 0){
				rs.close();
				stmt.close();
				statement.close();
				con.close();
				//ako nqma nalichna kniga se pravi prepratka kam metoda za generirane na stranicata za vzemane na knigi
				State1(response, request,"This book is not in stock at the moment!");
				return;
			}
			//ako ima takava nalichnost se dekrementira sled vzemaneto
			ibookFlow--;
			//zaqvka kam bazata
			statement = (PreparedStatement) con
					.prepareStatement("Update books Set Flow=?,Credit=? Where idBooks="
							+ str_id_books);
			statement.setInt(1, ibookFlow);
			statement.setInt(2, ibookCredit);
			statement.executeUpdate();
			statement.close();

			//izvlichane na parametar ot html
			str_user_fknumber = request.getParameter("str_fknumber");
			//zaqvka kam bazata
			statement = (PreparedStatement) con
					.prepareStatement("SELECT idpersons FROM persons where fknumber='"
							+ str_user_fknumber + "'");
			rs = statement.executeQuery();
			str_id_persons = "";
			//zapazvane na info ot bazata
			while (rs.next()) {
				str_id_persons = rs.getString("idpersons");
				break;
			}
			//zaqvka kam bazata
			statement = (PreparedStatement) con
					.prepareStatement("INSERT INTO bpref (idBooks,idpersons,datetaken) values(?,?,CURDATE())");

			statement.setString(1, str_id_books);
			statement.setString(2, str_id_persons);
			statement.executeUpdate();
				
			rs.close();
			stmt.close();
			statement.close();
			con.close();
			
		} catch (SQLException e) {
		}

	}

	public BookTake() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		//Sesii
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
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		//izvlichane na parametri ot html
		str_idbpref = request.getParameter("id_bpref");
		str_id_persons = request.getParameter("id_persons");
		str_id_books = request.getParameter("id_books");
		str_user_fknumber = request.getParameter("user_fknumber");
		str_book_title = request.getParameter("book_title");
		str_book_author = request.getParameter("book_author");
		str_name_student = request.getParameter("name_student");
		//usloviq za executvane na metodite
		if (str_idbpref != null) {
			EditRecordbpref(Integer.parseInt(str_idbpref), response);
		} else if (str_id_persons == null) {
			if (str_user_fknumber == null) {
				State1(response,request, "");
			} else {
				State2(str_user_fknumber, str_name_student, response, request,"");
			}
		} else if (str_id_books == null) {
			Stage3(str_book_title, str_book_author, response, request);
			response.sendRedirect("/Library/TakenBookRef");
		} else
			response.sendRedirect("/Library/BookTake");
		return;

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

}
