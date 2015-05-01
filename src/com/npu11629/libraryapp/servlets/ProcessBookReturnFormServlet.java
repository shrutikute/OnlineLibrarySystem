package com.npu11629.libraryapp.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.npu11629.libraryapp.domain.BookIssue;
import com.npu11629.libraryapp.services.BookIssueService;
import com.npu11629.libraryapp.services.BookService;

public class ProcessBookReturnFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		ServletContext context;
		RequestDispatcher dispatch;
		HttpSession session;
		session = request.getSession(false);
				
		
		BookIssue bookIssue = new BookIssue();
		
		int memid = Integer.parseInt(request.getParameter("memID"));
		System.out.println(memid);
		
		int bookid = Integer.parseInt(request.getParameter("bookID"));
		System.out.println(bookid);
		
		bookIssue.setBookid(bookid);
		bookIssue.setMemid(memid);
		
        BookIssueService.deleteEntry(memid, bookid);  
        
        try {
			String bookName = BookService.findBookNameById(bookid);
			session.setAttribute("name", bookName);
			
		} catch (SQLException | NamingException e) {
			e.printStackTrace();
		}
        
		context = getServletContext();
		dispatch = context.getRequestDispatcher("/returnSuccess.jsp");
		dispatch.forward(request, response);
				
	}

}

	
	
