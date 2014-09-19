<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<script type="text/javascript">
   		 
   			$(function(){
   					
					$('#contributions_list').datagrid({    
					   // url:'../../CompanyBasicAction', 
					    title:'待缴款企业列表',
					    iconCls:'icon-save',
					    pageSize:10,
					    pageList:[10,30,50,100],
					    pagination:true, //是否显示分页栏 
					    doSize:true,//在面板被创建的时候将重置大小和重新布局。
					    fit:true,	//面板大小将自适应父容器
				  	 	fitColumns:true,//是否显示横向滚动条，未生效
				  	 	nowrap:true,//如果为true，则在同一行中 。设置为true可以提高加载性能 
				  	 	idField:'id',
						rownumbers:false,//显示行号
						striped:true,//奇偶行使用不同背景色
						frozenColumns:[[
		        	        {field:'ck',checkbox:true}
						]],
					    columns:[[    
					        {field:'sArchivesCode',title:'档案编码',width:100},  
					        {field:'sTaxCode',title:'税务编码',width:100},  
					        {field:'sCompanyName',title:'企业名称',width:900,
					        		 formatter:function(value,row,index){
							               var c = '<a href="#" onclick="contributions_list.openViewCompany('+index+')">'+value+'</a>';
							                    return c;
							            }
					        
					        } ,
					        {field:'sProcessStatus',title:'流程状态',width:200} ,
					        {field:'action',title:'操作',width:250,align:'center',
							            formatter:function(value,row,index){
							              var v = '<a href="#" onclick="contributions_list.openContributions('+index+')">缴款</a> ';
							             return v;
							            }
							 }
					    ]],
					  toolbar:'#contributions_list_boolbar'//指定自定义菜单
					}); 
				var contributions_data={total:3,rows:[
								{"sArchivesCode":"1","sTaxCode":"1022012","sCompanyName":"黑龙江省委办公厅黑龙江省委办公厅","sProcessStatus":"未复审"},
								{"sArchivesCode":"12","sTaxCode":"1022012","sCompanyName":"黑龙江省委老干部局","sProcessStatus":"待缴款"},
								{"sArchivesCode":"12","sTaxCode":"1022012","sCompanyName":"中国人寿保险股份有限公司绥化分公司 ","sProcessStatus":"已缴款"}]};
				//加载前台数据		
				$("#contributions_list").datagrid("loadData", contributions_data);
  			
   			});
   			
   			contributions_list={};
   			/* 
   				打开缴款窗口页面
   			*/
   			contributions_list.openContributions=function(index){
   					$("#contributionsPanel").window({
						href:'payment/edit',
   						width:1000,
   						height:550,
   						title:'缴款',
   						minimizable:false,//不显示最小化按钮
   						loadingMessage:'正在加载缴款数据，请稍等。。。',
   						modal:true//模态窗口
   						
   					});
   			};
   			
   			/* 
				查看企业信息框
			 */
			contributions_list.openViewCompany = function(index) {
				$('#viewCompanyPanel').window({
					width : 1000,
					href : 'basicInfo/view_company',
					height : 550,
					title : '查看单位信息',
					minimizable : false,
					loadingMessage : '正在加载，请稍后。',
					iconCls : 'icon-save',
					modal:true//模态窗口
				});
			};
   </script>


<!-- 数据表格 -->
<table id="contributions_list"></table>

<!-- 自定义菜单 -->
<div id="contributions_list_boolbar" data-options="fit:false,doSize:false" style="white-space: nowrap;height: 50px;margin-top: 5px">

	<select>
		<option>2012年</option>
	</select>
	<button onclick="addCompany()">---</button>
	<div style="float: right;">
		<button>导入缴款结果文件</button>
	</div>
	<div>

		<input type="" style="width: 7%" /> <input type="" style="width: 6%" /> <input type="" style="width: 68%" />
		<button>查找</button>
	</div>
</div>

<div id="contributionsPanel"></div>
<!-- 企业信息窗口 -->
<div id="viewCompanyPanel"></div>





