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
<link rel="stylesheet" type="text/css" href="${contextPath}/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/bootstrap-theme.min.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/public.css">
<link href="${contextPath}/css/jquery-ui.min.css" rel="stylesheet">
<link href="${contextPath}/css/jquery-ui.theme.min.css" rel="stylesheet">
<style type="text/css">
	.input-group-addon,.form-control{padding:6px;}
	.ui-datepicker .ui-datepicker-title select{color:#1c94c4};
</style>
<script type="text/javascript" src="${contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/common.js"></script>
<!-- cx20150108 -->
<script type="text/javascript" src="${contextPath}/js/jquery.ui.core.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.ui.datepicker.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.ui.datepicker-ch.js"></script>
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
					<div class="form-group" style="width:170px;">
						<div class="input-group" style="width:170px;">
							<div class="input-group-addon">任务状态</div>
							<select class="form-control" id="statu" >
								<option value="3">全部</option>
								<option value="0">未完成</option>
								<option value="1">完成</option>
								<option value="2">超时/放弃</option>
							</select>
						</div>
					</div>
					<!-- cx20150108 -->
					<div class="form-group" style="width:135px;">
						<div class="input-group">
							<div class="input-group-addon">起始</div>
							<input class="form-control" onkeydown="if(event.keyCode==13){return false;}" id=beginDate type="text" placeholder="起始时间">
						</div>
					</div>
				
			
					<div class="form-group" style="width:135px;">
						<div class="input-group" >
							<div class="input-group-addon">截止</div>
							<input class="form-control" onkeydown="if(event.keyCode==13){return false;}" id="endDate" type="text" placeholder="截止时间">
						</div>
					</div>
				
				
					<div class="form-group">
						<div class="input-group">
							<div class="input-group-addon">类别</div>
							<select class="form-control" id="dateTypeCheck" style="padding-right: 3px;">
								<option value="1">日</option>
								<option value="2">月</option>
								<option value="3">年</option>
							</select>
						</div>
					</div>
					<div style="float: right;">	
						<button type="button" id="searchBtn" class="btn btn-default">查询</button>
						<button type="button" id="changeBtn" class="btn btn-default">修改密码</button>
					</div>	
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
		<!-- cx20150109 changePW -->
		<div class="modal fade" id="changePW">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
						<h4 class="modal-title"></h4>
					</div>
					<div class="modal-body">
						<span id="updateStatus"></span>
						<div style="padding:10px 30px 10px;">
							<form class="form-inline" role="form">
								<div class="input-group" style="padding:3px 3px;">
	        					 	<span class="input-group-addon">输入新密码</span>
	         						<input type="text" class="form-control" id="newPW" placeholder="请输入">
	      						</div>
						   </form>
					   </div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button type="button" id="changePWBtn" class="btn btn-primary">修改</button>
					</div>
				</div><!-- /.modal-content -->
			</div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
	</div>
	<script type="text/javascript">
		var nowUserType = 4;
		var nowPage = 1;
		var taskNameCondition = "";
		var month = 0;
		var statu = 3;
		var year = 0;
		var pageTotal = 0;
		/*-------cx20150108-------*/
		var dateType = 1;
		var beginDate = "";
		var endDate = "";
		/*-------------------------*/
		$(document).ready(function(){
			var date = new Date();
			/*-----------cx20150108--------------------------------------------------*/
			var todayDate = $.datepicker.formatDate( "yy-mm-dd",new Date());
			beginDate = todayDate;
			endDate = todayDate;
			$("#beginDate,#endDate").val(todayDate);
			/*---------------------------------------------------------------------*/
			chooseUserType(1);
			/*-------------------------------datepicker-----------------------------------------*/
			$.datepicker.regional[ "ch" ];
			$("#beginDate").datepicker({
				changeMonth:true,
				changeYear:true,
				numberOfMonths:1,
				setDate:todayDate,
				onClose: function( selectedDate ) {
					$( "#endDate" ).datepicker( "option", "minDate", selectedDate );
				}
			});
			$("#endDate").datepicker({
				changeMonth:true,
				changeYear:true,
				numberOfMonths:1,
				onClose: function( selectedDate ) {
					$( "#beginDate" ).datepicker( "option", "maxDate", selectedDate );
				}
			});
			/*-------------------------------------------------------------------------------*/
			$("#searchBtn").click(function(){
				/*------------------------------cx20150108-------------------------------*/
				dateType = $("#dateTypeCheck").val();
				beginDate = $("#beginDate").val();
				endDate = $("#endDate").val();
				/*---------------------------------------------------------------------*/
				taskUpload = $("#taskUpload").val();
				taskNameCondition = $("#taskNameCondition").val();
				statu = $("#statu").val();
				chooseUserType(1);
				
			});
			
		});
		chooseUserType = function(pageNum){
			$.ajax({
				type:'POST',
				data:{"userId":'${model.userId}',"page":pageNum,"taskNameCondition":taskNameCondition,"statu":statu,"userType":nowUserType,"dateType":dateType,"beginDate":beginDate,"endDate":endDate},
				url:'${contextPath}/security/workerDetail',
				dataType:'json',
				success:function(data){
					$("tbody").empty();
					if(data.list == ""){
						$("tbody").empty();
						$("#taskMarkTimeMonthTotle").text("有效标注:0");
						$("tbody").append("<tr class='text-danger'><td colspan='7'>无内容</td></tr>");
					}else{
						pageTotal = data.totlePage;
						$("#taskMarkTimeMonthTotle").text("有效标注:"+data.taskMarkTimeMonth);
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
		};
		/*--------------------------------------cx点击修改密码按钮-------------------------------------------------------*/
			$("#changeBtn").click(function(){
				var userName = "${model.chooseUserName}";
				$(".modal-title").text("修改"+userName+"密码");
				$("#changePW").modal('show');
			});
		/*--------------------------------------cx 点击修改按钮---------------------------------------------------*/
			$("#changePWBtn").click(function(){
				var newPW = $("#newPW").val();
				var userId = "${model.userId}";
				var userName = "${model.chooseUserName}";
				var conWin = confirm("确定要修改密码!");
				if(conWin){
					$.ajax({
						type:'POST',
						data:{"newPW":newPW,"userId":userId,"userName":userName},
						url:'${contextPath}/security/updatePW',
						dataType:'json',
						success:function(data){
							if(data.replay == 1){
								alert("修改成功");
							}
						}
					});
				}						
			});
	</script> 
</body>
</html>
