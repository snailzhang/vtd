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
		<% 
			//if(//workerMark == "down"){
				%>
				<a type="button" class="btn btn-lg btn-primary btn-block" href="">下载任务</a>
				<%
			//}else{
				%>
				<form action="/logins" method="post" name="form1" role="form" class="form-signin">
					<h2 class="form-signin-heading">选择上传文件</h2>
					<span>TAG:</span><input type="file" name="tag" autofocus="" required="" placeholder="上传TAG文件" class="form-control">
					<span>TextGrid:</span><input type="file" required="" placeholder="上传TextGrid文件" name="TextGrid" class="form-control">
					<button type="submit" class="btn btn-lg btn-primary btn-block">上传文件</button>
				</form>
				<%
			//}
		%>
			
	</div>
	<script type="text/javascript">
		$(document).ready(function(){
			$.ajax({
				type:'POST',
				url:'${contextPath}/worker',
				dataType:'json',
				success:function(data){
					$("tbody").append("");
					$.each(data,function(i,item){
						$("tbody").append(
							"<tr>"+
								"<td>"+(i+1)+"</td>"+
								"<td>"+item.packName+"</td>"+
								"<td>"+item.packStatus+"</td>"+
								"<td>"+item.packLockTime+"</td>"+
								"<td>"+item.createTime+"</td>"+
							"</tr>"
						);
					});
					
				}
			});
			$("button[type=button]").click(function(){
				var formName = $("#addmanager");
				var pack = $("#pack").val();
				if(checkout.text.isempty(pack,"文件不能为空！")) return;
				formName.submit();
				
			});
		});
	</script>
</body>
</html>
