var wsPingPong = "";
var wsWebRtc = "";
var context = "/psicologia-web";

$(document).ready(function() {
	$(".hideMessage").delay(3000).hide(100);
	
	$(".maskcpf").inputmask("999.999.999-99", { "placeholder": "000.000.000-00" });
	$(".masktelefone").inputmask("(99)[9]9999-9999", { "placeholder": "(xx)xxxxx-xxxx" });
	$(".maskdata").inputmask("99/99/99", { "placeholder": "dd/mm/aa" });
	$(".maskcep").inputmask("99.999-999", { "placeholder": "00.000-000" });
	
	var acc = document.getElementsByClassName("buttonAccordion");

	for (var i = 0; i < acc.length; i++) {
		acc[i].addEventListener("click", function() {
			this.classList.toggle("active");

			var panel = this.nextElementSibling;
			if (panel.style.maxHeight) {
				panel.style.maxHeight = null;
			} else {
				panel.style.maxHeight = panel.scrollHeight + "px";
			}

		});
	}
});

function urlWebSocket(hash){
	wsPingPong = "ws://" + document.location.host + context + "/pingpong/" + hash;
	wsWebRtc =  "ws://" + document.location.host + context + "/webtrc/" + hash;
}