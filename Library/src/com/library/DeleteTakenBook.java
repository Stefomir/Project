package com.library;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Connection;

@WebServlet("/DeleteTakenBook")
public class DeleteTakenBook extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DeleteTakenBook() {
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
		//zarezdane na draivera za vrazka s bazata
		try {
			Class.forName("com.mysql.jdbc.Driver");

		} catch (ClassNotFoundException e) {
		}

		String url = "jdbc:mysql://localhost/library";
		String user = "stefko";
		String password = "stefko";

		Connection con = null;
		//vrazka s bazata
		try {
			con = (Connection) DriverManager.getConnection(url, user, password);

		} catch (SQLException e) {
		}
		try {
			//izvlichane na parametar ot html
			String selecttt = request.getParameter("id111");
			//zaqvka kam bazata
			java.sql.PreparedStatement ps;
			ps = con.prepareStatement("DELETE FROM bpref WHERE idbpref =?");
			ps.setString(1, selecttt);

			ps.executeUpdate();

			ps.close();
			con.close();

		} catch (SQLException e1) {
		}
		//prepratka kam servlet za referencia na vzetite knigi v bibliotekata
		response.sendRedirect("/Library/TakenBookRef");

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

}
