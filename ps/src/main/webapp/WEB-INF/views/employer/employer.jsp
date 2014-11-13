<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>发包商页面</title>
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
</head>
<body>
	<jsp:include page="../head.jsp" />
	<div class="container">
		<div class="well well-sm">
			<span>FTP地址：</span>
			<span>${ftpUrl}</span>
		</div>
	</div>
	
	<!-------------------------------- 选项卡区域 -------------------------------------------------->
	<div class="container">
		<ul class="nav nav-tabs" role="tablist">
			<li class="active"><a href="#packUncomplete" role="tab" data-toggle="tab">未完成任务包列表</a></li>
			<li><a href="#packComplete" role="tab" data-toggle="tab">已完成任务包列表</a></li>
			<li><a href="#packUnzip" role="tab" data-toggle="tab">未解压任务包列表</a></li>
		</ul>
		<div class="tab-content">
		<!-- ****************************************未完成任务包列表******************************************************* -->
			<div class="tab-pane active" id="packUncomplete">
				<div class="panel panel-default">
					<div class="panel-body">
						<form class="form-inline" role="form">
							<div class="form-group">
								<div class="input-group">
									<div class="input-group-addon">任务包名称：</div>
									<input class="form-control" id="unpackNameCondition" type="text" placeholder="查询未完成任务包" onkeydown="if(event.keyCode==13){return false;}">
								</div>
							</div>
							<button type="button" id="un_searchBtn" class="btn btn-default">查询</button>
						</form>
					</div>
					<table class="table table-striped table-bordered">
						<thead>
							<tr>
								<th>序号</th>
								<th>名称</th>
								<th>等级</th>
								<th>总数</th>
								<th>剩余数</th>
								<th>完成数</th>
								<th>完成比例</th>
								<th>下载次数</th>
								<th>回传时间</th>
								<th>创建时间</th>
								<th>标注时间</th>
								<th>下载</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
					<ul class="pagination"></ul>
				</div>
			</div>
			<!-- ****************************************已完成任务包列表******************************************************* -->
			<div class="tab-pane" id="packComplete">
				<div class="panel panel-default">
					<div class="panel-body">
						<form class="form-inline" role="form">
							<div class="form-group">
								<div class="input-group">
									<div class="input-group-addon">任务包名称：</div>
									<input class="form-control" id="packNameCondition" type="text" placeholder="查询已完成任务包" onkeydown="if(event.keyCode==13){return false;}">
								</div>
							</div>
							<button type="button" id="searchBtn" class="btn btn-default">查询</button>
						</form>
					</div>
					<table class="table table-striped table-bordered">
						<thead>
							<tr>
								<th>序号</th>
								<th>任务包名称</th>
								<th>任务总数</th>
								<th>下载次数</th>
								<th>回传时间</th>
								<th>创建时间</th>
								<th>标注时间</th>
								<th>下载任务包</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
					<ul class="pagination"></ul>
				</div>
			</div>
			<!-- ****************************************未解压任务包列表******************************************************* -->
			<div class="tab-pane" id="packUnzip">
				<div class="panel panel-default">
					<div class="panel-heading">
						<form role="form" class="form-horizontal">
							<div class="form-group" id="">
								<label for="taskLvl" class="col-sm-2 control-label">任务等级：</label>
								<div class="col-sm-10">
									<select class="form-control" name="taskLvl" id="taskLvl">
										<option value="1">1</option>
										<option value="2">2</option>
										<option selected="selected" value="3">3</option>
										<option value="4">4</option>
										<option value="5">5</option>
									</select>
								</div>
							</div>
							<div class="form-group" id="lockTime">
								<label for="packLockTime" class="col-sm-2 control-label">任务时间：</label>
								<div class="col-sm-10">
									<div class="input-group">
										<input type="text" class="form-control" name="packLockTime" id="packLockTime" placeholder="添加任务时间">
										<span class="input-group-addon">小时</span>
										
									</div>
									<span class="help-block"></span>
								</div>
							</div>
						</form>
					</div>
					<div class="panel-body">
						<table class="table table-striped table-bordered">
							<thead>
								<tr>
									<th>序号</th>
									<th>任务包名称</th>
									<th>任务包状态</th>
								</tr>
							</thead>
							<tbody></tbody>
						</table>
					</div>
				</div>
				
			</div>
		</div>
		
	</div>
	<!-------------------------------- 弹出窗口 任务包详细-------------------------------------------------->
	<div id="packDetailModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
					<h4 class="modal-title">任务包详细内容</h4>
				</div>
				<div class="modal-body">
					<div class="panel panel-default">
						<div class="panel-body">
							<form class="form-inline" role="form">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon">任务名称：</div>
										<input class="form-control" id="taskSearch" type="text" placeholder="查询任务" onkeydown="if(event.keyCode==13){return false;}">
									</div>
								</div>
								<button type="button" id="tasksearchBtn" class="btn btn-default">查询</button>
							</form>
						</div>
						<table class="table table-striped table-bordered">
							<thead>
								<tr>
									<th>序号</th>
									<th>任务名称</th>
									<th>任务状态</th>
									<th>检测结果</th>
								</tr>
							</thead>
							<tbody id="packDetailTBody"></tbody>
						</table>
						<ul class="pagination"></ul>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	<!-------------------------------- 进度条-------------------------------------------------->
	<!--<div id="packUploadProgressModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">任务包上传中</h4>
				</div>
				<div class="modal-body">
					<div class="progress">
						<div  id="uploadProgress" class="progress-bar progress-bar-striped active"  role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%">
							0%
						</div>
						<div id="unzipProgress" class="progress-bar progress-bar-success progress-bar-striped active"  role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="0" style="width: 0%">
							0
						</div>
					</div>
				</div>
				
			</div> /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	<!-------------------------------- 修改任务等级-------------------------------------------------->
	<div id="changeTaskLevelModal" class="modal fade">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
						<h4 class="modal-title">修改任务等级</h4>
					</div>
					<div class="modal-body">
						<span id="updateStatus"></span>
						<form class="form-horizontal" role="form">
							<div class="form-group" id="">
								<label for="ctLvl" class="col-sm-3 control-label">任务等级：</label>
								<div class="col-sm-9">
									<select class="form-control" name="ctLvl" id="ctLvl">
										<option value="1">1</option>
										<option value="2">2</option>
										<option value="3">3</option>
										<option value="4">4</option>
										<option value="5">5</option>
									</select>
								</div>
							</div>
					   </form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button type="button" id="changeTaskLvlBtn" class="btn btn-primary">修改</button>
					</div>
				</div><!-- /.modal-content -->
			</div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
	<script type="text/javascript">
		var unpackNameCondition = "";
		var packNameCondition = "";
		var taskNameCondition = "";
		var searchPackId = 0;
		var pucPageTotle = 0;
		var pcPageTotle = 0;
		var pdPageTotle = 0;
		if('${match}' == '1'){
			alert("文件已存在");
		}
		$(document).ready(function(){
			loadUnCompletePackList(1);
			loadCompletePackList(1);
			loadUnzipPackList();
			/*---------------------------------------查询-------------------------------------------------------------------*/
			$("#un_searchBtn").click(function(){
				unpackNameConditionunpackNameCondition = $("#unpackNameCondition").val();
				loadUnCompletePackList(1);
			});
			$("#searchBtn").click(function(){
				packNameCondition = $("#packNameCondition").val();
				loadCompletePackList(1);
			});
			$("#tasksearchBtn").click(function(){
				var tnc = $("#taskSearch").val();
				taskNameCondition = tnc;
				loadPackDetailList(1);
			});
		});
		/*---------------------------------------请求未完成任务包列表-------------------------------------------------------------------*/
		loadUnCompletePackList = function(pageNum){
			$.ajax({
				type:'POST',
				url:'${contextPath}/security/employer',
				data:{"packStuts":0,"page":pageNum,"packNameCondition":unpackNameCondition},
				dataType:'json',
				success:function(data){
					if(data.list == ""){
						$("#packUncomplete tbody").empty();
						$("#packUncomplete tbody").append("<tr class='text-danger'><td colspan='11'>无内容</td></tr>");
					}else{
						$("#packUncomplete tbody").empty();
						$.each(data.list,function(i,item){
							if(item.unzip == 0){
								$("#packUncomplete tbody").append(
									"<tr>"+
										"<td>"+(i+1)+"</td>"+
										"<td>"+item.packName+"</td>"+
										"<td colspan='8'>任务包解压中.......</td>"+
									"</tr>"
								);
							}else{
								if(item.packLockTime == null){
									item.packLockTime = "";
								}
								var surplusTask = item.taskCount - item.finishTaskCount;//未完成任务数
								var finishTaskRatio = item.finishTaskCount/item.taskCount*100;//完成任务比例
								var downloadPack = "<td></td>";
								if(item.finishTaskCount != 0){
									downloadPack = "<td><a href='#' id='dp"+item.packId+"' class='downloadPack' onClick='downloadPackFn("+item.packId+")'>下载</a></td>";
								}
								$("#packUncomplete tbody").append(
									"<tr>"+
										"<td>"+(i+1)+"</td>"+
										"<td><a href='#' class='packId' onClick='showPackDetail("+item.packId+")'>"+item.packName+"</a></td>"+
										"<td><a href='javascript:changeTaskLvl("+item.packId+","+item.taskLvl+");'>"+item.taskLvl+"</a></td>"+
										"<td>"+item.taskCount+"</td>"+
										"<td>"+surplusTask+"</td>"+
										"<td>"+item.finishTaskCount+"</td>"+
										"<td>"+finishTaskRatio+"%</td>"+
										"<td>"+item.downCount+"</td>"+
										"<td>"+item.packLockTime+"小时</td>"+
										"<td>"+item.createTime+"</td>"+
										"<td>"+item.taskMarkTime+"</td>"+
										downloadPack+
									"</tr>"
								);
							}
						});
						var pageDom = $("#packUncomplete .pagination");
						pageDom.empty();
						pucPageTotle = data.totlePage;
						page.creatPageHTML(pageNum,pucPageTotle,pageDom,"loadUnCompletePackList");
						$("#packUncomplete .pageGoBtn").click(function(){
							var pageNum = 0;
							pageNum = $("#packUncomplete .pageGoText").val();
							if(pageNum !=0&&0<pageNum&&pageNum<pucPageTotle+1){
								loadUnCompletePackList(pageNum);
							}
						});
					}
				}
			});
		};
		/*---------------------------------------请求已完成任务包列表-------------------------------------------------------------------*/
		loadCompletePackList = function(pageNum){
			$.ajax({
				type:'POST',
				url:'${contextPath}/security/employer',
				data:{"packStuts":1,"page":pageNum,"packNameCondition":packNameCondition},
				dataType:'json',
				success:function(data){
					if(data.list == ""){
						$("#packComplete tbody").empty();
						$("#packComplete tbody").append("<tr class='text-danger'><td colspan='8'>无内容</td></tr>");
					}else{
						$("#packComplete tbody").empty();
						$.each(data.list,function(i,item){
							if(item.packLockTime == null){
								item.packLockTime = "";
							}
							var downloadPack = "<td><a href='#' id='dp"+item.packId+"' class='downloadPack' onClick='downloadPackFn("+item.packId+")'>下载</a></td>";
							$("#packComplete tbody").append(
								"<tr>"+
									"<td>"+(i+1)+"</td>"+
									"<td><a href='#' class='packId' onClick='showPackDetail("+item.packId+")'>"+item.packName+"</a></td>"+
									"<td>"+item.taskCount+"</td>"+
									"<td>"+item.downCount+"</td>"+
									"<td>"+item.packLockTime+"小时</td>"+
									"<td>"+item.createTime+"</td>"+
									"<td>"+item.taskMarkTime+"</td>"+
									downloadPack+
								"</tr>"
							);
						});
						var pageDom = $("#packComplete .pagination");
						pageDom.empty();
						pcPageTotle = data.totlePage;
						page.creatPageHTML(pageNum,pcPageTotle,pageDom,"loadCompletePackList");
						$("#packComplete .pageGoBtn").click(function(){
							var pageNum = 0;
							pageNum = $("#packComplete .pageGoText").val();
							if(pageNum !=0&&0<pageNum&&pageNum<pcPageTotle+1){
								loadCompletePackList(pageNum);
							}
						});
					}
				}
			});
		};
		/*---------------------------------------请求未解压任务包列表-------------------------------------------------------------------*/
		loadUnzipPackList = function(){
			$.ajax({
				type:'POST',
				url:'${contextPath}/security/unzipList',
				dataType:'json',
				success:function(data){
					if(data.list != ""){
						$("#packUnzip tbody").empty();
						$.each(data.list,function(i,item){
							$("#packUnzip tbody").append(
								"<tr class='unziptr"+i+"'>"+
									"<td>"+(i+1)+"</td>"+
									"<td class='packName'>"+item+"</td>"+
									"<td class='packZipStatus'><a href='javascript:zipOnePack(\""+item+"\",\"unziptr"+i+"\");'>解压</a></td>"+
								"</tr>"
							);
						})
					}else{
						$("#packUnzip tbody").append("<tr><td colspan='3'>无未解压任务包</td></tr>");
					}
				}
			});
		};
		zipOnePack = function(pName,trClass){
			
			var taskLvl = $("#taskLvl").val();
			var packLockTimeObj = $("#packLockTime");
			if(checkout.text.isempty(packLockTimeObj,"请填写任务时间！")) return;
			$("."+trClass+" .packZipStatus").text("任务包解压中");
			$.ajax({
				type:'POST',
				url:'${contextPath}/security/unzip',
				data:{"packName":pName,"taskLvl":taskLvl,"packLockTime":packLockTimeObj.val()},
				dataType:'json',
				success:function(data){
					$("."+trClass+" .packZipStatus").text(data.message);
				}
			});
		};
		/*---------------------------------------下载任务包---------------------------------------------------------------*/
		downloadPackFn = function(packId){
			$.ajax({
				type:'GET',
				url:'${contextPath}/security/downPack',
				data:{"packId":packId},
				dataType:'json',
				success:function(data){
					if(data.wrongPath != ""){
						window.open(data.wrongPath);
						window.location.reload();
					}
				}
			});
			$("#dp"+packId+"").text("正在打包");
		};
		/*---------------------------------------查看上传包详细内容---------------------------------------------------------------*/
		showPackDetail = function(packId){
			$("#packDetailTBody").empty();
			searchPackId = packId;
			loadPackDetailList(1);
			$("#packDetailModal").modal('show');
			$("#taskSearch").focus();
		};
		loadPackDetailList = function(pageNum){
			$.ajax({
				type:'POST',
				url:'${contextPath}/security/packDetail',
				data:{"packId":searchPackId,"page":pageNum,"taskStuts":2,"taskNameCondition":taskNameCondition},
				dataType:'json',
				success:function(data){
					if(data.list == ""){
						$("#packDetailTBody").empty();
						$("#packDetailTBody").append("<tr class='text-danger'><td colspan='4'>无内容</td></tr>");
					}else{
						$("#packDetailTBody").empty();
						$.each(data.list,function(i,item){
							var te = "";
							if(item.taskEffective != null)te = item.taskEffective;
							$("#packDetailTBody").append(
								"<tr>"+
									"<td>"+(i+1)+"</td>"+
									"<td>"+item.taskName+"</td>"+
									"<td>"+item.taskUploadTime+"</td>"+
									"<td>"+item.taskEffective+"</td>"+
								"</tr>"
							);
						});
						var pageDom = $("#packDetailModal .pagination");
						pageDom.empty();
						pdPageTotle = data.totlePage;
						page.creatPageHTML(pageNum,pdPageTotle,pageDom,"loadPackDetailList");
						$("#packDetailModal .pageGoBtn").bind("click",function(){
							var pageNum = 0;
							pageNum = $("#packDetailModal .pageGoText").val();
							if(pageNum !=0&&0<pageNum&&pageNum<pdPageTotle+1){
								loadPackDetailList(pageNum);
							}
						});
					}
					
					
				}
			});
		};
		/*---------------------------------------上传任务进度条显示---------------------------------------------------------------*/
		/*
		var oTimer = null;
		var zipTimer = null;
		showUploadProgress = function(){
			$("#unzipProgress").hide();
			$("#packUploadProgressModal").modal('show');
			oTimer = setInterval("getProgress()", 1000);
		};
		getProgress = function(){
			$.ajax({
				type:'POST',
				url:'${contextPath}/fileStatus/upfile/progress',
				dataType:'json',
				success:function(data){
					//alert(data.percent);
					var per = data.percent.split("%")[0];
					$("#uploadProgress").attr({"aria-valuenow":per,"style":"width:"+data.percent}).text(data.percent);
					if(per == '100'){
						window.clearInterval(oTimer);
						$("#uploadProgress").hide();
						showUnzipProgress();
					}
				}
			});
		};
		showUnzipProgress = function(){
			//$("#packUnzipProgressModal").modal('show');
			
			$("#unzipProgress").show();
			zipTimer = setInterval("unzipProgress()", 2000);
		};
		unzipProgress = function(){
			$.ajax({
				type:'get',
				url:'${contextPath}/security/fileCount',
				dataType:'json',
				success:function(data){
					var fileCount = data.fileCount;
					var finishCount = data.finishCount;
					var finishPer = finishCount/fileCount*100;
					
					$("#packUploadProgressModal .modal-title").text("任务包解压中");
					$("#unzipProgress").attr({"aria-valuenow":finishCount,"aria-valuemax":fileCount,"style":"width:"+finishPer+"%"}).text(finishCount+"个/"+fileCount+"个");
					
					if((finishCount!=0)&&(finishCount == fileCount)){
						window.clearInterval(zipTimer);
						window.location.reload();
					}
				}
			});
		};*/
		/*---------------------------------------修改任务等级---------------------------------------------------------------*/
		changeTaskLvl = function(packId,taskLvl){
			$("#ctLvl option[value="+taskLvl+"]").attr("selected","selected");
			$("#changeTaskLevelModal").modal('show');
			$("#changeTaskLvlBtn").click(function(){
				var lvl = ("#ctLvl").val();
				$.ajax({
					type:'post',
					url:'${contextPath}/security/updateTaskLvl',
					data:{"packId":packId,"taskLvl":lvl}
				});
			});
		};
	</script>
</body>
</html>
