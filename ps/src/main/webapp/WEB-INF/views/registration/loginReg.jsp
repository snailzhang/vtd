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
<link rel="stylesheet" type="text/css" href="${contextPath}/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/bootstrap-theme.min.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/login.css" />
<script type="text/javascript" src="${contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container">
	<form action="${contextPath}/loginReg" method="post" id="login" name="login" role="form" class="form-signin" >
		<h2 class="form-signin-heading">请登录</h2>
		<div class="form-group">
			<input type="text" id="username" name="username" autofocus="" required="required" placeholder="用户名" class="form-control">
			<span class="help-block"></span>
		</div>
		<div class="form-group">
			<input type="password" id="pwd" required="required" placeholder="密 码" name="password" class="form-control">
			<span class="help-block"></span>
		</div>
		<div class="form-group">
			<button type="submit" class="btn btn-lg btn-primary btn-block">登录</button>
		</div>
	</form>
</div>
<div align="center">${message}</div>
</body>
<script type="text/javascript">
	$(document).ready(function(){
		$("#username").blur(function(){
			var un = $(this);
			var username = un.val();
			$.ajax({
				type:'get',
				url:'${contextPath}/regCheckUserName',
				data:{"username":username},
				dataType:'json',
				success:function(data){
					if(data.replay == 0){
						un.next(".help-block").css("color","red").text(data.message);
					}else{
						un.next(".help-block").empty();
					}
				}
			});
		});
	});
</script>
</html>
