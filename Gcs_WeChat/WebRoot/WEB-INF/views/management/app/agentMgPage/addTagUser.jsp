<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>组织管理</title>
<meta name="description" content="人员添加" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/WEB-INF/views/easyUI-scripts.jsp"%>

<script
	src="${contextPath}/resources/validator-0.7.0/jquery-1.7.2.min.js"
	type="text/javascript"></script>
<script type="text/javascript"
	src="${contextPath}/resources/validator-0.7.0/jquery.validator.js"></script>
<script type="text/javascript"
	src="${contextPath}/resources/validator-0.7.0/local/zh_CN.js"></script>
<link rel="stylesheet" type="text/css"
	href="${contextPath}/resources/validator-0.7.0/jquery.validator.css" />
<script src="${contextPath}/resources/validator-0.7.0/formValidator.js"
	type="text/javascript"></script>

<script type="text/javascript">
	var msg = '${MSG}';
	var cfmsg = '${CFMSG}';
	if (msg) {
		alert(msg);
		var origin = artDialog.open.origin;
		origin.reloadGrid();
		art.dialog.close();
	}
	if(cfmsg){
		alert(cfmsg);
	}
	
		function checkForm() {
			
			$('form').submit();
		}
	
</script>
</head>
<body>
	<div class="" style="width:500px;">
		<div class="page-header">
			
		</div>
		<div class="col-xs-12">
			<!-- PAGE CONTENT BEGINS -->
			<form class="form-horizontal"
				action="${contextPath}/management/agent/addTagUser"
				method="post">
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right"
						for="form-field-5">应用可见：</label>

					<div class="col-sm-9">
						<div class="clearfix">
							<input id="users" name="users" class="easyui-combotree" data-options="url:'${contextPath}/management/msg/treeUsers',method:'post',cascadeCheck:true,required:true" multiple />
							<input id="agentID" name="agentID" type="hidden" value="${agentID }">
						</div>

						<div class="space-2"></div>

						<div class="help-block" id="input-span-slider"></div>
					</div>
				</div>




				<div class="clearfix form-actions">
					<div class="col-md-offset-3 col-md-9">
						<button class=" button glow button-rounded button-tiny" type="button" onclick="checkForm()">
							<i class="icon-ok bigger-110"></i> 确定
						</button>

					</div>
				</div>

			</form>
		</div>
	</div>

	<script type="text/javascript">
		 $(':radio[name="mjxb"]').eq('${user.mjxb }').attr("checked",true);
	</script>
	<script
		src="${contextPath}/resources/assets/js/jquery-ui-1.10.3.full.min.js"></script>
	<script
		src="${contextPath}/resources/assets/js/jquery.ui.touch-punch.min.js"></script>

</body>
</html>
