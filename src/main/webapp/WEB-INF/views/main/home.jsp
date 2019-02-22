<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${ContextPath}/static/img/fault.ico" rel="shortcut icon" />
<style type="text/css">
</style>
<title>CP/SP 数据接口管理</title>
</head>
<!-- 
<frameset rows="8%,88%,4%" border="0" frameborder="no" framespacing="0">
	<frame src="${ContextPath}/main/head" scrolling="No"
		noresize="noresize" />
	<frame src="${ContextPath}/main/center" scrolling="auto"
		noresize="noresize" />
	<frame src="${ContextPath}/main/bottom" scrolling="No"
		noresize="noresize" />
</frameset>
 -->
<frameset rows="54,*,3%" cols="*" frameborder="no" border="0" framespacing="0">
  <frame src="${ContextPath}/main/head" name="topFrame" scrolling="No" noresize="noresize" id="topFrame" title="topFrame" />
  <frameset cols="15%,*" frameborder="no" border="0" framespacing="0">
    <frame src="${ContextPath}/main/left" name="leftFrame" scrolling="No" noresize="noresize" id="leftFrame" title="leftFrame" />
    <frame src="${ContextPath}/main/right" name="rightFrame" id="rightFrame" title="rightFrame" />
  </frameset>
  <frame src="${ContextPath}/main/bottom" name="bottomFrame" scrolling="No" noresize="noresize" id="bottomFrame" title="bottomFrame" />
</frameset>
<noframes>
	<body>
	</body>
</noframes>
</html>