<jsp:include page="include/header.jsp" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="perso" uri="/WEB-INF/tags/taglib.tld"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<spring:message code="computerName" var="col1" />
<spring:message code="introducedDate" var="col2" />
<spring:message code="discontinuedDate" var="col3" />
<spring:message code="company" var="col4" />

<section id="main">

	<!-- Computers FOUND -->
	<div class="page-header">
		<div>
			<h1 class="${((not page.nameFilter) && page.currentPage == 0) ? 'comptersFound' : ''}">${page.totalRes}</h1>
			<h1 id="homeTitle">
				<spring:message code="computersFound" />
			</h1>
		</div>
		<h2 id="subtitle" style="display: none">
			<spring:message code="welcome" />
			:)
		</h2>
	</div>
	
	<!-- Success Message -->
	<div style="max-height: 30px; min-height: 30px;">
		<p class="bg-success status">
			<c:out value="${successMessage}" />
		</p>
	</div>

	<div id="actions">
	
		<!-- Search form -->
		<form action="dashboard" method="GET">
			<input type="search" id="searchbox" name="search"
				value="<c:out value="${page.nameFilter}"/>"
				placeholder="<spring:message code="searchName"/>" />
				
			<button type="submit" id="searchsubmit" class="btn btn-default" onclick="setCookie()">
				<span class="glyphicon glyphicon-search"></span>
				<spring:message code="filterByName" />
			</button>
		</form>
		
		<!-- Add Computer Link-->
		<a href="<spring:url value="/addComputer"/>" class="btn btn-success"><span
			class="glyphicon glyphicon-plus"></span> <spring:message
				code="addComputer" /></a>
	</div>
	
	<div class="text-center">
		<perso:pagination page="${page}" />
		<p>Page ${page.getFrontCurrentPage()}/${page.maxPages}</p>
	</div>
	
	<div class="row">
		<table class="computers table table-bordered table-hover">
			<thead>
				<tr>
					<!-- Variable declarations for passing labels as parameters -->
					<!-- Table header for Computer Name -->
					<perso:headColumnOrder page="${page}" orderByASC="0"
						orderByDESC="1" width="5" columnTitle="${col1}"></perso:headColumnOrder>
					<perso:headColumnOrder page="${page}" orderByASC="2"
						orderByDESC="3" width="2" columnTitle="${col2}"></perso:headColumnOrder>
					<perso:headColumnOrder page="${page}" orderByASC="4"
						orderByDESC="5" width="2" columnTitle="${col3}"></perso:headColumnOrder>
					<perso:headColumnOrder page="${page}" orderByASC="6"
						orderByDESC="7" width="2" columnTitle="${col4}"></perso:headColumnOrder>
			</thead>
			<tbody>
				<c:forEach var="computer" items="${page.computersList}">
					<perso:computerRow computer="${computer}" lang="${lang}"></perso:computerRow>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
	<div class="text-center">
		<p>Page ${page.getFrontCurrentPage()}/${page.maxPages}</p>
		<perso:pagination page="${page}" />
	</div>
	
</section>

<script>
	$(".page-header").click(function() {
		if ($("#subtitle").is(":hidden")) {
			$("#subtitle").slideDown("slow");
		} else
			$("#subtitle").slideUp("slow");
	});

	var currentSlided = -1;
	$("p.bg-success.status").fadeOut(3000);
	$("tr").click(function() {
		var id = $(this).attr("id");
		var rowId = "#slide" + id;
		if (id != currentSlided) {
			$(rowId).slideDown(0, function() {
			    $("#"+id).addClass('active');
				var last = "#slide" + currentSlided;
				$("#"+currentSlided).removeClass('active');
				$(last).slideUp(0);
				currentSlided = id;
			});
		} else {
		    $("#"+id).removeClass('active');
			$(rowId).slideUp(0);
			currentSlided = -1;
		}
	});

	function setCookie(){
	    document.cookie = "goTo=${page.getFrontCurrentPage()}";
	    document.cookie = "search=${page.nameFilter}";
	    document.cookie = "orderBy=${page.orderBy}";
	}
</script>

<jsp:include page="include/footer.jsp" />
