<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" /> 
 <script type="text/javascript">
  
   			$(function(){
   					$('#repeataudit_list_datagrid').datagrid({    
					   // url:'../../CompanyBasicAction', 
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
		        	        {field:'ck',checkbox:true},
						]],
					    columns:[[    
					        {field:'sArchivesCode1',title:'档案编码',width:150},  
					        {field:'sTaxCode',title:'税务编码',width:150},  
							{field:'sCompanyName',title:'企业名称',width:900,
					        		 formatter:function(value,row,index){
							               var c = '<a href="#" onclick="repeataudit_list.openViewCompany('+index+')">'+value+'</a>';
							                    return c;
							            }
					        } ,
					        {field:'sProcessStatus',title:'流程状态',width:150} ,
					        {field:'action',title:'操作',width:150,align:'center',
							            formatter:function(value,row,index){
							                	var v = '<a href="#" onclick="	repeataudit_list.openAudit('+index+')">复审</a> ';
							                    return v;
							                
							            }
							 }
					    ]],
					     toolbar:'#boolbarTest'//指定自定义菜单
					}); 
				var repeataudit_list_data={total:3,rows:[
								{"sArchivesCode1":"30256545","sTaxCode":"1022012","sCompanyName":"黑龙江省委办公厅黑龙江省委办公厅","sPreliminary":"朱佳音","sPreliminaryDate":"2012-12-12","sReview":"焦阳","sReviewDate":"2013-12-13","sProcessStatus":"未初审"},
								{"sArchivesCode":"34553242","sTaxCode":"2342012","sCompanyName":"黑龙江省委老干部局","sPreliminary":"朱佳音","sPreliminaryDate":"2012-12-12","sReview":"焦阳","sReviewDate":"2013-12-13","sProcessStatus":"未复审"},
								{"sArchivesCode":"45656545","sTaxCode":"8905432","sCompanyName":"中国人寿保险股份有限公司绥化分公司","sPreliminary":"朱佳音","sPreliminaryDate":"2012-12-12","sReview":"焦阳","sReviewDate":"2013-12-13","sProcessStatus":"待缴款"},
								
							]};
				//加载前台数据		
				$("#repeataudit_list_datagrid").datagrid("loadData", repeataudit_list_data);
  			       
   			});
   			
   	repeataudit_list={};
   	
   	/*
   	打开单位初审面板
   	*/
   	repeataudit_list.openAudit=function(index){
   			$('#auditPanel').window({
					width : 1200,
					href : 'repeatsecurity/repeataudit',
					height : 550,
					title : '年审单位复审',
					minimizable : false,
					loadingMessage : '正在加载，请稍后。',
					iconCls : 'icon-save',
					modal:true//模态窗口
			});
   	};
  	/* 
		查看企业信息框
	 */
	repeataudit_list.openViewCompany = function(index) {
		$('#auditPanel').window({
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
		<table id="repeataudit_list_datagrid"></table>
		
		<!-- 自定义菜单 -->
		<div id="boolbarTest" data-options="fit:false,doSize:false" style="white-space: nowrap;height: 50px;margin-top: 5px">
		    <div style="text-align: center;">
		    	年审单位复审-------------<select>
		    		<option>2012年</option>
		    	</select>
		    	<button onclick="addCompany()">---</button>
		    	
		    </div>
			<div>
					<input type="" style="width: 50px" />		
					<input type="" style="width: 50px" />	
					<input type="" style="width: 68%" />
					<button>查找</button>
			</div>
		</div>
	<!-- 复审窗口 -->
<div id="auditPanel"></div>


