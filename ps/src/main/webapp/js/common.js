var checkout = {};
checkout.text = {};
checkout.file = {};
checkout.text.isempty = function(obj,message){//校验文本是否为空
	var result = false;
	var text = obj.val();
	var help = obj.next(".help-block");
	if(help.length == 0){
		help = obj.parent().next(".help-block");
	}
	text = text.replace(/[ ]/g,"");
	if(text == ""){
		result = true;
		if(message !=""){
			help.css("color","red").text(message);
		};
	}else{
		help.text("");
	};
	return result;
};
checkout.file.fileType = function(obj,fileType,message){
	var result = false;
	var index = 0;
	var splitName= obj.val();
	splitName = splitName.split(".");
	index = splitName.length-1;
	var type = splitName[index];
	var help = obj.next(".help-block");
	if(help.length == 0){
		help = obj.parent().next(".help-block");
	}
	if(type == fileType){//类型匹配，返回true
		result = true;
		help.text("");
	}else{
		if(message !=""){
			help.css("color","red").text(message);
		}
	}
	return result;
};
var page = {};
page.creatPageHTML = function(nowPage,pageCount,pageDom,onClickFn){
	var nPage = 0;//当前页
	var pCount = 0;//总页数
	nPage = parseInt(nowPage);
	pCount = pageCount;
	
	
	var moreStr = "<li><a href='#'>...</a></li>";//...
	var lastPage = "";//上一页
	var nextPage = "";//下一页
	var inputStr = "<li><input class='pageGoText' type='text' value='' \><a class='pageGoBtn' href='#'>GO</a></li>";//跳转页面输入框

	var pages = "";
	
	if(pCount<8){//页面总数少于8时，全部列出
		for(var i=1;i<pCount+1;i++){
			if(i==1){//第一页
				if(nPage == 1){
					pages = "<li class='active'><a href='#' onClick='"+onClickFn+"(1)'>1</a>";
				}else{
					pages = "<li><a href='#' onClick='"+onClickFn+"(1)'>1</a>";
				}
			}else{//其他页
				if(nPage == i){
					pages += "<li class='active'><a href='#' onClick='"+onClickFn+"("+nPage+")'>"+nPage+"</a></li>";
				}else{
					pages += "<li><a href='#' onClick='"+onClickFn+"("+i+")'>"+i+"</a></li>";
				}	
			}
		}
		if(nPage == 1||nPage == 2){//页面为首页的时候，上一页等于第一页
			lastPage = "<li><a class='lastPage' href='#' onClick='"+onClickFn+"(1)'>&laquo;</a></li>";
		}else{
			var lastNum = nPage-1;
			lastPage = "<li><a class='lastPage' href='#' onClick='"+onClickFn+"("+lastNum+")'>&laquo;</a></li>";
		}
		if(nPage == pCount){//页面为最后一页时，下一页为最后一页
			nextPage = "<li><a class='lastPage' href='#' onClick='"+onClickFn+"("+pCount+")'>&raquo;</a></li>";
		}else{
			var nextNum = nPage+1;
			nextPage = "<li><a class='lastPage' href='#' onClick='"+onClickFn+"("+nextNum+")'>&raquo;</a></li>";
		}
		
	}else{//页面总数大于等于8时
		pages = "<li><a href='#' onClick='"+onClickFn+"(1)'>1</a></li>";
		if(nPage != 1){
			if(nPage - 1 > 2){
				pages += moreStr;
				for(var i=2;i>0;i--){
					var num = nPage-i;
					pages += "<li><a href='#' onClick='"+onClickFn+"("+num+")'>"+num+"</a></li>";
				}
				if(pCount - nPage > 2)pages += "<li class='active'><a href='#' onClick='"+onClickFn+"("+nPage+")'>"+nPage+"</a></li>";
			}else if(nPage - 1 == 2){
				var num = nPage-1;
				pages += "<li><a href='#' onClick='"+onClickFn+"("+num+")'>"+num+"</a></li>";
				if(pCount - nPage > 2)pages += "<li class='active'><a href='#' onClick='"+onClickFn+"("+nPage+")'>"+nPage+"</a></li>";
			}else{
				if(pCount - nPage > 2)pages += "<li class='active'><a href='#' onClick='"+onClickFn+"("+nPage+")'>"+nPage+"</a></li>";
			}
			var lastNum = nPage-1;
			if(nPage-1 == 1){
				lastPage = "<li><a class='lastPage' href='#' onClick='"+onClickFn+"(1)'>&laquo;</a></li>";
			}else{
				lastPage = "<li><a class='lastPage' href='#' onClick='"+onClickFn+"("+lastNum+")'>&laquo;</a></li>";
			}
		}else{
			lastPage = "<li><a class='lastPage' href='#' onClick='"+onClickFn+"(1)'>&laquo;</a></li>";
			pages = "<li class='active'><a href='#' onClick='"+onClickFn+"(1)'>1</a></li>";
		}
		
		if(nPage != pCount){
			if(pCount - nPage > 2){
				for(var i=1;i<3;i++){
					var num = nPage+i;
					pages += "<li><a href='#' onClick='"+onClickFn+"("+num+")'>"+num+"</a></li>";
				}
				pages += moreStr;
			}else if(pCount - nPage == 2){
				var num = nPage+1;
				if(nPage - 1 > 2)pages += "<li class='active'><a href='#' onClick='"+onClickFn+"("+nPage+")'>"+nPage+"</a></li>";
				pages += "<li><a href='#' onClick='"+onClickFn+"("+num+")'>"+num+"</a></li>";
			}else{
				if(nPage - 1 > 2)pages += "<li class='active'><a href='#' onClick='"+onClickFn+"("+nPage+")'>"+nPage+"</a></li>";
			}
			pages += "<li><a href='#' onClick='"+onClickFn+"("+pCount+")'>"+pCount+"</a></li>";
			nextPage = "<li><a class='lastPage' href='#' onClick='"+onClickFn+"("+(nPage+1)+")'>&raquo;</a></li>";
		}else{
			if(pCount!==1)pages += "<li class='active'><a href='#' onClick='"+onClickFn+"("+pCount+")'>"+pCount+"</a></li>";
			nextPage = "<li><a class='lastPage' href='#' onClick='"+onClickFn+"("+pCount+")'>&raquo;</a></li>";
		}
	}
	pageDom.append(lastPage+pages+nextPage+inputStr);
	//$("#pageGoText").blur(function(){trunToPage(pCount,pName,pType)});
};