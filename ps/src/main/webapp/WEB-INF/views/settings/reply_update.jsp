<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script type="text/javascript"> 
	var reply_detail = {};
	reply_detail.submit = function() {
		esd.common.syncPostSubmit("#form", function(data) {
			if (data == true) {
				$.messager.alert('消息', '更新成功', 'info', function() {
					esd.common.defaultOpenWindowClose();
					$("#reply_list_grid").datagrid('reload');
				});
			} else {
				$.messager.alert('消息', '更新失败', 'info');
			}
		});
	};

	reply_detail.back = function() {
		esd.common.defaultOpenWindowClose();
	};
</script>
<div>
	<div style="padding:15px 15px 15px 15px;">
		<form id="form" action="${contextPath }/security/settings/reply/update" method="post">
			<table class="parameter_tab">
				<tr>
					<td style="width: 50px;">标题:</td>
					<td style="width: 200px;" colspan="3">
					<input class="easyui-validatebox" required="true" name="title" id="title" type="text" value="${entity.title }" /> 
					<input class="easyui-validatebox" name="version" type="hidden" value="${entity.version }" />
					<input class="easyui-validatebox" name="id" type="hidden" value="${entity.id }" />
					</td>
				</tr>
				<tr>
					<td>内容:</td>
					<td colspan="3"><textarea required="true" name="content" id="content" rows="5" cols="70">${entity.content }</textarea></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td colspan="3"><a href="javascript:reply_detail.submit();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>  <a href="javascript:reply_detail.back();" class="easyui-linkbutton" iconCls="icon-back">返回</a></td>
				</tr>
			</table>
		</form>
	</div>
</div>
