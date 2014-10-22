<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>工作者历史页面</title>
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
<script type="text/javascript" src="${contextPath}/js/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.fileupload.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/common.js"></script>
</head>
<body>
	<jsp:include page="../head.jsp" />
	
		<div class="container">
			<div id="uploadFalTable" class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">任务包历史列表</h3>
				</div>
				<table class="table table-striped table-bordered">
					<thead>
						<tr>
							<th>序号</th>
							<th>下载包名称</th>
							<th>下载时间</th>
							<th>包含任务数</th>
							<th>任务包状态</th>
							<th>下载任务包</th>
						</tr>
					</thead>
					<tbody id="packHistoryTable" role="tablist" aria-multiselectable="true"></tbody>
				</table>
				<ul class="pagination"></ul>
			</div>
			
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function(){
			
			loadPackListHistory(1);
			/*******************************刷新按钮**************************************************/
			$("#refreshPage").click(function(){
				window.location.reload();
			});
			/*******************************显示任务包详细**************************************************/
			$("#packHistoryTable").on('shown.bs.collapse', function () {
				var collapseTR = $(this).children(".in");
				var id = collapseTR.attr("id");
				var thisTD = collapseTR.children("td");
				var dpn = collapseTR.attr("packName");
				var isf = collapseTR.attr("isfinish");
				$.ajax({
					url:'${contextPath}/security/workerHistoryTask',
					data:{"downPackName":dpn},
					type:'POST',
					dataType:'json',
					success:function(data){
						if(data.list != ""){
							thisTD.empty();
							thisTD.append(
								"<table class='table table-striped table-bordered'>"+
									"<thead><th>序号</th><th>任务名称</th><th>任务状态</th><th>回传时间</th><th>标注时间</th><th>下载时间</th><th>上传时间</th><th>标注状态</th><th>下载</th></thead><tbody>"
								
							);
							$.each(data.list,function(i,item){
								var downloadTD = "<td></td>";
								if(isf == 0){
									downloadTD = "<td><a onClick='downloadTask(\""+item.taskName+"\")'>下载</a></td>";
								}
								thisTD.append(
									"<tr>"+
										"<td>"+(i+1)+"</td>"+
										"<td>"+item.taskName+"</td>"+
										"<td>"+item.taskStatu+"</td>"+
										"<td>"+item.taskLockTime+"</td>"+
										"<td>"+item.taskMarkTime+"</td>"+
										"<td>"+item.taskDownTime+"</td>"+
										"<td>"+item.taskUploadTime+"</td>"+
										"<td>"+item.taskEffective+"</td>"+
										downloadTD+
									"</tr>"
								);
							});
							thisTD.append("</tbody></table>");
						}
					}
				});
				//thisTD.text(dpn+isf);
			});
		});
		/*******************************加载任务包**************************************************/
		loadPackListHistory = function(pagNum){
			$.ajax({
				type:'POST',
				url:'${contextPath}/security/workerHistoryPack',
				data:{"page":pagNum},
				dataType:'json',
				success:function(data){
					if(data.list == ""){
						$("tbody").empty();
						$("tbody").append("<tr class='text-danger'><td colspan='6'>无内容</td></tr>");
					}else{
						$.each(data.list,function(i,item){
							var ps = "";
							var downloadTD = "<td></td>";
							var packDetailTR = "<tr class='packDetailTr collapse' packName='"+item.downPackName+"' isfinish='1' id='collapse"+(i+1)+"'><td colspan='6'></td></tr>";
							if(item.packStatu == 0){
								ps = "未完成";
								var pName = "";
								 pName= item.downPackName;
								//pName = pName.substring(0,item.downPackName.indexOf(".zip"));
								downloadTD = "<td><a class='downloadPack' onClick='downloadPack(\""+pName+"\")'>下载</a></td>";
								packDetailTR = "<tr class='packDetailTr collapse' packName='"+item.downPackName+"' isfinish='0' id='collapse"+(i+1)+"'><td colspan='6'></td></tr>";
							}else if(item.packStatu == 1){
								ps = "已完成";
							}else if(item.packStatu == 2){
								ps = "已超时";
							}
							$("tbody").append(
								"<tr>"+
									"<td>"+(i+1)+"</td>"+
									"<td><a data-toggle='collapse' data-parent='#packHistoryTable' href='#collapse"+(i+1)+"' aria-expanded='true' aria-controls='collapse"+(i+1)+"' class='showPackDetail'>"+item.downPackName+"</a><span class='badge'>"+item.taskCount+"</span></td>"+
									"<td>"+item.downTime+"</td>"+
									"<td>"+item.taskCount+"</td>"+
									"<td>"+ps+"</td>"+
									downloadTD+
								"</tr>"+packDetailTR
							);
						});
						var pageTotal = data.totlePage;
						for(var i=1;i<pageTotal+1;i++){
							if(i==pageNum){
								$(".pagination").append(
									"<li class='active'><a onClick='loadPackListHistory("+i+")'>"+
									i+
									"</a></li>"
								);
							}else{
								$(".pagination").append(
									"<li><a onClick='loadPackListHistory("+i+")'>"+
									i+
									"</a></li>"
								);
							}
						}
					}
					
				}
			});
		};
		/*******************************下载任务包**************************************************/
		downloadPack = function(downPackName){
			$.ajax({
				type:'GET',
				url:'${contextPath}/security/downOncePack',
				data:{"downPackName":downPackName},
				dataType:'json',
				success:function(data){
					if(data.wrongPath != ""){
						window.open(data.wrongPath);
					}
				}
			});
		};
		/*******************************下载任务**************************************************/
		downloadTask = function(taskName){
			$.ajax({
				type:'GET',
				url:'${contextPath}/security/downOneTask',
				data:{"taskName":taskName},
				dataType:'json',
				success:function(data){
					if(data.wrongPath != ""){
						window.open(data.wrongPath);
					}
				}
			});
		};
	</script>
</body>
</html>
