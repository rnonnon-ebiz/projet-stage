<jsp:include page="include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<section id="main">

	<h1>Add Computer</h1>
	
	<form class="form-horizontal " action="addComputer" method="POST">
		<div class="form-group">
			<label class="col-sm-2 control-label" for="name">Computer name:</label>
			<div class="col-sm-10">
				<div class="col-xs-2">
					<input type="text" class="form-control" placeholder="Enter name" name="name" />
					<span class="help-block">Required</span>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="introduced">Introduced date:</label>
			<div class="col-sm-10">
				<div class="col-xs-2">
					<input type="text"  class="form-control col-xs-2 datepicker" placeholder="Click to select" id="introducedDate" name="introduced"/>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="discontinued">Discontinued date:</label>
			<div class="col-sm-10">
				<div class="col-xs-2">
					<input type="text" class="form-control datepicker" placeholder="Click to select" id="discontinuedDate" name="discontinued"/>
					<input type="text" class="hidden" id="discontinuedTimestamp" name="discontinuedTimestamp"/>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="company">Company Name:</label>
			<div class="col-sm-10">
				<div class="col-xs-2">
					<select class="form-control" name="company">
						<c:forEach var="company" items="${companiesList}">
							<option value="${company.getId()}">${company.getName()}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<div class="col-xs-2">
					<input type="submit" value="Add" class="btn btn-success">
					or <a href="dashboard" class="btn btn-danger">Cancel</a>
				</div>	
 			</div>
		</div>
	</form>
</section>
<jsp:include page="include/footer.jsp" />