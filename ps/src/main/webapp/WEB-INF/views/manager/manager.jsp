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
		<div class="panel panel-default">
			<div class="panel-heading">用户列表	
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
								<input class="form-control" onkeydown="if(event.keyCode==13){return false;}" id=beginDate type="text" placeholder="起始时间">
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
				<tbody></tbody>
			</table>
			<ul class="pagination"></ul>
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
		$(document).ready(function(){
			
			var date = new Date();
			var todayDate = $.datepicker.formatDate( "yy-mm-dd",new Date());
			beginDate = todayDate;
			endDate = todayDate;
			$("#beginDate,#endDate").val(todayDate);
			chooseUserType(1);
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
			/*--------------------------------------点击查询按钮-------------------------------------------------------*/
			$("#searchBtn").click(function(){
				dateType = $("#dateTypeCheck").val();
				beginDate = $("#beginDate").val();
				endDate = $("#endDate").val();
				taskUpload = $("#taskUpload").val();
				userNameCondition = $("#userNameCondition").val();
				chooseUserType(1);
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
		/*--------------------------------------加载页面-------------------------------------------------------*/
		chooseUserType = function(pageNum){
			$.ajax({
				type:'POST',
				data:{"userType":nowUserType,"page":pageNum,"userNameCondition":userNameCondition,"taskUpload":taskUpload,"dateType":dateType,"beginDate":beginDate,"endDate":endDate},
				url:'${contextPath}/security/manager',
				dataType:'json',
				success:function(data){
					$("tbody").empty();
					if(data.list == ""){
						$("tbody").empty();
						$("tbody").append("<tr class='text-danger'><td colspan='6'>无内容</td></tr>");
					}else{
						var taskMarkTimeMonthTotle = data.taskMarkTimeMonthTotle;
						var aduitingMarkTimeMonthTotle = data.aduitingMarkTimeMonthTotle			
						$("#aduitingMarkTimeMonthTotle").text("待审标注："+aduitingMarkTimeMonthTotle);
						$("#taskMarkTimeMonthTotle").text("有效标注："+taskMarkTimeMonthTotle);
						
						pageTotal = data.totlePage;
						$.each(data.list,function(i,item){
							var status = "不可用";
							if(item.userStatus == "1")status = "可用";
							$("tbody").append(
								"<tr>"+
									"<td>"+(i+1)+"</td>"+
									"<td><a target='_blank' href='${contextPath}/security/workerDetail?userId="+item.userId+"&username="+item.username+"'>"+item.username+"</a></td>"+
									"<td>"+item.realName+"</td>"+
									"<td>"+item.phone+"</td>"+
									"<td>"+item.taskMarkTimeMonth+"</td>"+
									"<td>"+item.waitingMarkTime+"</td>"+
									"<td>"+item.downCount+"/"+item.unUploadCount+"/"+item.waitingCount+"</td>"+
									"<td>"+item.finishCount+"/"+item.giveUpCount+"/"+item.oldCount+"</td>"+
									"<td class='userStatus'><a id='usta"+item.userId+"' href='#' onClick='changeUserStatus("+item.userId+","+item.userStatus+",\""+item.username+"\")'>"+status+"</a></td>"+
								"</tr>"
							);
							var pageDom = $(".pagination");
							pageDom.empty();
							page.creatPageHTML(pageNum,pageTotal,pageDom,"chooseUserType");
							/*--------------------------------------跳转页-------------------------------------------------------*/
							$(".pageGoBtn").click(function(){
								var pageNum = 0;
								pageNum = $(".pageGoText").val();
								if(pageNum !=0&&0<pageNum&&pageNum<pageTotal+1){
									chooseUserType(pageNum);
								}
							});
						});
					}
				}
			});
		}
	</script>
</body>
</html>
