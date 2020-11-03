<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Todo List</title>

<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
</head>
<body>
<header>
			<nav class="navbar navbar-expand-md navbar-dark"
				style="background-color: #4682B4">
				<div>
					<a href="http://www.vincenza.idv.tw" class="navbar-brand"> Todo
						List</a>
				</div>
	
				<ul class="navbar-nav navbar-collapse justify-content-end">
					<li><a href="LoginPage.jsp" class="nav-link">Log In</a></li>
					<li><a href="RegistrationPage.jsp" class="nav-link">Sign Up</a></li>
				</ul>
			</nav>
		</header>
	<div class="container col-md-8 col-md-offset-3" style="overflow: auto">
	<h1>User Register Form</h1>
				<form action="<%=request.getContextPath()%>/register" method="post">

					<div class="form-group">
						<div style="width:100%;">
							<label style="float:left;" for="uname">User Name:</label>
							<span class="errorMsg" style="float:right;color:red;">
									${requestScope.msg}
							</span>
						</div>
							<input type="text"
								class="form-control" id="signusername" placeholder="User Name"
								name="signusername" required>
					</div>			

					<div class="form-group">
						<label for="uname">Password:</label> <input type="password"
							class="form-control" id="signpassword" placeholder="Password"
							name="signpassword" required>
					</div>
					
					<div class="form-group">
						<label for="uname">Password Confirmed:</label> <input type="password"
							class="form-control" id="confirmpw" placeholder="Password Confirmed"
							name="confirmpw" required>
					</div>

					<button type="submit" class="btn btn-primary"  style="background-color:#4682B4">Sign-Up</button>

				</form>	
	</div>
<script src="http://code.jquery.com/jquery-1.7.2.min.js"></script>
	<script type="text/javascript">
			//after loding this page
			$(function () {
				$("#signusername").blur(function () {
					//get username
					var signusername = this.value;
					$.getJSON("http://localhost:8080/todo/duplicated","action=doPost&signusername=" + signusername,function (data) {
						if (data.existUsername) {
							$("span.errorMsg").text("Username already exists!");
						}
						else {
							$("span.errorMsg").text(" ");
						}
					});
				});
			});
	</script>
</body>
</html>