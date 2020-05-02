<!DOCTYPE html>


<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html lang='ru'>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<c:set var="style" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${style}/css/bootstrap.min.css" />
<link rel="stylesheet" href="${style}/css/main_page.css" />
<fmt:setLocale value="${pageContext.response.locale}" scope="session" />
<fmt:setLocale value="${pageContext.response.locale}" scope="session" />
<fmt:bundle basename="pagecontent" prefix="title.">
	<title><fmt:message key="create_ticket" /></title>
</fmt:bundle>
</head>

<header>
	<c:set var="page" value="CREATE_TICKET" scope="request" />
	<c:import url="/general/header.jsp" />
</header>

<body>
	<fmt:bundle basename="pagecontent" prefix="create_ticket.">
		<form name="select" method="post">
			<input type="hidden" name="from_page" value="${page}" /> <input
				type="text" class="form-control col-md-4 offset-md-4"
				name="first_name" value=""
				placeholder="<fmt:message key="first_name"/>" required /> <input
				type="text" class="form-control col-md-4 offset-md-4"
				name="last_name" value=""
				placeholder="<fmt:message key="last_name"/>" required /> <input
				type="text" class="form-control col-md-4 offset-md-4"
				name="passport_number" value=""
				placeholder="<fmt:message key="passport_number"/>" required
				pattern="([A-Za-z]{2}[0-9]{7})" /> <input type="text"
				class="form-control col-md-4 offset-md-4" name="luggage" value=""
				placeholder="<fmt:message key="luggage"/>" required
				pattern="[0-9]{1,}" />

			<div class="form-check col-md-4 offset-md-4">
				<input class="form-check-input" type="checkbox"
					name="primary_boarding" value="true">
				<fmt:message key="primary_boarding" />
			</div>
			<button type="submit" class="btn btn-light col-md-4 offset-md-4"
				name="command" value="CREATE_TICKET">
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
