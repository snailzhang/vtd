<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="shortcut icon" href="${contextPath }/images/logo.ico" />
<title>残疾人就业保障金</title>
<link rel="stylesheet" type="text/css" href="${contextPath}/js/lib/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/js/lib/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/cs.css" />
<script type="text/javascript" src="${contextPath}/js/lib/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/lib/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${contextPath}/js/lib/ajaxupload.3.6.js"></script>
<script type="text/javascript" src="${contextPath}/js/common.js"></script>
<script type="text/javascript" src="${contextPath}/js/menu.js"></script>
</head>
<body class="easyui-layout">
	<!-- 上部分 -->
	<!-- 左侧菜单 -->
	<div data-options="region:'west',split:true,title:'目录'" style="width:260px;padding:10px;">
		<!-- 主菜单 -->
		<ul id="main_menu" data-options="url:'/cs/security/menus'"></ul>
	</div>
	<!-- 右侧主体部分 -->
	<div id="super" data-options="region:'center',border:true,title:'残疾人就业保障金'">
		<div id="main" class="easyui-tabs" data-options="tools:'#tab-tools',fit:true,border:false," ></div>
	</div>
	<!-- 底部 -->
	<div data-options="region:'south',border:true" style="height:40px;text-align:center; padding:10px;">
		<div style="font-size: 12px;">© 2013 hrbesd.com, Inc. 哈尔滨亿时代数码科技开发有限公司, All rights reserved. 黑ICP备10202513号</div>
	</div>

	<div id="tab-tools" style="border-top: none; border-right: none;">
		<span>登录用户&nbsp;:&nbsp;${sessionScope.userName}</span>
		 <a href="javascript:window.parent.location.href = '/cs/security/index'" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-help'">帮助</a>  
		 <a href="javascript:window.parent.location.href = '/cs/quit'" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-exit'" >退出</a>
	</div>
</body>
</html>