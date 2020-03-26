<html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<c:set var="style" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${style}/css/bootstrap.min.css" />
<link rel="stylesheet" href="${style}/css/signin.css" />

</head>

<header>
	<c:set var="page_from" value="<%=by.training.karpilovich.lowcost.command.Page.SIGN_IN%>" scope="request"/>
	<c:import url="/general/header.jsp" />
</header>

<body>
	<fmt:bundle basename="pagecontent" prefix="signin.input.">
		<form name="input" class="border border-light " method="post">
			<p class="login-header text-center">
				<fmt:message key="title" />
			</p>
			<div class="login-form col-md-4 offset-md-4">
				<input type="email" name="email" class="form-control"
					placeholder="<fmt:message key="email"/>" value="" required
					pattern="([a-zA-Z_0-9]{1,}@([a-z]{3,7})\.(ru|com|by|net))" /> <input
					type="password" name="password" class="form-control"
					placeholder="<fmt:message key="password"/>" value="" required
					pattern="(\w{5,})" />
				<button class="btn btn-primary btn-block " type="submit"
					name="command" value="sign_in">
					<fmt:message key="command" />
				</button>

				<div class="form-check">
					<input class="form-check-input" type="checkbox" name="memory"
						value="true">
					<fmt:message key="memory" />
				</div>
			</div>
			<div class="login-info text-center">

				<p>
					<a href=""><fmt:message key="register" /></a>
				</p>
				<p>
					<a href=""><fmt:message key="forgot_password" /></a>
				</p>

			</div>
		</form>
	</fmt:bundle>

	<script type="text/javascript" src="${style}/js/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="${style}/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${style}/js/popper.min.js"></script>
	<script type="text/javascript" src="${style}/js/main.js"></script>
</body>
</html>
