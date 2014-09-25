<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
	<title>vtd的 'login.jsp'page</title> 
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta content="width=device-width, initial-scale=1" name="viewport">
	<link rel="stylesheet" type="text/css" href="http://cdn.bootcss.com/bootstrap/3.2.0/css/bootstrap.min.css" />
	<link rel="stylesheet" type="text/css" href="http://cdn.bootcss.com/bootstrap/3.2.0/css/bootstrap-theme.min.css" />
	<link rel="stylesheet" type="text/css" href="<%= basePath%>css/login.css" />
</head>  
<body>

<div class="container">
	<form action="<%=basePath%>logins" method="post" name="form1" role="form" class="form-signin">
		<h2 class="form-signin-heading">请登录</h2>
		<input type="text" name="username" autofocus="" required="" placeholder="用户名" class="form-control">
		<input type="password" required="" placeholder="密 码" name="password" class="form-control">
		<button type="submit" class="btn btn-lg btn-primary btn-block">登录</button>
	</form>
</div>
<div align="center">${loginreplay}</div>
<script type="text/javascript" src="http://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</body>

</html>
