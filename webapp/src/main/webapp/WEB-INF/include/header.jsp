<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
<title>EPF Computer Database</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<!-- Bootstrap -->
	
	<link href="<spring:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet" media="screen"/>
	<link href="<spring:url value="/resources/css/main.css"/>" rel="stylesheet" media="screen"/>
	<link href="<spring:url value="/resources/css/dark-hive/jquery-ui-1.10.4.custom.css"/>" rel="stylesheet">
	<link href="<spring:url value="/resources/css/anim.css"/>" rel="stylesheet">
	<script src="<spring:url value="/resources/js/jquery-1.10.2.js"/>"></script>
	<script src="<spring:url value="/resources/js/jquery-ui-1.10.4.custom.min.js"/>"></script>
	<script src="<spring:url value="/resources/js/jquery.validate.min.js"/>"></script>
	<script src="<spring:url value="/resources/js/additional-methods.min.js"/>"></script>
	<script src="<spring:url value="/resources/js/formValidator.js"/>"></script>
	<script src="<spring:url value="/resources/js/datepicker-locale.js"/>"></script>
	<link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-glyphicons.css" rel="stylesheet">
	<script type="text/javascript">
		$(function datePicker(){
			$.datepicker.setDefaults( $.datepicker.regional["<spring:message code="localeLang"/>"] );
			$(".datepicker").datepicker({
	 			dateFormat: "yy-mm-dd"});
		});
		
	</script>
	
</head>
<body>
	<nav class="navbar navbar-inverse" role="navigation">
	<div class="container-fluid">
		<div class="navbar-header">
	      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
	        <span class="sr-only">Toggle navigation</span>
	        <span class="icon-bar"></span>
	        <span class="icon-bar"></span>
	        <span class="icon-bar"></span>
	      </button>
	      <a class="navbar-brand" href="<spring:url value="/dashboard"/>">Application - Computer Database</a>
	    </div>
	    
		<!-- SignIn -->
		<div class="nav navbar-left" id="connectNav">
		  <security:authorize access="isAuthenticated()"> 
		  	<a href="<c:url value="j_spring_security_logout"/>" class="btn btn-primary navbar-btn"><span class="glyphicon glyphicon-off"></span> <spring:message code="button.logout"/></a>
		  </security:authorize>
		  
		  <security:authorize access="isAnonymous()"> 
		  	<a href="<spring:url value="/login"/>" class="btn btn-info navbar-btn"><span class="glyphicon glyphicon-off"></span> <spring:message code="button.connect"/></a>
		  </security:authorize>
		</div>
		
		<!-- Lang -->
	    <div class="nav navbar-right" id="langNav">
			<ul class="nav navbar-nav">
	       	 	<li><a href="langSwitch?lang=fr"><span class="label label-default">FR</span><img src="<spring:url value="/resources/images/flag_fr.png"/>"/></a></li>
	       	 	<li><a href="langSwitch?lang=en"><span class="label label-default">EN</span><img src="<spring:url value="/resources/images/flag_en.png"/>"/></a></li>
			</ul>
		</div>
	</div>
	</nav>
		