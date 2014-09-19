<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
      	i = 1;    
	    $(document).ready(function(){      
	        $("#btn_add1").click(function(){  
	            document.getElementById("newUpload1").innerHTML+='<div id="div_'+i+'"><input  name="file" type="file"  /><input type="button" value="删除"  onclick="del_1('+i+')"/></div>';  
	              i = i + 1;  
	        });
	    });  
	    function del_1(o){  
	     document.getElementById("newUpload1").removeChild(document.getElementById("div_"+o));  
	    }   
     </script>    
  </head>

  <body>
    wav任务列表页,工作者编号:${workerid} <br>    
        <div id="newUpload1">
        	<table align="center" border="0">
        		<tr align="center">
	        		<td>任务编号</td>
	        		<td>任务等级</td>
        		</tr>
        		<c:forEach items="${TaskList}" var="tl">
	        		<tr align="center">
	        			<td>
	        				<a href="<%=basePath%>downTask?taskId=${tl.taskId}&workerid=${workerid}">${tl.taskId}</a>
			            </td>
			            <td>${tl.taskLvl}</td>
		            </tr>
	            </c:forEach >
            </table>   
        </div>          
	  </body>
</html>
