<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="by.training.karpilovich.lowcost.command.JspParameter"%>
<%@ page import="by.training.karpilovich.lowcost.command.CommandType"%>
<%@ page import="by.training.karpilovich.lowcost.command.Page"%>
<%@ page import="by.training.karpilovich.lowcost.command.Attribute"%>
<html>

<body>
${ERROR_MESSAGE}
				<input type="text" class="form-control"
					name="<%=JspParameter.BOUND_FROM%>"
					value="${MAX_BOUND}" 
					readonly />
				 <input type="text"
					class="form-control"
					name="<%=JspParameter.BOUND_TO%>"
					value="" 
					placeholder="<fmt:message key="bound_to"/>" 
					required
					pattern="[0-9]{1,})" />
					<fmt:message key="max_bound_value"/>${MAX_BOUND_VALUE}
				<input type="text"
					class="form-control"
					name="<%=JspParameter.COEFFICIENT_VALUE%>"
					value="" 
					placeholder="<fmt:message key="value"/>" 
					required
					pattern="([0-9]{1,}(.[0-9]){0,1})" />
</html>