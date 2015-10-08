<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>应用菜单管理</title>
		<meta name="description" content="应用菜单管理" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<%@ include file="/WEB-INF/views/scripts.jsp"%>
		
		
		<link href="assets/css/bootstrap.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="assets/css/font-awesome.min.css" />

		<link rel="stylesheet" href="assets/css/ace.min.css" />
		<link rel="stylesheet" href="assets/css/ace-rtl.min.css" />
		<link rel="stylesheet" href="assets/css/ace-skins.min.css" />

		<script src="assets/js/ace-extra.min.js"></script>
		
	<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script src="assets/js/bootstrap.min.js"></script>
		<script src="assets/js/typeahead-bs2.min.js"></script>

		<!-- page specific plugin scripts -->

		<script src="assets/js/jquery.nestable.min.js"></script>

		<!-- ace scripts -->

		<script src="assets/js/ace-elements.min.js"></script>
		<script src="assets/js/ace.min.js"></script>

		<!-- inline scripts related to this page -->

		<script type="text/javascript">
			jQuery(function($){
			
				$('.dd').nestable();
			
				$('.dd-handle a').on('mousedown', function(e){
					e.stopPropagation();
				});
				
				$('[data-rel="tooltip"]').tooltip();
			
			});
		</script>
	
	</head>
	
  
  <body class="content">
  

			<div class="page-content">
						<div class="page-header">
							<h1>
								Nestable lists
								<small>
									<i class="icon-double-angle-right"></i>
									Drag &amp; drop hierarchical list
								</small>
							</h1>
						</div><!-- /.page-header -->

						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->

								<div class="row">
									<div class="col-sm-6">
										<div class="dd" id="nestable">
											<ol class="dd-list">
												<li class="dd-item" data-id="1">
													<div class="dd-handle">
														Item 1
														<i class="pull-right bigger-130 icon-warning-sign orange2"></i>
													</div>
												</li>

												<li class="dd-item" data-id="2">
													<div class="dd-handle">Item 2</div>

													<ol class="dd-list">
														<li class="dd-item" data-id="3">
															<div class="dd-handle">
																Item 3
																<a data-rel="tooltip" data-placement="left" title="Change Event Date" href="#" class="badge badge-primary radius-5 tooltip-info pull-right white no-hover-underline">
																	<i class="bigger-120 icon-calendar"></i>
																</a>
															</div>
														</li>

														<li class="dd-item" data-id="4">
															<div class="dd-handle">
																<span class="orange">Item 4</span>
																<span class="lighter grey">
																	&nbsp; with some description
																</span>
															</div>
														</li>

														<li class="dd-item" data-id="5">
															<div class="dd-handle">
																Item 5
																<div class="pull-right action-buttons">
																	<a class="blue" href="#">
																		<i class="icon-pencil bigger-130"></i>
																	</a>

																	<a class="red" href="#">
																		<i class="icon-trash bigger-130"></i>
																	</a>
																</div>
															</div>

															<ol class="dd-list">
																<li class="dd-item item-orange" data-id="6">
																	<div class="dd-handle"> Item 6 </div>
																</li>

																<li class="dd-item item-red" data-id="7">
																	<div class="dd-handle">Item 7</div>
																</li>

																<li class="dd-item item-blue2" data-id="8">
																	<div class="dd-handle">Item 8</div>
																</li>
															</ol>
														</li>

														<li class="dd-item" data-id="9">
															<div class="dd-handle btn-yellow no-hover">Item 9</div>
														</li>

														<li class="dd-item" data-id="10">
															<div class="dd-handle">Item 10</div>
														</li>
													</ol>
												</li>

												<li class="dd-item" data-id="11">
													<div class="dd-handle">
														Item 11
														<span class="sticker">
															<span class="label label-success arrowed-in">
																<i class="icon-ok bigger-110"></i>
															</span>
														</span>
													</div>
												</li>

												<li class="dd-item" data-id="12">
													<div class="dd-handle">Item 12</div>
												</li>
											</ol>
										</div>
									</div>

									<div class="vspace-sm-16"></div>

								</div><!-- PAGE CONTENT ENDS -->
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content -->
  </body>
</html>
