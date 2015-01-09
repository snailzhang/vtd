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
<script type="text/javascript" src="${contextPath}/js/common.js"></script>
<style type="text/css">
	.login-list{float: left; padding-left:3%;width:30%;}
	.login-page{float: right;width:30%; padding-right: 5%;}
	body{font-family: Microsoft YaHei;}
	caption{font-weight: bold;font-size:20px;};
</style>
</head>
<body>
		
<div class="container">
	<div class="login-list">
		<div class="moneyList">
			<div class="monthList">
				<table class="table table-striped table-bordered">
				 <caption>月榜</caption>
					<thead>
						<tr align="center" style="font-weight: bold;">
							<td >序号</td>
							<td >姓名</td>
							<td >金额(元)</td>
						</tr>
					</thead>
					<tbody id="month-list"></tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="login-list">
		<div class="moneyList">
			<div class="weekList">
				<table class="table table-striped table-bordered">
				 <caption>周榜</caption>
					<thead>
						<tr align="center" style="font-weight: bold;">
							<td>序号</td>
							<td>姓名</td>
							<td>金额(元)</td>
						</tr>
					</thead>
					<tbody id="week-list"></tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="login-page">
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
			<div id = "message"  align="left" style="font-size: 14px;color:red;">${message}</div>
			<div class="form-group">
				<!-- <button type="button" class="btn btn-lg btn-primary btn-block">登录</button> -->
				<button type="button" class="btn btn-primary">登录</button>
			</div>	
		</form>
	</div>
	<div style="clear: both;"></div>
</div>


<script type="text/javascript">
	$(document).ready(function(){
		moneyList();
		checkUserName = function(){
			var user = $("#username");
			if(checkout.text.isempty(user,"请填写用户名！")){
				return false;	
			}
			return true;
		};
		checkUserPWD = function(){
			var pwd = $("#pwd");
			if(checkout.text.isempty(pwd,"请填写密码！")){
				$("#message").empty();
				return false;	
			}
			return true;
		};
		checkOldTask();
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
					if(data.replay == "0"){
						user.next(".help-block").removeClass("text-success").addClass("text-danger").text(data.message);
					}else{
						user.next(".help-block").removeClass("text-danger").addClass("text-success").text(data.message);
					}
				}
			});
		});
		$("button[type=button]").click(function(){
			var formName = $("#login");
			if(checkUserName()&&checkUserPWD())formName.submit();
			
		});
		
	});
	
		/*-----------------------------------------cx20140108---------------------------------------------------*/
	moneyList = function(){
		$.ajax({
				type:'POST',
				url:'${contextPath}/moneyList',
				dataType:'json',
				success:function(data){
					if(data.monthList == ""){
						$("#month-list").empty();
						$("#month-list").append("<tr class='text-danger'><td colspan='6'>无内容</td></tr>");
					}else{
						$.each(data.monthList,function(i,item){
							var tr = "<tr style='color: red;' align='center'>";
							if(i>2){
								var tr = "<tr align='center'>"; 
							}
							$("#month-list").append(
								tr+
									"<td>"+(i+1)+"</td>"+
									"<td>"+item.realName+"</td>"+
									"<td>"+(item.sumMarkTime*data.salary/3600).toFixed(2)+"</td>"+
								"</tr>"
							);
						});
					}
					if(data.weekList == ""){
						$("#week-list").empty();
						$("#week-list").append("<tr class='text-danger'><td colspan='6'>无内容</td></tr>");
					}else{
						$.each(data.weekList,function(i,item){
							var tr = "<tr style='color: red;' align='center'>";
							if(i>2){
								var tr = "<tr align='center'>"; 
							}
							$("#week-list").append(
								tr+
									"<td>"+(i+1)+"</td>"+
									"<td>"+item.realName+"</td>"+
									"<td>"+(item.sumMarkTime*data.salary/3600).toFixed(2)+"</td>"+
								"</tr>"
							);
						});
					}
				}
			});
			};
	/*-------------------------------------------请求检查超时任务cx20150109--------------------------------------------------------*/
		checkOldTask = function(){
			$.ajax({
				type:'POST',
				url:'${contextPath}/checkOldTask',
				dataType:'json',
				success:function(data){}		
			});
			
		};
		/*---------------------------------------------------------------------------------------------------*/
</script>
</body>

</html>
