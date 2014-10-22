<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>gg历史页面</title>
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
					<tbody id="packHistoryTable"></tbody>
				</table>
			</div>
			
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function(){
			/*******************************加载页面**************************************************/
			$.ajax({
				type:'POST',
				url:'${contextPath}/security/workerHistoryPack',
				dataType:'json',
				success:function(data){
					if(data.list == ""){
						$("tbody").empty();
						$("tbody").append("<tr class='text-danger'><td colspan='6'>无内容</td></tr>");
					}else{
						$.each(data.list,function(i,item){
							var ps = "";
							var downloadTD = "<td></td>";
							var downPackName = "<td><a class='showPackDetail' onClick='showPackDetail(1,"+item.downPackName.toString()+")'>"+item.downPackName+"</a><span class='badge'>"+item.taskCount+"</span></td>";
							if(item.packStatu == 0){
								ps = "未完成";
								downloadTD = "<td><a class='downloadPack' onClick='downloadPack("+item.downPackName.subString(0,item.downPackName.indexOf(".zip"))+")'>载</a></td>";
								downPackName = "<td><a class='showPackDetail' onClick='showPackDetail(0,"+item.downPackName+")'>"+item.downPackName+"</a><span class='badge'>"+item.taskCount+"</span></td>";
							}else if(item.packStatu == 1){
								ps = "已完成";
							}else if(item.packStatu == 2){
								ps = "已超时";
							}
							$("tbody").append(
								"<tr>"+
									"<td>"+(i+1)+"</td>"+
									downPackName+
									"<td>"+item.downTime+"</td>"+
									"<td>"+item.taskCount+"</td>"+
									"<td>"+ps+"</td>"+
									downloadTD+
								"</tr>"
							);
						});
					}
					
				}
			});
			
			/*******************************下载任务包**************************************************/
			downloadPack = function(downPackName){
				$.ajax({
					type:'GET',
					url:'${contextPath}/security/downOncePack',
					data:{"downPackName":downPackName},
					dataType:'json',
					success:function(data){
						if(data.url != ""){
							window.open(data.url);
						}
					}
				});
			};
			/*******************************显示任务包详细**************************************************/
			showPackDetail = function(packType,packName){
				if(packType)
			};
			/*******************************刷新按钮**************************************************/
			$("#refreshPage").click(function(){
				window.location.reload();
			});
		});
	</script>
</body>
</html>
