<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script type="text/javascript"> 
	parameter_list = {};

	parameter_list.list = function() {
		var params = {};
		params.year = $('#year').combobox('getValue');
		esd.common.datagrid("#parameter_list_grid", "${contextPath}/security/settings/yearAuditParameter/list", "#parameter_list_boolbar", [ [ {
			field : 'year',
			title : '年度',
			width : 200,
		}, {
			field : 'areaCode',
			title : '所属区域',
			width : 200
		}, {
			field : 'averageSalary',
			title : '计算基数',
			width : 200
		}, {
			field : 'putScale',
			title : '安置比例(%)',
			width : 200
		}, {
			field : 'payCloseDate',
			title : '支付截至日期',
			width : 200
		}, {
			field : 'action',
			title : '操作',
			width : 250,
			align : 'center',
			formatter : function(value, row, index) {
				var e = '<a href="#" onclick="parameter_list.updateParameter(' + row.id + ')">编辑</a> ';
				var d = '<a href="#" onclick="parameter_list.deleteParameter(' + row.id + ')">删除</a>';
				return e + d;
			}
		} ] ], params);

	};

	/*
		打开参数窗口
	 */
	parameter_list.addParameter = function() {
		esd.common.defaultOpenWindow("添加年审参数", "${contextPath}/security/settings/yearAuditParameter/add");
	};
	parameter_list.updateParameter = function(index) {
		esd.common.defaultOpenWindow("更新年审参数", "${contextPath}/security/settings/yearAuditParameter/edit/" + index);
	};

	parameter_list.deleteParameter = function(index) {
		var params = new Array();
		params.push(index);
		$.messager.confirm('确认对话框', '是否删除', function(r) {
			if (r == true) {
				$.ajax({
					url : "${contextPath}/security/settings/yearAuditParameter/delete",
					type : 'POST',
					data : {
						params : params
					},
					success : function(data) {
						if (data == true) {
							$.messager.alert('消息', '删除成功', 'info', function() {
								$("#parameter_list_grid").datagrid('reload');
							});
						} else {
							$.messager.alert('消息', '删除失败, 可能存在该年的审核数据.', 'info');
						}
					},
					dataType : "json",
					async : false
				});
			}
		});

	};
	$(function() {
		$('#year').combobox({
			url : 'parameter/getyears',
			valueField : 'id',
			textField : 'text'
		});
		parameter_list.list();
	});
</script>



<!-- 数据表格 -->
<table id="parameter_list_grid"></table>

<!-- 自定义菜单 -->
<div id="parameter_list_boolbar" data-options="fit:false,doSize:false" style="white-space: nowrap;">
	<div>
		<table border="0" width="100%">
			<tr>

				<td width="80" style="text-align: right;">年审时间:</td>
				<td width="150"><input id="year" class="easyui-combobox" value="${nowYear}" data-options="height:30,editable:false" />
				</td>
				<td><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="parameter_list.list()">查找</a>
				</td>
				<td style="text-align: right" ><a href="javascript:parameter_list.addParameter();" class="easyui-linkbutton" plain="true" iconCls="icon-add">添加年审参数</a></td>
			</tr>
		</table>
	</div>
</div>


<div id="parameterPanel"></div>


