<jsp:directive.include file="/WEB-INF/jsp/includes.jsp"/>
<jsp:directive.include file="/WEB-INF/jsp/header.jsp"/>


<c:url value="/lab/${lab.id}/hosts.json" var="hosts_json_url"/>
<c:url value="/applications.json" var="applications_json_url"/>
<c:url value="/application" var="application_partial_url"/>
<c:url value="/lab/${lab.id}/host" var="lab_host_partial_url"/>
<script type="text/javascript">
	var applications_g = [];
	
	function newColumn(text)
	{
		var column = document.createElement("td");
		column.innerHTML = text;
		return column;
	}

	function applicationChangedForHost(descriptionColumn, linkId, selectedApplication, hostId)
	{
		$.post("${lab_host_partial_url}/" + hostId + "/updateApplicationLink/" + linkId, {appId: selectedApplication}, function() {
		}, "json"); 
		$(descriptionColumn).html(
				'<a href="${application_partial_url}/' + selectedApplication + '">Description</a>'
		);
	}

	function addApplicationReturn(parent, linkId, applications, hostId, selectedApplication)
	{
		var row = document.createElement("tr");
		var selectCol = document.createElement("td");
		var descCol = document.createElement("td");
		$(descCol).addClass("applicationDescription");
		
		row.appendChild(selectCol);
		row.appendChild(descCol);
		
		var selectBox = $("<select></select>");
		jQuery.each(applications, function() {
			// sanity check to make sure we're not adding in undefined values
			if ( this.id == undefined )
			{
				return;
			}
			var selected = selectedApplication == this.id ? "selected" : "";
			selectBox.append($('<option value="' + this.id + '" ' + selected + '">' + this.name + "</option>"));
		});

		$(selectCol).append($(selectBox));
		
		$(parent).append($(row));
		$(selectBox).change(function() {
			applicationChangedForHost($(descCol), linkId, $(this).find("option:selected").val(), hostId); 
		});

		applicationChangedForHost($(descCol), linkId, $(selectBox).find("option:selected").val(), hostId);
	}

	function addApplication(parent, hostId, linkId, appId)
	{
		if ( linkId == undefined || appId == undefined )
		{
			$.post("${lab_host_partial_url}/" + hostId + "/addApplication.json", {}, function(data) {
				addApplicationReturn(parent, data.linkId, data.applications, hostId);
			}, "json");
		}
		else
		{
			addApplicationReturn(parent, linkId, applications_g, hostId, appId);
		}
	}
		
	
	function addHost(host) {
		var newRow = document.createElement("tr");
		newRow.appendChild(newColumn(host.id));
		newRow.appendChild(newColumn(host.name));
		newRow.appendChild(newColumn(host.dnsNames));
		newRow.appendChild(newColumn(host.addressIP));

		var applicationRow = document.createElement("tr");
		applicationRow.appendChild(newColumn(""));
		var applicationColumn = document.createElement("td");
		applicationColumn.setAttribute("class", "applicationColumn");
		applicationColumn.setAttribute("colspan", 3);

		var applicationsTable = document.createElement("table");
		var applicationsBody = document.createElement("tbody");
		applicationsTable.appendChild(applicationsBody);

		$(applicationColumn).append($(applicationsTable));
		applicationRow.appendChild(applicationColumn);
		
		var addApplicationButton = $('<input type="button" value="Add Application" class="add"/>').click(function() {
			addApplication(applicationsBody, host.id);
		});

		var addApplicationColumn = document.createElement("td");
		$(addApplicationColumn).append($(addApplicationButton));
	 	$(addApplicationColumn).appendTo($(newRow));
		
		$("#hostTable > tbody").append($(newRow));
		$("#hostTable > tbody").append($(applicationRow));	

		// add in the application links that have been previously defined
		jQuery.each(host.applications, function() {
			addApplication(applicationsBody, host.id, this.id, this.application);
		});			
	}

	function applicationsReceived(data) {
		data = evalJSON(data);
		jQuery.each(data.applications, function() {
			applications_g[this.id] = this;
		});
	}

	function hostsReceived(data) {
		$("#hostTable tbody").html("");
		console.log(data);
		data = evalJSON(data);
		jQuery.each(data.hosts, function() {
			addHost(this);
		});
	}

	function evalJSON(data) {
		return eval('(' + data + ')');
	}

	$(document).ready(function() {
		$.get("${applications_json_url}", applicationsReceived, "json");
		$.get("${hosts_json_url}", hostsReceived, "json");
		$('#hostForm').ajaxForm({
			dataType: 'json',
			success: function(data) { addHost(data.host); },
			error: displayAjaxError
		});
		$(":input").focus(function() {
			$(this).addClass("highlight");
		}).blur(function() {
			$(this).removeClass("highlight");
		});
		
	});

</script>

<div style="width: 100%" title="Show Lab">

<c:if test="${not empty lab}">
	<h2>${lab.name} at ${lab.location} for ${lab.username}</h2>
	
	<table id="hostTable" class="dataTable">
	<thead>
		<tr>
			<th>ID</th>
			<th>Hostname</th>
			<th>DNS Name</th>
			<th>IPs</th>
			<th></th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td colspan="4" class="dataTables_empty">Loading data from server</td>
		</tr>
	</tbody>
	</table>
	
	<fieldset>
	<legend>Add Host</legend>
	<c:url value="/lab/${lab.id}/host/" var="create_host_url"/>
	<form id="hostForm" action="${create_host_url}" method="POST">
		<dl>
			<dd><label for="name">Host Name:</label></dd>
			<dt><input type="text" name="name"/></dt>
			<dd><label for="dnsNames">DNS Names:</label></dd>
			<dt><input type="text" name="dnsNames"/></dt>
			<dd><label for="addressIP">IP:</label></dd>
			<dt><input type="text" name="addressIP"/></dt>
		</dl>
		<div class="buttons">
			<input type="submit" value="Add Host"/>
		</div>
	</form> 
	</fieldset>
		
</c:if>
<c:if test="${empty lab}">No lab found with this id.</c:if>
</div>
<jsp:directive.include file="/WEB-INF/jsp/footer.jsp"/>
