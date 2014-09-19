<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script type="text/javascript"> 
</script>
<div>
	<div align="center"><p>新增worker</p></div>
	<div style="padding:15px 15px 15px 15px;" align="center">
		<form id="form" action="${contextPath }/addworker" method="post">
			<table class="parameter_tab">
				<tr>
					<td>用户id:</td>
					<td><input type="text" value="${replay}" name="userId" readonly="readonly"/></td>
				</tr>
				<tr>
					<td>真实姓名:</td>
					<td><input name="workerRealName" type="text" class="easyui-validatebox"   value="" /></td>
					<td><span id="realname_message"></span>
					</td>
				</tr>
				<tr>
					<td>身份证号:</td>
					<td><input name="workerIdCard" type="text" class="easyui-validatebox"   value="" /></td>
				</tr>
				<tr>
					<td>残疾人证:</td>
					<td><input name="workerDisabilityCard" type="text" class="easyui-validatebox"   value="" /></td>
				</tr>
				<tr>
					<td>手机号码:</td>
					<td><input name="workerPhone" class="easyui-validatebox"  type="text" value="" /></td>
					<td><span id="mobile_message"></span>
					</td>
				</tr>
				<tr>
					<td><input  type="submit" value="增加工作员" /></td>
				</tr>
			</table>
		</form>
	</div>
</div>
