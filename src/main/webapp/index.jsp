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
	<title><fmt:message key="main_page" /></title>
</fmt:bundle>
</head>

<header>
	<c:set var="page" value="DEFAULT" scope="request" />
	<c:import url="/general/header.jsp" />
</header>

<body>
	<fmt:bundle basename="pagecontent" prefix="index.select.">
		<div class="container">
			<div class="grid">
				<form name="select" method="post">
					<div class="row">
						<select class="custom-select  custom-select-lg col-md-3"
							name="country_from" required>
							<option selected><fmt:message key="departureCounty" /></option>
							<c:forEach var="city" items="${CITIES }">
								<option value="${city.id }">${city.country},${ city.name}</option>
							</c:forEach>
						</select> <select class="custom-select  custom-select-lg col-md-3"
							name="country_to" required>
							<option selected><fmt:message key="destinationCountry" /></option>
							<c:forEach var="city" items="${CITIES }">
								<option value="${city.id }">${city.country},${ city.name}</option>
							</c:forEach>
						</select> <input class="form-control form-control-lg col-md-3"
							placeholder="<fmt:message key="departureDate"/>" required
							class="textbox-n" type="text" onfocus="(this.type='date')"
							onblur="(this.type='text')" name="date" value="" /> <input
							type="text" class="form-control form-control-lg col-md-2"
							name="quantity" value=""
							placeholder="<fmt:message key="quantity"/>" required
							pattern="[0-9]" />
						<button type="submit" class="btn btn-light col-md-1"
							name="command" value="search_flight">
							<fmt:message key="command" />
						</button>
					</div>
				</form>
			</div>
		</div>
	</fmt:bundle>
	<div class="error-code col-md-4 offset-md-4">${ERROR_MESSAGE}</div>
	<script type="text/javascript" src="${style}/js/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="${style}/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${style}/js/popper.min.js"></script>
	<script type="text/javascript" src="${style}/js/main.js"></script>
</body>
</html>
