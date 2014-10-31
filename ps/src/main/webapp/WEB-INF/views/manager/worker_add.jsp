<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>添加工作者</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta content="width=device-width, initial-scale=1" name="viewport">
<link rel="stylesheet" type="text/css" href="http://cdn.bootcss.com/bootstrap/3.2.0/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="http://cdn.bootcss.com/bootstrap/3.2.0/css/bootstrap-theme.min.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/public.css">
<script type="text/javascript" src="http://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/common.js"></script>
<body>
	<jsp:include page="../head.jsp" />
	<div class="container">
		<form action="${contextPath}/security/addworker" method="post" id="addworker" name="addworker" role="form" class="form-horizontal" enctype="multipart/form-data">
			<div class="form-group">
		      <label for="workerRealName" class="col-sm-2 control-label">真实姓名：</label>
		      <div class="col-sm-10">
		         <input type="text" class="form-control" name="workerRealName" id="workerRealName" placeholder="请输入真实姓名">
		         <span class="help-block"></span>
		         <input type="hidden" class="form-control" name="userRegisted" id="" value="${userRegisted}">
		         
		      </div>
		   </div>
		   <div class="form-group">
		      <label for="workerDisabilityCard" class="col-sm-2 control-label">残疾证卡号：</label>
		      <div class="col-sm-10">
		         <input type="text" class="form-control" name="workerDisabilityCard" id="workerDisabilityCard" placeholder="请输入残疾证卡号">
		         <span class="help-block"></span>
		      </div>
		   </div>
		   <div class="form-group">
		      <label for="workerBankCard" class="col-sm-2 control-label">银行卡号：</label>
		      <div class="col-sm-10">
		         <input type="text" class="form-control" name="workerBankCard" id="workerBankCard" placeholder="请输入银行卡号">
		         <span class="help-block"></span>
		      </div>
		   </div>
		   <div class="form-group">
		      <label for="workerPaypal" class="col-sm-2 control-label">支付宝账号：</label>
		      <div class="col-sm-10">
		         <input type="text" class="form-control" name="workerPaypal" id="workerPaypal" placeholder="请输入支付宝账号">
		         <span class="help-block"></span>
		      </div>
		   </div>
		    <div class="form-group">
		      <label for="workerPhone" class="col-sm-2 control-label">电话号：</label>
		      <div class="col-sm-10">
		         <input type="text" class="form-control" name="workerPhone" id="workerPhone" placeholder="请输入电话号码">
		         <span class="help-block"></span>
		      </div>
		   </div>
		   <div class="form-group">
		      <label for="workerImage" class="col-sm-2 control-label">照片：</label>
		      <div class="col-sm-10">
		         <input type="file" class="form-control" name="workerImage" id="workerImage" placeholder="请选择照片">
		         <span class="help-block"></span>
		      </div>
		   </div>
		   <div class="form-group">
		      <div class="col-sm-offset-2 col-sm-10">
		         <button type="button" class="btn btn-default">添加</button>
		      </div>
		   </div>
		</form>
	</div>
	<script type="text/javascript">
		$(document).ready(function(){
			$("button[type=button]").click(function(){
				var formName = $("#addworker");
				var workRealName = $("#workerRealName");
				//var workerIdCard = $("#workerIdCard");
				var workerDisabilityCard = $("#workerDisabilityCard");
				var wc = workerDisabilityCard.val();
				var workerPhone = $("#workerPhone");
				if(checkout.text.isempty(workRealName,"真实姓名不能为空！")) return;
				//if(checkout.text.isempty(workerIdCard,"身份证号不能为空！")) return;
				if(checkout.text.isempty(workerDisabilityCard,"残疾证卡号不能为空！")) return;
				if(wc.length<19){
					workerDisabilityCard.next(".help-block").css("color","red").text("残疾证卡号最少20位");
					return;
				}
				if(checkout.text.isempty(workerPhone,"电话号号不能为空！")) return;
				formName.submit();
				
			});
		});
	</script>
</body>
</html>

