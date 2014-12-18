<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>修改密码</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta content="width=device-width, initial-scale=1" name="viewport">
<link rel="stylesheet" type="text/css" href="${contextPath}/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/bootstrap-theme.min.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/public.css">
<script type="text/javascript" src="${contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/common.js"></script>
</head>
<body>
	<jsp:include page="../head.jsp" />
	<form role="form" class="form-horizontal" id="updatePWDForm">
		<div id="updPWD" class="container">
			<h1 id="" class="page-header">密码修改</h1>
			<div id=message></div>
			<div class="form-group">
		      <label for="username" class="col-sm-2 control-label">现在的密码：</label>
		      <div class="col-sm-10">
		         <input type="password" class="form-control" name="oldPassWord" id="oldPassWord" placeholder="请输入原密码" autocomplete="off">
		         <span class="help-block"></span>
		      </div>
		   </div>
		   <div class="form-group">
		      <label for="username" class="col-sm-2 control-label">设置新密码：</label>
		      <div class="col-sm-10">
		         <input type="password" class="form-control" name="newPassWord" id="newPassWord" placeholder="请输入新密码" autocomplete="off">
		         <span class="help-block"></span>
		      </div>
		   </div>
		   <div class="form-group">
		      <label for="username" class="col-sm-2 control-label">重复新密码：</label>
		      <div class="col-sm-10">
		         <input type="password" class="form-control" id="reNewPassword" placeholder="请重复输入新密码" autocomplete="off">
		         <span class="help-block"></span>
		      </div>
		   </div>
		   <div class="form-group">
		      <div class="col-sm-offset-2 col-sm-10">
		         <button id="updBtn" type="button" class="btn btn-danger">修改</button>
		      </div>
		   </div>
		</div>
		
	</form>
	
	<script type="text/javascript">
		var pwdCorrect = false;
		checkPwdCorrect = function(){
			var pwd = $("#oldPassWord").val();
			$.ajax({
				type:'post',
				url:'${contextPath}/security/checkPassWord',
				data:{"oldPassWord":pwd},
				dataType:'json',
				success:function(data){
					if(!data.replay){
						$("#oldPassWord").next(".help-block").css("color","red").text(data.message);
						pwdCorrect = false;
					}else{
						$("#oldPassWord").next(".help-block").css("color","green").text(data.message);
						pwdCorrect = true;
					}
				}
			});
		};
		checkPwd = function(){
			var pwd = $("#oldPassWord");
			if(checkout.text.isempty(pwd,"原密码不能为空！")){
				return false;	
			}
			checkPwdCorrect();
			return true;
		};
		checkNewPwd = function(){
			var npwd = $("#newPassWord");
			var rnpwd = $("#reNewPassword");
			var np = npwd.val();
			var rnp = rnpwd.val();
			if(checkout.text.isempty(npwd,"新密码不能为空！")){
				return false;	
			}
			if(np != rnp){
				rnpwd.next(".help-block").css("color","red").text("与新密码不一致！");
				return false;
			}else{
				rnpwd.next(".help-block").empty();
			}
			return true;
		};
		
		$(document).ready(function(){
			$("#oldPassWord").blur(function(){
				checkPwd();
			});
			$("#newPassWord,#reNewPassword").blur(function(){
				checkNewPwd();
			});
			$("#updBtn").click(function(){
				var un = "${username}";
				if(!checkPwd())return;
				if(!checkNewPwd())return;
				if(!pwdCorrect)return;
				var npwd = $("#newPassWord").val();
				var pwd = $("#oldPassWord").val();
				$.ajax({
					type:'post',
					url:'${contextPath}/security/updatePassWord',
					data:{"oldPassWord":pwd,"newPassWord":npwd,"username":un},
					dataType:'json',
					success:function(data){
						if(!data.replay){
							var msgS = '<div class="alert alert-danger alert-dismissable">'
										+ '<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>'
										+ data.message + '</div>';
							$("#message").html(msgS);
						}else{
							var msgS = '<div class="alert alert-success alert-dismissable">'
										+ '<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>'
										+ data.message + '</div>';
							$("#message").html(msgS);
						}
						$("input[type=password]").val("");
						$(".help-block").empty();
					}
				});
			});
		});
			
		

	</script>
</body>
</html>
