<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.esd.db.model.user" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
List userList = (List)request.getAttribute("userlist");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'manager.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%= basePath%>css/bootstrap.min.css" />
	<link rel="stylesheet" type="text/css" href="<%= basePath%>css/bootstrap-theme.min.css" />
	<link rel="stylesheet" type="text/css" href="<%= basePath%>css/login.css" />
	<style type="text/css">
		body {
		  padding-top: 40px;
		  padding-bottom: 40px;
		  background-color: #eee;
		}
		div.container{
			max-width:1000px;
		}
		table{
			margin-top:20px;
		}
	</style>
  </head>

<body>
	<nav role="navigation" class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<a href="#" class="navbar-brand"> 用户名:${loginrName}</a>
			</div>
		</div>
	</nav>
	<div class="container">
		<table class="table table-striped table-bordered">
			<thead>
				<tr>
					<th>序号</th>
					<th>姓名</th>
					<th>用户组</th>
					<th>创建时间</th>
				</tr>
			</thead>
			<tbody>
				<%
					if(userList !=null){
						for(int i=0;i<userList.size();i++){
							user u = (user)userList.get(i);
							int uId = u.getUserId();
							String uName = u.getUsername();
							int uType = u.getUsertype();
							String ut = "";
							if(uType == 1){
								ut = "管理员";
							}else if(uType == 2){
								ut = "发包员";
							}else if(uType == 3){
								ut = "质检员";
							}else if(uType == 4){
								ut = "工作者";
							}
							Date cTime = u.getCreateTime();
							String ct = new SimpleDateFormat("yyyy-MM-dd").format(cTime);
							%>
							<tr>
				                <td><%=i+1 %></td>
				                <td><%=uName %></td>
				                <td><%=ut %></td>
				                <td><%=ct %></td>
				              </tr>
							<%
						}
					}
				%>
				
			</tbody>
		</table>
		<form role="form" class="form-signin">
			<button type="submit" class="btn btn-lg btn-primary btn-block">添加用户</button>
		</form>
	</div>
	<script type="text/javascript" src="<%= basePath%>js/lib/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="<%= basePath%>js/lib/bootstrap.min.js"></script>
</body>
</html>
