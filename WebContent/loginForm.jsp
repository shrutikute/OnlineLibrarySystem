<%@ include file="./include.jsp"%>


<html>
<head>
<title>Personal Details</title>
</head>
<body>
	<%@ include file="./homeNavbar2.jsp"%>

<center>
	<h1>Please enter your login details</h1>
	<form name="loginForm" action="${pageContext.request.contextPath}/servlet/processloginform" method="POST">
		UserName: 
		<input type="TEXT" name="username" value="${loginFormData.username}"> <font size="3" color="red">${errorInUserNameMsg}</font><BR>
		Password:
		<input type="password" name="password" value="${loginFormData.password}"> <font size="3" color="red">${errorInPasswordMsg}</font><BR>
		<a href="/OnlineLibrarySyatem/index.jsp">Back</a> 
		<input type="submit" name="Register" value="Enter">
	</form>
	</center>
</body>
</html>

