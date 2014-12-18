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
<link rel="stylesheet" type="text/css" href="${contextPath}/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/bootstrap-theme.min.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/public.css">
<script type="text/javascript" src="${contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/common.js"></script>
</head>
<body>
	<jsp:include page="../head.jsp" />
	<div class="container">
		<h1 id="" class="page-header">个人中心</h1>
		<div id=message></div>
	</div>
	<div class="container">
		<form action="${contextPath}/security/addworker" method="post" id="addworker" name="addworker" role="form" class="form-horizontal">
			<div class="form-group">
		      <label for="workerRealName" class="col-sm-2 control-label">真实姓名：</label>
		      <div class="col-sm-10">
		         <p class="form-control-static">${worker.workerRealName}</p>
		      </div>
		   </div>
		   <div class="form-group">
		      <label for="workerDisabilityCard" class="col-sm-2 control-label">残疾证卡号：</label>
		      <div class="col-sm-10">
		         <p class="form-control-static">${worker.workerDisabilityCard}</p>
		      </div>
		   </div>
		   <div class="form-group">
		      <label for="workerBankCard" class="col-sm-2 control-label">银行卡号：</label>
		      <div class="col-sm-10">
		         <input type="text" class="form-control" name="workerBankCard" id="workerBankCard" value="${worker.workerBankCard}">
		         <span class="help-block"></span>
		      </div>
		   </div>
		   <div class="form-group">
		      <label for="workerPaypal" class="col-sm-2 control-label">支付宝账号：</label>
		      <div class="col-sm-10">
		         <input type="text" class="form-control" name="workerPaypal" id="workerPaypal"  value="${worker.workerPaypal}">
		         <span class="help-block"></span>
		      </div>
		   </div>
		    <div class="form-group">
		      <label for="workerPhone" class="col-sm-2 control-label">电话号：</label>
		      <div class="col-sm-10">
		         <input type="text" class="form-control" name="workerPhone" id="workerPhone"  value="${worker.workerPhone}">
		         <span class="help-block"></span>
		      </div>
		   </div>
		  
		   <div class="form-group">
		      <div class="col-sm-offset-2 col-sm-10">
		         <button id="updDataBtn" type="button" class="btn btn-warning">提交修改</button>
		      </div>
		   </div>
		</form>
	</div>
	
	<script type="text/javascript">
		$(document).ready(function(){
			$("#updDataBtn").click(function(){
				var tel = $("#workerPhone");
				if(checkout.text.isempty(tel,"电话号不能为空！")){
					return;
				}
				var telNum = tel.val();
				var workerBankCard = $("#workerBankCard").val();
				var workerPaypal = $("#workerPaypal").val();
				$.ajax({
					type:'post',
					url:'${contextPath}/security/updateWorker',
					data:{"workerPhone":telNum,"workerBankCard":workerBankCard,"workerPaypal":workerPaypal},
					dataType:'json',
					success:function(data){
						if(!data.replay){
							var msgS = '<div class="alert alert-danger alert-dismissable">'
										+ '<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>'
										+ data.message + '</div>';
							$("#message").html(msgS);
						}else{
							var msgS = '<div class="alert alert-success alert-dismissable">'
										+ '<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>'
										+ data.message + '</div>';
							$("#message").html(msgS);
						}
					}
				});
			});
		});
			
		

	</script>
</body>
</html>
