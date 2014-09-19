<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" /> 
<style>

/*表格部分*/
#payment .company-examined {
	width: 886px;
	/*background-color: #6AB7DB;*/
	background-color: #E0ECFF;
	text-align: center;
	/*border: #95B8E7 1px solid;*/
}

#payment .td_short {
	width: 100px;
}

#payment .td_GD_short {
	width: 70px;
}

#payment .td_short_150 {
	width: 150px;
}

#payment .td_zhong {
	width: 200px;
}

#payment .text_short {
	width: 80px;
}

#payment .bj_belu {
	text-align: left;
}

#payment .bj_belu2 {
	text-align: left;
	width: 117px;
}

#payment .readonly {
	background: #d8d8d8;
	height: 30px;
}

#payment .warn {
	background-color: #D8D828;
}

#payment td {
	font-size: 12px;
}

#payment input {
	font-size: 12px;
	/*height: 18px;*/
	line-height: 28px;
	margin: 0px;
	width: 100%;
	border: 0px;
}

#payment textarea {
	font-size: 12px;
	line-height: 28px;
	width: 100%;
	border: 0px;
}

#payment td,th {
	border: #95B8E7 1px solid;
}

#payment .combo {
	border-style: none;
}

#defaultWindow .datagrid-cell {
	font-size: 12px;
}

#defaultWindow .datagrid-cell span {
	font-size: 12px;
}
</style>
<script type="text/javascript">

	payment.auditProcessStatus;
	payment.save = function() {
		esd.common.syncPostSubmit("#form", function(data) {
			if (data == true) {
				$.messager.alert('消息', '保存成功', 'info', function() {
					$("#auditPanel").window("close");
					//$("#initAuditList_datagrid").datagrid('reload');
				});
			} else {
				$.messager.alert('消息', '保存失败', 'info');
			}
		});
	};
	payment.getBalance = function() {
	//	var v = '${entity.accountsYear}/${entity.companyId}';
		//单选框选中的年份s
		var rdo_value = $('input[name="rdoAuditYear"]:checked').val();
		$.ajax({
			url : 'payment/getBalance/'+rdo_value+'/${entity.accountsYear}/${entity.companyId}',			
			type : 'GET',
			success : function(data) {
				$('#payAmount').html(data.payAmount);	//应缴
				$('#alreadyPayAmount').html(data.alreadyPayAmount);	//已缴
				$('#lessPayAmount').html(data.lessPayAmount);//未缴
				payment.hideOrShowThreeBar();
				if(rdo_value != 'all'){
					$('#companyEmpTotal').html(data.companyEmpTotal);	//员工总数
					$('#averageSalary').html(data.averageSalary);	//平均工资
					$('#companyShouldTotal').html(data.companyShouldTotal);	//应安排
					$('#companyAlreadyTotal').html(data.companyAlreadyTotal);	//已安排
					$('#companyHandicapTotal').html(data.companyHandicapTotal);	//已录入
					$('#companyPredictTotal').html(data.companyPredictTotal);	//预定人数
					$('#remark').val(data.remark);
				}
			//	$('#readyPayments').html(data.readyPayments);
			},
			error : function() {
			//	alert("请求错误");
			},
			dataType : "json",
			async : true
		});
	};
