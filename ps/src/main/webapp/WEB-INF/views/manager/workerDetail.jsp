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
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">${model.chooseUserName}的工作信息<span id="taskMarkTimeMonthTotle" class="pull-right text-success"></span></div>
			<div class="panel-body">
				<form class="form-inline" role="form">
					<div class="form-group">
						<div class="input-group">
							<div class="input-group-addon">任务名称：</div>
							<input class="form-control" onkeydown="if(event.keyCode==13){return false;}" id="taskNameCondition" type="text" placeholder="查询任务">
						</div>
					</div>
					<div class="form-group">
						<p class="form-control-static">任务状态：</p>
					</div>
					<div class="form-group">
						<select class="form-control" id="statu">
							<option value="3">全部</option>
							<option value="0">未完成</option>
							<option value="1">完成</option>
							<option value="2">超时/放弃</option>
						</select>
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
					
					<button type="button" id="searchBtn" class="btn btn-default">查询</button>
				</form>
			</div>
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<th width='6%'>序号</th>
						<th width='20%'>任务名称</th>
						<th width='20%'>下载时间</th>
						<th width='20%'>上传时间</th>
						<th width='8%'>任务状态</th>
						<th width='18%'>标注时间</th>
						<th width='8%'>审核状态</th>
					</tr>
				</thead>
				<tbody></tbody>
			</table>
			<ul class="pagination"></ul>
		</div>
	</div>
	<script type="text/javascript">
		var nowUserType = 4;
		var nowPage = 1;
		var taskNameCondition = "";
		var month = 0;
		var statu = 3;
		var year = 0;
		var pageTotal = 0;
		$(document).ready(function(){
			var date = new Date();
			var nowMonth = date.getMonth()+1;
			var nowYear = date.getFullYear();
			month = nowMonth;
			year = nowYear;
			$("#month option[value="+month+"]").attr("selected","selected");
			$("#year option[value="+year+"]").attr("selected","selected");
			chooseUserType(1);
			$("#searchBtn").click(function(){
				month = $("#month").val();
				year = $("#year").val();
				taskUpload = $("#taskUpload").val();
				taskNameCondition = $("#taskNameCondition").val();
				statu = $("#statu").val();
				chooseUserType(1);
			});
			
		});
		chooseUserType = function(pageNum){
			$.ajax({
				type:'POST',
				data:{"userId":'${model.userId}',"page":pageNum,"taskNameCondition":taskNameCondition,"month":month,"statu":statu,"year":year,"userType":nowUserType},
				url:'${contextPath}/security/workerDetail',
				dataType:'json',
				success:function(data){
					$("tbody").empty();
					if(data.list == ""){
						$("tbody").empty();
						$("#taskMarkTimeMonthTotle").text("本月有效标注时长:0");
						$("tbody").append("<tr class='text-danger'><td colspan='7'>无内容</td></tr>");
					}else{
						pageTotal = data.totlePage;
						$("#taskMarkTimeMonthTotle").text("本月有效标注时长:"+data.taskMarkTimeMonth);
						$.each(data.list,function(i,item){
							
							$("tbody").append(
								"<tr>"+
									"<td>"+(i+1)+"</td>"+
									"<td>"+item.taskName+"</a></td>"+
									"<td>"+item.taskDownTime+"</td>"+
									"<td>"+item.taskUploadTime+"</td>"+
									"<td>"+item.taskStatu+"</td>"+
									"<td>"+item.taskMarkTime+"</td>"+
									"<td>"+item.taskEffective+"</td>"+
								"</tr>"
							);
							var pageDom = $(".pagination");
							pageDom.empty();
							page.creatPageHTML(pageNum,pageTotal,pageDom,"chooseUserType");
							/*--------------------------------------跳转页-------------------------------------------------------*/
							$(".pageGoBtn").click(function(){
								var pageNum = 0;
								pageNum = $(".pageGoText").val();
								if(pageNum !=0&&0<pageNum&&pageNum<pageTotal+1){
									chooseUserType(pageNum);
								}
							});
						});
					}
				}
			});
		}
	</script>
</body>
</html>
