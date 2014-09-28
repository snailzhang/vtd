<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.esd.db.model.pack" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
List packList = (List)request.getAttribute("packList");
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
				<a href="#" class="navbar-brand"> 发包商名:${loginrName}</a>
			</div>
		</div>
	</nav>
	<div class="container">
		<table class="table table-striped table-bordered">
			<thead>
				<tr>
					<th>序号</th>
					<th>任务名称</th>
					<th>完成情况</th>
					<th>上传时间</th>
				</tr>
			</thead>
			<tbody>
				<%
					if(packList !=null){
						for(int i=0;i<packList.size();i++){
							pack p = (pack)packList.get(i);
							int pId = p.getPackId();
							String pName = p.getPackName();
							Boolean status = p.getPackStatus();
							String pStatus = "未完成";
							if(status) pStatus = "已完成";
							Date cTime = p.getCreateTime();
							String ct = new SimpleDateFormat("yyyy-MM-dd").format(cTime);
							%>
							<tr>
				                <td><%=i+1 %></td>
				                <td><%=pName %></td>
				                <td><%=pStatus %></td>
				                <td><%=ct %></td>
				              </tr>
							<%
						}
					}
				%>
				
			</tbody>
		</table>
		<form role="form" class="form-signin">
			
			<button type="button" class="btn btn-lg btn-primary btn-block">上传任务</button>
		</form>
	</div>
	<script type="text/javascript" src="http://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
	<script type="text/javascript" src="http://cdn.bootcss.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</body>
</html>
