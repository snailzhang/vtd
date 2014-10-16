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
		<table class="table table-striped table-bordered">
			<thead>
				<tr>
					<th>序号</th>
					<th>姓名</th>
					<th>用户组</th>
					<th>创建时间</th>
					<th>用户状态</th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>
		<form role="form" class="form-signin" action="${contextPath}/security/addUser" method="get">
			<button type="submit" class="btn btn-lg btn-primary btn-block">添加用户</button>
		</form>
	</div>
	<script type="text/javascript" src="http://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
	<script type="text/javascript" src="http://cdn.bootcss.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$.ajax({
				type:'POST',
				url:'${contextPath}/security/manager',
				dataType:'json',
				success:function(data){
					$("tbody").append("");
					$.each(data,function(i,item){
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
					});
					
				}
			});
		});
	</script>
</body>
</html>
