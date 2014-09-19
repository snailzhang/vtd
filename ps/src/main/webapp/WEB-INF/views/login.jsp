<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="shortcut icon" href="${contextPath }/images/logo.ico" />
<title>残疾人就业保障金</title>
<link rel="stylesheet" type="text/css" href="${contextPath}/js/lib/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/js/lib/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/cs.css" />
<script type="text/javascript" src="${contextPath}/js/lib/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/lib/jquery.easyui.min.js"></script>
<script type="text/javascript">
	function getimgcode() {
		var timestamp = new Date().getTime();
		$("#loginVerifyCode").attr("src", "${contextPath}/captcha/create?" + timestamp);
	}
	function submit() {
	/*	var code = $('input[name=checkCode]').val();
		if (code == '' || code == undefined) {
			$('#message').html("验证码不能不空");
			return;
		}	*/
		$("#loginform").submit();
	}
	$(function() {
		getimgcode();
		$("body").keypress(function(event) {
			if (event.keyCode == 13) {
				submit();
			}
		});
	});
</script>
<style type="text/css">
p {
	line-height: 30px;
}

img {
	width: 100px;
	height: 30px;
	float: right;
	cursor: pointer;
}
.bbc-name {
font-size: 28px;
font-family: 'Microsoft YaHei';
color: #0074A7;
text-align: center;
padding: 40px 0 0 0;
}
</style>
</head>
<body style="background-color: #E9E9E9;">
	<h2 class="bbc-name bbc-shop" >残疾人就业保障金管理系统</h2>
	<h2 style="color: #0074A7; text-align: center;" >Handicapped Employment Security Fund</h2>
	<div id="main" style=" display: none;">				<!-- 暂时高度定位250, 应为350比较好 -->
		<div id="win" class="easyui-window" title="登录 v1.0-Bate" style="width:450px; height:250px;" collapsible="false" minimizable="false" maximizable="false" closable="false">
			<form id="loginform" action="${contextPath}/login" method="post" style="padding-left: 80px; padding-top: 30px;">
				<div style="text-align: left;">
					<div style="color: red;" id="message">${message}</div>
					<p>
						<span style="display:-moz-inline-block;display:inline-block;width:50px;">用户名:</span><input name="username" type="text" style="width: 140px;padding-left: 5px;" value="admin" />
					</p>
					<p>
						<span style="display:-moz-inline-block;display:inline-block;width:50px;">密&nbsp;&nbsp;&nbsp;码:</span><input name="password" type="password" style="width: 140px;padding-left: 5px;" value="123123" />
					</p>
				<!-- 	<div>
						<div style="height: 30px; width: 216px;">
							<span style="display:-moz-inline-block;display:inline-block;width:50px;">验证码:</span><input name="checkCode" type="text" style="width: 60px;padding-left:5px; " /> 
							<img id="loginVerifyCode" onclick="getimgcode();" alt="验证码" style="width:90px;height:30px;"/>
						</div>
					</div>	 -->
				</div>
				<div style="padding-left: 40px;">
				 	<p style="line-height:20px;">
					<!--	<a href="javascript:getimgcode();" style="text-decoration:none;font-size:13px;margin-left:10px;">看不清验证码</a> -->
					</p>	
					<a id="login" href="javascript:submit();" plain="false" class="easyui-linkbutton" icon="icon-ok" style="margin-top:0px;margin-bottom:0px;">登录</a>
				</div>
			</form>
		</div>
	</div>
</body>
</html>