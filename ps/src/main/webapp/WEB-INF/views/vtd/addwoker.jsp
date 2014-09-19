<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script type="text/javascript"> 
</script><br /><br /><div>
<div align="center"><p>新增worker</p></div>
	<div style="padding:15px 15px 15px 15px;" align="center">
		<form id="form" action="${contextPath}/addworker" method="post">
			<table >
				  <tr>
					<td>真实姓名:</td>
					<td><input name="workerRealName" class="easyui-validatebox" type="text" value="" /></td>
				</tr>
				<tr>
					<td>残疾人证号:</td>
					<td><input name="workerDisabilityCard" class="easyui-validatebox"  type="text" value="" /></td>
				</tr>
				<tr>  
					<td>身份证号:</td>
					<td><input name="workerIdCard" class="easyui-validatebox"  type="text" value="" /></td>
				</tr>
				<tr>
					<td>手机号码:</td>
					<td><input name="workerPhone" class="easyui-validatebox"  type="text" value="" /></td>
				</tr>
				<tr>
					<td><input type="submit" value="提交鸟"/></td>
				</tr>						
			</table>
		</form>
	</div>
</div>
