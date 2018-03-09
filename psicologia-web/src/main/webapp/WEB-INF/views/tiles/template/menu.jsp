<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<nav class="my-2 my-md-0 mr-md-3">
		
	<a id="showBotaoVoltar" href="home" style="display: none;">
		<button type="button" class="btn btn-outline-success">Voltar</button>
	</a>
	
	<c:if test="${sessionScope.usuario.enumPerfil.ordinal() eq 0}">
		<a href="cadastrarMedico">
			<button type="button" class="btn btn-outline-info">CADASTRAR PSICÓLOGO</button>
		</a>
	</c:if>
	
	<c:if test="${sessionScope.usuario.enumPerfil.ordinal() eq 0 or sessionScope.usuario.enumPerfil.ordinal() eq 1}">
		<a href="cadastrarCliente">
			<button type="button" class="btn btn-outline-success">CADASTRAR PACIENTE</button>
		</a> 
	</c:if>
	
	<c:if test="${sessionScope.usuario.enumPerfil.ordinal() eq 1}">
		<a id="iniciarSessao" href="iniciarSessao">
			<button type="button" class="btn btn-outline-warning">CRIA SESSÃO</button>
		</a> 
	</c:if>
	
	<a id="sair" href="sair">
		<button type="button" class="btn btn-outline-info">Sair</button>
	</a>

</nav>