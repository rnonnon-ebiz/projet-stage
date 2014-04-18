<jsp:include page="include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="perso" uri="/WEB-INF/tags/taglib.tld"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<section id="main">

	<spring:message code="placeholder.name" var="placeholderName" />
	<spring:message code="placeholder.date" var="placeholderDate" />

	<div class="page-header">
		<h1>
			<spring:message code="title.editComputer" />
		</h1>
	</div>

	<form:form class="form-horizontal " id="editComputerForm"
		action="editComputer" method="POST" commandName="computer">

		<form:input type="hidden" path="id" />

		<div
			class="form-group <spring:hasBindErrors name="computer" > ${errors != null ? (errors.hasFieldErrors('name') ? 'has-error' : 'has-success') : ''}</spring:hasBindErrors>">
			<label class="col-sm-2 control-label" for="name"><spring:message
					code="label.name" /></label>
			<div class="col-sm-10">
				<div class="col-xs-2">
					<form:input class="form-control computerName" path="name"
						placeholder="${placeholderName}" />
				</div>
				<span class="help-block"><spring:message
						code="help-block.name" /></span>
				<form:errors path="name" cssClass="has-error" />
			</div>
		</div>

		<div
			class="form-group <spring:hasBindErrors name="computer" > ${errors != null ? (errors.hasFieldErrors('introducedDate') ? 'has-error' : 'has-success') : ''}</spring:hasBindErrors>">
			<label class="col-sm-2 control-label" for="introduced"><spring:message
					code="label.introduced" /></label>
			<div class="col-sm-10">
				<div class="col-xs-2">
					<form:input class="form-control col-xs-2 datepicker"
						placeholder="${placeholderDate}" path="introducedDate" />
				</div>
				<span class="help-block"><spring:message
						code="help-block.date" /></span>
				<form:errors path="introducedDate" cssClass="has-error"></form:errors>
			</div>
		</div>

		<div
			class="form-group <spring:hasBindErrors name="computer" > ${errors != null ? (errors.hasFieldErrors('discontinuedDate') ? 'has-error' : 'has-success') : ''}</spring:hasBindErrors>">
			<label class="col-sm-2 control-label" for="discontinued"><spring:message
					code="label.discontinued" /></label>
			<div class="col-sm-10">
				<div class="col-xs-2">
					<form:input class="form-control datepicker"
						placeholder="${placeholderDate}" path="discontinuedDate" />
				</div>
				<span class="help-block"><spring:message
						code="help-block.date" /></span>
				<form:errors path="discontinuedDate" cssClass="has-error"></form:errors>
			</div>
		</div>

		<div
			class="form-group <spring:hasBindErrors name="computer" > ${errors != null ? (errors.hasFieldErrors('company') ? 'has-error' : 'has-success') : ''}</spring:hasBindErrors>">
			<label class="col-sm-2 control-label" for="company"><spring:message
					code="label.companyName" /></label>
			<div class="col-sm-10">
				<div class="col-xs-2">
					<form:select class="form-control" path="company">
						<c:choose>
							<c:when test="${computer.company == 'null'}">
								<option value="null" selected="selected">--</option>
							</c:when>
							<c:when test="${computer.company != 'null'}">
								<option value="null">--</option>
							</c:when>
						</c:choose>
						<c:forEach var="company" items="${companiesList}">
							<c:choose>
								<c:when test="${computer.company == company.id}">
									<option selected="selected" value="${company.id}">${company.name}</option>
								</c:when>
								<c:when test="${computer.company != company.id}">
									<option value="${company.id}">${company.name}</option>
								</c:when>
							</c:choose>
						</c:forEach>
					</form:select>
					<form:errors path="company" cssClass="has-error"></form:errors>
				</div>
			</div>
		</div>
		
		<!-- Form foot -->
		
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<div class="col-xs-2">

					<button type="submit" class="btn btn-success">
						<span class="glyphicon glyphicon-ok"></span>
						<spring:message code="button.edit" />
					</button>

					<spring:message code="button.or" />

					<a href="<spring:url value="/dashboard"/>" class="btn btn-danger"> <span
						class="glyphicon glyphicon-remove"></span> <spring:message
							code="button.cancel" />
					</a>

				</div>
			</div>
		</div>
	</form:form>
	<script>
		$.validator.addMethod("regex", function(value, element, regexp) {
			var re = new RegExp(regexp);
			return this.optional(element) || re.test(value);
		}, "Please respect format");

		$("#editComputerForm")
				.validate(
						{
							rules : {
								name : {
									required : true,
									minlength : 1,
									regex : /(^\w|^\w(\w|\s)*\w)$/,
								},
								introduced : {
									regex : /^((19|20)\d\d+)-(0[1-9]|1[012]+)-(0[1-9]|[12][0-9]|3[01])$/,
								},
								discontinued : {
									regex : /^((19|20)\d\d+)-(0[1-9]|1[012]+)-(0[1-9]|[12][0-9]|3[01])$/,
								},
							},
							messages : {
								computerName : {
									required : "Please specify a name",
									minlength : "Insert at least 1 char or digit shouldn't begin/end with whitespace",
								},
								introduced : "Please respect Date format",
								discontinued : "Please respect Date format",
							},
							highlight : function(element) {
								$(element).closest('.form-group').removeClass(
										'has-success').addClass('has-error');
							},
							unhighlight : function(element) {
								$(element).closest('.form-group').removeClass(
										'has-error').addClass('has-success');
							}
						});
	</script>
</section>
<jsp:include page="include/footer.jsp" />