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
<script type="text/javascript" src="${contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/common.js"></script>
</head>
<body>
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">待审核任务信息<span id="taskUlT" class="pull-right text-success"></span></div>
			<div class="panel-body">
				<button type="button" id="downTask" class="btn btn-warning">下载待审核任务</button>
				<button type="button" id="qualifiedBtn" class="btn btn-success">审核合格</button>
				<button type="button" id="unqualifiedBtn" class="btn btn-danger">审核不合格</button>
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
	<!-------------------------------- 弹出窗口 -------------------------------------------------->
	<div id="unqualifiedModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
					<h4 class="modal-title">审核不合格</h4>
				</div>
				<div class="modal-body">
					<form class="form" role="form">
						<div class="form-group" id="reTime">
							<div class="input-group">
								<span class="input-group-addon">修改回传时间：</span>
								<input class="form-control" onkeydown="if(event.keyCode==13){return false;}" id="day" type="number" min="0" placeholder="填入数字，修改回传时间">
								<span class="input-group-addon">小时</span>
							</div>
						</div>
						<div class="form-group" id="">
							 <label class="control-label" for="unqualifiedReason">添加不合格原因</label>
							<textarea id="unqualifiedReason" class="form-control" rows="3" maxlength="200"></textarea>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" id="unpualifiedSubmitBtn" class="btn btn-primary">提交</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	<script type="text/javascript">
		var workerId = '${workerId}';
		var taskList = "";
		var firstDate = "";
		var lastDate = "";
		
		$(document).ready(function(){
			loadTaskList();
			$("#statu").change(function(){
				$this = $(this);
				$this.val() == "0"?$("#reTime").show():$("#reTime").hide();
			});
			/*--------------------------------------下载待审核任务-------------------------------------------------------*/
			$("#downTask").click(function(){
				if(taskList.size == 0){
					alert("暂无待审核任务！");
				}else{
					$.ajax({
						type:'POST',
						data:{"list":taskList,"workerId":workerId},
						url:'${contextPath}/security/downAuditTask',
						dataType:'json',
						success:function(data){
							if(data.wrongPath != ""){
								window.open("${contextPath}"+data.wrongPath);
							}
						}
					});
				}
			});
			/*--------------------------------------审核任务-------------------------------------------------------*/
			
			$("#qualifiedBtn").click(function(){//成功
				var conWin = confirm("确定该任务审核合格吗？");
				if(conWin){
					var day = 0;
					var taskEffective = 1;
					postQualifyFn(taskEffective,day,"");
				}
			});
			$("#unqualifiedBtn").click(function(){
				$("#unqualifiedModal").modal('show');
				
			});
			$("#unpualifiedSubmitBtn").click(function(){
				var day = $("#day").val();
				if(day == "")day = 0;
				if(day == 0){
					$("#day").parent(".input-group").addClass("has-error").focus();
					return;
				}else{
					$(".has-error").removeClass("has-error");
					var resaon = $("#unqualifiedReason").val();
					if(resaon == ""){
						var comW = confirm("确定不填写不合格原因吗？");
						if(comW){
							resaon = "";
						}else{
							return;
						}
					}
					postQualifyFn(0,day,resaon);
					$("#unqualifiedModal").modal('hide');
				}
			});
			postQualifyFn = function(taskEffective,day,note){
				$.ajax({
					type:'POST',
					data:{"workerId":workerId,"taskEffective":taskEffective,"day":day,"note":note},
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
						$(".panel-body").prepend($alertMsg);
					}
				});
			}
			
		});
		/*--------------------------------------加载列表-------------------------------------------------------*/
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
						lastDate = data.lastDate;
						$("#taskUlT").text("最早上传时间："+firstDate+"最后上传时间："+data.lastDate);
						$.each(data.list,function(i,item){
							if(i == 0){
								taskList = item.taskId;
							}else{
								taskList +="_"+item.taskId;
							}
							
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
