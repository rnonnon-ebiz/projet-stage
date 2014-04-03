<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="computer" required="true" type="fr.stage.domain.Computer" %>

<tr id="${computer.id}">
	<td><a href="#" onclick="">${computer.name}</a></td>
	<td>${computer.introducedDate}</td>
	<td>${computer.discontinuedDate}</td>
	<td>${computer.company.name}</td>
</tr>

<tr id="slide${computer.id}" style="display:none" class="warning">
	<td colspan="4">
 			<form method="get" action="editComputer" style="display:inline;">
				<input type="text" class="hidden" name="id"
						value="${computer.id}" />
				<button type="submit"
						class="btn btn-info" onclick="return edit(${computer.id})">
						<span class="glyphicon glyphicon-pencil"></span> Edit
				</button>
 			</form>
			<form method="post" action="deleteComputer" style="display:inline;">
				<input type="text" class="hidden" name="id"
						value="${computer.id}" />
				<button type="submit" type="button"
						class="btn btn-danger"
						onClick="return confirm('Confirm delete') ">	
						<span class="glyphicon glyphicon-remove"></span> Delete
				</button>	
			</form>
	</td>
</tr>

<%-- <tr id="edit${computer.getId()}" style="display:none"> --%>
<%-- 	<td><input type="text" class="form-control computerName" placeholder="Enter name" name="computerName" value="${computer.getName()}"/></td> --%>
<%-- 	<td><input type="text"  class="form-control col-xs-2 datepicker" placeholder="Click to select" name="introduced" value="${computer.getIntroducedDate()}"/></td> --%>
<%-- 	<td><input type="text" class="form-control datepicker" placeholder="Click to select" name="discontinued" value="${computer.getDiscontinuedDate()}"/></td> --%>
<!-- 	<td> -->
<!-- 					<select class="form-control" name="company"> -->
<%-- 						<c:choose> --%>
<%-- 							<c:when test="${computer.getCompany() == null}"> --%>
<!-- 								<option value="null" selected="selected">--</option> -->
<%-- 							</c:when> --%>
<%-- 							<c:when test="${computer.getCompany() != null}"> --%>
<!-- 								<option value="null">--</option> -->
<%-- 							</c:when> --%>
<%-- 						</c:choose> --%>
<%-- 						<c:forEach var="company" items="${companiesList}"> --%>
<%-- 							<c:choose> --%>
<%-- 								<c:when test="${computer.getCompany().getId() == company.getId()}"> --%>
<%-- 									<option selected="selected" value="${company.getId()}">${company.getName()}</option> --%>
<%-- 								</c:when> --%>
<%-- 								<c:when test="${computer.getCompany().getId() != company.getId()}"> --%>
<%-- 									<option value="${company.getId()}">${company.getName()}</option> --%>
<%-- 								</c:when> --%>
<%-- 							</c:choose> --%>
<%-- 						</c:forEach> --%>
<!-- 					</select> -->
<!-- 	</td> -->
<!-- </tr> -->