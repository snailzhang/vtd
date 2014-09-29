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
<style type="text/css">
	#downBtn,#upBtn{display:none;}
</style>
<script type="text/javascript" src="http://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
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
		<a type="button" class="btn btn-lg btn-primary btn-block" href="${contextPath}/downTask">下载任务</a>
	</div>
	<div class="container" id="upBtn">
		<form action="${contextPath}/upTagAndTextGrid" method="post" id="upTagAndTextGrid" name="upTagAndTextGrid" role="form" class="form-horizontal">
			<div class="form-group">
		      <label for="pack" class="col-sm-2 control-label">TAG:</label>
		      <div class="col-sm-10">
		         <input type="file" class="form-control" name="TAG" id="TAG" placeholder="请选择上传TAG文件">
		      </div>
		   </div>
		   <div class="form-group">
		      <label for="TextGrid" class="col-sm-2 control-label">TextGrid:</label>
		      <div class="col-sm-10">
		         <input type="file" class="form-control" name="TextGrid" id="TextGrid" placeholder="请选择上传TextGrid文件">
		      </div>
		   </div>
		   <div class="form-group">
		      <div class="col-sm-offset-2 col-sm-10">
		         <button type="button" class="btn btn-default">上传文件</button>
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
			$("button[type=button]").click(function(){
				var formName = $("#upTagAndTextGrid");
				
				formName.submit();
				
			});
		});
	</script>
</body>
</html>
