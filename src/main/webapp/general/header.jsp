<!DOCTYPE html>
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
</head>

<header>
	<nav class="navbar navbar-expand-sm navbar-light">
		<div class="navbar-brand">
			<form action="lowcost" method="post" name="default">
				<input type="hidden" name="to_page" value="DEFAULT">
				<button type="submit" class="btn btn-link " name="command"
					value="REDIRECT">
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
					<c:if test="${USER_ROLE != 'GUEST'}">
						<div class="nav-item dropdown">
							<fmt:bundle basename="pagecontent" prefix="header.">
								<button type="button"
									class="nav-link dropdown-toggle btn btn-link btn-lg"
									data-toggle="dropdown" aria-haspopup="true"
									aria-expanded="false">
									<fmt:message key="menu" />
								</button>
							</fmt:bundle>
							<div
								class="dropdown-menu dropdown-menu-right mt-0 mr-0 ml-0 mb-0 pt-0 pb-0 pr-1 pl-0">
								<fmt:bundle basename="pagecontent" prefix="header.menu.">
									<c:choose>
										<c:when test="${USER_ROLE == 'ADMIN'}">
											<form action="lowcost" method="post" name="add_city">
												<input type="hidden" name="to_page" value="ADD_CITY" /> <input
													type="hidden" name="from_page" value="${page}" />
												<button type="submit"
													class="dropdown-item pt-0 pb-0 pr-1 pl-0" name="command"
													value="REDIRECT">
													<fmt:message key="admin.add_city" />
												</button>
											</form>

											<form action="lowcost" method="post" name="show_all_cities">
												<input type="hidden" name="to_page" value="ALL_CITIES" /> <input
													type="hidden" name="from_page" value="${page}" />
												<button type="submit"
													class="dropdown-item pt-0 pb-0 pr-1 pl-0" name="command"
													value="SHOW_ALL_CITIES">
													<fmt:message key="admin.show_all_cities" />
												</button>
											</form>

											<form action="lowcost" method="post" name="add_flight">
												<input type="hidden" name="to_page" value="add_flight" /> <input
													type="hidden" name="from_page" value="${page }" />
												<button type="submit"
													class="dropdown-item pt-0 pb-0 pr-1 pl-0" name="command"
													value="REDIRECT_TO_CREATE_FLIGTH_PAGE">
													<fmt:message key="admin.add_flight" />
												</button>
											</form>

											<form action="lowcost" method="post" name="show_all_flight">
												<input type="hidden" name="to_page" value="all_flight" /> <input
													type="hidden" name="from_page" value="${page }" />
												<button type="submit"
													class="dropdown-item pt-0 pb-0 pr-1 pl-0" name="command"
													value="SHOW_ALL_FLIGHTS">
													<fmt:message key="admin.show_all_flights" />
												</button>
											</form>

											<form action="lowcost" method="post" name="show_next_twenty_four_hours_flights">
											 <input type="hidden" name="from_page" value="${page }" />
												<button type="submit"
													class="dropdown-item pt-0 pb-0 pr-1 pl-0" name="command"
													value="SHOW_NEXT_TWENTY_FOUR_HOURS_FLIGHTS">
													<fmt:message key="admin.show_next_twenty_four_hours_flights" />
												</button>
											</form>
											
											<form action="lowcost" method="post" name="show_flights_between_dates">
											 <input type="hidden" name="from_page" value="${page }" />
											 <input type="hidden" name="to_page" value="CHOOSE_DATE_BOUNDS" />
												<button type="submit"
													class="dropdown-item pt-0 pb-0 pr-1 pl-0" name="command"
													value="REDIRECT">
													<fmt:message key="admin.show_flights_between_dates" />
												</button>
											</form>
											
												<form action="lowcost" method="post" name="show_all_planes">
											 <input type="hidden" name="from_page" value="${page }" />
												<button type="submit"
													class="dropdown-item pt-0 pb-0 pr-1 pl-0" name="command"
													value="SHOW_ALL_PLANES">
													<fmt:message key="admin.show_planes" />
												</button>
											</form>
											
											<form action="lowcost" method="post" name="add_plane">
											 <input type="hidden" name="from_page" value="${page }" />
											 <input type="hidden" name="to_page" value="ADD_PLANE" />
												<button type="submit"
													class="dropdown-item pt-0 pb-0 pr-1 pl-0" name="command"
													value="REDIRECT">
													<fmt:message key="admin.add_plane" />
												</button>
											</form>
										</c:when>

										<c:otherwise>
											<form action="lowcost" method="post" name="deposit">
												<input type="hidden" name="to_page" value="DEPOSIT" /> <input
													type="hidden" name="from_page" value="${page }" />
												<button type="submit"
													class="dropdown-item pt-0 pb-0 pr-1 pl-0" name="command"
													value="REDIRECT">
													<fmt:message key="user.deposit" />
												</button>
											</form>
											<form action="lowcost" method="post" name="show_all_flight">
												<input type="hidden" name="to_page" value="show_ticket" />
												<input type="hidden" name="from_page" value="${page }" />
												<button type="submit"
													class="dropdown-item pt-0 pb-0 pr-1 pl-0" name="command"
													value="SHOW_ALL_TICKET">
													<fmt:message key="user.show_all_tickets" />
												</button>
											</form>
											
											<form action="lowcost" method="post" name="delete_user">
												<input type="hidden" name="to_page" value="DELETE_USER" />
												<input type="hidden" name="from_page" value="${page }" />
												<button type="submit"
													class="dropdown-item pt-0 pb-0 pr-1 pl-0" name="command"
													value="REDIRECT">
													<fmt:message key="user.delete_user" />
												</button>
											</form>
											
											<form action="lowcost" method="post" name="update_password">
												<input type="hidden" name="to_page" value="CHANGE_PASSWORD" />
												<input type="hidden" name="from_page" value="${page }" />
												<button type="submit"
													class="dropdown-item pt-0 pb-0 pr-1 pl-0" name="command"
													value="REDIRECT">
													<fmt:message key="user.update_password" />
												</button>
											</form>
										</c:otherwise>
									</c:choose>
								</fmt:bundle>
							</div>

						</div>
					</c:if>

					<c:if test="${USER_ROLE == 'USER'}">
						<div class="nav-item">
							<fmt:bundle basename="pagecontent">
								<fmt:message key="header.user.available_resources" /> = <fmt:formatNumber
									value="${USER.balanceAmount}" />
							</fmt:bundle>
						</div>
					</c:if>

				</div>
			</div>

			<c:if test="${page.equals('SHOW_FLIGHTS') }">
				<div class="nav-item dropdown">
					<fmt:bundle basename="pagecontent" prefix="header.">
						<button type="button"
							class="nav-link dropdown-toggle btn btn-link btn-lg"
							data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							<fmt:message key="sort_flight" />
						</button>
					</fmt:bundle>
					<div
						class="dropdown-menu dropdown-menu-right mt-0 mr-0 ml-0 mb-0 pt-0 pb-0 pr-1 pl-0">
						<fmt:bundle basename="pagecontent" prefix="header.sort_flights.">
							<form action="lowcost" method="post" name="sort_flight">
								<input type="hidden" name="from_page" value="${page}" />
								<button type="submit" class="dropdown-item pt-0 pb-0 pr-1 pl-0"
									name="command" value="SORT_FLIGHTS_BY_TICKET_PRICE">
									<fmt:message key="sort_flights_by_ticket_price" />
								</button>
							</form>
							<form action="lowcost" method="post" name="sort_flight">
								<input type="hidden" name="from_page" value="${page}" />
								<button type="submit" class="dropdown-item pt-0 pb-0 pr-1 pl-0"
									name="command" value="SORT_FLIGHTS_BY_DEPARTURE_DATE">
									<fmt:message key="sort_flights_by_departure_date" />
								</button>
							</form>
						</fmt:bundle>
					</div>
				</div>
			</c:if>

			<fmt:bundle basename="pagecontent" prefix="header.">
				<div class="navbar-nav ml-auto">
					<div class="nav-item dropdown">
						<button type="button"
							class="nav-link dropdown-toggle btn btn-link"
							data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							<fmt:message key="language" />
						</button>
						<div class="dropdown-menu">
							<form action="lowcost" method="post" name="language">
								<input type="hidden" name="command" value="CHANGE_LANGUAGE">
								<input type="hidden" name="from_page" value="${page}" />
								<button type="submit" class="btn btn-link" name="language"
									value="RU">
									<fmt:message key="language.ru" />
								</button>
								<button type="submit" class="btn btn-link" name="language"
									value="EN">
									<fmt:message key="language.en" />
								</button>
							</form>
						</div>
					</div>

					<c:if
						test="${page != 'SIGN_IN' && page != 'SIGN_UP' && USER_ROLE == 'GUEST'}">
						<div class="nav-item">
							<form action="lowcost" method="post" name="sign_up">
								<input type="hidden" name="to_page" value="sign_up" /> <input
									type="hidden" name="from_page" value="${page}" />
								<button type="submit" class="btn btn-link" name="command"
									value="REDIRECT">
									<fmt:message key="signup" />
								</button>
							</form>
						</div>
						<div class="nav-item">
							<form action="lowcost" method="post" name="sign_in">
								<input type="hidden" name="to_page" value="sign_in" /> <input
									type="hidden" name="from_page" value="${page}" />
								<button type="submit" class="btn btn-link" name="command"
									value="REDIRECT">
									<fmt:message key="signin" />
								</button>
							</form>
						</div>
					</c:if>

					<c:if
						test="${page != 'SIGN_IN' && page != 'SIGN_UP' && USER_ROLE != 'GUEST'}">
						<div class="nav-item">
							<form action="lowcost" name="sign_out" method="post">
								<button type="submit" class="btn btn-link" name="command"
									value="SIGN_OUT">
									<fmt:message key="signout" />
								</button>
							</form>
						</div>
					</c:if>

				</div>
			</fmt:bundle>
		</div>
	</nav>

</header>
</html>
