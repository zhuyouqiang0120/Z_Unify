<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"
	name="viewport" />
<link href="${ContextPath}/static/bootstrap/css/bootstrap.min.css" media="all" rel="stylesheet" type="text/css" />
<link href="${ContextPath}/static/hySpin/css/spin.core.css" media="all" rel="stylesheet" type="text/css" />
<title>投票详情</title>
</head>
<body>
<div class="panel panel-default"
	style="margin: 1px; height: 100%; width: 100%; overflow-y: scroll;">
	<div class="panel-heading">
		<h4 class="panel-title">
			<span class="glyphicon glyphicon-th"></span> 投票详情
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
		isAllowMulit : true,
		uri : '../option/getItemOptions?deleted=0',
		util : [{name : '移除投票详情', type : 'btn', direct:'left', css:'btn btn-default', icon : 'glyphicon glyphicon-minus', uri : '../option/removeItemOption', func:'remove'}],
	     field : [{name : 'ID', text : 'ID', isShow : true,  type:'INDEX',popover:false, readonly:true}, 
			         {name : 'GID', text : 'GID', isShow : true,  type:'text', popover:false, readonly:false}, 
			         {name : 'Title', text : '投票标题', isShow : true,  type:'text', popover:false, readonly:false},
			         {name : 'Option', text : '投票选项', isShow : true,  type:'text', popover:false, readonly:false}, 
			         {name : 'CANo', text : 'CA卡号', isShow : true,  type:'text',popover:false, readonly:false}, 
			         {name : 'MacAddr', text : '机顶盒 Mac地址', isShow : true,  type:'text',popover:false, readonly:false}, 
			         {name : 'CreateTime', text : '创建时间', isShow : true,  type:'sys', value:'', popover:false, readonly:true}, 
			         {name : 'UpdateTime', text : '更改时间', isShow : false,  type:'sys', value:'', popover:false, readonly:true}, 
			         {name : 'Deleted', text : '', isShow : false,  type:'sys', value:0, popover:false}]
	},
	init : function(){
		spin.table.init( this.table );
	}
}).init();
</script>
</html>