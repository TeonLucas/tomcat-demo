package com.newrelic.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author davidmorris
 */
public class DbConnection {
	private Connection con;

	public DbConnection(String file) {
		// create connection
		try {
			Class.forName("org.sqlite.JDBC");
			this.con = DriverManager.getConnection("jdbc:sqlite:" + file);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Opened " + file + " successfully");

		try {
			Statement stmt = this.con.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS users (id CHAR(32) PRIMARY KEY,"
					+ " count INTEGER DEFAULT 0, filename TEXT) WITHOUT ROWID;";
			stmt.executeUpdate(sql);
	        stmt.close();
		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	public Connection getConnection() {
		return this.con;
	}

	public void closeConnection() {
		try {
			this.con.close();
		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
}
