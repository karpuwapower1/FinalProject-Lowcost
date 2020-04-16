<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html lang="en">

<header>
	<nav class="navbar navbar-expand-sm navbar-light">
		<div class="navbar-brand">
			<form method="post" name="default">
<<<<<<< HEAD
				<input type="hidden" name="<%=JspParameter.TO_PAGE%>"
					value="<%=Page.DEFAULT%>">
				<button type="submit" class="btn btn-link "
					name="<%=JspParameter.COMMAND%>" value="<%=CommandType.REDIRECT%>">
=======
				<input type="hidden" name="to_page" value="DEFAULT">
				<button type="submit" class="btn btn-link " name="command"
					value="REDIRECT">
>>>>>>> dev
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
					<c:if test="${ROLE != 'GUEST'}">
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
										<c:when test="${ROLE == 'ADMIN'}">
											<form method="post" name="add_city">
<<<<<<< HEAD
												<input type="hidden" name="<%=JspParameter.TO_PAGE%>"
													value="<%=Page.ADD_CITY%>" /> <input type="hidden"
													name="<%=JspParameter.FROM_PAGE%>" value="${page}" />
												<button type="submit"
													class="dropdown-item pt-0 pb-0 pr-1 pl-0"
													name="<%=JspParameter.COMMAND%>"
													value="<%=CommandType.REDIRECT%>">
=======
												<input type="hidden" name="to_page" value="ADD_CITY" /> <input
													type="hidden" name="from_page" value="${page}" />
												<button type="submit"
													class="dropdown-item pt-0 pb-0 pr-1 pl-0" name="command"
													value="REDIRECT">
>>>>>>> dev
													<fmt:message key="admin.add_city" />
												</button>
											</form>

											<form method="post" name="show_all_cities">

<<<<<<< HEAD
												<input type="hidden" name="<%=JspParameter.TO_PAGE%>"
													value="<%=Page.ALL_CITIES%>" /> <input type="hidden"
													name="<%=JspParameter.FROM_PAGE%>" value="${page}" />
												<button type="submit"
													class="dropdown-item pt-0 pb-0 pr-1 pl-0"
													name="<%=JspParameter.COMMAND%>"
													value="<%=CommandType.SHOW_ALL_CITIES%>">
=======
												<input type="hidden" name="to_page" value="ALL_CITIES" />
												<input type="hidden" name="from_page" value="${page}" />
												<button type="submit"
													class="dropdown-item pt-0 pb-0 pr-1 pl-0" name="command"
													value="SHOW_ALL_CITIES">
>>>>>>> dev
													<fmt:message key="admin.show_all_cities" />
												</button>
											</form>

											<form method="post" name="add_flight">
												<input type="hidden" name="to_page" value="add_flight" /> <input
													type="hidden" name="from_page" value="${page }" />
												<button type="submit"
													class="dropdown-item pt-0 pb-0 pr-1 pl-0" name="command"
													value="REDIRECT_TO_CREATE_FLIGTH_PAGE">Add flight</button>
											</form>
											<form method="post" name="show_all_flight">
												<input type="hidden" name="to_page" value="all_flight" /> <input
													type="hidden" name="from_page" value="${page }" />
												<button type="submit"
													class="dropdown-item pt-0 pb-0 pr-1 pl-0" name="command"
													value="SHOW_ALL_FLIGHTS">Show flights</button>
											</form>
										</c:when>

										<c:otherwise>

										</c:otherwise>
									</c:choose>
								</fmt:bundle>
							</div>

						</div>
					</c:if>
				</div>
			</div>

			<fmt:bundle basename="pagecontent" prefix="header.">
				<div class="navbar-nav ml-auto">
					<div class="nav-item dropdown">
						<button type="button"
							class="nav-link dropdown-toggle btn btn-link"
							data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							<fmt:message key="language" />
						</button>
						<div class="dropdown-menu">
							<form method="post" name="language">
<<<<<<< HEAD
								<input type="hidden" name="<%=JspParameter.COMMAND%>"
									value="<%=CommandType.CHANGE_LANGUAGE%>"> <input
									type="hidden" name="<%=JspParameter.FROM_PAGE%>"
									value="${page}" />
								<button type="submit" class="btn btn-link"
									name="<%=JspParameter.LANGUAGE%>" value="<%=LocaleType.RU%>">
									<fmt:message key="language.ru" />
								</button>
								<button type="submit" class="btn btn-link"
									name="<%=JspParameter.LANGUAGE%>" value="<%=LocaleType.EN%>">
=======
								<input type="hidden" name="command" value="CHANGE_LANGUAGE">
								<input type="hidden" name="from_page" value="${page}" />
								<button type="submit" class="btn btn-link" name="language"
									value="RU">
									<fmt:message key="language.ru" />
								</button>
								<button type="submit" class="btn btn-link" name="language"
									value="EN">
>>>>>>> dev
									<fmt:message key="language.en" />
								</button>
							</form>
						</div>
					</div>

					<c:if
						test="${page != 'SIGN_IN' && page != 'SIGN_UP' && ROLE == 'GUEST'}">
						<div class="nav-item">
							<form method="post" name="sign_up">
<<<<<<< HEAD
								<input type="hidden" name="<%=JspParameter.TO_PAGE%>"
									value="sign_up" /> <input type="hidden"
									name="<%=JspParameter.FROM_PAGE%>" value="${page}" />
								<button type="submit" class="btn btn-link"
									name="<%=JspParameter.COMMAND%>"
									value="<%=CommandType.REDIRECT%>">
=======
								<input type="hidden" name="to_page" value="sign_up" /> <input
									type="hidden" name="from_page" value="${page}" />
								<button type="submit" class="btn btn-link" name="command"
									value="REDIRECT">
>>>>>>> dev
									<fmt:message key="signup" />
								</button>
							</form>
						</div>
						<div class="nav-item">
							<form method="post" name="sign_in">
<<<<<<< HEAD
								<input type="hidden" name="<%=JspParameter.TO_PAGE%>"
									value="sign_in" /> <input type="hidden"
									name="<%=JspParameter.FROM_PAGE%>" value="${page}" />
								<button type="submit" class="btn btn-link"
									name="<%=JspParameter.COMMAND%>"
									value="<%=CommandType.REDIRECT%>">
=======
								<input type="hidden" name="to_page" value="sign_in" /> <input
									type="hidden" name="from_page" value="${page}" />
								<button type="submit" class="btn btn-link" name="command"
									value="REDIRECT">
>>>>>>> dev
									<fmt:message key="signin" />
								</button>
							</form>
						</div>
					</c:if>

					<c:if
						test="${page != 'SIGN_IN' && page != 'SIGN_UP' && ROLE != 'GUEST'}">
						<div class="nav-item">
							<form name="sign_out" method="post">
<<<<<<< HEAD
								<button type="submit" class="btn btn-link"
									name="<%=JspParameter.COMMAND%>"
									value="<%=CommandType.SIGN_OUT%>">
=======
								<button type="submit" class="btn btn-link" name="command"
									value="SIGN_OUT">
>>>>>>> dev
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
