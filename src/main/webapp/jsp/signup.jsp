<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="by.training.karpilovich.lowcost.command.JspParameter"%>
<%@ page import="by.training.karpilovich.lowcost.command.CommandType"%>
<%@ page import="by.training.karpilovich.lowcost.command.Page"%>

<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<c:set var="style" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${style}/css/bootstrap.min.css" />
<link rel="stylesheet" href="${style}/css/signin.css" />
<fmt:setLocale value="${pageContext.response.locale}" scope="session" />
</head>

<header>
	<c:set var="page" value="<%=Page.SIGN_UP%>" scope="request"/>
	<c:import url="/general/header.jsp" />
</header>

<body>

	<form name="input" class="border border-light " method="post">
		<fmt:bundle basename="pagecontent" prefix="signup.input.">
			<p class="login-header text-center">
				<fmt:message key="title" />
			</p>

			<div class="login-form col-md-4 offset-md-4">

				<input class="form-control" type="email" 
					name="<%=JspParameter.EMAIL%>" value=""
					placeholder="<fmt:message key="email"/>" required
					pattern="([a-zA-Z_0-9]{1,}@([a-z]{3,7})\.(ru|com|by|net))" /> 
				<input class="form-control" type="password" 
					type="password" name="<%=JspParameter.PASSWORD%>" value=""
					id="password" 
					placeholder="<fmt:message key="password"/>" required
					pattern="(\w{5,})" /> 
				<input class="form-control" type="password" 
					name="<%=JspParameter.REPEAT_PASSWORD%>" value=""
					id="repeatPassword" 
					placeholder="<fmt:message key="repeat_password"/>" required 
					pattern="(\w{5,})" /> 
				<input class="form-control" type="text"
					name="<%=JspParameter.FIRST_NAME%>" value=""
					 placeholder="<fmt:message key="first_name"/>" required /> 
				<input class="form-control" type="text" 
					name="<%=JspParameter.LAST_NAME%>" value=""
					 placeholder="<fmt:message key="last_name"/>"/>

				<p>${errorMessage}</p>

				<button class="btn btn-primary btn-block " type="submit"
					name="<%=JspParameter.COMMAND%>" 
					value="<%=CommandType.SIGN_UP%>">
					<fmt:message key="command" /></button>
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
