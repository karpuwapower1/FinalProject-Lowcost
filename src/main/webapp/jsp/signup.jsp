<html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<c:set var="style" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${style}/css/bootstrap.min.css" />
<link rel="stylesheet" href="${style}/css/signin.css" />
</head>

<header>
	<c:set var="page_from" value="<%=by.training.karpilovich.lowcost.command.Page.SIGNUP%>" scope="request"/>
	<c:import url="/general/header.jsp" />
</header>

<body>

	<form name="input" class="border border-light " method="post">
		<fmt:bundle basename="pagecontent" prefix="signup.input.">
			<p class="login-header text-center">
				<fmt:message key="title" />
			</p>

			<div class="login-form col-md-4 offset-md-4">

				<input type="email" name="email" class="form-control"
					placeholder="<fmt:message key="email"/>" value="" required
					pattern="([a-zA-Z_0-9]{1,}@([a-z]{3,7})\.(ru|com|by|net))" /> <input
					type="password" name="password" id="password" class="form-control"
					placeholder="<fmt:message key="password"/>" value="" required
					pattern="(\w{5,})" /> <input type="password" name="repeatPassword"
					id="repeatPassword" class="form-control"
					placeholder="<fmt:message key="repeat_password"/>" value=""
					required pattern="(\w{5,})" /> <input type="text" name="firstName"
					class="form-control" placeholder="<fmt:message key="first_name"/>"
					value="" required /> <input type="text" name="lastName"
					class="form-control" placeholder="<fmt:message key="last_name"/>"
					value="" />


				<p>${errorMessage}</p>


				<button class="btn btn-primary btn-block " type="submit"
					name="command" value="signup"></button>
				<fmt:message key="command" />


			</div>
		</fmt:bundle>
	</form>



	<script type="text/javascript">
		window.onload = function() {
			document.getElementById("password").onchange = validatePassword;
			document.getElementById("repeatPassword").onchange = validatePassword;
		}
		function validatePassword() {
			var pass2 = document.getElementById("password").value;
			var pass1 = document.getElementById("repeatPassword").value;
			if (pass1 != pass2)
				document.getElementById("repeatPassword").setCustomValidity(
						"Passwords Don't Match");
			else
				document.getElementById("repeatPassword").setCustomValidity('');
		}
	</script>

	<script type="text/javascript" src="${style}/js/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="${style}/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${style}/js/popper.min.js"></script>
	<script type="text/javascript" src="${style}/js/main.js"></script>
</body>
</html>
