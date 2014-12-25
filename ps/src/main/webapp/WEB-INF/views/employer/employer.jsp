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
<link rel="stylesheet" type="text/css" href="${contextPath}/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/bootstrap-theme.min.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/umeditor.min.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/public.css">
<script type="text/javascript" src="${contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/umeditor.config.js"></script>
<script type="text/javascript" src="${contextPath}/js/umeditor.js"></script>
<script type="text/javascript" src="${contextPath}/lang/zh-cn/zh-cn.js"></script>
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
			<li><a href="#packAll" role="tab" data-toggle="tab">全部任务包列表</a></li>
			<li><a href="#packUnzip" role="tab" data-toggle="tab">未上传任务包列表</a></li>
			<li><a href="#editTaskRole" role="tab" data-toggle="tab">编辑标注说明</a></li>
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
								<th width='4%'>序号</th>
								<th width='17%'>名称</th>
								<th width='4%'>等级</th>
								<th width='5%'>总数</th>
								<th width='6%'>剩余数</th>
								<th width='6%'>无效数</th>
								<th width='6%'>完成数</th>
								<th width='7%'>完成比例</th>
								<th width='8%'>回传时间</th>
								<th width='15%'>创建时间</th>
								<th width='15%'>标注时间</th>
								<th width='7%'>下载</th>
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
								<th width='4%'>序号</th>
								<th width='17%'>名称</th>
								<th width='4%'>等级</th>
								<th width='5%'>总数</th>
								<th width='6%'>剩余数</th>
								<th width='6%'>无效数</th>
								<th width='6%'>完成数</th>
								<th width='7%'>完成比例</th>
								<th width='8%'>回传时间</th>
								<th width='15%'>创建时间</th>
								<th width='15%'>标注时间</th>
								<th width='7%'>下载</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
					<ul class="pagination"></ul>
				</div>
			</div>
			<!-- ****************************************全部任务包列表******************************************************* -->
			<div class="tab-pane" id="packAll">
				<div class="panel panel-default">
					<div class="panel-body">
						<form class="form-inline" role="form">
							<div class="form-group">
								<div class="input-group">
									<div class="input-group-addon">任务包名称：</div>
									<input class="form-control" id="allPackNameCondition" type="text" placeholder="查询任务包" onkeydown="if(event.keyCode==13){return false;}">
								</div>
							</div>
							<button type="button" id="searchAllBtn" class="btn btn-default">查询</button>
						</form>
					</div>
					<table class="table table-striped table-bordered">
						<thead>
							<tr>
								<th width='4%'>序号</th>
								<th width='17%'>名称</th>
								<th width='4%'>等级</th>
								<th width='5%'>总数</th>
								<th width='6%'>剩余数</th>
								<th width='6%'>无效数</th>
								<th width='6%'>完成数</th>
								<th width='7%'>完成比例</th>
								<th width='8%'>回传时间</th>
								<th width='15%'>创建时间</th>
								<th width='15%'>标注时间</th>
								<th width='7%'>下载</th>
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
						<form role="form" class="form-inline">
							<div class="form-group" id="">
								<p class="form-control-static">任务等级：</p>
							</div>
							<div class="form-group" id="">
								<select class="form-control" name="taskLvl" id="taskLvl">
									<option value="1">1</option>
									<option value="2">2</option>
									<option selected="selected" value="3">3</option>
									<option value="4">4</option>
									<option value="5">5</option>
								</select>
							</div>
							<div class="form-group" id="">
								<p class="form-control-static">统计时间：</p>
							</div>
							<div class="form-group" id="">
								<select class="form-control" name="markTimeMethodList" id="markTimeMethodList"></select>
							</div>
							<div class="form-group" id="">
								<p class="form-control-static">任务说明：</p>
							</div>
							<div class="form-group" id="">
								<select class="form-control" name="noteId" id="noteId"></select>
							</div>
							<div class="form-group" id="">
								<p class="form-control-static">任务时间：</p>
							</div>
							<div class="form-group" id="packLockTimeDiv">
								<div class="input-group">
									<input type="text" class="form-control" name="packLockTime" id="packLockTime" placeholder="添加任务时间" onkeydown="if(event.keyCode==13){return false;}">
									<span class="input-group-addon">小时</span>
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
			<!-- ****************************************编辑任务说明******************************************************* -->
			<div class="tab-pane" id="editTaskRole">
				<div class="panel panel-default">
					<div class="panel-heading">
						编辑任务说明
					</div>
					<div class="panel-body">
						<form role="form" class="form-horizontal">
							<div class="form-group" id="">
								<label for="noteTitle" class="col-sm-2 control-label">说明名称：</label>
								<div class="col-sm-6">
									<input type="text" class="form-control" name="noteTitle" id="noteTitle" placeholder="规则名称" required="required" autocomplete="off">
									<span class="help-block"></span>
								</div>
							</div>
							<script type="text/plain" id="myEditor" style="width:960px;height:240px;"></script>
						</form>
						<div style="margin:10px;"><button type="button" id="saveEdit" class="btn btn-primary btn-lg center-block">增加新说明</button></div>
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
						<span id="updateStatus" class="text-center"></span>
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
		<!--------------------------------提示上传内容-------------------------------------------------->
		<div id="taskUpCon" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
					<h4 class="modal-title">上传信息</h4>
				</div>
				<div class="modal-body">
					<dl class="dl-horizontal">
						<dt>任务等级：</dt>
						<dd id="taskLvlCon"></dd>
						<dt>统计时间：</dt>
						<dd id="markTimeMethodCon"></dd>
						<dt>任务说明：</dt>
						<dd id="noteIdCon"></dd>
						<dt>任务时间：</dt>
						<dd id="packLockTimeCon"></dd>
					</dl>
				</div>
				<div class="modal-footer">
					<button id="comfirmUpload" type="button" class="btn btn-primary">确定提交</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	<script type="text/javascript">
		var unpackNameCondition = "";
		var packNameCondition = "";
		var taskNameCondition = "";
		var allPackNameCondition= "";
		var searchPackId = 0;
		var pucPageTotle = 0;
		var pcPageTotle = 0;
		var pdPageTotle = 0;
		var changePackLvlPackId = 0;
		window.UMEDITOR_CONFIG.imageUrl = "${contextPath}/security/uploadImage";
		if('${match}' == '1'){
			alert("文件已存在");
		}
		
		var um = UM.getEditor('myEditor');
		$(document).ready(function(){
			loadUnCompletePackList(1);
			loadCompletePackList(1);
			loadAllPackList(1);
			loadUnzipPackList();
			/*---------------------------------------查询-------------------------------------------------------------------*/
			$("#un_searchBtn").click(function(){
				unpackNameCondition = $("#unpackNameCondition").val();
				loadUnCompletePackList(1);
			});
			$("#searchBtn").click(function(){
				packNameCondition = $("#packNameCondition").val();
				loadCompletePackList(1);
			});
			$("#searchAllBtn").click(function(){
				allPackNameCondition = $("#allPackNameCondition").val();
				loadAllPackList(1);
			});
			$("#tasksearchBtn").click(function(){
				var tnc = $("#taskSearch").val();
				taskNameCondition = tnc;
				loadPackDetailList(1);
			});
			$("#changeTaskLvlBtn").click(function(){
				var lvl = $("#ctLvl").val();
				$.ajax({
					type:'post',
					url:'${contextPath}/security/updateTaskLvl',
					data:{"packId":changePackLvlPackId,"taskLvl":lvl},
					success:function(data){
						if(data.replay == "1"){
							$("#updateStatus").removeClass("text-danger").addClass("text-success").text("修改成功！");
							window.location.reload();
						}
						else{
							$("#updateStatus").removeClass("text-success").addClass("text-danger").text("修改失败！");
						}
					}
				});
			});
			$("#saveEdit").click(function(){
				var hasContent = false;
				hasContent = UM.getEditor('myEditor').hasContents();
				if(hasContent){
					var con = UM.getEditor('myEditor').getContent();
					var noteTitle = $("#noteTitle");
					if(checkout.text.isempty(noteTitle,"名称不能为空！"))return;
					$.ajax({
						type:'POST',
						url:'${contextPath}/security/addVoiceNote',
						data:{"title":noteTitle.val(),"content":con,},
						dataType:'json',
						success:function(data){
							if($("#editTaskRole .panel-body .alert").length>0)$("#editTaskRole .panel-body .alert").remove();
							var $alertMsg = $("<div class='alert alert-dismissable'>"+data.message+"</div>");
							$alertMsg.append("<button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button>");
							if(data.replay == "1"){
								$alertMsg.addClass("alert-success");
							}else{
								$alertMsg.addClass("alert-success");
							}
							$("#editTaskRole .panel-body").prepend($alertMsg);
						}
					});
				}
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
						$("#packUncomplete tbody").append("<tr class='text-danger'><td colspan='12'>无内容</td></tr>");
					}else{
						$("#packUncomplete tbody").empty();
						$.each(data.list,function(i,item){
							if(item.unzip == 0){
								$("#packUncomplete tbody").append(
									"<tr>"+
										"<td>"+(i+1)+"</td>"+
										"<td>"+item.packName+"</td>"+
										"<td colspan='8'>任务包上传中.......</td>"+
									"</tr>"
								);
							}else{
								if(item.packLockTime == null){
									item.packLockTime = "";
								}
								var invalidCount = item.invalid+item.wavZero;//无效数
								var surplusTask = item.taskCount - item.finishTaskCount - invalidCount;//未完成任务数
								var finishTaskRatio = item.finishTaskCount/item.taskCount*100;//完成任务比例
								finishTaskRatio = finishTaskRatio.toFixed(2);
								var downloadPack = "<td></td>";
								if(item.finishTaskCount != 0){
									downloadPack = "<td><a href='#' id='dp"+item.packId+"' class='downloadPack' onClick='downloadPackFn("+item.packId+")'>下载 <span class='badge'>"+item.downCount+"</span></a></td>";
								}else{
									downloadPack = "<td><a href='#' id='dp"+item.packId+"' class='downloadPack' onClick='downloadPackFn(0)'>下载 <span class='badge'>0</span></a></td>";
								}
								
								$("#packUncomplete tbody").append(
									"<tr>"+
										"<td>"+(i+1)+"</td>"+
										"<td><a href='#' class='packId' onClick='showPackDetail("+item.packId+")'>"+item.packName+"</a></td>"+
										"<td><a href='javascript:changeTaskLvl("+item.packId+","+item.taskLvl+");'>"+item.taskLvl+"</a></td>"+
										"<td>"+item.taskCount+"</td>"+
										"<td>"+surplusTask+"</td>"+
										"<td>"+invalidCount+"</td>"+
										"<td>"+item.finishTaskCount+"</td>"+
										"<td>"+finishTaskRatio+"%</td>"+
										
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
						$("#packComplete tbody").append("<tr class='text-danger'><td colspan='12'>无内容</td></tr>");
					}else{
						$("#packComplete tbody").empty();
						$.each(data.list,function(i,item){
							if(item.packLockTime == null){
								item.packLockTime = "";
							}
							var downloadPack = "<td><a href='#' id='dp"+item.packId+"' class='downloadPack' onClick='downloadPackFn("+item.packId+")'>下载<span class='badge'>"+item.downCount+"</span></a></td>";
							var invalidCount = item.invalid+item.wavZero;//无效数
							$("#packComplete tbody").append(
								"<tr>"+
									"<td>"+(i+1)+"</td>"+
										"<td><a href='#' class='packId' onClick='showPackDetail("+item.packId+")'>"+item.packName+"</a></td>"+
										"<td>"+item.taskLvl+"</td>"+
										"<td>"+item.taskCount+"</td>"+
										"<td>0</td>"+
										"<td>"+invalidCount+"</td>"+
										
										"<td>"+item.finishTaskCount+"</td>"+
										"<td>100%</td>"+
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
		/*---------------------------------------请求全部任务包列表-------------------------------------------------------------------*/
		loadAllPackList = function(pageNum){
			$.ajax({
				type:'POST',
				url:'${contextPath}/security/employer',
				data:{"packStuts":3,"page":pageNum,"packNameCondition":allPackNameCondition},
				dataType:'json',
				success:function(data){
					if(data.list == ""){
						$("#packAll tbody").empty();
						$("#packAll tbody").append("<tr class='text-danger'><td colspan='12'>无内容</td></tr>");
					}else{
						$("#packAll tbody").empty();
						$.each(data.list,function(i,item){
							if(item.packLockTime == null){
								item.packLockTime = "";
							}
							var invalidCount = item.invalid+item.wavZero;//无效数
							var surplusTask = item.taskCount - item.finishTaskCount - invalidCount;//未完成任务数
							var finishTaskRatio = item.finishTaskCount/item.taskCount*100;//完成任务比例
							finishTaskRatio = finishTaskRatio.toFixed(2);
							var downloadPack = "<td></td>";
							if(item.finishTaskCount != 0){
								downloadPack = "<td><a href='#' id='dp"+item.packId+"' class='downloadPack' onClick='downloadPackFn("+item.packId+")'>下载<span class='badge'>"+item.downCount+"</span></a></td>";
							}else{
								downloadPack = "<td><a href='#' id='dp"+item.packId+"' class='downloadPack' onClick='downloadPackFn(0)'>下载<span class='badge'>0</span></a></td>";
							}
							
							$("#packAll tbody").append(
								"<tr>"+
									"<td>"+(i+1)+"</td>"+
										"<td><a href='#' class='packId' onClick='showPackDetail("+item.packId+")'>"+item.packName+"</a></td>"+
										"<td>"+item.taskLvl+"</td>"+
										"<td>"+item.taskCount+"</td>"+
										"<td>"+surplusTask+"</td>"+
										"<td>"+invalidCount+"</td>"+
										"<td>"+item.finishTaskCount+"</td>"+
										"<td>"+finishTaskRatio+"%</td>"+
										
										"<td>"+item.packLockTime+"小时</td>"+
										"<td>"+item.createTime+"</td>"+
										"<td>"+item.taskMarkTime+"</td>"+
										downloadPack+
								"</tr>"
							);
						});
						var pageDom = $("#packAll .pagination");
						pageDom.empty();
						pcPageTotle = data.totlePage;
						page.creatPageHTML(pageNum,pcPageTotle,pageDom,"loadAllPackList");
						$("#packAll .pageGoBtn").click(function(){
							var pageNum = 0;
							pageNum = $("#packAll .pageGoText").val();
							if(pageNum !=0&&0<pageNum&&pageNum<pcPageTotle+1){
								loadAllPackList(pageNum);
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
					if(data.voiceNoteList == ""){
						$("#noteId").append("<option>无说明文件</option>");
					}else{
						$.each(data.voiceNoteList,function(i,item){
							$("#noteId").append("<option value="+item.noteId+">"+item.noteId+"</option>");
						});
					}
					if(data.markTimeMethodList == ""){
						$("#markTimeMethodList").html("<option value='0'>无</option>");
					}else{
						$.each(data.markTimeMethodList,function(i,item){
							$("#markTimeMethodList").append("<option value="+item.id+">"+item.name+"</option>");
						});
					}
					if(data.list != ""){
						$("#packUnzip tbody").empty();
						$.each(data.list,function(i,item){
							$("#packUnzip tbody").append(
								"<tr class='unziptr"+i+"'>"+
									"<td>"+(i+1)+"</td>"+
									"<td class='packName'>"+item+"</td>"+
									"<td class='packZipStatus'><a href='javascript:zipOnePack(\""+item+"\",\"unziptr"+i+"\");'>上传</a></td>"+
								"</tr>"
							);
						});
					}else{
						$("#packUnzip tbody").append("<tr><td colspan='3'>无未上传任务包</td></tr>");
					}
				}
			});
		};
		zipOnePack = function(pName,trClass){
			
			var taskLvl = $("#taskLvl").val();
			$("#taskLvlCon").text(taskLvl);
			var noteId = $("#noteId").val();
			$("#noteIdCon").text(noteId);
			var packLockTimeObj = $("#packLockTime");
			$("#packLockTimeCon").text(packLockTimeObj.val()+"小时");
			var markTimeMethod = $("#markTimeMethodList").val();
			var markTimeMethodCon = $("#markTimeMethodList option[value="+markTimeMethod+"]").text();
			$("#markTimeMethodCon").text(markTimeMethodCon);
			if(checkout.text.isempty(packLockTimeObj,"请填写任务时间！")) {
				$("#packLockTimeDiv").addClass("has-error").focus();
				return;
			}else{
				$(".has-error").removeClass("has-error");
			}
			$("#taskUpCon").modal('show');
			$("#comfirmUpload").click(function(){
				$("."+trClass+" .packZipStatus").text("任务包上传中");
				$.ajax({
					type:'POST',
					url:'${contextPath}/security/unzip',
					data:{"packName":pName,"taskLvl":taskLvl,"packLockTime":packLockTimeObj.val(),"noteId":noteId,"markTimeMethod":markTimeMethod},
					dataType:'json',
					success:function(data){
						$("."+trClass+" .packZipStatus").text(data.message);
					}
				});
				$("#taskUpCon").modal('hide');
			});
			
		};
		/*---------------------------------------下载任务包---------------------------------------------------------------*/
		downloadPackFn = function(packId){
			if(packId == 0){
				alert('没有已完成任务，不能下载！');
			}else{
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
			}
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
			$("#updateStatus").empty();
			$("#ctLvl option:selected").removeAttr("selected");
			$("#ctLvl option[value="+taskLvl+"]").attr("selected","selected");
			$("#changeTaskLevelModal").modal('show');
			changePackLvlPackId = packId;
			
		};
	</script>
</body>
</html>
