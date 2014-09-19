<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" /> 
<html>
  <head>
    <title>My JSP 'test_controller.jsp' starting page</title>
    
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="${contextPath}/js/lib/jquery-1.8.0.min.js"></script>
	<script type="text/javascript">
	$(function(){
		$('#btnC').click(function(){
			cStart();
		});
		$('#btnD').click(function(){
			dStart();
		});
		$('#btnCs').click(function(){
			clearInterval(cValue);
		});
		$('#btnDs').click(function(){
			clearInterval(dValue);
		});
		
	});
	
	//预定义定时器的返回值
	var cValue; //controller
	var dValue;	//db
	
	//定时刷新器, 每0.5秒刷新一次
	function cStart(){
		cValue = setInterval('cRequest()',1000);
	}
	function dStart(){
		dValue = setInterval('dRequest()',1000);
	}
	
	//请求controller
	function cRequest(){
		$.ajax({
			url:'${contextPath}/test/80',
			type:'GET',
			success:function(data){
				$('#controllerValue').html("controller请求成功, 现在是第 "+data.entity+ " 次.");
			}
		});
	}
	
	//请求controller
	function dRequest(){
		$.ajax({
			url:'${contextPath}/test/81',
			type:'GET',
			success:function(data){
				if(data.entity == true){
					$('#dbValue').html("数据插入成功请求成功, 现在是第 "+data.dbCount+ " 次.");
				}else{
					$('#dbValue').html("数据插入成功请求失败, 现在是第 "+data.dbCount+ " 次.");
					//出错清除
					clearInterval(dValue);
				}
			}
			
		});
	}
	
	</script>
  </head>
  
  <body>
    <input type="button" id="btnC" value="开始请求controll, 1秒/次" /><input type="button" id="btnCs" value="停止" /><br/>
    <div id="controllerValue"></div>
    <input type="button" id="btnD" value="开始请求db, 1秒/次" /><input type="button" id="btnDs" value="停止" /><br/>
    <div id="dbValue"></div>
  </body>
</html>









