<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="page" required="true" type="fr.stage.domainClasses.Page" %>

		<ul class="pagination">
		  <li><a href="?page=${page.getFrontCurrentPage()-1}&search=<c:out value="${page.getNameFilter()}"/>&orderBy=${page.getOrderBy()}">&laquo;</a></li>
		  
			  <c:forEach var="pageNumber" begin="1" end="${page.getFrontCurrentPage()-1}">
			  	<li><a href="?page=${pageNumber}&search=<c:out value="${page.getNameFilter()}"/>&orderBy=${page.getOrderBy()}" >${pageNumber}</a></li>
			  </c:forEach>
			  
			  <li class="active"><a>${page.getFrontCurrentPage()}<span class="sr-only">(current)</span></a></li>
			  
			  <c:forEach var="pageNumber" begin="${page.getFrontCurrentPage()+1}" end="${page.getMaxPages()}">
			  	<li><a href="?page=${pageNumber}&search=<c:out value="${page.getNameFilter()}"/>&orderBy=${page.getOrderBy()}" >${pageNumber}</a></li>
			  </c:forEach>
			  
		  <li><a href="?page=${page.getFrontCurrentPage()+1}&search=<c:out value="${page.getNameFilter()}"/>">&raquo;</a></li>
		</ul>