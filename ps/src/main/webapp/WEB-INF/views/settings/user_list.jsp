<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<script type="text/javascript"> 
	payment = {};
	payment.getParams = function() {
		var params = {};
		params.userName = $('#userName').val();
		params.userRealName = $('#userRealName').val();
		params.userMobile = $('#userMobile').val();
		return params;
	};

	user_list = {};
	user_list.loadData = function() {

		var params = payment.getParams();
		esd.common.datagrid("#user_list_grid", "${contextPath}/security/settings/user/list", "#user_list_boolbar", [ [ {
			field : 'userName',
			title : '用户名',
			width : 200
		}, {
			field : 'userRealName',
			title : '真实姓名',
			width : 200
		}, {
			field : 'userMobile',
			title : '手机',
			width : 200
		}, {
			field : 'userGroup',
			title : '用户组',
			width : 200
		}, {
			field : 'userStatus',
			title : '状态',
			width : 200,
			formatter : function(value, row, index) {
				if (value == true) {
					return '正常';
				} else {
					return '停用';
				}
			}
		}, {
			field : 'action',
			title : '操作',
			width : 250,
			align : 'center',
			formatter : function(value, row, index) {
				var e = '<a href="javascript:user_list.editWindow(' + row.id + ')">编辑</a> ';
				var d = '<a href="javascript:user_list.deleteUser(' + row.id + ')">删除</a> ';
				if(row.userName == 'admin'){
					d = '';
				}				
				var u = '<c:if test="${userGroupId == 1}"><a href="javascript:user_list.changePwd(' + row.id + ')">更改密码</a></c:if>';	
				return e + d + u;
			}
		} ] ], params);

	};
	user_list.addWindow = function() {
		esd.common.defaultOpenWindowEx("添加用户", 750, 550, "${contextPath}/security/settings/user/add");
	};
	user_list.editWindow = function(index) {
		esd.common.defaultOpenWindowEx("编辑用户", 750, 550, "${contextPath}/security/settings/user/edit/" + index);
	};
	user_list.changePwd = function(index) {
		esd.common.defaultOpenWindowEx("修改密码", 400, 250, "${contextPath}/security/settings/user/change/" + index);
	};
	user_list.deleteUser = function(index) {
		var params = new Array();
		params.push(index);
		$.messager.confirm('确认对话框', '是否删除', function(r) {
			if (r == true) {
				$.ajax({
					url : "${contextPath}/security/settings/user/delete",
					type : 'POST',
					data : {
						params : params
					},
					success : function(data) {
						if (data == true) {
							$.messager.alert('消息', '删除成功', 'info', function() {
								$("#user_list_grid").datagrid('reload');
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
	$(function() {
		user_list.loadData();
	});
</script>




<!-- 数据表格 -->
<table id="user_list_grid"></table>

<!-- 自定义菜单 -->
<div id="user_list_boolbar" style="white-space: nowrap;">
	<div style="text-align: right;">
		<a href="javascript:user_list.addWindow();" class="easyui-linkbutton" plain="true" iconCls="icon-add">添加用户</a>
	</div>
	<div>
		<table width="100%" border="0">
			<tr>
				<td width="80" style="text-align: right;">用户名:</td>
				<td width="150"><input id="userName" type="text" />
				</td>
				<td width="80" style="text-align: right;">真实姓名:</td>
				<td width="150"><input id="userRealName" type="text" />
				</td>
				<td width="80" style="text-align: right;">手机:</td>
				<td width="150"><input type="text" id="userMobile" />
				</td>
				<td><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="user_list.loadData()">查找</a>
				</td>
			</tr>
		</table>
	</div>
</div>

<div id="userPanel"></div>