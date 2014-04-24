<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ taglib prefix="perso" uri="/WEB-INF/tags/taglib.tld" %>

<%@ attribute name="columnTitle" required="true" %>
<%@ attribute name="width" required="true" %>

<%@ attribute name="page" required="true" type="fr.stage.domain.Page" %>
<%@ attribute name="orderByASC" required="true" %>
<%@ attribute name="orderByDESC" required="true" %>

<th class="col-md-${width}">
	${columnTitle}
	<perso:orderURI body="<span class='glyphicon glyphicon-arrow-up'></span>" search="${page.nameFilter}" goTo="${page.getFrontCurrentPage()}" orderBy="${orderByASC}"></perso:orderURI>
	<perso:orderURI body="<span class='glyphicon glyphicon-arrow-down'></span>" search="${page.nameFilter}" goTo="${page.getFrontCurrentPage()}" orderBy="${orderByDESC}"></perso:orderURI>
</th>