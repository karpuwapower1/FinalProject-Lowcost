<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="by.training.karpilovich.lowcost.command.JspParameter"%>
<%@ page import="by.training.karpilovich.lowcost.command.CommandType"%>
<%@ page import="by.training.karpilovich.lowcost.command.Page"%>
<%@ page import="by.training.karpilovich.lowcost.command.LocaleType"%>

<html>
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
				<input type="hidden" 
				name="<%=JspParameter.TO_PAGE%>" 
				value="<%=Page.DEFAULT%>" >
				<button type="submit" class="btn btn-link " 
				name="<%=JspParameter.COMMAND%>"
				value="<%=CommandType.REDIRECT%>">
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
												<input type="hidden"
												name="<%=JspParameter.TO_PAGE%>" 
												value="<%=Page.ADD_CITY%>" />
												 <input type="hidden" 
													name="<%=JspParameter.FROM_PAGE%>" 
													value="${page}" />
												<button type="submit"
													class="dropdown-item pt-0 pb-0 pr-1 pl-0" 
													name="<%=JspParameter.COMMAND%>"
													value="<%=CommandType.REDIRECT%>">
													<fmt:message key="admin.add_city" />
												</button>
											</form>
							
											<form method="post" name="show_all_cities">
											
											<input type="hidden"
												name="<%=JspParameter.TO_PAGE%>" 
												value="<%=Page.ALL_CITIES%>" />
												 <input type="hidden" 
													name="<%=JspParameter.FROM_PAGE%>" 
													value="${page}" />
												<button type="submit"
													class="dropdown-item pt-0 pb-0 pr-1 pl-0" 
													name="<%=JspParameter.COMMAND%>"
													value="<%=CommandType.SHOW_ALL_CITIES%>">
													<fmt:message key="admin.show_all_cities" />
												</button>
											</form>

											<form method="post" name="add_flight">
												<input type="hidden" name="to_page" value="add_flight" /> <input
													type="hidden" name="from_page" value="${page }" />
												<button type="submit"
													class="dropdown-item pt-0 pb-0 pr-1 pl-0" name="command"
													value="redirect">Add flight</button>
											</form>
											<form method="post" name="update_flight">
												<input type="hidden" name="to_page" value="update_flight" />
												<input type="hidden" name="from_page" value="${page }" />
												<button type="submit"
													class="dropdown-item pt-0 pb-0 pr-1 pl-0" name="command"
													value="redirect">Update flight</button>
											</form>
											<form method="post" name="delete_flight">
												<input type="hidden" name="to_page" value="delete_flight" />
												<input type="hidden" name="from_page" value="${page }" />
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
								<input type="hidden"
									name="<%=JspParameter.COMMAND%>"
									value="<%=CommandType.CHANGE_LANGUAGE%>"> 
								<input type="hidden" 
								name="<%=JspParameter.FROM_PAGE%>" value="${page}" />
								<button type="submit" class="btn btn-link"
									name="<%=JspParameter.LANGUAGE%>"
									value="<%=LocaleType.RU%>">
									<fmt:message key="language.ru" />
								</button>
								<button type="submit" class="btn btn-link"
									name="<%=JspParameter.LANGUAGE%>"
									value="<%=LocaleType.EN%>">
									<fmt:message key="language.en" />
								</button>
							</form>
						</div>
					</div>
					
					<c:if
						test="${page != 'SIGN_IN' && page != 'SIGN_UP' && ROLE == 'GUEST'}">
						<div class="nav-item">
							<form method="post" name="sign_up">
								<input type="hidden" 
									name="<%=JspParameter.TO_PAGE%>"
									value="sign_up" /> 
								<input type="hidden" 
									name="<%=JspParameter.FROM_PAGE%>"
									value="${page}" />
								<button type="submit" class="btn btn-link" 
									name="<%=JspParameter.COMMAND%>"
									value="<%=CommandType.REDIRECT%>">
									<fmt:message key="signup" />
								</button>
							</form>
						</div>
						<div class="nav-item">
							<form method="post" name="sign_in">
							<input type="hidden" 
									name="<%=JspParameter.TO_PAGE%>"
									value="sign_in" /> 
								<input type="hidden" 
									name="<%=JspParameter.FROM_PAGE%>"
									value="${page}" />
								<button type="submit" class="btn btn-link" 
									name="<%=JspParameter.COMMAND%>"
									value="<%=CommandType.REDIRECT%>">
									<fmt:message key="signin" />
								</button>
							</form>
						</div>
					</c:if>
					
					<c:if
						test="${page != 'SIGN_IN' && page != 'SIGN_UP' && ROLE != 'GUEST'}">
						<div class="nav-item">
							<form name="sign_out" method="post">
								<button type="submit" class="btn btn-link" 
									name="<%=JspParameter.COMMAND%>"
									value="<%=CommandType.SIGN_OUT%>">
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
