<!DOCTYPE html">
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
	<title><fmt:message key="all_flights" /></title>
</fmt:bundle>
</head>

<header>
	<c:set var="page" value="SHOW_FLIGHTS" scope="request" />
	<c:import url="/general/header.jsp" />
</header>

<body>
	<fmt:bundle basename="pagecontent" prefix="all_flights.table.">
		<table class="table table-bordered text-center">
			<thead>
				<tr>
					<th scope="col"><fmt:message key="number" /></th>
					<th scope="col"><fmt:message key="date" /></th>
					<th scope="col"><fmt:message key="city_from" /></th>
					<th scope="col"><fmt:message key="city_to" /></th>
					<th scope="col"><fmt:message key="price" /></th>
					<th scope="col"><fmt:message key="plane" /></th>
					<th scope="col"><fmt:message key="places" /></th>
					<th scope="col"><fmt:message key="luggage" /></th>
					<th scope="col"><fmt:message key="overweight_price" /></th>
					<c:if test="${USER_ROLE == 'USER' || USER_ROLE == 'ADMIN' }">
						<th scope="col"><fmt:message key="action" /></th>
					</c:if>
			</thead>
			<tbody>
				<c:forEach var="flight" items="${FLIGHTS}">

					<tr>
						<td><c:out value="${flight.number}" /></td>
						<td><fmt:formatDate value="${flight.date.time}" />
						<td><c:out
								value="${flight.from.country}, ${flight.from.name}" /></td>
						<td><c:out value="${flight.to.country}, ${flight.to.name}" /></td>
						<td><fmt:formatNumber value="${flight.price}" /></td>
						<td><c:out value="${flight.planeModel.model}" /></td>
						<td><c:out
								value="${flight.availablePlaceQuantity} / ${flight.planeModel.placeQuantity }" />
						<td><c:out value="${flight.permittedLuggageWeigth}" /></td>
						<td><fmt:formatNumber
								value="${flight.priceForEveryKgOverweight}" /></td>

						<c:choose>
						<c:when test="${USER_ROLE == 'ADMIN'}">
							<td>
								<form method="post" name="update_flight">
									<input type="hidden" name="from_page" value="${page}" /> <input
										type="hidden" name="flight_id" value="${flight.id }" />
									<button type="submit" class="btn btn-primary btn-sm"
										name="command" value="REDIRECT_TO_UPDATE_FLIGHT_PAGE">
										<fmt:message key="update_button" />
									</button>
									<button type="submit" class="btn btn-primary btn-sm"
										name="command" value="DELETE_FLIGHT">
										<fmt:message key="delete_button" />
									</button>
									<button type="submit" class="btn btn-primary btn-sm"
										name="command" value="SHOW_SOLD_TICKETS">
										<fmt:message key="sold_ticket" />
									</button>
								</form>
							</td>
						</c:when>

						<c:otherwise>
							<td>
								<form name="action" method="post">
									<input type="hidden" name="from_page" value="${page}" /> <input
										type=hidden name=flight_id value=${flight.id } />
									<button class="btn btn-primary" type="submit" name="command"
										value="REDIRECT_TO_CREATE_TICKET_PAGE">
										<fmt:message key="buy_ticket" />
									</button>
								</form>
							</td>
						</c:otherwise>
						</c:choose>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</fmt:bundle>
	<div class="error-code col-md-4 offset-md-4">${ERROR_MESSAGE}</div>
	<script type="text/javascript" src="${style}/js/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="${style}/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${style}/js/popper.min.js"></script>
	<script type="text/javascript" src="${style}/js/main.js"></script>
</body>
</html>