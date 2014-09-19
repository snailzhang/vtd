<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" /> 






<h2>查看参数</h2>
		<table class="parameter_tab">
			<tr>
				<td>年度:</td>
				<td>
					<input type="text" />
				</td>
				<td>审核地区:</td>
				<td>
					<input type="text" />
				</td>
			</tr>
			<tr>
			<td>安置比例(%):</td>
				<td>
					<input type="text" />
				</td>
				<td>计算基数:</td>
				<td>
					<input type="text" />
				</td>
			</tr>
			
			<tr>
				<td>保障金开始日期:</td>
				<td >
					<input type="text" />
				</td>
				<td>保障金截止日期:</td>
				<td >
					<input type="text" />
				</td>
			</tr>
			<tr>
				<td>滞纳金开始日期:</td>
				<td colspan="3">
					<input type="text" />
				</td>
				
				
			</tr>
			<tr>
				<td>计算保障小数点位数:</td>
				<td colspan="3">
					<input type="text" />
				</td>
			</tr>
			<tr>
				<td>少安排人数:</td>
				<td>
					<input type="text" />
				</td>
				<td>应缴保障金:</td>
				<td>
					<input type="text" />
				</td>
			</tr>
			
			<tr>
				<td>视力1，2，3，4级:</td>
				<td>
					<div class="select_level"></div>
				</td>
				<td>听力1，2，3，4级:</td>
				<td>
					<div class="select_level"></div>
				</td>
			</tr>
			<tr>
				<td>言语1，2，3，4级:</td>
				<td>
					<div class="select_level"></div>
				</td>
				<td>肢体1，2，3，4级:</td>
				<td>
					<div class="select_level"></div>
				</td>
			</tr>
			<tr>
				<td>智力1，2，3，4级:</td>
				<td>
					<div class="select_level"></div>
				</td>
				<td>精神1，2，3，4级:</td>
				<td>
					<div class="select_level"></div>
				</td>
			</tr>
			<tr>
				<td>多重1，2，3，4级</td>
				<td>
					<div class="select_level"></div>
				</td>
				<td>是否大学生:</td>
				<td>
					<div class="select_level"></div>
					
				</td>
			</tr>
		</table>

<script type="text/javascript">

	var level="<select><option>1</option><option>2</option></select>&nbsp;";
	$(".select_level").append(level+level+level+level);
	
</script>