/*	payment.open = function(index) {
		esd.common.defaultOpenWindow("新建缴款记录", "${contextPath}/security/payment/edit/" + index);
	};	*/
	payment.pay = function() {
		esd.common.syncPostSubmitEx("#form", "${contextPath }/security/payment/confirm", function(data) {
			if (data == true) {
				$.messager.alert('消息', '审批成功', 'info', function() {
					$("#auditPanel").window("close");
					$("#payment_datagrid").datagrid('reload');
				});
			} else {
				$.messager.alert('消息', '审批失败', 'info');
			}
		});
	};

	/*
	关闭单位年审窗口
	 */
	payment.back = function() {
		esd.common.defaultOpenWindowClose();
	};
	payment.insert = function() {
		var lessPayAmount = $('#lessPayAmount').html();
		var auditYear = $("input[name='rdoAuditYear']:checked").val();
		esd.common.openWindow("#add", "新建缴款", 750, 350, "${contextPath}/security/payment/add/"+auditYear+"/${entity.accountsYear}/" + lessPayAmount+"/${entity.companyId}");
	};
	payment.confirm = function(id) {
		esd.common.openWindow("#add", "确认缴款", 750, 350, "${contextPath}/security/payment/confirm/" + id);
	};
	payment.view = function(id) {
		esd.common.openWindow("#add", "查看缴款", 750, 350, "${contextPath}/security/payment/view/" + id);
	};
	
	payment.backAudit = function() {
		var rdo_value = $('input[name="rdoAuditYear"]:checked').val();
		if(rdo_value == 'all'){
			esd.common.noCloseButtonDialog('警告','只能单个年份返回重审,请重新选择!');
			return;
		}else{
			//确认重申
			$.messager.confirm('消息','确认要重审'+rdo_value+'年度的审核信息吗?',function(r){
				if(!r){
					return;
				}else{
					$.ajax({
						url : 'payment/backAudit/'+rdo_value+'/${entity.accountsYear}/${entity.companyId}',
						type : 'GET',
						success : function(data) {
							if (data == true) {
								esd.common.noCloseButtonDialog('消息', '重审提交成功');
								$("#paymentList_datagrid").datagrid('reload');
							} else {
								$.messager.alert('消息', '失败，有缴款记录不能重审', 'info');
							}
						},
						dataType : "json",
						async : false
					});
				}
			});
			
		}
	};

	//判断’新建缴款‘，’重申‘，‘返回’ 三个按钮是否隐藏
	payment.hideOrShowThreeBar = function(){
		//初始进入页面时使用
		var status = '${entity.auditProcessStatus}';
		if(status != null && '' != status){
			if(status == '3' || status == '4'){
				$('#defaultWindow .datagrid-toolbar').css('display','block');
			}else{
				$('#defaultWindow .datagrid-toolbar').css('display','none');
			}
		}
		//单选按钮改变时使用
		var lessPayAmount = $('#lessPayAmount').html();
		if(lessPayAmount == 0.00 || lessPayAmount == '0.00'){
			$('#defaultWindow .datagrid-toolbar').css('display','none');
		}else{
			$('#defaultWindow .datagrid-toolbar').css('display','block');
		}
	};
	
	payment.loadPaymentData = function() {
		esd.common.datagridEx("#payment_datagrid", "${contextPath }/security/payment/getPayments/${entity.accountsYear}/${entity.companyId}", [ {
			text : '新建缴款记录',
			iconCls : 'icon-add',
			handler : payment.insert
		}, {
			text : '重审',
			iconCls : 'icon-reload',
			handler : payment.backAudit
		},{
			text : '返回',
			iconCls : 'icon-undo',
			handler : payment.back

		} ], [ [ {
			field : 'id',
			hidden : true
		}, {
			field : 'billPrintDate',
			title : '出票日期',
			width : 80,
			align : 'center'
		}, {
			field : 'paymentMoney',
			title : '缴费金额',
			align : 'right',
			width : 90,
			formatter : function(value, row, index) {
				if (value.indexOf('-') != -1) {
					var v = '<span style="color: red;">' + value + '</span>';
				} else {
					var v = '<span style="color: green;">' + value + '</span>';
				}
				return v;
			}
		}, {
			field : 'paymentType',
			title : '缴费方式',
			width : 70,
			align : 'center'
		}, {
			field : 'paymentBill',
			title : '票据号',
			width : 90
		}, {
			field : 'billExchangeDate',
			title : '返票日期',
			width : 75,
			align : 'center'
		}, {
			field : 'paymentExceptional',
			title : '状态',
			width : 60,
			align : 'center'
		}, {
			field : 'billReturn',
			title : '返',
			align : 'center',
			width : 25,
			formatter : function(value, row, index) {
				val = '<strong style="color: orange;" >F</strong>';
				if (value == true) {
					var val = '<strong>B</strong>';
				}
				return val;
			}
		}, {
			field : 'billObsolete',
			title : '废',
			width : 25,
			align : 'center',
			formatter : function(value, row, index) {
				val = '<strong style="color: orange;" >F</strong>';
				if (value == true) {
					var val = '<strong style="color: red;" >T</strong>';
				}
				return val;
			}
		}, {
			field : 'auditYear',
			title : '审核年度',
			width : 50,
			align : 'center'
		}, {
			field : 'userRealName',
			title : '收款人',
			width : 60,
			align : 'center'
		}, {
			field : 'action',
			title : '操作',
			width : 40,
			align : 'center',
			formatter : function(value, row, index) {
				if (row.billReturn == true || row.billObsolete==true) {
					var v = '<a href="#" style="font-size: 12px;" onclick="payment.view(' + row.id + ')">查看</a>';
				} else {
					var v = '<a href="#" style="font-size: 12px;" onclick="payment.confirm(' + row.id + ')">确认</a>';
				}
				return v;
			}
		} ] ]);
	};
	$.parser.onComplete = function() {
	//	payment.getBalance();
		payment.loadPaymentData();
		payment.hideOrShowThreeBar();
	};
