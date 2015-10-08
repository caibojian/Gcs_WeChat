<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<title></title>
		<meta http-equiv="X-UA-Compatible" content="IE=Edge">
		<%@ include file="/WEB-INF/views/easyUI-scripts.jsp"%>
		
		<%-- <%@ taglib uri="http://ckeditor.com" prefix="ckeditor"%> --%>
		<script type="text/javascript">
		var basePath = '${contextPath}';
	</script>
		<script type="text/javascript"
			src="${contextPath}/resources/ckeditor/ckeditor.js"></script>
	</head>
	<body>
		<div id="content">
		<form id="ff" method="post" enctype="multipart/form-data"
								action="${contextPath}/management/article/zxdtSave">
			<div id="girdDiv">
				<gcs:layout style="width:100%;height:100%;">
					<gcs:layoutDiv options="region:'center',split:true" title="消息内容">
						<!-- 列表显示 -->
						<div style="padding: 10px 0 10px 60px">
							<table border="0">
								<tr height="35px" wedth="90%">
									<td>
										标&nbsp;&nbsp;&nbsp;&nbsp;题:
									</td>
									<td>
										<input class="easyui-validatebox" type="text" name="title"
											style="width: 800px;" data-options="required:true" maxlength="120"></input>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<input type="hidden" name="articleType" id="articleType" value="1">
									</td>
								</tr>
								<tr height="35px">
									<td>
										文章内容：
									</td>
									<td colspan="5">
										<textarea id="contentText" name="content"
											style="width: 98%; height: 600px;"></textarea>
										<ckeditor:replace replace="contentText"
											basePath="${contextPath}/resources/ckeditor/"></ckeditor:replace>
											
									</td>
								</tr>
								<tr>
									<td colspan="6">
										<div style="text-align: center; padding: 5px">
											<a href="javascript:void(0)" class="easyui-linkbutton"
												onclick="submitForm()">发布</a>
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
	</body>
	<script type="text/javascript">
	
  $("#girdDiv").css("height",window.screen.height-250<=520?520:window.screen.height-250);
  		function submitForm(){
  			var text = CKEDITOR.instances.contentText.getData();
  			if(text == ""){
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
								    	window.location.href = '${contextPath}/management/msg/msgListIndex';
								        return true;
								    },
								    cancelVal: '继续发消息',
								    cancel: true //为true等价于function(){}
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
</html>
