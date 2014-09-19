<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="now" class="java.util.Date" /> 
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	payment.add = {};
	payment.add.save = function() {
		//校验返票和作废票不可同时为true
		var billReturn = $('#billReturn').combobox('getValue');
		var billObsolete = $('#billObsolete').combobox('getValue');
		if(billReturn == 'true' && billObsolete == 'true'){
			$.messager.alert('提示','已经返票的缴款不可以作废, 请重新操作!');
			return;
		}
		$.messager.confirm('警告', '确认缴款后本条记录将不能更改', function(r) {
			if (r) {
				esd.common.syncPostSubmit("#add_form", function(data) {
					if (data == true) {
						//先关闭弹出窗, 防止反复确认,造成数据重复提交
						payment.add.back();
						payment.getBalance();
						$.messager.alert('消息', '确认缴款成功', 'info', function() {
							//更新显示的已缴金额, 代缴金额
							//重载 缴款页面
							$("#payment_datagrid").datagrid('reload');
							$("#paymentList_datagrid").datagrid('reload');
						});
					} else {
						$.messager.alert('消息', '确认缴款失败', 'info');
					}
				});
			}
		});

	};
	
	
	payment.add.back = function() {
		$('#add').window("close");
	};
	$.parser.onComplete = function() {

	};
</script>
<div id="payment_add" style="padding: 10px 10px 10px 10px;">
	<form id="add_form" action="${contextPath}/security/payment/confirm" method="post">
		<input type="hidden" value="${entity.id}" name="id" /> <input type="hidden" value="${entity.version}" name="version" /> <input
			type="hidden" value="${entity.paymentPerson.id}" name="paymentPerson.id" />
		<table border="0">
			<tbody>
				<tr>
					<td>收款人:</td>
					<td><input type="text" disabled="disabled" class="readonly" readonly="readonly" value="${entity.paymentPerson.userRealName}" />
					</td>
					<td>缴款方式:</td>
					<td><input disabled="disabled" readonly="readonly" class="easyui-combobox readonly"
						data-options="value:1,height:30,editable:false,valueField:'id',textField:'text',url:'parameter/getPaymentType'" /></td>
				</tr>
				<tr>
					<td>出票时间:</td>
					<td><fmt:formatDate value="${entity.billPrintDate}" type="date" dateStyle="long" pattern="yyyy-MM-dd" var="billPrintDate" /> <input disabled="disabled" readonly="readonly"
						class="easyui-datebox readonly" name="billPrintDate" data-options="height:30,showSeconds:false" value="${billPrintDate}" style="width:150px" />
					</td>
					<td>票据号:</td>
					<td><input disabled="disabled" class="readonly" readonly="readonly" type="text" value="${entity.paymentBill }" />
					</td>
					<td>缴费金额:</td>
					<td><input disabled="disabled" class="readonly" readonly="readonly" type="text" value="${entity.paymentMoney }" />
					</td>
				</tr>
			</tbody>
		</table>
		<hr />
		<table border="0">
			<tbody>
				<tr>
					<td>返票时间:</td>
					<td><c:if test="${entity.billExchangeDate==null}">
							<fmt:formatDate value="${now}" type="both" dateStyle="long" pattern="yyyy-MM-dd" var="billExchangeDate" />
						</c:if> <c:if test="${entity.billExchangeDate!=null}">
							<fmt:formatDate value="${entity.billExchangeDate}" type="date" dateStyle="long" pattern="yyyy-MM-dd" var="billExchangeDate" />
						</c:if> <input class="easyui-datebox easyui-validatebox" name="billExchangeDate" data-options="height:30,showSeconds:false" value="${billExchangeDate}" style="width:150px" />
					</td>
					<td>是否返票:</td>
					<td><select style="font-size: 12px;" class="easyui-combobox" id="billReturn" name="billReturn" data-options="value:true,width:100,panelHeight:80,height:30,editable:false">
							<option value="true" <c:if test="${entity.billReturn eq 'true'}">selected="selected"</c:if>>是</option>
							<option value="false" <c:if test="${entity.billReturn eq 'false'}">selected="selected"</c:if>>否</option>
					</select>
					</td>
					<td>作废票据号:</td>
					<td><select style="font-size: 12px;" class="easyui-combobox" id="billObsolete" name="billObsolete" data-options="value:true,width:100,panelHeight:80,height:30,editable:false">
							<option value="true" <c:if test="${entity.billObsolete eq 'true'}">selected="selected"</c:if>>是</option>
							<option value="false" <c:if test="${entity.billObsolete eq 'false'}">selected="selected"</c:if>>否</option>
					</select></td>
				</tr>
				<tr>
					<td>备注:</td>
					<td colspan="5"><textarea name="remark" rows="2" cols="20" style="width: 100%">${entity.remark}</textarea>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>


<div style="text-align: center;margin-top: 30px;">
	<a href="javascript:payment.add.save();" class="easyui-linkbutton" iconCls="icon-save">确认</a> <a href="javascript:payment.add.back();" class="easyui-linkbutton" iconCls="icon-back">取消</a>
</div>


