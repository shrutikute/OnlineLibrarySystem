package com.npu11629.libraryapp.servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.npu11629.libraryapp.domain.BookIssue;
import com.npu11629.libraryapp.domain.Member;
import com.npu11629.libraryapp.services.BookIssueService;

public class ProcessBookIssueFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		ServletContext context;
		RequestDispatcher dispatch;
		HttpSession session;
		session = request.getSession(false);		
		BookIssue bookIssue = new BookIssue();
		
		int memid = Integer.parseInt(request.getParameter("memID"));
		int bookid = Integer.parseInt(request.getParameter("bookID"));
		
		bookIssue.setBookid(bookid);
		bookIssue.setMemid(memid);
		java.sql.Date today = new java.sql.Date(new java.util.Date().getTime());
        bookIssue.setDate(today);
		
        BookIssueService.addNewEntry(bookIssue); 
           		
		Member member = (Member) session.getAttribute("member");
		session.setAttribute("member", member);
        
		context = getServletContext();
		dispatch = context.getRequestDispatcher("/issueSuccess.jsp");
		dispatch.forward(request, response);
				
	}

}

	
	
