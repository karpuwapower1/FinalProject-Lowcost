<html>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
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
<c:set var="page" value="<%=by.training.karpilovich.lowcost.command.Page.DEFAULT%>" scope="request"/> 
<c:import url="/general/header.jsp" />
</header>

<body>

	<fmt:bundle basename="pagecontent" prefix="index.select.">
		<div class="container">
			<div class="grid">
				<form name="select" method="post">
					<div class="row">
						<select class="custom-select  custom-select-lg col-md-2"
							name="departure">
							<option selected><fmt:message key="departureCounty" /></option>
							<option value=""></option>
							<option value=""></option>
							<option value=""></option>
						</select> <select class="custom-select  custom-select-lg col-md-2"
							name="destination">
							<option selected><fmt:message key="destinationCountry" /></option>
							<option value=""></option>
							<option value=""></option>
							<option value=""></option>
						</select> <input class="form-control form-control-lg col-md-2"
							placeholder="<fmt:message key="departureDate"/>"
							class="textbox-n" type="text" onfocus="(this.type='date')"
							onblur="(this.type='text')" name="departureDate" value="" /> <input
							class="form-control form-control-lg col-md-2"
							placeholder="<fmt:message key="returnDate"/>" class="textbox-n"
							type="text" onfocus="(this.type='date')"
							onblur="(this.type='text')" name="returnDate" value="" /> <input
							type="text" class="form-control form-control-lg col-md-2"
							name="quantity" id="quantity"
							placeholder="<fmt:message key="quantity"/>" value="" />
						<button type="submit" class="btn btn-light col-md-2"
							name="command" value="search_flight">
							<fmt:message key="command" />
						</button>
					</div>
				</form>
			</div>
		</div>
	</fmt:bundle>
	<script type="text/javascript" src="${style}/js/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="${style}/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${style}/js/popper.min.js"></script>
	<script type="text/javascript" src="${style}/js/main.js"></script>
</body>
</html>
