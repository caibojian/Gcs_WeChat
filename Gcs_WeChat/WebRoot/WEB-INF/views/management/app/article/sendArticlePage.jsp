<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<!DOCTYPE HTML>
<html>
<head>

    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <title>文章发布</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    
    <%@ include file="/WEB-INF/views/easyUI-scripts.jsp"%>
    
    <link href="${contextPath}/resources/umeditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" charset="utf-8" src="${contextPath}/resources/umeditor/umeditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${contextPath}/resources/umeditor/umeditor.min.js"></script>
    <script type="text/javascript" src="${contextPath}/resources/umeditor/lang/zh-cn/zh-cn.js"></script>
</head>
<body>
<div id="content">
		<div class="navbar navbar-default" id="navbar" style="background: url(${contextPath}/resources/img/banner_bg.jpg);">
			<img alt="" src="${contextPath}/resources/img/my_logo_01.png">
		
		</div>
		<form id="ff" method="post" enctype="multipart/form-data"
								action="${contextPath}/management/article/zxdtSave">
			<div id="girdDiv">
				<!-- 列表显示 -->
				<div style="padding: 10px 0 10px 40px">
					<table border="0">
						<tr height="35px" wedth="90%">
							<td>
								<h3>文章标题:</h3>
							</td>
							<td>
								<input class="easyui-validatebox" type="text" name="title"
									style="width: 800px;margin : 20px 5px 15px 5px;" data-options="required:true" maxlength="120"></input>
								<input type="hidden" name="articleType" id="articleType" value="${articleType}">
								<input type="hidden" name="bak1" id="bak1" >
							</td>
						</tr>
						<tr height="35px">
							<td colspan="6">
								<script type="text/plain" id="myEditor" name="content" style="width: 900px; height:240px;">
    							</script>	
							</td>
						</tr>
						<tr>
							<td colspan="6">
								<div style="text-align: center; padding: 20px">
									<a href="javascript:void(0)" class="easyui-linkbutton"
										onclick="submitForm()">发布</a>
										
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			</form>
		</div>

<script type="text/javascript">
	 
  		function submitForm(){
  			var stemTxt=UM.getEditor('myEditor').getPlainTxt(); //取得纯文本  
  			$('#bak1').val(stemTxt);
  			var text = UM.getEditor('myEditor').getContent();
  			//$('#content').val(text);
  			if(!UM.getEditor('myEditor').getContentTxt()){
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
								    	//window.location.href = '${contextPath}/management/article/zxdtListIndex';
								    	var origin = artDialog.open.origin;
										origin.reloadGrid();
										art.dialog.close();
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
		function clearForm(){
			$('#ff').form('clear');
		}
				
		
		var chart;
		
		var d;

		function clearCheckBox(){
			 $("input[name='hour']:checkbox").each(function(){ 
				 $(this).attr("checked",false);
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

</body>
</html>