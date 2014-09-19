<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="now" class="java.util.Date" />
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<style>
 
/*表格部分*/
.company-examined {
	width: 900px;
	background-color: #E0ECFF;
	text-align: center;
}

#startaudit_tabs td {
	font-size: 12px;
	height: 28px;
}

#account td {
	font-size: 12px;
}

#startaudit_tabs input {
	font-size: 12px;
	/*height: 18px;*/
	line-height: 28px;
	margin: 0px;
	width: 100%;
	border: 0px;
	height: 28px;
}

#startaudit_tabs textarea {
	font-size: 12px;
	line-height: 28px;
	width: 100%;
	border: 0px;
}

#startaudit_tabs td {
	border: #95B8E7 1px solid;
}

#startaudit_tabs .combo {
	border-style: none;
}
</style>
<script type="text/javascript">
	//组件解析完成
	$.parser.onComplete = function() {
		//加载单位档案数据
	};
</script>
<form id="form" action="${contextPath }/security/audits/save" method="post" style="margin-top: 5px; margin-left: 2px;">
	<!-- 年审企业表格  第二部分 -->
	<div style="border: #95B8E7 1px solid; width: 900px;">
		<table class="company-examined" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="100">档案号码:</td>
				<td width="290" colspan="3"><input name="companyCode" class="bj_belu2 readonly" type="text" value="${entity.company.companyCode}" /> <input type="hidden" id="companyId" name="company.id"
					value="${entity.company.id}" /> <input type="hidden" name="year" value="${entity.year}" /> <input type="hidden" name="company.version" value="${entity.company.version}" />
				</td>
				<td>年审年度:</td>
				<td colspan="2"><input name="year" class="readonly" type="text" value="${entity.year}" />
				</td>
				<td>未审年度:</td>
				<td colspan="2"><input type="text" readonly="readonly" <c:if test="${unAudityearNum==0}">value="无" class="readonly"</c:if>
					<c:if test="${unAudityearNum>0}">value="${unAudityear}" class="error"</c:if> /></td>
			</tr>
			<tr>
				<td>企业名称:</td>
				<td colspan="3"><input name="company.companyName" type="text" value="${entity.company.companyName}" />
				</td>

				<td style="width:100px;">税务代码:</td>
				<td width="180" colspan="2"><input name="company.companyTaxCode" type="text" value="${entity.company.companyTaxCode}" />
				</td>
				<td width="100">年龄超标</td>
				<td colspan="2"><input type="text" readonly="readonly" value="${ageEx}" <c:if test="${ageEx>0}">class="error"</c:if> <c:if test="${ageEx==0}">class="readonly"</c:if> />
				</td>
			</tr>

			<tr>
				<td rowspan="4">单位基本情况</td>
				<td width="100">法人代表:</td>
				<td width="199" colspan="2"><input name="company.companyLegal" class="bj_belu3" type="text" value="${entity.company.companyLegal}" /></td>
				<td>组织机关代码证:</td>
				<td colspan="2"><input name="company.companyOrganizationCode" class="bj_belu3" type="text" value="${entity.company.companyOrganizationCode}" />
				</td>
				<td>企业性质:</td>
				<td colspan="2"><select style="font-size: 12px; width: 100px;height: 28px;" class="" name="company.companyProperty.id" data-options="width:106,height:30,editable:false">
						<c:forEach items="${companyPropertys}" var="item">
							<option value="${item.id}" title="${item.companyProperty}" <c:if test="${entity.company.companyProperty.id eq item.id}">selected="selected"</c:if>>${item.companyProperty }</option>
						</c:forEach>
				</select>
				</td>

			</tr>
			<tr>
				<td>联系人 :</td>
				<td colspan="2"><input name="company.companyContactPerson" class="bj_belu3" type="text" value="${entity.company.companyContactPerson}" />
				</td>
				<td>电话号码:</td>
				<td colspan="2"><input name="company.companyPhone" class="bj_belu3" type="text" value="${entity.company.companyPhone}" />
				</td>
				<td>经济类型:</td>
				<td colspan="2"><select style="font-size: 12px;width: 100px;height: 28px;" class="" name="company.companyEconomyType.id" data-options="width:106,height:30,editable:false">
						<c:forEach items="${companyEconomyTypes}" var="item">
							<option value="${item.id}" <c:if test="${entity.company.companyEconomyType.id eq item.id}">selected="selected"</c:if>>${item.companyEconomyType }</option>
						</c:forEach>
				</select>
				</td>

			</tr>
			<tr>
				<td>手机号码:</td>
				<td colspan="2"><input name="company.companyMobile" class="bj_belu3" value="${entity.company.companyMobile}" />
				</td>
				<td>企业地址:</td>
				<td colspan="5"><input name="company.companyAddress" value="${entity.company.companyAddress }" />
				</td>

			</tr>
			<tr>
				<td>开户银行:</td>
				<td colspan="2"><input name="company.companyBank" class="bj_belu3" value="${entity.company.companyBank}" />
				</td>
				<td>银行账号:</td>
				<td colspan="2"><input name="company.companyBankAccount" class="bj_belu3" value="${entity.company.companyBankAccount}" />
				</td>
				<td>邮政编码:</td>
				<td colspan="2"><input name="company.companyZipCode" value="${entity.company.companyZipCode}" />
				</td>

			</tr>
			<!-- 年审企业表格  第三部分(残疾人信息) -->
			<tr>
				<td rowspan="3" width="100">在职员工情况:</td>
				<td width="100">职员工总数:</td>
				<td colspan="4" width="200">职残疾职工情况</td>
				<td width="98">补缴金额:</td>
				<td width="91">未审年数:</td>
				<td>补审年度:</td>
			</tr>
			<tr>
				<td rowspan="2" style="vertical-align: bottom"><input name="companyEmpTotal" title="在职员工总数" id="zaiZhiYuanGongZongShu" type="text" value="${entity.companyEmpTotal }"
					class="easyui-numberbox readonly" style="border-top: #95B8E7 2px solid;" data-options="min:0" />
				</td>
				<td width="97">应按排数:</td>
				<td width="100">已安排数:</td>
				<td width="100">已录入数</td>
				<td width="98">预定人数</td>
				<td><input style="display: none;" class="readonly" value="点击查看明细" type="button" id="message" /></td>
				<td style="vertical-align: bottom" rowspan="2"><input name="unauditYears" id="weiShenNianShu" title="已补缴金额" class="readonly" style="border-top: #95B8E7 2px solid;" value="${unAudityearNum}" />
				</td>
				<td style="vertical-align: bottom" rowspan="2"><input name="supplementYear" title="审计年度" class="readonly" style="border-top: #95B8E7 2px solid;" value="${supplementYear}" />
				</td>

			</tr>
			<tr>

				<td><input type="text" class="text_short readonly" value="${entity.companyShouldTotal}" name="companyShouldTotal" id="yingAnPaiCanJiRen" title="应按排数" />
				</td>
				<td><input type="text" class="text_short readonly" value="${entity.companyAlreadyTotal}" name="companyAlreadyTotal" id="yiAnPaiCanJiRen" title="已按排数" />
				</td>
				<td><input id="yiLuRuCanJiRen" name="companyHandicapTotal" class="readonly" title="已录入残疾职工人数" value="${entity.companyHandicapTotal }" />
				</td>
				<td><input id="yuDingCanJiRen" name="companyPredictTotal" value="${entity.companyPredictTotal }" title="预定残疾职工人数" class="easyui-numberbox readonly" data-options="min:0" />
				</td>
				<td><input name="remainAmount" id="shangNianDuWeiJiaoBaoZhangJin" title="补缴金额" class="readonly" value="${entity.remainAmount}" />
				</td>
			</tr>

			<!-- 年审企业表格  第四部分(保障金额度) -->
			<tr>
				<td width="100" rowspan="2">保障金情况:</td>
				<td width="100">应缴金额:</td>
				<td><input id="yingJiaoJinE" type="text" name="amountPayable" class="readonly" value="${entity.amountPayable}" />
				</td>
				<td width="100">减缴金额:</td>
				<td width="100"><input id="jianJiaoJinE" type="text" class="readonly" name="reductionAmount" value="${entity.reductionAmount}" />
				</td>
				<td width="99">免滞纳金:</td>
				<td><select disabled="disabled" id="mianZhiNaJin" style="font-size: 12px; width: 100px;height: 28px;" class="" name="isDelayPay"
					data-options="width:100,panelHeight:80,height:30,editable:false">
						<option value="true" <c:if test="${entity.isDelayPay eq 'true'}">selected="selected"</c:if>>是</option>
						<option value="false" <c:if test="${entity.isDelayPay eq 'false'}">selected="selected"</c:if>>否</option>
				</select>
				</td>

				<td width="91">是否免交:</td>
				<td><select disabled="disabled" id="mianJiao" style="font-size: 12px; width: 100px;height: 28px;" class="" name="isExempt" data-options="width:100,panelHeight:80,height:30,editable:false">
						<option value="true" title="是" <c:if test="${entity.isExempt eq 'true'}">selected="selected"</c:if>>是</option>
						<option value="false" title="否" <c:if test="${entity.isExempt eq 'false'}">selected="selected"</c:if>>否</option>
				</select>
				</td>
			</tr>
			<tr>
				<td>实缴金额:</td>
				<td><input id="shiJiaoJinE" type="text" class="readonly" name="actualAmount" value="${entity.actualAmount}" /></td>
				<td width="91">滞纳金天数:</td>
				<td><input name="delayDays" class="readonly" id="zhiNaJinTianShu" value="${entity.delayDays}" />
				</td>
				<td>滞纳金:</td>
				<td><input id="zhiNaJin" type="text" class="readonly" name="delayPayAmount" value="${entity.delayPayAmount}" /></td>
				<td>实缴总金额:</td>
				<td colspan="1"><input id="shiJiaoZongJinE" type="text" class="readonly" name="payAmount" value="${entity.payAmount}" /></td>

			</tr>
			<tr>
				<td width="100">备注:</td>
				<td colspan="10"><textarea name="remark" rows="2" style="height: 60px;">${entity.remark }</textarea>
				</td>
			</tr>
			<!-- 年审企业表格  第六部分(意见栏) -->
			<tr>
				<td width="100" rowspan="2">初审意见:</td>
				<td colspan="3" rowspan="2"><textarea name="initAuditComment" rows="3" cols="45" style="height: 60px;">${entity.initAuditComment}</textarea>
				</td>
				<td width="100" rowspan="3">复审意见:</td>
				<td width="100" style="height: 30px;">拒绝意见:</td>
				<td colspan="3" rowspan="2"><textarea class="readonly" rows="3" cols="45" style="height: 60px;">${entity.verifyAuditComment}</textarea>
				</td>
			</tr>
			<tr>
				<td><select disabled="disabled" style="font-size: 12px; width: 100px; height: 28px;">
						<c:forEach items="${reasons}" var="item">
							<option value="${item.id}" title="${item.content }" <c:if test="${1 eq item.id}">selected="selected"</c:if>>${item.title }</option>
						</c:forEach>
				</select>
				</td>
			</tr>
			<tr>
				<td class="">初审日期:</td>
				<td><c:if test="${entity.initAuditDate==null}">
						<fmt:formatDate value="${now}" type="both" dateStyle="long" pattern="yyyy-MM-dd" var="date" />
					</c:if> <c:if test="${entity.initAuditDate!=null}">
						<fmt:formatDate value="${entity.initAuditDate}" type="both" dateStyle="long" pattern="yyyy-MM-dd" var="date" />
					</c:if> <input class="readonly" value="${date}" />
				</td>
				<td width="100">初审人:</td>
				<td><input type="text" class="readonly" value="${entity.initAuditUser.userRealName}" />
				</td>
				<td class="">复审日期:</td>
				<td><fmt:formatDate value="${entity.verifyAuditDate}" type="both" dateStyle="long" pattern="yyyy-MM-dd" var="verifyAuditDate" /> <input type="text" class="bj_belu4 readonly"
					value="${verifyAuditDate}" />
				</td>
				<td width="100">复审人:</td>
				<td><input type="text" class="readonly" value="${entity.verifyAuditUser.userRealName}" />
				</td>
			</tr>
		</table>
	</div>
	<div style="text-align: center;margin-top: 10px;">
		<a href="javascript:initAudit.back();" class="easyui-linkbutton" iconCls="icon-back">返回</a> <a href="javascript:esd.common.printWindow();" class="easyui-linkbutton" iconCls="icon-print">打印</a>
	</div>

</form>

