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
	<jsp:include page="head_reg.jsp" />
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">用户列表</div>
			<div class="panel-body">
				<form class="form-inline" role="form">
					<div class="form-group">
						<div class="input-group">
							<div class="input-group-addon">用户名：</div>
							<input class="form-control" onkeydown="if(event.keyCode==13){return false;}" id="search_user" type="text" placeholder="查询用户">
						</div>
					</div>
					<div class="form-group">
						<div class="input-group">
							<div class="input-group-addon">部门：</div>
							<input class="form-control" onkeydown="if(event.keyCode==13){return false;}" id="search_dep" type="text" placeholder="查询部门">
						</div>
					</div>
					<button type="button" id="searchBtn" class="btn btn-default">查询</button>
				</form>
			</div>
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<th>序号</th>
						<th>用户名</th>
						<th>部门</th>
						<th>电话</th>
						<th>地址</th>
						<th>银行卡号</th>
					</tr>
				</thead>
				<tbody></tbody>
			</table>
			<ul class="pagination"></ul>
		</div>
		
		<form role="form" class="form-signin" action="${contextPath}/addDistrict" method="get">
			<button type="submit" class="btn btn-lg btn-primary btn-block">添加用户</button>
		</form>
		
	</div>
	<script type="text/javascript">
		var user_name = "";
		var dep = "";
		$(document).ready(function(){
			loadMainList(1);
			$("#searchBtn").click(function(){
				user_name = $("#search_user").val();
				dep = $("#search_dep").val();
				loadMainList(1);
			});
		});
		loadMainList = function(pageNum){
			$.ajax({
				type:'POST',
				data:{"userName":user_name,"name":dep,"page":pageNum},
				url:'${contextPath}/district',
				dataType:'json',
				success:function(data){
					$("tbody").empty();
					if(data.list == ""){
						$("tbody").empty();
						$("tbody").append("<tr class='text-danger'><td colspan='6'>无内容</td></tr>");
					}else{
						var pageTotal = data.totlePage;
						$.each(data.list,function(i,item){
							$("tbody").append(
								"<tr>"+
									"<td>"+(i+1)+"</td>"+
									"<td>"+item.userName+"</td>"+
									"<td>"+item.name+"</td>"+
									"<td>"+item.phone+"</td>"+
									"<td>"+item.address+"</td>"+
									"<td>"+item.bank+"</td>"+
								"</tr>"
							);
							var pageDom = $(".pagination");
							pageDom.empty();
							page.creatPageHTML(pageNum,pageTotal,pageDom,"loadMainList");
							/*--------------------------------------跳转页-------------------------------------------------------*/
							$(".pageGoBtn").click(function(){
								var pageNum = 0;
								pageNum = $(".pageGoText").val();
								if(pageNum !=0&&0<pageNum&&pageNum<pageTotal+1){
									chooseUserType(pageNum);
								}
							});
							
						});
					}
				}
			});
		};
	</script>
</body>
</html>
