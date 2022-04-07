<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>My Notes</title>
	</head>
	<body>
		<h1>Forgot Password</h1>
		<form action="forgot" method="post">
			email: <input type="text" name="email"><br>
			<input type="submit" value="Submit">
		</form>
		
		<p>
			${message}
		</p>
	</body>
</html>
