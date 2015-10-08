<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>消息内容</title>
		<meta name="description" content="消息内容" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<%@ include file="/WEB-INF/views/scripts.jsp"%>
		
	</head>
	<body>
	<div class="" id="content" style="width:300px">
		<div class="col-xs-12 col-sm-3 widget-container-span ui-sortable">
			<div class="widget-box">
				<div class="widget-header">
					<h5 class="smaller">消息接收单位：</h5>
				</div>

				<div class="widget-body">
					<div class="widget-main padding-6">
						<div class="alert alert-info"> ${orgss } </div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-xs-12 col-sm-3 widget-container-span ui-sortable">
			<div class="widget-box">
				<div class="widget-header">
					<h5 class="smaller">消息接收人员：</h5>
				</div>

				<div class="widget-body">
					<div class="widget-main padding-6">
						<div class="alert alert-info"> ${userss } </div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-xs-12 col-sm-3 widget-container-span ui-sortable">
			<div class="widget-box">
				<div class="widget-header">
					<h5 class="smaller">消息状态信息：</h5>
				</div>
				<c:if test="${error ==\"\"}">
				<div class="widget-body">
					<div class="widget-main padding-6">
						<div class="alert alert-info"> 
							<span class="label label-xlg label-primary arrowed arrowed-right">消息发送成功！</span>
						</div>
					</div>
				</div>
				</c:if>
				<c:if test="${error !=\"\"}">
				<div class="widget-body">
					<div class="widget-main padding-6">
					<div class="alert alert-info"> 
							<span class="label label-xlg label-warning arrowed arrowed-right">消息发送失败！</span>
						</div>
						<div class="alert alert-info"> ${error } </div>
					</div>
				</div>
				</c:if>
			</div>
		</div>
	</div>
	</body>
</html>
