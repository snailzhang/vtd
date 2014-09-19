<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<div id="payment_add" style="padding: 10px 10px 10px 10px;"> 
	<form id="add_form" action="${contextPath}/security/payment/confirm" method="post">
		<table border="0">
			<tbody>
				<tr>
					<td>收款人:</td>
					<td><input type="text" value="${entity.paymentPerson.userRealName}" /></td>
					<td>缴款方式:</td>
					<td><input name="paymentType.id" class="easyui-combobox readonly" data-options="value:1,height:30,editable:false,valueField:'id',textField:'text',url:'parameter/getPaymentType'" />
					</td>
					<td>状态:</td>
					<td><input name="paymentExceptional.id" class="easyui-combobox" data-options="value:1,height:30,editable:false,valueField:'id',textField:'paymentExceptional',url:'parameter/getExStatic'" />
					</td>

				</tr>
				<tr>
					<td>出票时间:</td>
					<td><fmt:formatDate value="${entity.billPrintDate}" type="date" dateStyle="long" pattern="yyyy-MM-dd" var="billPrintDate" /> <input disabled="disabled" readonly="readonly"
						class="easyui-datebox readonly" name="billPrintDate" data-options="height:30,showSeconds:false" value="${billPrintDate}" style="width:150px" /></td>
					<td>票据号:</td>
					<td><input name="paymentBill" type="text" value="${entity.paymentBill }" /></td>
					<td>缴费金额:</td>
					<td><input name="paymentMoney" type="text" value="${entity.paymentMoney }" /></td>
				</tr>
			</tbody>
		</table>
		<hr />
		<table border="0">
			<tbody>
				<tr>
					<td>换票时间:</td>
					<td><fmt:formatDate value="${entity.billExchangeDate}" type="date" dateStyle="long" pattern="yyyy-MM-dd" var="billExchangeDate" /> <input class="easyui-datebox" name="billExchangeDate"
						data-options="height:30,showSeconds:false" value="${billExchangeDate}" style="width:150px" /></td>
					<td>是否返票:</td>
					<td><select style="font-size: 12px;" class="easyui-combobox" name="billReturn" data-options="width:100,panelHeight:80,height:30,editable:false">
							<option value="true" <c:if test="${entity.billReturn eq 'true'}">selected="selected"</c:if>>是</option>
							<option value="false" <c:if test="${entity.billReturn eq 'false'}">selected="selected"</c:if>>否</option>
					</select></td>
					<td>作废票据号:</td>
					<td><select style="font-size: 12px;" class="easyui-combobox" name="billObsolete" data-options="width:100,panelHeight:80,height:30,editable:false">
							<option value="true" <c:if test="${entity.billObsolete eq 'true'}">selected="selected"</c:if>>是</option>
							<option value="false" <c:if test="${entity.billObsolete eq 'false'}">selected="selected"</c:if>>否</option>
					</select></td>
				</tr>
				<tr>
					<td>备注:</td>
					<td colspan="5"><textarea name="remark" rows="2" cols="20" style="width: 100%">${entity.remark}</textarea></td>
				</tr>
			</tbody>
		</table>
	</form>
</div>



