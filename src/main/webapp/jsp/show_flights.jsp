<!DOCTYPE html">
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
	<title><fmt:message key="all_flights" /></title>
</fmt:bundle>
</head>

<header>
	<c:set var="page" value="ALL_CITIES" scope="request" />
	<c:import url="/general/header.jsp" />
</header>

<body>
	<p>${ERROR_MESSAGE}</p>
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
					<th scope="col"><fmt:message key="action" /></th>
			</thead>
			<tbody>
				<c:forEach var="flight" items="${FLIGHTS}">
					<input type=hidden name=flight_id value=${flight.id } />
					<tr>
						<td><c:out value="${flight.number}" /></td>
						<td><fmt:formatDate value="${flight.date.time}" />
						<td><c:out
								value="${flight.from.country}, ${flight.from.name}" /></td>
						<td><c:out value="${flight.to.country}, ${flight.to.name}" /></td>
						<td><c:out value="${flight.price}" /></td>
						<td><c:out value="${flight.planeModel.model}" /></td>
						<td><c:out
								value="${flight.availablePlaceQuantity} / ${flight.planeModel.placeQuantity }" />
						<td><c:out value="${flight.permittedLuggageWeigth}" /></td>
						<td><c:out value="${flight.priceForEveryKgOverweight}" /></td>
						<td>
							<form name="action" method="post">
								<input type="hidden" name="city" value="${city.name}"> <input
									type="hidden" name="country" value="${city.country}"> <input
									type="hidden" name="city_id" value="${city.id}"> <input
									type="hidden" name="to_page" value="update_city">
								<button class="btn btn-primary" type="submit" name="command"
									value="REDIRECT_TO_UPDATE_FLIGHT_PAGE">
									<fmt:message key="update_button" />
								</button>
								<button class="btn btn-primary" type="submit" name="command"
									value="DELETE_FLIGHT">
									<fmt:message key="delete_button" />
								</button>
							</form>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</fmt:bundle>

	<script type="text/javascript" src="${style}/js/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="${style}/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${style}/js/popper.min.js"></script>
	<script type="text/javascript" src="${style}/js/main.js"></script>
</body>
</html>