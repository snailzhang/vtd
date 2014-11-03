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
							<div class="input-group-addon">用户名：</div>
							<input class="form-control" id="userNameCondition" type="text" placeholder="查询用户">
						</div>
					</div>
					<button type="button" id="searchBtn" class="btn btn-default">查询</button>
				</form>
			</div>
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<th>序号</th>
						<th>姓名</th>
						<th class="dropdown">
							<a id="userType" data-target="#" href="" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								用户组<span class="caret"></span>
							</a>
							<ul class="dropdown-menu" role="menu" aria-labelledby="userType">
								<li><a href="#" onClick="chooseUserType(0,1)">全部</a></li>
								<li><a href="#" onClick="chooseUserType(1,1)">管理员</a></li>
								<li><a href="#" onClick="chooseUserType(2,1)">发包商</a></li>
								<li><a href="#" onClick="chooseUserType(3,1)">质检员</a></li>
								<li><a href="#" onClick="chooseUserType(4,1)">工作者</a></li>
							</ul>
						</th>
						<th>创建时间</th>
						<th>用户状态</th>
					</tr>
				</thead>
				<tbody></tbody>
			</table>
			<ul class="pagination"></ul>
		</div>
		
		<form role="form" class="form-signin" action="${contextPath}/security/addUser" method="get">
			<button type="submit" class="btn btn-lg btn-primary btn-block">添加用户</button>
		</form>
		
	</div>
	<script type="text/javascript" src="http://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
	<script type="text/javascript" src="http://cdn.bootcss.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		var utDropdownOpen = false;
		var nowUserType = 0;
		var nowPage = 0;
		$(document).ready(function(){
			chooseUserType(0,1,"");
			
			$("#userType").click(function(){
				var utd = $(this);
				if(utDropdownOpen){
					utd.parent(".dropdown").removeClass("open");
					utDropdownOpen = false;
				}else{
					utd.parent(".dropdown").addClass("open");
					utDropdownOpen = true;
				}
			});
			$("#searchBtn").click(function(){
				var un = $("#userNameCondition").val();
				chooseUserType(0,1,un);
			});
		});
		chooseUserType = function(userType,pageNum,userNameCondition){
			//nowPage = pageNum;
			//nowUserType = userType;
			$.ajax({
				type:'POST',
				data:{"userType":userType,"page":pageNum,"userNameCondition":userNameCondition},
				url:'${contextPath}/security/manager',
				dataType:'json',
				success:function(data){
					$("tbody").empty();
					utDropdownOpen = false;
					if(data.list == ""){
						$("tbody").empty();
						$("tbody").append("<tr class='text-danger'><td colspan='5'>无内容</td></tr>");
					}else{
						var pageTotal = data.totlePage;
						$.each(data.list,function(i,item){
							var status = "不可用";
							if(item.userStatus)status = "可用";
							$("tbody").append(
								"<tr>"+
									"<td>"+(i+1)+"</td>"+
									"<td>"+item.username+"</td>"+
									"<td>"+item.usertypeenglish+"</td>"+
									"<td>"+item.createTime+"</td>"+
									"<td>"+status+"</td>"+
								"</tr>"
							);
							$(".pagination").empty();
							for(var i=1;i<pageTotal+1;i++){
								if(i==pageNum){
									$(".pagination").append(
										"<li class='active'><a onClick='chooseUserType("+userType+","+i+",\""+userNameCondition+"\")'>"+
										i+
										"</a></li>"
									);
								}else{
									$(".pagination").append(
										"<li><a onClick='chooseUserType("+userType+","+i+",\""+userNameCondition+"\")'>"+
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
