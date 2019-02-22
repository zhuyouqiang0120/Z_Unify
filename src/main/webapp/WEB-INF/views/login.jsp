<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"
	name="viewport" />
<link href="${ContextPath}/static/bootstrap/css/bootstrap.min.css"
	media="all" rel="stylesheet" type="text/css" />
<link href="${ContextPath}/static/bootstrap/css/login.css"
	media="all" rel="stylesheet" type="text/css" />
<link href="${ContextPath}/static/img/fault.ico" rel="shortcut icon" />
<title>CP/SP 数据接口管理</title>
<style type="text/css">
body {
	background: url(${ContextPath}/static/img/bg.png) no-repeat center
		center;
}
#loginBody {
	-webkit-animation: fadeInLeft 1s .2s ease both;
	-moz-animation: fadeInLeft 1s .2s ease both;
}

@-webkit-keyframes fadeInLeft { 
	0%{	opacity: 0;	-webkit-transform: translateX(-40px)}
	100%{opacity:1; -webkit-transform:translateX(0)}
}
@-moz-keyframes fadeInLeft { 
	0%{	opacity: 0;	-moz-transform: translateX(-40px)}
	100%{opacity:1;	-moz-transform:translateX(0)}
}
</style>
</head>
<body class="login1">
	<!-- Login Screen -->
	<div class="login-wrapper">
		<div class="login-container active" id="loginBody">
			<div class="row">
				<div class="col-md-2">
					<img src="${ContextPath}/static/img/zens_logo.png"
						style="margin-left: -25px; width: 80px; height: 60px;" />
				</div>
				<div class="col-md-10">
					<h3 style="font-size: 24px; line-height: 80px;">
						<b>CP/SP 数据接口管理</b>
					</h3>
				</div>
			</div>
			<form action="${ContextPath}/login" method="post">
				<div class="form-group">
					<input type="text" class="form-control" name="username"
						placeholder="用户名">
				</div>
				<div class="form-group">
					<input type="password" class="form-control" name="password"
						placeholder="密码">
				</div>
				<div id="loginErr"
					style="color: red; font-size: 18px; margin: auto; line-height: 20px;">${loginErr}</div>
				<div class="form-group" style="padding-top: 10px;">
					<button type="submit" class="btn btn-primary">
						<span class="glyphicon glyphicon-log-in"></span> 登录
					</button>
					<button type="reset" class="btn btn-default">
						<span class="glyphicon glyphicon-log-out"></span> 取消
					</button>
				</div>
			</form>
			<div class="signup" style="padding-top: 60px;">
				<h5>上海仁视信息科技有限公司</h5>
			</div>
		</div>
	</div>
	<!-- End Login Screen -->
</body>
<script type="text/javascript">
</script>
</html>