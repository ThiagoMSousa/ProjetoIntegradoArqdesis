<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>

	<form class="form-signin" action="do?comando=Login" method="POST">
		<h2 class="form-signin-heading">Acesso ao Sistema</h2>
		<label for="input" class="sr-only">Usuario</label> <input
			class="form-control" type="text" name="usuario" placeholder="Usuario"
			autofocus="" required=""> <label for="inputPassword"
			class="sr-only">Senha</label> <input class="form-control"
			type="password" name="senha" placeholder="Senha" required="">
		<div class="checkbox">
			<label> <input type="checkbox" value="remember-me">
				Relembre-me
			</label>
		</div>
		<button class="btn btn-lg btn-primary btn-block" type="submit">Entrar</button>
	</form>

	<form class="form-signin">
		<button type="button" class="btn btn-lg btn-primary btn-block"
			data-toggle="modal" data-target="#mymodal">Cadastrar-se</button>
	</form>

	<section>
		<p>${message}</p>
	</section>

	</div>
	<!-- /container -->



	<form class="form-signin" action="do?comando=CriarLogin" class="form"
		method="POST">
		<div class="modal fade" id="mymodal">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h3>Cadastro</h3>
					</div>
					<div class="modal-body">
						<div class="form-incline">
							<label class="control-label">Nome de usuario</label> <input
								class="form-control" type="text" name="usuario"
								placeholder="Nome">
								
						</div>

						<div class="form-incline">
							<label class="control-label" for="titular">Senha</label> <input
								class="form-control" type="password" name="senha"
								placeholder="Senha">
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-lg btn-success">Salvar</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
	<script src="js/jquery-1.12.3.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/bootstrap-theme.min.css">
<link rel="stylesheet" href="css/signin.css">
</html>