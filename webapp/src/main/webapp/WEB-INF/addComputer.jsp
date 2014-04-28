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
			<spring:message code="title.addComputer" />
		</h1>
	</div>

	<form:form class="form-horizontal " id="addComputerForm" action="addComputer" method="POST" commandName="computer">
		<div class="form-group <spring:hasBindErrors name="computer" > ${errors != null ? (errors.hasFieldErrors('name') ? 'has-error' : 'has-success') : ''}</spring:hasBindErrors>">
			<label class="col-sm-2 control-label" for="name">
				<spring:message code="label.name" />
			</label>
			<div class="col-sm-10">
				<div class="col-xs-2">
					<form:input class="form-control" placeholder="${placeholderName}" path="name" name="name" autofocus/>
				</div>
				<span class="help-block">
					<spring:message code="help-block.name" />
				</span>
				<div class="col-xs-2">
					<strong>
						<form:errors path="name" cssClass="control-label has-error" />
					</strong>
				</div>
			</div>
		</div>

		<div
			class="form-group <spring:hasBindErrors name="computer" > ${errors != null ? (errors.hasFieldErrors('introducedDate') ? 'has-error' : 'has-success') : ''}</spring:hasBindErrors>">
			<label class="col-sm-2 control-label" for="introduced">
				<spring:message code="label.introduced" />
			</label>
			<div class="col-sm-10">
				<div class="col-xs-2">
					<form:input class="form-control datepicker" placeholder="${placeholderDate}" path="introducedDate" name="introducedDate" />
				</div>
				<span class="help-block">
					<spring:message code="help-block.date" />
				</span>
				<div class="col-xs-2">
					<strong>
						<form:errors path="introducedDate" cssClass="control-label has-error"></form:errors>
					</strong>
				</div>
			</div>
		</div>

		<div
			class="form-group <spring:hasBindErrors name="computer" > ${errors != null ? (errors.hasFieldErrors('discontinuedDate') ? 'has-error' : 'has-success') : ''}</spring:hasBindErrors>">
			<label class="col-sm-2 control-label" for="discontinued">
				<spring:message code="label.discontinued" />
			</label>
			<div class="col-sm-10">
				<div class="col-xs-2">
					<form:input class="form-control datepicker"
						placeholder="${placeholderDate}" path="discontinuedDate"
						name="discontinuedDate" />
				</div>
				<span class="help-block"><spring:message
						code="help-block.date" /></span>
				<div class="col-xs-2">
					<strong><form:errors path="discontinuedDate"
							cssClass="control-label has-error"></form:errors></strong>
				</div>
			</div>
		</div>

		<div class="form-group <spring:hasBindErrors name="computer" > ${errors != null ? (errors.hasFieldErrors('company') ? 'has-error' : 'has-success') : ''}</spring:hasBindErrors>">
			<label class="col-sm-2 control-label" for="company">
				<spring:message code="label.companyName" />
			</label>
			<div class="col-sm-10">
				<div class="col-xs-2">
					<form:select class="form-control" path="company" name="company">
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
					<div class="col-xs-2">
						<strong><form:errors path="company"
								cssClass="control-label has-error"></form:errors></strong>
					</div>
				</div>
			</div>
		</div>

		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<div class="col-xs-2">
					<!-- Add -->
					<button type="submit" class="btn btn-success">
						<span class="glyphicon glyphicon-ok"></span>
						<spring:message code="button.add" />
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
		</div>
	</form:form>
	<script>
	$.validator.addMethod("regex", function(value, element, regexp) {
	    var re = new RegExp(regexp);
	    return this.optional(element) || re.test(value);
	}, "Please respect format");

	$("#addComputerForm")
		.validate(
			{
			    errorClass : 'control-label has-error',
			    rules : {
				name : {
				    required : true,
				    minlength : 1,
				    regex : /(^\w|^\w(\w|\s)*\w)$/,
				},
				introducedDate : {
				    regex : /^((19|20)\d\d+)-(0[1-9]|1[012]+)-(0[1-9]|[12][0-9]|3[01])$/,
				},
				discontinuedDate : {
				    regex : /^((19|20)\d\d+)-(0[1-9]|1[012]+)-(0[1-9]|[12][0-9]|3[01])$/,
				},
			    },
			    messages : {
				name : {
				    required : "<spring:message code="computer.add.name.invalid"/>",
				    minlength : "<spring:message code="computer.add.name.invalid"/>",
				    regex : "<spring:message code="computer.add.name.invalid"/>",
				},
				introducedDate : "<spring:message code="computer.add.introducedDate.invalid"/>",
				discontinuedDate : "<spring:message code="computer.add.discontinuedDate.invalid"/>",
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