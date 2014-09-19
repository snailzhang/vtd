

//单位档案查询
var archivesQuery={}; 


/*
 * 加载单位数据
 * */
archivesQuery.loadData=function(){
	$('#archivesQuery_grid').datagrid({    
			pageSize : viewCommon.paging,
			pageList : viewCommon.pagingArray,
		    pagination:true, //是否显示分页栏 
		    doSize:true,//在面板被创建的时候将重置大小和重新布局。
		    fit:true,	//面板大小将自适应父容器
	  	 	fitColumns:false,//是否显示横向滚动条，未生效
	  	 	nowrap:false,//如果为true，则在同一行中 。设置为true可以提高加载性能 
	  	 	idField:'id',
			rownumbers:false,//显示行号
			striped:true,//奇偶行使用不同背景色
			frozenColumns:[[
 	        {field:'ck',checkbox:true},
			]],
		    columns:[[    
		     {field:'sArchi2vesCode',title:'年度',width:100},  
		        {field:'sArchivesCode',title:'档案编码',width:100},  
		        {field:'sTaxCode',title:'税务编码',width:100},  
		        {field:'sCompanyName',title:'企业名称',width:300,
		        		 formatter:function(value,row,index){
				               var c = '<a href="#" onclick="viewCommon.openViewCompany('+index+')">'+value+'</a>';
				                    return c;
				            } } ,
		        {field:'s1',title:'企业地址',width:300}, 
		        {field:'sTax1Code',title:'法人代表',width:100}, 
		        {field:'sTax1Co2de',title:'经办人',width:100}, 
		        {field:'2Tax1Co2de',title:'单位性质',width:100}, 
		        {field:'2Tax11Co2de',title:'隶属关系',width:100}, 
		        {field:'22Tax11Co2de',title:'营业状况',width:100}, 
		        {field:'22Tax11Cos2de',title:'企业总人数',width:100},
		        {field:'22Tax11sCos2de',title:'残疾职工总人数',width:100} ,
		        {field:'22Tax11sC1os2de',title:'所在地区',width:100} 
		    ]],
		     toolbar:'#archives_query_boolbar'//指定自定义菜单
		});
};