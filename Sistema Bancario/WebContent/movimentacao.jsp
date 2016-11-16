<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<body>

	<jsp:include page="header.jsp"/>
	
	<div class="container">
	<!-- Começo Movimentação.do -->
    	<!-- <form action="Movimentacao.do" method="POST"> -->
    		<form action="do?comando=ListarMovimentacoes" method="POST">
    		
    		 <h2 class="form-signin-heading">Movimentações</h2>
    		 
    		 
	      	<div class="form-incline">
				<label class="control-label" for="banco">Banco</label>      
	      		<input class="form-control" name="banco"placeholder="Agencia">
	      	</div>
	      	
	      	<div class="form-incline">
				<label class="control-label" for="banco">Agencia</label>      
	      		<input class="form-control" name="agencia"placeholder="Agencia">
	      	</div>
	      	
	      	<div class="form-incline">
				<label class="control-label" for="banco">Numero</label>      
	      		<input class="form-control" name="numero"placeholder="Numero">
	      	</div>
	      	
	    	<div class="form-incline">
				<label class="control-label" for="titular">Senha</label>      
      			<input class="form-control" type="password" name="senha"placeholder="Senha">
      		</div>
      		
      		<div class="form-incline">
      	 		<br/>
      			<button type="submit" class="btn btn-default">Consultar</button>
  			</div> 
    	
    	</form>
    	</div>
    	<!-- Fim Movimentação.do -->

<link rel="stylesheet" href="css/bootstrap.min.css" >
<link rel="stylesheet" href="css/bootstrap-theme.min.css">
<script src="js/bootstrap.min.js"></script>
</body>
</html>