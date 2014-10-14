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
<link rel="stylesheet" type="text/css" href="http://cdn.bootcss.com/bootstrap/3.2.0/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="http://cdn.bootcss.com/bootstrap/3.2.0/css/bootstrap-theme.min.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/public.css">
<style type="text/css">
	#downBtn,#upBtn{display:none;}
	input[type=file]{
		border:0;
		padding:0;
		box-shadow:none;
	}
</style>
<script type="text/javascript" src="http://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.fileupload.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/common.js"></script>
</head>
<body>
	<jsp:include page="../head.jsp" />
	
	<div class="container" id="downBtn">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title"></h3>
			</div>
			<form action="${contextPath}/downTask" method="get" id="downTask" name="downTask" role="form" class="form-horizontal">
				<div class="form-group">
			      <label for="downTaskCount" class="col-sm-2 control-label">选择下载任务个数:</label>
			      <div class="col-sm-10">
			      	<select class="form-control" name="downTaskCount" id="downTaskCount"></select>
			      </div>
			   </div>
			   <div class="form-group">
			      <div class="col-sm-offset-2 col-sm-10">
			         <button type="submit" class="btn btn-default">下载任务</button>
			      </div>
			   </div>
			</form>
		</div>
	</div>
	<div class="container" id="upBtn">
		<div class="container">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title" id="taskLeftTimeInterval">距离任务完成时间还剩:<span class="text-danger"></span></h3>
				</div>
				<form action="${contextPath}/upTagAndTextGrid" method="post" name="upTagAndTextGrid" role="form" class="form-horizontal" enctype="multipart/form-data">
					<div class="form-group">
				      <label for="TAG" class="col-sm-2 control-label">选择已完成任务</label>
				      <div class="col-sm-10">
				         <input type="file" class="form-control" name="file" id="fileupload" placeholder="请选择上传文件" autocomplete="off" multiple>
				      </div>
				   </div>
				   
				   <div class="form-group">
				      <div class="col-sm-offset-2 col-sm-10">
				         <button id="uploadBtn" type="button" class="btn btn-default" >上传文件</button>
				      </div>
				   </div>
				</form>
				
			</div>
			
		</div>
		<div class="container">
			<div id="uploadFalTable" class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">待上传任务列表</h3>
				</div>
				<table class="table table-striped table-bordered">
					<thead>
						<tr>
							<th>序号</th>
							<th>任务名称</th>
							<th>下载时间</th>
						</tr>
					</thead>
					<tbody id="waitForUpTable"></tbody>
				</table>
			</div>
			
		</div>
	</div>
	<div class="modal fade">
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
		$(document).ready(function(){
			$.ajax({
				type:'POST',
				url:'${contextPath}/worker',
				dataType:'json',
				success:function(data){
					$("tbody").append("");
					var workerMark = data.workerMark;
					if(workerMark == 0){//下载
						var taskTotal = data.countTaskDoing;
						var packTotal = data.countPackDoing;
						$("#downBtn h3").text("共"+packTotal+"任务包，当前包可下载任务数为"+taskTotal+"个");
						if(taskTotal != 0){
							if(taskTotal>20)taskTotal = 20;//单次下载最多20个任务
							for(var i=1;i<taskTotal+1;i++){
								$("#downTaskCount").append("<option vale='"+i+"'>"+i+"</option>");
							}
						}else{
							$("#downTaskCount").attr("disabled","disabled");
						}
						$("#upBtn").hide();
						$("#downBtn").show();
					}else{//上传
						var mm = data.mm;
						$.each(data.list,function(i,item){
							$("#waitForUpTable").append(
								"<tr>"+
									"<td>"+(i+1)+"</td>"+
									"<td>"+item.taskName+"</td>"+
									"<td>"+item.taskDownloadTime+"</td>"+
								"</tr>"
							);
						});
						var tltInterval = 1000; 
						window.setInterval(function(){ShowCountDown(mm);}, tltInterval);
						$("#downBtn").hide();
						$("#upBtn").show();
					}
					
				}
			});
			
			function ShowCountDown(endTime){//倒计时
				var now = new Date(); 
				var leftTime=endTime-now.getTime(); 
				var leftsecond = parseInt(leftTime/1000); 
				var day1=Math.floor(leftsecond/(60*60*24)); 
				var hour=Math.floor((leftsecond-day1*24*60*60)/3600); 
				var minute=Math.floor((leftsecond-day1*24*60*60-hour*3600)/60); 
				var second=Math.floor(leftsecond-day1*24*60*60-hour*3600-minute*60); 
				
				$("#taskLeftTimeInterval span").text(day1+"天"+hour+"小时"+minute+"分"+second+"秒"); 
			}
			 
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
			//$("button[type=button]").click(function(){
				//var formName = $("#upTagAndTextGrid");
				//formName.submit();
			//});
			
			
				$("#fileupload").fileupload({//多文件上传
					singleFileUploads:false,
					typ:"POST",
					dataType:"json",
					url:"${contextPath}/upTagAndTextGrid",
					add:function(e,data){
						var $this = $(this);
						data.autoUpload = false;
						$("#uploadBtn").click(function(){
							//$this.fileupload('send', {
								//files:data.files
							//});
							data.submit();
						});
					},
					
					done:function(e,result){
						var taskListNoMath = $("#taskListNoMath ul");
						var taskListMath = $("#taskListMath ul");
						var jsonData = result.result;
						if(jsonData.listNoMath!=""){
							taskListNoMath.html("");
							$.each(jsonData.listNoMath,function(i,item){
								taskListNoMath.append(
									"<li>"+
										
										"<span>"+item+"</span>"+
									"</li>"
								);
							});
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
							taskListMath.html("<li class='text-success'>无匹配内容</li>");
						}
						$(".modal").modal('show');
					}
				});
			$(".modal").on('hidden.bs.modal', function (e) {
				window.location.reload();
			});
				
			
		});
	</script>
</body>
</html>
