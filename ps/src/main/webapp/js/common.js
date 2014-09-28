var checkout = {};
checkout.text = {};
checkout.text.isempty = function(str,message){//校验文本是否为空
	var result = false;
	var text = str.replace(/[ ]/g,"");
	if(text == ""){
		result = true;
		if(message !="")alert(message);
	};
	return result;
};