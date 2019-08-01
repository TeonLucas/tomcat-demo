package com.newrelic.servlet;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.servlet.DbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
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
@WebServlet("/count")
public class GetCount extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletContext ctx = req.getServletContext();

		// Get session id
		HttpSession session = req.getSession();
		String id = session.getId();
		Integer count = 0;

		DbConnection db = (DbConnection) ctx.getAttribute("DB");
		Connection con = db.getConnection();
		Statement stmt;
		try {
			stmt = con.createStatement();
			// Increment count
			String sql = "UPDATE users SET count = (count + 1) WHERE id = '" + id + "';";
			stmt.executeUpdate(sql);

			// Query state
		    sql = "SELECT count FROM users WHERE id = '" + id + "';";
			ResultSet resultSet = stmt.executeQuery(sql);
			count = resultSet.getInt(1);
			stmt.close();
		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		// Add user info to Transaction
		NewRelic.addCustomParameter("userCount", count);
		NewRelic.addCustomParameter("userId", id);

		resp.setContentType("text/plain");
		resp.getWriter().write("Welcome User, session: " + id + "\n");
		resp.getWriter().write("Count: " + count.toString() + "\n");
	}
}
