<jsp:include page="include/header.jsp" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section id="main">
	<h1 id="homeTitle">${nComputerFound} Computers found</h1>
	<div id="actions">
		<form action="dashboard" method="GET">
			<input type="search" id="searchbox" name="search"
				value="" placeholder="Search name">
			<input type="submit" id="searchsubmit"
				value="Filter by name"
				class="btn btn-default">
		</form>
		<a href="addComputer" class="btn btn-success">Add Computer</a>
	</div>
		
		<ul class="pagination">
		  
		  <c:forEach var="page" begin="0" end="${maxPages}">
		  	<li><a href="?page=${page}">${page+1}</a></li>
		  </c:forEach>
		  
		</ul>
	
		<table class="computers table table-bordered table-hover">
			<thead>
				<tr>
					<!-- Variable declarations for passing labels as parameters -->
					<!-- Table header for Computer Name -->
					<th>Computer Name</th>
					<th>Introduced Date</th>
					<!-- Table header for Discontinued Date -->
					<th>Discontinued Date</th>
					<!-- Table header for Company -->
					<th>Company</th>
					<th>Delete</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="computer" items="${computersList}"> 
					<tr>
						<td><a href="#" onclick="">${computer.getName()}</a></td>
						<td>${computer.getIntroducedDate()}</td>
						<td>${computer.getDiscontinuedDate()}</td>
						<td>${computer.getCompany().getName()}</td>
						<td>
							<form method="post">
								<input type="text" class="hidden" name="computerToDelete" value="${computer.getId()}"/>
								<input type="submit" type="button" class="btn btn-danger" value="Delete">
							</form>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
</section>

<jsp:include page="include/footer.jsp" />
