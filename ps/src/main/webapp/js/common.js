/**
 * 通用方法提高重用性
 */
var esd = {};
esd.common = {};
/**
 * 通过容器ID提交表单的参数集合 
 */
esd.common.getSubmitParams = function(id) {
	var str = "";
	$(id + " [name]").each(function() {
		var name = $(this).attr("name");
		if (name != undefined && name != null && name != "") {
			var val = $(this).val();
			val = val.replace(/[\r\n]+/g, '\\n');
			if (str == "") {
				str = "'" + name + "':'" + val + "'";
			} else {
				str = str + "," + "'" + name + "':'" + val + "'";
			}
		}
	});
	var e = "var params={" + str + "}";
	eval(e);
	return params;
};
/**
 * 同步POST 提交表单提交表单
 * 
 * @param url
 * @param id
 */
esd.common.syncPostSubmitEx = function(id, url, callback) {
	var b = $(id).form('validate');
	if (b == false) {
		return;
	}
	var params = esd.common.getSubmitParams(id);
	action = url;
	method = 'POST';
	$.ajax({
		url : action,
		type : method,
		data : params,
		success : function(data) {
			callback(data);
		},
		error : function() {
			alert("请求错误");
		},
		dataType : "json",
		async : false
	});
};
/**
 * 同步POST 提交表单提交表单
 * 
 * @param url
 * @param id
 */
esd.common.syncPostSubmit = function(id, callback) {
	var b = $(id).form('validate');
	if (b == false) {
		return;
	}
	var params = esd.common.getSubmitParams(id);
	action = $(id).attr('action');
	method = $(id).attr('method');
	$.ajax({
		url : action,
		type : method,
		data : params,
		success : function(data) {
			callback(data);
		},
		error : function() {
			alert("请求错误");
		},
		dataType : "json",
		async : false
	});
};

/**
 * 同步提交返回提交状态
 * 
 * @param id
 * @param url
 * @returns {Boolean}
 */
esd.common.syncPostSubmitBoolean = function(id, url) {
	var b = $(id).form('validate');
	if (b == false) {
		return;
	}
	var bSubmit = false;
	esd.common.syncPostSubmit(id, function(data) {
		bSubmit = data;
	});
	return bSubmit;
};
/**
 * 创建通用的列表控件
 */
esd.common.datagrid = function(grid, url, toolbar, columns) {
	esd.common.datagrid(grid, url, toolbar, columns, null);
};

/**
 * 创建通用的列表控件
 */
esd.common.datagrid = function(grid, url, toolbar, columns, params) {

	$(grid).datagrid({
		url : url,
		pageSize : 20,
		pageList : [ 20, 40, 60, 80 ],
		border : false,// 不显示边框
		pagination : true, // 是否显示分页栏
		doSize : true,// 在面板被创建的时候将重置大小和重新布局。
		fit : true, // 面板大小将自适应父容器
		fitColumns : true,// 是否显示横向滚动条，未生效
		nowrap : true,// 如果为true，则在同一行中 。设置为true可以提高加载性能
		idField : 'id',// 指明哪一个字段是标识字段。
		rownumbers : true,// 显示行号
		striped : true,// 奇偶行使用不同背景色
		checkOnSelect : false,// 单击不选择复选框
		selectOnCheck : false,
		singleSelect : true,
		queryParams : params,
		frozenColumns : [ [ {// 复选框
			field : 'id',
			checkbox : true
		}, ] ],
		columns : columns,
		toolbar : toolbar
	});
};

/**
 * 创建通用的列表控件
 */
esd.common.datagrid = function(grid, url, toolbar, columns, params, onLoadSuccess) {

	$(grid).datagrid({
		url : url,
		pageSize : 20,
		pageList : [ 20, 40, 60, 80 ],
		border : false,// 不显示边框
		pagination : true, // 是否显示分页栏
		doSize : true,// 在面板被创建的时候将重置大小和重新布局。
		fit : true, // 面板大小将自适应父容器
		fitColumns : true,// 是否显示横向滚动条，未生效
		nowrap : true,// 如果为true，则在同一行中 。设置为true可以提高加载性能
		idField : 'id',// 指明哪一个字段是标识字段。
		rownumbers : true,// 显示行号
		striped : true,// 奇偶行使用不同背景色
		checkOnSelect : false,// 单击不选择复选框
		selectOnCheck : false,
		singleSelect : true,
		queryParams : params,
		frozenColumns : [ [ {// 复选框
			field : 'id',
			checkbox : true
		}, ] ],
		columns : columns,
		toolbar : toolbar,
		onLoadSuccess : onLoadSuccess
	});
};
/**
 * 缴款的
 */
esd.common.datagridEx = function(grid, url, toolbar, columns) {
	$(grid).datagrid({
		url : url,
		pageSize : 20,
		pageList : [ 20, 40, 60, 1000 ],
		border : false,// 不显示边框
		pagination : false, // 是否显示分页栏
		doSize : true,// 在面板被创建的时候将重置大小和重新布局。
		fit : true, // 面板大小将自适应父容器
		fitColumns : true,// 是否显示横向滚动条，未生效
		nowrap : true,// 如果为true，则在同一行中 。设置为true可以提高加载性能
		rownumbers : true,// 显示行号
		striped : true,// 奇偶行使用不同背景色
		checkOnSelect : false,// 单击不选择复选框
		selectOnCheck : true,
		singleSelect : true,
		columns : columns,
		toolbar : toolbar
	});
};
/**
 * 通用弹出框
 */
