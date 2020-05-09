<!DOCTYPE html >
<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html lang='ru'>

<head>
<meta charset="utf-8" http-equiv="Content-Type"
	content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<c:set var="style" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${style}/css/bootstrap.min.css" />
<link rel="stylesheet" href="${style}/css/main_page.css" />
<fmt:setLocale value="${pageContext.response.locale}" scope="session" />
<fmt:bundle basename="pagecontent" prefix="title.">
	<title><fmt:message key="choose_dates" /></title>
</fmt:bundle>
</head>

<header>
	<c:set var="page" value="CHOOSE_DATE_BOUNDS" scope="request" />
	<c:import url="/general/header.jsp" />
</header>

<body>
	<fmt:bundle basename="pagecontent" prefix="choose_dates.input.">
		<form action="lowcost" name="input" class="border border-light " method="post">
			<input type="hidden" name="from_page" value="${page}" /> <input
				class="form-control form-control-lg col-md-4 offset-md-4" 
				placeholder="<fmt:message key="bound_from"/>" required type="text"
				onfocus="(this.type='date')" onblur="(this.type='text')"
				name="bound_from" value="" /> <input
				class="form-control form-control-lg col-md-4 offset-md-4"
				placeholder="<fmt:message key="bound_to"/>" required type="text"
				onfocus="(this.type='date')" onblur="(this.type='text')"
				name="bound_to" value="" />
			<button
				class="btn btn-primary btn-block login-form col-md-4 offset-md-4"
				type="submit" name="command" value="SHOW_FLIGHTS_BETWEEN_DATES">
				<fmt:message key="command" />
			</button>
		</form>
	</fmt:bundle>
	<div class="error-code col-md-4 offset-md-4">${ERROR_MESSAGE}</div>
	<script type="text/javascript" src="${style}/js/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="${style}/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${style}/js/popper.min.js"></script>
	<script type="text/javascript" src="${style}/js/main.js"></script>
</body>
</html>
