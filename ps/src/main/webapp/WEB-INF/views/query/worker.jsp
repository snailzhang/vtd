<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" /> 

<script type="text/javascript">
	var queryWorker = {};
	/**
		初始化组件
	 **/
	queryWorker.init = function() {
		//残疾类型
		$('#workerFind_workerHandicapType').combobox({
			url : 'parameter/workerHandicapTypeService',
			valueField : 'id',
			textField : 'handicapType',
			editable:false
		});

		//残疾等级
		$('#workerFind_workerHandicapLevel').combobox({
			url : 'parameter/workerHandicapLevelService',
			valueField : 'id',
			editable:false,
			textField : 'handicapLevel'
		});

		$('#year').combobox({
			url : 'parameter/getyears',
			valueField : 'id',
			textField : 'text'
		});
	//初始化输入元素默认值
		var element=$(".inputElement");
		for(var i=0;i<element.length;i++){
			$(element[i]).val("");
		}
		
		//性别
		$('#workerFind_Gender').combobox({
			data : [ {
				label : '',
				value : '请选择'
			}, {
				label : '1',
				value : '男'
			}, {
				label : '0',
				value : '女'
			} ],
			valueField : 'label',
			textField : 'value',
			editable:false,
			height:30,
			width : 76,
			panelHeight:80
		});
	};
	/** 
	创建数据表格并初始化数据
	 **/
	queryWorker.loadData = function(params) {

		esd.common.datagrid("#queryWorker_grid", "query/worker/list", "#queryWorker_boolbar", [ [ {
			field : 'workerName',
			title : '姓名',
			width : 300
		}, {
			field : 'workerHandicapCode',
			title : '残疾证号',
			width : 600
		}, {
			field : 'workerGender',
			title : '性别',
			width : 250,
			align : 'center'
		}, {
			field : 'workerAge',
			title : '年龄',
			width : 250,
			align : 'center'
		}, {
			field : 'phone',
			title : '联系电话',
			width : 350,
			align : 'center'
		}, {
			field : 'workerHandicapType',
			title : '残疾类别',
			width : 250,
			align : 'center'
		}, {
			field : 'workerHandicapLevel',
			title : '残疾等级',
			width : 250,
			align : 'center'
		} ] ], params);

	};

	/**
		组装查询参数
	 **/
	queryWorker.getParams = function() {
		params = {};
		params.companyId = $("#companyId").val();//单位id
		params.workerName = $("#workerFind_workerName").val(); // //员工名
		params.workerHandicapCode = $("#workerFind_workerHandicapCode").val();// 残疾证号
		params.workerGender = $("#workerFind_Gender").combobox("getValue");// 性别
		params.currentJob = $("#workerFind_currentJob").val();// 现任岗位
		params.workerAge_1 = $("#workerFind_workerAge1").val();// 年龄1
		params.workerAge_2 = $("#workerFind_workerAge2").val();// 年龄2
		params.workerHandicapType = $("#workerFind_workerHandicapType").combobox("getValue");//残疾类型 
		params.workerHandicapLevel = $("#workerFind_workerHandicapLevel").combobox("getValue");// 残疾等级
		params.careerCard = $("#workerFind_careerCard").val();// 就业证号
		return params;
	};
	/**
	 查询数据 并校验所有输入框
	 **/
	queryWorker.findData = function() {
		if (esd.common.validatebox("#queryWorkerParams")) {
			//重新根据参数加载数据
			$('#queryWorker_grid').datagrid('load', queryWorker.getParams());
		}
	};
	//组件解析完成
	$.parser.onComplete=function(){
		//创建数据表格并初始化数据
		queryWorker.loadData(queryWorker.getParams());
	};
	
	$(function() {
		//初始化组件
		queryWorker.init();
		
	});
	
	/**
	*	导出选中的残疾职工
	*
	**/
	queryWorker.downloadSelected = function() {
		
		// 获取所有选中列
		var selection = $("#queryWorker_grid").datagrid('getChecked');
	
		// 判断选择数目是否大于0
		if (selection.length == 0) {
			$.messager.alert('消息', '未选择任何数据。', 'error');
			
		} else {
			// 显示确认删除对话框
			$.messager.confirm('确认', '您确认想要导出' + selection.length + '条残疾职工信息吗？', function(r) {
				if (r) {
					// 组装参数
					var params = new Array();
					for ( var i = 0; i < selection.length; i++) {
						params.push(selection[i].id);
					}
					//发送导出请求
					
					$.ajax({
						url:'query/worker/export',
						type:'post',
						data: {
								params : params
							},
						success:function(data){
						if(data!="null"){
				
							window.location.href=data;
						}else{
								$.messager.alert('消息', '残疾职工信息导出错误。', 'error');
							
						}
						},error:function(){
						$.messager.alert('消息', 'exportCompanyErrror。', 'error');
						
						}
					});
				}
			});
		}
	};
	
	/**
	*
	* 导出所有数据
	*/
	queryWorker.downloadAll = function() {
		$.messager.confirm('确认', '您确认想要导出所有残疾职工信息吗？', function(r) {
			if (r) {
				var params = [2147483647];
				//发送导出请求
				$.ajax({
					url:'query/worker/export',
					type:'post',
					data: {
						params : params
					},
					success:function(data){
						if(data!="null"){
							window.location.href=data;
						}else{
							$.messager.alert('消息', '残疾职工信息导出错误。', 'error');
						}
					},error:function(){
						$.messager.alert('消息', 'exportCompanyErrror。', 'error');
					}
				});
			}
		});
	};
	
