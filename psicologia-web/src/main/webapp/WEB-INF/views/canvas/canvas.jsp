<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="tilesx" uri="http://tiles.apache.org/tags-tiles-extras" %>

<div id="containerCanvas">
	<canvas id="canvas" height="auto" width="auto" ></canvas>
</div>

<tilesx:useAttribute id="scriptList" name="scripts" classname="java.util.List" />
<c:if test="${not empty scriptList}">
	<c:forEach var="item" items="${scriptList}">
		<script type="text/javascript" src="${item}"></script>
	</c:forEach>
</c:if>

<tilesx:useAttribute id="addScriptList" name="addScripts" classname="java.util.List" />
 <c:if test="${not empty addScriptList}">
	<c:forEach var="item" items="${addScriptList}">
	    <c:if test="${item ne ''}">
			<script type="text/javascript" src="${item}"></script>
		</c:if>
	</c:forEach>
</c:if>

<tilesx:useAttribute id="addAudiosList" name="addAudios" classname="java.util.List" />
<c:if test="${not empty addAudiosList}">
	<c:forEach var="item" items="${addAudiosList}" varStatus="vs">
		<c:if test="${item ne ''}">
			<audio id="${item}" preload="auto" loop src="resources/js/audio/${item}.mp3"> </audio>
		</c:if>
	</c:forEach>
</c:if>

<script type="text/javascript">
	$(document).ready(function() {
		$("#containerCanvas").width($(window).width());
		$("#containerCanvas").height($(window).height());
		var velocidade = getQueryString().velocidade;
		var playStop = getQueryString().playStop;
		canvasCliente(parseInt(velocidade), playStop);
	});
	
	function getQueryString() {
        var queryStringKeyValue = window.location.search.replace('?', '').split('&');
        var qsJsonObject = {};
        if (queryStringKeyValue != '') {
            for (i = 0; i < queryStringKeyValue.length; i++) {
                qsJsonObject[queryStringKeyValue[i].split('=')[0]] = queryStringKeyValue[i].split('=')[1];
            }
        }
        return qsJsonObject;
    }
</script>
