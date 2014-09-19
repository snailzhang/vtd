<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script type="text/javascript"> 
	var parameter_save = {};
	parameter_save.submit = function() {
		//校验年度
		var date = new Date();
		var year = $('#jinnian').val();
		if (year < 1970) {
			$.messager.alert('消息', '输入的年份太小', 'info');
			return;
		} else if (year >= date.getFullYear()) {
			$.messager.alert('消息', '输入的年份过大', 'info');
			return;
		}
		var copy = $('#copy').combobox('getValue');
		//如果是当年的审核数据
		if((year == (date.getFullYear()-1))&&(copy == 'false')){
			$.messager.confirm('确认对话框','<span style="font-size:12px;">您确定不创建审核数据? 如需创建请"取消",强烈建议您创建!</span>',function(r){
				if(!r){
					$('#copy').focus();
					return;
				}else{
					//提交
					esd.common.syncPostSubmit("#form", function(data) {
						if (data.notice == true || data.notice == 'true') {
							$.messager.alert('消息', '添加成功', 'info', function() {
								esd.common.defaultOpenWindowClose();
								$("#parameter_list_grid").datagrid('reload');
							});
						} else {
							$.messager.alert('消息', data.notice, 'info');
						}
					});
				}
				
			});
		}else{
			//提交. 很恶心, 由于confirm不能提到window.confirm
			esd.common.syncPostSubmit("#form", function(data) {
				if (data.notice == true || data.notice == 'true') {
					$.messager.alert('消息', '添加成功', 'info', function() {
						esd.common.defaultOpenWindowClose();
						$("#parameter_list_grid").datagrid('reload');
					});
				} else {
					$.messager.alert('消息', data.notice, 'info');
				}
			});
		}
		

	};
	parameter_save.back = function() {
		esd.common.defaultOpenWindowClose();
	};

	parameter_save.change = function(val) {
		if (val.length == 4) {
			var auditDelayDate = $('#auditDelayDate').datebox('getValue');
			var payCloseDate = $('#payCloseDate').datebox('getValue');
			var a = auditDelayDate.substr(4, auditDelayDate.length);
			var p = payCloseDate.substr(4, payCloseDate.length);
			var amd = (parseInt(val) + 1) + a;
			var pmd = (parseInt(val) + 1) + p;
			$('#auditDelayDate').datebox('setValue', amd);
			$('#payCloseDate').datebox('setValue', pmd);
		}
	};
