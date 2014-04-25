<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>

<%@ attribute name="computer" required="true"
	type="fr.stage.domain.Computer"%>
<%@ attribute name="lang" required="true"%>

<tr id="${computer.id}">
	<td><a href="#" onclick="">${computer.name}</a></td>
	<td><joda:format var="parsed" locale="${lang}"
			value="${computer.introducedDate}" />${parsed}</td>
	<td><joda:format var="parsed2" locale="${lang}"
			value="${computer.discontinuedDate}" />${parsed2}</td>
	<td>${computer.company.name}</td>
</tr>

<tr id="slide${computer.id}" style="display: none" class="warning">
	<td colspan="4">
		<form method="get" action="editComputer" style="display: inline;">
			<input type="hidden" name="id" value="${computer.id}" />
			<button type="submit" class="btn btn-info"
				onclick="setCookie(); return edit(${computer.id})">
				<span class="glyphicon glyphicon-pencil"></span>
				<spring:message code="button.edit" />
			</button>
		</form>
		
		<form method="post" action="deleteComputer" style="display: inline;">
			<input type="hidden" name="id" value="${computer.id}" />
			<button type="submit" type="button" class="btn btn-danger"
				onClick="setCookie(); return confirm('<spring:message code="confirm.delete"/>') ">
				<span class="glyphicon glyphicon-remove"></span>
				<spring:message code="button.delete" />
			</button>
		</form>
	</td>
</tr>