<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" /> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>500</title>

<link href="${contextPath}/css/error.css" rel="stylesheet" type="text/css" />

</head>

<body>
	<div class="container">
		<div class="wrong text-center">
			<p><img src="${contextPath}/images/error/404.png"/></p>
			<p class="wrong-num">500</p>
			<p class="wrong-sorry">抱歉, 服务器出错啦!</p>
			<a href="${contextPath }" class="btn btn-large btn-success">返回首页</a>
		</div>
	
		<!-- footer -->
		<%@ include file="footer.jsp" %>
	</div>
</body>
</html>
