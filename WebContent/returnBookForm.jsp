<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>Return Books Issued</title>
</head>

<body>
	<%@ include file="./homeNavbar2.jsp"%>

	<h1>Book list</h1>

	<label>Member ID : <b> ${member.memid}</b></label>
	
	<table BORDER="8" bgcolor="#FAEBD7" cellspacing="10" cellpadding="15">
		<tr>
			<th>Select book for return</th>
		</tr>
		<c:forEach items="${newbooklist}" var="curBook" >
			<tr>
				<td>${curBook.title}</td>
				<td><c:url value="/bookReturned" var="theUrl">
						<c:param name="bookID" value="${curBook.bookid}" />
						<c:param name="memID" value="${member.memid}" />
					</c:url> <a href="${theUrl}">Return</a></td>
			</tr>
		</c:forEach>
	</table>

	<br>
	<br>
	<h2>
		<a href=${pageContext.request.contextPath}/success.jsp>Home</a>
	</h2>
</body>
</html>



