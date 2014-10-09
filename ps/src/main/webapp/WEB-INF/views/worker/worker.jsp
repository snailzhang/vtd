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
	<div class="container">
		<h2>已完成任务列表</h2>
		<table class="table table-striped table-bordered">
			<thead>
				<tr>
					<th>序号</th>
					<th>任务名称</th>
					<th>任务时长</th>
					<th>下载时间</th>
					<th>上传时间</th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>
	</div>
	<div class="container" id="downBtn">
		<h2>下载新任务</h2>
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
	<div class="container" id="upBtn">
		<h2>上传已完成的任务</h2>
		<form action="${contextPath}/upTagAndTextGrid" method="post" id="fileupload" name="upTagAndTextGrid" role="form" class="form-horizontal" enctype="multipart/form-data">
			<div class="form-group">
		      <label for="TAG" class="col-sm-2 control-label">TAG:</label>
		      <div class="col-sm-10">
		         <input type="file" class="form-control" name="file" id="" placeholder="请选择上传文件" multiple>
		      </div>
		   </div>
		   
		   <div class="form-group">
		      <div class="col-sm-offset-2 col-sm-10">
		         <button type="submit" class="btn btn-default" >上传文件</button>
		      </div>
		   </div>
		</form>
	</div>
		
			
	
	<script type="text/javascript">
		$(document).ready(function(){
			$.ajax({
				type:'POST',
				url:'${contextPath}/worker',
				dataType:'json',
				success:function(data){
					$("tbody").append("");
					var workerMark = data.workerMark;
					var taskTotal = data.taskTotal;
					if(taskTotal != 0){
						if(taskTotal>20)taskTotal = 20;//单次下载最多20个任务
						for(var i=1;i<taskTotal+1;i++){
							$("#downTaskCount").append("<option vale='"+i+"'>"+i+"</option>");
						}
					}else{
						$("#downTaskCount").attr("disabled","disabled");
					}
					$.each(data.list,function(i,item){
						$("tbody").append(
							"<tr>"+
								"<td>"+(i+1)+"</td>"+
								"<td>"+item.taskName+"</td>"+
								"<td>"+item.taskMarkTime+"</td>"+
								"<td>"+item.taskDownloadTime+"</td>"+
								"<td>"+item.taskUploadTime+"</td>"+
							"</tr>"
						);
					});
					
					if(workerMark == 0){
						$("#downBtn").show();
						$("#upBtn").hide();
					}else{
						$("#downBtn").hide();
						$("#upBtn").show();
					}
					
				}
			});
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
			$("button[type=button]").click(function(){
				var formName = $("#upTagAndTextGrid");
				formName.submit();
			});
			$("fileupload").fileupload({
				submit:function(e,data){
					$(this).fileupload('send', data);
				}
			});
		});
	</script>
</body>
</html>
