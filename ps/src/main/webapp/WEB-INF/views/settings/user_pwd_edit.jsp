<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<style type="text/css">
	.toright{ 
		width:100px;
		text-align:right;
		padding-right:10px;
	}
</style>
<script type="text/javascript">
	var user_change_pwd = {};
	user_change_pwd.submit = function() {
		esd.common.syncPostSubmit("#form", function(data) {
			if (data == true) {
				$.messager.alert('消息', '修改密码成功', 'info',function(){
					$('#userPassword').val('');
					$('#confirmUserPassword').val('');
				});
			} else {
				$.messager.alert('消息', '修改密码失败', 'info');
			}
		});
	};
	$.extend($.fn.validatebox.defaults.rules, {
		equals : {
			validator : function(value, param) {
				return value == $('input[name=userPassword]').val();
			},
			message : '两次输入的密码不一致'
		}
	});

	$(function() {
	});
</script>



<!-- 自定义菜单 -->
<div style="margin-top:100px;margin-left:200px;">
	<form id="form" action="${contextPath }/security/settings/user/change" method="post">
		<table class="parameter_tab">
			<tr>
				<td class="toright">用户名:
					<input type="hidden" name="id" value="${userId }" />
				</td>
				<td>${userRealName}</td>
			</tr>

			<tr>
				<td class="toright">新密码:</td>
				<td><input class="easyui-validatebox" required="true" name="userPassword" id="userPassword" type="password" /></td>
			</tr>
			<tr>
				<td class="toright">确认密码:</td>
				<td><input class="easyui-validatebox" name="confirmUserPassword" id="confirmUserPassword" type="password" required="true" validType="equals['#userPassword']" /></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2" style="text-align:center;">
					<a href="javascript:user_change_pwd.submit();" class="easyui-linkbutton" iconCls="icon-ok">修改密码</a>
				</td>
			</tr>
		</table>
	</form>
</div>

