<%@ page language= "java" contentType= "text/html; charset=UTF-8" pageEncoding= "UTF-8" %>
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
      		<th>Numero</th>
      		<th>Agencia</th>
      		<th>Banco</th>
      		<th>Valor</th>
      		<th>Tipo de Movimentação</th>
      		<th>Data</th> 
      	</tr>     		      		      		
      	</thead>
      <tbody>
      <c:forEach var="movimentacao" items="${movimentacoes}">
      	<tr>
			<td>${movimentacao.fromConta.numero}</td>
			<td>${movimentacao.fromConta.agencia}</td>
			<td>${movimentacao.fromConta.banco}</td>
			<td>${movimentacao.valor}</td>
			<td>${movimentacao.tipoMovimentacao}</td>
			<td>${movimentacao.date}</td> 
		</tr>
      </c:forEach>
      </tbody>
      
      </table>
      
      </div>
            
	</div>

<link rel="stylesheet" href="css/bootstrap.min.css" >
<link rel="stylesheet" href="css/bootstrap-theme.min.css">
<script src="js/bootstrap.min.js"></script>
	
	
</html>