<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<html>
<body>
	<div class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom box-shadow">
		<h5 class="my-0 mr-md-auto font-weight-normal">
			<img src="resources/imagens/logo.png" alt="Smiley face" height="50" width="90">
		</h5>
		
		<tiles:insertAttribute name="menu"/>
		
	</div>

</html>