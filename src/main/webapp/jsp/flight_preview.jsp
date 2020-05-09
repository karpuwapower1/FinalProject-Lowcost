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
<fmt:bundle basename="pagecontent" prefix="title.">
	<title><fmt:message key="flight_preview" /></title>
</fmt:bundle>
</head>


<header>
	<c:set var="page" value="FLIGHT_PREVIEW" scope="request" />
	<c:import url="/general/header.jsp" />
</header>

<body>
	<div class="container">
		<fmt:bundle basename="pagecontent" prefix="flight_preview.intro.">
			<p>
				<fmt:message key="number" />
				${FLIGHT.number}
				<fmt:message key="from" />
				${FLIGHT.from.country}, ${FLIGHT.from.name}
				<fmt:message key="date" />
				<fmt:formatDate value="${FLIGHT.date.time}" type="both" />
				<fmt:message key="to" />
				${FLIGHT.to.country}, ${FLIGHT.to.name}
				<fmt:message key="plane" />
				<fmt:message key="price" />
				<fmt:formatNumber value="${FLIGHT.price}" />
				${FLIGHT.planeModel.model}
				<fmt:message key="place_having" />
				${FLIGHT.planeModel.placeQuantity}
				<fmt:message key="place_quantity" />
				<fmt:message key="luggage" />
				${FLIGHT.permittedLuggageWeigth}
				<fmt:message key="overweight_luggage_price" />
				<fmt:formatNumber value="${FLIGHT.priceForEveryKgOverweight}" />
			</p>
		</fmt:bundle>

		<fmt:bundle basename="pagecontent" prefix="flight_preview.table.">
			<form action="lowcost" name="input" method="post">
				<input type="hidden" name="from_page" value="${page}" />
				<table class="table table-bordered">
					<thead>
						<tr>
							<th scope="col"><fmt:message key="from" /></th>
							<th scope="col"><fmt:message key="to" /></th>
							<th scope="col"><fmt:message key="value" /></th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td colspan="3"><fmt:message
									key="add_place_coefficient.title" /></td>
						</tr>
						<c:forEach var="coefficient" items="${PLACE_COEFFICIENT }">
							<tr>
								<td>${coefficient.from}</td>
								<td>${coefficient.to}</td>
								<td>${coefficient.value}</td>
							</tr>
						</c:forEach>
						<tr>
							<td colspan="3">
								<div class="login-form col-md-4 offset-md-4">
									<button class="btn btn-primary btn-block"
										type="submit" name="command"
										value="REDIRECT_TO_ADD_PLACE_COEFFICIENT_PAGE">
										<fmt:message key="add_place_coefficient" />
									</button>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="3"><fmt:message
									key="add_date_coefficient.title" /></td>
						</tr>
						<c:forEach var="coefficient" items="${DATE_COEFFICIENT}">
							<tr>
								<td><fmt:formatDate value="${coefficient.from.time}" /></td>
								<td><fmt:formatDate value="${coefficient.to.time}" /></td>
								<td>${coefficient.value}</td>
							</tr>
						</c:forEach>
						<tr>
							<td colspan="3">
								<div class="login-form col-md-4 offset-md-4">
									<button class="btn btn-primary btn-block" type="submit"
										name="command" value="REDIRECT_TO_ADD_DATE_COEFFICIENT_PAGE">
										<fmt:message key="add_date_coefficient" />
									</button>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
				<div class="login-form col-md-4 offset-md-4">
					<button class="btn btn-primary btn-block" type="submit"
						name="command" value="ADD_FLIGHT">
						<fmt:message key="add_flight" />
					</button>

					<button class="btn btn-primary btn-block" type="submit"
						name="command" value="CANCEL_FLIGHT_ADDING">
						<fmt:message key="cancel" />
					</button>
				</div>
			</form>
		</fmt:bundle>
	</div>
	<div class="error-code col-md-4 offset-md-4">${ERROR_MESSAGE}</div>
	<script type="text/javascript" src="${style}/js/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="${style}/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${style}/js/popper.min.js"></script>
	<script type="text/javascript" src="${style}/js/main.js"></script>
</body>
</html>
