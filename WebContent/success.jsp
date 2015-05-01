<%@ include file="./include.jsp"%>
<%-- <%@ page session="false" %>
 --%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html" %>
<c:set var="context" scope="request" value="<%= request.getContextPath()%>" />

<html>
<head>
<title>Success</title>
</head>
<body>
	<%@ include file="./homeNavbar2.jsp"%>

	<label>Welcome : <b> ${member.name}</b></label>
	<label> Your Member ID is : <b>${member.memid}</b></label>
	<br />
</body>
</html>
<c:remove var="name" scope="session" />

<link rel="stylesheet" type="text/css" href="${context}/resources/css/nav.css" />

<%  
String memId = request.getParameter("id");  
%> 
<div id="wrap">
  <ul id="nav">
	<li><a href="/OnlineLibrarySyatem/issueBook">Issue a book</a> 
	<li><a href="/OnlineLibrarySyatem/returnBook">Return book</a> 
	<li><a href="/OnlineLibrarySyatem/bookmembers">View Books </a> 	
	<li><a href="${context}">Log Out</a>
  </ul>
</div>
    


