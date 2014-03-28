<jsp:include page="include/header.jsp" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="perso" %>

<section id="main">
	<div class="page-header">
		<h1 id="homeTitle">${page.getTotalRes()} Computers found</h1>
		<h2 id="subTitle" style="display:none"> WELCOME :)</h2>
	</div>
	
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
	
		<perso:pagination page="${page}"/>
		<div class="row">
			<table class="computers table table-bordered table-hover">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->
						<perso:headColumnOrder page="${page}" orderByASC="0" orderByDESC="1" width="5" columnTitle="Computer Name"></perso:headColumnOrder>
						<perso:headColumnOrder page="${page}" orderByASC="2" orderByDESC="3" width="2" columnTitle="Introduced Date"></perso:headColumnOrder>
						<perso:headColumnOrder page="${page}" orderByASC="4" orderByDESC="5" width="2" columnTitle="Discontinued Date"></perso:headColumnOrder>
						<perso:headColumnOrder page="${page}" orderByASC="6" orderByDESC="7" width="2" columnTitle="Company"></perso:headColumnOrder>
				</thead>
				<tbody>
					<c:forEach var="computer" items="${page.getComputersList()}"> 
						<perso:computerRow computer="${computer}"></perso:computerRow>
					</c:forEach>
				</tbody>
			</table>
		</div>
</section>

<script>
	var currentSlided = -1; 
// 	var currentEdited = -1; 	
	
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
