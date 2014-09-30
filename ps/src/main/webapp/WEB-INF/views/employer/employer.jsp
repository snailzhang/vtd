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
	<div class="container">
		<table class="table table-striped table-bordered">
			<thead>
				<tr>
					<th>序号</th>
					<th>任务名称</th>
					<th>完成情况</th>
					<th>回传时间</th>
					<th>上传时间</th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>
	</div>
	<div class="container">
		<h2>上传任务包</h2>
		<form action="${contextPath}/uploadPack" method="post" id="uploadPack" name="employer" role="form" class="form-horizontal">
			<div class="form-group" id="packUploadDiv">
		      <label for="pack" class="col-sm-2 control-label">选择任务包：</label>
		      <div class="col-sm-10">
		         <input type="file" class="form-control" name="pack" id="pack" placeholder="请选择上传文件">
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
	<script type="text/javascript">
		$(document).ready(function(){
			$.ajax({
				type:'POST',
				url:'${contextPath}/employer',
				dataType:'json',
				success:function(data){
					$("tbody").append("");
					$.each(data,function(i,item){
						if(item.packLockTime == null){
							item.packLockTime = "";
						}
						
						$("tbody").append(
							"<tr>"+
								"<td>"+(i+1)+"</td>"+
								"<td><a href='${contextPath}/packDetail?packId="+item.packId+"'>"+item.packName+"</a></td>"+
								"<td>"+item.packStatus+"</td>"+
								"<td>"+item.packLockTime+"</td>"+
								"<td>"+item.createTime+"</td>"+
							"</tr>"
						);
					});
					
				}
			});
			
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
					checkSubBtnStaus();
					return;
				}
				if(checkout.file.fileType(pack,"zip","请上传zip格式文件！")){
					fileReady = true;
					checkSubBtnStaus();
				}else{
					fileReady = false;
					checkSubBtnStaus();
				}
			});
			$("button[type=button]").click(function(){
				var formName = $("#addmanager");
				formName.submit();
			});
		});
	</script>
</body>
</html>
