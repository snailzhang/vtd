<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>管理者页面</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta content="width=device-width, initial-scale=1" name="viewport">
<link rel="stylesheet" type="text/css" href="${contextPath}/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/bootstrap-theme.min.css" />
<link href="${contextPath}/css/jquery-ui.min.css" rel="stylesheet">
<link href="${contextPath}/css/jquery-ui.theme.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="${contextPath}/css/public.css">
<style type="text/css">
	.input-group-addon,.form-control{padding:6px;}
	.ui-datepicker .ui-datepicker-title select{color:#1c94c4}
</style>
<script type="text/javascript" src="${contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/common.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.ui.core.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.ui.datepicker.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.ui.datepicker-ch.js"></script>
</head>
<body>
	<jsp:include page="../head.jsp" />
	<div class="container">
	<!-----------------------------------------------------选项卡 ----------------------------->
		<ul class="nav nav-tabs" role="tablist">
			<li class="active"><a href="#workerList" role="tab" data-toggle="tab">用户列表</a></li>
			<li><a href="#workerSalarList" role="tab" data-toggle="tab">工资列表</a></li>
			<li><a href="#workerLvlList" role="tab" data-toggle="tab">等级列表</a></li>
		</ul>
	<!--------------------------------------------------------------------------------------->
		<div class="tab-content">
		<!-- -----------------------------------------------用户列表------------------------------------------------ -->
			<div class="tab-pane active" id="workerList">
				<div class="panel panel-default">
					<div class="panel-heading" style="height: 38px;">	
						<span id="aduitingMarkTimeMonthTotle" class="pull-right text-success" style="padding-left: 20px;"></span>
						<span id="taskMarkTimeMonthTotle" class="pull-right text-success"></span>
					</div>
					<div class="panel-body">
						<form class="form-inline" role="form">					
							<div class="col-sm-3" style="width: 225px;">
								<div class="form-group">
									<div class="input-group" style="width: 220px;"> 
										<div class="input-group-addon"> 用户名</div>
										<input class="form-control" onkeydown="if(event.keyCode==13){return false;}" id="userNameCondition" type="text" placeholder="查询用户">
									</div>
								</div>
							
							</div>					
							<div class="col-xs-2" style="width: 140px;">
								<div class="form-group">
									<div class="input-group" style="width: 135px;">
										<div class="input-group-addon">起始</div>
										<input class="form-control" onkeydown="if(event.keyCode==13){return false;}" id="beginDate" type="text" placeholder="起始时间">
									</div>
								</div>
							</div>					
							<div class="col-xs-2" style="width: 140px;">
								<div class="form-group">
									<div class="input-group" style="width: 135px;">
										<div class="input-group-addon">截止</div>
										<input class="form-control" onkeydown="if(event.keyCode==13){return false;}" id="endDate" type="text" placeholder="截止时间">
									</div>
								</div>
							</div>			
							<div class="col-xs-2" style="width: 101px;">
								<div class="form-group">
									<div class="input-group" style="width: 96px;">	
										<div class="input-group-addon">类别</div>
										<select class="form-control" id="dateTypeCheck">
											<option value="1">日</option>
											<option value="2">月</option>
											<option value="3">年</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-xs-2" style="width: 101px;">
								<div class="form-group">
									<div class="input-group" style="width: 96px;">
										<div class="input-group-addon">状态</div>
										<select class="form-control" id="taskUpload">
											<option value="2">全</option>
											<option value="1">有</option>
											<option value="0">无</option>
										</select>
									</div>
								</div>				
							</div>
							<div class="btn2" style="float: right;">
								<button type="button" id="searchBtn" class="btn btn-default" >查询</button>
							<!-- cx20150105 -->
								<button type="button" id="changeBtn" class="btn btn-default" >设置</button>
							</div>			
						</form>
						<div style="clear: both;"></div>
					</div>
					<table class="table table-striped table-bordered">
						<thead>
							<tr>
								<th width='4%'>序号</th>
								<th width='12%'>用户名</th>
								<th width='12%'>姓名</th>
								<th width='12%'>电话号</th>
								<th width='15%'>标注时间</th>
								<th width='15%'>未审时间</th>
								<th width='12%'>下/进/审</th>
								<th width='12%'>完/弃/过</th>
								<th width='8%'>状态</th>
							</tr>
						</thead>
						<tbody id = "user-tbody"></tbody>
					</table>
					<ul class="pagination"></ul>
				</div>
			</div>
			<!-- ----------------------------------------------工资列表--------------------------------------------------- -->
			<div class="tab-pane" id="workerSalarList">
				<div class="panel panel-default">
					<div class="panel-heading" style="height: 38px;">
						<span id="aduitingMarkTimeMonthTotle1" class="pull-right text-success" style="padding-left: 20px;"></span>
						<span id="taskMarkTimeMonthTotle1" class="pull-right text-success"></span>
					</div>
					<div class="panel-body">
						<form class="form-inline" role="form">					
							<div class="col-sm-3" style="width: 175px;">
								<div class="form-group">
									<div class="input-group" style="width: 170px;"> 
										<div class="input-group-addon"> 用户名</div>
										<input class="form-control" onkeydown="if(event.keyCode==13){return false;}" id="userNameCondition1" type="text" placeholder="查询用户">
									</div>
								</div>							
							</div>					
							<div class="col-xs-2" style="width: 140px;">
								<div class="form-group">
									<div class="input-group" style="width: 135px;">
										<div class="input-group-addon">起始</div>
										<input class="form-control" onkeydown="if(event.keyCode==13){return false;}" id="beginDate1" type="text" placeholder="起始时间">
									</div>
								</div>
							</div>					
							<div class="col-xs-2" style="width: 140px;">
								<div class="form-group">
									<div class="input-group" style="width: 135px;">
										<div class="input-group-addon">截止</div>
										<input class="form-control" onkeydown="if(event.keyCode==13){return false;}" id="endDate1" type="text" placeholder="截止时间">
									</div>
								</div>
							</div>			
							<div class="col-xs-2" style="width: 101px;">
								<div class="form-group">
									<div class="input-group" style="width: 96px;">	
										<div class="input-group-addon">类别</div>
										<select class="form-control" id="dateTypeCheck1">
											<option value="1">日</option>
											<option value="2" selected="selected">月</option>
											<option value="3">年</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-sm-3" style="width: 101px;">
								<div class="form-group">
									<div class="input-group" style="width: 96px;"> 
										<div class="input-group-addon"> 金额</div>
										<input class="form-control" onkeydown="if(event.keyCode==13){return false;}" id="salaryLine" type="text" placeholder="输入金额" value="0">
									</div>
								</div>							
							</div>
					  		<div class="col-xs-2" style="width: 111px;">
								<div class="form-group">
									<div class="input-group" style="width: 105px;">
										<div class="input-group-addon">结算</div>
										<select class="form-control" id="payOffType">
											<option value="2">全部</option>
											<option value="1">已结</option>
											<option value="0">未结</option>
										</select>
									</div>
								</div>				
							</div>
							<div class="btn2" style="float: right;">
								<button type="button" id="searchSalaryBtn" class="btn btn-default" >查询</button>
								<button type="button" id="payOffBtn" class="btn btn-default" >结算</button>
							</div>			
						</form>
						<div style="clear: both;"></div>
					</div>
					<table class="table table-striped table-bordered">
						<thead>
							<tr>
								<th width='4%'>序号</th>
								<th width='12%'>姓名</th>
								<th width='12%'>银行卡号</th>
								<th width='15%'>标注时间</th>
								<th width='15%'>金额</th>
								<th width='10%'><a href="#" id="choose"><span id="choose-p">全选</span></a></th>
							</tr>
						</thead>
						<tbody id = "workerSalary-tbody"></tbody>
					</table>
					<ul class="pagination"></ul>
				</div>
			</div>
			<!-- ------------------------------------------------------------------------------------------------- -->
			<!-- ----------------------------------------------等级列表--------------------------------------------------- -->
			<div class="tab-pane" id="workerLvlList">
				<div class="panel panel-default">
					<div class="panel-body">
						<form class="form-inline" role="form">					
							<div class="col-sm-3" style="width: 175px;">
								<div class="form-group">
									<div class="input-group" style="width: 170px;"> 
										<div class="input-group-addon"> 用户名</div>
										<input class="form-control" onkeydown="if(event.keyCode==13){return false;}" id="userNameConditionWorkerLvl" type="text" placeholder="查询用户">
									</div>
								</div>							
							</div>
							<div class="col-xs-2" style="width: 101px;">
								<div class="form-group">
									<div class="input-group" style="width: 105px;">	
										<div class="input-group-addon">等级</div>
										<select class="form-control" id="userLvl">
											<option value="0" selected="selected">全部</option>
											<option value="1">一级</option>
											<option value="2">二级</option>
										</select>
									</div>
								</div>
							</div>					
							<div class="btn2" style="float: right;">
								<button type="button" id="searchWorkerLvlBtn" class="btn btn-default" >查询</button>
								<button type="button" id="updateWorkerLvlBtn" class="btn btn-default" >设置</button>
							</div>			
						</form>
						<div style="clear: both;"></div>
					</div>
					<table class="table table-striped table-bordered">
						<thead>
							<tr>
								<th width='4%'>序号</th>
								<th width='12%'>姓名</th>
								<th width='15%'>等级</th>
								<th width='10%'><a href="#" id="chooseLvl"><span id="choose-lvl">全选</span></a></th>
							</tr>
						</thead>
						<tbody id = "workerLvl-tbody"></tbody>
					</table>
					<ul class="pagination"></ul>
				</div>
			</div>
			<!-- ------------------------------------------------------------------------------------------------- -->
		</div>
		
		<div class="modal fade" id="changeUserStatusModal">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
						<h4 class="modal-title"></h4>
					</div>
					<div class="modal-body">
						<span id="updateStatus"></span>
						<form class="form-inline" role="form">
						<div class="form-group">
							<label class="radio-inline">
								<input type="radio" class="changeust" name="inlineRadioOptions" id="" value="1" autocomplete="off">可用
							</label>
					   </div>
					   <div class="form-group">
							<label class="radio-inline">
								<input type="radio" class="changeust" name="inlineRadioOptions" id="" value="0" autocomplete="off">禁用
							</label>
					   </div>
					   </form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button type="button" id="changeUserStatusBtn" class="btn btn-primary">修改</button>
					</div>
				</div><!-- /.modal-content -->
			</div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
		
		
		<!-- cx20150106 changeManagerInform -->
		<div class="modal fade" id="changeManagerInform">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
						<h4 class="modal-title"></h4>
					</div>
					<div class="modal-body">
						<span id="updateStatus"></span>
						<div style="padding:10px 30px 10px;">
							<form class="form-inline" role="form">
								<div class="input-group" style="padding:3px 3px;">
	        					 	<span class="input-group-addon">最大下载数</span>
	         						<input type="text" class="form-control" id="downMaxCountInput" placeholder="请输入">
	      						</div>
	      						<div class="input-group" style="padding:3px 3px;">
	        					 	<span class="input-group-addon">单次下载数</span>
	         						<input type="text" class="form-control" id="downCountInput" placeholder="请输入">
	      						</div>
	      						<br>
	      						<div class="input-group" style="padding:3px 3px;">
	        					 	<span class="input-group-addon">文件过滤数</span>
	         						<input type="text" class="form-control" id="fileSizeInput" placeholder="请输入">
	      						</div>
	      						<!--  <div class="input-group" style="padding:3px 3px;">
	        					 	<span class="input-group-addon">每小时薪金</span>
	         						<input type="text" class="form-control" id="salaryInput" placeholder="请输入">
	      						</div> -->
						   </form>
					   </div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button type="button" id="changeManagerInformBtn" class="btn btn-primary">修改</button>
					</div>
				</div><!-- /.modal-content -->
			</div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
	</div>
	<script type="text/javascript">
		var utDropdownOpen = false;
		var nowUserType = 4;
		var nowPage = 0;
		var userNameCondition = "";
		
		var taskUpload = 2;
		var userId = 0;
		var pageTotal = 0;
		var dateType = 1;
		var beginDate = "";
		var endDate = "";
		//等级条件
		var userNameConditionWorkerLvl = "";
		var userLvl = 0;
		//
		var userNameCondition1 = "";	
		var dateType1 = 2;
		var beginDate1 = "";
		var endDate1 = "";
		var salaryLine = 0;
		var payOffType = 2;//2:常规 0:未结 1:已结
		$(document).ready(function(){
			
			var date = new Date();
			var todayDate = $.datepicker.formatDate( "yy-mm-dd",new Date());
			beginDate = todayDate;
			endDate = todayDate;
			beginDate1 = todayDate;
			endDate1 = todayDate;
			$("#beginDate,#endDate").val(todayDate);
			$("#beginDate1,#endDate1").val(todayDate);
			chooseUserType(1);
			//结算按钮
			$("#payOffBtn").attr("disabled","disabled");
			workerSalaryList(1);
			workerLvlList(1);
			/*-------------------------------datepicker-----------------------------------------*/
			$.datepicker.regional[ "ch" ];
			$("#beginDate").datepicker({
				changeMonth:true,
				changeYear:true,
				numberOfMonths:1,
				setDate:todayDate,
				onClose: function( selectedDate ) {
					$( "#endDate" ).datepicker( "option", "minDate", selectedDate );
				}
			});
			$("#endDate").datepicker({
				changeMonth:true,
				changeYear:true,
				numberOfMonths:1,
				onClose: function( selectedDate ) {
					$( "#beginDate" ).datepicker( "option", "maxDate", selectedDate );
				}
			});
			$("#beginDate1").datepicker({
				changeMonth:true,
				changeYear:true,
				numberOfMonths:1,
				setDate:todayDate,
				onClose: function( selectedDate ) {
					$( "#endDate1" ).datepicker( "option", "minDate", selectedDate );
				}
			});
			$("#endDate1").datepicker({
				changeMonth:true,
				changeYear:true,
				numberOfMonths:1,
				onClose: function( selectedDate ) {
					$( "#beginDate1" ).datepicker( "option", "maxDate", selectedDate );
				}
			});
			/*--------------------------------------点击查询按钮-------------------------------------------------------*/
			$("#searchBtn").click(function(){
				dateType = $("#dateTypeCheck").val();
				beginDate = $("#beginDate").val();
				endDate = $("#endDate").val();
				taskUpload = $("#taskUpload").val();
				userNameCondition = $("#userNameCondition").val();
				chooseUserType(1);
			});
			$("#searchSalaryBtn").click(function(){
				dateType1 = $("#dateTypeCheck1").val();
				beginDate1 = $("#beginDate1").val();
				endDate1 = $("#endDate1").val();
				salaryLine = $("#salaryLine").val();
				payOffType = $("#payOffType").val();
				if(salaryLine.length == 0){
					salaryLine = 0;
				}
				userNameCondition1 = $("#userNameCondition1").val();
				//
				var noPay = $("#payOffType").val();
				if(noPay == 0){
					$("#payOffBtn").removeAttr("disabled");
				}else{
					$("#payOffBtn").attr("disabled","disabled");
				}	
				workerSalaryList(1);
			});
			/*---------------------查询等级列表-------------------------------*/
			$("#searchWorkerLvlBtn").click(function(){
				userNameConditionWorkerLvl = $("#userNameConditionWorkerLvl").val();
				userLvl = $("#userLvl").val();	
				workerLvlList(1);
			});
			/*--------------------------------------cx点击设置按钮-------------------------------------------------------*/
			$("#changeBtn").click(function(){
				$.ajax({
				type:'POST',
				url:'${contextPath}/security/getManager',
				dataType:'json',
				success:function(data){
					$("#downMaxCountInput").attr("value",data.manager.downMaxCount);
					$("#downCountInput").attr("value",data.manager.downCount);
					$("#fileSizeInput").attr("value",data.manager.fileSize);
				}
			});
				$(".modal-title").text("修改下载数值,文件尺寸,金额等");
				$("#changeManagerInform").modal('show');
			});
			/*--------------------------------------点击更改用户状态-------------------------------------------------------*/
			$("#changeUserStatusBtn").click(function(){
				var ust = $(".changeust:checked").val();
				var ustv = "不可用";
				if(ust == 1)ustv = "可用";
				$.ajax({
					type:'POST',
					data:{"userId":userId,"userStatus":ust},
					url:'${contextPath}/security/userStatus',
					dataType:'json',
					success:function(data){
						if(data.replay == 1){
							$("#updateStatus").addClass("text-success").text(data.message);
							$("#usta"+userId+"").text(ustv);
						}else{
							$("#updateStatus").addClass("text-danger").text(data.message);
						}
					}
				});
			});
			$("#changeUserStatusModal").on('hidden.bs.modal', function (e) {
				$("#updateStatus,.modal-title").empty();
				$(".radio-inline input").attr("checked"," ");
			});
			
		});
		/*--------------------------------------更改用户状态-------------------------------------------------------*/
		changeUserStatus = function(uId,userStatus,username){
			userId = uId;
			$(".modal-title").text("修改"+username+"状态");
			if(userStatus == 1){
				$(".radio-inline input[value=1]").attr("checked","checked");
			}else{
				$(".radio-inline input[value=0]").attr("checked","checked");
			}
			$("#changeUserStatusModal").modal('show');
			
		};
		/*--------------------------------------cx更改下载数值,文件尺寸,金额等--------------------------------------------*/
		$("#changeManagerInformBtn").click(function(){
			var downMaxCount = $("#downMaxCountInput").val();
			var downCount = $("#downCountInput").val();
			var fileSize = $("#fileSizeInput").val();
			$.ajax({
				type:'POST',
				data:{"downMaxCount":downMaxCount,"downCount":downCount,"fileSize":fileSize},
				url:'${contextPath}/security/updateCount',
				dataType:'json',
				success:function(data){
					if(data.replay == 1){
						alert("修改成功");
					}
				}
			});
		});
		/*--------------------------------------用户列表页-------------------------------------------------------*/
		chooseUserType = function(pageNum){
			$.ajax({
				type:'POST',
				data:{"userType":nowUserType,"page":pageNum,"userNameCondition":userNameCondition,"taskUpload":taskUpload,"dateType":dateType,"beginDate":beginDate,"endDate":endDate},
				url:'${contextPath}/security/manager',
				dataType:'json',
				success:function(data){
					$("#user-tbody").empty();
					if(data.list == ""){
						$("#user-tbody").empty();
						$("#user-tbody").append("<tr class='text-danger'><td colspan='6'>无内容</td></tr>");
					}else{
						pageTotal = data.totlePage;
						$.each(data.list,function(i,item){
							var status = "不可用";
							if(item.userStatus == "1")status = "可用";
							$("#user-tbody").append(
								"<tr>"+
									"<td>"+(i+1)+"</td>"+
									"<td><a target='_blank' href='${contextPath}/security/workerDetail?userId="+item.userId+"&username="+item.username+"'>"+item.username+"</a></td>"+
									"<td>"+item.realName+"</td>"+
									"<td>"+item.phone+"</td>"+
									"<td id = markTime"+i+">"+ "-" +"</td>"+
									"<td id = waitingMarkTime"+i+">"+ "-" +"</td>"+
									"<td id = downCount"+i+">"+ "-" +"</td>"+
									"<td id = finishCount"+i+">"+ "-" +"</td>"+
									"<td class='userStatus'><a id='usta"+item.userId+"' href='#' onClick='changeUserStatus("+item.userId+","+item.userStatus+",\""+item.username+"\")'>"+status+"</a></td>"+
								"</tr>"
							);
							var pageDom = $("#workerList .pagination");
							pageDom.empty();
							page.creatPageHTML(pageNum,pageTotal,pageDom,"chooseUserType");
							/*--------------------------------------跳转页-------------------------------------------------------*/
							$("#workerList .pageGoBtn").click(function(){
								var pageNum = 0;
								pageNum = $("#workerList .pageGoText").val();
								if(pageNum !=0&&0<pageNum&&pageNum<pageTotal+1){
									chooseUserType(pageNum);
								}
							});
							/*-------------------------------------异步获取数据--------------------------------------------*/			
							getMarkTime(item.userId,i,nowUserType,pageNum,userNameCondition,taskUpload,dateType,beginDate,endDate);			
						});
						getMarkTimeTotle(userNameCondition,taskUpload,dateType,beginDate,endDate);
					}
				}
			});
		};
		/*----------------------------------------------用户薪金页----------------------------------------------------------------*/
		workerSalaryList = function(pageNum){
			$.ajax({
				type:'POST',
				data:{"page":pageNum,"userNameCondition":userNameCondition1,"dateType":dateType1,"beginDate":beginDate1,"endDate":endDate1,"salaryLine":salaryLine,"payOffType":payOffType},
				url:'${contextPath}/security/workerSalary',
				dataType:'json',
				success:function(data){
					$("#workerSalary-tbody").empty();			
					if(data.list == ""){
						$("#workerSalary-tbody").empty();
						$("#workerSalarList .pageGoBtn").empty();
						$("#workerSalary-tbody").append("<tr class='text-danger'><td colspan='6'>无内容</td></tr>");
					}else{
						pageTotal = data.totlePage; 
						$.each(data.list,function(i,item){
							$("#workerSalary-tbody").append(
							"<tr>"+
								"<td>"+(i + 1)+"</td>"+
								"<td>"+item.realName+"</td>"+
								"<td>"+item.bankCard+"</td>"+
								"<td>"+item.taskMarkTimeMonth  +"</td>"+
								"<td>"+item.salary+"</td>"+
								"<td><input type='checkbox' class='checkbox1' name='checkbox1' value='"+item.workerId+"'/></td>"+
							"</tr>"
							);			
						});
						getSumSalary(userNameCondition1,dateType1,beginDate1,endDate1,salaryLine,payOffType);
						var pageDom = $("#workerSalarList .pagination");
							pageDom.empty();
							page.creatPageHTML(pageNum,pageTotal,pageDom,"workerSalaryList");
							/*--------------------------------------跳转页-------------------------------------------------------*/
							$("#workerSalarList .pageGoBtn").click(function(){
								var pageNum = 0;
								pageNum = $("#workerSalarList .pageGoText").val();
								if(pageNum !=0&&0<pageNum&&pageNum<pageTotal+1){
									workerSalaryList(pageNum);
								}
							});					
					}
				}
			});
		};
		/*----------------------------------------------用户等级页----------------------------------------------------------------*/
		workerLvlList = function(pageNum){
			userLvl = $("#userLvl").val();
			$.ajax({
				type:'POST',
				data:{"page":pageNum,"userNameCondition":userNameConditionWorkerLvl,"userLvl":userLvl},
				url:'${contextPath}/security/workerLvl',
				dataType:'json',
				success:function(data){
					$("#workerLvl-tbody").empty();			
					if(data.list == ""){
						$("#workerLvl-tbody").empty();
						$("#workerLvlList .pageGoBtn").empty();
						$("#workerLvl-tbody").append("<tr class='text-danger'><td colspan='6'>无内容</td></tr>");
					}else{
						pageTotal = data.totlePage; 
						$.each(data.list,function(i,item){
							$("#workerLvl-tbody").append(
							"<tr>"+
								"<td>"+(i + 1)+"</td>"+
								"<td>"+item.wrn+"</td>"+
								"<td>"+item.uln+"</td>"+
								"<td><input type='checkbox' class='checkbox1Lvl' name='checkbox1Lvl' value='"+item.workerId+"'/></td>"+
							"</tr>"
							);			
						});
						//getSumSalary(userNameCondition1,dateType1,beginDate1,endDate1,salaryLine,payOffType);
						var pageDom = $("#workerLvlList .pagination");
							pageDom.empty();
							page.creatPageHTML(pageNum,pageTotal,pageDom,"workerLvlList");
							/*--------------------------------------跳转页-------------------------------------------------------*/
							$("#workerLvlList .pageGoBtn").click(function(){
								var pageNum = 0;
								pageNum = $("#workerLvlList .pageGoText").val();
								if(pageNum !=0&&0<pageNum&&pageNum<pageTotal+1){
									workerLvlList(pageNum);
								}
							});					
					}
				}
			});
		};
		/*--------------------------------------全选-----------------------------------------------------------------*/
		$("#choose").click(function(){
			var choose = $("#choose-p").text();
			if(choose == "全选"){
				$(".checkbox1").prop("checked",true);
				$("#choose-p").text("取消");
			}else{
				$(".checkbox1").prop("checked",false);
				$("#choose-p").text("全选");
			}
		});
		/*****************等级**********************/
		$("#chooseLvl").click(function(){
			var choose = $("#choose-lvl").text();
			if(choose == "全选"){
				$(".checkbox1Lvl").prop("checked",true);
				$("#choose-lvl").text("取消");
			}else{
				$(".checkbox1Lvl").prop("checked",false);
				$("#choose-lvl").text("全选");
			}
		});
		/*---------------------------------------等级设置---------------------------------------------------------------*/
		$("#updateWorkerLvlBtn").click(function(){
			userLvl = $("#userLvl").val();
			var userLavlName = $("#userLvl option[value="+userLvl+"]").text();	
				if(userLvl > 0){
					var conWin = confirm("确定要设置到"+userLavlName+"吗?");
					if(conWin){
						var checkList = $(".checkbox1Lvl");
						var l = checkList.length;
						var str="";
						for(var i=0;i<l;i++){
							if (checkList[i].checked == true) {
								var workerId = checkList[i].value;
								str =workerId +"/"+str;
			        		}
						}		
						if(str.length == 0){
							alert("请选择要设置用户!!!");
						}else{
							updateWorkerLvl(str);
						}
					}
				}else{
					alert("请选择要设置的等级!!!");
				}
				
		});
		updateWorkerLvl= function(str){
			$.ajax({
				type:'POST',
				data:{"workerIds":str,"userLvl":userLvl},
				url:'${contextPath}/security/updateWorkerLvl',
				dataType:'json',
				success:function(data){
					var replay = data.replay;
					if(replay>0){
						alert("等级设置完成!");
					}else{
						alert("等级设置失败!");
					}
					workerLvlList(1);	
				}	
			});
		};
		/*---------------------------------------结算---------------------------------------------------------------*/
		$("#payOffBtn").click(function(){
			payOffType = $("#payOffType").val();
			if(payOffType == 0){
				var conWin = confirm("确定要执行结算吗？");
				if(conWin){
					var checkList = $(".checkbox1");
					var l = checkList.length;
					var str="";
					for(var i=0;i<l;i++){
						if (checkList[i].checked == true) {
							var workerId = checkList[i].value;
							str = workerId +"/"+str;
		        		}
					}
					
					dateType1 = $("#dateTypeCheck1").val();
					beginDate1 = $("#beginDate1").val();
					endDate1 = $("#endDate1").val();				
					payOff(str);	
				}
			}else{
				alert("现在显示的不是未结数据,不能进行结算!!");
			}	
		});
		/*--------------------------------------结算工资-------------------------------------------*/
		payOff = function(str){
			$.ajax({
				type:'POST',
				data:{"workerIds":str,"dateType":dateType1,"beginDate":beginDate1,"endDate":endDate1},
				url:'${contextPath}/security/payOff',
				dataType:'json',
				success:function(data){
					var replay = data.replay;
					if(replay>0){
						alert("结算工资完成!");
					}else{
						alert("结算工资出错!");
					}
					workerSalaryList(1);	
				}	
			});
		};
		/*--------------------------------------获得语音标注总和-------------------------------------------*/
		getMarkTimeTotle = function(userNameCondition,taskUpload,dateType,beginDate,endDate){
			$.ajax({
				type:'POST',
				data:{"userNameCondition":userNameCondition,"taskUpload":taskUpload,"dateType":dateType,"beginDate":beginDate,"endDate":endDate},
				url:'${contextPath}/security/getMarkTimeTotle',
				dataType:'json',
				success:function(data){
					var taskMarkTimeMonthTotle = data.taskMarkTimeMonthTotle;
					var aduitingMarkTimeMonthTotle = data.aduitingMarkTimeMonthTotle;			
					$("#aduitingMarkTimeMonthTotle").text("待审标注："+aduitingMarkTimeMonthTotle);
					$("#taskMarkTimeMonthTotle").text("有效标注："+taskMarkTimeMonthTotle);
				}
			});
		};
		getSumSalary = function(userNameCondition,dateType,beginDate,endDate,salaryLine,payOffType){
			$.ajax({
				type:'POST',
				data:{"userNameCondition":userNameCondition,"dateType":dateType,"beginDate":beginDate,"endDate":endDate,"salaryLine":salaryLine,"payOffType":payOffType},
				url:'${contextPath}/security/getSumSalary',
				dataType:'json',
				success:function(data){
					var sumSalary = data.sumSalary;			
					$("#taskMarkTimeMonthTotle1").text("有效标注："+(sumSalary/3600).toFixed(2)+" 小时");
					$("#aduitingMarkTimeMonthTotle1").text("总金额："+(sumSalary*data.salary/3600).toFixed(2)+" 元");
				}
			});
		};
		/*
		数据显示顺序
		downCount+"/"+unUploadCount+"/"+waitingCount
		finishCount+"/"+giveUpCount+"/"+oldCount
		*/
		getMarkTime = function(userId,i,nowUserType,pageNum,userNameCondition,taskUpload,dateType,beginDate,endDate){
			$.ajax({
				type:'POST',
				data:{"userId":userId,"userType":nowUserType,"page":pageNum,"userNameCondition":userNameCondition,"taskUpload":taskUpload,"dateType":dateType,"beginDate":beginDate,"endDate":endDate},
				url:'${contextPath}/security/getMarkTime',
				dataType:'json',
				success:function(data){
					$("#markTime"+i).text(data.markTime);
					$("#waitingMarkTime"+i).text(data.waitMarkTime);
					$("#downCount"+i).text(data.downCount+"/"+data.unUpLoadCount+"/"+data.waitingEffectiveCount);
					$("#finishCount"+i).text(data.finishCount+"/"+data.giveUpCount+"/"+data.oldCount);
				}
			});
		};
	</script>
</body>
</html>
