<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
	<head>
		<title>添加用户</title>
		<meta content="width=device-width, initial-scale=1" name="viewport">
		<link rel="stylesheet" type="text/css" href="http://cdn.bootcss.com/bootstrap/3.2.0/css/bootstrap.min.css" />
		<link rel="stylesheet" type="text/css" href="http://cdn.bootcss.com/bootstrap/3.2.0/css/bootstrap-theme.min.css" />
	</head>
	<body>
		<nav role="navigation" class="navbar navbar-inverse navbar-fixed-top">
			<div class="container">
				<div class="navbar-header">
					<a href="#" class="navbar-brand"> 添加用户:${loginrName}</a>
				</div>
			</div>
		</nav>
		<div class="container">
			<form action="<%=basePath%>addUser" method="post" name="form1" role="form" class="form-horizontal">
				<div class="form-group">
			      <label for="username" class="col-sm-2 control-label">用户名：</label>
			      <div class="col-sm-10">
			         <input type="text" class="form-control" name="username" id="username" placeholder="请输入用户名">
			      </div>
			   </div>
			   <div class="form-group">
			      <label for="password" class="col-sm-2 control-label">登录密码：</label>
			      <div class="col-sm-10">
			         <input type="password" class="form-control" name="password" id="password" placeholder="请输入密码">
			      </div>
			   </div>
			   <div class="form-group">
			      <label for="repassword" class="col-sm-2 control-label">重复输入密码：</label>
			      <div class="col-sm-10">
			         <input type="password" class="form-control" name="repassword" id="repassword" placeholder="请输入密码">
			      </div>
			   </div>
			   <div class="form-group">
			      <label for="usertype" class="col-sm-2 control-label">选择用户类别：</label>
			      <div class="col-sm-10">
			         <select class="form-control" id="usertype" name="usertype">
				         <option value="1">管理员</option>
				         <option value="2">发包商</option>
				         <option value="4">工作者</option>
				      </select>
			      </div>
			   </div>
			   <div class="form-group">
			      <div class="col-sm-offset-2 col-sm-10">
			         <button type="submit" class="btn btn-default">添加</button>
			      </div>
			   </div>
			</form>
		</div>
		<script type="text/javascript" src="http://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
		<script type="text/javascript" src="http://cdn.bootcss.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
		<script type="text/javascript">
			$("button[type=submit]").click(function(){
				var pwd1 = $("#password").val().trim();
				
			});
		</script>
	</body>
</html>

