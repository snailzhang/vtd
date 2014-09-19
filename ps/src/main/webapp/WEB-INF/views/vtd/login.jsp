<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>vtd的 'login.jsp'page</title> 
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
	<div align="center">
	    <form action="<%=basePath%>logins" method="post" name="form1">  
	   <table align="center" border="0">  
	    <tr>  
	     <td colspan="2" align="center">  
	      <H1>&nbsp;请登录   </H1>  
	     </td>  
	    </tr>  
	    <tr>  
	     <td>  
	      用户名：  
	     </td>  
	     <td>  
	      <input type="text" name="username" value=""/>  
	     </td>  
	    </tr>  
	    <tr>  
	     <td>  
	      密 码：  
	     </td>  
	     <td>  
	      <input type="password" name="password" value=""/>  
	     </td>  
	    </tr>  
	    <tr>  
	     <td colspan="2" align="center">  
	      <input type="submit" value="登录" />           
	      <input type="reset" value="重置" />  
	     </td>  
	    </tr>  
	   </table>  
	  </form>
	</div>
  <div align="center">${loginreplay}</div>  
  </body>
</html>
