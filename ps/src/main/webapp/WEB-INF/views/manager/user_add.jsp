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
		<form action="${contextPath}/security/addUser" method="post" id="addUser" name="addUser" role="form" class="form-horizontal">
			<div class="form-group">
		      <label for="username" class="col-sm-2 control-label">用户名：</label>
		      <div class="col-sm-10">
		         <input type="text" class="form-control" name="username" id="username" placeholder="请输入用户名">
		         <span class="help-block"></span>
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
		         <span class="help-block"></span>
		      </div>
		   </div>
		   <div class="form-group">
		      <label for="usertype" class="col-sm-2 control-label">选择用户类别：</label>
		      <div class="col-sm-10">
		         <select class="form-control" id="usertype" name="usertype">
			         <option value="1" checked="checked">管理员</option>
			         <option value="2">发包商</option>
			         <option value="4">工作者</option>
			      </select>
		      </div>
		   </div>
		   <div class="form-group">
		      <div class="col-sm-offset-2 col-sm-10">
		         <button type="button" disabled="disabled" class="btn btn-default">添加</button>
		      </div>
		   </div>
		</form>
	</div>
	<script type="text/javascript">
		$(document).ready(function(){
			var userOnly = false;
			var pwdReady = false;
			checkSubBtnStaus = function(){
				if(userOnly&&pwdReady){
					$("button").removeAttr("disabled");
				}else{
					$("button").attr("disabled","disabled");
				}
			};
			$("#username").blur(function(){
				var user = $("#username");
				var userValue = user.val();
				if(checkout.text.isempty(user,"用户名不能为空！")){
					userOnly = false;
					return;	
				}
				$.ajax({
					type:'get',
					url:'${contextPath}/security/checkUserName',
					data:"username="+userValue,
					dataType:'text',
					success:function(data){
						if(data == "true"){
							userOnly = false;
							user.next(".help-block").css("color","red").text("用户名重复");
						}else{
							userOnly = true;
							user.next(".help-block").css("color","green").text("用户名可用");
						}
						checkSubBtnStaus();
					}
				});
				
			});
			$("#repassword").blur(function(){
				var pwd1 = $("#password").val();
				var pwd2Obj = $("#repassword");
				var pwd2 = pwd2Obj.val();
				if(checkout.text.isempty(pwd2Obj,"密码不能为空！")){
					pwdReady = false;
					return;
				};
				if(pwd1 == pwd2){
					pwdReady = true;
				}else{
					pwdReady = false;
					pwd2Obj.next(".help-block").css("color","red").text("密码不一致");
				}
				checkSubBtnStaus();
			});
			$("button[type=button]").click(function(){
				var formName = $("#addUser");
				formName.submit();
			});
		});
	</script>
</body>
</html>

