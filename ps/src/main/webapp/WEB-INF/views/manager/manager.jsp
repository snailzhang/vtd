<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>管理者页面</title>
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
</head>
<body>
	<jsp:include page="../head.jsp" />
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">用户列表<span id="taskMarkTimeMonthTotle" class="pull-right text-success"></span></div>
			<div class="panel-body">
				<form class="form-inline" role="form">
					<div class="form-group">
						<div class="input-group">
							<div class="input-group-addon">用户名：</div>
							<input class="form-control" onkeydown="if(event.keyCode==13){return false;}" id="userNameCondition" type="text" placeholder="查询用户">
						</div>
					</div>
					<div class="form-group">
						<p class="form-control-static">年份：</p>
					</div>
					<div class="form-group">
						<select class="form-control" id="year">
							<option value="2014">2014年</option>
							<option value="2015">2015年</option>
							<option value="2016">2016年</option>
							<option value="2017">2017年</option>
							<option value="2018">2018年</option>
						</select>
					</div>
					<div class="form-group">
						<p class="form-control-static">月份：</p>
					</div>
					<div class="form-group">
						<select class="form-control" id="month">
							<option value="1">1月</option>
							<option value="2">2月</option>
							<option value="3">3月</option>
							<option value="4">4月</option>
							<option value="5">5月</option>
							<option value="6">6月</option>
							<option value="7">7月</option>
							<option value="8">8月</option>
							<option value="9">9月</option>
							<option value="10">10月</option>
							<option value="11">11月</option>
							<option value="12">12月</option>
						</select>
					</div>
					<div class="form-group">
						<p class="form-control-static">本月工作状态：</p>
					</div>
					<div class="form-group">
						<select class="form-control" id="taskUpload">
							<option value="2">全部</option>
							<option value="1">工作</option>
							<option value="0">无工作</option>
						</select>
					</div>
					<button type="button" id="searchBtn" class="btn btn-default">查询</button>
				</form>
			</div>
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<th>序号</th>
						<th>姓名</th>
						<th>用户组</th>
						<th>创建时间</th>
						<th>标注时间</th>
						<th>用户状态</th>
					</tr>
				</thead>
				<tbody></tbody>
			</table>
			<ul class="pagination"></ul>
		</div>
		
		<form role="form" class="form-signin" action="${contextPath}/security/addUser" method="get">
			<button type="submit" class="btn btn-lg btn-primary btn-block">添加用户</button>
		</form>
		
	</div>
	<script type="text/javascript">
		var utDropdownOpen = false;
		var nowUserType = 4;
		var nowPage = 0;
		var userNameCondition = "";
		var month = 0;
		var taskUpload = 2;
		var year = 0;
		$(document).ready(function(){
			var date = new Date();
			var nowMonth = date.getMonth()+1;
			var nowYear = date.getYear();
			month = nowMonth;
			year = nowYear;
			$("#month option[value="+month+"]").attr("selected","selected");
			$("#year option[value="+year+"]").attr("selected","selected");
			chooseUserType(1);
			$("#searchBtn").click(function(){
				month = $("#month").val();
				year = $("#year").val();
				taskUpload = $("#taskUpload").val();
				userNameCondition = $("#userNameCondition").val();
				chooseUserType(1);
			});
		});
		chooseUserType = function(pageNum){
			$.ajax({
				type:'POST',
				data:{"userType":nowUserType,"page":pageNum,"userNameCondition":userNameCondition,"month":month,"taskUpload":taskUpload,"year":year},
				url:'${contextPath}/security/manager',
				dataType:'json',
				success:function(data){
					$("tbody").empty();
					if(data.list == ""){
						$("tbody").empty();
						$("tbody").append("<tr class='text-danger'><td colspan='6'>无内容</td></tr>");
					}else{
						var taskMarkTimeMonthTotle = data.taskMarkTimeMonthTotle;
						$("#taskMarkTimeMonthTotle").text("本月标注总时长："+taskMarkTimeMonthTotle);
						var pageTotal = data.totlePage;
						$.each(data.list,function(i,item){
							var status = "不可用";
							if(item.userStatus)status = "可用";
							$("tbody").append(
								"<tr>"+
									"<td>"+(i+1)+"</td>"+
									"<td><a target='_blank' href='${contextPath}/security/workerDetail?userId="+item.userId+"&username="+item.username+"'>"+item.username+"</a></td>"+
									"<td>"+item.usertypeenglish+"</td>"+
									"<td>"+item.createTime+"</td>"+
									"<td>"+item.taskMarkTimeMonth+"</td>"+
									"<td class='userStatus'><a href='#'>"+status+"</a></td>"+
								"</tr>"
							);
							$(".pagination").empty();
							for(var i=1;i<pageTotal+1;i++){
								if(i==pageNum){
									$(".pagination").append(
										"<li class='active'><a onClick='chooseUserType("+i+")'>"+
										i+
										"</a></li>"
									);
								}else{
									$(".pagination").append(
										"<li><a onClick='chooseUserType("+i+")'>"+
										i+
										"</a></li>"
									);
								}
							}
						});
					}
				}
			});
		}
	</script>
</body>
</html>
