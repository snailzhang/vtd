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
			<div class="panel-heading">待审核任务信息<span id="taskUlT" class="pull-right text-success"></span></div>
			<div class="panel-body">
				<form class="form-inline" role="form">
					
					<div class="form-group">
						<p class="form-control-static">审核结果：</p>
					</div>
					<div class="form-group">
						<select class="form-control" id="statu">
							<option value="0">不合格</option>
							<option value="1">合格</option>
						</select>
					</div>
					<div class="form-group" id="reTime">
						<div class="input-group">
							<span class="input-group-addon">修改回传时间：</span>
							<input class="form-control" onkeydown="if(event.keyCode==13){return false;}" id="day" type="number" min="0" placeholder="填入数字，修改回传时间">
							<span class="input-group-addon">小时</span>
						</div>
					</div>
					<button type="button" id="submitBtn" class="btn btn-danger">提交审核结果</button>
				</form>
				
			</div>
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<th width='6%'>序号</th>
						<th width='20%'>任务名称</th>
						<th width='20%'>上传时间</th>
						<th width='18%'>标注时间</th>
					</tr>
				</thead>
				<tbody></tbody>
			</table>
		</div>
	</div>
	<script type="text/javascript">
		var workerId = '${workerId}';
		var firstDate = "";
		var day = 0;
		$(document).ready(function(){
			loadTaskList();
			$("#statu").change(function(){
				$this = $(this);
				$this.val() == "0"?$("#reTime").show():$("#reTime").hide();
			});
			$("#submitBtn").click(function(){
				var sta = $("#statu").val();
				if(sta == 0){
					day = $("#day").val();
					if(day == "")day = 0;
					if(day == 0){
						$("#day").parent(".input-group").addClass(".has-error").focus();
						return;
					}else{
						$(".has-error").removeClass(".has-error");
					}
				}
				$.ajax({
					type:'POST',
					data:{"workerId":workerId,"taskEffective":sta,"day":day,"firstDate":firstDate,},
					url:'${contextPath}/security/auditing',
					dataType:'json',
					success:function(data){
						if($(".alert").length>0)$(".alert").remove();
						var $alertMsg = $("<div class='alert alert-dismissable'>"+data.message+"</div>");
						$alertMsg.append("<button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button>");
						if(data.replay == "1"){
							$alertMsg.addClass("alert-success");
						}else{
							$alertMsg.addClass("alert-danger");
						}
						$("form").prepend($alertMsg);
					}
				});
			});
		});
		loadTaskList = function(){
			$.ajax({
				type:'POST',
				data:{"workerId":workerId},
				url:'${contextPath}/security/inspectorList',
				dataType:'json',
				success:function(data){
					$("tbody").empty();
					if(data.list == ""){
						$("tbody").empty();
						$("tbody").append("<tr class='text-danger'><td colspan='4'>无内容</td></tr>");
					}else{
						firstDate = data.firstDate;
						$("#taskUlT").text("最早上传时间："+firstDate+"最后上传时间："+data.lastDate);
						$.each(data.list,function(i,item){
							$("tbody").append(
								"<tr>"+
									"<td>"+(i+1)+"</td>"+
									"<td>"+item.taskName+"</a></td>"+
									"<td>"+item.taskUploadTime+"</td>"+
									"<td>"+item.taskMarkTime+"</td>"+
								"</tr>"
							);
						});
					}
				}
			});
		}
	</script>
</body>
</html>
