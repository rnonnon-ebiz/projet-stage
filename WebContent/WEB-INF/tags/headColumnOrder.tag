<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ attribute name="columnTitle" required="true"%>
<%@ attribute name="width" required="true"%>
<th class="col-md-${width}">
		${columnTitle} 
		<a href="#"><span class="glyphicon glyphicon-arrow-up"></span></a> 
		<a href="#"><span class="glyphicon glyphicon-arrow-down"></span></a>
</th>