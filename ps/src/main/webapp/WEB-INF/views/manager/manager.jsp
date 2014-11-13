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
		<div class="panel panel-default">
			<div class="panel-heading">用户列表<span id="taskMarkTimeMonthTotle" class="pull-right text-success"></span></div>
			<div class="panel-body">
				<form class="form-inline" role="form">
					<div class="form-group">
						<div class="input-group">
							<div class="input-group-addon"> 用户名：</div>
							<input class="form-control" onkeydown="if(event.keyCode==13){return false;}" id="userNameCondition" type="text" placeholder="查询用户">
						</div>
					</div>
					<div class="form-group">
						<p class="form-control-static">年份：</p>
					</div>
					<div class="form-group">
						<select class="form-control" id="year">
							<option value="2014">2014年</option>
							<option value="2015">2015年</option>
							<option value="2016">2016年</option>
							<option value="2017">2017年</option>
							<option value="2018">2018年</option>
						</select>
					</div>
					<div class="form-group">
						<p class="form-control-static">月份：</p>
					</div>
					<div class="form-group">
						<select class="form-control" id="month">
							<option value="1">1月</option>
							<option value="2">2月</option>
							<option value="3">3月</option>
							<option value="4">4月</option>
							<option value="5">5月</option>
							<option value="6">6月</option>
							<option value="7">7月</option>
							<option value="8">8月</option>
							<option value="9">9月</option>
							<option value="10">10月</option>
							<option value="11">11月</option>
							<option value="12">12月</option>
						</select>
					</div>
					<div class="form-group">
						<p class="form-control-static">本月工作状态：</p>
					</div>
					<div class="form-group">
						<select class="form-control" id="taskUpload">
							<option value="2">全部</option>
							<option value="1">工作</option>
							<option value="0">无工作</option>
						</select>
					</div>
					<button type="button" id="searchBtn" class="btn btn-default">查询</button>
				</form>
			</div>
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<th>序号</th>
						<th>姓名</th>
						<th>用户组</th>
						<th>创建时间</th>
						<th>标注时间</th>
						<th>用户状态</th>
					</tr>
				</thead>
				<tbody></tbody>
			</table>
			<ul class="pagination"></ul>
		</div>
		
		<form role="form" class="form-signin" action="${contextPath}/security/addUser" method="get">
			<button type="submit" class="btn btn-lg btn-primary btn-block">添加用户</button>
		</form>
		<div class="modal fade">
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
	</div>
	<script type="text/javascript">
		var utDropdownOpen = false;
		var nowUserType = 4;
		var nowPage = 0;
		var userNameCondition = "";
		var month = 0;
		var taskUpload = 2;
		var year = 0;
		var userId = 0;
		var pageTotal = 0;
		$(document).ready(function(){
			var date = new Date();
			var nowMonth = date.getMonth()+1;
			var nowYear = date.getFullYear();
			month = nowMonth;
			year = nowYear;
			$("#month option[value="+month+"]").attr("selected","selected");
			$("#year option[value="+year+"]").attr("selected","selected");
			chooseUserType(1);
			/*--------------------------------------点击查询按钮-------------------------------------------------------*/
			$("#searchBtn").click(function(){
				month = $("#month").val();
				year = $("#year").val();
				taskUpload = $("#taskUpload").val();
				userNameCondition = $("#userNameCondition").val();
				chooseUserType(1);
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
			$(".modal").on('hidden.bs.modal', function (e) {
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
			$(".modal").modal('show');
			
		};
		/*--------------------------------------加载页面-------------------------------------------------------*/
		chooseUserType = function(pageNum){
			$.ajax({
				type:'POST',
				data:{"userType":nowUserType,"page":pageNum,"userNameCondition":userNameCondition,"month":month,"taskUpload":taskUpload,"year":year},
				url:'${contextPath}/security/manager',
				dataType:'json',
				success:function(data){
					$("tbody").empty();
					if(data.list == ""){
						$("tbody").empty();
						$("tbody").append("<tr class='text-danger'><td colspan='6'>无内容</td></tr>");
					}else{
						var taskMarkTimeMonthTotle = data.taskMarkTimeMonthTotle;
						$("#taskMarkTimeMonthTotle").text("本月标注总时长："+taskMarkTimeMonthTotle);
						pageTotal = data.totlePage;
						$.each(data.list,function(i,item){
							var status = "不可用";
							if(item.userStatus == "1")status = "可用";
							$("tbody").append(
								"<tr>"+
									"<td>"+(i+1)+"</td>"+
									"<td><a target='_blank' href='${contextPath}/security/workerDetail?userId="+item.userId+"&username="+item.username+"'>"+item.username+"</a></td>"+
									"<td>"+item.usertypeenglish+"</td>"+
									"<td>"+item.createTime+"</td>"+
									"<td>"+item.taskMarkTimeMonth+"</td>"+
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
