<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%
int userType = (Integer)session.getAttribute("usertype");
String ut = "";
switch(userType){
	case 1:
		ut = "manager";
		break;
	case 2:
		ut = "employer";
		break;
	case 3:
		ut = "inspector";
		break;
	case 4:
		ut = "worker";
		break;
	case 5:
		ut = "administrator";
		break;
	case 6:
		ut = "inspectorManager";
		break;
}
%>
<nav role="navigation" class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand"> 你好:${username}</a>
		</div>
		<nav role="navigation" class="collapse navbar-collapse bs-navbar-collapse">
			<ul class="nav navbar-nav">
				<li><a href="${contextPath}/security/<%=ut%>">工作区</a></li>
				<li><a href="${contextPath}/security/updatePassWord">修改密码</a></li>
				<li><a href="${contextPath}/security/voiceNote">标注说明</a></li>
			<% 
				if(userType == 4){
					%>
						<li id="headJSPWorkerCenter"><a href="${contextPath}/security/workerCenter">个人中心</a></li>
						<li id="headJSPWorkerHistoryPack"><a href="${contextPath}/security/workerHistoryPack">工作历史</a></li>
					<%
				}
			%>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li><a href="${contextPath}/quit">退出</a></li>
			</ul>
		</nav>
	</div>
</nav>
