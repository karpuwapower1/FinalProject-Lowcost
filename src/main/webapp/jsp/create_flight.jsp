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
<link rel="stylesheet" href="${style}/css/create_flight.css" />
<fmt:setLocale value="${pageContext.response.locale}" scope="session" />
<fmt:setLocale value="${pageContext.response.locale}" scope="session" />
<fmt:bundle basename="pagecontent" prefix="title.">
<title><fmt:message key="create_flight" /></title>
</fmt:bundle>
</head>

<header>
	<c:set var="page"
		value="CREATE_FLIGHT"
		scope="request" />
	<c:import url="/general/header.jsp" />
</header>

<body>
	<fmt:bundle basename="pagecontent" prefix="create_flight.">
		<form name="select" method="post">
			<input type="text" class="form-control col-md-4 offset-md-4"
				name="number"
				value="" placeholder="<fmt:message key="number"/>" required /> <select
				class="custom-select custom-select-lg col-md-4 offset-md-4"
				name="country_from"
				required>
				<option selected>
					<c:if test="${COUNTRY_FROM == null}">
						<fmt:message key="choose_from" />
					</c:if>
					<c:if test="${COUNTRY_FROM != null}">
				${COUNTRY_FROM}
				</c:if>
				</option>

				<c:forEach var="city" items="${CITIES }">
					<option value="${city.id }">${city.country},${city.name}</option>
				</c:forEach>
			</select> <select class="custom-select  custom-select-lg col-md-4 offset-md-4"
				name="country_to"
				required>
				<option selected><fmt:message key="choose_to" /></option>
				<c:forEach var="city" items="${CITIES }">
					<option value="${city.id }">${city.country},${ city.name}</option>
				</c:forEach>
			</select> <select class="custom-select  custom-select-lg col-md-4 offset-md-4"
				name="plane" required>
				<option selected><fmt:message key="choose_plane" /></option>
				<c:forEach var="plane" items="${PLANES}">
					<option value="${plane.model}">${plane.model},
						${plane.placeQuantity}
						<fmt:message key="place_quantity" /></option>
				</c:forEach>
			</select> <input class="form-control form-control-lg col-md-4 offset-md-4"
				placeholder="<fmt:message key="choose_date"/>" required
				class="textbox-n" type="text" onfocus="(this.type='date')"
				onblur="(this.type='text')"
				name="date"
				value="" />
			<input type="text"
				class="form-control form-control-lg col-md-4 offset-md-4"
				name="price"
				value=""
				placeholder="<fmt:message key="price"/>" required
				pattern="([0-9]{1,}(.[0-9]{0,2}){0,1}" /> 
			<input type="text"
				class="form-control form-control-lg col-md-4 offset-md-4"
				name="primary_boarding_price"
				value=""
				placeholder="<fmt:message key="primary_boarding_price"/>" required
				pattern="([0-9]{1,}(.|,[0-9]{0,2}){0,1}" />
			<input type="text"
				class="form-control form-control-lg col-md-4 offset-md-4"
				name="luggage"
				value="" placeholder="<fmt:message key="luggage"/>" required
				               />
		<input type="text"
				class="form-control form-control-lg col-md-4 offset-md-4"
				name="overweight_price"
				value=""placeholder="<fmt:message key="price"/>" required
				pattern="([0-9]{1,}(.[0-9]{0,2}){0,1}" /> 
			<button type="submit" class="btn btn-light col-md-4 offset-md-4"
				name="command"
				value="CREATE_FLIGHT">
				<fmt:message key="command" />
			</button>
		</form>
	</fmt:bundle>
	<script type="text/javascript" src="${style}/js/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="${style}/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${style}/js/popper.min.js"></script>
	<script type="text/javascript" src="${style}/js/main.js"></script>
</body>
</html>
