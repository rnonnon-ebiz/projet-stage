<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html>
<html>
<head>
<title>EPF Computer Database</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<!-- Bootstrap -->
	<link href="resources/css/bootstrap.min.css" rel="stylesheet" media="screen"/>
	<link href="resources/css/main.css" rel="stylesheet" media="screen"/>
	<link href="resources/css/dark-hive/jquery-ui-1.10.4.custom.css" rel="stylesheet">
	<script src="resources/js/jquery-1.10.2.js"></script>
	<script src="resources/js/jquery-ui-1.10.4.custom.min.js"></script>
	<script src="resources/js/jquery.validate.min.js"></script>
	<script src="resources/js/additional-methods.min.js"></script>
	<script src="resources/js/formValidator.js"></script>
	<script src="resources/js/datepicker-locale.js"></script>
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

	<nav class="navbar navbar-inverse">
	<div class="container-fluid">
		<div class="navbar-header">
	      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
	        <span class="sr-only">Toggle navigation</span>
	        <span class="icon-bar"></span>
	        <span class="icon-bar"></span>
	        <span class="icon-bar"></span>
	      </button>
	      <a class="navbar-brand" href="dashboard">Application - Computer Database</a>
	    </div>
	    <div class="collapse navbar-collapse pull-right" id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
	       	 	<li><a href="langSwitch?lang=fr"><span class="label label-default">FR</span><img src="resources/images/flag_fr.png"/></a></li>
	       	 	<li><a href="langSwitch?lang=en"><span class="label label-default">EN</span><img src="resources/images/flag_en.png"/></a></li>
			</ul>
		</div>
	</div>
	</nav>
		