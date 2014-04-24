<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="page" required="true" type="fr.stage.domain.Page"%>

<ul class="pagination">
	<li>
		<a href="?goTo=${page.getFrontCurrentPage()-1}&search=<c:out value="${page.nameFilter}"/>&orderBy=${page.orderBy}">&laquo;</a>
	</li>

	<c:forEach var="pageNumber" begin="1" end="${page.getFrontCurrentPage()-1}">
		<li>
			<a href="?goTo=${pageNumber}&search=<c:out value="${page.nameFilter}"/>&orderBy=${page.orderBy}">${pageNumber}</a>
		</li>
	</c:forEach>

	<li class="active"><a>${page.getFrontCurrentPage()}<span class="sr-only">(current)</span></a></li>

	<c:forEach var="pageNumber" begin="${page.getFrontCurrentPage()+1}" end="${page.maxPages}">
		<li>
			<a href="?goTo=${pageNumber}&search=<c:out value="${page.nameFilter}"/>&orderBy=${page.orderBy}">${pageNumber}</a>
		</li>
	</c:forEach>

	<li>
		<a href="?goTo=${page.getFrontCurrentPage()+1}&search=<c:out value="${page.nameFilter}"/>&orderBy=${page.orderBy}">&raquo;</a>
	</li>
</ul>

