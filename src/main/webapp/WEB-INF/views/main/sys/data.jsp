<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"
	name="viewport" />
<link href="${ContextPath}/static/bootstrap/css/bootstrap.min.css" media="all" rel="stylesheet" type="text/css" />
<link href="${ContextPath}/static/hySpin/css/spin.core.css" media="all" rel="stylesheet" type="text/css" />
<title>数据保护</title>
</head>
<body>
	<div class="panel panel-default"
		style="margin: 1px; height: 100%; width: 101%; overflow-y: scroll;">
		<div class="panel-heading">
			<h4 class="panel-title">
				<span class="glyphicon glyphicon-lock"></span> 数据保护
			</h4>
		</div>
		<div class="panel-body">
			<div id="loading"
				style="position: absolute; display: hidden; width: 200px; height: 200px; align: center; left: 45%; top: 40%;">
				<img src="${ContextPath}/static/hySpin/img/loadingR.gif" />
			</div>
			<div id="spin-table"></div>
		</div>
	</div>
</body>
<script type="text/javascript" src="${ContextPath}/static/js/jquery-2.0.3.js"></script>
<script type="text/javascript" src="${ContextPath}/static/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ContextPath}/static/hySpin/lib/netname.core.js"></script>
<script type="text/javascript" src="${ContextPath}/static/hySpin/lib/spin.util.js"></script>
<script type="text/javascript" src="${ContextPath}/static/hySpin/lib/spin.table.js"></script>
<script type="text/javascript">
;({
	table : {
		pageSize : 15,
		orderCase : '',
		isAllowMulit : false,
		uri : '../backup/getBackupFiles?order=time',
		util :[{name : '数据备份', type : 'btn', direct:'left', css:'btn btn-default', icon : 'glyphicon glyphicon-save-file', uri : '../backup/backUpDB', func:'default'},
	              {name : '数据还原', type : 'btn', direct:'left', css:'btn btn-default', icon : 'glyphicon glyphicon-open-file', uri : '../backup/restoreDB', func:'submit'},
	              {name : '数据删除', type : 'btn', direct:'left', css:'btn btn-default', icon : 'glyphicon glyphicon-minus', uri : '../backup/deleteDBFile>name', func:'direct'}],
	     field : [
	   	         {name : 'name', text : '文件名', isShow : true,  type:'text', popover:false, readonly:false}, 
		         {name : 'size', text : '文件大小', isShow : true,  type:'text',popover:false, readonly:false}, 
		         {name : 'time', text : '创建时间', isShow : true,  type:'sys', value:'', popover:false, readonly:true}, 
		         ]
	},
	init : function(){
		spin.table.init( this.table );
	}
}).init();
</script>
</html>