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

@WebServlet("/UserReg")
public class UserReg extends HttpServlet {
	private static final long serialVersionUID = 1L;

	String str_id_persons;
	String str_user_name;
	String str_user_fknumber;
	//metod za generirane na stranicata za reg na user
	protected void stage1(HttpServletRequest request,
			HttpServletResponse response, String Msg) throws ServletException,
			IOException {
		//opcia za kirilica
		response.setContentType("text/html");
		response.setContentType("text/html;charset=UTF-8");

		PrintWriter out = response.getWriter();

		String selectt = request.getParameter("id11");

		out.println("<html>");
		out.println("<head><title>Library</title></head>");
		out.println("<body background='/Library/image/background.jpg'>");
		out.println("<center>");

		String strUser_name = null, strUser_fknumber = null;

		if (selectt == null) {

			strUser_name = "";
			strUser_fknumber = "";
			out.println("<form action=/Library/UserReg method=get>");
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
			//zarezdane na zaqvka
			try {
				stmt = (Statement) con.createStatement();

			} catch (SQLException e) {
				out.println("Something went wrong with the Statement");
			}

			ResultSet rs = null;
			try {
				//zaqka kam bazata
				rs = stmt.executeQuery("SELECT * FROM persons Where idpersons="
						+ selectt);

				strUser_name = "";
				strUser_fknumber = "";
				//zapazvane na info ot bazata
				while (rs.next()) {
					strUser_name = rs.getString("name");
					strUser_fknumber = rs.getString("fknumber");
					break;
				}
				rs.close();
				stmt.close();
				con.close();
			} catch (SQLException e) {
				out.println("There is a problem with the ExecuteQuery");
			}
			out.println("<form action=/Library/UserReg method=get>");
			out.println("<input type='hidden' name='id11' value='" + selectt
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
		out.println("<td ALIGN=left>*User Name:</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left><input type=text name='user_name' value='"
				+ strUser_name + "'></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left>*User FKnumber:</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left><input type=text name='user_fknumber' value='"
				+ strUser_fknumber + "'></td>");
		out.println("</tr>");
		out.println("<tr><td style='width: 33%; padding-left:20px' ALIGN=left>"
				+ "<button type=submit style='width:80%; height:29px'>Save</button></form></td></tr>");
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
	//metod za redaktirane na zapisite na registriranite useri
	protected void userEdit(HttpServletRequest request,
			HttpServletResponse response, String Msg) throws ServletException,
			IOException {
		//opcia za kirilica
		response.setContentType("text/html");
		response.setContentType("text/html;charset=UTF-8");

		PrintWriter out = response.getWriter();

		String selectt = request.getParameter("id11");

		out.println("<html>");
		out.println("<head><title>Library</title></head>");
		out.println("<body background='C:/workplace Servlets/Library/WebContent/WEB-INF/image/background.jpg'>");
		out.println("<center>");

		String strUser_name = null, strUser_fknumber = null;

		if (selectt == null) {

			strUser_name = "";
			strUser_fknumber = "";
			out.println("<form action=/Library/UserReg method=get>");
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
			//zarezdane na zaqvka
			try {
				stmt = (Statement) con.createStatement();

			} catch (SQLException e) {
				out.println("Something went wrong with the Statement");
			}

			ResultSet rs = null;
			try {
				//zaqvka kam bazata
				rs = stmt.executeQuery("SELECT * FROM persons Where idpersons="
						+ selectt);

				strUser_name = "";
				strUser_fknumber = "";
				//zapazvane na info ot bazata
				while (rs.next()) {
					strUser_name = rs.getString("name");
					strUser_fknumber = rs.getString("fknumber");
					break;
				}
				rs.close();
				stmt.close();
				con.close();
			} catch (SQLException e) {
				out.println("There is a problem with the ExecuteQuery");
			}
			out.println("<form action=/Library/UserReg method=get>");
			out.println("<input type='hidden' name='id11' value='" + selectt
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
		out.println("<td ALIGN=left>*User Name:</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left><input type=text name='user_name' value='"
				+ strUser_name + "'></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left>*User FKnumber:</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td ALIGN=left><input type=text name='user_fknumber' value='"
				+ strUser_fknumber + "'></td>");
		out.println("</tr>");
		out.println("<tr><td style='width: 33%; padding-left:20px' ALIGN=left><button type=submit style='width:80%; height:29px'>Save</button></form></td></tr>");
		out.println("<tr><td style='width: 33%; padding-left:20px' ALIGN=left><form action=/Library/MainForm method=get><button type=submit style='width:80%; height:29px'>Back</button></form></td></tr>");
		out.println("<tr><td style='width: 33%; padding-left:20px' ALIGN=left><form action=/Library/Login method=get><button type=submit style='width:80%; height:29px'>Exit</button></form></td></tr>");
		out.println("</table>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");

		out.close();
	}
	//metod za zapazvane na info na registrirania user
	protected void userSave(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String url = "jdbc:mysql://localhost/library";
		String user = "stefko";
		String password = "stefko";

		java.sql.Connection con = null;
		//izvlichane na parametri ot html
		String selectt = request.getParameter("id11");
		String user_Name = request.getParameter("user_name");
		String user_Fknumber = request.getParameter("user_fknumber");
		//proverka e vavedeno chislo v poleto za user name
		try {
			Integer.parseInt(user_Name);
			stage1(request, response, "You can't enter a number for UserName!");
			return;
		} catch (Exception e) {
		}
		//proverka dali poletata name i fknumber sa prazni
		if ((user_Name.equals("")) || user_Fknumber.equals("")) {
			stage1(request, response, "Please fill the lines with *!");
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
			String checkFk = null;
			PreparedStatement statement = null;
			if (selectt == null)
				//zaqvka kam bazata
				statement = (PreparedStatement) con
						.prepareStatement("SELECT * FROM persons where Upper(fknumber)=Upper(?)");
			else
				statement = (PreparedStatement) con
						.prepareStatement("SELECT * FROM persons where Upper(fknumber)=Upper(?) and idpersons<>"
								+ selectt);
			statement.setString(1, user_Fknumber);
			ResultSet rs = statement.executeQuery();
			//zapazvane na info ot bazata
			while (rs.next()) {
				checkFk = rs.getString("fknumber");
				break;
			}
			statement.close();
			//proverka dali veche sastestvuva user s takav fknumber
			if (checkFk != null) {
				stage1(request, response,
						"There is another person with the same FkNumber!");
				return;
			}

			java.sql.PreparedStatement ps;

			if (selectt == null)
				//zaqvka kam bazata
				ps = con.prepareStatement("INSERT INTO persons (name,fknumber) values(?,?)");
			else
				ps = con.prepareStatement("Update persons Set name=?,fknumber=? Where idpersons="
						+ selectt);

			ps.setString(1, user_Name);
			ps.setString(2, user_Fknumber);

			ps.executeUpdate();

			ps.close();
			con.close();

		} catch (SQLException e1) {
		}

	}

	public UserReg() {
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
		//izvlichane na parametri ot html
		String str_id_persons = request.getParameter("id11");
		String str_user_name = request.getParameter("user_name");
		String str_user_fknumber = request.getParameter("user_fknumber");
		//usloviq za executvane na metodite
		if (str_id_persons != null && str_user_name == null
				&& str_user_fknumber == null) {
			userEdit(request, response, "");
		} else {
			if (str_user_name == null && str_user_fknumber == null) {
				stage1(request, response, "");

			} else {
				userSave(request, response);
				response.sendRedirect("/Library/UserRef");
			}
		}

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

}
