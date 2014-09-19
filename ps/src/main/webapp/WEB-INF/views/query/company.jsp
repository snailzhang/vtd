<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" /> 



<script type="text/javascript">
	var queryCompany = {};
	/**
		初始化数据
	 **/
	queryCompany.init = function() {
		//企业性质
		$('#companyProperty').combobox({
			url : 'parameter/property',
			valueField : 'id',
			textField : 'companyProperty',
		});

		//经济类型
		$('#companyEconomyType').combobox({
			url : 'parameter/economytype',
			valueField : 'id',
			textField : 'companyEconomyType',
		});
		//地区
		$('#area').combobox({
			url : 'parameter/getArea',
			valueField : 'code',
			textField : 'name',
		});

		
		//初始化输入元素默认值
		var element=$(".inputElement");
		for(var i=0;i<element.length;i++){
			$(element[i]).val("");
		}
	};

	/*
	 * 获取企业基本档案函数
	 */
	queryCompany.loadData = function(params) {
		esd.common.datagrid("#queryCompanyGrid", "query/company/list", "#queryCompany_boolbar", [ [ {
			field : 'companyArea',
			title : '所在地区',
			width : 100
		}, {
			field : 'companyCode',
			title : '档案编码',
			width : 80
		}, {
			field : 'companyTaxCode',
			title : '税务编码',
			width : 100
		}, {
			field : 'companyName',
			title : '企业名称',
			width : 300,
			formatter : function(value, row, index) {
			
				var c = '<a href="#" onclick="queryCompany.openViewCompany(\'' + row.id + '\')">' + value + '</a>';
				return c;
			}

		}, {
			field : 'companyProperty',
			title : '企业性质',
			width : '100'
		}, {
			field : 'companyEconomyType',
			title : '企业经济类型',
			width : '100'
		}, {
			field : 'companyType',
			title : '企业类型',
			width : '100'
		},{
			field : 'companyPhone',
			title : '联系方式',
			width : '100'
		} ] ], params);

	};

	/**
		组装查询参数
	 **/
	queryCompany.getParams = function() {
		var params = {};
		params.companyCode = $("#companyCode").val(); // 档案号码
		params.companyTaxCode = $("#companyTaxCode").val();// 税务编码
		params.companyOrganizationCode = $("#companyOrganizationCode").val();// 组织机构代码证号
		params.companyProperty = $("#companyProperty").combobox("getValue");// 公司性质 _
		params.companyEconomyType = $("#companyEconomyType").combobox("getValue");// 企业经济类型
		params.area = $("#area").combobox("getValue");// 公司所属地区
		params.companyEmpTotal_1 = $("#companyEmpTotal_1").val();// 员工总数
		params.companyEmpTotal_2 = $("#companyEmpTotal_2").val();
		params.companyName = $("#companyName").val(); // 企业名称
		params.companyAddress = $("#companyAddress").val();// 企业地址
		params.companyLegal = $("#companyLegal").val();//	企业法人
		params.companyHandicapTotal_1 = $("#companyHandicapTotal_1").val();// 残疾员工总数
		params.companyHandicapTotal_2 = $("#companyHandicapTotal_2").val();// 残疾员工总数
		return params;
	};

	/**
	 查询数据 并校验所有输入框
	 **/
	queryCompany.findData = function() {
		if (esd.common.validatebox()) {
			//重新根据参数加载数据
			$('#queryCompanyGrid').datagrid('load', queryCompany.getParams());
		};
	};
	/**
	查看企业信息框
	**/
	queryCompany.openViewCompany = function(id) {
		esd.common.defaultOpenWindow("查看企业信息",'company/view/' + id);
		
	};
	
	/**
	导出企业
	**/
	queryCompany.downloadSelected = function() {
		
		// 获取所有选中列
		var selection = $("#queryCompanyGrid").datagrid('getChecked');
	
		// 判断选择数目是否大于0
		if (selection.length == 0) {
			$.messager.alert('消息', '未选择任何数据。', 'error');
			
		} else {
			// 显示确认删除对话框
			$.messager.confirm('确认', '您确认想要导出' + selection.length + '记录吗？', function(r) {
				if (r) {
					// 组装参数
					var params = new Array();
					for ( var i = 0; i < selection.length; i++) {
						params.push(selection[i].id);
					}
					//发送导出请求
					
					$.ajax({
						url:'query/company/exportCompany',
						type:'post',
						data: {
								params : params
							},
						success:function(data){
						if(data!="null"){
				
							window.location.href=data;
						}else{
								$.messager.alert('消息', '单位信息导出错误。', 'error');
							
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
	queryCompany.downloadAll = function() {
		$.messager.confirm('确认', '您确认想要导出所有记录吗？', function(r) {
			if (r) {
				var params = [2147483647];
				//发送导出请求
				$.ajax({
					url:'query/company/exportCompany',
					type:'post',
					data: {
						params : params
					},
					success:function(data){
						if(data!="null"){
							window.location.href=data;
						}else{
							$.messager.alert('消息', '单位信息导出错误。', 'error');
						}
					},error:function(){
						$.messager.alert('消息', 'exportCompanyErrror。', 'error');
					}
				});
			}
		});
	};
	//组件解析完成
	$.parser.onComplete=function(){
	};
	
	$(function() {
		queryCompany.init();
		//加载单位档案数据
		queryCompany.loadData(queryCompany.getParams());
		

	});
</script>

<table id="queryCompanyGrid"></table>
<!-- 自定义菜单 -->
<div id="queryCompany_boolbar">
	<div class="paramsTab">
		<table>
			<tr>
				<td class="tipsText">档案号码:</td>
				<td><input type="text" id="companyCode" class="inputElement" /></td>
				<td class="tipsText">税务号码:</td>
				<td><input id="companyTaxCode" type="text" class="inputElement" /></td>
				

				<td class="tipsText">企业性质:</td>
				<td><input id="companyProperty" class="easyui-combobox" data-options="height:30,editable:false" /></td>
				<td class="tipsText">残疾职工数:</td>
				<td><input type="text" style="width: 40px" id="companyHandicapTotal_1" data-options="validType:['_number']" class="easyui-validatebox inputElement" /> - <input type="text"
					style="width: 40px" id="companyHandicapTotal_2" data-options="validType:['_number']" class="easyui-validatebox inputElement" />人</td>
			</tr>
			<tr>
				<td class="tipsText">经济类型:</td>
				<td><input id="companyEconomyType" class="easyui-combobox" data-options="height:30,editable:false" /></td>
				
								<td class="tipsText">法人代表:</td>
				<td><input id="companyLegal" type="text" class="inputElement" />
				</td>
								<td class="tipsText">企业人数:</td>
				<td><input type="text" style="width: 40px" id="companyEmpTotal_1" data-options="validType:['_number']" class="easyui-validatebox inputElement" />- <input type="text" style="width: 40px"
					id="companyEmpTotal_2" data-options="validType:['_number']" class="easyui-validatebox inputElement" />人</td>
			</tr>
			<tr>
				<td class="tipsText">企业名称:</td>
				<td colspan="3"><input class="longInputBox inputElement" id="companyName" /></td>
				<td class="tipsText">企业地址:</td>
				<td colspan="3"><input class="longInputBox inputElement" type="text" id="companyAddress" />
				</td>
			</tr>
			<tr>
				<td class="tipsText">地区:</td>
				<td><input id="area" class="easyui-combobox" data-options="height:30,editable:false" value="10230000" />
			</td>
			<td class="tipsText" >组织机构代码:</td>
				<td>
					<input type="text" id="companyOrganizationCode" class="inputElement" />
				</td>
			</tr>
		</table>
		<div class="findBut">
			<a href="#" onclick="queryCompany.findData()" class="easyui-linkbutton" iconCls="icon-search">查询</a> 
			<a href="javascript:queryCompany.init()" class="easyui-linkbutton" iconCls="icon-redo">重置</a> 
			<a href="javascript:queryCompany.downloadSelected()" class="easyui-linkbutton" iconCls="icon-ok">下载选中</a>
			<a href="javascript:queryCompany.downloadAll()" class="easyui-linkbutton" iconCls="icon-ok">下载所有数据</a>
		</div>
	</div>
</div>


