package com.newrelic.servlet;

import com.newrelic.servlet.DbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author davidmorris
 */
@WebServlet("/session")
public class GetSession extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletContext ctx = req.getServletContext();

		// Get session id
		HttpSession session = req.getSession();
		String id = session.getId();

		DbConnection db = (DbConnection) ctx.getAttribute("DB");
		Connection con = db.getConnection();
		Statement stmt;
		try {
			stmt = con.createStatement();
			// Insert sessionId if not exists
			String sql = "INSERT INTO users (id) SELECT '" + id
					+ "' WHERE NOT EXISTS(SELECT 1 FROM users WHERE id = '" + id + "');";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		resp.setContentType("text/plain");
		resp.getWriter().write("Welcome User, session: " + id);
	}
}
