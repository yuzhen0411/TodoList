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
<body>
	<script>
	
	  function statusChangeCallback(response) {  // Called with the results from FB.getLoginStatus().
	    console.log('statusChangeCallback');
	    console.log(response);                   // The current login status of the person.
	    if (response.status === 'connected') {   // Logged into your webpage and Facebook.
	      testAPI(); 
	    } else {                                 // Not logged into your webpage or we are unable to tell.
	      /*document.getElementById('status').innerHTML = 'Please log ' +
	        'into this webpage.';*/
	    }
	  }
	
	
	  function checkLoginState() {               // Called when a person is finished with the Login Button.
	    FB.getLoginStatus(function(response) {   // See the onlogin handler
	      statusChangeCallback(response);
	    });
	  }
	
	
	  window.fbAsyncInit = function() {
	    FB.init({
	      appId      : '2851061771804676',
	      cookie     : true,                     // Enable cookies to allow the server to access the session.
	      xfbml      : true,                     // Parse social plugins on this webpage.
	      version    : 'v8.0'           // Use this Graph API version for this call.
	    });
	
	
	    FB.getLoginStatus(function(response) {   // Called after the JS SDK has been initialized.
	      statusChangeCallback(response);        // Returns the login status.
	    });
	  };
	 
	  function testAPI() {                      // Testing Graph API after login.  See statusChangeCallback() for when this call is made.
	    console.log('Welcome!  Fetching your information.... ');
	    FB.api('/me?fields=name,id,email', function (response) {
	    	
	    	//create a form 
	    	var form1 = document.createElement("form"); 
	    	form1.id = "form1"; 
	    	form1.name = "form1"; 
	    	//put form into body
	    	document.body.appendChild(form1); 
	    	//put username info into input
	    	var input = document.createElement("input"); 
	    	input.type = "hidden"; 
	    	input.name = "signusername"; 
	    	input.value = response.email; 
	    	//put username input into form
	    	form1.appendChild(input); 
	    	//put password info into input
	    	var input1 = document.createElement("input"); 
	    	input1.type = "hidden"; 
	    	input1.name = "signpassword"; 
	    	input1.value = response.id; 
	    	//put password into form
	    	form1.appendChild(input1); 
	    	//commit a form
	    	form1.method = "POST";  
	    	form1.action = "http://localhost:8080/todo/FBServlet?action=doPost"; 
	    	form1.submit(); 
	    	//delete a form
	    	document.body.removeChild(form1); 
	    	})
	  }
	
	</script>
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
		<h1>Login Form</h1>
		<form action="<%=request.getContextPath()%>/login" onsubmit="return dosubmit()" method="post">
			<div class="form-group">
				<label for="uname">User Name:</label> <input type="text"
					class="form-control" id="username" placeholder="User Name"
					name="username" required>
			</div>

			<div class="form-group">
				<label for="uname">Password:</label> <input type="password"
					class="form-control" id="password" placeholder="Password"
					name="password" required>
			</div>

			<div style="vertical-align:middle;">
				<button type="submit" class="btn btn-primary" style="background-color:#4682B4;margin-top:-20px; height:40px;">Log-In</button>
				<fb:login-button scope="public_profile,email" onlogin="checkLoginState();" style="padding-left:5px;margin-top:10px;" data-size="large" data-button-type="login_with" data-layout="default" data-use-continue-as="false" data-width=""></fb:login-button> 
			</div>
		</form>	
		
	</div>
	<div id="status">
	</div>
	<!-- Load the JS SDK asynchronously -->
    <script async defer crossorigin="anonymous" src="https://connect.facebook.net/en_US/sdk.js"></script>
</body>
</html>