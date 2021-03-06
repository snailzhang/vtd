<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>标注说明列表页</title>
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
	<c:if test="${usertype == 4}">
		<jsp:include page="../headWorker.jsp" />
	</c:if>
	<c:if test="${usertype != 4}">
		<jsp:include page="../head.jsp" />
	</c:if>
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">标注说明列表</div>
			<div class="panel-body">
				<form class="form-inline" role="form">
					<div class="form-group">
						<div class="input-group">
							<div class="input-group-addon">说明名称/编号：</div>
							<input class="form-control" onkeydown="if(event.keyCode==13){return false;}" id="searchTitle" type="text" placeholder="查询说明名称或者编号">
						</div>
					</div>
					<button type="button" id="searchBtn" class="btn btn-default">查询</button>
				</form>
			</div>
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<th>序号</th>
						<th>名称</th>
						<th>编号</th>
						<th>上传时间</th>
						<th>查看</th>
						<c:if test="${usertype =='1'||usertype =='2'}">
							<th>编辑</th>
							<!--  <th>删除</th>-->
						</c:if>
					</tr>
				</thead>
				<tbody></tbody>
			</table>
			<ul class="pagination"></ul>
		</div>
	</div>
	<script type="text/javascript">
		var userType = '${usertype}';
		var condition = "";
		$(document).ready(function() {
			loadVoiceNoteList(1);
			$("#searchBtn").click(function() {
				condition = $("#searchTitle").val();
				loadVoiceNoteList(1);
			});
		});
		loadVoiceNoteList = function(pageNum) {
			$
					.ajax({
						type : 'POST',
						url : '${contextPath}/security/voiceNote',
						data : {
							"condition" : condition,
							"page" : pageNum
						},
						dataType : 'json',
						success : function(data) {
							if (data.list == "") {
								$("tbody").empty();
								if (userType == '1' || userType == '2') {
									$("tbody")
											.append(
													"<tr class='text-danger'><td colspan='6'>无内容</td></tr>");
								} else {
									$("tbody")
											.append(
													"<tr class='text-danger'><td colspan='5'>无内容</td></tr>");
								}

							} else {
								$("tbody").empty();
								var editTd = "";

								$
										.each(
												data.list,
												function(i, item) {
													if (userType == '1'
															|| userType == '2') {
														editTd = "<td><a href='${contextPath}/security/voiceNoteContent?id="
																+ item.id
																+ "&type=1' target='_blank'>编辑</a></td>";
														//+"<td><a href='javascript:deleteVoiceNote("+item.id+")'>删除</a></td>";
													}
													$("tbody")
															.append(
																	"<tr>"
																			+ "<td>"
																			+ (i + 1)
																			+ "</td>"
																			+ "<td>"
																			+ item.noteTitle
																			+ "</td>"
																			+ "<td>"
																			+ item.noteId
																			+ "</td>"
																			+ "<td>"
																			+ item.createTime
																			+ "</td>"
																			+ "<td><a href='${contextPath}/security/voiceNoteContent?id="
																			+ item.id
																			+ "&type=0' target='_blank'>查看</a></td>"
																			+ editTd
																			+ "</tr>");

												});
								var pageDom = $(".pagination");
								pageDom.empty();
								pucPageTotle = data.totlePage;
								page.creatPageHTML(pageNum, pucPageTotle,
										pageDom, "loadVoiceNoteList");
								$(".pageGoBtn")
										.click(
												function() {
													var pageNum = 0;
													pageNum = $(
															"#packUncomplete .pageGoText")
															.val();
													if (pageNum != 0
															&& 0 < pageNum
															&& pageNum < pucPageTotle + 1) {
														loadUnCompletePackList(pageNum);
													}
												});
							}
						}
					});
		};
		deleteVoiceNote = function(id) {
			var conWin = confirm("确定要删除该说明吗？");
			if (conWin) {
				$.ajax({
					type : 'POST',
					url : '${contextPath}/security/deleteVoiceNote',
					data : {
						"id" : id
					},
					dataType : 'json',
					success : function(data) {
						if (data.replay == "1") {
							alert(data.message);
							window.location.reload();
						}
					}
				});
			}

		};
	</script>
</body>
</html>
