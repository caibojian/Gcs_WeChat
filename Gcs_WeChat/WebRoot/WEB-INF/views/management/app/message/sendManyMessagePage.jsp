<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<%@page import="java.util.Date"%> 
<!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<title></title>
		<meta http-equiv="X-UA-Compatible" content="IE=Edge">
		<%@ include file="/WEB-INF/views/easyUI-scripts.jsp"%>
		
		<link href="${contextPath}/resources/multiple/static/css/common.css" rel="stylesheet" type="text/css" />
		<link href="${contextPath}/resources/multiple/static/css/material.css?v=2014030901" rel="stylesheet" type="text/css"/>
		<link href="${contextPath}/resources/multiple/static/css/appmsg_edit.css?v=2014030901" rel="stylesheet" type="text/css"/>

		<link href="${contextPath}/resources/umeditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
	    <script type="text/javascript" charset="utf-8" src="${contextPath}/resources/umeditor/umeditor.config.js"></script>
	    <script type="text/javascript" charset="utf-8" src="${contextPath}/resources/umeditor/umeditor.min.js"></script>
	    <script type="text/javascript" src="${contextPath}/resources/umeditor/lang/zh-cn/zh-cn.js"></script>
		
		<script type="text/javascript">
		var basePath = '${contextPath}';
		
		var toUser,toOrgs='';
		var flig = false;
		 $(document).ready(function(){
			 $("#toUser").click(function(){
				 var toOrgss = $("#toOrgs").combotree("getValues");
				 
				 if(toOrgs == ''||toOrgss.sort().toString()!=toOrgs.sort().toString()){
					 toOrgs = toOrgss;
					 flig = true;
				 }
				
				 if(flig){
					 $('#toUsers').combotree({    
						    url: '${contextPath}/management/msg/userDate?toOrgs='+toOrgs
						});
					 flig = false;	
				 }
				});
			});
		
		</script>
	</head>
	<body>
		<div id="content">
		<form id="fff" method="post" enctype="multipart/form-data"
								action="${contextPath}/management/msg/sendManyWechatMessage">
			<div id="tb" style="overflow-x: hidden; width: auto;">
				<div style="background-color: white; width: 100%;">
					<gcs:panel id="panel3" options="collapsible:true" title="接收信息单位">
						<table style="width: 90%;">
							<tr height="30px">
								<td>接收消息单位：</td>
								
								<td>
								<select id="toOrgs" name="toOrgs" class="easyui-combotree" 
								data-options="url:'${contextPath}/management/organizationMG/treeData?random =',method:'post',cascadeCheck:true,required:true" multiple style="width: 285px;"></select>
								</td>
								<td>接收消息单位：</td>
								<td id="toUser">
								<select id="toUsers" name="toUsers" class="easyui-combotree" data-options="method:'get',cascadeCheck:true" multiple style="width: 285px;"></select>
								</td>
								<td>
									应&nbsp;&nbsp;&nbsp;&nbsp;用:
								</td>
								<td>
									<input id="agentId" class="easyui-combobox" name="agentId" style="width: 245px;" 
									data-options="valueField:'id',textField:'text',url:'${contextPath}/management/msg/agentList',required:true" /> 
								</td>
								<td colspan="4">
									<div style="text-align: center; padding: 5px">
										<a href="javascript:void(0)" class="easyui-linkbutton"
											onclick="sendMsgForm()">发送多图文消息</a>
									</div>
								</td>
							</tr>
						</table>
					</gcs:panel>
				</div>
			</div>
			</form>
			<form id="ff" method="post" enctype="multipart/form-data"
								action="${contextPath}/management/msg/addManyWechatMessage">
			<div id="girdDiv">
				<gcs:layout style="width:100%;height:100%;">
					<gcs:layoutDiv options="region:'center',split:true" title="消息内容">
						<!-- 列表显示 -->
						<div style="padding: 10px 0 10px 10px">
							<table border="0">
								<tr height="35px" width="99%">
									<td>
										标&nbsp;&nbsp;&nbsp;&nbsp;题:
									</td>
									<td>
										<input class="easyui-validatebox" type="text" name="title"
											style="width: 245px;margin:5px;" data-options="required:true"></input>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</td>
									<td>
										简介:
									</td>
									<td>
										<input class="easyui-validatebox" type="text" name="description"
											style="width: 245px;margin:5px;" maxlength="300"
											data-options="required:true"></input>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</td>
								
							    	<td>图片:</td>
							    	<td><input class="easyui-validatebox" type="file" name="title_img_file" style="width: 200px"  
							    	data-options="required:true,validType:'fileType[\'JPG|JPEG\']',invalidMessage:'请选择(JPG|JPEG)格式的图片'"></input></td>
							    	
							    	<td rowspan="3" style="width:330px">
							    		<gcs:panel id="panel3" options="collapsible:true" title="微信消息预览----有  ${fn:length(MsgTempVOlist)}  条消息(最多10条)" 
							    		style="height:450px;padding:10px;background:#fafafa;">
											
										 <div class="main_bd">
											<div class="media_preview_area">
												<div class="appmsg multi editing">
													<div class="appmsg_content" id="js_appmsg_preview">
														<c:forEach items="${MsgTempVOlist}" var="msg" begin="0" end="0">
														<div class="js_appmsg_item" data-id="1" data-fileid=""	id="appmsgItem1">
														
															<div class="appmsg_info">
																<em class="appmsg_date"></em>
															</div>
															<div class="cover_appmsg_item">
																<h4 class="appmsg_title" id="appmsg_title1">
																	<a target="_blank" onclick="return false;" href="javascript:void(0);">标题:${msg.title}</a>
																</h4>
																<div class="appmsg_thumb_wrp">
																	<img src="${msg.picUrl }"  > 
																</div>
																<div class="appmsg_edit_mask">
																	<!-- 
																	<a href="javascript:;" data-id="1" class="icon18_common edit_gray js_edit" onclick="return false;">编辑</a>
																	 -->
																	<a href="javascript:void(0);" onclick="clearForm('${msg.id}');" data-id="2" class="icon18_common del_gray js_del">删除</a>
																</div>
															</div>
														</div>
														</c:forEach>
														<c:forEach items="${MsgTempVOlist}" var="msg1" begin="1" > 
														<div class="appmsg_item js_appmsg_item " data-id="2" data-fileid="" id="appmsgItem2">
															
															<img src="${msg1.picUrl}" id="appmsg_thumb2" class="appmsg_thumb default"> 
															<h4 class="appmsg_title" id="appmsg_title2">
																<a target="_blank" href="javascript:void(0);" onclick="return false;">标题:${msg1.title}</a>
															</h4>
															<div class="appmsg_edit_mask">
																<!-- 
																<a href="javascript:void(0);" onclick="return false;" data-id="2" class="icon18_common edit_gray js_edit">编辑</a>
																 -->
																<a href="javascript:void(0);" onclick="clearForm('${msg1.id}');" data-id="2" class="icon18_common del_gray js_del">删除</a>
															</div>
														</div>
														</c:forEach> 
														
														<!-- js加载更多 -->
														
													</div>
												</div>
											</div>
										</div>
										</gcs:panel>
							    	</td>
								</tr>
								<tr height="35px">
									<td>
										内容：
									</td>
									<td colspan="5">
										<script type="text/plain" id="myEditor" name="text" style="width: 900px; height:240px;">
    									</script>
									</td>
								</tr>
								<tr>
									<td colspan="6">
										<div style="text-align: center; padding: 5px">
											<a href="javascript:void(0)" class="easyui-linkbutton"
												onclick="submitForm()">添加</a>
												<!-- 
											<a href="javascript:void(0)" class="easyui-linkbutton"
												onclick="clearForm()">重置</a>
												 -->
										</div>
									</td>
								</tr>
							</table>
						</div>
					</gcs:layoutDiv>
				</gcs:layout>
			</div>
			</form>
		</div>
		<input  type="hidden" name="size" id="size" value="${fn:length(MsgTempVOlist)}"></input>
	</body>
	<script type="text/javascript">
	 
  $("#girdDiv").css("height",window.screen.height-250<=520?520:window.screen.height-250);
  		function submitForm(){
  			var text = UM.getEditor('myEditor').getContent();
  			//去掉空格
  			//text = text.replace(/(^\s*)|(\s*$)/g,'');
  			var size = $('#size').val();
  			if(parseInt(size)>=10){
  				alert("多图文消息超出最大条数！");
  			}else if(text == ""){
  				alert("内容不能为空！");
  			}else{
  			$.messager.progress();	// 显示进度条
			$('#ff').form('submit',{
				onSubmit: function(){
						var isValid = $(this).form('validate');
						if (!isValid){
							$.messager.progress('close');	// 如果表单是无效的则隐藏进度条
						}
						return isValid;	// 返回false终止表单提交
					},success: function(data){
						$.messager.progress('close');	// 如果提交成功则隐藏进度条
						 var data = eval('(' + data + ')');  
						 if(data.success==true){
						 	art.dialog.through({
							    content: data.msg,
							    icon : '',
							    fixed: true,
							    lock: true,
							    ok: function () {
							    	window.location.href = '${contextPath}/management/msg/sendManyMsgIndex';
							        return true;
							    }
							});
						 }else{
						 	art.dialog.through({
						 		content: data.msg,
							    icon : 'error', 
							    fixed: true,
							    lock: true,
							    ok:true
							});
						 }
					}
			});
  			}
		}
		function clearForm(id){
			$.messager.progress();	// 显示进度条
			$.ajax({    	
				type:"get", 
				async : false,
			    url:'${contextPath}/management/msg/clearTempMsg',
			    data: {tempMsgID:id},
				success:function(data){
					$.messager.progress('close');
					alert("删除成功！");
					window.location.href = '${contextPath}/management/msg/sendManyMsgIndex';
				}
			});
		}
				
		function sendMsgForm(){
			
  			$.messager.progress();	// 显示进度条
			$('#fff').form('submit',{
				onSubmit: function(){
						var isValid = $(this).form('validate');
						if (!isValid){
							$.messager.progress('close');	// 如果表单是无效的则隐藏进度条
						}
						return isValid;	// 返回false终止表单提交
					},success: function(data){
						$.messager.progress('close');	// 如果提交成功则隐藏进度条
						 var data = eval('(' + data + ')');  
						 if(data.success==true){
						 	art.dialog.through({
							    content: data.msg,
							    icon : '',
							    fixed: true,
							    lock: true,
							    ok: function () {
							    	window.location.href = '${contextPath}/management/msg/sendManyMsgIndex';
							        return true;
							    }
							});
						 }else{
						 	art.dialog.through({
						 		content: data.msg,
							    icon : 'error', 
							    fixed: true,
							    lock: true,
							    ok:true
							});
						 }
					}
			});
		}
		
