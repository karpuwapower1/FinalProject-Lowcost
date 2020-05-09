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
	<title><fmt:message key="add_date_coefficient" /></title>
</fmt:bundle>
</head>

<header>
	<c:set var="page" value="ADD_DATE_COEFFICIENT" scope="request" />
	<c:import url="/general/header.jsp" />
</header>

<body>
	<fmt:bundle basename="pagecontent" prefix="add_coefficient_input.date.">
		<form action="lowcost" name="input" method="post">
			
				<input type="hidden" name="from_page" value="${page}" /> <input class="form-control col-md-4 offset-md-4"
					type="text" name="bound_from"
					value="<fmt:formatDate value="${BOUND_FROM.time}" pattern="yyyy-MM-dd"/>"
					readonly />
		
			<input class="form-control form-control-lg col-md-4 offset-md-4"
				placeholder="<fmt:message key="bound_to"/> <fmt:message key="max_bound_value" /> 
				<fmt:formatDate value="${MAX_BOUND_VALUE.time}"/>" required type="text"
				onfocus="(this.type='date')" onblur="(this.type='text')"
				name="bound_to" value="" />
				<input type="text" class="form-control col-md-4 offset-md-4" name="value" value=""
					placeholder="<fmt:message key="value"/>" required
					pattern="([0-9]{1,}(.[0-9]){0,1})" />
				<button class="btn btn-primary btn-block col-md-4 offset-md-4" type="submit"
					name="command" value="ADD_DATE_COEFFICIENT">
					<fmt:message key="add_coefficient" />
				</button>
		</form>
			<form action="lowcost" method="post" name="redirect">
				<input type="hidden" name="to_page" value="FLIGHT_PREVIEW" />
				<button class="btn btn-primary btn-block col-md-4 offset-md-4" type="submit"
					name="command" value="REDIRECT">
					<fmt:message key="redirect_to_preview_page" />
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
