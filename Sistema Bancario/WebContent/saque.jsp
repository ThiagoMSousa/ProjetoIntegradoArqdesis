<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<body>
	<!-- Começo saque.do -->
	<jsp:include page="header.jsp" />
	<div class="container">
	
		<c:if test="${not empty message}">
			<div class="message-success">
				<p class="lead">${message}</p>
				<p> Valor SACADO: R$ ${valor} </p>
				
				<p>Novo Saldo: R$ ${conta.saldo}</p>
			</div>
		</c:if>

		<!-- <form action="efetuarSaque.do" method="post"> FORMA ANTIGA-->
		<form action="do?comando=EfetuarSaque" method="post">
			<h2 class="form-signin-heading">Efetuar Saque</h2>

			<div class="form-incline">
				<label class="control-label">Nome Completo</label> <input
					class="form-control" type="text" name="titular" placeholder="Nome">
			</div>

			<div class="form-incline">
				<label class="control-label">Seu Numero</label> <input
					class="form-control" type="text" name="numero" placeholder="Numero">
			</div>

			<div class="form-incline">
				<label class="control-label">Informe seu Banco</label> <input
					class="form-control" name="banco" placeholder="Banco">
			</div>

			<div class="form-incline">
				<label class="control-label" for="titular">Informe sua
					Agencia</label> <input class="form-control" name="agencia"
					placeholder="Agencia">
			</div>

			<div class="form-incline">
				<label class="control-label" for="titular">Senha</label> <input
					class="form-control" type="password" name="senha"
					placeholder="Senha">
			</div>

			<div class="form-incline">
				<label class="control-label" for="titular">Valor</label> <input
					class="form-control" name="valor" placeholder="Valor">
			</div>

			<div class="form-incline">
				<br />
				<button type="submit" class="btn btn-default">Sacar</button>
			</div>

		</form>

	</div>
	<!-- Fim saque.do -->

	<link rel="stylesheet" href="css/NewStyle1.css">
	<link rel="stylesheet" href="css/bootstrap.min.css">
	<link rel="stylesheet" href="css/bootstrap-theme.min.css">
	<script src="js/bootstrap.min.js"></script>
</body>
</html>
