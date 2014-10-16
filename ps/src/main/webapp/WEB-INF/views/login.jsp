<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>登录页面</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta content="width=device-width, initial-scale=1" name="viewport">
<link rel="stylesheet" type="text/css" href="http://cdn.bootcss.com/bootstrap/3.2.0/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="http://cdn.bootcss.com/bootstrap/3.2.0/css/bootstrap-theme.min.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/login.css" />
<script type="text/javascript" src="http://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/common.js"></script>
</head>
<body>

<div class="container">
	<form action="${contextPath}/login" method="post" id="login" name="login" role="form" class="form-signin" >
		<h2 class="form-signin-heading">请登录</h2>
		<div class="form-group">
			<input type="text" id="username" name="username" autofocus="" required="" placeholder="用户名" class="form-control">
			<span class="help-block"></span>
		</div>
		<div class="form-group">
			<input type="password" id="pwd" required="" placeholder="密 码" name="password" class="form-control">
			<span class="help-block"></span>
		</div>
		<div class="form-group">
			<button type="button" class="btn btn-lg btn-primary btn-block">登录</button>
		</div>
	</form>
</div>
<div align="center">${message}</div>

<script type="text/javascript">
	$(document).ready(function(){
		checkUserName = function(){
			var user = $("#username");
			var userValue = user.val();
			if(checkout.text.isempty(user,"请填写用户名！")){
				return false;	
			}
			return true;
		};
		checkUserPWD = function(){
			var pwd = $("#pwd");
			if(checkout.text.isempty(pwd,"请填写密码！")){
				return false;	
			}
			return true;
		};
		$("#username").blur(function(){
			checkUserName();
			var user = $("#username");
			var userValue = user.val();
			$.ajax({
				type:'get',
				url:'${contextPath}/checkUserName',
				data:"username="+userValue,
				dataType:'json',
				success:function(data){
					if(data == "false"){
						user.next(".help-block").css("color","red").text("用户不存在");
					}
				}
			});
		});
		$("button[type=button]").click(function(){
			var formName = $("#login");
			if(checkUserName()&&checkUserPWD())formName.submit();
			
		});
	});
</script>
</body>

</html>
