<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" /> 

<script type="text/javascript">
	$(function() {

		esd.common.datagrid("#reply_list_grid", "${contextPath}/security/settings/reply/list", "#reply_list_boolbar", [ [ {
			field : 'createTime',
			title : '创建时间',
			width : 100
		}, {
			field : 'title',
			title : '标题名',
			width : 150
		}, {
			field : 'updateTime',
			title : '更新时间',
			width : 100
		}, {
			field : 'action',
			title : '操作',
			width : 250,
			align : 'center',
			formatter : function(value, row, index) {
				var e = '<a href="javascript:reply_list.editWindow(' + row.id + ')">修改</a> ';
				var d = '<a href="javascript:reply_list.deleteUser(' + row.id + ')">删除</a> ';
				return e + d;
			}
		} ] ]);
	});
	reply_list = {};
	reply_list.addWindow = function() {
		esd.common.defaultOpenWindowEx("添加复审意见", 600, 300, "${contextPath}/security/settings/reply/save");
	};
	reply_list.editWindow = function(index) {
		esd.common.defaultOpenWindowEx("编辑复审意见", 600, 300, "${contextPath}/security/settings/reply/update?id=" + index);
	};
	reply_list.deleteUser = function(index) {
		var params = new Array();
		params.push(index);
		$.messager.confirm('确认对话框', '是否删除', function(r) {
			if (r == true) {
				$.ajax({
					url : "${contextPath}/security/settings/reply/delete",
					type : 'POST',
					data : {
						params : params
					},
					success : function(data) {
						if (data == true) {
							$.messager.alert('消息', '删除成功', 'info', function() {
								$("#reply_list_grid").datagrid('reload');
							});
						} else {
							$.messager.alert('消息', '删除失败', 'info');
						}
					},
					dataType : "json",
					async : false
				});
			}
		});

	};
</script>




<!-- 数据表格 -->
<table id="reply_list_grid"></table>

<!-- 自定义菜单 -->
<div id="reply_list_boolbar" style="white-space: nowrap;">
	<div style="text-align: right;">
		<a href="javascript:reply_list.addWindow();" class="easyui-linkbutton" plain="true" iconCls="icon-add">新增复审意见</a> 
		<!-- <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-cancel">批量删除</a>	 -->
	</div>
</div>

<div id="replyPanel"></div>