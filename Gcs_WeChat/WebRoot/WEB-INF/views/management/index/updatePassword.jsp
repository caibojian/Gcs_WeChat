<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title></title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/WEB-INF/views/easyUI-scripts.jsp"%>
</head>

  <body style="width: 340px;">
  	<gcs:panel id="passwordPanel">
  		<form id="updateForm">
  			<table align="center" style="margin-top: 5px;">
						<tr>
							<td>新密码：</td>
							<td><input id="password" name="password" type="password"
								class="easyui-validatebox" style="height: 22px;"
								data-options="required:true" /></td>
						</tr>
						<tr>
							<td>确认新密码：</td>
							<td><input id="rPassword" name="rPassword" type="password"
								class="easyui-validatebox" style="height: 22px;"
								data-options="required:true" validType="equals['#password']" />
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center"><a id="subbtn"
								class="button glow button-rounded button-tiny"
								style="width: 50px; cursor: pointer;" onclick="updatePass();"><i
									class="icon-save"></i>保存</a></td>
						</tr>
					</table>
  		</form>
  	</gcs:panel>
  	<script type="text/javascript">
  		function updatePass() {
			if ($('#password').val() == "") {
				alert("新密码不能为空！");
			} else if ($('#rPassword').val() == "") {
				alert("确认新密码不能为空！");
			} else if ($('#password').val() != $('#rPassword').val()) {
				alert("两次输入密码不一致！");
			} else {
				$.post("${contextPath }/management/index/updatePwd", {
					plainPassword : $('#password').val(),
					rPassword : $('#rPassword').val()
				}, function(data) {
					alert(data.message);
					$('#password').val('');
					$('#rPassword').val('');
					art.dialog.close();
					//$('#updatePass').window('close');
				}, "json");
			}
		}
  	</script>
  </body>
</html>