esd.common.window = function(id, title, width, height, url, onCloseFun) {
	$(id).window({
		width : width,
		height : height,
		href : url,
		title : title,
		loadingMessage : '正在加载，请稍后。',
		collapsible : false,
		minimizable : false,
		maximizable : false,
		modal : true,
		onClose : function() {
			if (onCloseFun != null) {
				onCloseFun();
			}
			$(id).window("destroy");
		}
	});
};

/**
 * 自定义ID的通用的编辑弹出框
 */
esd.common.openWindow = function(id, title, width, height, url) {
	$("#main").append('<div id="' + id.substring(1) + '"></div>');
	esd.common.window(id, title, width, height, url, null);
};
/**
 * 自定义ID的通用的编辑弹出框 EX
 */
esd.common.openWindowEx = function(id, title, width, height, url, closeFun) {
	$("#main").append('<div id="' + id.substring(1) + '"></div>');
	esd.common.window(id, title, width, height, url, closeFun);
};

/**
 * 使用默认ID的通用弹出框
 */
esd.common.defaultOpenWindowEx = function(title, width, height, url) {
	$("#main").append('<div id="defaultWindow"></div>');
	esd.common.window('#defaultWindow', title, width, height, url, null);
};
/**
 * 使用默认ID 和宽高的弹出框
 */
esd.common.defaultOpenWindow = function(title, url) {
	esd.common.defaultOpenWindowEx(title, 900, 590, url);
};
/**
 * 使用默认ID 关闭
 */
esd.common.defaultOpenWindowClose = function() {
	$('#defaultWindow').window("close");
};

/**
 * 重写校验函数
 */
$.extend($.fn.validatebox.defaults.rules, {
	tel : {
		validator : function(value, param) {
			return value.length > 3;
		},
		message : '长度不符.'
	},
	_number : {
		validator : function(value, param) {
			var reg = new RegExp("^[0-9]*$");
			return reg.test(value);
		},
		message : '请输入数字'
	},

	// 输入指定长度
	_length : {
		validator : function(_3c2, _3c3) {
			var len = $.trim(_3c2).length;
			return len == _3c3[0];
		},
		message : "长度为 {0} 位"
	},

});

/**
 * 文本框验证
 */
esd.common.validatebox = function(id) {
	var Controls = $(id + " .easyui-validatebox");
	var b = true;
	Controls.each(function(i, n) {
		if ($(n).validatebox('isValid') == false) {
			b = false;
			return;
		}
	});
	return b;
};

/**
 * 打印 <!--startprint--> 开始部分 <!--endprint--> 结束部分
 */
esd.common.printWindow = function() {

	bdhtml = window.document.body.innerHTML;
	sprnstr = "<!--startprint-->";
	eprnstr = "<!--endprint-->";
	prnhtml = bdhtml.substr(bdhtml.indexOf(sprnstr) + 17);
	prnhtml = prnhtml.substring(0, prnhtml.indexOf(eprnstr));
	var newWindow = window.open();
	newWindow.window.document.body.innerHTML = prnhtml;
	newWindow.window.document.head.innerHTML = window.document.head.innerHTML;
	newWindow.window.print();

	// window.document.body.innerHTML=prnhtml;
	// window.print();

};
/**
 * 查看企业
 */
esd.common.viewCompany = function(id) {
	esd.common.defaultOpenWindow("查看企业信息", 'company/view/' + id);
};

/**
 * 回车查询事件
 */
window.onkeydown = function(event) {
	var e = event.srcElement;
	if (event.keyCode == 13) {
		// 获得当前激活面板对象
		var activityWin = $("#defaultWindow");
		var findBut;
		// 二级窗口
		if (activityWin.length > 0) {
			findBut = $("#defaultWindow .icon-search");
			// 如果二级窗口有搜索按钮，则调用搜索按钮单击事件
			if (findBut.length > 0) {
				$(findBut).parent().parent().click();
				return false;
			} else {
				// 如果没有搜索按钮，事件交给系统。
				return true;
			}
		}
		// 一级窗口
		else {
			findBut = $(".icon-search");
			if (findBut.length > 0) {
				$(findBut).parent().parent().click();
				return false;
			} else {
				// 如果没有搜索按钮，事件交给系统。
				return true;
			}
		}
	}
};
/**
 * 自定义没有关闭按钮的alert框
 * 适用于关闭当前默认打开的窗口
 * */
//重写的alert
esd.common.noCloseButtonDialog = function(title,msg){
	$('body').append('<div id="dd"></div>');
	$('#dd').dialog({
		title : title,
		width : 280,
		height : 120,
		closed : false,
		closable : false,
		cache : false,
		modal : true,
		content : '<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+msg,
		bodyCls : 'noCloseButtonDialogBody',
	/*	toolbar : [{
			text : '添加',
			iconCls : 'icon-add',
			handler : function(){
				alert('add!');
			}
		},{
			text : '提交',
			iconCls : 'icon-ok',
			handler : function(){
				alert('ok!');
			}
		}],	*/
		buttons : [{
			text : '确定',
			handler : function(){
				$('#dd').window('close');
				esd.common.defaultOpenWindowClose();
			}
		}]
	});
};

/**
 * 预定义字符串
 **/
esd.common.unknown = function(){
	return '未知';
};