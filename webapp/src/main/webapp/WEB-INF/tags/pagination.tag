<%@ tag language="java" pageEncoding="UTF-8"%>
<!-- taglibs -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="perso" uri="/WEB-INF/tags/taglib.tld"%>

<!-- Attributes -->
<%@ attribute name="page" required="true" type="org.springframework.data.domain.Page"%>
<%@ attribute name="nameFilter" required="true"%>
<%@ attribute name="orderBy" required="true"%>

<!-- Local Vars -->
<c:set var="totalPages" value="${page.getTotalPages()}"/>

<c:set var="currentPage" value="${page.getNumber() + 1 }"/>
<c:set var="previousPage" value="${page.getNumber()}"/>
<c:set var="nextPage" value="${page.getNumber() + 2}"/>

<!-- Start -->
	<ul class="pagination text-center pager">
	
		<!-- go to First -->
		<li>
			<perso:orderURI search="${nameFilter}" goTo="1" orderBy="${orderBy}">
				<span class='glyphicon glyphicon-fast-backward'></span>
			</perso:orderURI>
		</li>
		<!-- go to Previous -->
		<li>
			<perso:orderURI search="${nameFilter}" goTo="${previousPage}" orderBy="${orderBy}">
				<span class='glyphicon glyphicon-step-backward'></span>
			</perso:orderURI>
<%-- 			<a href="?goTo=${previousPage}&search=<c:out value="${nameFilter}"/>&orderBy=${orderBy}"class="glyphicon glyphicon-step-backward" ></a> --%>
		</li>
		
		<!-- Cute dots -->
		<c:if test="${previousPage > 1 && totalPages > 4}">
		      	<li>
					<a>..</a>
				</li>
		</c:if>
		
		<!-- Before Pages -->
		<c:if test="${previousPage > 0 && totalPages >= 1}">
			<c:forEach var="pageNumber" begin="${currentPage < 3 ? 1 : (currentPage > totalPages-3 ? (totalPages-3 >0 ? totalPages-3 : 1) : previousPage) }" end="${previousPage}">
				<li>
					<perso:orderURI search="${nameFilter}" goTo="${pageNumber}" orderBy="${orderBy}">
						${pageNumber}
					</perso:orderURI>
				</li>
			</c:forEach>
		</c:if>
		
		<!-- Current Page -->
		<li class="active"><a>${currentPage}<span class="sr-only">(current)</span></a></li>
		
		<!-- Next Pages -->
		<c:if test="${nextPage <= totalPages && totalPages > 1}">
			<c:forEach var="pageNumber" begin="${nextPage}" end="${currentPage < 3 ? (totalPages >= 4 ? 4 : totalPages) : (currentPage >= totalPages-1 ? totalPages : nextPage)}">
				<li>
					<perso:orderURI search="${nextPage}" goTo="${pageNumber}" orderBy="${orderBy}">
						${pageNumber}
					</perso:orderURI>
				</li>
			</c:forEach>
		</c:if>
		
		<!-- Cute dots -->
		<c:if test="${nextPage < totalPages && totalPages > 4 }">
		      	<li>
					<a>..</a>
				</li>
		</c:if>
		
		<!-- go to Next -->
		<li>
			<perso:orderURI search="${nameFilter}" goTo="${nextPage}" orderBy="${orderBy}">
				<span class='glyphicon glyphicon-step-forward'></span>
			</perso:orderURI>
		</li>
		<!-- go to Last -->
		<li>
			<perso:orderURI search="${nameFilter}" goTo="${totalPages}" orderBy="${orderBy}">
				<span class='glyphicon glyphicon-fast-forward'></span>
			</perso:orderURI>
		</li>
	</ul>