</script>

<table id="queryWorker_grid"></table>
<!-- 自定义菜单 -->
<div id="queryWorker_boolbar">

	<div class="paramsTab">
		<table id="queryWorkerParams">
			<tr>
				<td class="tipsText">残疾证号:</td>
				<td><input type="text" id="workerFind_workerHandicapCode" class="inputElement long" /></td>
				<td class="tipsText">就业证号:</td>
				<td><input type="text" id="workerFind_careerCard" class="inputElement long"  /></td>
				<td class="tipsText">性别:</td>
				<td><input type="text" id="workerFind_Gender" /></td>
			</tr>

			<tr>

				<td class="tipsText">现任岗位:</td>
				<td><input type="text" id="workerFind_currentJob" class="inputElement" /></td>
				<td class="tipsText">年龄:</td>
				<td><input class="easyui-validatebox inputElement" style="width: 40px" id="workerFind_workerAge1" type="text" data-options="validType:['_number']" />-<input style="width: 40px" type="text"
					id="workerFind_workerAge2" data-options="validType:['_number']" class="easyui-validatebox inputElement" />岁</td>
				<td class="tipsText">姓名:</td>
				<td><input type="text" id="workerFind_workerName" class="inputElement" /></td>
			</tr>
			<tr>
				<td class="tipsText">残疾类别:</td>
				<td><input class="easyui-combobox" type="text" id="workerFind_workerHandicapType" data-options="height:30,panelHeight:240" />
				</td>
				<td class="tipsText">残疾等级:</td>
				<td><input class="easyui-combobox" type="text" id="workerFind_workerHandicapLevel" data-options="height:30,panelHeight:140" />
				</td>
			</tr>
		</table>
		<div class="findBut">
			<a href="#" onclick="queryWorker.init()" class="easyui-linkbutton" iconCls="icon-redo">重置</a> 
			<a href="#" onclick="queryWorker.findData()" class="easyui-linkbutton" iconCls="icon-search">查询</a>
			<a href="javascript:queryWorker.downloadSelected()" class="easyui-linkbutton" iconCls="icon-ok">下载选中</a>
			<a href="javascript:queryWorker.downloadAll()" class="easyui-linkbutton" iconCls="icon-ok">下载所有数据</a>
		</div>
	</div>
</div>
<div id="viewCompanyPanel"></div>
<!-- 数据表格 -->