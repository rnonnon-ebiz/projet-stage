<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="page" required="true" type="fr.stage.domain.Page"%>

	<ul class="pagination text-center pager">
		<!-- Previous -->
		<li>
			<a href="?goTo=1&search=<c:out value="${page.nameFilter}"/>&orderBy=${page.orderBy}" class="glyphicon glyphicon-fast-backward"></a>
		</li>
		<li>
			<a href="?goTo=${page.getFrontCurrentPage()-1}&search=<c:out value="${page.nameFilter}"/>&orderBy=${page.orderBy}" class="glyphicon glyphicon-step-backward"></a>
		</li>
		
		<c:if test="${page.getFrontCurrentPage()-1 > 1 && page.maxPages > 4}">
		      	<li>
					<a>..</a>
				</li>
		</c:if>
		
		<!-- Pages Before -->
		<c:forEach var="pageNumber" begin="${page.getFrontCurrentPage() < 3 ? 1 : (page.getFrontCurrentPage() > page.maxPages-3 ? (page.maxPages-3 >0 ? page.maxPages-3 : 1) : page.getFrontCurrentPage()-1) }" end="${page.getFrontCurrentPage()-1}">
			<li>
				<a href="?goTo=${pageNumber}&search=<c:out value="${page.nameFilter}"/>&orderBy=${page.orderBy}">${pageNumber}</a>
			</li>
		</c:forEach>
		
		<!-- Current Page -->
		<li class="active"><a>${page.getFrontCurrentPage()}<span class="sr-only">(current)</span></a></li>
		
		<!-- Pages After -->
		<c:forEach var="pageNumber" begin="${page.getFrontCurrentPage()+1}" end="${page.getFrontCurrentPage() < 3 ? (page.maxPages >= 4 ? 4 : page.maxPages) : (page.getFrontCurrentPage() >= page.maxPages-1 ? page.maxPages : page.getFrontCurrentPage()+1)}">
			<li>
				<a href="?goTo=${pageNumber}&search=<c:out value="${page.nameFilter}"/>&orderBy=${page.orderBy}">${pageNumber}</a>
			</li>
		</c:forEach>
		
		<c:if test="${page.getFrontCurrentPage()+1 < page.maxPages && page.maxPages > 4 }">
		      	<li>
					<a>..</a>
				</li>
		</c:if>
		<!-- Next -->
		<li>
			<a href="?goTo=${page.getFrontCurrentPage()+1}&search=<c:out value="${page.nameFilter}"/>&orderBy=${page.orderBy}" class="glyphicon glyphicon-step-forward"></a>
		</li>
		<li>
			<a href="?goTo=${page.maxPages}&search=<c:out value="${page.nameFilter}"/>&orderBy=${page.orderBy}" class="glyphicon glyphicon-fast-forward"></a>
		</li>
	</ul>

