package com.npu11629.libraryapp.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.npu11629.libraryapp.domain.Member;
import com.npu11629.libraryapp.services.MemberService;

@WebServlet("/servlet/processsignupform")
public class ProcessSignUpFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String pageCtx;
		Member mem = new Member();
		
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String classification= request.getParameter("classification");
		String userName= request.getParameter("username");
		String password= request.getParameter("password");
		
		mem.setName(name);
		mem.setAddress(address);
		mem.setClassification(classification);
		mem.setUserName(userName);
		mem.setPassword(password);
		
		MemberService.addNewMember(mem);
		
		/* The form was valid.   Apply business logic on it and then redirect to new view */
		pageCtx = request.getContextPath();
				
		response.sendRedirect(pageCtx + "/index.jsp");
	}

}
