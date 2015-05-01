<%@ include file="./include.jsp"%>
<html>
<head>
<title>New Member Information</title>
<style>
.error {
	font-size: 0.8em;
	color: #ff0000;
}
</style>
</head>



<body>
	<%@ include file="./homeNavbar2.jsp"%>
	
	<h1>Please Sign Up</h1>
	<form name="signupForm"
		action="${pageContext.request.contextPath}/servlet/processsignupform"
		method="POST">
		Name: <input type="TEXT" name="name"> <BR> Address : <input
			type="TEXT" name="address"> <BR> Classification : <input
			type="radio" name="classification" value="Grad" checked="true">Grad<br>
		<input type="radio" name="classification" value="UnderGrad">UnderGrad<br>
		<input type="radio" name="classification" value="Faculty">Faculty<br>

		UserName : <input type="TEXT" name="username"> <font size="3"
			color="red">${errorInPasswordMsg}</font><BR> Password : <input
			type="password" name="password"> <font size="3" color="red">${errorInPasswordMsg}</font><BR>

		<a href="/OnlineLibrarySyatem/index.jsp">Back</a> <input type="submit"
			name="Register" value="Enter">
	</form>
</body>
</html>


