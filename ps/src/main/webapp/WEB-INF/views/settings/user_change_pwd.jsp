<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script type="text/javascript"> 
	var user_change_pwd = {};
	user_change_pwd.submit = function() {
		esd.common.syncPostSubmit("#form", function(data) {
			if (data == true) {
				$.messager.alert('消息', '修改密码成功', 'info', function() {
					esd.common.defaultOpenWindowClose();
					$("#user_list_grid").datagrid('reload');
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
</script>
<div>
	<div style="padding:15px 15px 15px 15px;">
		<form id="form" action="${contextPath }/security/settings/user/change" method="post">
			<input name="id" type="hidden" value="${entity.id}" />
			<table class="parameter_tab">
				<tr>
					<td width="100">用户名:</td>
					<td>${entity.userName}</td>
				</tr>

				<tr>
					<td>新密码:</td>
					<td><input class="easyui-validatebox" required="true" name="userPassword" type="password" /></td>
				</tr>
				<tr>
					<td>确认密码:</td>
					<td><input class="easyui-validatebox" name="confirm" type="password" required="true" validType="equals['#userPassword']" /></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td></td>
					<td colspan="3">
						<a href="javascript:user_change_pwd.submit();" class="easyui-linkbutton" iconCls="icon-ok">修改密码</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
