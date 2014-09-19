<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'test_controller.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>

  <body>
    用户名:${loginrName}&nbsp;&nbsp;&nbsp;&nbsp;用户类别:${usertype}&nbsp;&nbsp;&nbsp;&nbsp;发包商号或工作号:${ewid}<br>
	<div style="padding:15px 15px 15px 15px;">
		<table class="parameter_tab" align="center">
				<tr align="center">
					<td style="width: 100px;">
					<a href="<%request.getContextPath(); %>/ps/turn?type=0&ewid=${ewid}">新增用户</a>
					</td>
				</tr>
				<tr align="center">
					<td style="width: 100px;">
					<a href="<%request.getContextPath(); %>/ps/turn?type=1&ewid=${ewid}">新增管理员</a>	
					</td>
				</tr>
				<tr align="center">
					<td style="width: 100px;">
					<a href="<%request.getContextPath(); %>/ps/turn?type=2&ewid=${ewid}">新增发包商</a>	
					</td>
				</tr>
				<tr align="center">
					<td style="width: 100px;">
					<a href="<%request.getContextPath();%>/ps/turn?type=4&ewid=${ewid}">新增工作者</a>	
					</td>
				</tr>
				<tr align="center">
					<td>
						<span id="username_message"></span>
					</td>
				</tr>
				<tr align="center">
					<td style="width: 100px;">		
					<a href="<%request.getContextPath();%>/ps/turn?type=6&ewid=${ewid}">上传任务包</a>
					</td>
				</tr>
				<tr align="center">
					<td style="width: 100px;">		
					<a href="<%request.getContextPath();%>/ps/turn?type=5&ewid=${ewid}">上传分解文件</a>
					</td>
				</tr>
				<tr align="center">
					<td style="width: 100px;">
					<a href="<%request.getContextPath();%>/ps/turn?type=7&ewid=${ewid}">下载</a>
					</td>
				</tr>
				<tr>
					<td style="width: 100px;">				
					</td>
				</tr>
			</table>
	</div>
  </body>
</html>
