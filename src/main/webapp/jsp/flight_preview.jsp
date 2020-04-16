<!DOCTYPE html>

<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html lang='en'>

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
				${FLIGHT.planeModel.model}
				<fmt:message key="place_having" />
				${FLIGHT.planeModel.placeQuantity}
				<fmt:message key="place_quantity" />
				<fmt:message key="luggage" />
				${FLIGHT.permittedLuggageWeigth}
				<fmt:message key="overweight_luggage_price" />
				${FLIGHT.priceForEveryKgOverweight}
			</p>
		</fmt:bundle>

		<fmt:bundle basename="pagecontent" prefix="flight_preview.table.">
			<form name="input" method="post">
				<table class="table table-bordered">
					<thead>
						<tr>
							<th scope="col"><fmt:message key="from" /></th>
							<th scope="col"><fmt:message key="to" /></th>
							<th scope="col"><fmt:message key="value" /></th>
							<th scope="col"><fmt:message key="action" /></th>
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
							<td colspan="3"><button class="btn btn-primary btn-block"
									type="submit" name="command"
									value="REDIRECT_TO_ADD_PLACE_COEFFICIENT_PAGE">
									<fmt:message key="add_place_coefficient" />
								</button></td>
						</tr>
						<tr>
							<td colspan="3"><fmt:message
									key="add_date_coefficient.title" /></td>
						</tr>
						<c:forEach var="coefficient" items="${DATE_COEFFICIENT}">
							<tr>
								<td>${coefficient.from}"/></td>
								<td>${coefficient.to}"/></td>
								<td>${coefficient.value}</td>
							</tr>
						</c:forEach>
						<tr>
							<td colspan="3"><button class="btn btn-primary btn-block"
									type="submit" name="command"
									value="REDIRECT_TO_ADD_DATE_COEFFICIENT_PAGE">
									<fmt:message key="add_date_coefficient" />
								</button></td>
						</tr>
					</tbody>
				</table>
				<button class="btn btn-primary btn-block" type="submit"
					name="command"
					value="ADD_FLIGHT">
					<fmt:message key="add_flight" />
				</button>
			</form>
		</fmt:bundle>
	</div>
	<script type="text/javascript" src="${style}/js/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="${style}/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${style}/js/popper.min.js"></script>
	<script type="text/javascript" src="${style}/js/main.js"></script>
</body>
</html>
