<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>工作者个人中心</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta content="width=device-width, initial-scale=1" name="viewport">
<link rel="stylesheet" type="text/css" href="http://cdn.bootcss.com/bootstrap/3.2.0/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="http://cdn.bootcss.com/bootstrap/3.2.0/css/bootstrap-theme.min.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/public.css">
<style type="text/css">
#updDataBtn{display:none;}
</style>
<script type="text/javascript" src="http://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/common.js"></script>
</head>
<body>
	<jsp:include page="../head.jsp" />
	<div class="container">
		<h1 id="" class="page-header">个人中心</h1>
		<div id=message></div>
	</div>
	<div class="container">
		<form action="${contextPath}/security/addworker" method="post" id="addworker" name="addworker" role="form" class="form-horizontal" enctype="multipart/form-data">
			<div class="form-group">
		      <label for="workerRealName" class="col-sm-2 control-label">真实姓名：</label>
		      <div class="col-sm-10">
		         <input type="text" class="form-control" name="workerRealName" id="workerRealName" value="${worker.workerRealName}" readonly="readonly">
		         <span class="help-block"></span>
		         
		         
		      </div>
		   </div>
		   <div class="form-group">
		      <label for="workerDisabilityCard" class="col-sm-2 control-label">残疾证卡号：</label>
		      <div class="col-sm-10">
		         <input type="text" class="form-control" name="workerDisabilityCard" id="workerDisabilityCard" value="${worker.workerDisabilityCard}" readonly="readonly">
		         <span class="help-block"></span>
		      </div>
		   </div>
		   <div class="form-group">
		      <label for="workerBankCard" class="col-sm-2 control-label">银行卡号：</label>
		      <div class="col-sm-10">
		         <input type="text" class="form-control" name="workerBankCard" id="workerBankCard" value="${worker.workerBankCard}" readonly="readonly">
		         <span class="help-block"></span>
		      </div>
		   </div>
		   <div class="form-group">
		      <label for="workerPaypal" class="col-sm-2 control-label">支付宝账号：</label>
		      <div class="col-sm-10">
		         <input type="text" class="form-control" name="workerPaypal" id="workerPaypal"  value="${worker.workerPaypal}" readonly="readonly">
		         <span class="help-block"></span>
		      </div>
		   </div>
		    <div class="form-group">
		      <label for="workerPhone" class="col-sm-2 control-label">电话号：</label>
		      <div class="col-sm-10">
		         <input type="text" class="form-control" name="workerPhone" id="workerPhone"  value="${worker.workerPhone}" readonly="readonly">
		         <span class="help-block"></span>
		      </div>
		   </div>
		   <div class="form-group">
		      <label for="workerImage" class="col-sm-2 control-label">照片：</label>
		      <div class="col-sm-10">
		         <input type="file" class="form-control" name="workerImage" id="workerImage"  value="${worker.workerImage}" readonly="readonly">
		         <span class="help-block"></span>
		      </div>
		   </div>
		   <div class="form-group">
		      <div class="col-sm-offset-2 col-sm-10">
		         <button id="changeDataBtn" type="button" class="btn btn-primary">编辑资料</button>
		         <button id="updDataBtn" type="button" class="btn btn-warning">提交修改</button>
		      </div>
		   </div>
		</form>
	</div>
	
	<script type="text/javascript">
		$(document).ready(function(){
			
			$("#changeDataBtn").click(function(){
				$("#changeDataBtn").hide();
				$("#workerBankCard,#workerPaypal,#workerPhone,#workerImage").removeAttr("readonly");
				$("#updDataBtn").show();
			});
		});
			
		

	</script>
</body>
</html>
