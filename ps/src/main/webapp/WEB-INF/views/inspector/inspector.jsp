<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>审核列表页面</title>
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
			<div class="panel-heading">用户列表</div>
			<div class="panel-body">
				<form class="form-inline" role="form">
					<div class="form-group">
						<div class="input-group">
							<div class="input-group-addon"> 用户名：</div>
							<input class="form-control" onkeydown="if(event.keyCode==13){return false;}" id="userNameCondition" type="text" placeholder="查询用户">
						</div>
					</div>
					<div class="form-group">
						<p class="form-control-static">标注时间段：</p>
					</div>
					<div class="form-group">
						<select class="form-control" id="timeMark">
							<option value="0">大于十分钟</option>
							<option value="1">小于十分钟</option>
						</select>
					</div>
					<button type="button" id="searchBtn" class="btn btn-default">查询</button>
				</form>
			</div>
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<th width='10%'>序号</th>
						<th width='15%'>姓名</th>
						<th width='20%'>个数</th>
						
					</tr>
				</thead>
				<tbody></tbody>
			</table>
			<ul class="pagination"></ul>
		</div>
	</div>
	<script type="text/javascript">
		var nowPage = 0;
		var timeMark = 0;
		var userNameCondition = "";
		var pageTotal = 0;
		$(document).ready(function(){
			loadUserList(1);
			/*--------------------------------------点击查询按钮-------------------------------------------------------*/
			$("#searchBtn").click(function(){
				timeMark = $("#timeMark").val();
				userNameCondition = $("#userNameCondition").val();
				loadUserList(1);
			});
		});
		/*--------------------------------------加载页面-------------------------------------------------------*/
		loadUserList = function(pageNum){
			$.ajax({
				type:'POST',
				data:{"timeMark":timeMark,"page":pageNum,"userName":userNameCondition},
				url:'${contextPath}/security/inspector',
				dataType:'json',
				success:function(data){
					$("tbody").empty();
					if(data.list == ""){
						$("tbody").empty();
						$("tbody").append("<tr class='text-danger'><td colspan='5'>无内容</td></tr>");
					}else{
						pageTotal = data.totlePage;
						$.each(data.list,function(i,item){
							var status = "不可用";
							if(item.userStatus == "1")status = "可用";
							$("tbody").append(
								"<tr>"+
									"<td>"+(i+1)+"</td>"+
									"<td><a target='_blank' href='${contextPath}/security/inspectorList?workerId="+item.worker_id+"'>"+item.user_name+"</a></td>"+
									"<td>"+item.c+"</td>"+
								"</tr>"
							);
							var pageDom = $(".pagination");
							pageDom.empty();
							page.creatPageHTML(pageNum,pageTotal,pageDom,"loadUserList");
							/*--------------------------------------跳转页-------------------------------------------------------*/
							$(".pageGoBtn").click(function(){
								var pageNum = 0;
								pageNum = $(".pageGoText").val();
								if(pageNum !=0&&0<pageNum&&pageNum<pageTotal+1){
									loadUserList(pageNum);
								}
							});
						});
					}
				}
			});
		}
	</script>
</body>
</html>
