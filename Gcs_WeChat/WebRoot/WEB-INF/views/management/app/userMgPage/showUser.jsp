<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>组织管理</title>
		<meta name="description" content="人员信息详细" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<%@ include file="/WEB-INF/views/easyUI-scripts.jsp"%>
		
		<script src="${contextPath}/resources/validator-0.7.0/jquery-1.7.2.min.js" type="text/javascript"></script>
        <script type="text/javascript" src="${contextPath}/resources/validator-0.7.0/jquery.validator.js"></script>
		<script type="text/javascript" src="${contextPath}/resources/validator-0.7.0/local/zh_CN.js"></script>
		<link rel="stylesheet" type="text/css" href="${contextPath}/resources/validator-0.7.0/jquery.validator.css"/>
		<script src="${contextPath}/resources/validator-0.7.0/formValidator.js" type="text/javascript"></script>
		
		<script type="text/javascript">
			var msg = '${MSG}';
			var sbmsg = '${SBMSG}';
			if(msg){
				alert(msg);
				var origin = artDialog.open.origin;
				origin.reloadGrid();
				art.dialog.close();
			}
			
			if(sbmsg){
				alert(sbmsg);
			}
			
			
			
			function check(){
				if($('#name').val()==""){
					alert("请输入姓名！");
						return false;
				}else if($('#policeID').val()==""){
					alert("请输入警员ID！");
						return false;
				}else if($('#mobile').val()==""){
					alert("请输入手机号码！");
						return false;
				}else if($('#email').val()==""){
					alert("请输入电子邮箱！");
						return false;
				}else{
					return true;
				}
			}
			
	function checkForm(){
		$('form').validator({
			rules: {
	        	mobile: [/^1[3458]\d{9}$/, '请检查手机号格式']
	    	},
			fields: {
				'name':{
					 rule:"姓名:required;",
					 tip:"请输入姓名",
					 ok:"输入正确"
				 },
				 'policeID':{
					 rule:"编号:required;",
					 tip:"请输入警员编号",
					 ok:"输入正确"
				 },
			 	'email':{
					 rule:"邮箱:required;email",
					 tip:"请输入正确邮箱格式",
					 ok:"输入正确"
				 },
				 'mobile':{
					 rule:"手机号:required;mobile",
					 tip:"请输入正确手机号",
					 ok:"输入正确"
				 }
		   	}
		});
		$('form').trigger("validate");
		$('form').submit();
}
	
		</script>
	</head>
	<body>
	<div class="" id="content" style="width:500px">
			<div class="page-header">
				
			</div>
		<div class="col-xs-12">
			<!-- PAGE CONTENT BEGINS -->
			<form class="form-horizontal" action="${contextPath}/management/userMg/synWechatUser" method="post"> 
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right"
						for="form-field-5">警员姓名：</label>

					<div class="col-sm-9">
						<div class="clearfix">
							<input type="text" id="name" name="name"  class="col-xs-5 col-sm-5" value="${user.name }" readonly="true">
							<input type="hidden" id="userid" name="userid"  class="col-xs-5 col-sm-5" value="${user.userid }">
						</div>

						<div class="space-2"></div>

						<div class="help-block" id="input-span-slider"></div>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right"
						for="form-field-5">警员编号：</label>

					<div class="col-sm-9">
						<div class="clearfix">
							<input type="text" id="policeID" name="policeID"  class="col-xs-5 col-sm-5" value="${user.policeID }" readonly="true">
						</div>

						<div class="space-2"></div>

						<div class="help-block" id="input-span-slider"></div>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right"
						for="form-field-5">所属部门：</label>

					<div class="col-sm-9">
						<div class="clearfix">
							<select class="easyui-combotree col-xs-5 col-sm-5" data-options="url:'${contextPath}/management/organizationMG/treeData',method:'post',valueField:'id',textField:'text',panelHeight:'300',required:true,value:'${user.department }'" name="department" id="department" readonly="true">
							</select>
						</div>
						<div class="space-2"></div>

						<div class="help-block" id="input-span-slider"></div>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right"
						for="form-field-5">手机号码：</label>

					<div class="col-sm-9">
						<div class="clearfix">
							<input type="text" id="mobile" name="mobile"  class="col-xs-5 col-sm-5" value="${user.mobile }" readonly="true">
						</div>

						<div class="space-2"></div>

						<div class="help-block" id="input-span-slider"></div>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right"
						for="form-field-5">电子邮箱：</label>

					<div class="col-sm-9">
						<div class="clearfix">
							<input type="text" class="col-xs-5 col-sm-5" id="email" name="email" value="${user.email }" readonly="true">
						</div>

						<div class="space-2"></div>

						<div class="help-block" id="input-span-slider"></div>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right"
						for="form-field-5">微信号码：</label>

					<div class="col-sm-9">
						<div class="clearfix">
							<input class="col-xs-5 col-sm-5" type="text" id="weixinid" name="weixinid" value="${user.weixinid }" readonly="true">
						</div>

						<div class="space-2"></div>

						<div class="help-block" id="input-span-slider"></div>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right"
						for="form-field-5">职位信息：</label>

					<div class="col-sm-9">
						<div class="clearfix">
							<input type="text" class="col-xs-5 col-sm-5" id="position" name="position" value="${user.position }" readonly="true">
						</div>

						<div class="space-2"></div>

						<div class="help-block" id="input-span-slider"></div>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right"
						for="form-field-5">警员警衔：</label>

					<div class="col-sm-9">
						<div class="clearfix">
							<input type="text" class="col-xs-5 col-sm-5" id="form-input-readonly" id="policeRank" name="policeRank" value="${user.policeRank }" readonly="true">
						</div>

						<div class="space-2"></div>

						<div class="help-block" id="input-span-slider"></div>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right"
						for="form-field-5">警员种类：</label>

					<div class="col-sm-9">
						<div class="clearfix">
							<input type="text" class="col-xs-5 col-sm-5" id="policeType" name="policeType" value="${user.policeType }" readonly="true">
						</div>

						<div class="space-2"></div>

						<div class="help-block" id="input-span-slider"></div>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right"
						for="form-field-5">从警时间：</label>

					<div class="col-sm-9">
						<div class="clearfix">
							<input id="policeAge" name="policeAge" 
								class="easyui-datebox col-xs-5 col-sm-5" value="${user.dateStr }" readonly="true">
						</div>

						<div class="space-2"></div>

						<div class="help-block" id="input-span-slider"></div>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right"
						for="form-field-5">警员性别：</label>

					<div class="col-sm-9">
						<div class="clearfix">
							<input type="radio" id="mjxb" name="mjxb" value="0" readonly="true">&nbsp;&nbsp;&nbsp;男&nbsp;&nbsp;&nbsp;
							<input type="radio" id="mjxb" name="mjxb" value="1" readonly="true">&nbsp;&nbsp;&nbsp;女
						</div>

						<div class="space-2"></div>

						<div class="help-block" id="input-span-slider"></div>
					</div>
				</div>
				
				
				
				<c:if test="${user.ifsyn eq '0'}">
				<div class="clearfix form-actions">
					<div class="col-md-offset-3 col-md-9">
						<button class=" button glow button-rounded button-tiny" type="submit" >
							<i class="icon-ok bigger-110" ></i>
								同步用户
						</button>
						
					</div>
				</div>
				</c:if>
			</form>
		</div>
	</div>
			
		<script type="text/javascript">
			  $(".page-content").css("height",window.screen.height-150<=520?520:window.screen.height-150);
			  $(':radio[name="mjxb"]').eq('${user.mjxb }').attr("checked",true);
		</script>
		<script src="${contextPath}/resources/assets/js/jquery-ui-1.10.3.full.min.js"></script>
		<script src="${contextPath}/resources/assets/js/jquery.ui.touch-punch.min.js"></script>
		
	</body>
</html>
