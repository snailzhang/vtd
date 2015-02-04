<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%
int userType = 4;
String ut = "worker";
%>
<nav role="navigation" class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand"> </a>
		</div>
		<nav role="navigation" class="collapse navbar-collapse bs-navbar-collapse">
			<div class="nav navbar-nav">
				<p class="navbar-text">待审核时间：<fmt:formatNumber type="number" value="${aduiting/60}" maxFractionDigits="2"/> 分</p>
				<p class="navbar-text"><font color="yellow">|</font></p>
				<p class="navbar-text">已审核时间：<fmt:formatNumber type="number" value="${aduited/60}" maxFractionDigits="2"/> 分</p>
				<p class="navbar-text"><font color="yellow">|</font></p>
				<p class="navbar-text">金额：${salary} 元</p>
			</div>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown">你好:${username}<span class="caret"></span></a>
					<ul class="dropdown-menu" role="menu">
						<li><a href="${contextPath}/security/<%=ut%>">工作区</a></li>
						<li><a href="${contextPath}/security/updatePassWord">修改密码</a></li>
						<li><a href="${contextPath}/security/voiceNote">标注说明</a></li>
						<li id="headJSPWorkerCenter"><a href="${contextPath}/security/workerCenter">个人中心</a></li>
						<li id="headJSPWorkerHistoryPack"><a href="${contextPath}/security/workerHistoryPack">工作历史</a></li>
						<li><a href="${contextPath}/security/workerSalary">工资单</a></li>
						<li class="divider"></li>
						<li><a href="${contextPath}/quit">退出</a></li>
					</ul>
				</li>
			</ul>
		</nav>
	</div>
</nav>
