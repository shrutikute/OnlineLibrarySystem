package com.npu11629.libraryapp.servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handles requests for the application home page.
 * http://localhost:8080/OnlineLibrarySyatem/
 */

public class HomePageServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context;
		RequestDispatcher dispatch;
		
		context = getServletContext();
		dispatch = context.getRequestDispatcher("/index.jsp");
		dispatch.forward(request, response);
	}
	
}
