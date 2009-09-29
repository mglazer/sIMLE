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

var apiKeys_g = {
        'mglazer-08161100707.jpl.nasa.gov':
            'ABQIAAAA1YqKx1VhbjKyA0klDwok0hQ2G3lqt4RAj8ArvURSqy88SnNjnxQ1hqVZ7Qlb6DCmknuojdClzP54zw',
        'localhost':
            'ABQIAAAA1YqKx1VhbjKyA0klDwok0hT2yXp_ZAY8_ufC3CFXhHIE1NvwkxT-BFW8Vbno3N5qK4FQgqnOn6cKug'
}

function getFlexApp(appName)
{
	if ( navigator.appName.indexOf("Microsoft") >= 0 )
	{
		return window[appName];
	}
	else
	{
		return document[appName];
	}
}