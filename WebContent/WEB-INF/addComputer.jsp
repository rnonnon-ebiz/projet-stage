<jsp:include page="include/header.jsp" />
<section id="main">

	<h1>Add Computer</h1>
	
	<form action="addComputer" method="POST">
		<div class="form-group">
			<label for="name">Computer name:</label>
			<div class="form-inline">
				<input type="text" class="form-control" placeholder="Enter name" name="name" />
				<span class="help-block">Required</span>
			</div>
		</div>
		<div class="form-group">
			<label for="introduced">Introduced date:</label>
			<div class="form-inline">
				<input type="date" class="form-group" placeholder="Click to select" name="introducedDate" pattern="YY-MM-dd"/>
				<span class="help-block">YYYY-MM-DD</span>
			</div>
		</div>
		<div class="form-group">
			<label for="discontinued">Discontinued date:</label>
			<div class="form-inline">
				<input type="date" class="form-group" placeholder="Click to select"name="introducedDate" pattern="YY-MM-dd"/>
				<span class="help-block">YYYY-MM-DD</span>
			</div>
		</div>
		<div class="form-group">
			<label for="company">Company Name:</label>
			<select name="company">
				<option value="0">--</option>
				<option value="1">Apple</option>
				<option value="2">Dell</option>
				<option value="3">Lenovo</option>
			</select>
		</div>
		<div class="actions">
			<input type="submit" value="Add" class="btn primary">
			or <a href="dashboard" class="btn">Cancel</a>
		</div>
	</form>
</section>

<jsp:include page="include/footer.jsp" />