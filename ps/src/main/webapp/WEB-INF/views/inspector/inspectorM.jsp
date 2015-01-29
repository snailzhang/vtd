<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>审核管理员页面</title>
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
	<!-------------------------------- 选项卡区域 -------------------------------------------------->
	<div class="container">
		<ul class="nav nav-tabs" role="tablist">
			<li class="active"><a href="#assignList" role="tab" data-toggle="tab">分配列表</a></li>
			<li><a href="#inspectList" role="tab" data-toggle="tab">审核列表</a></li>
		</ul>
		<div class="tab-content">
	<!-- ****************************************分配列表******************************************************* -->
			<div class="tab-pane active" id="assignList">
				<div class="panel panel-default">
					<div class="panel-body">
						<form class="form-inline" role="form">
							<div class="form-group">
								<div class="input-group">
									<div class="input-group-addon"> 用户名：</div>
									<input class="form-control" onkeydown="if(event.keyCode==13){return false;}" id="userNameCondition1" type="text" placeholder="查询用户">
								</div>
							</div>
							<div class="form-group">
								<p class="form-control-static">标注时间段：</p>
							</div>
							<div class="form-group">
								<select class="form-control" id="timeMark1">
									<option value="0">大于十分钟</option>
									<option value="1">小于十分钟</option>
								</select>
							</div>
							<div class="form-group" id="">
								<p class="form-control-static">审核人：</p>
							</div>
							<div class="form-group" id="inspectId-select" >
								<select class="form-control" name="inspectId" id="inspectId"></select>
							</div>
							<button type="button" id="search1Btn" class="btn btn-default">查询</button>
							<button type="button" id="assignBtn" class="btn btn-default">分配</button>
						</form>
					</div>
					<table class="table table-striped table-bordered">
						<thead>
							<tr>
								<th width='10%'>序号</th>
								<th width='15%'>别名</th>
								<th width='15%'>姓名</th>
								<th width='20%'>个数</th>	
								<th width='10%'><a href="#" id="choose"><span id="choose-p">全选</span></a></th>					
							</tr>
						</thead>
						<tbody id="assign-tbody"></tbody>
					</table>
					<ul class="pagination" id="assign-pagination"></ul>
				</div>
			</div>
	<!-- ***********************************************审核列表*************************************************** -->
			<div class="tab-pane" id="inspectList">
				<div class="panel panel-default">
					<div class="panel-body">
						<form class="form-inline" role="form">
							<div class="form-group">
								<div class="input-group">
									<div class="input-group-addon"> 用户名：</div>
									<input class="form-control" onkeydown="if(event.keyCode==13){return false;}" id="userNameCondition" type="text" placeholder="查询用户">
								</div>
							</div>
							<div class="form-group">
								<p class="form-control-static">标注时间段：</p>
							</div>
							<div class="form-group">
								<select class="form-control" id="timeMark">
									<option value="0">大于十分钟</option>
									<option value="1">小于十分钟</option>
								</select>
							</div>
							<button type="button" id="searchBtn" class="btn btn-default">查询</button>
							
						</form>
					</div>
					<table class="table table-striped table-bordered">
						<thead>
							<tr>
								<th width='10%'>序号</th>
								<th width='15%'>别名</th>
								<th width='15%'>姓名</th>
								<th width='20%'>个数</th>					
							</tr>
						</thead>
						<tbody id="inspect-tbody"></tbody>
					</table>
					<ul class="pagination"></ul>
				</div>
			</div>
	<!-- ****************************************************************************************************** -->
		</div>
	</div>
	<script type="text/javascript">
		var nowPage = 0;
		var timeMark = 0;
		var userNameCondition = "";
		var pageTotal = 0;
		$(document).ready(function(){
			assignPage(1);
			loadUserList(1);
			/*--------------------------------------点击查询按钮-------------------------------------------------------*/
			$("#searchBtn").click(function(){
				timeMark = $("#timeMark").val();
				userNameCondition = $("#userNameCondition").val();
				loadUserList(1);
			});
			$("#search1Btn").click(function(){
				timeMark = $("#timeMark1").val();
				userNameCondition = $("#userNameCondition1").val();
				assignPage(1);
			});	
		});
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
		/*----------------------------------------分配按钮---------------------------------------------------------*/	
		$("#assignBtn").click(function(){
			var checkList = $(".checkbox1");
			var l = checkList.length;
			var str="";
			for(var i=0;i<l;i++){
				if (checkList[i].checked == true) {
					var workerId = checkList[i].value;
					str = workerId +"/"+str;
        		}
			}
			var inspectorId = $("#inspectId").val();
			$.ajax({
				type:'POST',
				data:{"workerIds":str,"inspectorId":inspectorId},
				url:'${contextPath}/security/assignT',
				dataType:'json',
				success:function(data){
				if(data.replay == 1){
					alert("分配完成!!");
					assignPage(1);
				}
					
				}	
			});	
		});	//
		/*--------------------------------------分配页面-------------------------------------------------------*/
		assignPage = function(pageNum){
			$.ajax({
				type:'POST',
				data:{"inspectorId":0,"timeMark":timeMark,"page":pageNum,"userName":userNameCondition},
				url:'${contextPath}/security/inspectorManager',
				dataType:'json',
				success:function(data){
					$("#inspectId").empty();
					if(data.inspectorIds == ""){
						$("#inspectId").append("<option>无</option>");
					}else{
						$.each(data.inspectorIds,function(i,item){
							$("#inspectId").append("<option value="+item.inspectorId+">"+item.inspectorName+"</option>");
						});
					}
					$("#assign-tbody").empty();
					if(data.list == ""){
						$("#assign-tbody").empty();
						$("#assign-tbody").append("<tr class='text-danger'><td colspan='5'>无内容</td></tr>");
					}else{
						pageTotal = data.totlePage;
						$.each(data.list,function(i,item){
							//var status = "不可用";
							//if(item.userStatus == "1")status = "可用";
							$("#assign-tbody").append(
								"<tr>"+
									"<td>"+(i+1)+"</td>"+
									"<td>"+item.user_name+"</td>"+
									"<td>"+item.workerRealName+"</td>"+
									"<td>"+item.c+"</td>"+
									"<td><input type='checkbox' class='checkbox1' name='checkbox1' value='"+item.worker_id+"'/></td>"+
								"</tr>"
							);
							var pageDom = $(".pagination");
							pageDom.empty();
							page.creatPageHTML(pageNum,pageTotal,pageDom,"assignPage");
							/*--------------------------------------跳转页-------------------------------------------------------*/
							$(".pageGoBtn").click(function(){
								var pageNum = 0;
								pageNum = $(".pageGoText").val();
								if(pageNum !=0&&0<pageNum&&pageNum<pageTotal+1){
									loadUserList(pageNum);
								}
							});
						});
					}
				}
			});
		};
		/*--------------------------------------审核页面-------------------------------------------------------*/
		loadUserList = function(pageNum){
			$.ajax({
				type:'POST',
				data:{"timeMark":timeMark,"page":pageNum,"userName":userNameCondition},
				url:'${contextPath}/security/inspectorManager',
				dataType:'json',
				success:function(data){
					$("#inspect-tbody").empty();
					if(data.list == ""){
						$("#inspect-tbody").empty();
						$("#inspect-tbody").append("<tr class='text-danger'><td colspan='5'>无内容</td></tr>");
					}else{
						pageTotal = data.totlePage;
						$.each(data.list,function(i,item){
							var status = "不可用";
							if(item.userStatus == "1")status = "可用";
							$("#inspect-tbody").append(
								"<tr>"+
									"<td>"+(i+1)+"</td>"+
									"<td><a target='_blank' href='${contextPath}/security/inspectorList?workerId="+item.worker_id+"'>"+item.user_name+"</a></td>"+
									"<td>"+item.workerRealName+"</td>"+
									"<td>"+item.c+"</td>"+
								"</tr>"
							);
							var pageDom = $(".pagination");
							pageDom.empty();
							page.creatPageHTML(pageNum,pageTotal,pageDom,"loadUserList");
							/*--------------------------------------跳转页-------------------------------------------------------*/
							$(".pageGoBtn").click(function(){
								var pageNum = 0;
								pageNum = $(".pageGoText").val();
								if(pageNum !=0&&0<pageNum&&pageNum<pageTotal+1){
									loadUserList(pageNum);
								}
							});
						});
					}
				}
			});
		};
	</script>
</body>
</html>
