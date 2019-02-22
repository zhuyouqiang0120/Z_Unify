<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport" />
<link href="${ContextPath}/static/bootstrap/css/bootstrap.min.css" media="all" rel="stylesheet" type="text/css" />
<link href="${ContextPath}/static/bootstrap/css/bootstrap-switch.css" media="all" rel="stylesheet" type="text/css" />
<link href="${ContextPath}/static/bootstrap/css/bootstrap-datetimepicker.min.css" media="all" rel="stylesheet" type="text/css" />
<link href="${ContextPath}/static/hySpin/css/spin.core.css" media="all" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ContextPath}/static/jquery-file-upload-ui/css/jquery.fileupload.css">
<link rel="stylesheet" href="${ContextPath}/static/jquery-file-upload-ui/css/jquery.fileupload-ui.css">
<title>投票业务</title>
<style type="text/css">
.preview{ padding:10px; }
	.pre-title{ text-align:center; color:#000; font-size:1.2em; line-height:30px; border-bottom:1px dashed #BBBBBB; font-weight: 900; }
	.pre-sign{ padding:5px; height:20px; }
		.sign-left{ float:left; }
		.sign-right{ float:right; }
	.pre-option{ padding:10px; }
		.option{ padding: 15px; border-bottom: 1px dashed #000; }
		.option-text{ display: inline-block; width: 50%; text-align: center; }
		.option-vote{ display: inline-block; width:30%; text-align: right;}
.preview-img{ padding:15px; text-align:center; }
</style>
</head>
<body>
<div class="panel panel-default" style="margin: 1px; height: 100%; width: 100%; overflow:hidden;">
	<div class="panel-heading">
		<h4 class="panel-title">
			<span class="glyphicon glyphicon-hand-up"></span> 投票业务
		</h4>
	</div>
	<div class="panel-body">
		<div id="spin-group"></div>
		<div id="spin-table"></div>
	</div>
</div>
</body>
<script type="text/javascript" src="${ContextPath}/static/js/jquery-2.0.3.js"></script>
<script type="text/javascript" src="${ContextPath}/static/js/jquery.form.js"></script>
<script type="text/javascript" src="${ContextPath}/static/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ContextPath}/static/bootstrap/js/bootstrap-switch.js"></script>
<script type="text/javascript" src="${ContextPath}/static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="${ContextPath}/static/hySpin/lib/netname.core.js"></script>
<script type="text/javascript" src="${ContextPath}/static/hySpin/lib/spin.util.js"></script>
<script type="text/javascript" src="${ContextPath}/static/hySpin/lib/spin.group.js"></script>
<script type="text/javascript" src="${ContextPath}/static/hySpin/lib/spin.table.js"></script>
<script type="text/javascript">
;({
	group : {
		pageSize : 15,
		orderCase : '',
		uri : '../group/getGroups?deleted=0',
		util : [{name : '添加投票组', type : 'btn', direct:'left', css:'btn btn-default', icon : 'glyphicon glyphicon-plus', uri : '../group/insertGroup', func:'add'},
	              {name : '编辑投票组', type : 'btn', direct:'left', css:'btn btn-default', icon : 'glyphicon glyphicon-edit', uri : '../group/updateGroup', func:'edit'},
	              {name : '移除投票组', type : 'btn', direct:'left', css:'btn btn-default', icon : 'glyphicon glyphicon-minus', uri : '../group/removeGroup', func:'remove'}],
	     field : [{name : 'ID', text : 'ID', isShow : true,  type:'INDEX',popover:false, readonly:true}, 
	              {name : 'GID', text : 'GID', isShow : true,  type:'sys', value:'', popover:false, readonly:false},
	              {name : 'Name', text : '投票组名称', isShow : true,  type:'text', popover:false, readonly:false}, 
			         {name : 'Summary', text : '概要', isShow : true,  type:'text', popover:false, readonly:false}, 
			         {name : 'Creator', text : '创建者', isShow : true,  type:'creator', value:'${loginUser}', popover:false, readonly:false}, 
			         {name : 'Operator', text : '操作者', isShow : true,  type:'operator', value:'${loginUser}', popover:false, readonly:false},
			         {name : 'CreateTime', text : '创建时间', isShow : false,  type:'sys', value:'', popover:false, readonly:true}, 
			         {name : 'UpdateTime', text : '更改时间', isShow : true,  type:'sys', value:'', popover:false, readonly:true}, 
			         {name : 'Freezed', text : '', isShow : false, type:'sys', value:0, popover:false},
			         {name : 'Deleted', text : '', isShow : false, type:'sys', value:0, popover:false}]
	},
	init : function(){
		$('#spin-group').show();
		$('#spin-table').hide();
		
		spin.group.init( this.group );
	}
}).init();

Class('spin.group_inner', null, {
	Static : {
		table : {
			pageSize : 15,
			orderCase : '',
			uri : '../item/getGroupItems?deleted=0',
			isAllowMulit : true,
			util : [{name : '', type : 'btn', direct:'left', css:'btn btn-default', icon : 'glyphicon glyphicon-backward', uri : '', func:'extend', item : 'none', extend : 'backGroup'},
			        {name : '创建投票项', type : 'btn', direct:'left', css:'btn btn-default', icon : 'glyphicon glyphicon-plus', uri : '../item/insertGroupItem', func:'add'},
		              {name : '编辑投票项', type : 'btn', direct:'left', css:'btn btn-default', icon : 'glyphicon glyphicon-edit', uri : '../item/updateGroupItem', func:'edit'},
		              {name : '预览投票项', type : 'btn', direct:'left', css:'btn btn-default', icon : 'glyphicon glyphicon-folder-open', uri : '', func:'extend', item : 'single', extend : 'preview'},
		              {name : '移除投票项', type : 'btn', direct:'left', css:'btn btn-default', icon : 'glyphicon glyphicon-minus', uri : '../item/removeGroupItem', func:'remove'},
		              {name : '锁定投票项', type : 'btn', direct:'right', css:'btn btn-danger', icon : 'glyphicon glyphicon-lock', uri : '../item/freezeGroupItem?freezed=0', premise:[{'name':'Freezed', value:0, text:'投票项状态', debug:'该投票项已锁定。'}, {'name':'ValidTime', type:'rangetime', value:true, text:'投票项有效期', debug:'该投票项正处于投票阶段，不能锁定。'}], func:'submit'},
		              {name : '发布投票项', type : 'btn', direct:'right', css:'btn btn-info', icon : 'glyphicon glyphicon-eye-open', uri : '../item/freezeGroupItem?freezed=1', premise:[{'name':'Freezed', value:1, text:'投票项状态', debug:'该投票项已发布。'}], func:'submit'}],
		     field : [{name : 'ID', text : 'ID', isShow : true,  type:'INDEX',popover:false, readonly:true}, 
		              {name : 'GID', text : 'GID', isShow : true,  type:'sys', value:'', popover:false, readonly:false},
		              {name : 'Title', text : '投票标题', isShow : true,  type:'text', popover:false, readonly:false}, 
		              {name : 'IsSingle', text : '投票类型', isShow : true,  type:'switch', switched:{on:{'name':'单选', value:1, cls:'info'}, off:{'name':'多选', value:2, cls:'success'}}, value:{1:'<span class="label label-info">单选</span>', 2:'<span class="label label-success">多选</span>'}, popover:false, readonly:false},
		              {name : 'Auth', text : '投票隐私', isShow : true,  type:'switch', switched:{on:{'name':'智能卡用户', value:1, cls:'info'}, off:{'name':'所有用户', value:2, cls:'success'}}, value:{1:'<span class="label label-info">智能卡用户</span>', 2:'<span class="label label-success">所有用户</span>'}, popover:false, readonly:false},
		              {name : 'Answer', text : '投票选项', isShow : false,  type:'group', popover:false, readonly:false},
		              {name : 'Summary', text : '概要', isShow : true,  type:'text', popover:false, readonly:false}, 
		              {name : 'ValidTime', text : '起止时间', isShow : true,  type:'rangetime', value:'', popover:false, readonly:false},
		              {name : 'Creator', text : '创建者', isShow : true,  type:'creator', value:'${loginUser}', popover:false, readonly:false}, 
		              {name : 'Operator', text : '操作者', isShow : true,  type:'operator', value:'${loginUser}', popover:false, readonly:false},
		              {name : 'CreateTime', text : '创建时间', isShow : true,  type:'sys', value:'', popover:false, readonly:true}, 
		              {name : 'UpdateTime', text : '更改时间', isShow : false,  type:'sys', value:'', popover:false, readonly:false}, 
		              {name : 'Freezed', text : '状态', isShow : true,  type:'tag', value:{0:'<span class="label label-danger">锁定</span>', 1:'<span class="label label-info">发布</span>'}, popover:false},
		              {name : 'Deleted', text : '', isShow : false,  type:'sys', value:0, popover:false}]
		},
		init : function( _gid ){
			$('#spin-group').hide();
			$('#spin-table').show();
			if( this.table.uri.indexOf('GID') > -1 ){
				this.table.uri = this.table.uri.substring(0, this.table.uri.indexOf('GID=')) + 'GID=' + _gid;				
			}else{
				this.table.uri += '&GID=' + _gid;
			}
			this.table.field[1].value = _gid; //GID
			
			spin.table.init( this.table );
		}
	}
});

Class('Extend', null, {
	Static : {
		backGroup : function(){
			$('#spin-group').show();
			$('#spin-table').hide();
		},
		preview : function( _name, _idx ){
			var item = spin.table.list[ _idx ],
				options = item.Answer,
				validTime = item.ValidTime;
			$.ajax({
				type : 'post',
				url : '../option/getVoteResult' + '?GID=' + item.GID + '&ItemID=' + item.ID,
				success : function( data ){
					if( typeof data === 'string' ){
						data = eval('(' + data + ')');
					}
					options = eval('(' + options + ')');
					validTime = eval('(' + validTime + ')');
					
					for(var i = 0; i < options.length; ++ i){
						for(var j = 0; j < data.length; ++ j){
							if( options[i].id === data[j].OptionID ){
								options[i].Vote = data[j].Vote;
							}
						}
						if( !options[i].Vote ){
							options[i].Vote = 0;
						}
					}
					
					if( options ){
						var preHTML = '<div class="preview">' + 
						  				  '<div class="pre-title">' + item.Title + '<span class="badge" style="background-color:#ADACA0;">' + (item.Freezed === 0 ? '锁定' : '发布' ) + '</span></div>' + 
						  				  '<div class="pre-sign">' + 
						  				  	  '<span class="sign-left"><span class="badge" style="background-color:#B00BB5;">' + ( item.IsSingle === 1 ? '单选' : '多选') + '</span></span>' +
		  				  				  	  '<span class="sign-right"><span class="badge" style="background-color:#5C5C57;">' + validTime.start + '</span> 到 <span class="badge" style="background-color:#F90357;">' + validTime.end + '</span></span>' +
						  				  '</div>' +
						  				  '<div class="pre-option">';
						for(var i = 0; i < options.length; ++ i){
							preHTML += '<div class="option">' + 
									   		'<span class="badge">' + (i + 1) + '</span>' +
									   		'<span class="option-text" ' + ( options[i].img ? '' : 'style="width:65%"') + '>' + options[i].text + '</span>' +
									   			( options[i].img ? '<button class="btn btn-sm btn-info" onclick="Extend.previewImg(\'' + options[i].img + '\')"><span class="glyphicon glyphicon-picture"></span> 预览</button>' : '' ) +
									   		'<span class="option-vote">当前票数: <span class="badge" style="background-color:#F74E1C;">' + ( options[i].Vote || 0 ) + '</span></span>' +
									   '</div>';
						}
						preHTML += '</div></div>';
					}else{
						preHTML = '请先添加投票选项，谢谢！';				
					}
					spin.modal.show( 1, _name, false, preHTML );
				},
				error : function(){
					console.log('获取投票结果异常。');
				}
			})
		},
		previewImg : function( _img ){
			spin.modal.addOtherModal( '预览图片', false, '<div class="preview-img"><img src="' + _img + '" width="300" height="250" /></div>');
		}
	}
});
</script>
</html>