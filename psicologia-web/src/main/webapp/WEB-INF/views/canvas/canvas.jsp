<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="tilesx" uri="http://tiles.apache.org/tags-tiles-extras" %>

<div id="containerCanvas">
	<canvas id="canvas" height="150" width="auto"></canvas>
</div>

<script type="text/javascript">
	var canvas = document.getElementById("canvas");
	canvas.width = document.getElementById("containerCanvas").offsetWidth;
</script>
