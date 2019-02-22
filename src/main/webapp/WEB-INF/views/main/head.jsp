<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setAttribute("currAddr", request.getRemoteAddr());
	request.setAttribute("currPort", request.getServerPort());
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"
	name="viewport" />
<link href="${ContextPath}/static/bootstrap/css/bootstrap.min.css"	media="all" rel="stylesheet" type="text/css" />
<style type="text/css">
a,a:focus{ text-decoration:none;color:#000;outline:none;blr:expression(this.onFocus=this.blur()); }
a:hover{ color:#00a4ac;text-decoration:none; }
</style>
<title>HEAD</title>
<script type="text/javascript"	src="${ContextPath}/static/js/jquery-2.0.3.js"></script>
<script type="text/javascript"	src="${ContextPath}/static/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="nav navbar navbar-default" role="navigation"
		style="width: 100%; height:7% border: 1px solid #B3B2B2; border-radius:9px; margin-bottom:0px;">
		<div class="container-fluid">
			<table style="float: left">
				<tr>
					<td rowspan="2"><img width="70" height="50"	src="${ContextPath}/static/img/zens_logo.png" /></td>
					<td>
						<h4 style="text-align: center; margin: 25px 0 1px 0;">
							<a href="${ContextPath}/main/right" target="rightFrame" style="cursor:pointer;">
								<b>CP/SP 数据接口管理</b>
							</a>
						</h4>
					</td>
				</tr>
			</table>
			<table style="float: right; text-align: center" align="center">
				<tr>
					<td></td>
					<td></td>
					<td><button
							onclick="window.parent.location.href='${ContextPath}/logout';"
							class="btn btn-default btn-sm"
							style="margin: auto; margin: 1;">
							<span class="glyphicon glyphicon-off"></span> 退出
						</button></td>
				</tr>
				<tr>
					<td></td>
					<td><h6 style="margin: 3px 0 2px 0;">
							<span class="glyphicon glyphicon-user"></span> ${loginUser }&nbsp;&nbsp;
						</h6></td>
					<td><h6 style="margin: 3px 0 2px 0;">
							<span class="glyphicon glyphicon-eye-open"></span> ${currAddr }
						</h6></td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>