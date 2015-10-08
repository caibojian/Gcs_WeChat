<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>长江委水资源论证与取水许可台账系统</title>
		<meta name="description" content="长江委水资源论证与取水许可台账系统" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<%@ include file="/WEB-INF/views/scripts.jsp"%>
	</head>
	<body>
			<div class="page-content" id="content">
				<div class="row">
					<div class="col-xs-12">
						<div class="widget-box">
							<div class="widget-header widget-header-flat">
								<h4 class="lighter">
									<i class="icon-star orange"></i>
										行政许可事务审批信息
								</h4>
								<div class="widget-toolbar">
									<a href="#" data-action="collapse">
										<i class="icon-chevron-up"></i>
									</a>
								</div>
							</div>
							<div class="widget-body">
								<div class="widget-main no-padding">
									<div class="comments">
										<c:forEach  var="item" items="${homeList}">
										<div class="itemdiv commentdiv">

											<div class="body">
												<div class="name">
													<a href="#">${item.gljd_code_str}</a>
												</div>

												<div class="time">
													<i class="icon-time"></i>
													<span class="green">${item.time}</span>
												</div>

												<div class="text">
													<i class="icon-quote-left"></i>
													进行了事务“${item.gljd_code_str}”
												</div>
											</div>
										</div>
										</c:forEach>
									</div>
								</div>
							</div>					
						</div>					
					</div>
				</div>			
			</div>
			<script type="text/javascript">
			  $(".page-content").css("height",window.screen.height-150<=520?520:window.screen.height-150);
			</script>
	</body>
</html>
