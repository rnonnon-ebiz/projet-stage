<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ attribute name="computer" required="true" type="fr.stage.domainClasses.Computer" %>

<tr>
	<td><a href="#" onclick="">${computer.getName()}</a></td>
	<td>${computer.getIntroducedDate()}</td>
	<td>${computer.getDiscontinuedDate()}</td>
	<td>${computer.getCompany().getName()}</td>
	<td>
		<form method="post">
			<input type="text" class="hidden" name="computerToDelete"
				value="${computer.getId()}" /> <input type="submit" type="button"
				class="btn btn-danger" value="Delete">
		</form>
	</td>
</tr>