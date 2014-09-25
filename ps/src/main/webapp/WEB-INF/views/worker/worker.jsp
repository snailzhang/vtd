<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.esd.db.model.task" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
List taskList = (List)request.getAttribute("taskList");
String workerMark = (String)request.getAttribute("workerMark");
%>

<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'manager.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="http://cdn.bootcss.com/bootstrap/3.2.0/css/bootstrap.min.css" />
	<link rel="stylesheet" type="text/css" href="http://cdn.bootcss.com/bootstrap/3.2.0/css/bootstrap-theme.min.css" />
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
				<a href="#" class="navbar-brand"> 工作者名:${loginrName}</a>
			</div>
		</div>
	</nav>
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
			<tbody>
				<%
					if(taskList !=null){
						for(int i=0;i<taskList.size();i++){
							task t = (task)taskList.get(i);
							int tId = t.getTaskId();
							String tName = t.getTaskName();
							Double tMarkTime = t.getTaskMarkTime();
							Date dTime = t.getTaskDownloadTime();
							Date uTime = t.getTaskUploadTime();
							String dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dTime);
							String ut = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(uTime);
							%>
							<tr>
				                <td><%=i+1 %></td>
				                <td><%=tName %></td>
				                <td><%=tMarkTime %></td>
				                <td><%=dt %></td>
				                <td><%=ut %></td>
				              </tr>
							<%
						}
					}
				%>
				
			</tbody>
		</table>
		<% 
			if(workerMark == "down"){
				%>
				<a type="button" class="btn btn-lg btn-primary btn-block" href="">下载任务</a>
				<%
			}else{
				%>
				<form action="<%=basePath%>logins" method="post" name="form1" role="form" class="form-signin">
					<h2 class="form-signin-heading">选择上传文件</h2>
					<span>TAG:</span><input type="file" name="tag" autofocus="" required="" placeholder="上传TAG文件" class="form-control">
					<span>TextGrid:</span><input type="file" required="" placeholder="上传TextGrid文件" name="TextGrid" class="form-control">
					<button type="submit" class="btn btn-lg btn-primary btn-block">上传文件</button>
				</form>
				<%
			}
		%>
			
	</div>
	<script type="text/javascript" src="http://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
	<script type="text/javascript" src="http://cdn.bootcss.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</body>
</html>
