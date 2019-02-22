<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${ContextPath}/static/bootstrap/css/bootstrap.min.css" media="all" rel="stylesheet" type="text/css" />
<link href="${ContextPath}/static/hySpin/css/spin.css" rel="stylesheet" type="text/css" />
<style type="text/css">
</style>
<title>center</title>
<style type="text/css">
body, html { width:100%; height:100%; border-radius:9px; }
</style>
</head>
<body>
<div class="menuPanel">
<div class="menuHead">
	<span class="glyphicon glyphicon-home"></span> CP/SP 数据接口管理
</div>
<div id="hMenu" class="hMenu">
</div>
</div>
</body>
<script type="text/javascript"	src="${ContextPath}/static/js/jquery-2.0.3.js"></script>
<script type="text/javascript"	src="${ContextPath}/static/hySpin/lib/netname.core.js"></script>
<script type="text/javascript"	src="${ContextPath}/static/hySpin/lib/spin.menu.js"></script>
<script type="text/javascript">
;({
	init : function( response ){
		if( typeof response == 'string' ){
			response = eval('(' + response + ')');
		}
		spin.menu.init( response, "rightFrame" );
	}
}).init( '${menus}' );
</script>
</html>