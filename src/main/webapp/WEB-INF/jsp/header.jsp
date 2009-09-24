<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

  <link rel='stylesheet' type='text/css' media="screen" href='<c:url value="/static/styles/no-theme/jquery-ui-1.7.custom.css"/>'>
  <link rel='stylesheet' type='text/css' media="screen" href='<c:url value="/static/styles/simle.css"/>'>  
  
  <script type="text/javascript" src="<c:url value="/static/js/jquery-1.3.2.min.js"/>" type="text/javascript"></script>
  <script type="text/javascript" src="<c:url value="/static/js/jquery-ui-1.7.custom.min.js"/>" type="text/javascript"></script>
  <script type="text/javascript" src="<c:url value="/static/js/jquery.form.js"/>" type="text/javascript"></script>
  <script type="text/javascript" src="<c:url value="/static/js/jquery.dataTables.min.js"/>" type="text/javascript"></script>
  <script type="text/javascript" src="<c:url value="/static/js/simle.js"/>" type="text/javascript"></script>
  
  <title>sIMLE</title>	 
</head>

<body class="tundra spring">	

  <div id="wrap">
  
  	<div id="logo">
  		<img src="<c:url value="/static/images/simle-logo.png"/>" alt="sIMLE"/>
  	</div>
  
  	<div id="menu">
    	<%@ include file="/WEB-INF/jsp/menu.jsp" %>
    </div>
    
    <div id="remoteErrors" class="errors" style="display:none"></div>
    
    <div id="main">