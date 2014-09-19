<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Basic DataGrid - jQuery EasyUI Demo</title>

<link rel="stylesheet" type="text/css" href="/cs/js/themes/gray/easyui.css" />
<link rel="stylesheet" type="text/css" href="/cs/js/demo.css" />
<script type="text/javascript" src="/cs/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="/cs/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/cs/js/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript">
	$(function() {
		$('#asddsa').datagrid({
			// url:'../../CompanyBasicAction', 

			iconCls : 'icon-save',
			pageSize : 10,
			pageList : [ 10, 30, 50, 100 ],
			pagination : true, //是否显示分页栏 
			doSize : true,//在面板被创建的时候将重置大小和重新布局。
			//fit:true,	//面板大小将自适应父容器
			fitColumns : true,//是否显示横向滚动条，未生效
			nowrap : true,//如果为true，则在同一行中 。设置为true可以提高加载性能 
			idField : 'id',
			rownumbers : false,//显示行号
			striped : true,//奇偶行使用不同背景色
			frozenColumns : [ [ {
				field : 'ck',
				checkbox : true
			},

			] ],
			columns : [ [ {
				field : 'sNumber',
				title : '序号',
				width : 100
			}, {
				field : 'sCjrName',
				title : '姓名',
				width : 300
			}, {
				field : 'sCjrNumber',
				title : '残疾证号',
				width : 900
			}, {
				field : 'sGender',
				title : '性别',
				width : 250,
				align : 'center'
			}, {
				field : 'sAge',
				title : '年龄',
				width : 250,
				align : 'center'
			}, {
				field : 'sTel',
				title : '联系电话',
				width : 250,
				align : 'center'
			}, {
				field : 'sCjrCategories',
				title : '残疾类别',
				width : 250,
				align : 'center'
			}, {
				field : 'sCjrLevel',
				title : '残疾等级',
				width : 250,
				align : 'center'
			}, {
				field : 'yanz',
				title : '验证标志',
				width : 250,
				align : 'center',
				formatter : function(value, row, index) {
					if (row.editing) {
						var s = '<a href="#" onclick="saverow(' + index + ')">Save</a> ';
						var c = '<a href="#" onclick="cancelrow(' + index + ')">Cancel</a>';
						return s + c;
					} else {
						var e = '<a href="#" onclick="editrow(' + index + ')">编辑</a> ';
						var d = '<a href="#" onclick="deleterow(' + index + ')">删除</a>';
						return e + d;
					}
				}
			} ] ]

		
		});
		var staff_list = {
			total : 3,
			rows : [ {
				"sNumber" : "1",
				"sCjrName" : "张三",
				"sCjrNumber" : "2323011185151541",
				"sGender" : "男",
				"sAge" : "22",
				"sTel" : "18545457845",
				"sCjrCategories" : "肢体",
				"sCjrLevel" : "一级",
				"yanz" : "已验证"
			}, {
				"sNumber" : "2",
				"sCjrName" : "崔宝文",
				"sCjrNumber" : "2323011112351541",
				"sGender" : "男",
				"sAge" : "32",
				"sTel" : "13445457845",
				"sCjrCategories" : "肢体",
				"sCjrLevel" : "二级",
				"yanz" : "已验证"
			}, {
				"sNumber" : "3",
				"sCjrName" : "李天吧",
				"sCjrNumber" : "2323011185151541",
				"sGender" : "女",
				"sAge" : "22",
				"sTel" : "18345457845",
				"sCjrCategories" : "精神",
				"sCjrLevel" : "一级",
				"yanz" : "已验证"
			}, ]
		};
		//加载前台数据		
		$("#staff_dataGrid").datagrid("loadData", staff_list);



	});

	
</script>

</head>
<body class="easyui-layout" region="center"   border="false"  doSize ="true"  fit="true" >

	<!-- 上部分 -->
	<div data-options="region:'north',border:false" style="height:60px;background:#B3DFDA;padding:10px">
		top
		<div class="tree-node-selected">123</div>

	</div>

	<!-- 左侧菜单 -->
	<div data-options="region:'west',split:true,title:'目录'" style="width:260px;padding:10px;">
		<!-- 主菜单 -->
		<ul id="main_menu"></ul>

	</div>


	<!-- 右侧主体部分 -->
	<div id="mainPanle" >
	
	
<!-- 数据表格 -->
<table id="asddsa"></table>




	
	
	
	
	
	
	
	
	</div>
</body>
</html>