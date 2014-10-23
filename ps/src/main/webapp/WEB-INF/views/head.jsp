<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%
int userType = (Integer)session.getAttribute("usertype");
%>
<nav role="navigation" class="navbar navbar-default navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<a href="#" class="navbar-brand"> 你好:${username}</a>
		</div>
		<nav role="navigation" class="collapse navbar-collapse bs-navbar-collapse">
			<% 
				if(userType == 4){
					
					%>
						<ul class="nav navbar-nav">
							<li id="headJSPWorkerHistoryPack"><a href="${contextPath}/security/workerHistoryPack">工作历史</a></li>
							<li id="headJSPWorker"><a href="${contextPath}/security/worker">工作页面</a></li>
						</ul>
					<%
				}
			%>
			
			<ul class="nav navbar-nav navbar-right">
				<li><a href="${contextPath}/quit">退出</a></li>
			</ul>
		</nav>
	</div>
</nav>
