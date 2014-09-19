<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<div>
	<div style="margin-top: 20px; margin-left: 20px;">
		<table class="parameterTab">
			<tr> 
				<td>年度:</td>
				<td><input class="easyui-validatebox" required="true" name="year" type="text" value="${params.year}" /> <input class="easyui-validatebox" name="version" type="hidden"
					value="${params.version}" /> <input class="easyui-validatebox" name="id" type="hidden" value="${params.id}" /></td>
			</tr>
			<tr>
				<td>所属地区:</td>
				<td><input value="${areaName}" /></td>
				<td>滞纳金开始日期:</td>
				<td><fmt:formatDate value="${params.auditDelayDate}" type="date" dateStyle="long" pattern="yyyy-MM-dd" var="auditDelayDate" /> <input class="easyui-datebox easyui-validatebox"
					name="auditDelayDate" data-options="required:true,height:30,showSeconds:false" value="${auditDelayDate}" style="width:150px" />
				</td>
			</tr>
			<tr>
				<td>安置比例(%):</td>
				<td><input class="easyui-validatebox" name="putScale" type="text" required="true" value="${params.putScale}" /></td>
				<td>滞纳金征收比率(%):</td>
				<td><input class="easyui-validatebox" name="auditDelayRate" type="text" required="true" value="${params.auditDelayRate}" /></td>
				<td>职工退休年龄(男)：</td>
				<td><input class="easyui-validatebox" name="retireAgeMale" type="text" required="true" value="${params.retireAgeMale}" /></td>
			</tr>
			<tr>
				<td>计算基数:</td>
				<td><input class="easyui-validatebox" name="averageSalary" type="text" required="true" value="${params.averageSalary}" /></td>
				<td>支付截至日期:</td>
				<td><fmt:formatDate value="${params.payCloseDate}" type="date" dateStyle="long" pattern="yyyy-MM-dd" var="date" /> <input class="easyui-datebox easyui-validatebox" name="payCloseDate"
					data-options="required:false,showSeconds:false,height:30" value="${date}" style="width:150px" /></td>
				<td>职工退休年龄(女)：</td>
				<td><input class="easyui-validatebox" name="retireAgeFemale" type="text" required="true" value="${params.retireAgeFemale }" /></td>
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
						<option value="1" <c:if test="${params.eyeOne eq '1'}">selected="selected"</c:if>>1</option>
						<option value="2" <c:if test="${params.eyeOne eq '2'}">selected="selected"</c:if>>2</option>
				</select></td>
				<td><select class="easyui-combobox" name="eyeTwo" data-options="panelHeight:70,height:30">
						<option value="1" <c:if test="${params.eyeTwo eq '1'}">selected="selected"</c:if>>1</option>
						<option value="2" <c:if test="${params.eyeTwo eq '2'}">selected="selected"</c:if>>2</option>
				</select></td>
				<td><select class="easyui-combobox" name="eyeThree" data-options="panelHeight:70,height:30">
						<option value="1" <c:if test="${params.eyeThree eq '1'}">selected="selected"</c:if>>1</option>
						<option value="2" <c:if test="${params.eyeThree eq '2'}">selected="selected"</c:if>>2</option>
				</select></td>
				<td><select class="easyui-combobox" name="eyeFour" data-options="panelHeight:70,height:30">
						<option value="1" <c:if test="${params.eyeFour eq '1'}">selected="selected"</c:if>>1</option>
						<option value="2" <c:if test="${params.eyeFour eq '2'}">selected="selected"</c:if>>2</option>
				</select></td>
			</tr>
			<tr>
				<td>听力残疾:</td>
				<td><select class="easyui-combobox" name="hearingOne" data-options="panelHeight:70,height:30">
						<option value="1" <c:if test="${params.hearingOne eq '1'}">selected="selected"</c:if>>1</option>
						<option value="2" <c:if test="${params.hearingOne eq '2'}">selected="selected"</c:if>>2</option>
				</select></td>
				<td><select class="easyui-combobox" name="hearingTwo" data-options="panelHeight:70,height:30">
						<option value="1" <c:if test="${params.hearingTwo eq '1'}">selected="selected"</c:if>>1</option>
						<option value="2" <c:if test="${params.hearingTwo eq '2'}">selected="selected"</c:if>>2</option>
				</select></td>
				<td><select class="easyui-combobox" name="hearingThree" data-options="panelHeight:70,height:30">
						<option value="1" <c:if test="${params.hearingThree eq '1'}">selected="selected"</c:if>>1</option>
						<option value="2" <c:if test="${params.hearingThree eq '2'}">selected="selected"</c:if>>2</option>
				</select></td>
				<td><select class="easyui-combobox" name="hearingFour" data-options="panelHeight:70,height:30">
						<option value="1" <c:if test="${params.hearingFour eq '1'}">selected="selected"</c:if>>1</option>
						<option value="2" <c:if test="${params.hearingFour eq '2'}">selected="selected"</c:if>>2</option>
				</select></td>
			</tr>
			<tr>
				<td>言语残疾:</td>
				<td><select class="easyui-combobox" name="speakOne" data-options="panelHeight:70,height:30">
						<option value="1" <c:if test="${params.speakOne eq '1'}">selected="selected"</c:if>>1</option>
						<option value="2" <c:if test="${params.speakOne eq '2'}">selected="selected"</c:if>>2</option>
				</select></td>
				<td><select class="easyui-combobox" name="speakTwo" data-options="panelHeight:70,height:30">
						<option value="1" <c:if test="${params.speakTwo eq '1'}">selected="selected"</c:if>>1</option>
						<option value="2" <c:if test="${params.speakTwo eq '2'}">selected="selected"</c:if>>2</option>
				</select></td>
				<td><select class="easyui-combobox" name="speakThree" data-options="panelHeight:70,height:30">
						<option value="1" <c:if test="${params.speakThree eq '1'}">selected="selected"</c:if>>1</option>
						<option value="2" <c:if test="${params.speakThree eq '2'}">selected="selected"</c:if>>2</option>
				</select></td>
				<td><select class="easyui-combobox" name="speakFour" data-options="panelHeight:70,height:30">
						<option value="1" <c:if test="${params.speakFour eq '1'}">selected="selected"</c:if>>1</option>
						<option value="2" <c:if test="${params.speakFour eq '2'}">selected="selected"</c:if>>2</option>
				</select></td>
			</tr>
			<tr>
				<td>肢体残疾:</td>
				<td><select class="easyui-combobox" name="bodyOne" data-options="panelHeight:70,height:30">
						<option value="1" <c:if test="${params.bodyOne eq '1'}">selected="selected"</c:if>>1</option>
						<option value="2" <c:if test="${params.bodyOne eq '2'}">selected="selected"</c:if>>2</option>
				</select></td>
				<td><select class="easyui-combobox" name="bodyTwo" data-options="panelHeight:70,height:30">
						<option value="1" <c:if test="${params.bodyTwo eq '1'}">selected="selected"</c:if>>1</option>
						<option value="2" <c:if test="${params.bodyTwo eq '2'}">selected="selected"</c:if>>2</option>
				</select></td>
				<td><select class="easyui-combobox" name="bodyThree" data-options="panelHeight:70,height:30">
						<option value="1" <c:if test="${params.bodyThree eq '1'}">selected="selected"</c:if>>1</option>
						<option value="2" <c:if test="${params.bodyThree eq '2'}">selected="selected"</c:if>>2</option>
				</select></td>
				<td><select class="easyui-combobox" name="bodyFour" data-options="panelHeight:70,height:30">
						<option value="1" <c:if test="${params.bodyFour eq '1'}">selected="selected"</c:if>>1</option>
						<option value="2" <c:if test="${params.bodyFour eq '2'}">selected="selected"</c:if>>2</option>
				</select></td>
			</tr>
			<tr>
				<td>智力残疾:</td>
				<td><select class="easyui-combobox" name="intelligenceOne" data-options="panelHeight:70,height:30">
						<option value="1" <c:if test="${params.intelligenceOne eq '1'}">selected="selected"</c:if>>1</option>
						<option value="2" <c:if test="${params.intelligenceOne eq '2'}">selected="selected"</c:if>>2</option>
				</select></td>
				<td><select class="easyui-combobox" name="intelligenceTwo" data-options="panelHeight:70,height:30">
						<option value="1" <c:if test="${params.intelligenceTwo eq '1'}">selected="selected"</c:if>>1</option>
						<option value="2" <c:if test="${params.intelligenceTwo eq '2'}">selected="selected"</c:if>>2</option>
				</select></td>
				<td><select class="easyui-combobox" name="intelligenceThree" data-options="panelHeight:70,height:30">
						<option value="1" <c:if test="${params.intelligenceThree eq '1'}">selected="selected"</c:if>>1</option>
						<option value="2" <c:if test="${params.intelligenceThree eq '2'}">selected="selected"</c:if>>2</option>
				</select></td>
				<td><select class="easyui-combobox" name="intelligenceFour" data-options="panelHeight:70,height:30">
						<option value="1" <c:if test="${params.intelligenceFour eq '1'}">selected="selected"</c:if>>1</option>
						<option value="2" <c:if test="${params.intelligenceFour eq '2'}">selected="selected"</c:if>>2</option>
				</select></td>
			</tr>
			<tr>
				<td>精神残疾:</td>
				<td><select class="easyui-combobox" name="mentalOne" data-options="panelHeight:70,height:30">
						<option value="1" <c:if test="${params.mentalOne eq '1'}">selected="selected"</c:if>>1</option>
						<option value="2" <c:if test="${params.mentalOne eq '2'}">selected="selected"</c:if>>2</option>
				</select></td>
				<td><select class="easyui-combobox" name="mentalTwo" data-options="panelHeight:70,height:30">
						<option value="1" <c:if test="${params.mentalTwo eq '1'}">selected="selected"</c:if>>1</option>
						<option value="2" <c:if test="${params.mentalTwo eq '2'}">selected="selected"</c:if>>2</option>
				</select></td>
				<td><select class="easyui-combobox" name="mentalThree" data-options="panelHeight:70,height:30">
						<option value="1" <c:if test="${params.mentalThree eq '1'}">selected="selected"</c:if>>1</option>
						<option value="2" <c:if test="${params.mentalThree eq '2'}">selected="selected"</c:if>>2</option>
				</select></td>
				<td><select class="easyui-combobox" name="mentalFour" data-options="panelHeight:70,height:30">
						<option value="1" <c:if test="${params.mentalFour eq '1'}">selected="selected"</c:if>>1</option>
						<option value="2" <c:if test="${params.mentalFour eq '2'}">selected="selected"</c:if>>2</option>
				</select></td>
			</tr>
			<tr>
				<td>多重残疾:</td>
				<td><select class="easyui-combobox" name="multiOne" data-options="panelHeight:70,height:30">
						<option value="1" <c:if test="${params.multiOne eq '1'}">selected="selected"</c:if>>1</option>
						<option value="2" <c:if test="${params.multiOne eq '2'}">selected="selected"</c:if>>2</option>
				</select></td>
				<td><select class="easyui-combobox" name="multiTwo" data-options="panelHeight:70,height:30">
						<option value="1" <c:if test="${params.multiTwo eq '1'}">selected="selected"</c:if>>1</option>
						<option value="2" <c:if test="${params.multiTwo eq '2'}">selected="selected"</c:if>>2</option>
				</select></td>
				<td><select class="easyui-combobox" name="multiThree" data-options="panelHeight:70,height:30">
						<option value="1" <c:if test="${params.multiThree eq '1'}">selected="selected"</c:if>>1</option>
						<option value="2" <c:if test="${params.multiThree eq '2'}">selected="selected"</c:if>>2</option>
				</select></td>
				<td><select class="easyui-combobox" name="multiFour" data-options="panelHeight:70,height:30">
						<option value="1" <c:if test="${params.multiFour eq '1'}">selected="selected"</c:if>>1</option>
						<option value="2" <c:if test="${params.multiFour eq '2'}">selected="selected"</c:if>>2</option>
				</select></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
		</table>
	</div>
</div>
