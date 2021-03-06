<!DOCTYPE html >
<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html lang='ru'>

<head>
<meta charset="UTF-8" http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<c:set var="style" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${style}/css/bootstrap.min.css" />
<link rel="stylesheet" href="${style}/css/main_page.css" />
<fmt:setLocale value="${pageContext.response.locale}" scope="session" />
<fmt:bundle basename="pagecontent" prefix="title.">
	<title><fmt:message key="add_city" /></title>
</fmt:bundle>
</head>

<header>
	<c:set var="page" value="ADD_CITY" scope="request" />
	<c:import url="/general/header.jsp" />
</header>

<body>
	<fmt:bundle basename="pagecontent" prefix="add_city.input.">
		<form action="lowcost" name="input" class="border border-light " method="post">
		<input type="hidden" name="from_page" value="${page}" />
			<div class="login-form col-md-4 offset-md-4">
				<input type="text" class="form-control" name="country" value=""
					placeholder="<fmt:message key="country"/>" required
					pattern="([А-Я]{1}[А-Яа-я]{1,})" /> <input type="text"
					class="form-control" name="city" value=""
					placeholder="<fmt:message key="city"/>" required
					pattern="([А-Я]{1}[А-Яа-я]{1,})" />
				<button class="btn btn-primary btn-block " type="submit"
					name="command" value="ADD_CITY">
					<fmt:message key="add_city" />
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
