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
	<title><fmt:message key="update_flight" /></title>
</fmt:bundle>
</head>

<header>
	<c:set var="page" value="UPDATE_FLIGHT" scope="request" />
	<c:import url="/general/header.jsp" />
</header>

<body>
	<fmt:bundle basename="pagecontent" prefix="update_flight.">
		<form name="select" method="post">
			<input type="hidden" name="from_page" value="${page}" /> <input
				type="hidden" name="flight_id" value="${FLIGHT.id}" /> <input
				type="hidden" name="country_from" value="${FLIGHT.from.id}" /> <input
				type="hidden" name="country_to" value="${FLIGHT.to.id}" /> <input
				type="hidden" name="plane" value="${FLIGHT.planeModel.model}" />
			<div class="text-center">
				<fmt:message key="number" />
				<input type="text" class="form-control col-md-4 offset-md-4"
					name="number" value="${FLIGHT.number}" id="flightNumber" />
				<fmt:message key="date" />
				<input class="form-control form-control-lg col-md-4 offset-md-4"
					name="date"
					value="<fmt:formatDate value="${FLIGHT.date.time}" pattern="yyyy-MM-dd"/>" />
				<fmt:message key="price" />
				<input type="text"
					class="form-control form-control-lg col-md-4 offset-md-4"
					name="price" value="${FLIGHT.price }"
					pattern="([0-9]{1,}(.[0-9]{0,2}){0,1}" />
				<fmt:message key="primary_boarding_price" />
				<input type="text"
					class="form-control form-control-lg col-md-4 offset-md-4"
					name="primary_boarding_price"
					value="${FLIGHT.primaryBoardingPrice }"
					pattern="([0-9]{1,}(.|,[0-9]{0,2}){0,1}" />
				<fmt:message key="luggage" />
				<input type="text"
					class="form-control form-control-lg col-md-4 offset-md-4"
					name="luggage" value="${FLIGHT.permittedLuggageWeigth }" />
				<fmt:message key="overweight_luggage_price" />
				<input type="text"
					class="form-control form-control-lg col-md-4 offset-md-4"
					name="overweight_price"
					value="${FLIGHT.priceForEveryKgOverweight }"
					pattern="([0-9]{1,}(.[0-9]{0,2}){0,1}" />
			</div>
			<button type="submit" class="btn btn-light col-md-4 offset-md-4"
				name="command" value="UPDATE_FLIGHT">
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
