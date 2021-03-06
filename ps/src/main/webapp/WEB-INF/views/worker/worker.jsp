<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>工作者页面</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta content="width=device-width, initial-scale=1" name="viewport">
<link rel="stylesheet" type="text/css" href="${contextPath}/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/bootstrap-theme.min.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/public.css">
<style type="text/css">
#downloadPanel,#uploadPanel {
	display: none;
}

.form-inline .form-control[type=file] {
	border: 0;
	padding: 0;
	box-shadow: none;
	width: 70px;
}

#downloadPanel h3 {
	display: inline-block;
	margin-right: 10px;
}

form {
	padding: 10px;
}

#warning {
	border: solid thin yellow;
	background-color: pink;
	color: red;
	font-size: 16px;
}
</style>
<script type="text/javascript" src="${contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.fileupload.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${contextPath}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/common.js"></script>
</head>
<body>
	<div align="center">
		<div class="alert alert-warning" align="center" style="width: 1000px;">
			<p>
				严重警告：工作者在标注过程中接触到的所有语料(.wav文件),必须在标注完成后全部销毁，禁止拷贝复制到U盘或其他存储介质中。<br> 一经发现取消标注资格没收所有标注所得金额。并保留追究保密协议中所承担的法律责任。望所有工作者悉知!
			</p>
		</div>
	</div>
	<jsp:include page="../headWorker.jsp" />
	<!-------------------------------- 下载任务 -------------------------------------------------->
	<div class="container" id="downloadPanel">
		<c:forEach var="i" begin="1" end="${typeCount}">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title" id = "totleCount${i}"></h3>
					<button type="button" id="refreshPage${i}" class="btn btn-info">刷新</button>
				</div>
				<form action="${contextPath}/security/downTask" method="get" id="downTask" name="downTask" role="form" class="form-inline">
					<div class="form-group" id="">
						<p class="form-control-static">选择下载任务个数:</p>
					</div>
					<div class="form-group">
						<select class="form-control" name="downTaskCount${i}" id="downTaskCount${i}"></select>
					</div>
					<div class="form-group">
						<button type="button" id = "doneLoadBtn${i}"  class="btn btn-primary doneLoadBtn">下载任务</button>
					</div>
				</form>
			</div>
		</c:forEach>
	</div>
	<!-------------------------------- 上传任务 -------------------------------------------------->
	<div class="container" id="uploadPanel">
		<div class="container">
			<div class="panel panel-default">

				<form action="${contextPath}/security/upTagAndTextGrid" method="post" name="upTagAndTextGrid" role="form" class="form-inline" enctype="multipart/form-data">
					<div class="form-group" style="width:50%">
						<div class="col-sm-4">
							<label for="TAG" class="control-label">选择已完成任务</label>
						</div>
						<div class="col-sm-3">
							<input type="file" class="form-control" name="file" id="fileupload" autocomplete="off" multiple>
						</div>
						<span class="col-sm-5 help-block" id="uploadHelp">已选择0个文件</span>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<button id="uploadBtn" type="button" class="btn btn-primary">上传文件</button>
						</div>
					</div>
				</form>
			</div>
		</div>
		<!-------------------------------- 选项卡区域 -------------------------------------------------->
		<div class="container">
			<ul class="nav nav-tabs" role="tablist">
				<li class="active"><a href="#uploadFalTable" role="tab" data-toggle="tab">待上传任务列表</a></li>
				<li><a href="#unqualifiedList" role="tab" data-toggle="tab">不合格任务包列表</a></li>
			</ul>
			<div class="tab-content">
				<!-- ****************************************待上传任务列表******************************************************* -->
				<div class="tab-pane active" id="uploadFalTable">
					<div class="panel panel-default">
						<div class="panel-body">
							<h3 class="panel-title" id="taskLeftTimeInterval">
								距离任务完成时间还剩:<span class="text-danger"></span>
							</h3>
						</div>
						<table class="table table-striped table-bordered">
							<thead>
								<tr>
									<th width="10%">序号</th>
									<th width="40%">任务名称</th>
									<th width="40%">下载时间</th>
									<th width="10%">放弃</th>
								</tr>
							</thead>
							<tbody id="waitForUpTable"></tbody>
						</table>
					</div>
				</div>
				<!-- ****************************************不合格任务包列表******************************************************* -->
				<div class="tab-pane" id="unqualifiedList">
					<div class="panel panel-default">
						<div class="panel-body">
							<h3 class="panel-title" id="taskUnLeftTimeInterval">
								距离任务完成时间还剩:<span class="text-danger"></span>
							</h3>
						</div>
						<table class="table table-striped table-bordered">
							<thead>
								<tr>
									<th width="10%">序号</th>
									<th width="30%">任务名称</th>
									<th width="20%">下载时间</th>
									<th width="15%">标注说明</th>
									<th width="15%">错误说明</th>
									<th width="10%">放弃</th>
								</tr>
							</thead>
							<tbody id="unqualifiedTable"></tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-------------------------------- 弹出窗口 -------------------------------------------------->
	<div id="taskUpResult" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">Modal title</h4>
				</div>
				<div class="modal-body">
					<ul class="nav nav-tabs" role="tablist">
						<li class="active"><a href="#taskListNoMath" role="tab" data-toggle="tab">未匹配任务</a></li>
						<li><a href="#taskListMath" role="tab" data-toggle="tab">已匹配任务</a></li>
					</ul>
					<div class="tab-content">
						<div class="tab-pane active" id="taskListNoMath">
							<ul></ul>
						</div>
						<div class="tab-pane" id="taskListMath">
							<ul></ul>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
	<!-------------------------------- 显示错误信息-------------------------------------------------->
	<div id="showUnqualifie" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">审核未通过原因</h4>
				</div>
				<div class="modal-body"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
	<script type="text/javascript">
		var tltInterval = 1000;
		var todayTime = "";
		var intervalMainList = null;
		var intervalUnqList = null;
		$(document).ready(
	function() {
		/*******************************加载页面**************************************************/
		loadPage();

		/*******************************上传check**************************************************/
		var textGridReady = false;
		var tagReady = false;
		checkSubBtnStaus = function() {
			if (tagReady && textGridReady) {
				$("button").removeAttr("disabled");
			} else {
				$("button").attr("disabled", "disabled");
			}
		};
		$("#TAG").change(
				function() {
					var tag = $("#TAG");

					if (checkout.text.isempty(tag,"文件不能为空！")) {
						tagReady = false;
						checkSubBtnStaus();
						return;
					}
					if (checkout.file.fileType(tag, "tag","请上传tag格式文件！")) {
						tagReady = true;
						checkSubBtnStaus();
					} else {
						tagReady = false;
						checkSubBtnStaus();
					}
				});
		$("#TextGrid").change(
			function() {
				var textGrid = $("#TextGrid");

				if (checkout.text.isempty(textGrid, "文件不能为空！")) {
					textGridReady = false;
					checkSubBtnStaus();
					return;
				}
				if (checkout.file.fileType(textGrid, "textGrid","请上传textGrid格式文件！")) {
					textGridReady = true;
					checkSubBtnStaus();
				} else {
					textGridReady = false;
					checkSubBtnStaus();
				}
			});

			/*******************************多文件上传**************************************************/
			$("#fileupload").fileupload({
				singleFileUploads : false,
				formAcceptCharset : 'gb2312',
				typ : "POST",
				dataType : "json",
				url : "${contextPath}/security/upTagAndTextGrid",
				add : function(e, data) {
					var $this = $(this);
					data.autoUpload = false;
					var fileNum = data.files.length;
					$("#uploadHelp").text("已选择" + fileNum+ "个文件");
					$("#uploadBtn").click(function() {
						if (data.files.length == 0) {
							$("#uploadHelp").text("请选择上传的文件");
							return false;
						};
						data.submit();
						$(this).attr("disabled","disabled").text("上传中。。");
	
					});
				},
				done : function(e, result) {
					var taskListNoMath = $("#taskListNoMath ul");
					var taskListMath = $("#taskListMath ul");
					var jsonData = result.result;
					if (jsonData.listAll != "") {
						taskListNoMath.html("");
						for ( var i = 0; i < jsonData.listAll.length; i++) {
							//$.each(jsonData.listAll,function(i,alllist){
							var alllist = jsonData.listAll[i];
							var unhad = false;
							if (jsonData.listMath != "") {
								for ( var k = 0; k < jsonData.listMath.length; k++) {
									//$.each(jsonData.listMath,function(k,matchlist){
									var matchlist = jsonData.listMath[k];
									if (matchlist == alllist) {
										unhad = false;
										break;
									} else {
										unhad = true;
									}
	
								}
								;
							} else {
								unhad = true;
							}
							if (unhad) {
								taskListNoMath.append("<li>"
												+"<span>"
												+ alllist
												+ "</span>"
												+ "</li>");
							}
						};
					} else {
						taskListNoMath.html("<li class='text-success'>全部通过</li>");
					}
					if (jsonData.listMath != "") {
						taskListMath.html("");
						$.each(jsonData.listMath,function(i,item) {
							taskListMath.append("<li>"
											+
											"<span>"
											+ item
											+ "</span>"
											+ "</li>");
						});
					} else {
						taskListMath.html("<li class='text-danger'>无匹配内容</li>");
					}
					$("#taskUpResult").modal('show');
				}
			});
							/*******************************modal关闭时刷新页面**************************************************/
							$("#taskUpResult").on('hidden.bs.modal',
									function(e) {
										loadPage();

									});
							/*******************************下载任务**************************************************/
							$("#doneLoadBtn1").click(
								function() {
									var downing = <%=session.getAttribute("downing")%>
									if (downing == 1) {
										alert("下载中,请耐心等待!!!");
										$(this).attr("disabled","disabled").text("下载中。。");
										$("#doneLoadBtn2").attr("disabled","disabled").text("下载中。。");
									} else {
										$(this).attr("disabled","disabled").text("下载中。。");
										$("#doneLoadBtn2").attr("disabled","disabled").text("下载中。。");
										var	downTaskCount = $("#downTaskCount1").val();
										if (downTaskCount == 0) {
											return false;
										} else {
											$.ajax({
												type : 'GET',
												url : '${contextPath}/security/downTask',
												data : {
													"downTaskCount" : downTaskCount,
													"packType" : 1,
												},
												dataType : 'json',
												success : function(
														data) {
													if(data.replay == 0){
														if (data.wrongPath != "") {
															window.open("${contextPath}"+ data.wrongPath);
															loadPage();
														}
													}else{
														alert("您有任务没有完成，不能下载新任务！");
													}
													
												}
											});
										}
									}
								});
		/**************************************************************/						
								$("#doneLoadBtn2").click(
								function() {
									var downing = <%=session.getAttribute("downing")%>
									if (downing == 1) {
										alert("下载中,请耐心等待!!!");
										$(this).attr("disabled","disabled").text("下载中。。");
										$("#doneLoadBtn1").attr("disabled","disabled").text("下载中。。");
									} else {
										$(this).attr("disabled","disabled").text("下载中。。");
										$("#doneLoadBtn1").attr("disabled","disabled").text("下载中。。");
										var	downTaskCount = $("#downTaskCount2").val();
										if (downTaskCount == 0) {
											return false;
										} else {
											$.ajax({
												type : 'GET',
												url : '${contextPath}/security/downTask',
												data : {
													"downTaskCount" : downTaskCount,
													"packType" : 2,
												},
												dataType : 'json',
												success : function(
														data) {
													if (data.wrongPath != "") {
														window.open("${contextPath}"+ data.wrongPath);
														loadPage();
													}
												}
											});
										}
									}
								});
							/*******************************刷新按钮**************************************************/
							$("#refreshPage1").click(function() {
								window.location.reload();
							});
							$("#refreshPage2").hide();
						});
		/*-----------------------------------加载页面----------------------------------------------------*/
		loadPage = function() {
			$("#uploadBtn").removeAttr("disabled").text("上传文件");
			var today = new Date();
			todayTime = today.valueOf();
			if (intervalMainList || intervalUnqList) {
				window.clearInterval(intervalMainList);
				window.clearInterval(intervalUnqList);
			}
			$.ajax({
				type : 'POST',
				url : '${contextPath}/security/worker',
				data : {
					"taskEffective" : 0,
					"packType" :0
				},
				dataType : 'json',
				success : function(data) {
					var workerMark = data.workerMark;
					if (workerMark == 0) {//下载
						var taskTotal = data.countTaskDoing;
						var taskTotal2 = data.countTaskDoing2;
						//var packTotal = data.countPackDoing;
						$("#downloadPanel #totleCount1").text("一层   任务数:" + taskTotal + "个。下载标注说明编号："+ data.noteId);
						$("#downloadPanel #totleCount2").text("三层   任务数:" + taskTotal2 + "个。下载标注说明编号："+ data.noteId);
						if (data.downCount != 0) {
							if (parseInt(data.downCount / 10)) {
								$("#downTaskCount1").append(
										"<option vale='1'>1</option>");
								for ( var i = 1; i < parseInt(data.downCount / 10) + 1; i++) {
									$("#downTaskCount1").append(
											"<option vale='" + (i * 10)+ "'>" + (i * 10)+ "</option>");
								}
								if (data.downCount % 10) {
									$("#downTaskCount1").append(
											"<option vale='"+data.downCount+"'>"+ data.downCount+ "</option>");
								}
							} else {
								for ( var i = 1; i < data.downCount + 1; i++) {
									$("#downTaskCount1").append("<option vale='"+i+"'>" + i+ "</option>");
								}
							}
	
						} else {
							$("#downTaskCount1").attr("disabled","disabled");
						}
						/************/
						if (data.downCount2 != 0) {
							$("#downTaskCount2").removeAttr("disabled");
							if (parseInt(data.downCount2 / 10)) {
								$("#downTaskCount2").append(
										"<option vale='1'>1</option>");
								for ( var i = 1; i < parseInt(data.downCount2 / 10) + 1; i++) {
									$("#downTaskCount2").append(
											"<option vale='" + (i * 10)+ "'>" + (i * 10)+ "</option>");
								}
								if (data.downCount2 % 10) {
									$("#downTaskCount2").append(
											"<option vale='"+data.downCount2+"'>"+ data.downCount2+ "</option>");
								}
							} else {
								for ( var i = 1; i < data.downCount2 + 1; i++) {
									$("#downTaskCount2").append("<option vale='"+i+"'>" + i+ "</option>");
								}
							}
	
						} else {
							$("#downTaskCount2").attr("disabled","disabled");
						}
						/*************************/
						$("#uploadPanel").hide();
						var downing = <%=session.getAttribute("downing")%> ;
						if (downing == 0) {
							$("#doneLoadBtn1").removeAttr("disabled").text("下载任务");
							$("#doneLoadBtn2").removeAttr("disabled").text("下载任务");
						} else if (downing == 1) {
							$("#doneLoadBtn1").attr("disabled","disabled").text("下载中。。");
							$("#doneLoadBtn2").attr("disabled","disabled").text("下载中。。");
						}
						$("#downloadPanel").show();
					} else {//上传
						loadUnqualified();
						var mm = data.mm;
						if (data.list == "") {
							$("#waitForUpTable").empty();
							$("#waitForUpTable")
									.append(
											"<tr class='text-danger'><td colspan='4'>无内容</td></tr>");
						} else {
							$("#waitForUpTable").empty();
							$.each(
								data.list,
								function(i, item) {
									var giveUpBtn = "<td><a href='javascript:giveUpTask("
											+ item.taskId
											+ ")'>放弃</a></td>";
									$("#waitForUpTable").append(
									"<tr>"
											+ "<td>"
											+ (i + 1)
											+ "</td>"
											+ "<td>"
											+ item.taskName
											+ "</td>"
											+ "<td>"
											+ item.taskDownloadTime
											+ "</td>"
											+ giveUpBtn
											+ "</tr>");
								});
						}
						if (mm != 0) {
							var endTime = todayTime + mm;
							intervalMainList = window.setInterval(
							function() {
								ShowCountDown(endTime,$("#taskLeftTimeInterval span"));
							}, tltInterval);
						}
	
						$("#downloadPanel").hide();
						$("#uploadPanel").show();
					}
				}
			});
		}
		/*******************************倒计时**************************************************/
		function ShowCountDown(endTime, obj) {
			var now = new Date();
			var leftTime = endTime - now.valueOf();
			var leftsecond = parseInt(leftTime / 1000);
			var day1 = Math.floor(leftsecond / (60 * 60 * 24));
			var hour = Math.floor((leftsecond - day1 * 24 * 60 * 60) / 3600);
			var minute = Math
					.floor((leftsecond - day1 * 24 * 60 * 60 - hour * 3600) / 60);
			var second = Math.floor(leftsecond - day1 * 24 * 60 * 60 - hour
					* 3600 - minute * 60);

			obj.text(day1 + "天" + hour + "小时" + minute + "分" + second + "秒");
		}
		/*-----------------------------------加载不合格任务列表----------------------------------------------------*/
		function loadUnqualified() {
			$.ajax({
						type : 'POST',
						url : '${contextPath}/security/worker',
						data : {
							"taskEffective" : 2,
							"packType" :0
						},
						dataType : 'json',
						success : function(data) {
							var mm = data.mm;
							if (data.list == "") {
								$("#unqualifiedTable").empty();
								$("#unqualifiedTable").append(
									"<tr class='text-danger'><td colspan='5'>无内容</td></tr>");
							} else {
								$.each(
								data.list,
								function(i, item) {
									var giveUpBtn = "<td><a href='javascript:giveUpTask("
											+ item.taskId
											+ ")'>放弃</a></td>";
									var showUnqualifiedBtn = "<td><a href='javascript:showUnqualifie("
											+ item.inspectorrecordId
											+ ")'>"
											+ item.inspectorrecordId
											+ "</a></td>";
									$("#unqualifiedTable").append(
										"<tr>"
											+ "<td>"
											+ (i + 1)
											+ "</td>"
											+ "<td>"
											+ item.taskName
											+ "</td>"
											+ "<td>"
											+ item.taskDownloadTime
											+ "</td>"
											+ "<td>"
											+ item.noteId
											+ "</td>"
											+ showUnqualifiedBtn
											+ giveUpBtn
											+ "</tr>");
								});
							}
							if (mm != 0) {
								var endTime = todayTime + mm;
								intervalUnqList = window.setInterval(
								function() {
									ShowCountDown(
											endTime,
											$("#taskUnLeftTimeInterval span"));
								}, tltInterval);
							}
						}
					});
		}
		/*-----------------------------------放弃任务----------------------------------------------------*/
		function giveUpTask(taskId) {
			var conWin = confirm("确定要放弃该任务吗？");
			if (conWin) {
				$.ajax({
					type : 'POST',
					url : '${contextPath}/security/GiveUpTask',
					data : {
						"taskId" : taskId
					},
					dataType : 'json',
					success : function(data) {
						if (data.replay == "1") {
							alert("任务已放弃！");
							loadPage();
						} else {
							alert("放弃任务失败！");
						}
					}
				});
			}
		}
		function showUnqualifie(inspectorrecordId) {
			var inspectorrecordMsg = "";
			if (inspectorrecordId == 0) {
				inspectorrecordMsg = "暂无不合格原因！"
				$("#showUnqualifie .modal-body").html(inspectorrecordMsg);
				$("#showUnqualifie").modal('show');
			} else {
				$.ajax({
					type : 'POST',
					url : '${contextPath}/security/getInspectrecord',
					data : {
						"inspectorrecordId" : inspectorrecordId
					},
					dataType : 'json',
					success : function(data) {
						inspectorrecordMsg = data.note;
						$("#showUnqualifie .modal-body").html(inspectorrecordMsg);
						$("#showUnqualifie").modal('show');
					}
				});
			}
		}
	</script>
</body>
</html>
