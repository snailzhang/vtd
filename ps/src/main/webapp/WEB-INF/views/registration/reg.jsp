<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>vtd的 'login.jsp'page</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta content="width=device-width, initial-scale=1" name="viewport">
<link rel="stylesheet" type="text/css" href="http://cdn.bootcss.com/bootstrap/3.2.0/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="http://cdn.bootcss.com/bootstrap/3.2.0/css/bootstrap-theme.min.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/public.css" />
<script type="text/javascript" src="http://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/common.js"></script>
</head>
<body>
	<div class="container">


		<form id="form" action="${contextPath}/registration" method="post" class="form-horizontal" role="form">
			<h1 class="text-center">语音标注网上报名系统</h1>
			<div id=message></div>
			<div class="form-group">
				<label for="name" class="col-sm-2 control-label">姓名</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="name" name="name" placeholder="姓名">
				</div>
			</div>
			<div class="form-group">
				<label for="card" class="col-sm-2 control-label">残疾证号</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="card" name="card" placeholder="残疾证号">
				</div>
			</div>
			<div class="form-group">
				<label for="district" class="col-sm-2 control-label">发证机构</label>
				<div class="col-sm-10">
					<select class="form-control" name="district" placeholder="发证机构">
						<option value="0">请选择发证机构</option>
						<c:forEach items="${districts}" var="item">
							<option value="${item.id}">${item.name}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label for="phone" class="col-sm-2 control-label">手机</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="phone" name="phone" placeholder="手机">
				</div>
			</div>
			<div class="form-group">
				<label for="qq" class="col-sm-2 control-label">QQ</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="qq" name="qq" placeholder="QQ">
				</div>
			</div>
			<div class="form-group">
				<label for="address" class="col-sm-2 control-label">家庭住址</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="address" name="address" placeholder="家庭住址">
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<div class="checkbox">
						<label> <input type="checkbox">签署保密协议 <a href="javascript:bmxy();">点击查看</a> </label>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-success btn-lg">点击报名</button>
				</div>
			</div>
		</form>
	</div>
	<!-- 模态框（Modal） -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">

					<h4 class="modal-title" id="myModalLabel">保密协议</h4>
				</div>
				<div class="modal-body">
					<p>一、保密范围</p>
					<p>甲、乙双方确认，乙方应承担保密义务的范围包括但不限于以下内容：</p>
					<p>1.1 技术信息，其范围包含但不仅限于语音文件、标注程序、培训文档、技术方案、信息系统设计方案、操作流程、技术指标、技术文档、计算机软件、数据库、录入单据、输单信息、操作手册、涉及商业秘密的业务函电等等；</p>
					<p>1.2 经营信息，其范围主要包括甲方业务票据所载的所有相关信息，及甲方公司的相关信息等；</p>
					<p>1.3属于第三人的商业秘密（含甲方客户的经营信息和个人信息），依照法律规定或者有关合同的约定，乙方承诺对外承担保密义务的事项；</p>
					<p>1.4甲方未予公开的其他商业信息。</p>
					<p>二、保密义务</p>
					<p>对第一条所称的商业秘密，乙方承担以下保密义务：</p>
					<p>2.1 不得刺探与本职工作或本身业务无关的商业秘密；</p>
					<p>2.2 不得向不承担保密义务的任何第三人披露甲方的商业秘密；</p>
					<p>2.3 不得以出借、赠与、出租、转让、外泄等方式处理甲方商业秘密，或协助不承担保密义务的任何第三人使用甲方的商业秘密；</p>
					<p>2.4 如发现商业秘密被泄露或者自己过失泄露商业秘密，应当采取有效措施防止泄密进一步扩大，并及时向甲方报告；</p>
					<p>2.5 乙方对业务票据的备份数据按要求定期清除，不得再以任何方式保存业务票据的数据。</p>
					<p>三、保密期限</p>
					<p>甲、乙双方确认，乙方的保密义务自乙方获知本合同第一条所述的商业秘密时开始，至该商业秘密被合法公开时止。乙方是否在甲方工作，不影响保密义务的承担。</p>
					<p>四、违约责任</p>
					<p>4.1 乙方未能履行本协议所规定的保密义务，造成甲方商业秘密泄露的，将视违约行为的责任性质、情节严重程度、损失程度等要求乙方承担赔偿责任。</p>
					<p>4.2 乙方应就下列损失向甲方承担损害赔偿责任：</p>
					<p>4.2.1 甲方因乙方的违约行为所受到的实际经济损失。实际经济损失难以计算的，按照乙方因违约行为所获得的收入或受益第三人因乙方的违约行为所获得的收入计算。</p>
					<p>4.2.2 乙方侵害甲方商业秘密的，应当承担甲方为此产生的全部费用，包括但不限于：调查费、鉴定费、差旅费、律师费、诉讼费等。</p>
					<p>4.2.3 因乙方的违约行为导致第三方（含甲方客户）向甲方提出索赔要求的，乙方应承担甲方因此遭受的经济损失。</p>
					<p>4.3 因乙方的违约行为侵犯了甲方的商业秘密的，甲方可以选择根据本合同要求乙方承担违约责任，或者根据国家有关法律、法规要求乙方承担侵权责任。</p>
					<p>4.4未经甲方书面允许，不得以任何方式向任何第三方泄露票据信息，乙方如有违背，甲方将追究乙方的侵权、违约责任。</p>
					<p>五、争议的解决办法</p>
					<p>因执行本合同而发生纠纷，可以由双方协商解决。协商不成或者一方不愿意协商的，应由甲方所在地人民法院审理。</p>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" data-dismiss="modal">同意</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->


	<script type="text/javascript">
		function bmxy() {
			$('#myModal').modal({
				keyboard : true
			});
		}
		function checkNameAndCard() {
			var name = $("#name").val();
			var card = $("#card").val();
			var msg = '<div class="alert alert-danger alert-dismissable">'
					+ '<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>'
					+ '错误：姓名和残疾证号不匹配!' + '</div>';
			$.ajax({
				type : 'post',
				url : '/ps/rc',
				data : {
					'name' : name,
					'card' : card
				},
				dataType : 'json',
				success : function(data) {
					if (data.result == 0) {
						$("#message").html(msg);

					} else {
						$("#message").empty();
						$("#form").submit();
					}
				}
			});
		}

		$(document).ready(function() {
			$("#form").submit(function() {
				checkNameAndCard();
				return false;
			});

		});
	</script>
	<script>
		$(function() {
			$('#myModal').on('hide.bs.modal', function() {
				//alert('嘿，我听说您喜欢模态框...');
			})
		});
	</script>
</body>

</html>
