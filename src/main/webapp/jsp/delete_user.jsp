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
	<title><fmt:message key="delete_user" /></title>
</fmt:bundle>
</head>

<header>
	<c:set var="page" value="DELETE_USER" scope="request" />
	<c:import url="/general/header.jsp" />
</header>

<body>
	<fmt:bundle basename="pagecontent" prefix="delete_user.">
		<form action="lowcost" name="input" class="border border-light " method="post">
			<input type="hidden" name="from_page" value="${page}" />
			<p class="login-header text-center">
				<fmt:message key="title" />
			</p>
			<div class="login-form col-md-4 offset-md-4">
				<input class="form-control" type="password" name="repeat_password" value=""
					placeholder="<fmt:message key="repeat_password"/>" required
					pattern="(\w{5,})" />
				<button class="btn btn-primary btn-block " type="submit"
					name="command" value="DELETE_USER">
					<fmt:message key="command" />
				</button>
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
