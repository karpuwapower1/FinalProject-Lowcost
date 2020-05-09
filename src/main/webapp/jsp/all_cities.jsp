<!DOCTYPE html>
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
	<title><fmt:message key="all_cities" /></title>
</fmt:bundle>
</head>

<header>
	<c:set var="page" value="ALL_CITIES" scope="request" />
	<c:import url="/general/header.jsp" />
</header>

<body>
	<fmt:bundle basename="pagecontent" prefix="all_cities.table.">
		<table class="table table-bordered text-center">
			<thead>
				<tr>
					<th scope="col"><fmt:message key="city" /></th>
					<th scope="col"><fmt:message key="country" /></th>
					<th scope="col"><fmt:message key="action" /></th>
			</thead>
			<tbody>
				<c:forEach var="city" items="${CITIES}">
					<tr>
						<td><c:out value="${city.name}" /></td>
						<td><c:out value="${city.country}" /></td>
						<td>
							<form action="lowcost" name="action" method="post">
							<input type="hidden" name="from_page" value="${page}" /> 
								<input type="hidden" name="city_id" value="${city.id}">
								<input type="hidden" name="to_page" value="UPDATE_CITY">
								<button class="btn btn-primary" type="submit" name="command"
									value="REDIRECT_TO_UPDATE_CITY_PAGE">
									<fmt:message key="update_button" />
								</button>
								<button class="btn btn-primary" type="submit" name="command"
									value="DELETE_CITY">
									<fmt:message key="delete_button" />
								</button>
							</form>
						</td>
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