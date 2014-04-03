<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ taglib prefix="perso" uri="/WEB-INF/tags/taglib.tld" %>

<%@ attribute name="columnTitle" required="true" %>
<%@ attribute name="width" required="true" %>

<%@ attribute name="page" required="true" type="fr.stage.domain.Page" %>
<%@ attribute name="orderByASC" required="true" %>
<%@ attribute name="orderByDESC" required="true" %>

<th class="col-md-${width}">
		${columnTitle}
		<perso:orderURI body="<span class='glyphicon glyphicon-arrow-up'></span>" search="${page.nameFilter}" page="${page.getFrontCurrentPage()}" orderBy="${orderByASC}"></perso:orderURI>
		<perso:orderURI body="<span class='glyphicon glyphicon-arrow-down'></span>" search="${page.nameFilter}" page="${page.getFrontCurrentPage()}" orderBy="${orderByDESC}"></perso:orderURI>
<%-- 		<a href="<perso:OrderURI page="${page.getCurrentPage()-1}" search="${page.getNameFilter()}" />"></a>  --%>
<%-- 		<a href="?page=${page.getCurrentPage()-1}&search=${page.getNameFilter()}&orderBy=${orderByDESC}"><span class="glyphicon glyphicon-arrow-down"></span></a> --%>
</th>