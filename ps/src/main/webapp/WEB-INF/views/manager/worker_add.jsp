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
		         <input type="text" class="form-control" name="workerRealName" id="workerRealName" required="required" placeholder="请输入真实姓名">
		         <span class="help-block"></span>
		         <input type="hidden" class="form-control" name="userRegisted" id="" value="${userRegisted}">
		         
		      </div>
		   </div>
		   <div class="form-group">
		      <label for="workerDisabilityCard" class="col-sm-2 control-label">残疾证卡号：</label>
		      <div class="col-sm-10">
		         <input type="text" class="form-control" name="workerDisabilityCard" id="workerDisabilityCard" required="required" placeholder="请输入残疾证卡号">
		         <span class="help-block"></span>
		      </div>
		   </div>
		   <div class="form-group">
		      <label for="workerBankCard" class="col-sm-2 control-label">银行卡号：</label>
		      <div class="col-sm-10">
		         <input type="text" class="form-control" name="workerBankCard" id="workerBankCard" required="required" placeholder="请输入银行卡号">
		         <span class="help-block"></span>
		      </div>
		   </div>
		   <div class="form-group">
		      <label for="workerPaypal" class="col-sm-2 control-label">支付宝账号：</label>
		      <div class="col-sm-10">
		         <input type="text" class="form-control" name="workerPaypal" id="workerPaypal" required="required" placeholder="请输入支付宝账号">
		         <span class="help-block"></span>
		      </div>
		   </div>
		    <div class="form-group">
		      <label for="workerPhone" class="col-sm-2 control-label">电话号：</label>
		      <div class="col-sm-10">
		         <input type="text" class="form-control" name="workerPhone" id="workerPhone" required="required" maxlength="12" placeholder="请输入电话号码">
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
		         <button type="submit" class="btn btn-default">添加</button>
		      </div>
		   </div>
		</form>
	</div>
	<script type="text/javascript">
		var disCardOnlyStatus = false;//残疾证号唯一
		var telOnlyStatus = false;
		checkDisCardOnly = function(){
			var disCard = $("#workerDisabilityCard").val();
			$.ajax({
				type:'post',
				url:'${contextPath}/security/checkWorkerDisabilityCard',
				data:{"WorkerDisabilityCard":disCard},
				dataType:'json',
				success:function(data){
					if(data.replay){
						disCardOnlyStatus = true;
						$("#workerDisabilityCard").next(".help-block").empty();
					}else{
						$("#workerDisabilityCard").next(".help-block").addClass("text-danger").text(data.message);
						disCardOnlyStatus = false;
					}
				}
			});
		};
		checkTelOnly = function(){
			var tel = $("#workerPhone").val();
			$.ajax({
				type:'post',
				url:'${contextPath}/security/checkWorkerPhone',
				data:{"workerPhone":tel},
				dataType:'json',
				success:function(data){
					if(data.replay){
						telOnlyStatus = true;
						$("#workerPhone").next(".help-block").empty();
					}else{
						telOnlyStatus = false;
						$("#workerPhone").next(".help-block").addClass("text-danger").text(data.message);
					}
				}
			});
		};
		checkDiscard = function(){
			var wc = $("#workerDisabilityCard");
			if(wc.val().length<19){
				wc.next(".help-block").css("color","red").text("残疾证卡号最少20位");
				return false;
			}
			
		};
		$(document).ready(function(){
			$("#workerDisabilityCard").blur(function(){
				checkDiscard();
				checkDisCardOnly();
			});
			$("#workerPhone").blur(function(){
				checkTelOnly();
			});
			$("#addworker").submit(function(){
				if(!checkDiscard())return false;
				checkTelOnly();
				if(disCardOnlyStatus && telOnlyStatus){
					return true;
				}
				
				return false;
			});
		});
	</script>
</body>
</html>

