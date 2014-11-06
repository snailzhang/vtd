<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>注册用户列表</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta content="width=device-width, initial-scale=1" name="viewport">
<link rel="stylesheet" type="text/css" href="http://cdn.bootcss.com/bootstrap/3.2.0/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="http://cdn.bootcss.com/bootstrap/3.2.0/css/bootstrap-theme.min.css" />
<link href="http://cdn.bootcss.com/jqueryui/1.11.2/jquery-ui.min.css" rel="stylesheet">
<link href="http://cdn.bootcss.com/jqueryui/1.11.2/jquery-ui.theme.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="${contextPath}/css/public.css">
<script type="text/javascript" src="http://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
<script src="http://cdn.bootcss.com/jqueryui/1.11.2/jquery-ui.min.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/common.js"></script>
</head>
<body>
	<jsp:include page="head_reg.jsp" />
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">用户列表</div>
			<div class="panel-body">
				<form class="form-inline" role="form">
					<div class="form-group">
						<div class="input-group">
							<div class="input-group-addon">起始时间：</div>
							<input class="form-control" onkeydown="if(event.keyCode==13){return false;}" id=beginDate type="text" placeholder="起始时间">
						</div>
						<span id="bdhelp" class="help-block"></span>
					</div>
					<div class="form-group">
						<div class="input-group">
							<div class="input-group-addon">截止时间：</div>
							<input class="form-control" onkeydown="if(event.keyCode==13){return false;}" id="endDate" type="text" placeholder="起始时间">
						</div>
						<span id="edhelp" class="help-block"></span>
					</div>
					<div class="form-group">
						<button type="button" id="searchBtn" class="btn btn-default">查询</button>
						<span class="help-block"></span>
					</div>
					
				</form>
			</div>
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<th>序号</th>
						<th>用户名</th>
						<th>电话</th>
						<th>QQ</th>
						<th>残疾证号</th>
						<th>地址</th>
						<th>创建时间</th>
					</tr>
				</thead>
				<tbody></tbody>
			</table>
			<ul class="pagination"></ul>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function(){
			chooseUserType("","",1);
			$("#beginDate,#endDate").datepicker();
			$("#searchBtn").click(function(){
				var bd = $("#beginDate").val();
				var ed = $("#endDate").val();
				if(bd == ed == ""){
					chooseUserType("","",1);
					$("#edhelp,#bdhelp").empty();
				}else if((bd != "")&&(ed != "")){
					chooseUserType(bd,ed,1);
					$("#edhelp,#bdhelp").empty();
				}else if((bd != "")&&(ed == "")){
					$("#edhelp").css("color","red").text("请填写截止日期");
				}else{
					$("#edhelp").css("color","red").text("请填写起始日期");
				}
				
			});
		});
		chooseUserType = function(beginDate,endDate,pageNum){
			$.ajax({
				type:'POST',
				data:{"beginDate":beginDate,"endDate":endDate,"page":pageNum},
				url:'${contextPath}/regList',
				dataType:'json',
				success:function(data){
					$("tbody").empty();
					
					if(data.list == ""){
						$("tbody").empty();
						$("tbody").append("<tr class='text-danger'><td colspan='7'>无内容</td></tr>");
					}else{
						var pageTotal = data.totlePage;
						$.each(data.list,function(i,item){
							$("tbody").append(
								"<tr>"+
									"<td>"+(i+1)+"</td>"+
									"<td>"+item.name+"</td>"+
									"<td>"+item.phone+"</td>"+
									"<td>"+item.qq+"</td>"+
									"<td>"+item.card+"</td>"+
									"<td>"+item.address+"</td>"+
									"<td>"+item.createTime+"</td>"+
								"</tr>"
							);
							$(".pagination").empty();
							for(var i=1;i<pageTotal+1;i++){
								if(i==pageNum){
									$(".pagination").append(
										"<li class='active'><a onClick='chooseUserType(\""+beginDate+"\",\""+endDate+"\","+i+")'>"+
										i+
										"</a></li>"
									);
								}else{
									$(".pagination").append(
										"<li><a onClick='chooseUserType(\""+beginDate+"\",\""+endDate+"\","+i+")'>"+
										i+
										"</a></li>"
									);
								}
							}
						});
					}
				}
			});
		}
	</script>
</body>
</html>