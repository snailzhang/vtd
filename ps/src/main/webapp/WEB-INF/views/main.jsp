<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
</head>
<body class="easyui-layout">
	<div style="padding: 10px;">
		<h2>残疾人就业保障金计算公式：</h2>
		<div>保障金计算=(单位在职职工总数×1.5%﹣(减)单位在岗残疾职工总数)×本地区上年度职工年人均工资数</div>
			<h3>例:A公司情况如下</h3>
		<table border="1">
			<tr>
				<td>本地区上年度职工年人均工资数:</td>
				<td>30000.00元</td>
				<td></td>
			</tr>
			<tr>
				<td>单位在职职工总数:</td>
				<td>100人</td>
				<td></td>
			</tr>
			<tr>
				<td>单位在岗残疾职工总数:</td>
				<td>1人</td>
				<td></td>
			</tr>
			<tr>
				<td>应安排残疾人比例：</td>
				<td>1.5%</td>
				<td></td>
			</tr>
			<tr>
				<td>A公司应安排残疾人为：</td>
				<td>1.5</td>
				<td>计算公式：100×1.5%=1.5</td>
			</tr>
			<tr>
				<td>A公司应缴保障金为:</td>
				<td>15000</td>
				<td>计算公式：(100×1.5%-1)×30000=15000</td>
			</tr>
		</table>
	<h2>年审流程</h2>
	 设置年审参数→添加公司→添加残疾人职工→初审→复审→缴款
	
	
	
	</div>
</body>
</html>