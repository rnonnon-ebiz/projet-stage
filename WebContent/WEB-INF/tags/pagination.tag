<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="currentPage" required="true" %>
<%@ attribute name="maxPages" required="true" %>
<%@ attribute name="search" required="true" %>

		<ul class="pagination">
		  <li><a href="?page=${currentPage-1}&search=${search}">&laquo;</a></li>
			  <c:forEach var="page" begin="1" end="${currentPage-1}">
			  	<li><a href="?page=${page}&search=${search}" >${page}</a></li>
			  </c:forEach>
			  
			  <li class="active"><a>${currentPage}<span class="sr-only">(current)</span></a></li>
			  
			  <c:forEach var="page" begin="${currentPage+1}" end="${maxPages}">
			  	<li><a href="?page=${page}&search=${search}" >${page}</a></li>
			  </c:forEach>
		  <li><a href="?page=${currentPage+1}&search=${search}">&raquo;</a></li>
		</ul>