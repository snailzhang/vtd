<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<script type="text/javascript">
	var printWorkerList = {};
	printWorkerList.loadData = function() {
		params = {};
		params.companyId = $("#companyId").val();//单位id
		params.year = $("#year").val();//年份

		$.ajax({
			url : "query/worker/company_worker_list",
			type : "post",
			data : params,
			success : function(data) {
				if (data == undefined) {
					return;
				}
				$("#printWorkerTab tbody").empty();
				for ( var i = 0; i < data.rows.length; i++) {

					$("#printWorkerTab tbody").append(
							"<tr><td>" + data.rows[i].workerName + "</td><td>" + data.rows[i].workerGender + "</td><td>" + data.rows[i].workerHandicapCode + "</td><td>" + data.rows[i].phone + "</td>"
									+ "<td>" + data.rows[i].workerHandicapType + "</td><td>" + data.rows[i].workerHandicapLevel + "</td><td>" + data.rows[i].currentJob + "</tr>");
				}

			},
			error : function() {
				alert("获取残疾数据错误");
			}

		});
	};

	$(function() {

		printWorkerList.loadData();

	});
</script>
<!--startprint-->
<style>
.print_tab, .printBut {
text-align: center;
}
</style>

<input type="hidden" value="${companyId}" id="companyId" />
<input type="hidden" value="${year}" id="year" />
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
<table id="printWorkerTab" cellspacing="0" cellpadding="0" border="" title="企业年审信息" class="print_tab">
	<thead>
		<tr>
			<td style="width: 90px">姓名</td>
			<td style="width: 40px">性别</td>
			<td style="width: 180px">残疾证号</td>
			<td class="" style="width: 100px">联系电话</td>
			<td class="" style="width: 100px">残疾类别</td>
			<td class=" " style="width: 100px">残疾等级</td>
			<td class="" style="width: 100px">现任岗位</td>
		</tr>
	</thead>
	<tbody>
	</tbody>
</table>
<!--endprint-->

<div class="printBut">
	<a href="javascript:esd.common.printWindow('printAudit','Preview')" class="easyui-linkbutton" iconCls="icon-search">打印预览</a> <a href="javascript:esd.common.printWindow('printAudit','Preview')"
		class="easyui-linkbutton" iconCls="icon-search">打印</a> <a href="javascript:esd.common.defaultOpenWindowClose()" class="easyui-linkbutton" iconCls="icon-undo">取消</a>
</div>
