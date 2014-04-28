<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ taglib prefix="perso" uri="/WEB-INF/tags/taglib.tld"%>

<%@ attribute name="columnTitle" required="true" %>
<%@ attribute name="width" required="true" %>

<%@ attribute name="currentPage" required="true" %>
<%@ attribute name="nameFilter" required="true" %>
<%@ attribute name="orderByASC" required="true" %>
<%@ attribute name="orderByDESC" required="true" %>

<th class="col-md-${width}">

	${columnTitle}
	
	<perso:orderURI search="${nameFilter}" goTo="${currentPage}" orderBy="${orderByASC}">
		<span class='glyphicon glyphicon-arrow-up'></span>
	</perso:orderURI>
	
	<perso:orderURI search="${nameFilter}" goTo="${currentPage}" orderBy="${orderByDESC}">
		<span class='glyphicon glyphicon-arrow-down'></span>
	</perso:orderURI>
</th>