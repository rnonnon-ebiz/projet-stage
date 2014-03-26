<jsp:include page="include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<section id="main">

	<h1>Add Computer</h1>
	
	<form class="form-horizontal " id="addComputerForm" action="addComputer" method="POST">
		<div class="form-group">
			<label class="col-sm-2 control-label" for="name">Computer name:</label>
			<div class="col-sm-10">
				<div class="col-xs-2">
					<input type="text" class="form-control computerName" placeholder="Enter name" name="computerName"/>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="introduced">Introduced date:</label>
			<div class="col-sm-10">
				<div class="col-xs-2">
					<input type="text"  class="form-control col-xs-2 datepicker" placeholder="Click to select" name="introduced"/>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="discontinued">Discontinued date:</label>
			<div class="col-sm-10">
				<div class="col-xs-2">
					<input type="text" class="form-control datepicker" placeholder="Click to select" name="discontinued"/>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="company">Company Name:</label>
			<div class="col-sm-10">
				<div class="col-xs-2">
					<select class="form-control" name="company">
						<option value="null">--</option>
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
					<button type="submit" class="btn btn-success" >
						<span class="glyphicon glyphicon-ok"></span>Add
					</button>
					or <a href="dashboard" class="btn btn-danger"><span class="glyphicon glyphicon-remove"></span> Cancel</a>
				</div>	
 			</div>
		</div>
	</form>
	<script>
		$.validator.addMethod(
		        "regex",
		        function(value, element, regexp) {
		            var re = new RegExp(regexp);
		            return this.optional(element) || re.test(value);
		        },
		        "Please respect format"
		);
		
		$("#addComputerForm").validate({
			rules: {
				computerName:{
			           required: true,
			           minlength: 2,
			    },
			    introduced:{
			    	regex : /^((19|20)\d\d+)-(0[1-9]|1[012]+)-(0[1-9]|[12][0-9]|3[01])$/,
				},
				discontinued:{
			    	regex : /^((19|20)\d\d+)-(0[1-9]|1[012]+)-(0[1-9]|[12][0-9]|3[01])$/,
				},
			},
		    messages: {
		    	computerName: { 
					 required : "Please specify a name",
					 minlength : "Insert at least 2 char",
				},
				introduced : "Please respect Date format",
				discontinued : "Please respect Date format",
			},
			highlight: function(element) {
				 $(element).closest('.form-group').removeClass('has-success').addClass('has-error');
			},
			unhighlight: function(element) {
		        $(element).closest('.form-group').removeClass('has-error').addClass('has-success');
		    }
		});
	</script>
</section>
<jsp:include page="include/footer.jsp" />