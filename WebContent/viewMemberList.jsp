<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Book Issue List</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/nav.css" />
</head>

<body>
		<%@ include file="./homeNavbar2.jsp"%>

	<h1>List of Members who issued ${param.BookName}</h1>

	<c:choose>
		<c:when test='${empty memberList}'>
         No Member has currently issued this book
    </c:when>
		<c:otherwise>
			<ul>
				<c:forEach items="${memberList}" var="curMember">
					<li>${curMember.name}</li>
				</c:forEach>
			</ul>
		</c:otherwise>
	</c:choose>
	<br>
	<br>
	<a href=${pageContext.request.contextPath}/enterBookNameForm.jsp> View Other Books</a>
	<a href=${pageContext.request.contextPath}/success.jsp> Back to Home Page</a>
	
</body>
</html>