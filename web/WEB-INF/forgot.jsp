<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>My Notes</title>
	</head>
	<body>
		<h1>Forgot Password</h1>
		<form action="forgot" method="post" name = "template">
			name: <input type="text" name="name"><br>
			email: <input type="text" name="email"><br>
			<button type = "submit">Submit</button>
		</form>
		
		<p>
			${message}
		</p>
	</body>
</html>
