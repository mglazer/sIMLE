function displayAjaxError(xhr, textStatus, errorThrown)
{
	$("#remoteErrors").show("normal", function() {
		setTimeout("hideAjaxError", 5000);
	});
}

function hideAjaxError()
{
	$("#remoteErrors").hide("normal");
}