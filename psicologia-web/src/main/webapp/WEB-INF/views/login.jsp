<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="resources/css/styles/bootstrap.min.css">
    <link rel="stylesheet" href="resources/css/styles/signin.css">
    <script type="text/javascript" src="resources/js/jquery/jquery.min.js"></script>

	<link rel="icon" href="resources/imagens/logo.png">
	
	<style>
	/* Base */
	* { margin:0; padding:0; }
	html, body { height:100%; }
	body {
	
		background: url(resources/imagens/fundo1.jpg) no-repeat center center;
		text-align:center;
	 }
	body:before { content:''; float:left; height:50%; margin-bottom:-100px; }
	.centered { display:inline-block; vertical-align:middle; }
	.centered:before { content:''; display:inline-block; height:100%; vertical-align:middle; }
	.intro { -webkit-animation:instructions 7s linear 0 1; animation:instructions 7s linear 0 1; background:#111; color:#ddd; font-size:18px; left:0; line-height:0; opacity:0; overflow:hidden; padding:0; position:absolute; right:0; top:0; z-index:1; }
	a { color:#fff; cursor:pointer; }
	a:visited { color:#7eeded; cursor:pointer; }
	@-webkit-keyframes instructions {
		0%,
		90% { opacity:1; line-height:1.5; padding:8px; }
		100% { opacity:0; line-height:0; padding:0; }
	}
	@keyframes instructions {
		0%,
		90% { opacity:1; line-height:1.5; padding:8px; }
		100% { opacity:0; line-height:0; padding:0; }
	}
	/* Eyes */
	.eyes { clear:both; }
	.eye { background-color:#fff; border-radius:100px; box-shadow:inset 0 0 50px rgba(10, 10, 100, .4), inset 0 0 20px rgba(10, 10, 100, .4), 0 0 40px rgba(0, 0, 0, .8); height:200px; margin:0 30px; position:relative; width:200px; }
	.iris { background-color:#51b2d5; border-radius:40px; box-shadow:inset 0 0 18px rgba(0, 0, 0, .5), inset 0 0 30px #1b4e6d; height:80px; opacity:.9; width:80px; }
	.pupil { background:linear-gradient(45deg, #333, #000); border-radius:20px; box-shadow:inset 0 0 8px rgba(0, 0, 0, .8); height:40px; width:40px; }
	.eye,
	.iris,
	.pupil { -webkit-backface-visibility:hidden; backface-visibility:hidden; -webkit-perspective:1000; perspective:1000; -webkit-transition:-webkit-transform .3s ease-out, box-shadow .3s ease-out; transition:transform .3s ease-out, box-shadow .3s ease-out; }
	/* #1 Top Left */
	.step-1:hover ~ .eyes .eye { box-shadow:inset 0 0 50px rgba(10, 10, 100, .4), inset -15px -15px 30px rgba(10, 10, 100, .4), 6px 6px 40px rgba(0, 0, 0, .8); }
	.step-1:hover ~ .eyes .iris {
		box-shadow:inset 4px 4px 18px rgba(0, 0, 0, .5), inset -12px -12px 30px #1b4e6d;
		-webkit-transform:translateX(-38px) translateY(-30px) skewX(-15deg) skewY(10deg) scale(0.95);
		transform:translateX(-38px) translateY(-30px) skewX(-15deg) skewY(10deg) scale(0.95);
	}
	.step-1:hover ~ .eyes .pupil {
		-webkit-transform:translateX(-3px) translateY(-3px) skewX(-4deg) skewY(4deg) scale(0.95);
		transform:translateX(-3px) translateY(-3px) skewX(-4deg) skewY(4deg) scale(0.95);
	}
	/* #2 Top Center */
	.step-2:hover ~ .eyes .eye { box-shadow:inset 0 0 50px rgba(10, 10, 100, .4), inset 0 -15px 30px rgba(10, 10, 100, .4), 0 6px 40px rgba(0, 0, 0, .8); }
	.step-2:hover ~ .eyes .iris {
		box-shadow:inset 0 4px 18px rgba(0, 0, 0, .5), inset 0 -12px 30px #1b4e6d;
		-webkit-transform:translateY(-45px) rotateX(15deg) scale(0.97);
		transform:translateY(-45px) rotateX(15deg) scale(0.97);
	}
	.step-2:hover ~ .eyes .pupil {
		-webkit-transform:translateY(-2px) rotateX(10deg) scale(0.95);
		transform:translateY(-2px) rotateX(10deg) scale(0.95);
	}
	/* #3 Top Right */
	.step-3:hover ~ .eyes .eye { box-shadow:inset 0 0 50px rgba(10, 10, 100, .4), inset 15px -15px 30px rgba(10, 10, 100, .4), 6px 6px 40px rgba(0, 0, 0, .8); }
	.step-3:hover ~ .eyes .iris {
		box-shadow:inset -4px 4px 18px rgba(0, 0, 0, .5), inset 12px -12px 30px #1b4e6d;
		-webkit-transform:translateX(38px) translateY(-30px) skewX(15deg) skewY(-10deg) scale(0.95);
		transform:translateX(38px) translateY(-30px) skewX(15deg) skewY(-10deg) scale(0.95);
	}
	.step-3:hover ~ .eyes .pupil {
		-webkit-transform:translateX(3px) translateY(-3px) skewX(4deg) skewY(-4deg) scale(0.95);
		transform:translateX(3px) translateY(-3px) skewX(4deg) skewY(-4deg) scale(0.95);
	}
	/* #4 Center Left */
	.step-4:hover ~ .eyes .eye,
	.step-5:hover ~ .eyes .eye-right  { box-shadow:inset 0 0 50px rgba(10, 10, 100, .4), inset -15px 0 30px rgba(10, 10, 100, .4), 6px 0 40px rgba(0, 0, 0, .8); }
	.step-4:hover ~ .eyes .iris,
	.step-5:hover ~ .eyes .eye-right .iris {
		box-shadow:inset 4px 0 18px rgba(0, 0, 0, .5), inset -12px 0 30px #1b4e6d;
		-webkit-transform:translateX(-45px) rotateY(15deg) scale(0.97);
		transform:translateX(-45px) rotateY(15deg) scale(0.98);
	}
	.step-4:hover ~ .eyes .pupil,
	.step-5:hover ~ .eyes .eye-right .pupil {
		-webkit-transform:translateX(-2px) rotateY(10deg) scale(0.95);
		transform:translateX(-2px) rotateY(10deg) scale(0.95);
	}
	/* #6 Center Right */
	.step-6:hover ~ .eyes .eye,
	.step-5:hover ~ .eyes .eye-left { box-shadow:inset 0 0 50px rgba(10, 10, 100, .4), inset 15px 0 30px rgba(10, 10, 100, .4), -6px 0 40px rgba(0, 0, 0, .8); }
	.step-6:hover ~ .eyes .iris,
	.step-5:hover ~ .eyes .eye-left .iris {
		box-shadow:inset -4px 0 18px rgba(0, 0, 0, .5), inset 12px 0 30px #1b4e6d;
		-webkit-transform:translateX(45px) rotateY(15deg) scale(0.97);
		transform:translateX(45px) rotateY(15deg) scale(0.98);
	}
	.step-6:hover ~ .eyes .pupil,
	.step-5:hover ~ .eyes .eye-left .pupil {
		-webkit-transform:translateX(2px) rotateY(10deg) scale(0.95);
		transform:translateX(2px) rotateY(10deg) scale(0.95);
	}
	/* #7 Bottom Left */
	.step-7:hover ~ .eyes .eye { box-shadow:inset 0 0 50px rgba(10, 10, 100, .4), inset -15px 15px 30px rgba(10, 10, 100, .4), 6px -6px 40px rgba(0, 0, 0, .8); }
	.step-7:hover ~ .eyes .iris {
		box-shadow:inset 4px -4px 18px rgba(0, 0, 0, .5), inset -12px 12px 30px #1b4e6d;
		-webkit-transform:translateX(-38px) translateY(30px) skewX(15deg) skewY(-10deg) scale(0.95);
		transform:translateX(-38px) translateY(30px) skewX(15deg) skewY(-10deg) scale(0.95);
	}
	.step-7:hover ~ .eyes .pupil {
		-webkit-transform:translateX(-3px) translateY(3px) skewX(4deg) skewY(-4deg) scale(0.95);
		transform:translateX(-3px) translateY(3px) skewX(4deg) skewY(-4deg) scale(0.95);
	}
	/* #8 Bottom Center */
	.step-8:hover ~ .eyes .eye { box-shadow:inset 0 0 50px rgba(10, 10, 100, .4), inset 0 15px 30px rgba(10, 10, 100, .4), 0 -6px 40px rgba(0, 0, 0, .8); }
	.step-8:hover ~ .eyes .iris {
		box-shadow:inset 0 -4px 18px rgba(0, 0, 0, .5), inset 0 12px 30px #1b4e6d;
		-webkit-transform:translateY(45px) rotateX(15deg) scale(0.97);
		transform:translateY(45px) rotateX(15deg) scale(0.97);
	}
	.step-8:hover ~ .eyes .pupil {
		-webkit-transform:translateY(2px) rotateX(-10deg) scale(0.95);
		transform:translateY(2px) rotateX(-10deg) scale(0.95);
	}
	/* #9 Bottom Right */
	.step-9:hover ~ .eyes .eye { box-shadow:inset 0 0 50px rgba(10, 10, 100, .4), inset 15px 15px 30px rgba(10, 10, 100, .4), -6px -6px 40px rgba(0, 0, 0, .8); }
	.step-9:hover ~ .eyes .iris {
		box-shadow:inset -4px -4px 18px rgba(0, 0, 0, .5), inset 12px 12px 30px #1b4e6d;
		-webkit-transform:translateX(38px) translateY(30px) skewX(-15deg) skewY(10deg) scale(0.95);
		transform:translateX(38px) translateY(30px) skewX(-15deg) skewY(10deg) scale(0.95);
	}
	.step-9:hover ~ .eyes .pupil {
		-webkit-transform:translateX(3px) translateY(3px) skewX(-4deg) skewY(4deg) scale(0.95);
		transform:translateX(3px) translateY(3px) skewX(-4deg) skewY(4deg) scale(0.95);
	}
	/* Directional Steps */
	.step { background-color:rgba(0, 0, 0, .2); box-shadow:inset 0 0 1px #000; cursor:default; opacity:0; position:fixed; -webkit-tap-highlight-color:rgba(0, 0, 0, 0); }
	.step-1,
	.step-2,
	.step-3 { top:0; bottom:calc(50% + 80px); }
	.step-4,
	.step-5,
	.step-6 { top:calc(50% - 80px); bottom:calc(50% - 80px); }
	.step-7,
	.step-8,
	.step-9 { top:calc(50% + 80px); bottom:0; }
	@media only screen and (min-width:580px) {
		.step-1,
		.step-4,
		.step-7 { left:0; right:calc(50% + 210px); }
		.step-2,
		.step-8 { left:calc(50% - 210px); right:calc(50% - 210px); }
		.step-3,
		.step-6,
		.step-9 { left:calc(50% + 210px); right:0; }
		.step-5 { left:calc(50% - 50px); width:100px; }
	}
	/* Mobile Tweaks */
	@media only screen and (max-width:579px) {
		.eye-right,
		.step-5 { display:none; }
		.step-1,
		.step-4,
		.step-7 { left:0; right:calc(50% + 80px); }
		.step-2,
		.step-8 { left:calc(50% - 80px); right:calc(50% - 80px); }
		.step-3,
		.step-6,
		.step-9 { left:calc(50% + 80px); right:0; }
	}
	
	</style>
	
	<title><tiles:insertAttribute name="title" ignore="true" /></title>
</head>
<body>
	<!-- STEPS -->
	<a href="#" class="step step-1"></a>
	<a href="#" class="step step-2"></a>
	<a href="#" class="step step-3"></a>
	<a href="#" class="step step-4"></a>
	<a href="#" class="step step-5"></a>
	<a href="#" class="step step-6"></a>
	<a href="#" class="step step-7"></a>
<!-- 	<a href="#" class="step step-8"></a> -->
<!-- 	<a href="#" class="step step-9"></a> -->
	
	<!-- EYES -->
	<figure class="eyes">
		<div class="eye eye-left centered"><div class="iris centered"><div class="pupil centered"></div></div></div>
		<div class="eye eye-right centered"><div class="iris iris-45 centered"><div class="pupil centered"></div></div></div>
	</figure>
	<tiles:insertAttribute name="pageMessages" />
	<br/>
	<spring:url value="/auth" var="logar" />
	<form:form id="formId" modelAttribute="formularioLogin" action="${logar}" method="POST" cssClass="form-signin">
		<form:hidden path="recuperaSenha" id="recuperaSenha"/>
		
		<img class="mb-4" src="resources/imagens/logo.png" alt="" width="60%">
		<h1 class="h3 mb-3 font-weight-normal">Entre em seu login</h1>
		
		<label for="inputEmail" class="sr-only">Email </label>
		<form:input path="email" id="email" class="form-control" placeholder="Email" autofocus="" />
		<form:errors path="email" cssClass="error"/>
		
		<label for="inputSenha" class="sr-only">Password</label>
		<form:password path="senha" id="senha" class="form-control" placeholder="Senha" />
		<form:errors path="senha" cssClass="error"/>
		
		<div class="checkbox mb-3">
		  <label>
		  	<form:checkbox path="relembre"/>
		    Desejo lembrar minha senha
		  </label>
		</div>
		<div class="checkbox mb-3">
		  <label>
		  	<a id="recuperar" href="#">
		    	Recuperar senha
		    </a>
		  </label>
		</div>
		<button id="entrar" class="btn btn-lg btn-primary btn-block" type="submit">Entrar</button>
	
	</form:form>
	
	<script type="text/javascript">
		$(document).ready(function() {
			$("#recuperar").click(function() {
				$("#recuperaSenha").val(true);
				$("#senha").val(" ");
				$("#formId").submit();
			});
			
			$("#entrar").click(function() {
				$("#recuperaSenha").val(false);
				$("#senha").val("");
			});
		});	
	</script>

</body>
</html>