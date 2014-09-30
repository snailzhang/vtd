var checkout = {};
checkout.text = {};
checkout.file = {};
checkout.text.isempty = function(obj,message){//校验文本是否为空
	var result = false;
	var text = obj.val();
	text = text.replace(/[ ]/g,"");
	if(text == ""){
		result = true;
		if(message !=""){
			obj.next(".help-block").css("color","red").text(message);
		};
	}else{
		obj.next(".help-block").text("");
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
	if(type == fileType){
		result = true;
		obj.next(".help-block").text("");
	}else{
		if(message !=""){
			obj.next(".help-block").css("color","red").text(message);
		}
	}
	return result;
};