<!--WARNING: This file is maintained by ROO! IT WILL BE OVERWRITTEN unless you specify null	@RooWebScaffold(automaticallyMaintainView = false) in the governing controller-->
<jsp:directive.include file="/WEB-INF/jsp/includes.jsp"/>
<jsp:directive.include file="/WEB-INF/jsp/header.jsp"/>
<script type="text/javascript">dojo.require("dijit.TitlePane");</script>
<div dojoType="dijit.TitlePane" style="width: 100%" title="Show Host">
<c:if test="${not empty host}">
<div id="roo_host_lab">
<label for="_lab">Lab:</label>
<div class="box" id="_lab">${host.lab}</div>
</div>
<br/>
<div id="roo_host_applications">
<label for="_applications">Applications:</label>
<div class="box" id="_applications">${host.applications}</div>
</div>
<br/>
<div id="roo_host_name">
<label for="_name">Name:</label>
<div class="box" id="_name">${host.name}</div>
</div>
<br/>
<div id="roo_host_dnsNames">
<label for="_dnsNames">Dns Names:</label>
<div class="box" id="_dnsNames">${host.dnsNames}</div>
</div>
<br/>
<div id="roo_host_addressIP">
<label for="_addressIP">Address I P:</label>
<div class="box" id="_addressIP">${host.addressIP}</div>
</div>
<br/>
</c:if>
<c:if test="${empty host}">No Host found with this id.</c:if>
</div>
<jsp:directive.include file="/WEB-INF/jsp/footer.jsp"/>
