<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>主页</title>
<link href="${ContextPath}/static/hySpin/css/spin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="place">
    <span>位置@</span>
    <ul class="placeul">
	    <li><a href="#">首页</a></li>
	    <li>导航</li>
    </ul>
</div>
<div class="formbody">
    <div class="formtitle"><span>CP/SP 接口管理</span></div>
    <div class="toolsli">
	    <ul class="toollist">
		    <li><a href="${ContextPath}/main/task"><img src="${ContextPath}/static/img/interCom.png" width="60" height="60"/></a><h5>通用接口</h5></li>
		    <li><a href="${ContextPath}/main/nativeTask"><img src="${ContextPath}/static/img/interNative.png" width="60" height="60"/></a><h5>本地接口</h5></li>
	    </ul>
<!--    <span class="tooladd"><img src="${ContextPath}/static/img/add.png" title="添加" /></span>  --> 
    </div>
    <div class="formtitle"><span>CP/SP 业务管理</span></div>
    <div class="toolsli">
	    <ul class="toollist">
		    <li><a href="${ContextPath}/main/vote"><img src="${ContextPath}/static/img/vote.png" width="60" height="60"/></a><h5>投票业务</h5></li>
		    <li><a href="${ContextPath}/main/vote_detail"><img src="${ContextPath}/static/img/detail.png" width="60" height="60"/></a><h5>投票详情</h5></li>
	    </ul>
<!--    <span class="tooladd"><img src="${ContextPath}/static/img/add.png" title="添加" /></span>  -->  
   	</div>
    <div class="formtitle"><span>日志记录</span></div>
    <div class="toolsli">
	    <ul class="toollist">
		    <li><a href="${ContextPath}/main/sys_log"><img src="${ContextPath}/static/img/system.png" width="60" height="60"/></a><h5>系统日志</h5></li>
	    </ul>
<!--    <span class="tooladd"><img src="${ContextPath}/static/img/add.png" title="添加" /></span>  -->  
   	</div>
   	<div class="formtitle"><span>系统管理</span></div>
    <div class="toolsli">
	    <ul class="toollist">
		    <li><a href="${ContextPath}/main/userIndex"><img src="${ContextPath}/static/img/user.png" width="60" height="60"/></a><h5>用户管理</h5></li>
		    <li><a href="${ContextPath}/main/backup"><img src="${ContextPath}/static/img/database.png" width="60" height="60"/></a><h5>数据保护</h5></li>
	    </ul>
<!--    <span class="tooladd"><img src="${ContextPath}/static/img/add.png" title="添加" /></span>  -->  
   	</div>
   	
</div>
</body>
</html>