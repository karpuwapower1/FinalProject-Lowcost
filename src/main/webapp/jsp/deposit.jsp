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
	<title><fmt:message key="deposit" /></title>
</fmt:bundle>
</head>

<header>
	<c:set var="page" value="DEPOSIT" scope="request" />
	<c:import url="/general/header.jsp" />
</header>

<body>
	<fmt:bundle basename="pagecontent" prefix="deposit.input.">
		<form name="input" class="border border-light " method="post">
			<div class="login-form col-md-4 offset-md-4">
				<input type="text" class="form-control"
					placeholder="<fmt:message key="current_amount"/>, <fmt:formatNumber value="${user.balanceAmount}"/>"
					readonly /> <input type="text" class="form-control" name="amount"
					value="" placeholder="<fmt:message key="deposit_value"/>" required
					pattern="(([0-9]{1,})(.[0-9]{0,}))" />
				<button class="btn btn-primary btn-block " type="submit"
					name="command" value="DEPOSIT">
					<fmt:message key="submit" />
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
