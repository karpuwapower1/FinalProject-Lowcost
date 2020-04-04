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





				<div class="menu">
					<c:if test="${role != 'GUEST'}">
						<div class="nav-item dropdown">
							<button type="button"
								class="nav-link dropdown-toggle btn btn-link btn-lg"
								data-toggle="dropdown" aria-haspopup="true"
								aria-expanded="false">Menu</button>

							<div
								class="dropdown-menu dropdown-menu-right mt-0 mr-0 ml-0 mb-0 pt-0 pb-0 pr-1 pl-0">
								<c:choose>
									<c:when test="${role == 'ADMIN'}">
										<form method="post" name="add_city">
											<input type="hidden" name="to_page" value="add_city" /> <input
												type="hidden" name="from_page" value="${page }" />
											<button type="submit"
												class="dropdown-item pt-0 pb-0 pr-1 pl-0" name="command"
												value="redirect">Add city</button>
										</form>
										<form method="post" name="update_city">
											<input type="hidden" name="to_page" value="update_city" /> <input
												type="hidden" name="from_page" value="${page }" />
											<button type="submit"
												class="dropdown-item pt-0 pb-0 pr-1 pl-0" name="command"
												value="redirect">Update city</button>
										</form>
										<form method="post" name="update_city">
											<input type="hidden" name="to_page" value="delete_city" /> <input
												type="hidden" name="from_page" value="${page }" />
											<button type="submit"
												class="dropdown-item pt-0 pb-0 pr-1 pl-0" name="command"
												value="redirect">Delete city</button>
										</form>
										<form method="post" name="show_all_city">
											<input type="hidden" name="to_page" value="all_city" /> <input
												type="hidden" name="from_page" value="${page }" />
											<button type="submit"
												class="dropdown-item pt-0 pb-0 pr-1 pl-0" name="command"
												value="redirect">Show all cities</button>
										</form>
										
										<form method="post" name="add_flight">
											<input type="hidden" name="to_page" value="add_flight" /> <input
												type="hidden" name="from_page" value="${page }" />
											<button type="submit"
												class="dropdown-item pt-0 pb-0 pr-1 pl-0" name="command"
												value="redirect">Add flight</button>
										</form>
										<form method="post" name="update_flight">
											<input type="hidden" name="to_page" value="update_flight" /> <input
												type="hidden" name="from_page" value="${page }" />
											<button type="submit"
												class="dropdown-item pt-0 pb-0 pr-1 pl-0" name="command"
												value="redirect">Update flight</button>
										</form>
										<form method="post" name="delete_flight">
											<input type="hidden" name="to_page" value="delete_flight" /> <input
												type="hidden" name="from_page" value="${page }" />
											<button type="submit"
												class="dropdown-item pt-0 pb-0 pr-1 pl-0" name="command"
												value="redirect">Delete flight</button>
										</form>
										<form method="post" name="show_all_flight">
											<input type="hidden" name="to_page" value="all_flight" /> <input
												type="hidden" name="from_page" value="${page }" />
											<button type="submit"
												class="dropdown-item pt-0 pb-0 pr-1 pl-0" name="command"
												value="redirect">Show all flight</button>
										</form>
									</c:when>

									<c:otherwise>

									</c:otherwise>
								</c:choose>
							</div>

						</div>
					</c:if>
				</div>

			</div>
			<div class="navbar-nav ml-auto">
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
				<c:if
					test="${!page.equals('sign_in') && !page.equals('sign_up') && role == 'GUEST'}">
					<div class="nav-item">
						<form method="post" name="sign_up">
							<input type="hidden" name="to_page" value="sign_up" /> <input
								type="hidden" name="page_from" value="${page}" />
							<button type="submit" class="btn btn-link" name="command"
								value="redirect">Signup</button>
						</form>
					</div>


					<div class="nav-item">
						<form method="post" name="sign_in">
							<input type="hidden" name="to_page" value="sign_in" /> <input
								type="hidden" name="page_from" value="${page}" />
							<button type="submit" class="btn btn-link" name="command"
								value="redirect">Signin</button>
						</form>
					</div>
				</c:if>
				<c:if
					test="${!page.equals('sign_in') && !page.equals('sign_up') && role!= 'GUEST'}">
					<div class="nav-item">
						<form name="sign_out" method="post">
							<%-- <input
								type="hidden" name="page_from" value="${page_from}" /> --%>
							<button type="submit" class="btn btn-link" name="command"
								value="sign_out">Signout</button>
						</form>
					</div>
				</c:if>


			</div>

		</div>


	</nav>
</header>
</html>
