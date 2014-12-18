<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>查看标注说明页</title>
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
	
	<div class="container">
		<form role="form" class="form-horizontal">
			<div class="form-group" id="">
				<label for="noteTitle" class="col-sm-2 control-label">规则名称：</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" value='${voiceNote.noteTitle}' name="noteTitle" id="noteTitle" placeholder="规则名称" required="required" autocomplete="off">
					<span class="help-block"></span>
				</div>
			</div>
			<script type="text/plain" id="myEditor" style="width:100%;height:240px;">${voiceNote.noteContentText}</script>
			<button type="button" id="saveEdit" class="btn btn-default">保存</button>
		</form>
	</div>
	<script type="text/javascript">
		window.UMEDITOR_CONFIG.imageUrl = "${contextPath}/security/uploadImage";
		var um = UM.getEditor('myEditor');
		$("#saveEdit").click(function(){
			var hasContent = false;
			hasContent = UM.getEditor('myEditor').hasContents();
			if(hasContent){
				var con = UM.getEditor('myEditor').getContent();
				var noteTitle = $("#noteTitle");
				if(checkout.text.isempty(noteTitle,"名称不能为空！"))return;
				$.ajax({
					type:'POST',
					url:'${contextPath}/security/updateVoiceNote',
					data:{"title":noteTitle.val(),"content":con,"id":'${voiceNote.id}'},
					dataType:'json',
					success:function(data){
						if($("#editTaskRole .panel-body .alert").length>0)$("#editTaskRole .panel-body .alert").remove();
						var $alertMsg = $("<div class='alert alert-dismissable'>"+data.message+"</div>");
						$alertMsg.append("<button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button>");
						if(data.replay == "1"){
							$alertMsg.addClass("alert-success");
						}else{
							$alertMsg.addClass("alert-danger");
						}
						$("form").prepend($alertMsg);
					}
				});
			}
		});
	</script>
</body>
</html>
