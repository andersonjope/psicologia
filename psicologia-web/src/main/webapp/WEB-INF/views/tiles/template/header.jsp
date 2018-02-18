<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<body>
	<div class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom box-shadow">
		<h5 class="my-0 mr-md-auto font-weight-normal">
			<img src="resources/imagens/logo.png" alt="Smiley face" height="50" width="90">
		</h5>
		
		<nav class="my-2 my-md-0 mr-md-3">
		
			<a id="showBotaoVoltar" href="#" onclick="window.history.back();" style="display: none;">
				<button type="button" class="btn btn-outline-success">Voltar</button>
			</a>
			
			<a href="cadastrarCliente">
				<button type="button" class="btn btn-outline-success">CADASTRAR PACIENTE</button>
			</a> 
			
			<a href="cadastrarMedico">
				<button type="button" class="btn btn-outline-info">CADASTRAR PSICÓLOGO</button>
			</a>

		</nav>

	</div>

</html>