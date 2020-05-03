<!DOCTYPE html >
<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html lang='ru'>

<head>
<meta charset="UTF-8" http-equiv="Content-Type"
	content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<c:set var="style" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${style}/css/bootstrap.min.css" />
<link rel="stylesheet" href="${style}/css/main_page.css" />
<fmt:setLocale value="${pageContext.response.locale}" scope="session" />
<fmt:bundle basename="pagecontent" prefix="title.">
	<title><fmt:message key="error_page" /></title>
</fmt:bundle>
</head>
<body>
	<div class="error-code text-center">
		<h1>Error</h1>
		<h1>${pageContext.errorData.statusCode}</h1>
	</div>
	<div class="error-code col-md-4 offset-md-4">${ERROR_MESSAGE}</div>
	<script type="text/javascript" src="${style}/js/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="${style}/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${style}/js/popper.min.js"></script>
	<script type="text/javascript" src="${style}/js/main.js"></script>
</body>
</html>