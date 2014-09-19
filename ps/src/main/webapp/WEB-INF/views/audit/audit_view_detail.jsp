<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!-- 容器面板 -->
<div class="easyui-panel" data-options="closable:true,collapsible:true,minimizable:true,
       					maximizable:true,fit:true,border:false,doSize:true">

	<div class="easyui-tabs" data-options="fit:true">
		<div id="startaudit_tabs" title="年审信息">
			<jsp:include page="audit_view.jsp" />
		</div>
		<div title="残疾职工信息" data-options="href:'worker/view/${entity.company.id}'"></div>
		<div title="年审参数" ><jsp:include page="audit_params.jsp" /></div>
	</div>
</div>