</script>
<script type="text/javascript">
    //实例化编辑器
    var um = UM.getEditor('myEditor');
    um.addListener('blur',function(){
        $('#focush2').html('编辑器失去焦点了')
    });
    um.addListener('focus',function(){
        $('#focush2').html('')
    });
    //按钮的操作
    function insertHtml() {
        var value = prompt('插入html代码', '');
        um.execCommand('insertHtml', value)
    }
    function isFocus(){
        alert(um.isFocus())
    }
    function doBlur(){
        um.blur()
    }
    function createEditor() {
        enableBtn();
        um = UM.getEditor('myEditor');
    }
    function getAllHtml() {
        alert(UM.getEditor('myEditor').getAllHtml())
    }
    function getContent() {
        var arr = [];
        arr.push("使用editor.getContent()方法可以获得编辑器的内容");
        arr.push("内容为：");
        arr.push(UM.getEditor('myEditor').getContent());
        alert(arr.join("\n"));
    }
    function getPlainTxt() {
        var arr = [];
        arr.push("使用editor.getPlainTxt()方法可以获得编辑器的带格式的纯文本内容");
        arr.push("内容为：");
        arr.push(UM.getEditor('myEditor').getPlainTxt());
        alert(arr.join('\n'))
    }
    function setContent(isAppendTo) {
        var arr = [];
        arr.push("使用editor.setContent('欢迎使用umeditor')方法可以设置编辑器的内容");
        UM.getEditor('myEditor').setContent('欢迎使用umeditor', isAppendTo);
        alert(arr.join("\n"));
    }
    function setDisabled() {
        UM.getEditor('myEditor').setDisabled('fullscreen');
        disableBtn("enable");
    }

    function setEnabled() {
        UM.getEditor('myEditor').setEnabled();
        enableBtn();
    }

    function getText() {
        //当你点击按钮时编辑区域已经失去了焦点，如果直接用getText将不会得到内容，所以要在选回来，然后取得内容
        var range = UM.getEditor('myEditor').selection.getRange();
        range.select();
        var txt = UM.getEditor('myEditor').selection.getText();
        alert(txt)
    }

    function getContentTxt() {
        var arr = [];
        arr.push("使用editor.getContentTxt()方法可以获得编辑器的纯文本内容");
        arr.push("编辑器的纯文本内容为：");
        arr.push(UM.getEditor('myEditor').getContentTxt());
        alert(arr.join("\n"));
    }
    function hasContent() {
        var arr = [];
        arr.push("使用editor.hasContents()方法判断编辑器里是否有内容");
        arr.push("判断结果为：");
        arr.push(UM.getEditor('myEditor').hasContents());
        alert(arr.join("\n"));
    }
    function setFocus() {
        UM.getEditor('myEditor').focus();
    }
    function deleteEditor() {
        disableBtn();
        UM.getEditor('myEditor').destroy();
    }
    function disableBtn(str) {
        var div = document.getElementById('btns');
        var btns = domUtils.getElementsByTagName(div, "button");
        for (var i = 0, btn; btn = btns[i++];) {
            if (btn.id == str) {
                domUtils.removeAttributes(btn, ["disabled"]);
            } else {
                btn.setAttribute("disabled", "true");
            }
        }
    }
    function enableBtn() {
        var div = document.getElementById('btns');
        var btns = domUtils.getElementsByTagName(div, "button");
        for (var i = 0, btn; btn = btns[i++];) {
            domUtils.removeAttributes(btn, ["disabled"]);
        }
    }
</script>
</html>
