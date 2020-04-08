<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="by.training.karpilovich.lowcost.command.JspParameter"%>
<%@ page import="by.training.karpilovich.lowcost.command.CommandType"%>
<%@ page import="by.training.karpilovich.lowcost.command.Page"%>

<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<c:set var="style" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${style}/css/bootstrap.min.css" />
<link rel="stylesheet" href="${style}/css/main_page.css" />
<fmt:setLocale value="${pageContext.response.locale}" scope="session" />
</head>

<header>
	<c:set var="page" value="<%=Page.ALL_CITIES%>" scope="request" />
	<c:import url="/general/header.jsp" />
</header>

<body>
	<p>${ERROR_MESSAGE}</p>
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
							<form name="action" method="post">
								<input type="hidden" name="<%=JspParameter.CITY_NAME%>"
									value="${city.name}"> <input type="hidden"
									name="<%=JspParameter.COUNTRY_NAME%>" value="${city.country}">
								<input type="hidden" name="<%=JspParameter.CITY_ID%>"
									value="${city.id}">
								<input type="hidden" name="<%=JspParameter.TO_PAGE%>"
									value="<%=Page.UPDATE_CITY%>">
								<button class="btn btn-primary" type="submit"
									name="<%=JspParameter.COMMAND%>" value="<%=CommandType.REDIRECT_TO_UPDATE_CITY_PAGE%>">
									<fmt:message key="update_button" />
								</button>
								<button class="btn btn-primary" type="submit"
									name="<%=JspParameter.COMMAND%>"
									value="<%=CommandType.DELETE_CITY%>">
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