<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<table border="1">
  <tr>
    <th>未审年度</th> 
    <th>应缴金额</th>
    <th>逾期天数</th>
    <th>滞纳金比例</th>
    <th>滞纳金</th>
    <th>年度总金额</th>    
  </tr>
  <tr>
    <td>Row 1: Col 1</td> 
    <td>Row 1: Col 2</td>
    <td>Row 1: Col 2</td>
    <td>Row 1: Col 2</td>
    <td>Row 1: Col 2</td>
    <td>Row 1: Col 2</td>
  </tr>
</table>
<div>应缴金额=审计年度的残疾人保障金（注：安排残疾人总人数按0计算）</div>
<div>滞纳金=应缴金额×逾期天数×滞纳金比例</div>
<div>年度总金额 = 应缴金额+滞纳金</div>


<table border="1">
  <tr>
    <th>欠缴年度</th>
    <th>欠缴金额</th>
    <th>逾期天数</th>
    <th>滞纳金比例</th>
    <th>滞纳金</th>
    <th>年度总金额</th>    
  </tr>
  <tr>
    <td>Row 1: Col 1</td>
    <td>Row 1: Col 2</td>
    <td>Row 1: Col 2</td>
    <td>Row 1: Col 2</td>
    <td>Row 1: Col 2</td>
  </tr>
</table>
<div>滞纳金=欠缴金额×逾期天数×滞纳金比例</div>
<div>年度总金额 = 应缴金额+滞纳金</div>


