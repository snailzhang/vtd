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