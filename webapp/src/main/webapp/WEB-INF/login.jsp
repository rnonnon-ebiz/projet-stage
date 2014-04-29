<jsp:include page="include/header.jsp" />

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<spring:message code="placeholder.username" var="placeholderUsername" />
<spring:message code="placeholder.password" var="placeholderPassword" />

<section id="main">
	<form method="POST" action="<c:url value='j_spring_security_check'/>" class="form-horizontal container">
		<fieldset>
		<legend><spring:message code="legend.connectionForm" /></legend>
		<div class="form-group">
			<label class="col-xs-4 control-label" for="username">
				<spring:message code="label.username" /> :
			</label>
			<input type="text" autocomplete="on" name="username" id="username" placeholder="${placeholderUsername}"/>
		</div>
		<div class="form-group">
			<label class="col-xs-4 control-label" for="password">
				<spring:message code="label.password" /> :
			</label>
			<input type="password" name="password" id="password" placeholder="${placeholderPassword}"/>
		</div>
		<div class="form-group">
			<div class="col-xs-offset-3 col-xs-4">
				<!-- Connect -->
				<button type="submit" class="btn btn-success">
						<span class="glyphicon glyphicon-ok"></span>
						<spring:message code="button.connect"/>
				</button>
				
				<!-- OR -->
				<spring:message code="button.or" />
				
				<!-- Cancel -->
				<a href="<spring:url value="/dashboard"/>" class="btn btn-danger">
					<span class="glyphicon glyphicon-remove"></span> <spring:message
						code="button.cancel" />
				</a>
			</div>
		</div>
		</fieldset>
	</form>
</section>

<jsp:include page="include/footer.jsp" />