<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="perso" uri="/WEB-INF/tags/taglib.tld"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!-- page numbers -->
<c:set var="currentPage" value="${page.getNumber() + 1}"/>
<c:set var="totalPages" value="${page.getTotalPages()}"/>

<div class="text-center">
	<perso:pagination page="${page}" orderBy="${orderBy}"
		nameFilter="${search}" />
	<p>Page ${currentPage}/${totalPages}</p>
</div>

<div class="row">
	<table class="computers table table-bordered table-hover">
		<thead>
			<tr>
				<!-- Variable declarations for passing labels as parameters -->
				<!-- Table header for Computer Name -->
				<perso:headColumnOrder currentPage="${currentPage}" orderByASC="0"
					nameFilter="${search}" orderByDESC="1" width="5" columnTitle="${col1}"></perso:headColumnOrder>
					
				<perso:headColumnOrder currentPage="${currentPage}" orderByASC="2"
					nameFilter="${search}" orderByDESC="3" width="2" columnTitle="${col2}"></perso:headColumnOrder>
					
				<perso:headColumnOrder currentPage="${currentPage}" orderByASC="4"
					nameFilter="${search}" orderByDESC="5" width="2" columnTitle="${col3}"></perso:headColumnOrder>
					
				<perso:headColumnOrder currentPage="${currentPage}" orderByASC="6"
					nameFilter="${search}" orderByDESC="7" width="2" columnTitle="${col4}"></perso:headColumnOrder>
		</thead>
		<tbody>
			<c:forEach var="computer" items="${page.getContent()}">
				<perso:computerRow computer="${computer}" lang="${lang}"></perso:computerRow>
			</c:forEach>
		</tbody>
	</table>
</div>

<div class="text-center">
	<p>Page ${currentPage}/${totalPages}</p>
	<perso:pagination page="${page}" orderBy="${orderBy}"
		nameFilter="${search}" />
</div>