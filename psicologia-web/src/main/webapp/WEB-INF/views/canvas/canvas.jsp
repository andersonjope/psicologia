<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="tilesx" uri="http://tiles.apache.org/tags-tiles-extras" %>

<div id="containerCanvas">
	<canvas id="canvas" height="auto" width="auto" ></canvas>
</div>

<script type="text/javascript">
	document.getElementById("containerCanvas").width = (window.innerWidth - 30);
	document.getElementById("containerCanvas").height = 150;

	var canvas = document.getElementById("canvas");
	canvas.width = document.getElementById("containerCanvas").width;
	canvas.height = document.getElementById("containerCanvas").height;
</script>
