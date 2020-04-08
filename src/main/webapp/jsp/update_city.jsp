<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="by.training.karpilovich.lowcost.command.JspParameter"%>
<%@ page import="by.training.karpilovich.lowcost.command.CommandType"%>
<%@ page import="by.training.karpilovich.lowcost.command.Page"%>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<c:set var="style" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${style}/css/bootstrap.min.css" />
<link rel="stylesheet" href="${style}/css/main_page.css" />
<fmt:setLocale value="${pageContext.response.locale}" scope="session" />
</head>

<header>
	<c:set var="page" value="<%=Page.ADD_CITY%>" scope="request" />
	<c:import url="/general/header.jsp" />
</header>

<body>
<p>${ERROR_MESSAGE}</p>
	<fmt:bundle basename="pagecontent" prefix="update_city.input.">
		<form name="input" class="border border-light " method="post">
			<div class="login-form col-md-4 offset-md-4">
				<input type="hidden" name="<%=JspParameter.CITY_ID%>"
					value="${CITY.id}">
				<input type="text" class="form-control"
					name="<%=JspParameter.COUNTRY_NAME%>" 
					value="${CITY.country}" required
					pattern="([А-Я]{1}[А-Яа-я]{1,})" />
				<input type="text"
					class="form-control" name="<%=JspParameter.CITY_NAME%>"
					value="${CITY.name}"
					required pattern="([А-Я]{1}[А-Яа-я]{1,})" />
				<button class="btn btn-primary btn-block " type="submit"
					name="<%=JspParameter.COMMAND%>" value="<%=CommandType.UPDATE_CITY%>">
					<fmt:message key="update_city" />
				</button>
			</div>
		</form>
	</fmt:bundle>

	<script type="text/javascript" src="${style}/js/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="${style}/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${style}/js/popper.min.js"></script>
	<script type="text/javascript" src="${style}/js/main.js"></script>
</body>
</html>
