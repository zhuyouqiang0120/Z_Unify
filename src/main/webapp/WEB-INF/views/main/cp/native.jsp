<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport" />
<link href="${ContextPath}/static/bootstrap/css/bootstrap.min.css"	media="all" rel="stylesheet" type="text/css" />
<link href="${ContextPath}/static/bootstrap/css/bootstrap-switch.css" media="all" rel="stylesheet" type="text/css" />
<link href="${ContextPath}/static/hySpin/css/spin.core.css" media="all" rel="stylesheet" type="text/css" />
<title>本地接口</title>
</head>
<body>
	<div class="panel panel-default"
		style="margin: 1px; height: 100%; width: 101%; overflow-y: scroll;">
		<div class="panel-heading">
			<h4 class="panel-title">
				<span class="glyphicon glyphicon-th-large"></span> 本地接口
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
<script type="text/javascript" src="${ContextPath}/static/bootstrap/js/bootstrap-switch.js"></script>
<script type="text/javascript" src="${ContextPath}/static/hySpin/lib/netname.core.js"></script>
<script type="text/javascript" src="${ContextPath}/static/hySpin/lib/spin.util.js"></script>
<script type="text/javascript" src="${ContextPath}/static/hySpin/lib/spin.table.js"></script>
<script type="text/javascript">
;({
	table : {
		pageSize : 15,
		orderCase : '',
		isAllowMulit : true,
		uri : '../task/getTasks?deleted=0&taskType=0&interTag=1',
		util :[{name : '添加任务', type : 'btn', direct:'left', css:'btn btn-default', icon : 'glyphicon glyphicon-plus', uri : '../task/insertTask', func:'add'},
	              {name : '编辑任务', type : 'btn', direct:'left', css:'btn btn-default', icon : 'glyphicon glyphicon-edit', uri : '../task/updateTask', func:'edit'},
	              {name : '移除任务', type : 'btn', direct:'left', css:'btn btn-default', icon : 'glyphicon glyphicon-minus', uri : '../task/removeTask', func:'remove'},
	              {name : '立即运行', type : 'btn', direct:'left', css:'btn btn-default', icon : 'glyphicon glyphicon-play', uri : '', func:'entend', item : 'mulit',  extend:'runTask'},
	              {name : '抓取详情', type : 'btn', direct:'left', css:'btn btn-default', icon : 'glyphicon glyphicon-list-alt', uri : '', func:'extend', item : 'single', extend:'dataDetail'},
	              {name : '业务类型', type : 'btnG', direct:'right', css:'btn btn-success', icon : 'glyphicon glyphicon-search', uri : [
 			              {name : '网络爬取', type : 'btn', css:'btn btn-default', icon : 'glyphicon glyphicon-random', uri : '../task/getTasks?deleted=0&taskType=1&interTag=1', func:'refresh'},
 			              {name : '数据对接', type : 'btn', css:'btn btn-default', icon : 'glyphicon glyphicon-retweet', uri : '../task/getTasks?deleted=0&taskType=2&interTag=1', func:'refresh'},
 			              {name : '全部任务', type : 'btn', css:'btn btn-default', icon : 'glyphicon glyphicon-list', uri : '../task/getTasks?deleted=0&taskType=0&interTag=1', func:'refresh'},
    		      		]}
	              ],
	     field : [{name : 'ID', text : 'ID', isShow : true,  type:'INDEX',popover:false, readonly:true}, 
			         {name : 'Name', text : 'CP名称', isShow : true,  type:'text', popover:false, readonly:false}, 
			         {name : 'Address', text : '所属地市', isShow : true,  type:'text',popover:false, readonly:false},
			         {name : 'Code', text : '别名', isShow : true,  type:'text', popover:false, readonly:false}, 
			         {name : 'Cycle', text : '运行周期', isShow : true,  type:'text',popover:false, readonly:false}, 
			         {name : 'Provider', text : '内容提供者', isShow : true,  type:'text',popover:false, readonly:false},
			         {name : 'TaskType', text : '接口类型', isShow : true,  type:'selected', value:[{name:'爬取',value:1}, {name:'对接',value:2}], css:{2:'<span class="label label-primary">对接</span>', 1:'<span class="label label-info">爬取</span>'}, popover:false},
			         {name : 'Status', text : '状态', isShow : true,  type:'switch', switched:{on:{'name':'定时', value:1, cls:'warning'}, off:{'name':'暂停', value:0, cls:'danger'}}, value:{0:'<span class="label label-danger">暂停</span>', 1:'<span class="label label-warning">定时</span>'}, popover:false},
			         {name : 'CreateTime', text : '创建时间', isShow : false,  type:'sys', value:'', popover:false, readonly:true}, 
			         {name : 'UpdateTime', text : '更改时间', isShow : true,  type:'sys', value:'', popover:false, readonly:true}, 
			         {name : 'Url', text : '地址', isShow : true,  type:'linkBtn', popover:true, readonly:false, content:'URL格式不正确',title:'远程地址', regex:'((http|ftp|https)://)(([a-zA-Z0-9\._-]+\.[a-zA-Z]{2,6})|([0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\&%_\./-~-]*)?'},
			         {name : 'InterTag', text : '', isShow : false,  type:'sys', value:1, popover:false},
			         {name : 'Deleted', text : '', isShow : false,  type:'sys', value:0, popover:false}]
	},
	init : function(){
		spin.table.init( this.table );
	}
}).init();

Class('Extend', null, {
	Static : {
		runTask : function( _name, items ){
			if( items.length == 0 ){
				spin.modal.show(1, _name, '请先选择您需要 ' + _name + '的业务，谢谢~');
			}else{
				var param = param = '{\'ids\' : \'';
				for(var i = 0; i < items.length; ++i){
					//此处默认删除是按照主键ID，如需拓展可根据field中type=INDEX参数进行判断
					param += spin.table.list[ items[i] ].ID;
					if(i != items.length - 1){
						param += ',';
					}
				}
				param = eval('(' + ( param + '\'}' ) + ')');
				
				$.ajax({
					type : "post",
					url : '../task/runTask',
					data : param,
					async : false,
					success : function( data ){
						if(typeof data === 'string'){
							data = eval("(" + data + ")");
						}
						$("#alertTitle").html("数据爬取");
						//{taskID:'', name:'', success:''}
						var dataHTML = '<table class="table table-bordered"	style="text-align: center"><caption align="top" style="text-align: center"><b>运行结果</b></caption>';
						for(var i = 0; i < data.length; i++){
							dataHTML += '<tr>' + 
											'<td>' + ( data[i].name || '' ) + '</td>' + 
											'<td>' + (data[i].success ? '<span class="label label-success">爬取成功</span>' : '<span class="label label-danger">爬取失败</span>') + '</td>' +
											'<td><button　class="btn btn-default btn-sm" onclick="window.open(\'../data/getData?ID=' + data[i].taskID + '\');"><span class="glyphicon glyphicon-list-alt"></span> 查看詳情</button></td>' +
										'</tr>';	
						}	
						dataHTML += '</table>';
						spin.modal.show(1, "数据爬取", dataHTML);
					},
					dataType:'json',
					timeout : 50000,
					error : function(req, error){
						alert('爬取結果出錯');
					}
				});
			}
		},
		dataDetail : function( _name, item ){
			window.open('../data/getData?ID=' + spin.table.list[ item ].ID);
		}
	}
});
</script>
</html>