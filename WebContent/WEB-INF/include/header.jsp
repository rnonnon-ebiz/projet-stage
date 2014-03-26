<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<title>EPF Computer Database</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<!-- Bootstrap -->
	<link href="css/bootstrap.min.css" rel="stylesheet" media="screen"/>
	<link href="css/main.css" rel="stylesheet" media="screen"/>
	<link href="css/dark-hive/jquery-ui-1.10.4.custom.css" rel="stylesheet">
	<script src="js/jquery-1.10.2.js"></script>
	<script src="js/jquery-ui-1.10.4.custom.js"></script>
	<script src="js/jquery.validate.js"></script>
	<script src="js/additional-methods.js"></script>
	<link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-glyphicons.css" rel="stylesheet">
	
	<script type="text/javascript">
		$(function datePicker(){
			$(".datepicker").datepicker({
	 			dateFormat: "yy-mm-dd"});
		});
	</script>
	
</head>
<body>
	<nav class="navbar navbar-inverse">
		<h1>
			<a href="dashboard"> Application - Computer Database </a>
		</h1>
	</nav>