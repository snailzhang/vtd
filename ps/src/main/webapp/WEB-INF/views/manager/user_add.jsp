<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>添加用户</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta content="width=device-width, initial-scale=1" name="viewport">
<link rel="stylesheet" type="text/css" href="http://cdn.bootcss.com/bootstrap/3.2.0/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="http://cdn.bootcss.com/bootstrap/3.2.0/css/bootstrap-theme.min.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/public.css">
<script type="text/javascript" src="http://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/common.js"></script>
<body>
	<jsp:include page="../head.jsp" />
	<div class="container">
		<form action="${contextPath}/addUser" method="post" id="addUser" name="addUser" role="form" class="form-horizontal">
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
		         <button type="button" class="btn btn-default">添加</button>
		      </div>
		   </div>
		</form>
	</div>
	<script type="text/javascript">
		$(document).ready(function(){
			$("button[type=button]").click(function(){
				var formName = $("#addUser");
				var username = $("#username").val();
				if(checkout.text.isempty(username,"用户名不能为空！")) return;
				var pwd1 = $("#password").val();
				var pwd2 = $("#repassword").val();
				if(checkout.text.isempty(pwd1,"密码不能为空！"))return;
				if(pwd1 == pwd2){
					formName.submit();
				}else{
					alert("密码不一致");
				}
			});
		});
	</script>
</body>
</html>

