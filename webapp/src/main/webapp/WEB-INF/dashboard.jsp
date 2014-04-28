<jsp:include page="include/header.jsp" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="perso" uri="/WEB-INF/tags/taglib.tld"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!-- TableHeaders -->
<spring:message code="computerName" var="col1" />
<spring:message code="introducedDate" var="col2" />
<spring:message code="discontinuedDate" var="col3" />
<spring:message code="company" var="col4" />

<!-- page numbers -->
<c:set var="currentPage" value="${page.getNumber() + 1}"/>
<c:set var="totalPages" value="${page.getTotalPages()}"/>
<c:set var="numberOfElements" value="${page.getTotalElements()}"/>

<section id="main">

	<!-- Computers FOUND -->
	<div class="page-header">
		<div>
			<h1 class="${currentPage == 1 ? 'computersFound' : ''}">${numberOfElements}</h1>
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
				value="<c:out value="${search}"/>"
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
	<c:choose>
		<c:when test="${numberOfElements > 0}">
			<jsp:include page="include/dashboardTable.jsp"/>
		</c:when>
		<c:otherwise>
			<strong><spring:message code="noResult"/></strong>
		</c:otherwise>
	</c:choose>
	
	
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
	    document.cookie = "goTo=${currentPage}";
	    document.cookie = "search=${search}";
	    document.cookie = "orderBy=${orderBy}";
	}
</script>

<jsp:include page="include/footer.jsp" />
