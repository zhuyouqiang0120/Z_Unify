<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport" />
<link href="${ContextPath}/static/bootstrap/css/bootstrap.min.css" media="all" rel="stylesheet" type="text/css" />
<link href="${ContextPath}/static/hySpin/css/spin.core.css" media="all" rel="stylesheet" type="text/css" />
<title>系统日志</title>
<style type="text/css">
body, html {
	width: 100%;
	height: 100%;
	overflow: hidden;
}
</style>
</head>
<body>
	<div class="panel panel-default" style="margin: 1px; height: 100%; width: 101%; overflow-y: scroll;">
		<div class="panel-heading">
			<h4 class="panel-title">
				<span class="glyphicon glyphicon-list-alt"></span> 系统日志
			</h4>
		</div>
		<div class="panel-body">
			<div id="loading" style="position: absolute; display: hidden; width: 200px; height: 200px; align: center; left: 45%; top: 40%;">
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
		isAllowMulit : true,
		uri : '../sysLog/getSysLogs?deleted=0',
		util :[{name : '清除日志', type : 'btn', direct:'left', css:'btn btn-default', icon : 'glyphicon glyphicon-minus', uri : '../sysLog/deleteSysLog', func:'delete'}],
	     field : [{name : 'ID', text : 'ID', isShow : true,  type:'INDEX',popover:false, readonly:true}, 
			         {name : 'Action', text : '操作', isShow : true,  type:'text', popover:false, readonly:false}, 
			         {name : 'LogLevel', text : '操作级别', isShow : true,  type:'tag', value:{'DEBUG':'<span class="badge" style="background-color:#260EFA">调试级别</span>', 'ERROR':'<span class="badge" style="background-color:#FC2003">错误级别</span>', 'INFO':'<span class="badge" style="background-color:#08BBFC">消息 级别</span>', 'WARN':'<span class="badge" style="background-color:#FCB706">系统级别</span>', 'FATAL':'<span class="badge" style="background-color:#080808">崩溃级别</span>'}, popover:false, readonly:false},
			         {name : 'Summary', text : '详情摘要', isShow : true,  type:'text', popover:false, readonly:false}, 
			         {name : 'Operator', text : '操作者', isShow : true,  type:'text',popover:false, readonly:false}, 
			         {name : 'CurrTime', text : '创建时间', isShow : true,  type:'sys', value:'', popover:false, readonly:true}, 
			         {name : 'Deleted', text : '', isShow : false,  type:'sys', value:0, popover:false}]
	},
	init : function(){
		spin.table.init( this.table );
	}
}).init();
</script>
</html>