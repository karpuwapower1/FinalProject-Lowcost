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
<link rel="stylesheet" href="${style}/css/main_page.css" />
<%-- <link rel="stylesheet" href="${style}/font/Sen/Sen-bold.ttf" />
<link rel="stylesheet" href="${style}/font/Roboto/Roboto-regular.ttf" /> --%>
</head>

<header>
	<nav class="navbar navbar-expand-sm navbar-light">
		<div class="navbar-brand">
			<form method="post" name="default">
				<input type="hidden" name="to_page" value="default" />
				<button type="submit" class="btn btn-link " name="command"
					value="redirect">
					<h4>
						LOWCOST<br>AIRLINES
					</h4>
				</button>
			</form>
		</div>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarSupportedContent"
			aria-controls="navbarSupportedContent" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div class="collapse navbar-collapse">
			<div class="navbar-nav mr-auto ">
				<div class="nav-item dropdown">
					<button type="button" class="nav-link dropdown-toggle btn btn-link"
						data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">${locale}</button>


					<div class="dropdown-menu">
						<form method="post" name="language">
							<input type="hidden" name="command" value="changeLanguage" />
							<button type="submit" class="btn btn-link" name="language"
								value="ru">Ru</button>
							<button type="submit" class="btn btn-link" name="language"
								value="en">En</button>
						</form>
					</div>
				</div>
			</div>
			<div class="navbar-nav ml-auto">
				<c:if
					test="${!page.equals('signin') && !page.equals('signup') && role == 'GUEST'}">
					<div class="nav-item">
						<form method="post" name="signup">
							<input type="hidden" name="to_page" value="signup" /> <input
								type="hidden" name="page_from" value="${page}" /> 
							<button type="submit" class="btn btn-link" name="command"
								value="redirect">Signup</button>
						</form>
					</div>

					<div class="nav-item">
						<form method="post" name="signin">
							<input type="hidden" name="to_page" value="signin" /> <input
								type="hidden" name="page_from" value="${page}" /> 
							<button type="submit" class="btn btn-link" name="command"
								value="redirect">Signin</button>
						</form>
					</div>
				</c:if>
				<c:if
					test="${!page.equals('signin') && !page.equals('signup') && role!= 'GUEST'}">
					<div class="nav-item col-4">
						<form name="signout" method="post">
						<%-- <input
								type="hidden" name="page_from" value="${page_from}" /> --%>
							<button type="submit" class="btn btn-link" name="command"
								value="signout">Signout</button>
						</form>
					</div>
				</c:if>
			</div>
		</div>
	</nav>
</header>
</html>