</script>
<div>
	<div style="margin-top: 20px; margin-left: 20px;">
		<form id="form" action="${contextPath}/security/settings/yearAuditParameter/add" method="post">
			<table class="parameterTab" border="0">
				<tr>
					<td>年度:</td>
					<td><input class="easyui-validatebox" required="true" name="year" id="jinnian" type="text" value="" onfocus="parameter_save.change(this.value)" onchange="parameter_save.change(this.value)" />
						<input name="version" type="hidden" value="1" />
					</td>
					<td>批量创建审计记录:</td>
					<td colspan="3"><select class="easyui-combobox" id="copy" name="copy" data-options="editable:false,panelHeight:47,height:30" style="width:120px">
							<option value="true">是</option>
							<option value="false">否</option>
					</select><span style="font-size:12px;color:red;"> (*)当前年度审核参数选"是"; 往年审核参数(即补审)选"否"</span></td>
				</tr>
				<tr>
					<td>所属地区:</td>
					<td><input name="area.code" class="easyui-combobox" value="10230000" data-options="height:30,required:true,editable:false,valueField:'code',textField:'name',url:'parameter/getArea'" />
					</td>
					<td>滞纳金开始日期:</td>
					<td><input class="easyui-datebox" id="auditDelayDate" name="auditDelayDate" data-options="required:true,height:30,showSeconds:false" value="${year+1}-9-01" style="width:120px" /></td>
				</tr>
				<tr>
					<td>安置比例(%):</td>
					<td><input class="easyui-validatebox" name="putScale" type="text" required="true" value="0.015" />
					</td>
					<td>滞纳金征收比率(%):</td>
					<td><input class="easyui-validatebox" name="auditDelayRate" type="text" required="true" value="0.005" style="width:118px;"/></td>
					<td>职工退休年龄(男)：</td>
					<td><input class="easyui-validatebox" name="retireAgeMale" type="text" required="true" value="61" style="width:120px;"/></td>
				</tr>
				<tr>
					<td>计算基数:</td>
					<td><input class="easyui-numberbox" name="averageSalary" type="text" required="true" data-options="min:0,precision:2" />
					</td>
					<td>支付截至日期:</td>
					<td><input class="easyui-datebox" id="payCloseDate" name="payCloseDate" data-options="required:true,height:30,showSeconds:false" value="${year+1}-11-25" style="width:120px" /></td>
					<td>职工退休年龄(女)：</td>
					<td><input class="easyui-validatebox" name="retireAgeFemale" type="text" required="true" value="51" style="width:120px;" /></td>
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
					<td><select class="easyui-combobox" name="eyeOne" data-options="panelHeight:47,height:25">
							<option value="1" >1</option>
							<option value="2" selected="selected">2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="eyeTwo" data-options="panelHeight:47,height:25">
							<option value="1" <c:if test="${entity.eyeTwo eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.eyeTwo eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="eyeThree" data-options="panelHeight:47,height:25">
							<option value="1" <c:if test="${entity.eyeThree eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.eyeThree eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="eyeFour" data-options="panelHeight:47,height:25">
							<option value="1" <c:if test="${entity.eyeFour eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.eyeFour eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
				</tr>
				<tr>
					<td>听力残疾:</td>
					<td><select class="easyui-combobox" name="hearingOne" data-options="panelHeight:47,height:25">
							<option value="1" <c:if test="${entity.hearingOne eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.hearingOne eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="hearingTwo" data-options="panelHeight:47,height:25">
							<option value="1" <c:if test="${entity.hearingTwo eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.hearingTwo eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="hearingThree" data-options="panelHeight:47,height:25">
							<option value="1" <c:if test="${entity.hearingThree eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.hearingThree eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="hearingFour" data-options="panelHeight:47,height:25">
							<option value="1" <c:if test="${entity.hearingFour eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.hearingFour eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
				</tr>
				<tr>
					<td>言语残疾:</td>
					<td><select class="easyui-combobox" name="speakOne" data-options="panelHeight:47,height:25">
							<option value="1" <c:if test="${entity.speakOne eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.speakOne eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="speakTwo" data-options="panelHeight:47,height:25">
							<option value="1" <c:if test="${entity.speakTwo eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.speakTwo eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="speakThree" data-options="panelHeight:47,height:25">
							<option value="1" <c:if test="${entity.speakThree eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.speakThree eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="speakFour" data-options="panelHeight:47,height:25">
							<option value="1" <c:if test="${entity.speakFour eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.speakFour eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
				</tr>
				<tr>
					<td>肢体残疾:</td>
					<td><select class="easyui-combobox" name="bodyOne" data-options="panelHeight:47,height:25">
							<option value="1" <c:if test="${entity.bodyOne eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.bodyOne eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="bodyTwo" data-options="panelHeight:47,height:25">
							<option value="1" <c:if test="${entity.bodyTwo eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.bodyTwo eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="bodyThree" data-options="panelHeight:47,height:25">
							<option value="1" <c:if test="${entity.bodyThree eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.bodyThree eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="bodyFour" data-options="panelHeight:47,height:25">
							<option value="1" <c:if test="${entity.bodyFour eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.bodyFour eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
				</tr>
				<tr>
					<td>智力残疾:</td>
					<td><select class="easyui-combobox" name="intelligenceOne" data-options="panelHeight:47,height:25">
							<option value="1" <c:if test="${entity.intelligenceOne eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.intelligenceOne eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="intelligenceTwo" data-options="panelHeight:47,height:25">
							<option value="1" <c:if test="${entity.intelligenceTwo eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.intelligenceTwo eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="intelligenceThree" data-options="panelHeight:47,height:25">
							<option value="1" <c:if test="${entity.intelligenceThree eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.intelligenceThree eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="intelligenceFour" data-options="panelHeight:47,height:25">
							<option value="1" <c:if test="${entity.intelligenceFour eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.intelligenceFour eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
				</tr>
				<tr>
					<td>精神残疾:</td>
					<td><select class="easyui-combobox" name="mentalOne" data-options="panelHeight:47,height:25">
							<option value="1" <c:if test="${entity.mentalOne eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.mentalOne eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="mentalTwo" data-options="panelHeight:47,height:25">
							<option value="1" <c:if test="${entity.mentalTwo eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.mentalTwo eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="mentalThree" data-options="panelHeight:47,height:25">
							<option value="1" <c:if test="${entity.mentalThree eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.mentalThree eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="mentalFour" data-options="panelHeight:47,height:25">
							<option value="1" <c:if test="${entity.mentalFour eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.mentalFour eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
				</tr>
				<tr>
					<td>多重残疾:</td>
					<td><select class="easyui-combobox" name="multiOne" data-options="panelHeight:47,height:25">
							<option value="1" <c:if test="${entity.multiOne eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.multiOne eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="multiTwo" data-options="panelHeight:47,height:25">
							<option value="1" <c:if test="${entity.multiTwo eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.multiTwo eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="multiThree" data-options="panelHeight:47,height:25">
							<option value="1" <c:if test="${entity.multiThree eq '1'}">selected="selected"</c:if>>1</option>
							<option value="2" <c:if test="${entity.multiThree eq '2'}">selected="selected"</c:if>>2</option>
					</select>
					</td>
					<td><select class="easyui-combobox" name="multiFour" data-options="panelHeight:47,height:25">
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
					<td colspan="3"><a href="javascript:parameter_save.submit();" class="easyui-linkbutton" iconCls="icon-ok">保存</a> <a href="javascript:parameter_save.back();" class="easyui-linkbutton"
						iconCls="icon-undo">返回</a></td>
				</tr>
			</table>
		</form>
	</div>
</div>
