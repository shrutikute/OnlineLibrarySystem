<%@ include file="./include.jsp"%>
<%@ page session="false" %>
<c:set var="context" scope="request" value="<%= request.getContextPath()%>" />

<html>
<head>
<title>ReturnSuccess</title>
</head>
<body>
	<%@ include file="./homeNavbar2.jsp"%>

	<br />
	<label>You Have Successfully returned one book <b> ${name} </b>
	</label>
	
<h2>
<a href=${pageContext.request.contextPath}/success.jsp>Home</a>
</h2>
</body>
</html>
