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
	<jsp:include page="head_reg.jsp" />
	<div class="container">
		<form action="${contextPath}/addDistrict" method="post" id="addUser" name="addUser" role="form" class="form-horizontal">
			<div class="form-group">
		      <label for="userName" class="col-sm-2 control-label">用户名：</label>
		      <div class="col-sm-10">
		         <input type="text" class="form-control" name="userName" id="userName" placeholder="请输入用户名" autocomplete="off" required="required">
		         <span class="help-block"></span>
		      </div>
		   </div>
		   <div class="form-group">
		      <label for="password" class="col-sm-2 control-label">登录密码：</label>
		      <div class="col-sm-10">
		         <input type="password" class="form-control" name="password" id="password" placeholder="请输入密码" autocomplete="off" required="required">
		         <span class="help-block"></span>
		      </div>
		   </div>
		   <div class="form-group">
		      <label for="name" class="col-sm-2 control-label">重复输入密码：</label>
		      <div class="col-sm-10">
		         <input type="password" class="form-control" name="repassword" id="repassword" placeholder="请输入密码" autocomplete="off">
		         <span class="help-block"></span>
		      </div>
		   </div>
		   <div class="form-group">
		      <label for="name" class="col-sm-2 control-label">所属部门：</label>
		      <div class="col-sm-10">
		         <input type="text" class="form-control" name="name" id="name" placeholder="请输入部门名称" autocomplete="off" required="required">
		         <span class="help-block"></span>
		      </div>
		   </div>
		   <div class="form-group">
		      <label for="pinyin" class="col-sm-2 control-label">部门拼音：</label>
		      <div class="col-sm-10">
		         <input type="text" class="form-control" name="pinyin" id="pinyin" placeholder="请输入部门拼音" autocomplete="off" required="required">
		         <span class="help-block"></span>
		      </div>
		   </div>
		   <div class="form-group">
		      <label for="phone" class="col-sm-2 control-label">电话号码：</label>
		      <div class="col-sm-10">
		         <input type="text" class="form-control" name="phone" id="phone" placeholder="请输入电话号码" autocomplete="off" required="required">
		         <span class="help-block"></span>
		      </div>
		   </div>
		   <div class="form-group">
		      <label for="bank" class="col-sm-2 control-label">银行卡号：</label>
		      <div class="col-sm-10">
		         <input type="text" class="form-control" name="bank" id="bank" placeholder="请输入银行卡号" autocomplete="off" required="required">
		         <span class="help-block"></span>
		      </div>
		   </div>
		   <div class="form-group">
		      <label for="address" class="col-sm-2 control-label">联系地址：</label>
		      <div class="col-sm-10">
		         <input type="text" class="form-control" name="address" id="address" placeholder="请输入联系地址" autocomplete="off" required="required">
		         <span class="help-block"></span>
		      </div>
		   </div>
		   <div class="form-group">
		      <div class="col-sm-offset-2 col-sm-10">
		         <button type="submit" class="btn btn-default">添加</button>
		      </div>
		   </div>
		</form>
	</div>
	<script type="text/javascript">
		$(document).ready(function(){
			checkUserPWD = function(){
				var pwd1 = $("#password").val();
				var pwd2 = $("#repassword").val();
				if(pwd1 != pwd2){
					$("#repassword").next(".help-block").css("color","red").text("密码不一致");
					return false;
				}else{
					$("#repassword").next(".help-block").empty();
				}
				return true;
			};
			$("#password,#repassword").blur(function(){
				checkUserPWD();
			});
			$("#addUser").submit(function(){
				if(checkUserPWD()){
					return true;
				};
				return false;
			});
		});
	</script>
</body>
</html>

