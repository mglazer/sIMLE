<jsp:directive.include file="/WEB-INF/jsp/includes.jsp" />
<jsp:directive.include file="/WEB-INF/jsp/header.jsp" />

<script type="text/javascript">
	$(document).ready(function() {
		$("#map_pick").click(function() {
			pickFromMap();
		});
	});

	function pickFromMap()
	{
		getFlexApp("NodeMap").pickFromMap("newlab");
	}

	function handlePickFromMap(lat, lng)
	{
		$('#_latitude').val(lat);
		$('#_longitude').val(lng);
	}
</script>
<div style="width: 100%" title="Create New Lab"><c:url value="/lab" var="form_url" /> 



<form:form	action="${form_url}" method="POST" modelAttribute="lab">
	<form:errors path="*" cssClass="errorBox"/>
	<div id="lab_name">
		<label for="_name">Name:</label> 
		<form:input id="_name" maxlength="30" path="name" size="0" />
		<form:errors path="name" cssClass="errors"/>
	</div>
	<div id="lab_location">
		<label for="_location">Location:</label>
		<label for="_latitude">Latitude:</label>
		<form:input id="_latitude" maxlength="30" path="latitude"  />
		<form:errors path="latitude" cssClass="errors"/>
		<label for="_longitude">Longitude:</label>
		<form:input id="_longitude" maxlength="30" path="longitude"  />
		<form:errors path="longitude" cssClass="errors"/>
		<a href="#" class="ui-state-default ui-corner-all map_pick_button" id="map_pick"><span class="ui-icon ui-icon-arrow-4">Pick From Map</span></a>
	</div>
	<div id="lab_domainName">
		<label for="_domainName">Domain Name:</label>
		<form:input id="_domainName" maxLength="30" path="domainName" size="0"/>
		<form:errors path="domainName" cssClass="errors"/>
	</div>
	<div class="submit" id="lab_submit">
		<input id="proceed" type="submit" value="Save" />
	</div>
</form:form>

</div>

<jsp:directive.include file="/WEB-INF/jsp/embed_google_map.jsp"/>
<jsp:directive.include file="/WEB-INF/jsp/footer.jsp" />
