<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>发包商页面</title>
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
	<!-------------------------------- 上传区域 -------------------------------------------------->
	<div class="container">
		<h2>上传任务包</h2>
		<form action="${contextPath}/security/uploadPack" method="post" id="uploadPack" name="employer" role="form" class="form-horizontal" enctype="multipart/form-data">
			<div class="form-group" id="packUploadDiv">
		      <label for="pack" class="col-sm-2 control-label">选择任务包：</label>
		      <div class="col-sm-10">
		         <input type="file" class="form-control" name="pack" id="pack" placeholder="请选择上传文件">
		         <span class="help-block"></span>
		      </div>
		   </div>
		   <div class="form-group" id="lockTime">
		      <label for="packLockTime" class="col-sm-2 control-label">任务时间：</label>
		      <div class="col-sm-10">
		         <input type="text" class="form-control" name="packLockTime" id="packLockTime" placeholder="添加任务时间">
		         <span class="help-block"></span>
		      </div>
		   </div>
		   <div class="form-group">
		      <div class="col-sm-offset-2 col-sm-10">
		         <button type="button" class="btn btn-default" disabled="disabled">上传</button>
		      </div>
		   </div>
		</form>
	</div>
	<!-------------------------------- 选项卡区域 -------------------------------------------------->
	<div class="container">
		<ul class="nav nav-tabs" role="tablist">
			<li class="active"><a href="#packUncomplete" role="tab" data-toggle="tab">未完成任务包列表</a></li>
			<li><a href="#packComplete" role="tab" data-toggle="tab">已完成任务包列表</a></li>
		</ul>
		<div class="tab-content">
			<div class="tab-pane active" id="packUncomplete">
				<table class="table table-striped table-bordered">
					<thead>
						<tr>
							<th>序号</th>
							<th>任务包名称</th>
							<th>任务总数</th>
							<th>未完成任务数</th>
							<th>已完成任务数</th>
							<th>完成任务比例</th>
							<th>下载次数</th>
							<th>回传时间</th>
							<th>创建时间</th>
							<th>下载任务包</th>
						</tr>
					</thead>
					<tbody></tbody>
				</table>
			</div>
			<div class="tab-pane" id="packComplete">
				<table class="table table-striped table-bordered">
					<thead>
						<tr>
							<th>序号</th>
							<th>任务包名称</th>
							<th>任务总数</th>
							<th>下载次数</th>
							<th>回传时间</th>
							<th>创建时间</th>
							<th>下载任务包</th>
						</tr>
					</thead>
					<tbody></tbody>
				</table>
			</div>
		</div>
		
	</div>
	<!-------------------------------- 弹出窗口 -------------------------------------------------->
	<div class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
					<h4 class="modal-title">任务包详细内容</h4>
				</div>
				<div class="modal-body">
					<table class="table table-striped table-bordered">
						<thead>
							<tr>
								<th>序号</th>
								<th>任务名称</th>
								<th>上传时间</th>
								<th>检测结果</th>
							</tr>
						</thead>
						<tbody id="packDetailTBody"></tbody>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	<script type="text/javascript">
	if('${match}' == '1'){
		alert("文件已存在");
	}
		$(document).ready(function(){
			$.ajax({
				type:'POST',
				url:'${contextPath}/security/employer',
				dataType:'json',
				success:function(data){
					$("tbody").append("");
					$.each(data,function(i,item){
						if(item.packLockTime == null){
							item.packLockTime = "";
						}
						var surplusTask = item.taskCount - item.finishTaskCount;//未完成任务数
						var finishTaskRatio = item.finishTaskCount/item.taskCount;//完成任务比例
						var downloadPack = "<td></td>";
						if(item.finishTaskCount != 0){
							downloadPack = "<td><a class='downloadPack' onClick='downloadPackFn("+item.packId+")'></a>下载</td>";
						}
						if(item.packStatus == 0){//判断任务包的状态为未完成
							$("#packUncomplete tbody").append(
								"<tr>"+
									"<td>"+(i+1)+"</td>"+
									"<td><a class='packId' onClick='showPackDetail("+item.packId+")'>"+item.packName+"</a></td>"+
									"<td>"+item.taskCount+"</td>"+
									"<td>"+surplusTask+"</td>"+
									"<td>"+item.finishTaskCount+"</td>"+
									"<td>"+finishTaskRatio+"%</td>"+
									"<td>"+item.DownCount+"</td>"+
									"<td>"+item.packLockTime+"</td>"+
									"<td>"+item.createTime+"</td>"+
									downloadPack+
								"</tr>"
							);
						}else{
							$("#packComplete tbody").append(
								"<tr>"+
									"<td>"+(i+1)+"</td>"+
									"<td><a class='packId' onClick='showPackDetail("+item.packId+")'>"+item.packName+"</a></td>"+
									"<td>"+item.taskCount+"</td>"+
									"<td>"+item.DownCount+"</td>"+
									"<td>"+item.packLockTime+"</td>"+
									"<td>"+item.createTime+"</td>"+
									downloadPack+
								"</tr>"
							);
						}
						
					});
					
				}
			});
			/*---------------------------------------查看上传包详细内容---------------------------------------------------------------*/
			var showPackDetail = function(packId){
				$("#packDetailTBody").append("");
				$.ajax({
					type:'POST',
					url:'${contextPath}/security/packDetail',
					data:{"packId":packId},
					dataType:'json',
					success:function(data){
						$.each(data,function(i,item){
							$("#packDetailTBody").append(
								"<tr>"+
									"<td>"+(i+1)+"</td>"+
									"<td>"+item.taskName+"</td>"+
									"<td>"+item.taskCreateTime+"</td>"+
									"<td>"+item.taskEffective+"</td>"+
								"</tr>"
							);
						});
						
					}
				});
			};
			/*---------------------------------------下载任务包---------------------------------------------------------------*/
			var downloadPackFn = function(packId){
				$.ajax({
					type:'GET',
					url:'${contextPath}/security/downPack',
					data:{"packId":packId},
					dataType:'text',
					success:function(data){
						if(data != ""){
							window.open(data);
						}
					}
				});
			};
			/*---------------------------------------上传文件check-------------------------------------------------------------------*/
			var fileReady = false;
			checkSubBtnStaus = function(){
				if(fileReady){
					$("button").removeAttr("disabled");
				}else{
					$("button").attr("disabled","disabled");
				}
			};
			$("#pack").change(function(){
				var pack = $("#pack");
				if(checkout.text.isempty(pack,"文件不能为空！")){
					fileReady = false;
				}else if(checkout.file.fileType(pack,"zip","请上传zip格式文件！")){
					var fileData = pack.val().split("/");
					var lastIndex = fileData.length()-1;
					var fileName = fileData[lastIndex];
					fileReady = true;
				}else{
					fileReady = false;
				}
				checkSubBtnStaus();
			});
			$("button[type=button]").click(function(){
				var formName = $("#uploadPack");
				formName.submit();
			});
		});
	</script>
</body>
</html>
