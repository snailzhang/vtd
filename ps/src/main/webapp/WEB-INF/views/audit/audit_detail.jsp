<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!-- 容器面板 -->
<script type="text/javascript"> 
/*	$('#auditTabs').tabs({
		onSelect : function(title,index) {
			if(index==0){
				if($('#worker_HandicapTotal').length>0){
					if($('#yiLuRuCanJiRen').val($('#worker_HandicapTotal').html()));
				};
			};
		}
	});	*/
	//如果初审选项卡被选中时, 更新里面的内容
	
</script>

<div class="easyui-panel" data-options="closable:true,collapsible:true,minimizable:true,
       					maximizable:true,fit:true,border:false,doSize:true">

	<div id="auditTabs" class="easyui-tabs" data-options="fit:true">
	<!--startprint-->
		<div id="startaudit_tabs" title="年审信息" cache="false">
			<!-- 初审页面 -->
			<c:if test="${process==1}">
				<jsp:include page="audit_init.jsp" />
			</c:if>
			<!-- 复审页面 -->
			<c:if test="${process==2}">
				<jsp:include page="audit_verify.jsp" />
			</c:if>
			<!-- 查看页面 -->
			<c:if test="${process==3}">
				<jsp:include page="audit_view.jsp" />
			</c:if>
		</div>
		<!--endprint-->
		<c:if test="${process==1}">
			<div title="残疾职工信息" data-options="href:'worker/list/${entity.company.id}/${entity.year}'"></div>
		</c:if>
		<c:if test="${process==2}">
			<div title="残疾职工信息" data-options="href:'worker/view/${entity.company.id}/${entity.year}'"></div>
		</c:if>
		<c:if test="${process==3}">
			<div title="残疾职工信息" data-options="href:'worker/view/${entity.company.id}/${entity.year}'"></div>
		</c:if>
		<div title="年审参数"><jsp:include page="audit_params.jsp" /></div>
	</div>
</div>


