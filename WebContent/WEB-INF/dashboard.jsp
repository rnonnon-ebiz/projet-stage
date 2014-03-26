<jsp:include page="include/header.jsp" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="perso" %>

<section id="main">
	<h1 id="homeTitle">${computerDAOPaginationFilter.getnComputers()} Computers found</h1>
	
	<div id="actions">
		<form action="dashboard" method="GET">
			<input type="search" id="searchbox" name="search"
				value="" placeholder="Search name"/>
			<button type="submit" id="searchsubmit"
				class="btn btn-default">
				<span class="glyphicon glyphicon-search"></span>Filter by name
			</button>
		</form>
		<a href="addComputer" class="btn btn-success"><span class="glyphicon glyphicon-plus"></span> Add Computer</a>
	</div>
	
		<perso:pagination search="${computerDAOPaginationFilter.getFilterName()}" currentPage="${computerDAOPaginationFilter.getFrontCurrentPage()}" maxPages="${computerDAOPaginationFilter.getMaxPages()}" />
		<div class="row">
			<table class="computers table table-bordered table-hover">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->
						<perso:headColumnOrder width="5" columnTitle="Computer Name"></perso:headColumnOrder>
						<perso:headColumnOrder width="2" columnTitle="Introduced Date"></perso:headColumnOrder>
						<perso:headColumnOrder width="2" columnTitle="Discontinued Date"></perso:headColumnOrder>
						<perso:headColumnOrder width="2" columnTitle="Company"></perso:headColumnOrder>
						<th class="col-md-1">Delete</th>
				</thead>
				<tbody>
					<c:forEach var="computer" items="${computerDAOPaginationFilter.getComputersList()}"> 
						<perso:computerRow computer="${computer}"></perso:computerRow>
					</c:forEach>
				</tbody>
			</table>
		</div>
</section>

<jsp:include page="include/footer.jsp" />
