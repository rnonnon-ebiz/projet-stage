<jsp:include page="include/header.jsp" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="perso" %>

<section id="main">
	<h1 id="homeTitle">${computerDAOPaginationFilter.getnComputers()} Computers found</h1>
	
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
	
		<perso:pagination search="${computerDAOPaginationFilter.getFilterName()}" currentPage="${computerDAOPaginationFilter.getFrontCurrentPage()}" maxPages="${computerDAOPaginationFilter.getMaxPages()}" />
		
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
			</thead>
			<tbody>
				<c:forEach var="computer" items="${computerDAOPaginationFilter.getComputersList()}"> 
					<perso:computerRow computer="${computer}"></perso:computerRow>
				</c:forEach>
			</tbody>
		</table>
</section>

<jsp:include page="include/footer.jsp" />
