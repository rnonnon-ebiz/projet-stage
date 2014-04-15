<jsp:include page="include/header.jsp" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="perso" uri="/WEB-INF/tags/taglib.tld" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<spring:message code="computerName" var="col1"/>
<spring:message code="introducedDate" var="col2"/>
<spring:message code="discontinuedDate" var="col3"/>
<spring:message code="company" var="col4"/>

<section id="main">

	<div class="page-header">
		<h1 id="homeTitle">${page.totalRes} <spring:message code="computersFound"/></h1>
		<h2 id="subtitle" style="display:none"> <spring:message code="welcome"/> :)</h2>
	</div>
	
	<p class="bg-success status" >${successMessage}</p>
	
	<div id="actions">
		<form action="dashboard" method="GET">
			<input type="search" id="searchbox" name="search"
				value="<c:out value="${page.nameFilter}"/>" placeholder="<spring:message code="searchName"/>"/>
			<button type="submit" id="searchsubmit"
				class="btn btn-default">
				<span class="glyphicon glyphicon-search"></span><spring:message code="filterByName"/>
			</button>
		</form>
		<a href="addComputer" class="btn btn-success"><span class="glyphicon glyphicon-plus"></span> <spring:message code="addComputer"/></a>
	</div>
	
		<perso:pagination page="${page}"/>
		
		<div class="row">
			<table class="computers table table-bordered table-hover">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->
						<perso:headColumnOrder page="${page}" orderByASC="0" orderByDESC="1" width="5" columnTitle="${col1}"></perso:headColumnOrder>
						<perso:headColumnOrder page="${page}" orderByASC="2" orderByDESC="3" width="2" columnTitle="${col2}"></perso:headColumnOrder>
						<perso:headColumnOrder page="${page}" orderByASC="4" orderByDESC="5" width="2" columnTitle="${col3}"></perso:headColumnOrder>
						<perso:headColumnOrder page="${page}" orderByASC="6" orderByDESC="7" width="2" columnTitle="${col4}"></perso:headColumnOrder>
				</thead>
				<tbody>
					<c:forEach var="computer" items="${page.computersList}">
						<perso:computerRow computer="${computer}" lang="${lang}"></perso:computerRow>
					</c:forEach>
				</tbody>
			</table>
		</div>
</section>

<script>
$( ".page-header" ).click(function() {
	if($( "#subtitle" ).is(":hidden")){
		$( "#subtitle" ).slideDown("slow");
	}
	else
		$( "#subtitle" ).slideUp("slow");
});




	var currentSlided = -1; 
// 	var currentEdited = -1; 	
		$( "p.bg-success.status" ).fadeOut(3000);
		$( "tr" ).click(function() {
			var id = $(this).attr( "id" );
			var rowId = "#slide"+id;
			if(id != currentSlided){
				$( rowId ).slideDown( 0, function() {
					var last =  "#slide"+currentSlided;
					$( last ).slideUp(0);
					currentSlided = id;
					
					//Update Edit Line
// 					var lastEditId = "#edit"+currentEdited;
// 					$(lastEditId).hide();
// 					var last = "#"+currentEdited;
// 					$( last ).fadeIn("slow");
// 					currentEdited = -1;
				});
			}
			else{
				$( rowId ).slideUp(0);
				currentSlided = -1;
				
				//Update Edit Line
// 				var lastEditId = "#edit"+currentEdited;
// 				$(lastEditId).hide();
// 				var last = "#"+currentEdited;
// 				$( last ).fadeIn("slow");
// 				currentEdited = -1;
			}
		});
	
// 	function edit(id) {
// 		if(id != currentEdited){
// 			var rowId = "#"+id;
// 			$( rowId ).hide();
// 			var editId = "#edit"+id;
// 			$(editId).fadeIn("slow");
			
// 			var last = "#"+currentEdited;
// 			$( last ).fadeIn("slow");
// 			var lastEditId = "#edit"+currentEdited;
// 			$(lastEditId).hide();
			
// 			currentEdited = id;
// 		}
// 		else{
			
// 		}
// 		return false;
// 	};
</script>

<jsp:include page="include/footer.jsp" />
