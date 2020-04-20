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
	<title><fmt:message key="add_place_coefficient" /></title>
</fmt:bundle>
</head>

<header>
	<c:set var="page" value="ADD_PLACE_COEFFICIENT" scope="request" />
	<c:import url="/general/header.jsp" />
</header>

<body>
	<fmt:bundle basename="pagecontent"
		prefix="add_coefficient_input.place.">
		<form name="input" class="border border-light " method="post">
			<div class="login-form col-md-4 offset-md-4">

				<input type="text" class="form-control" name="bound_from"
					value="${BOUND_FROM}" readonly /> <input type="text"
					class="form-control" name="bound_to" value=""
					placeholder="<fmt:message key="bound_to"/>" required
					pattern="[0-9]{1,})" />
				<fmt:message key="max_bound_value" />${MAX_BOUND_VALUE}
				<input type="text" class="form-control" name="value" value=""
					placeholder="<fmt:message key="value"/>" required
					pattern="([0-9]{1,}(.[0-9]){0,1})" />
				<button class="btn btn-primary btn-block " type="submit"
					name="command" value="ADD_PLACE_COEFFICIENT">
					<fmt:message key="add_coefficient" />
				</button>
				<button class="btn btn-primary btn-block " type="submit"
					name="command" value="REDIRECT_TO_FLIGHT_PREVIEW_PAGE">
					<fmt:message key="redirect_to_preview_page" />
				</button>
			</div>
		</form>
	</fmt:bundle>
	<div class="error-code col-md-4 offset-md-4">${ERROR_MESSAGE}</div>
	<script type="text/javascript" src="${style}/js/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="${style}/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${style}/js/popper.min.js"></script>
	<script type="text/javascript" src="${style}/js/main.js"></script>
</body>
</html>
