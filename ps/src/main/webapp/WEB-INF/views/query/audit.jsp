<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" /> 


<script type="text/javascript">
	var queryAudit = {};
	/**
		初始化数据
	 **/
	queryAudit.init = function() {
		//企业性质
		$('#companyProperty').combobox({
			url : 'parameter/property',
			valueField : 'id',
			textField : 'companyProperty'
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

		//年份
		$('#year').combobox({
			url : 'parameter/getyears',
			valueField : 'id',
			textField : 'text'
		});
		//流程状态
		$('#auditProcessStatus').combobox({
			url : 'parameter/getStatus',
			valueField : 'id',
			textField : 'auditProcessStatus'
		});

		//缴款人
		$('#paymentPerson').combobox({
			url : 'parameter/getPayer',
			valueField : 'id',
			textField : 'userName'
		});
		//是否免交
		$('#isExempt').combobox({
			data : [ {
				label : '',
				value : '请选择'
			}, {
				label : 'true',
				value : '是'
			}, {
				label : 'false',
				value : '否'
			} ],
			valueField : 'label',
			textField : 'value',
			editable : false,
			height : 30,
			width : 140,
			panelHeight : 80
		});
		//初始化输入元素默认值
		var element = $(".inputElement");
		for ( var i = 0; i < element.length; i++) {
			$(element[i]).val("");
		}
		//初始化超过X年未审企业
		$("#overYear").val("0");

	};
	queryAudit.openAudit = function(index) {
		esd.common.defaultOpenWindowEx("年审查看", 920, 600,
				"${contextPath}/security/audits/edit/" + index+"/3");
	};
	/*
	 * 获取企业基本档案函数
	 */
	queryAudit.loadData = function(params) {
		esd.common
				.datagrid(
						"#queryAuditGrid",
						"query/audit/list",
						"#queryAuditBoolbar",
						[ [
								{
									field : 'companyCode',
									title : '档案编码',
									width : 80
								},
								{
									field : 'companyTaxCode',
									title : '税务编码',
									width : 150
								},
								{
									field : 'companyId',
									hidden : true
								},
								{
									field : 'companyName',
									title : '企业名称',
									width : 300,
									formatter : function(value, row, index) {
										var c = '<a href="javascript:void(0);" onclick="queryAudit.openViewCompany('
												+ row.companyId
												+ ')">'
												+ value
												+ '</a>';
										return c;
									}
								},{
									field : 'companyLegal',
									title : '企业法人',
									width : 100
								},{
									field : 'companyAddress',
									title : '地址',
									width : 250
								},
								{
									field : 'companyPhone',
									title : '联系电话',
									width : 110
								},
								{
									field : 'auditProcessStatusId',
									hidden : true
								},
								{
									field : 'action',
									title : '操作',
									width : 80,
									align : 'center',
									formatter : function(value, row, index) {
										var v = '<a href="#" onclick="queryAudit.openAudit('
												+ row.id + ')">查看</a>';
										return v;
									}
								} ] ], params);

	};

	/**
		组装查询参数
	 **/
	queryAudit.getParams = function() {
		var params = {};
		params.year = $("#year").combobox("getValue"); // 年度
		params.companyCode = $("#companyCode").val(); // 档案号码
		params.companyTaxCode = $("#companyTaxCode").val();// 税务编码
		params.companyOrganizationCode = $("#companyOrganizationCode").val();// 组织机构代码证号
		params.companyProperty = $("#companyProperty").combobox("getValue");// 公司性质 _
		params.companyEconomyType = $("#companyEconomyType").combobox(
				"getValue");// 企业经济类型
		params.area = $("#area").combobox("getValue");// 公司所属地区
		params.companyEmpTotal_1 = $("#companyEmpTotal_1").val();// 员工总数
		params.companyEmpTotal_2 = $("#companyEmpTotal_2").val();
		params.companyName = $("#companyName").val(); // 企业名称
		params.companyAddress = $("#companyAddress").val();// 企业地址
		params.companyLegal = $("#companyLegal").val();//	企业法人

		params.auditProcessStatus = $("#auditProcessStatus").combobox(
				"getValue");// 流程状态
		params.paymentPerson = $("#paymentPerson").combobox("getValue");// 缴款人

		params.overYear = $("#overYear").val(); // 超过指定年度未初审的企业
		params.isExempt = $("#isExempt").combobox("getValue");// 是否免交
		return params;
	};

	/**
	 查询数据 并校验所有输入框
	 **/
	queryAudit.findData = function() {
		if (esd.common.validatebox("#queryAuditParams")) {
			//重新根据参数加载数据
			$('#queryAuditGrid').datagrid('load', queryAudit.getParams());
		}
		;
	};

	/**
		下载企业年审缴款记录
	 **/
	queryAudit.download = function() {
		//发送导出请求
		$.ajax({
			url : '${contextPath}/security/payment/download',
			type : 'post',
			success : function(data) {
				if (data != null) {
					window.location.href = data;
				} else {
					$.messager.alert('消息', '企业年审缴款信息导出出错错误1。', 'error');
				}
			},
			error : function() {
				$.messager.alert('消息', '企业年审缴款信息导出出错错误。', 'error');

			}
		});
	};
	/**
	查看企业信息框
	 **/
	queryAudit.openViewCompany = function(id) {
		esd.common.defaultOpenWindow("查看企业信息", 'company/view/' + id);
	};

	//组件解析完成
	$.parser.onComplete = function() {
		//加载单位档案数据
		queryAudit.loadData(queryAudit.getParams());
	};
	$(function() {
		queryAudit.init();

	});
</script>



<!-- 数据表格 -->
<table id="queryAuditGrid"></table>

<!-- 自定义菜单 -->
<div id="queryAuditBoolbar">
	<div class="paramsTab">
		<table id="queryAuditParams">
			<tr>
				<td class="tipsText">年度:</td>
				<td><input id="year" class="easyui-combobox" value="${nowYear}"
					data-options="height:30,editable:false" />
				</td>
				<td class="tipsText">档案号码:</td>
				<td><input type="text" id="companyCode" class="inputElement" />
				</td>
				<td class="tipsText">税务号码:</td>
				<td class="tipsText"><input id="companyTaxCode" type="text"
					class="inputElement" />
				</td>
				<td class="tipsText">组织机构代码:</td>
				<td><input type="text" id="companyOrganizationCode"
					class="inputElement" /></td>

			</tr>
			<tr>
				<td class="tipsText">企业性质:</td>
				<td><input id="companyProperty" class="easyui-combobox"
					data-options="height:30,editable:false" />
				</td>
				<td class="tipsText">经济类型:</td>
				<td><input id="companyEconomyType" class="easyui-combobox"
					data-options="height:30,editable:false" />
				</td>
				<td class="tipsText">地区:</td>
				<td><input id="area" class="easyui-combobox"
					data-options="height:30,editable:false" value="10230000" />
				</td>
				<td class="tipsText">企业人数:</td>
				<td><input type="text" style="width: 40px"
					id="companyEmpTotal_1" data-options="validType:['_number']"
					class="easyui-validatebox inputElement" />-<input type="text"
					style="width: 40px" id="companyEmpTotal_2"
					data-options="validType:['_number']"
					class="easyui-validatebox inputElement" />人</td>
			</tr>
			<tr>
				<td class="tipsText">企业名称:</td>
				<td colspan="3"><input class="longtext inputElement"
					id="companyName" />
				</td>
				<td class="tipsText">企业地址:</td>
				<td colspan="3"><input id="companyAddress"
					class="longtext inputElement" type="text" /></td>

			</tr>
			<tr>
				<td class="tipsText">法人代表:</td>
				<td><input id="companyLegal" type="text" class=" inputElement" />
				</td>
				<td class="tipsText">缴款人:</td>
				<td><input type="text" id="paymentPerson"
					class="easyui-combobox" data-options="height:30,editable:false" />
				</td>
				<td class="tipsText">超过:</td>
				<td><input id="overYear" type="text" value="0"
					style="width: 60px" data-options="validType:['_number']"
					class="easyui-validatebox" /> 年未初审</td>

				<td class="tipsText">是否免交:</td>
				<td><input type="text" id="isExempt" /></td>
			</tr>
			<tr>
				<td class="tipsText">流程状态:</td>
				<td><input id="auditProcessStatus" class="easyui-combobox"
					data-options="height:30,editable:false" />
				</td>

			</tr>
		</table>
		<div class="findBut">
			<a href="#" onclick="queryAudit.findData()" class="easyui-linkbutton" iconCls="icon-search">查询</a> 
			<a href="javascript:queryAudit.init()" class="easyui-linkbutton" iconCls="icon-redo">重置</a> 
			<a href="javascript:queryAudit.download()" class="easyui-linkbutton" iconCls="icon-ok">下载年审缴款记录</a>
		</div>
	</div>
</div>

<div id="viewCompanyPanel"></div>
