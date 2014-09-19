$(document).ready(function() {
	view.tabs.addIndexTab();
	view.bindMenu();
});
/**
 * 视图类
 */
var view = {};
view.tabs = {};
/**
 * 绑定菜单事件
 */
view.bindMenu = function() {
	// 1.创建树形菜单
	$('#main_menu').tree({
		// 绑定单击事件
		onSelect : function(e, node) {
			if(e.text=='退出'){
				var host = document.location.host;
			//	$.messager.alert('提示信息',host);
				window.top.location.href='http://'+host+'/cs/quit';
				return;
			}
			if (e.attributes != undefined) {
				// 页面单击，转到实体页面
				var _url = e.attributes.url;
				var _title = e.text;
				view.tabs.update(_title, _url, false);
			}
		}
	});
};

/**
 * 更新tab
 */
view.tabs.update = function(title, url, closable) {
	var tab = $('#main').tabs('getSelected');
	$('#main').tabs('update', {
		tab : tab,
		options : {
			title : title,
			href : url,
			closable : closable
		},
	});
};

/**
 * 添加首页
 */
view.tabs.addIndexTab = function() {
	$('#main').tabs('add', {
		title : "帮助",
		href : "/cs/security/main",
		closable : false
	});
};
