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
<title>系统用户管理</title>
</head>
<body>
	<div class="panel panel-default"
		style="margin: 1px; height: 100%; width: 100%; overflow-y: scroll;">
		<div class="panel-heading">
			<h4 class="panel-title">
				<span class="glyphicon glyphicon-globe"></span> 用户管理
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
		uri : '../user/getUsers?deleted=0',
		util : [{name : '添加用户', type : 'btn', direct:'left', css:'btn btn-default', icon : 'glyphicon glyphicon-plus', uri : '../user/insertUser', func:'add'},
	              {name : '编辑用户', type : 'btn', direct:'left', css:'btn btn-default', icon : 'glyphicon glyphicon-edit', uri : '../user/updateUser', func:'edit'},
	              {name : '移除用户', type : 'btn', direct:'left', css:'btn btn-default', icon : 'glyphicon glyphicon-minus', uri : '../user/removeUser', func:'remove'}],
	     field : [{name : 'ID', text : 'ID', isShow : true,  type:'INDEX',popover:false, readonly:true}, 
			         {name : 'Username', text : '用户名', isShow : true,  type:'text', popover:true, content:'用户名不能为空',title:'提示', regex:'', readonly:false}, 
			         {name : 'Password', text : '密码', isShow : true,  type:'text', popover:true, content:'密码不能为空',title:'提示', regex:'', readonly:false},
			         {name : 'Organization', text : '组织', isShow : true,  type:'text', popover:false, readonly:false}, 
			         {name : 'Department', text : '部门', isShow : true,  type:'text',popover:false, readonly:false}, 
			         {name : 'Area', text : '区域', isShow : true,  type:'text',popover:false, readonly:false}, 
			         {name : 'CreateTime', text : '创建时间', isShow : true,  type:'sys', value:'', popover:false, readonly:true}, 
			         {name : 'UpdateTime', text : '更改时间', isShow : true,  type:'sys', value:'', popover:false, readonly:true}, 
			         {name : 'Extj', text : '', isShow : false,  type:'sys', value:'', popover:false},
			         {name : 'Deleted', text : '', isShow : false,  type:'sys', value:0, popover:false}]
	},
	init : function(){
		spin.table.init( this.table );
	}
}).init();
</script>
</html>