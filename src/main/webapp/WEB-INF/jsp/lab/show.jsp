<jsp:directive.include file="/WEB-INF/jsp/includes.jsp"/>
<jsp:directive.include file="/WEB-INF/jsp/header.jsp"/>


<c:url value="/lab/${lab.id}/hosts.json" var="hosts_json_url"/>
<script type="text/javascript">
	function addHost(host) {
		var newRow = $("<tr><td>" + host.id + "</td><td>" + host.name + "</td><td>" + host.dnsNames + "</td><td>" + host.addressIP + "</td></tr>");
		$("#hostTable tbody").append(newRow);				
		newRow.hover(
				function() {
					console.log($(this));
					$(this).addClass("hover");
				},
				function() {
					$(this).removeClass("hover");
				}
		).click(function() {
			alert("clicked");
		});
	}

	function hostsReceived(data) {
		$("#hostTable tbody").html("");
		var data = eval('(' + data +')');
		console.log(data);
		jQuery.each(data.hosts, function() {
			addHost(this);
		});
	}

	$(document).ready(function() {
		$.get("${hosts_json_url}", hostsReceived);
		$('#hostForm').ajaxForm({
			dataType: 'json',
			success: function(data) { addHost(data.host); },
			error: displayAjaxError
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
		</tr>
	</thead>
	<tbody>
		<tr>
			<td colspan="4" class="dataTables_empty">Loading data from server</td>
		</tr>
	</tbody>
	</table>
	
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
		
</c:if>
<c:if test="${empty lab}">No lab found with this id.</c:if>
</div>
<jsp:directive.include file="/WEB-INF/jsp/footer.jsp"/>
