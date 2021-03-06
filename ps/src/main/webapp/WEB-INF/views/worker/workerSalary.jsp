<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>工资页面</title>
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
<script type="text/javascript" src="${contextPath}/js/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.fileupload.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${contextPath}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/common.js"></script>
</head>
<body>
	<jsp:include page="../headWorker.jsp" />

	
		<div class="container">
			<div id="uploadFalTable" class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">工资列表</h3>
				</div>
				<!--  <div class="panel-body">
					<form class="form-inline" role="form">
						<div class="form-group">
							<div class="input-group">
								<div class="input-group-addon">任务包名称：</div>
								<input class="form-control" id="downPackName" type="text" placeholder="查询任务包"  onkeydown="if(event.keyCode==13){return false;}">
							</div>
						</div>
						<button type="button" id="searchBtn" class="btn btn-default">查询</button>
					</form>
				</div>-->
				<table class="table table-striped table-bordered">
					<thead>
						<tr>
							<th>序号</th>
							<th>日期</th>
							<th>有效时间(秒)</th>
							<th>薪金(元)</th>
							<th>已结(元)</th>
						</tr>
					</thead>
					<tbody id="packHistoryTable" role="tablist" aria-multiselectable="true"></tbody>
				</table>
				<ul class="pagination"></ul>
			</div>
			
		</div>

	<script type="text/javascript">
		var pageTotal = 0;
		//var downPackName = "";
		$(document).ready(function(){		
			loadWorkerSalary();
			
			/*******************************刷新按钮**************************************************/
			$("#refreshPage").click(function(){
				window.location.reload();
			});
			/*$("#searchBtn").click(function(){
				downPackName = $("#downPackName").val();
				loadPackListHistory(1);
			});*/
			/*******************************显示任务包详细**************************************************/
			$("#packHistoryTable").on('show.bs.collapse', function () {
				$(this).children(".in").removeClass("in");
			});
			$("#packHistoryTable").on('shown.bs.collapse', function () {
				var collapseTR = $(this).children(".in");
				var id = collapseTR.attr("id");
				var thisTD = collapseTR.children("td");
				var dpn = collapseTR.attr("packName");
				//var isf = collapseTR.attr("isfinish");
				$.ajax({
					url:'${contextPath}/security/workerHistoryTask',
					data:{"downPackName":dpn},
					type:'POST',
					dataType:'json',
					success:function(data){
						if(data.list != ""){
							
							
							var addBodyTr = "";
							$.each(data.list,function(i,item){
								var downloadTD = "<td></td>";
								var tS = "";
								var taskEf = "";
								if(item.taskStatus == 0){
									tS = "未上传";
									downloadTD = "<td><a href='#' onClick='downloadTask("+item.taskId+",\""+item.taskName+"\")'>下载</a></td>";
								}else if(item.taskStatus == 1){
									tS = "已上传";
								}else if(item.taskStatus == 2){
									tS = "超时";
								}else if(item.taskStatus == 3){
									tS = "放弃";
								}
								addBodyTr +=
									"<tr class='success'>"+
										"<td>"+(i+1)+"</td>"+
										"<td>"+item.taskName+"</td>"+
										"<td>"+tS+"</td>"+
										"<td>"+item.taskLockTime+"小时</td>"+
										"<td>"+item.taskMarkTime+"</td>"+
										"<td>"+item.taskDownTime+"</td>"+
										"<td>"+item.taskUploadTime+"</td>"+
										"<td>"+item.taskEffective+"</td>"+
										downloadTD+
									"</tr>";
								
							});
							thisTD.empty();
							thisTD.append(
								"<table class='table table-condensed table-hover'>"+
									"<thead>"+
										"<tr class='success'>"+
											"<th>序号</th><th>任务名称</th><th>任务状态</th><th>回传时间</th><th>标注时间</th><th>下载时间</th><th>上传时间</th><th>审核状态</th><th>下载</th>"+
										"</tr>"+
									"</thead>"+
									"<tbody>"+addBodyTr+"</tbody>"+
								"</table>"
								
							);
						}
					}
				});
				//thisTD.text(dpn+isf);
			});
		});
		/*******************************加载任务包历史列表**************************************************/
		//loadPackListHistory
		loadWorkerSalary = function(){
			$.ajax({
				type:'POST',
				url:'${contextPath}/security/workerMonthSalary',
				data:{},
				dataType:'json',
				success:function(data){
					if(data.list == ""){
						$("tbody").empty();
						$("tbody").append("<tr class='text-danger'><td colspan='4'>无内容</td></tr>");
					}else{
						$("tbody").empty();
						var totle1 = 0;
						$.each(data.list,function(i,item){
							if(item.totle1 != null){
								totle1 = item.totle1;
							}
							
							$("tbody").append(
								"<tr>"+
									"<td>"+(i+1)+"</td>"+
									"<td>"+item.timer+"</td>"+
									"<td>"+item.totle+"</td>"+
									"<td>"+(item.totle*data.salary/3600).toFixed(0)+"</td>"+
									"<td>"+(totle1*data.salary/3600).toFixed(0)+"</td>"+
								"</tr>"
							);
						});
						/*pageTotal = data.totlePage;
						var pageDom = $(".pagination");
						pageDom.empty();
						page.creatPageHTML(pageNum,pageTotal,pageDom,"loadPackListHistory");*/
						/*--------------------------------------跳转页-------------------------------------------------------*/
						/*$(".pageGoBtn").click(function(){
							var pageNum = 0;
							pageNum = $(".pageGoText").val();
							if(pageNum !=0&&0<pageNum&&pageNum<pageTotal+1){
								loadPackListHistory(pageNum);
							}
						});*/
					}
					
				}
			});
		};
	</script>
</body>
</html>
