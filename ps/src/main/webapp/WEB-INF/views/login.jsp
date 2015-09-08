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
	.datas{padding:0 0 60px 0;}
	.datass{padding-top:15px;background-color:#D2B48C;height: 50px;font-size:17px;font-weight: bold;}
	.datas1{padding:0 0 20px 7%;}
	.login-list{float: left; padding-left:3%;width:22.5%;}
	.login-page{float: right;width:260px; padding-right: 1%;}
	body{font-family: Microsoft YaHei;padding-top: 0;background:#FAFAD2;}
	.section{font-weight: bold;font-size:17px;}

</style>
</head>
<body>
					
<div class="topArea">
	<div  class = "datas">
		<div class = "datass">
			<span id = "peopleCountTotle" class="datas1"></span>
			<span id = "moneyTotle" class="datas1"></span>
			<span id = "taskCountTotle" class="datas1"></span>
			<span id = "taskCount" class="datas1"></span>
			<span id = "moneyToday" class="datas1"></span>
		</div>
	</div>
	<div style="clear: both;"></div>
</div>
<div class="container">
	<div class="login-list">
		<div class="totleList">
			<div class="totleList">
				<table class="table table-striped table-bordered">
				 <caption><span class="section">总榜</span><br><span class = "date-section">(20141226至今)</span></caption>
					<thead>
						<tr align="center" style="font-weight: bold;">
							<td >序号</td>
							<td >姓名</td>
							<td >金额(元)</td>
						</tr>
					</thead>
					<tbody id="totle-list"></tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="login-list">
		<div class="moneyList">
			<div class="monthList">
				<table class="table table-striped table-bordered">
				 <caption><span class="section">月榜</span><br><span id = "monthTime" class = "date-section"></span></caption>
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
				 <caption><span class="section">周榜</span><br><span id = "dateTime" class = "date-section"></span></caption>
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
				<button id = "login-btn" type="button" class="btn btn-primary">登录</button>
				<!-- <button id = "task" type="button" class="btn btn-primary">序列化测试</button> -->
			</div>	
		</form>
	</div>
	<div style="clear: both;"></div>
</div>
<script type="text/javascript">
	$(document).ready(function(){
		datas();
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
		$("button[id=login-btn]").click(function(){
			var formName = $("#login");
			if(checkUserName()&&checkUserPWD())formName.submit();
			
		});
		/*---------------------------------------测试语音波-----------------------------------------------------------*/
		$("button[id=task]").click(function(){
			$.ajax({
				type:'POST',
				url:'${contextPath}/serializeWav',
				dataType:'json',
				success:function(data){
				}		
			});
		});
	});
	/*-------------------------------------------总人数,总金额,任务总量,今日金额--------------------------------------------------------*/
	datas = function(){
			$.ajax({
				type:'POST',
				url:'${contextPath}/datas',
				dataType:'json',
				success:function(data){
					$("#peopleCountTotle").text("总人数 : "+data.peopleCountTotle+"位");
					$("#moneyTotle").text("总金额 : "+data.moneyTotle+"元");
					$("#taskCountTotle").text("总任务数 : "+data.taskCountTotle+"个");
					$("#taskCount").text("可做任务 : "+data.taskCount+"个");
					$("#moneyToday").text("今日金额 : "+data.moneyToday+"元");
				}		
			});
			
		};
	/*-----------------------------------------cx20140108---------------------------------------------------*/
	moneyList = function(){
		$.ajax({
				type:'POST',
				url:'${contextPath}/moneyList',
				dataType:'json',
				success:function(data){
					var date=new Date;
					var year=date.getFullYear(); 
					var month=date.getMonth()+1;
					month =(month<10 ? "0"+month:month); 
					var mydate = (year.toString()+month.toString());
					if(data.totleList == ""){
						$("#totle-list").empty();
						$("#totle-list").append("<tr class='text-danger'><td colspan='3'>无内容</td></tr>");
					}else{
						$.each(data.totleList,function(i,item){
							var tr = "<tr style='color: red;' align='center'>";
							if(i>2){
								var tr = "<tr align='center'>"; 
							}
							$("#totle-list").append(
								tr+
									"<td>"+(i+1)+"</td>"+
									"<td>"+item.realName+"</td>"+
									"<td>"+(item.sumMarkTime*data.salary/3600).toFixed(0)+"</td>"+
								"</tr>"
							);
						});
					}
					if(data.monthList == ""){
						$("#month-list").empty();
						$("#month-list").append("<tr class='text-danger'><td colspan='6'>无内容</td></tr>");
					}else{					
						$("#monthTime").text("("+mydate+")");
						$.each(data.monthList,function(i,item){
							var tr = "<tr style='color: red;' align='center'>";
							if(i>2){
								var tr = "<tr align='center'>"; 
							}
							$("#month-list").append(
								tr+
									"<td>"+(i+1)+"</td>"+
									"<td>"+item.realName+"</td>"+
									"<td>"+(item.sumMarkTime*data.salary/3600).toFixed(0)+"</td>"+
								"</tr>"
							);
						});
					}
					if(data.weekList == ""){
						$("#week-list").empty();
						$("#week-list").append("<tr class='text-danger'><td colspan='6'>无内容</td></tr>");
					}else{
						$("#dateTime").text("("+data.weekDate+")");
						$.each(data.weekList,function(i,item){
							var tr = "<tr style='color: red;' align='center'>";
							if(i>2){
								var tr = "<tr align='center'>"; 
							}
							$("#week-list").append(
								tr+
									"<td>"+(i+1)+"</td>"+
									"<td>"+item.realName+"</td>"+
									"<td>"+(item.sumMarkTime*data.salary/3600).toFixed(0)+"</td>"+
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
