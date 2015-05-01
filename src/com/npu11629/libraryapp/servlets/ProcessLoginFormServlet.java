package com.npu11629.libraryapp.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.npu11629.libraryapp.domain.Member;
import com.npu11629.libraryapp.services.MemberService;


@WebServlet("/servlet/processloginform")
public class ProcessLoginFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session;
		session = request.getSession(false);
		String pageCtx;
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		pageCtx = request.getContextPath();

		String n = request.getParameter("username");
		String p = request.getParameter("password");

		try {
			
			Member mem = new Member();
			mem = MemberService.findMemberIdByName(n);
			session.setAttribute("member", mem);

		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (NamingException e1) {
			e1.printStackTrace();
		}


		try {
			if (MemberService.validate(n, p)) {
				response.sendRedirect(pageCtx + "/success.jsp");
			} else {
				out.print("<p style=\"color:red\">Sorry username or password error</p>");
				RequestDispatcher rd = request
						.getRequestDispatcher("/loginForm.jsp");
				rd.include(request, response);
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

}
