package com.npu11629.libraryapp.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.npu11629.libraryapp.domain.Member;
import com.npu11629.libraryapp.services.BookService;

public class ProcessListMemberFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		ServletContext context;
		RequestDispatcher dispatch;
		HttpSession session;
		session = request.getSession(false);
		
		Member member = (Member) session.getAttribute("member");
		session.setAttribute("member", member);
				
		String bookName = request.getParameter("BookName");
		
		try {
			List<Member> memberList = BookService.getMemberList(bookName);
			request.setAttribute("memberList", memberList);      
		} catch (SQLException | NamingException e) {
			e.printStackTrace();
		}
					
        
		context = getServletContext();
		dispatch = context.getRequestDispatcher("/viewMemberList.jsp");
		dispatch.forward(request, response);
				
	}

}

	
	
