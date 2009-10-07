<jsp:directive.include file="/WEB-INF/jsp/includes.jsp"/>
<jsp:directive.include file="/WEB-INF/jsp/header.jsp"/>


<c:url value="/application/${application.id}/protocols.json" var="protocols_json_url"/>
<script type="text/javascript">
	function addProtocol(protocol) {
		var newRow = $("<tr><td>" + 
						protocol.id + 
					   "</td><td>" + 
					   protocol.applicationProtocol + 
					   "</td><td>" + 
					   protocol.networkProtocol + 
					   "</td><td>" + 
					   protocol.ports + 
					   "</td><td>" +
					   protocol.direction +
					   "</td><td>" + 
					   protocol.destinationIP + 
					   "</td><td>" +
					   protocol.notes +
					   "</td></tr>");
		$("#protocolTable tbody").append(newRow);				
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

	function protocolsReceived(data) {
		$("#protocolTable tbody").html("");
		data = eval('(' + data + ')');
		jQuery.each(data.protocols, function() {
			addProtocol(this);
		});
	}

	$(document).ready(function() {
		$.get("${protocols_json_url}", protocolsReceived, "json");
		$('#protocolForm').ajaxForm({
			dataType: 'json',
			success: function(data) { addProtocol(data.protocol); },
			error: displayAjaxError
		});
		$("#notes").focus(function() {
			$(this).select();
		});
		$(":input").focus(function() {
			$(this).addClass("highlight");
		}).blur(function() {
			$(this).removeClass("highlight");
		});
		$("input").tooltip();
	});

</script>

<div style="width: 100%" title="Show Lab">

<c:if test="${not empty application}">
	<h2>${application.name}</h2>
	
	<table id="protocolTable" class="dataTable">
	<thead>
		<tr>
			<th>ID</th>
			<th>Application Protocol</th>
			<th>Network Protocol</th>
			<th>Network Ports/Protocol</th>
			<th>Direction</th>
			<th>Destination IP</th>
			<th>Notes</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td colspan="7" class="dataTables_empty">Loading data from server</td>
		</tr>
	</tbody>
	</table>
	
	<security:authorize ifAllGranted="ROLE_GROUP_ADMIN">
	<c:url value="/application/${application.id}/protocol/" var="create_protocol_url"/>
	<fieldset>
	<legend>Add Protocol</legend>
	<form id="protocolForm" action="${create_protocol_url}" method="POST">
		<dl>
			<dd><label for="applicationProtocol">Application Protocol:</label></dd>
			<dt><input type="text" name="applicationProtocol"/></dt>
			<dd><label for="networkProtocol">Network Protocol:</label></dd>
			<dt><input type="text" name="networkProtocol"/></dt>
			<dd><label for="ports">Ports:</label></dd>
			<dt><input id="ports" type="text" name="ports" title="Ports have the following syntax: 45,55,60-370/10,380-390 will include ports 45,55,60,70,80,90,...370,380,381,382,...390"/></dt>
			<dd><label for="direction">Direction:</label></dd>
			<dt><select name="direction">
					<option value="In">In</option>
					<option value="Out">Out</option>
					<option value="Both">Both</option>
				</select>
			</dt>
			<dd><label for="destinationIP">Destination IP:</label></dd>
			<dt><input type="text" name="destinationIP"/></dt>
			<dd><label for="notes">Notes:</label></dd>
			<dt><textarea id="notes" name="notes" cols="30" rows="5">Notes...</textarea></dt>
		</dl>
		<div class="buttons">
			<input type="submit" value="Add"/>
		</div>
	</form> 
	</fieldset>
	</security:authorize>
		
</c:if>
<c:if test="${empty application}">No application found with this id.</c:if>
</div>
<jsp:directive.include file="/WEB-INF/jsp/footer.jsp"/>