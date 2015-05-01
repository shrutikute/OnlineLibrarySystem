<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>List of Books</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/nav.css" />
</head>
<body>

	<%@ include file="./homeNavbar2.jsp"%>
	
	<h1>List of books:</h1>
	<c:choose>
		<c:when test='${bookList == null || empty bookList}'>
         No books available at this time
    </c:when>
		<c:otherwise>
			<ul>
				<c:forEach items="${bookList}" var="curBook">
					<li><a
						href=${pageContext.request.contextPath}/listMembers?BookName=${curBook.title}>${curBook.title}</a>
					</li>
				</c:forEach>
			</ul>
		</c:otherwise>
	</c:choose>
</body>
</html>


