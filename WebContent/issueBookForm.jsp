
<%@ include file="./include.jsp"%>
<html>
<head>
<title>List Of Books</title>
<style>
.error {
	font-size: 0.8em;
	color: #ff0000;
}
</style>
</head>

<body>
	<%@ include file="./homeNavbar2.jsp"%>

	<h1>Book list</h1>

	<label>Member ID : <b> ${member.memid}</b></label>
	
	<table BORDER="8" bgcolor="#FAEBD7" cellspacing="10" cellpadding="15">
		<tr>
			<th>Book Name</th>
		</tr>
		<c:forEach items="${booklist}" var="curBook" >
			<tr>
				<td>${curBook.title}</td>
				<td><c:url value="/bookIssued" var="theUrl">
						<c:param name="bookID" value="${curBook.bookid}" />
						<c:param name="memID" value="${member.memid}" />
					</c:url> <a href="${theUrl}">Issue</a></td>
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
