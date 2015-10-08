 /** @serializedParams looks like "prop1=value1&prop2=value2".  
Nested property like 'prop.subprop=value' is also supported **/
function paramString2obj (serializedParams) {
	
	var obj={};
	function evalThem (str) {
		var attributeName = str.split("=")[0];
		var attributeValue = str.split("=")[1];
		if(!attributeValue){
			return ;
		}
		
		var array = attributeName.split(".");
		for (var i = 1; i < array.length; i++) {
			var tmpArray = Array();
			tmpArray.push("obj");
			for (var j = 0; j < i; j++) {
				tmpArray.push(array[j]);
			};
			var evalString = tmpArray.join(".");
			// alert(evalString);
			if(!eval(evalString)){
				eval(evalString+"={};");				
			}
		};
    
		eval("obj."+attributeName+"='"+attributeValue+"';");
	};

	var properties = serializedParams.split("&");
	for (var i = 0; i < properties.length; i++) {
		evalThem(properties[i]);
	};

	return obj;
}


$.fn.form2json = function(){
	var serializedParams = this.serialize();
	var obj = paramString2obj(serializedParams);
	var str = decodeURIComponent(JSON.stringify(obj))
	return $.parseJSON(str);
}
/**
 * 弹出art窗口
 */
function showWindow(title,url){
	 art.dialog.open(url, {title: title});
}

/**
 * 鼠标提示
 * @param value
 * @param row
 * @param index
 * @return
 */
function addTitle(value,row,index){
	if(value){
		return "<span title='"+value+"'>"+value+"</span>";
	}else{
		return "";
	}
}

/**
 * 根据文号和业务ID预览附件
 * @param value
 * @return
 */
function fjylByWhAndYwid(value,flag,ywid){
	if(value&&flag=='true'){
		return	"<a href='javascript:void(0);' title='"+value+"' onclick='viewFjList(\""+value+"\",\"\",\""+ywid+"\");return false;'>"+value+"</a>";
	}else{
		if(value){
			return value;
		}else{
			return "";
		}
	}
}

function fjylWjmcByWhAndYwid(wh,wjmc,flag,ywid){
	if(wh&&flag=='true'){
		return	"<a href='javascript:void(0);' title='"+wjmc+"' onclick='viewFjList(\""+wh+"\",\"\",\""+ywid+"\");return false;'>"+wjmc+"</a>";
	}else{
		if(wjmc){
			return wjmc;
		}else{
			return "";
		}
	}
}

/**
 * 根据文号预览附件（一个文号可能包含多个文档）
 * @param value
 * @return
 */
function fjyl(value,flag){
	if(value&&flag=='true'){
		return	"<a href='javascript:void(0);' title='"+value+"' onclick='viewFjList(\""+value+"\",\"\",\"\");return false;'>"+value+"</a>";
	}else{
		if(value){
			return value;
		}else{
			return "";
		}
	}
}
/**
 * 根据文号预览   如果没有返回文件名称
 * @param wh
 * @param wjmc
 * @param flag
 * @return
 */
function fjylWjmc(wh,wjmc,flag){
	if(wh&&flag=='true'){
		return	"<a href='javascript:void(0);' title='"+wjmc+"' onclick='viewFjList(\""+wh+"\",\"\",\"\");return false;'>"+wjmc+"</a>";
	}else{
		if(wjmc){
			return wjmc;
		}else{
			return "";
		}
	}
}

function fjylByFjid(value,zh,flag){
	if(value&&flag=='true'){
		return	"<a href='javascript:void(0);' title='"+zh+"' onclick='fjylByid(\""+value+"\",\"\");return false;'>"+zh+"</a>";
	}else{
		if(zh){
			return zh;
		}else{
			return "";
		}
	}
}

/**
 * 判断附件是否是多个，并且弹出附件列表
 */
function viewFjList(wh,code,ywid){
	$.ajax({
		   type: "POST",
		   url: path+"/management/cjw/szy/fjgl/fjNum",
		   data: {
			   wh:wh,
			   gljdCode:code,
			   ywid:ywid
		   },
		   dataType: "json",
		   async: false,
		   success: function(data){
			   if(data.exist == "0"){
				   art.dialog.through({
					   	title: '提示',
					   	icon: 'warning',
					    content: '附件不存在',
					    lock:true
				   });
			   }else if(data.exist == "1"){
				   art.dialog.through({
					   	title: '提示',
					    content: '在线预览或下载附件?',
					    lock:true,
					    button: [
					             {
					                 name: '在线预览',
					                 callback: function () {
					            	 	fjzxyl(data.fjid);
					                 }
					             },
					             {
					                 name: '下载',
					                 callback: function () {
					            	 	fjxz(data.fjid);
					               }
					             }
					         ]
				   });
			   }else{
				   var _openUrl = path+'/management/cjw/szy/fjgl/fjListDataIndex?wh='+encodeURI(encodeURI(wh))+'&gljdCode='+code+'&ywid='+ywid;
				   art.dialog.open(_openUrl, {title: '附件查看'});
			   }
		   }
	});
}
/**
 * 根据id附件在线预览
 */
function fjzxyl(fjid){
	window.open(path+"/management/cjw/szy/fjgl/viewFj?fjid="+encodeURI(encodeURI(fjid)));
}

/**
 * 根据id附件下载
 */
function fjxz(fjid){
	window.open(path+"/management/cjw/szy/fjgl/fjDownload?fjid="+encodeURI(encodeURI(fjid)));
}
/**
 * 根据ID直接进行附件预览
 */
function fjylByid(fjid){
	$.ajax({
		   type: "POST",
		   url: path+"/management/cjw/szy/fjgl/isFjExist",
		   data: {
			   "fjid":fjid
		   },
		   async: false,
		   success: function(data){
			   if(data == "true"){
				   art.dialog.through({
					   	title: '提示',
					    content: '在线预览或下载附件?',
					    lock:true,
					    button: [
					             {
					                 name: '在线预览',
					                 callback: function () {
					            	 	fjzxyl(fjid);
					                 }
					             },
					             {
					                 name: '下载',
					                 callback: function () {
					            	 	fjxz(fjid);
					               }
					             }
					         ]
				   });
			   }else{
				   art.dialog.through({
					   	title: '提示',
					   	icon: 'warning',
					    content: '附件不存在',
					    lock:true
				   });
			   }
		   }
		});
}

/**
 * 按照附件ID进行附件预览
 * @param value
 * @param row
 * @param index
 * @return
 */
function operat(value,row,index){
	var btns=[];
	var lookBtn = "";
	var downLoadBtn = "";
	if(row.isFjExist=='true')
		lookBtn = "<a href='javascript:void(0);' onclick=\"fjzxyl('"+value+"');return false;\">在线预览</a>";
	else
	    lookBtn = "在线预览";
	btns.push(lookBtn);
	if(row.isFjExist=='true')
	    downLoadBtn = "<a href='javascript:;' onclick=\"fjxz('"+value+"');return false;\">下载</a>";
	else
		downLoadBtn = "下载";	
		btns.push(downLoadBtn);
	return btns.join(" ");
}

/**
 * 设置jqueryeasyui  commboxtree 的值
*/
function _setCommboxtreeValue(value,id,splitStr){
	var zrdwValue = [];
	if(value){
		for(var i=0;i<value.split(splitStr).length;i++){
			if(value.split(splitStr)[i]){
				zrdwValue.push(value.split(splitStr)[i]);
			}
		}
	}
	$('#'+id).combotree('setValues', zrdwValue);
}	