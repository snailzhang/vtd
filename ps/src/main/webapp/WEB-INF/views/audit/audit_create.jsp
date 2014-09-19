<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />


<table border="0" width="100%" style="padding-top: 10px;"> 
	<tr>
		<td style="text-align: right;">年审时间:</td>
		<td><input id="createYear" class="easyui-combobox" data-options="height:30,width:100,editable:false" /></td>
	</tr>
	<tr>
		<td style="text-align: right;">档案编码:</td>
		<td><input id="findCompanyCode" class="easyui-combobox" data-options="width:400,height:30,editable:true,panelHeight:240" /></td>

	</tr>
	<tr>
		<td></td>
	</tr>
	<tr>
		<td></td>
		<td><a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" onclick="auditCreate.add();">添加记录</a></td>
	</tr>

</table>

<!-- 数据表格 -->
<script type="text/javascript">
	$.parser.onComplete = function(v) {
		$('#createYear').combobox({
			url : 'parameter/getyears',
			valueField : 'id',
			textField : 'text'
		});
		$('#findCompanyCode').combobox({
			valueField : 'id',
			textField : 'text',
			method : 'GET',
			onChange : function(newValue, oldValue) {
				var url = 'audits/create/' + newValue;
				$('#findCompanyCode').combobox('reload', url);
			}
		});
	};
	auditCreate = {};
	auditCreate.add = function() {
		var params = {};
		params.year = $('#createYear').combobox("getValue");
		params.companyCode = $('#findCompanyCode').combobox("getValue");
		$.ajax({
			url : 'audits/create/',
			type : 'POST',
			data : params,
			success : function(data) {
				if (data == true) {
					$.messager.alert('消息', '添加成功!', 'info', function(data) {
						esd.common.defaultOpenWindowClose();
					});
				} else {
					$.messager.alert('消息', '添加失败,审核数据可能已经存在了!', 'info');
				}
			},
			dataType : "json",
			async : false
		});
	};
</script>

