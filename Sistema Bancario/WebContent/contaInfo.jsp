<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>



<body>
    	<jsp:include page="header.jsp"/>

	<div class="container">
	
	  <div class="starter-template">
        	<h1>Informações da sua conta: </h1>
        	<p class="lead">${message}</p>
      </div>
      
      <div class="w3-padding w3-white notraslater">
      
      <table class="table table-bordered">
      
      	<thead>
      	<tr>
      		<th>Titular</th>
      		<th>Banco</th>
      		<th>Numero</th>
      		<th>Agencia</th>
      		<th>Senha</th>
      		<th>Saldo</th> 
      	</tr>     		      		      		
      	</thead>
      
      <tbody>
      	<tr>
			<td>${contaTO.titular}</td>
			<td>${contaTO.banco}</td>
			<td>${contaTO.numero}</td>
			<td>${contaTO.agencia}</td>
			<td>${contaTO.senha}</td>
			<td>${contaTO.saldo}</td>
		</tr>
      </tbody>
      
      </table>
      
      </div>
            
	</div>

<link rel="stylesheet" href="css/bootstrap.min.css" >
<link rel="stylesheet" href="css/bootstrap-theme.min.css">
<script src="js/bootstrap.min.js"></script>
</html>