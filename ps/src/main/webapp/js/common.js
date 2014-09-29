var checkout = {};
checkout.text = {};
checkout.file = {};
checkout.text.isempty = function(str,message){//校验文本是否为空
	var result = false;
	var text = str.replace(/[ ]/g,"");
	if(text == ""){
		result = true;
		if(message !="")alert(message);
	};
	return result;
};
checkout.file.fileType = function(fileName,fileType,message){
	var result = false;
	var index = 0;
	var splitName= fileName.split(".");
	index = splitName.length-1;
	var type = splitName[index];
	if(type == fileType){
		result = true;
	}else{
		if(message !="")alert(message);
	}
	return result;
};