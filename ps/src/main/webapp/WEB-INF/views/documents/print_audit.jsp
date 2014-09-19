<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	var printAudit = {}; 
	printAudit.init = function() {

	};
	


	$(function() {
		
	});

</script>
<input type="hidden" value="${companyId}" />
<input type="hidden" value="${year}" />
<!--startprint-->
<style>

.print_tab ,.printBut{
	margin: auto;
	width: 800px;
	text-align: center;
}
.print_outline {
width: 100px;
}
</style>

	<table title="企业年审信息" class="print_tab" >
		<tr>
			<td colspan="4">
				<h2 style="text-align: center;">按比例安排残疾人就业情况表</h2>
			</td>
		</tr>
		<tr>
			<td style="text-align: left;" class="print_outline">组织机关代码证：${company.companyOrganizationCode}</td>
			<td class="print_outline" style="text-align: right;width: 150px">税务编码：${company.taxCode}</td>
		</tr>
		<tr>
			<td style="text-align: left;" class="print_outline">企业名称(盖章):${company.companyName}</td>
			<td class="print_outline" style="text-align: right;width: 150px">年度:${year}年</td>
		</tr>
	</table>
	
	<!-- 企业基本情况表 -->
	<table cellspacing="0" cellpadding="0" border="1" title="企业年审信息" class="print_tab">
		<tr>
			<td class="print_outline" rowspan="3">企业基本情况</td>
			<td>法人代表</td>
			<td class="print_content">${company.legal}</td>
			<td class="">联系人</td>
			<td class="print_content">${company.contactPerson}</td>
			<td class="">联系电话</td>
			<td class="print_content">${company.companyPhone}</td>
		</tr>
		<tr>
			<td class="">企业地址</td>
			<td colspan="5" class="print_content" align="left">${company.address}</td>
		</tr>
		<tr>
			<td class="">开户银行</td>
			<td colspan="2" class="print_content">${company.companyBank}</td>
			<td class="">账号</td>
			<td colspan="2" class="print_content">${company.bankAccount}</td>
		</tr>
	</table>

	<!-- 在职职工情况 -->
	<table cellspacing="0" cellpadding="0" border=""  title="企业年审信息" class="print_tab" >
		<tr>
			<td rowspan="3" class="print_outline">在职职工情况</td>
			<td rowspan="2">职工总数(人)</td>

			<td colspan="4">在职残疾职工情况(人)</td>
			<td rowspan="2">在职残疾职工占在职职工%</td>

		</tr>
		<tr>
			<td>应安排数(人)</td>
			<td>已安排数(人)</td>
			<td>预定人数(人)</td>
			<td>已录入人数(人)</td>
		</tr>
		<tr>
			<td>${company.companyEmpTotal}</td>
			<td>${company.companyShouldTotal}</td>
			<td>${company.companyAlreadyTotal}</td>
			<td>${company.companyPredictTotal}</td>
			<td>${company.companyHandicapTotal}</td>
			<td>${company.companyHandicapTotal/company.companyEmpTotal}</td>
		</tr>
	</table>

	<!-- 保障金应缴数 -->
	<table cellspacing="0" cellpadding="0" border="" title="保障金应缴数" class="print_tab">
		<tr>
			<td class="print_outline">保障金应缴数</td>
			<td colspan="6" class="print_content" style="text-align: c">￥:0.0元 大写:</td>
		</tr>
		<tr>
			<td class="print_outline">备注</td>
			<td></td>
		</tr>

	</table>

	<table class="print_tab">
		<tr>
			<td class="print_outline" align="left">企业负责人:${company.legal}</td>
			<td class="print_outline" align="center">统计负责人:李成果</td>
			<td class="print_outline" align="right">报出日期:2013年07月</td>
		</tr>
	</table>
</div>
<!--endprint-->
<div class="printBut">
	<a href="javascript:esd.common.printWindow()"  class="easyui-linkbutton"  iconCls="icon-search" >打印预览</a>
	<a href="javascript:esd.common.printWindow()"  class="easyui-linkbutton"  iconCls="icon-search">打印</a> 
	<a href="javascript:esd.common.defaultOpenWindowClose()" class="easyui-linkbutton" 	iconCls="icon-undo">取消</a>
</div>
