/*
 * 
 * 基本档案脚本
 */

var basicFile = {}; 

/**
 * 组装查询参数
 */
basicFile.getParams = function() {
	var params = {};
	params.companyProperty = $("#companyProperty").val(); // 当前企业类型
	params.companyCode = $("#companyCode").val();// 档案号
	params.companyTaxCode = $("#companyTaxCode").val();// 税务编码
	params.companyName = $("#companyName").val();// 企业名称
	return params;
};
/**
 * 查询数据 并校验所有输入框
 */
basicFile.findData = function() {
	if (esd.common.validatebox()) {
		// 重新根据参数加载数据
		$('#company_grid').datagrid('reload', basicFile.getParams());
	};
};

basicFile.toolbar = [ {
	
	handler : function() {
		alert('编辑按钮');
	}
}, '-', {
	iconCls : 'icon-edit',
	handler : function() {
		alert('编辑按钮');
	}
}, '-', {
	iconCls : 'icon-help',
	handler : function() {
		alert('帮助按钮');
	}
} ];

/**
 * 获取企业基本档案函数
 */
basicFile.loadData = function(params) {
	esd.common.datagrid("#company_grid", "query/company/list", '#company_boolbar', [ [ {
		field : 'companyCode',
		title : '档案编码',
		width : 230
	}, {
		field : 'companyTaxCode',
		title : '税务编码',
		width : 230
	}, {
		field : 'companyName',
		title : '企业名称',
		width : 800,
		formatter : function(value, row, index) {
			var c = '<a href="#" onclick="basicFile.openViewCompany(\'' + row.id + '\')">' + value + '</a>';
			return c;
		}
	}, {
		field : 'action',
		title : '操作',
		width : 250,
		align : 'center',
		formatter : function(value, row, index) {
			var e = '<a  href="#" onclick="basicFile.openEditCompany(\'' + row.id + '\')">编辑</a> ';
			var d = '<a href="#" onclick="basicFile.openDeleteCompany(\'' + row.id + '\')">删除</a> ';
		
			return e + d;
		}
	} ] ], params);

};

/**
 * 打开增加企业信息框
 */
basicFile.openAddCompany = function(companyProperty) {
	esd.common.defaultOpenWindow("增加企业信息", 'company/add/'+companyProperty);
};

/**
 * 增加企业信息
 */
basicFile.addCompany = function() {
	// 校验
	if (parseInt($("#companyEmpTotal").val()) < parseInt($("#companyHandicapTotal").val())) {
		$.messager.alert('消息', '残疾职工人数不能大于员工总人数!', 'error');
		return;
	}

	esd.common.syncPostSubmit("#addComapnyForm", function(data) {
		if (data == true) {
			$.messager.alert('消息', '单位信息增加成功!', 'info');
			esd.common.defaultOpenWindowClose();
			$('#company_grid').datagrid("reload");
		} else {
		
			$.messager.alert('消息', '单位信息增加失败!', 'error');
		}
	});
};
/**
 * 查看企业信息框
 */
basicFile.openViewCompany = function(id) {
	esd.common.defaultOpenWindow("查看企业信息",'company/view/' + id);
};
/**
 * 打开编辑企业信息框
 */
basicFile.openEditCompany = function(id) {
	esd.common.defaultOpenWindow("编辑企业信息", 'company/edit/' + id);
};

/**
 * 编辑企业信息
 */
basicFile.editCompany = function() {
	esd.common.syncPostSubmit("#editComapnyForm", function(data) {
		if (data == true) {
			$.messager.alert('消息', '单位信息编辑成功。', 'info');
			esd.common.defaultOpenWindowClose();
			$('#company_grid').datagrid("reload");
		} else {
			$.messager.alert('消息', '单位信息编辑失败。', 'error');
		
		}
	});
};
/**
 * 显示企业残疾人职工页面
 */
basicFile.openCompanyStaff = function(id) {
	var year=$("#year").combobox("getValue");//
	esd.common.defaultOpenWindow("企业残疾职工列表", 'worker/list/' + id+'/'+year);
};
/**
 * 单条删除企业数据
 */
basicFile.openDeleteCompany = function(id) {
	// 显示确认删除对话框
	$.messager.confirm('确认', '您确认想要删除该记录吗？', function(r) {
		if (r) {
			// 组装参数
			var params = new Array();
			params.push(id);
			// 调用删除函数
			basicFile.deleteCompany(params);
		}
	});

};
/*
 * 批量删除
 */
basicFile.openBatchDeleteCompany = function() {
	// 获取所有选中列
	var selection = $("#company_grid").datagrid('getChecked');
	// 判断选择数目是否大于0
	if (selection.length == 0) {
		
		$.messager.alert('消息', '未选择任何数据。', 'error');
	} else {
		// 显示确认删除对话框
		$.messager.confirm('确认', '您确认想要删除' + selection.length + '记录吗？', function(r) {
			if (r) {
				// 组装参数
				var params = new Array();
				for ( var i = 0; i < selection.length; i++) {
					params.push(selection[i].id);
				}
				// 调用删除函数
				basicFile.deleteCompany(params);
			}
		});
	}
};
/*
 * 删除企业
 */
basicFile.deleteCompany = function(params, type) {
	// 删除请求
	$.ajax({
		url : 'company/delete',
		data : {
			params : params
		},
		type : 'post',
		success : function(data) {
			if (data == true) {
				// 刷新数据列表
				$('#company_grid').datagrid('reload');
				$("#company_grid").datagrid('clearSelections');
			
				$.messager.alert('消息', '删除成功。', 'ok');
			} else {
				$.messager.alert('消息', '企业信息删除失败。', 'error');
				
			}
		},
		error : function() {
			$.messager.alert('消息', '删除企业信息时发生错误。', 'error');
			
		}
	});
};
