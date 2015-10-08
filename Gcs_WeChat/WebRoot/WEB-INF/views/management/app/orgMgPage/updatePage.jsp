<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<%@ include file="/WEB-INF/views/scripts.jsp"%>
		<script type="text/javascript">
			var msg = '${MSG}';
			if(msg){
				alert(msg);
				var origin = artDialog.open.origin;
				origin.tableReload();
				art.dialog.close();
			}
			function check(){
				if($('#name').val()==""){
					alert("请输入名称！");
						return false;
				}else{
					return true;
				}
			}
		</script>
  </head>
  
  <body style="background: none repeat scroll 0 0 #eeeeee;">
  <div style="width:500px;">
  <br/>
  	  <div class="col-xs-12 col-sm-3">
		<div class="widget-box">
			<div class="widget-header">
				<h4>组织添加</h4>
					<span class="widget-toolbar">
					</span>
			</div>
		<form class="form-horizontal" method="post" action="${contextPath}/management/organizationMG/updateOrgDate" name="basic_validate" id="basic_validate" novalidate="novalidate" onsubmit="return check()">
			<div class="widget-body">
				<div class="widget-main">
					<div>
						<label for="form-field-mask-1">
							上级组织名称
						<small class="text-success"></small>
						</label>
						<div class="input-group">
                  			<input type="hidden" name="parentids" id="parentids" value="${org.id}" readonly="true">
						</div>
					</div>
					<div>
						<label for="form-field-mask-1">
							组织名称
						<small class="text-success"></small>
						</label>
						<div class="input-group">
							<input class="form-control" type="text" id="name" name="name" style="width:450px" value="${org.name}">
						</div>
					</div>
					<hr>
					<button class="btn btn-info" type="submit">
						<i class="icon-ok bigger-110"></i>
						确定
					</button>
				</div>
			</div>
		 </form>
		</div>
	</div>
  </div>
  </body>
</html>
