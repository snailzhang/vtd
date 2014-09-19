<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	var parameter_edit = {};
	parameter_edit.update = function() {
		esd.common.syncPostSubmit("#form", function(data) {
			if (data == true) {
				$.messager.alert('消息', '更新成功', 'info', function() {
					esd.common.defaultOpenWindowClose();
					$("#parameter_list_grid").datagrid('reload');
				});
			} else {
				$.messager.alert('消息', '更新失败', 'info');
			}
		});
	};
	parameter_edit.back = function() {
		esd.common.defaultOpenWindowClose();
	};
	parameter_edit.change = function(val) {
		if (val.length == 4) {
			var auditDelayDate = $('#auditDelayDate').datebox('getValue');
			var md = auditDelayDate.substr(4, auditDelayDate.length);
			var y = (parseInt(val)+1) + md;
			$('#auditDelayDate').datebox('setValue', y);
			
			var payCloseDate = $('#payCloseDate').datebox('getValue');
			var md = payCloseDate.substr(4, payCloseDate.length);
			var y = (parseInt(val)+1) + md;
			$('#payCloseDate').datebox('setValue', y);
		}
	};
</script>
<div>
	<div style="margin-top: 20px; margin-left: 20px;">
		<form id="form" action="${contextPath}/security/settings/yearAuditParameter/update" method="post">
			<table class="parameterTab">
				<tr>
					<td>年度:</td>
					<td><input class="easyui-validatebox" required="true" name="year" type="text" readonly="readonly" value="${entity.year}" onfocus="parameter_edit.change(this.value)" onchange="parameter_edit.change(this.value)" /> <input class="easyui-validatebox" name="version" type="hidden"
						value="${entity.version}" /> <input class="easyui-validatebox" name="id" type="hidden" value="${entity.id}" />
					</td>
					<td colspan="2">&nbsp;</td>
					<!-- <span style="color: red; font-size: 12px;">提示：默认设置为当前年度减一年</span> -->
				</tr>
				<tr>
					<td>所属地区:</td>
					<td><input name="area.code" class="easyui-combobox" data-options="value:${entity.area.code},height:30,required:true,editable:false,valueField:'code',textField:'name',url:'parameter/getArea'" />
					</td>
					<td>滞纳金开始日期:</td>
					<td>
					<fmt:formatDate value="${entity.auditDelayDate}" type="date" dateStyle="long" pattern="yyyy-MM-dd" var="auditDelayDate" />
					<input class="easyui-datebox easyui-validatebox" id="auditDelayDate" name="auditDelayDate" data-options="required:true,height:30,showSeconds:false" value="${auditDelayDate}" style="width:150px" /></td>
				</tr>
				<tr>
					<td>安置比例(%):</td>
					<td><input class="easyui-validatebox" name="putScale" type="text" required="true" value="${entity.putScale}" />
					</td>
					<td>滞纳金征收比率(%):</td>
					<td><input class="easyui-validatebox" name="auditDelayRate" type="text" required="true" value="${entity.auditDelayRate}" />
					</td>
					<td>职工退休年龄(男)：</td>
					<td><input class="easyui-validatebox" name="retireAgeMale" type="text" required="true" value="${entity.retireAgeMale}" />
					</td>
				</tr>
				<tr>
					<td>计算基数:</td>
					<td><input class="easyui-validatebox" name="averageSalary" type="text" required="true" value="${entity.averageSalary}" />
					</td>
					<td>支付截至日期:</td>
					<td><fmt:formatDate value="${entity.payCloseDate}" type="date" dateStyle="long" pattern="yyyy-MM-dd" var="date" /> <input class="easyui-datebox easyui-validatebox"  id="payCloseDate" name="payCloseDate"
						data-options="required:false,showSeconds:false,height:30" value="${date}" style="width:150px" />
					</td>
					<td>职工退休年龄(女)：</td>
					<td><input class="easyui-validatebox" name="retireAgeFemale" type="text" required="true" value="${entity.retireAgeFemale }" />
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>

			</table>
			<div>
				<strong>残疾人等级计算：</strong>
			</div>
			<table style="margin-top: 10px; margin-left: 20px; float: left;" border="0">
				<tr>
					<td width="80">&nbsp;</td>
					<td>一级</td>
					<td>二级</td>
					<td>三级</td>
					<td>四级</td>
				</tr>
				<tr>
					<td>视力残疾:</td>
					<td><select class="easyui-combobox" name="eyeOne" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.eyeOne eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.eyeOne eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="eyeTwo" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.eyeTwo eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.eyeTwo eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="eyeThree" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.eyeThree eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.eyeThree eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="eyeFour" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.eyeFour eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.eyeFour eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
				</tr>
				<tr>
					<td>听力残疾:</td>
					<td><select class="easyui-combobox" name="hearingOne" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.hearingOne eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.hearingOne eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="hearingTwo" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.hearingTwo eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.hearingTwo eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="hearingThree" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.hearingThree eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.hearingThree eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="hearingFour" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.hearingFour eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.hearingFour eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
				</tr>
				<tr>
					<td>言语残疾:</td>
					<td><select class="easyui-combobox" name="speakOne" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.speakOne eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.speakOne eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="speakTwo" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.speakTwo eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.speakTwo eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="speakThree" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.speakThree eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.speakThree eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="speakFour" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.speakFour eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.speakFour eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
				</tr>
				<tr>
					<td>肢体残疾:</td>
					<td><select class="easyui-combobox" name="bodyOne" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.bodyOne eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.bodyOne eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="bodyTwo" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.bodyTwo eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.bodyTwo eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="bodyThree" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.bodyThree eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.bodyThree eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="bodyFour" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.bodyFour eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.bodyFour eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
				</tr>
				<tr>
					<td>智力残疾:</td>
					<td><select class="easyui-combobox" name="intelligenceOne" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.intelligenceOne eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.intelligenceOne eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="intelligenceTwo" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.intelligenceTwo eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.intelligenceTwo eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="intelligenceThree" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.intelligenceThree eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.intelligenceThree eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="intelligenceFour" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.intelligenceFour eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.intelligenceFour eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
				</tr>
				<tr>
					<td>精神残疾:</td>
					<td><select class="easyui-combobox" name="mentalOne" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.mentalOne eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.mentalOne eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="mentalTwo" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.mentalTwo eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.mentalTwo eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="mentalThree" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.mentalThree eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.mentalThree eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="mentalFour" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.mentalFour eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.mentalFour eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
				</tr>
				<tr>
					<td>多重残疾:</td>
					<td><select class="easyui-combobox" name="multiOne" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.multiOne eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.multiOne eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="multiTwo" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.multiTwo eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.multiTwo eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="multiThree" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.multiThree eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.multiThree eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="multiFour" data-options="panelHeight:70,height:30">
							<option value="1" <c:if test="${entity.multiFour eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.multiFour eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td></td>
					<td colspan="3"><a href="javascript:parameter_edit.update();" class="easyui-linkbutton" iconCls="icon-ok">更新</a> <a href="javascript:parameter_edit.back();" class="easyui-linkbutton"
						iconCls="icon-undo">返回</a></td>
				</tr>
			</table>
		</form>
	</div>
</div>
