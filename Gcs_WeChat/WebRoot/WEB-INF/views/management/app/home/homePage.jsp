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
<%@ include file="/WEB-INF/views/scripts.jsp"%>
  
  <body>
    <div class="page-content" id="content">
			<div id="user-profile-1" class="user-profile row">
				<div class="col-xs-12 col-sm-12 center">
					<div>
						<span class="profile-picture">
							<img id="avatar" height="300" width="300" class="editable img-responsive" alt="Alex's Avatar" src="${contextPath}/resources/img/jinghui.jpg" />
						</span>

						<div class="space-4"></div>

						<div class="width-80 label label-info label-xlg arrowed-in arrowed-in-right">
							<div class="inline position-relative">
								<a href="#" class="user-title-label dropdown-toggle" data-toggle="dropdown">
									<i class="icon-circle light-green middle"></i>
									&nbsp;
									<span class="white">${user.realname }</span>
								</a>
							</div>
						</div>
					</div>

					<div class="space-6"></div>


					<div class="hr hr12 dotted"></div>

					<div class="clearfix">
						<div class="grid2">
							<span class="bigger-175 blue">${orgs }</span>

							<br />
							部门个数
						</div>

						<div class="grid2">
							<span class="bigger-175 blue">${users }</span>

							<br />
							部门人数
						</div>
					</div>

					<div class="hr hr16 dotted"></div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			  $(".page-content").css("height",window.screen.height-150<=520?520:window.screen.height-150);
			</script>
  </body>
</html>
