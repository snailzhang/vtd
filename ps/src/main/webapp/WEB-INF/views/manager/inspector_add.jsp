<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>添加审核员</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta content="width=device-width, initial-scale=1" name="viewport">
<link rel="stylesheet" type="text/css" href="${contextPath}/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/bootstrap-theme.min.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/public.css">
<script type="text/javascript" src="${contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/common.js"></script>
<body>
	<jsp:include page="../head.jsp" />
	<div class="container">
		<form action="${contextPath}/security/addinspector" method="post" id="addinspector" name="addinspector" role="form" class="form-horizontal">
			<div class="form-group">
		      <label for="managerName" class="col-sm-2 control-label">审核员名称：</label>
		      <div class="col-sm-10">
		         <input type="text" class="form-control" name="inspectorName" id="inspectorName" placeholder="请输入审核员名称" required="required">
		      	<input type="hidden" class="form-control" name="userRegisted" id="" value="${userRegisted}">
		      </div>
		   </div>
		   <div class="form-group">
		      <div class="col-sm-offset-2 col-sm-10">
		         <button type="submit" class="btn btn-default">添加</button>
		      </div>
		   </div>
		</form>
	</div>
</body>
</html>

