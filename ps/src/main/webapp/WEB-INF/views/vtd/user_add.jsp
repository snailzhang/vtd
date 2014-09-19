<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script type="text/javascript"> 
	var user_detail = {};
	user_detail.submit = function() {
		esd.common.syncPostSubmit("#form", function(data) {
			if (data.notice == true) {
				$.messager.alert('消息', '添加成功', 'info', function() {
					esd.common.defaultOpenWindowClose();
					$("#user_list_grid").datagrid('reload');
				});
			} else {
				$.messager.alert('消息', data.notice, 'info');
			}
		});
	};
	 $.extend($.fn.validatebox.defaults.rules, {
		equals : {
			validator : function(value, param) {
				return value == $('input[name=userPassword]').val();
			},
			message : '两次输入的密码不一致'
		}
	}); 

	user_detail.back = function() {
		esd.common.defaultOpenWindowClose();
	};
	/* $(document).ready(function() {
		var url = document.location.href;
		var urlList = url.split('?');
		var urltype = urlList[urlList.length-1].split('=')[1];
		alert("urltype:"+urltype);
		if(urltype==0){
			$("#usertype1").hide();
		}else{
			$("#usertype").hide();
			$("#usertype").val(urltype);
		}	
	}); */
</script><br /><br /><div>
<div align="center"><p>新增用户页user ,管理员编号:${mangerid}</p></div>
	<div style="padding:15px 15px 15px 15px;">
		<form id="form" action="${contextPath }/addUser" method="post">
			<table class="parameter_tab" align="center">
				<tr>
					<td style="width: 100px;">用户名:</td>
					<td style="width: 200px;">
						<input class="easyui-validatebox" data-options="required:true"  id="userName" name="username" type="text" value="" />
					</td>
					<td><span id="username_message"></span>
					</td>
				</tr>

				<tr>
					<td>登陆密码:</td>
					<td><input class="easyui-validatebox" required="true" name="password" type="password" /></td>
					<td></td>
				</tr>
				<tr>
					<td>确认密码:</td>
					<td><input name="confirm" class="easyui-validatebox" type="password" required="true" validType="equals['#userPassword']" /></td>
					<td></td>
				</tr> 
				<!-- <tr>
					<td>&nbsp;</td>
				</tr> -->
				<!--  <tr>
					<td>真实姓名:</td>
					<td><input name="userRealName" type="text" class="easyui-validatebox"  required="true" value="" /></td>
					<td><span id="realname_message"></span>
					</td>
				</tr>  -->
				<tr>
					<td>用户编号:</td>
					<td><input name="createId" class="easyui-validatebox"  type="text" value="" /></td>
					<td><span id="mobile_message"></span>
					</td>
				</tr>
				<tr style="line-height: 20px;">
					<td>用户类别:</td>
					<td>
						<select id="usertype" class="easyui-combobox" name="usertype"  data-options="panelHeight:70,height:30,editable:false" <%="0".equals(request.getParameter("type"))?"":"disabled"%>>
							<option value="1" <%="1".equals(request.getParameter("type"))?"selected":"" %>>管理员</option>
							<option value="2" <%="2".equals(request.getParameter("type"))?"selected":"" %>>发包商</option>
							<option value="3" <%="3".equals(request.getParameter("type"))?"selected":"" %>>质检员</option>
							<option value="4" <%="4".equals(request.getParameter("type"))?"selected":"" %>>工作员</option>
					    </select>
					</td>
				</tr>
				<%-- <tr style="line-height: 20px;">
					<td>用户组:</td>
					<td><select style="font-size: 12px;" class="easyui-combobox" name="userGroup.id" data-options="width:120,panelHeight:120,height:30,editable:false">
							<c:forEach items="${group}" var="item">
								<option value="${item.id}" <c:if test="${item.id==1}">selected="selected"</c:if>>${item.userGroupName}</option>
							</c:forEach>
					</select>
					</td>
				</tr> --%>
				<!-- <tr>
					<td>备注</td>
					<td colspan="3"><textarea name="userRemark" rows="5" cols="70"></textarea></td>
				</tr> -->
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td><input type="text" name="mangerid" value="${mangerid}"/></td>
					<td><input type="submit" value="提交鸟"/></td>
					
					<!-- <td colspan="3"><a href="javascript:user_detail.submit();" class="easyui-linkbutton" iconCls="icon-ok">保存</a> <a href="javascript:user_detail.back();" class="easyui-linkbutton"
						iconCls="icon-back">返回</a>
					</td> -->
				</tr>
			</table>
		</form>
	</div>
</div>
