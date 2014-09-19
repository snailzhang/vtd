<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<c:set var="contextPath" value="${pageContext.request.contextPath}" />


<script type="text/javascript">
	var printNotice = {};
	printNotice.init = function() {
		$.ajax({
			url : 'print/notice/' + $("#companyId").val() + '/' + $("#year").val(),
			type : 'post',
			success : function() {
				//alert("12332111");
			},
			error : function() {

			}

		});

	};
	$(function() {
		printNotice.init();
	});
</script>
<input type="hidden" value="${companyId}" id="companyId" />
<input type="hidden" value="${year}" id="year" />
<!--startprint-->
<style>
.printNotice{
	width: 400px;float: left;	
    margin: auto auto auto 30px;

	text-align: center;
}
</style>
<div class="printNotice">
	<div>
		<p>用人单位名称：黑龙江省机械科学研究院</p>
	</div>
	<div style="1px solid;border-top: 1px solid;border-left: 1px solid;border-right: 1px solid;">
		<span>残疾人劳动就业服务机构审核意见</span>
		<p>1．您的企业2012年度在职职工平均人数111人，其中残疾职工2.00人，已达到规定安排残疾人1.5%比例。</p>
		<p>2．您的企业 年度在职职工平均人数 人，其中残疾职工 人，少安排人，按上年度本地区职工年平均工资标准元计算，应缴纳残疾人就业保障金（大写） 请于 前缴款，逾期不缴纳按《按比例安排残疾人就业规定》(省政府第206号令)有关条款执行。</p>
		<span>初审员：陆丰</span> <span>初审员：陆丰</span>
	</div>
	<div style="border: 1px solid;">
		<p>实际缴纳残疾人就业保障金</p>
		<p>合计金额￥：</p>
		<p>人民币（大写）：</p>
		<p>收款单位（盖章）：黑龙江省机械科学研究院</p>
	</div>
	（一） 受检单位存档
</div>


<div class="printNotice">
	<div>
		<p>用人单位名称：黑龙江省机械科学研究院</p>
	</div>
	<div style="1px solid;border-top: 1px solid;border-left: 1px solid;border-right: 1px solid;">
		<span>残疾人劳动就业服务机构审核意见</span>
		<p>1．您的企业2012年度在职职工平均人数111人，其中残疾职工2.00人，已达到规定安排残疾人1.5%比例。</p>
		<p>2．您的企业 年度在职职工平均人数 人，其中残疾职工 人，少安排人，按上年度本地区职工年平均工资标准元计算，应缴纳残疾人就业保障金（大写） 请于 前缴款，逾期不缴纳按《按比例安排残疾人就业规定》(省政府第206号令)有关条款执行。</p>
		<span>初审员：陆丰</span> <span>初审员：陆丰</span>
	</div>
	<div style="border: 1px solid;">
		<p>实际缴纳残疾人就业保障金</p>
		<p>合计金额￥：</p>
		<p>人民币（大写）：</p>
		<p>收款单位（盖章）：黑龙江省机械科学研究院</p>
	</div>
	（一） 受检单位存档
</div>
<!--endprint-->
<div class="printBut">
	<a href="javascript:esd.common.printWindow('printAudit','Preview')" class="easyui-linkbutton" iconCls="icon-search">打印预览</a> <a href="javascript:esd.common.printWindow('printAudit','Preview')"
		class="easyui-linkbutton" iconCls="icon-search">打印</a> <a href="javascript:esd.common.defaultOpenWindowClose()" class="easyui-linkbutton" iconCls="icon-undo">取消</a>
</div>

