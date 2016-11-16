<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<body>

<!-- Começo Conta.do -->
    
    <jsp:include page="header.jsp"/>
    
    <div class="container">
    
    <!-- <form action="Conta.do" class="form" method="POST"> -->
     <form action="do?comando=CriarConta" class="form" method="POST">
    	<h2 class="form-signin-heading">Criar Nova Conta</h2>
    
    	<div class="form-incline">
	    	 <label class="control-label">Nome Completo</label>
    	  	 <input  class="form-control"type="text" name="titular" placeholder="Nome">
    	</div>
    	
      	<div class= "form-incline">
			<label class="control-label">Informe seu Banco</label>
     	 	<input class="form-control" name="banco"placeholder="Banco">
      	</div>
      
      	<div class="form-incline">
           <label class="control-label" for="titular">Informe sua Agencia</label>
           <input class="form-control" name="agencia"placeholder="Agencia">
      	</div>
      
      	<div class="form-incline">
			<label class="control-label" for="titular">Senha</label>      
      		<input class="form-control" type="password" name="senha"placeholder="Senha">
      	</div>
      	
      	 <div class="form-incline">
      	 	<br/>
      		<button type="submit" class="btn btn-default">Criar</button>
  		</div> 
  		
      </form>
      	
    </div>  	

</body>

<link rel="stylesheet" href="css/bootstrap.min.css" >
<link rel="stylesheet" href="css/bootstrap-theme.min.css">
<script src="js/bootstrap.min.js"></script>
</html>