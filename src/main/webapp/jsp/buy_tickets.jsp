<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html lang='en'>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<c:set var="style" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${style}/css/bootstrap.min.css" />
<link rel="stylesheet" href="${style}/css/main_page.css" />
<fmt:setLocale value="${pageContext.response.locale}" scope="session" />
<fmt:bundle basename="pagecontent" prefix="title.">
<title><fmt:message key="buy_tickets" /></title>
</fmt:bundle>
</head>

<header>
	<c:set var="page" value="BUY_TICKETS" scope="request" />
	<c:import url="/general/header.jsp" />
</header>

<body>
	<fmt:bundle basename="pagecontent" prefix="tickets.table.">
		<table class="table table-bordered text-center">
			<thead>
				<tr>
					<th scope="col"><fmt:message key="from" /></th>
					<th scope="col"><fmt:message key="to" /></th>
					<th scope="col"><fmt:message key="date" /></th>
					<th scope="col"><fmt:message key="passenger_info" /></th>
					<th scope="col"><fmt:message key="primary_boarding" /></th>
					<th scope="col"><fmt:message key="price"/></th>
					<th scope="col"><fmt:message key="luggage" /></th>
					<th scope="col"></th>
			</thead>
			<tbody>
				<c:forEach var="ticket" items="${TICKETS}">
					<tr>
						<td><c:out value="${ticket.flight.from.name}, ${ticket.flight.from.country}" /></td>
						<td><c:out value="${ticket.flight.to.name}, ${ticket.flight.to.country}" /></td>
						<td><fmt:formatDate value="${ticket.flight.date.time}" /></td>
						<td><c:out value="${ticket.passengerFirstName}"/>
						<c:out value="${ticket.passengerLastName}" />
						<c:out value="${ticket.passengerPassportNumber}" />
						</td>
						<td><c:out value="${ticket.primaryBoargingRight}" /></td>
						<td><fmt:formatNumber value="${ticket.price}" /></td>
						<td><c:out value="${ticket.luggageQuantity}"/>/<fmt:formatNumber value="${ticket.overweightLuggagePrice}"/></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
			<form name="action" method="post">
								<button class="btn btn-primary" type="submit"
									name="command"
									value="BUY_TICKET">
									<fmt:message key="buy" />
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