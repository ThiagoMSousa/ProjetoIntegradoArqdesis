<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>

<head>
	<title>Ooooops!!</title>
</head>




<body>
   	<jsp:include page="header.jsp"/>
	
	<div class="container">
	
	  <div class="starter-template">
        	<h1>Algo de errado nao esta certo...</h1>
        	<p class="lead">${message}</p>
      </div>
      
	</div>

<link rel="stylesheet" href="css/bootstrap.min.css" >
<link rel="stylesheet" href="css/bootstrap-theme.min.css">
<script src="js/bootstrap.min.js"></script>
</html>