<!DOCTYPE html >
<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html lang='ru'>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<c:set var="style" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${style}/css/bootstrap.min.css" />
<link rel="stylesheet" href="${style}/css/main_page.css" />
<fmt:setLocale value="${pageContext.response.locale}" scope="session" />
<fmt:bundle basename="pagecontent" prefix="title.">
	<title><fmt:message key="sign_in" /></title>
</fmt:bundle>
</head>

<header>
	<c:set var="page" value="SIGN_IN" scope="request" />
	<c:import url="/general/header.jsp" />
</header>

<body>
	<fmt:bundle basename="pagecontent" prefix="signin.input.">
		<form name="input" class="border border-light " method="post">
		<input type="hidden" name="from_page" value="${page}" /> 
			<p class="login-header text-center">
				<fmt:message key="title" />
			</p>
			<div class="login-form col-md-4 offset-md-4">
				<input class="form-control" type="email" name="email" value=""
					placeholder="<fmt:message key="email"/>" required
					pattern="([a-zA-Z_0-9]{1,}@([a-z]{3,7})\.(ru|com|by|net))" /> <input
					class="form-control" type="password" name="password" value=""
					placeholder="<fmt:message key="password"/>" required
					pattern="(\w{5,})" />
				<button class="btn btn-primary btn-block " type="submit"
					name="command" value="SIGN_IN">
					<fmt:message key="command" />
				</button>

				<div class="form-check">
					<input class="form-check-input" type="checkbox" name="remember"
						value="true">
					<fmt:message key="memory" />
				</div>

			</div>
			<div class="login-info text-center">

				<p>
					<a href=""><fmt:message key="register" /></a>
				</p>
				<p>
					<a href=""><fmt:message key="forgot_password" /></a>
				</p>

			</div>
		</form>
	</fmt:bundle>
	<div class="error-code col-md-4 offset-md-4">${ERROR_MESSAGE}</div>
	<script type="text/javascript" src="${style}/js/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="${style}/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${style}/js/popper.min.js"></script>
	<script type="text/javascript" src="${style}/js/main.js"></script>
</body>
</html>
