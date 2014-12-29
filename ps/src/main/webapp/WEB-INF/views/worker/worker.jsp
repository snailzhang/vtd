<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>工作者页面</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta content="width=device-width, initial-scale=1" name="viewport">
<link rel="stylesheet" type="text/css" href="${contextPath}/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/bootstrap-theme.min.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/public.css">
<style type="text/css">
	#downloadPanel,#uploadPanel{display:none;}
	.form-inline .form-control[type=file]{
		border:0;
		padding:0;
		box-shadow:none;
		width:70px;
	}
	#downloadPanel h3{
		display:inline-block;
		margin-right:10px;
	}
	form{padding:10px;}
</style>
<script type="text/javascript" src="${contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.fileupload.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${contextPath}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/common.js"></script>
</head>
<body>
	<jsp:include page="../head.jsp" />
	<!-------------------------------- 下载任务 -------------------------------------------------->
	<div class="container" id="downloadPanel">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title"></h3><button type="button" id="refreshPage" class="btn btn-info">刷新</button>
			</div>
			<form action="${contextPath}/security/downTask" method="get" id="downTask" name="downTask" role="form" class="form-inline">
				<div class="form-group" id="">
					<p class="form-control-static">选择下载任务个数:</p>
				</div>
				<div class="form-group">
			      <select class="form-control" name="downTaskCount" id="downTaskCount"></select>
			   </div>
			   <div class="form-group">
			      <button type="button" id="doneLoadBtn" class="btn btn-primary">下载任务</button>
			   </div>
			   <div class="form-group">
			      <p class="form-control-static" id="downTaskTip"></p>
			   </div>
			</form>
		</div>
	</div>
	<!-------------------------------- 上传任务 -------------------------------------------------->
	<div class="container" id="uploadPanel">
		<div class="container">
			<div class="panel panel-default">
				
				<form action="${contextPath}/security/upTagAndTextGrid" method="post" name="upTagAndTextGrid" role="form" class="form-inline" enctype="multipart/form-data">
					<div class="form-group" style="width:50%">
				       <div class="col-sm-4">
				      <label for="TAG" class="control-label">选择已完成任务</label>
				      </div>
				      <div class="col-sm-3">
				         <input type="file" class="form-control" name="file" id="fileupload" autocomplete="off" multiple>
				      </div>
				      <span class="col-sm-5 help-block" id="uploadHelp">已选择0个文件</span>
				   </div>
				   <div class="form-group">
				      <div class="col-sm-offset-2 col-sm-10">
				         <button id="uploadBtn" type="button" class="btn btn-primary" >上传文件</button>
				      </div>
				   </div>
				</form>
			</div>
		</div>
		<!-------------------------------- 选项卡区域 -------------------------------------------------->
		<div class="container">
			<ul class="nav nav-tabs" role="tablist">
				<li class="active"><a href="#uploadFalTable" role="tab" data-toggle="tab">待上传任务列表</a></li>
				<li><a href="#unqualifiedList" role="tab" data-toggle="tab">不合格任务包列表</a></li>
			</ul>
			<div class="tab-content">
			<!-- ****************************************待上传任务列表******************************************************* -->
				<div class="tab-pane active" id="uploadFalTable">
					<div class="panel panel-default">
						<div class="panel-body">
							<h3 class="panel-title" id="taskLeftTimeInterval">距离任务完成时间还剩:<span class="text-danger"></span></h3>
						</div>
						<table class="table table-striped table-bordered">
							<thead>
								<tr>
									<th width="10%">序号</th>
									<th width="40%">任务名称</th>
									<th width="40%">下载时间</th>
									<th width="10%">放弃</th>
								</tr>
							</thead>
							<tbody id="waitForUpTable"></tbody>
						</table>
					</div>
				</div>
				<!-- ****************************************不合格任务包列表******************************************************* -->
				<div class="tab-pane" id="unqualifiedList">
					<div class="panel panel-default">
						<div class="panel-body">
							<h3 class="panel-title" id="taskUnLeftTimeInterval">距离任务完成时间还剩:<span class="text-danger"></span></h3>
						</div>
						<table class="table table-striped table-bordered">
							<thead>
								<tr>
									<th width="10%">序号</th>
									<th width="30%">任务名称</th>
									<th width="30%">下载时间</th>
									<th width="20%">标注说明</th>
									<th width="10%">放弃</th>
								</tr>
							</thead>
							<tbody id="unqualifiedTable"></tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-------------------------------- 弹出窗口 -------------------------------------------------->
	<div id="#taskUpResult" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
					<h4 class="modal-title">Modal title</h4>
				</div>
				<div class="modal-body">
					<ul class="nav nav-tabs" role="tablist">
						<li class="active"><a href="#taskListNoMath" role="tab" data-toggle="tab">未匹配任务</a></li>
						<li><a href="#taskListMath" role="tab" data-toggle="tab">已匹配任务</a></li>
					</ul>
					<div class="tab-content">
						<div class="tab-pane active" id="taskListNoMath"><ul></ul></div>
						<div class="tab-pane" id="taskListMath"><ul></ul></div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	<script type="text/javascript">
		var tltInterval = 1000; 
		var todayTime = "";
		var intervalMainList = null;
		var intervalUnqList = null;
		$(document).ready(function(){
			/*******************************加载页面**************************************************/
			loadPage();
			
			 /*******************************上传check**************************************************/
			var textGridReady = false;
			var tagReady = false;
			checkSubBtnStaus = function(){
				if(tagReady&&textGridReady){
					$("button").removeAttr("disabled");
				}else{
					$("button").attr("disabled","disabled");
				}
			};
			$("#TAG").change(function(){
				var tag = $("#TAG");
				
				if(checkout.text.isempty(tag,"文件不能为空！")){
					tagReady = false;
					checkSubBtnStaus();
					return;
				}
				if(checkout.file.fileType(tag,"tag","请上传tag格式文件！")){
					tagReady = true;
					checkSubBtnStaus();
				}else{
					tagReady = false;
					checkSubBtnStaus();
				}
			});
			$("#TextGrid").change(function(){
				var textGrid = $("#TextGrid");
				
				if(checkout.text.isempty(textGrid,"文件不能为空！")){
					textGridReady = false;
					checkSubBtnStaus();
					return;
				}
				if(checkout.file.fileType(textGrid,"textGrid","请上传textGrid格式文件！")){
					textGridReady = true;
					checkSubBtnStaus();
				}else{
					textGridReady = false;
					checkSubBtnStaus();
				}
			});
			
			/*******************************多文件上传**************************************************/
				$("#fileupload").fileupload({
					singleFileUploads:false,
					formAcceptCharset:'gb2312',
					typ:"POST",
					dataType:"json",
					url:"${contextPath}/security/upTagAndTextGrid",
					add:function(e,data){
						var $this = $(this);
						data.autoUpload = false;
						var fileNum = data.files.length;
						$("#uploadHelp").text("已选择"+fileNum+"个文件");
						$("#uploadBtn").click(function(){
							if(data.files.length == 0){
								$("#uploadHelp").text("请选择上传的文件");
								return false;
							};
							data.submit();
						});
					},
					done:function(e,result){
						var taskListNoMath = $("#taskListNoMath ul");
						var taskListMath = $("#taskListMath ul");
						var jsonData = result.result;
						if(jsonData.listAll!=""){
							taskListNoMath.html("");
							for(var i=0;i<jsonData.listAll.length;i++){
							//$.each(jsonData.listAll,function(i,alllist){
								var alllist = jsonData.listAll[i];
								var unhad = false;
								if(jsonData.listMath !=""){
									for(var k=0;k<jsonData.listMath.length;k++){
									//$.each(jsonData.listMath,function(k,matchlist){
										var matchlist = jsonData.listMath[k];
										if(matchlist == alllist){
											unhad = false;
											break;
										}else{
											unhad = true;
										}
	
									};
								}else{
									unhad = true;
								}
								if(unhad){
									taskListNoMath.append(
										"<li>"+
											
											"<span>"+alllist+"</span>"+
										"</li>"
									);
								}
							};
						}else{
							taskListNoMath.html("<li class='text-success'>全部通过</li>");
						}
						if(jsonData.listMath!=""){
							taskListMath.html("");
							$.each(jsonData.listMath,function(i,item){
								taskListMath.append(
									"<li>"+
										
										"<span>"+item+"</span>"+
									"</li>"
								);
							});
						}else{
							taskListMath.html("<li class='text-danger'>无匹配内容</li>");
						}
						$(".modal").modal('show');
					}
				});
			/*******************************modal关闭时刷新页面**************************************************/
			$(".modal").on('hidden.bs.modal', function (e) {
				loadPage();
			});
			/*******************************下载任务**************************************************/
			$("#doneLoadBtn").click(function(){
				$("#downTaskTip").text("任务下载中。。。");
				var downTaskCount = $("#downTaskCount").val();
				if(downTaskCount == 0){
					return false;
				}else{
					$.ajax({
						type:'GET',
						url:'${contextPath}/security/downTask',
						data:{"downTaskCount":downTaskCount},
						dataType:'json',
						success:function(data){
							if(data.wrongPath != ""){
								
								window.open("${contextPath}"+data.wrongPath);
								loadPage();
							}
						}
					});
				}
			});
			/*******************************刷新按钮**************************************************/
			$("#refreshPage").click(function(){
				window.location.reload();
			});
		});
		/*-----------------------------------加载页面----------------------------------------------------*/
		loadPage = function(){
			var today = new Date();
			todayTime = today.valueOf();
			if(intervalMainList || intervalUnqList){
				window.clearInterval(intervalMainList);
				window.clearInterval(intervalUnqList);
			}
			$.ajax({
				type:'POST',
				url:'${contextPath}/security/worker',
				data:{"taskEffective":0},
				dataType:'json',
				success:function(data){
					var workerMark = data.workerMark;
					if(workerMark == 0){//下载
						var taskTotal = data.countTaskDoing;
						var packTotal = data.countPackDoing;
						$("#downloadPanel h3").text("共"+packTotal+"任务包，当前包可下载任务数为"+taskTotal+"个。当前下载包标注说明编号为："+data.noteId);
						
						if(data.downCount != 0){
							if(parseInt(data.downCount/10)){
								$("#downTaskCount").append("<option vale='1'>1</option>");
								for(var i=1;i<parseInt(data.downCount/10)+1;i++){
									$("#downTaskCount").append("<option vale='"+(i*10)+"'>"+(i*10)+"</option>");
								}
								if(data.downCount%10){
									$("#downTaskCount").append("<option vale='"+data.downCount+"'>"+data.downCount+"</option>");
								}
							}else{
								for(var i=1;i<data.downCount+1;i++){
									$("#downTaskCount").append("<option vale='"+i+"'>"+i+"</option>");
								}
							}
							
						}else{
							$("#downTaskCount").attr("disabled","disabled");
						}
						$("#uploadPanel").hide();
						$("#downloadPanel").show();
					}else{//上传
						loadUnqualified();
						var mm = data.mm;
						if(data.list == ""){
							$("#waitForUpTable").empty();
							$("#waitForUpTable").append("<tr class='text-danger'><td colspan='3'>无内容</td></tr>");
						}else{
							$("#waitForUpTable").empty();
							$.each(data.list,function(i,item){
								var giveUpBtn = "<td><a href='javascript:giveUpTask("+item.taskId+")'>放弃</a></td>";
								$("#waitForUpTable").append(
									"<tr>"+
										"<td>"+(i+1)+"</td>"+
										"<td>"+item.taskName+"</td>"+
										"<td>"+item.taskDownloadTime+"</td>"+
										giveUpBtn+
									"</tr>"
								);
							});
						}
						if(mm !=0){
							var endTime = todayTime+mm ;
							intervalMainList = window.setInterval(function(){ShowCountDown(endTime,$("#taskLeftTimeInterval span"));}, tltInterval);
						}
						
						$("#downloadPanel").hide();
						$("#uploadPanel").show();
					}
					
				}
			});
		}
		/*******************************倒计时**************************************************/
		function ShowCountDown(endTime,obj){
			var now = new Date(); 
			var leftTime=endTime-now.valueOf(); 
			var leftsecond = parseInt(leftTime/1000); 
			var day1=Math.floor(leftsecond/(60*60*24)); 
			var hour=Math.floor((leftsecond-day1*24*60*60)/3600); 
			var minute=Math.floor((leftsecond-day1*24*60*60-hour*3600)/60); 
			var second=Math.floor(leftsecond-day1*24*60*60-hour*3600-minute*60); 
			
			obj.text(day1+"天"+hour+"小时"+minute+"分"+second+"秒"); 
		}
		/*-----------------------------------加载不合格任务列表----------------------------------------------------*/
		function loadUnqualified(){
			$.ajax({
				type:'POST',
				url:'${contextPath}/security/worker',
				data:{"taskEffective":2},
				dataType:'json',
				success:function(data){
					var mm = data.mm;
					if(data.list == ""){
						$("#unqualifiedTable").empty();
						$("#unqualifiedTable").append("<tr class='text-danger'><td colspan='5'>无内容</td></tr>");
					}else{
						$.each(data.list,function(i,item){
							var giveUpBtn = "<td><a href='javascript:giveUpTask("+item.taskId+")'>放弃</a></td>";
							$("#unqualifiedTable").append(
								"<tr>"+
									"<td>"+(i+1)+"</td>"+
									"<td>"+item.taskName+"</td>"+
									"<td>"+item.taskDownloadTime+"</td>"+
									"<td>"+item.noteId+"</td>"+giveUpBtn+
								"</tr>"
							);
						});
					}
					if(mm != 0){
						var endTime = todayTime+mm ;
						intervalUnqList = window.setInterval(function(){ShowCountDown(endTime,$("#taskUnLeftTimeInterval span"));}, tltInterval);
					}
				}
			});
		}
		/*-----------------------------------放弃任务----------------------------------------------------*/
		function giveUpTask(taskId){
			var conWin = confirm("确定要放弃该任务吗？");
			if(conWin){
				$.ajax({
					type:'POST',
					url:'${contextPath}/security/GiveUpTask',
					data:{"taskId":taskId},
					dataType:'json',
					success:function(data){
						if(data.replay == "1"){
							alert("任务已放弃！");
							loadPage();
						}else{
							alert("放弃任务失败！");
						}
					}
				})
			}
		}
	</script>
</body>
</html>
