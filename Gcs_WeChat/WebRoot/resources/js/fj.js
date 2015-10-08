function isWdFjExist(value){
	$.ajax({
	   type: "POST",
	   url: path+"/management/cjw/szy/fjgl/isFjExist",
	   data: {
		   "fjid":value
	   },
	   async: false,
	   success: function(data){
		   if(data == "true"){
			   $.extend($.messager.defaults, {
				   	ok: "在线预览",
				   	cancel: "下载"
			   });
			   $.messager.confirm('提示:','在线预览或下载附件?',function(event){   
				   if(event){   
					  window.open(path+"/management/cjw/szy/fjgl/viewFj?fjid="+encodeURI(encodeURI(value)));
					  
				   }else{   
					   window.open(path+"/management/cjw/szy/fjgl/fjDownload?fjid="+encodeURI(encodeURI(value)));
				   }   
			   });
		   }else{
			   alert('附件不存在!');
		   }
	   }
	});
}

function isFjExist(value,type){
	$.ajax({
	   type: "POST",
	   url: path+"/management/cjw/szy/fjgl/isFjExist",
	   data: {
		   "fjid":value
	   },
	   async: false,
	   success: function(data){
		   if(data == "true"){
			   if(type == "1")
				   window.open(path+"/management/cjw/szy/fjgl/viewFj?fjid="+encodeURI(encodeURI(value)));
			  
			   else
				   window.open(path+"/management/cjw/szy/fjgl/fjDownload?fjid="+encodeURI(encodeURI(value)));
		   }else{
			   alert('附件不存在!');
		   }
	   }
	});
}
//判断附件是否是多个
function fjNum(wh,code){
	$.ajax({
	   type: "POST",
	   url: path+"/management/cjw/szy/fjgl/fjNum",
	   data: {
		   wh:wh,
		   gljdCode:code
	   },
	   dataType: "json",
	   async: false,
	   success: function(data){
		   if(data.exist == "0"){
			   alert('附件不存在!');
		   }else if(data.exist == "1"){
			   alert('1');
			   $.extend($.messager.defaults, {
				   	ok: "在线预览",
				   	cancel: "下载"
			   });
			   $.messager.confirm('提示:','在线预览或下载附件?',function(event){   
				   if(event){   
					  window.open(path+"/management/cjw/szy/fjgl/viewFj?fjid="+encodeURI(encodeURI(data.fjid)));
				   }else{   
					   window.open(path+"/management/cjw/szy/fjgl/fjDownload?fjid="+encodeURI(encodeURI(data.fjid)));
				   }   
			   });
		   }else{
			   var left_p = ($(window).width() - 700) * 0.5;
			   if(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE9.0"){ 
				   left_p = Math.abs(left_p)*0.5;
			   } 
			   $('#fjlist').window({
				    width:700,
				    height:400,
				    maximizable:false,
				    modal:true,
				    left:left_p
				});
				$('#fjlist').window('open');
				$('#fjlistData').datagrid({url:path+'/management/cjw/szy/fjgl/fjListDate',method:'post',queryParams:{wh:wh,gljdCode:code}}); 
		   }
	   }
	});
}