package com.newrelic.servlet;

import com.newrelic.servlet.DbConnection;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author davidmorris
 */
public class StartStop implements ServletContextListener {
	
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext ctx = servletContextEvent.getServletContext();
		
		// Open database
		DbConnection db = new DbConnection("session.db");
    		ctx.setAttribute("DB", db);
	}

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("Stateful Server exiting, closing traces");
		ServletContext ctx = servletContextEvent.getServletContext();
		
		// Close database
		DbConnection db = (DbConnection) ctx.getAttribute("DB");
		db.closeConnection();
    }
}

