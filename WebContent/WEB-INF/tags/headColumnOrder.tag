<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ attribute name="columnTitle" required="true" %>
<%@ attribute name="width" required="true" %>

<%@ attribute name="page" required="true" type="fr.stage.domainClasses.Page" %>
<%@ attribute name="orderByASC" required="true" %>
<%@ attribute name="orderByDESC" required="true" %>

<th class="col-md-${width}">
		${columnTitle} 
		<a href="?page=${page.getCurrentPage()-1}&search=${page.getNameFilter()}&orderBy=${orderByASC}"><span class="glyphicon glyphicon-arrow-up"></span></a> 
		<a href="?page=${page.getCurrentPage()-1}&search=${page.getNameFilter()}&orderBy=${orderByDESC}"><span class="glyphicon glyphicon-arrow-down"></span></a>
</th>