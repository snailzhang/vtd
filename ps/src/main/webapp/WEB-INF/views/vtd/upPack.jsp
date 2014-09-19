<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">  
    <title>My JSP 'upTG.jsp' starting page</title> 
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="/js/lib/jquery-1.8.0.min.js"></script>
	<link rel="stylesheet" type="text/css"  href="/js/uploadify/uploadify.css" >  
     <script type="text/javascript" src="/js/uploadify/jquery.uploadify.min.js"></script>
      <script  type="text/javascript">
     </script>    
  </head>

  <body>
    This is my upPack 发包商编号:${employerid}<br>
    <div style="padding:15px 15px 15px 15px;">
		<form id="form" action="<%=basePath%>uploadpack"  enctype="multipart/form-data" method="post">
			任务等级:<input id="taskLvl" type="text" name="taskLvl" value=""/>
			发包商编号:<input id="employerId" type="text" name="employerId" value="${employerid}"/>
			上传的文件:<input id="pack" type="file" value="上传包" name="pack" />
			<input id="upload" type="submit" value="上传"/>	
		</form>
	</div>  
	  </body>
</html>
