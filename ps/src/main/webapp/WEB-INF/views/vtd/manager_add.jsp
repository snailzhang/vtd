<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script type="text/javascript"> 
</script><br /><br /><div>
<div><p>新增manager</p></div>
	<div style="padding:15px 15px 15px 15px;">
		<form id="form" action="${contextPath }/addManager" method="post">
			<table class="parameter_tab">
				<tr>
					<td>用户id:</td>
					<td><input class="easyui-validatebox" name="userId" type="text" value="${replay}"  readonly="readonly"/></td>
				</tr>
				<tr>
					<td style="width: 100px;">昵称:</td>
					<td style="width: 200px;">
						<input class="easyui-validatebox" data-options="required:true"  id="userName" name="managerName" type="text" value="" />
					</td>
					<td><span id="username_message"></span>
					</td>
				</tr>
				<tr>
					<td>用户id:</td>
					<td><input class="easyui-validatebox" name="createId" type="text" value=""/></td>
				</tr>
				<tr>
					<td><input type="submit" value="提交"/></td>
				</tr>							
			</table>
		</form>
	</div>
</div>