</script>
<div id="payment">
	<input id="auditId" type="hidden" value="${entity.id}" />
	<!-- ↓↓↓↓↓↓↓↓↓↓↓↓ 暂时无用 ↓↓↓↓↓↓↓↓↓↓↓↓ -->
	<input id="auditProcessStauts" type="hidden" value="${entity.auditProcessStatus }" />
	<!-- 年审企业表格  第一部分 -->
	<table cellspacing="0" cellpadding="0" border="0" title="企业年审信息" class="company-examined">
		<tbody>
			<tr>
				<td class="td_short">档案号码:</td>
				<td class="td_long bj_belu readonly" colspan="3">${entity.companyCode}</td>
				<td class="td_short">审计年度:</td>
				<td class="td_long bj_belu readonly" colspan="3">
					<c:forEach items="${entity.auditYears}" var="item">
						 ${item }<input type="radio" checked="checked" value="${item }" name="rdoAuditYear" id="rdoAuditYear${item }" onclick="payment.getBalance()" style="width:auto;margin-right:15px;"/>
					</c:forEach>
					<c:if test="${fn:length(entity.auditYears) > 1 }">
						全部 <input type="radio" checked="checked" value="all" name="rdoAuditYear" id="rdoAuditYear" onclick="payment.getBalance()" style="width:auto;margin-right:15px;"/>
					</c:if>
			<!--		<input type="radio" name="rdoAuditYears" style="width:auto;margin-left:15px;margin-right:5px;"/>
					所有审核年(
						<c:forEach items="${entity.auditYears}" var="item">
							${item},
						</c:forEach>)
			 		<input type="radio" name="rdoAuditYears" style="width:auto;margin-left:15px;margin-right:5px;" />
					单选
					<select>
						<c:forEach items="${entity.auditYears}" var="item">
							<option value="${item}" title="${item }">${item}</option>
						</c:forEach>
					</select>	 -->
				</td>
			</tr>
			<tr>
				<td class="td_short">单位名称:</td>
				<td class="bj_belu readonly" style="width: 200px;" colspan="3">${entity.companyName}</td>
				<td class="td_short">税务代码:</td>
				<td class="td_long bj_belu readonly" colspan="3">${entity.companyTaxCode}</td>
			</tr>
			<tr>
				<td class="bj_belu2" style="text-align:center;">缴款年度:</td>
				<td class="td_short readonly">${entity.accountsYear}</td>
				<td class="bj_belu2" style="text-align:center;">应缴金额:</td>
				<td class="td_short readonly" id="payAmount">${entity.payAmount}</td>
				<td class="td_short">已缴金额(总):</td>
				<td class="bj_belu2 readonly" id="alreadyPayAmount">${entity.alreadyPayAmount}</td>
				<td class="td_short">待缴金额:</td>
				<td class="bj_belu readonly" id="lessPayAmount">${entity.lessPayAmount}</td>


			</tr>
		<!-- 	<tr>
				<td class="td_short">预缴金额:</td>
				<td class="bj_belu2 readonly" id="readyPayments"></td>
				<td class="td_short">已缴金额:</td>
				<td class="bj_belu2 readonly" id="payments"></td>
				<td class="td_short">余缴金额:</td>
				<td class="bj_belu2 readonly" id="balance"></td>
				<td class="td_short">实缴总金额:</td>
				<td class="bj_belu2 readonly">${entity.payAmount }</td>

			</tr>	 -->
			<tr>
				<td class="td_short" rowspan="3">备注:</td>
				<td colspan="3" rowspan="3"><textarea class="readonly" style="height: 100%" rows="2" cols="90" id="remark">${entity.remark}</textarea>
				</td>
				<td class="td_short">职工总人数:</td>
				<td class="td_short readonly" style="text-align: left;" id="companyEmpTotal">${entity.companyEmpTotal }</td>
				<td class="td_short">年人均工资:</td>
				<td class="td_short readonly" style="text-align: left;" id="averageSalary">${entity.averageSalary }</td>
			</tr>
			<tr>
				<td class="td_short">应按排数:</td>
				<td class="td_short">已安排数:</td>
				<td class="td_short">已录入数:</td>
				<td class="td_short">预定人数:</td>
			</tr>
			<tr>
				<td class="td_short readonly" id="companyShouldTotal">${entity.companyShouldTotal}</td>
				<td class="td_short readonly" id="companyAlreadyTotal">${entity.companyAlreadyTotal}</td>
				<td class="td_short readonly" id="companyHandicapTotal">${entity.companyHandicapTotal }</td>
				<td class="td_short readonly" id="companyPredictTotal">${entity.companyPredictTotal }</td>
			</tr>
		</tbody>
	</table>
</div>
<table id="payment_datagrid" style="font-size: 12px; height: 100px;"></table>



