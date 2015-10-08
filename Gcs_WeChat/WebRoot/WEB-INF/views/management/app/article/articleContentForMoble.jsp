<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%><%@ include file="/WEB-INF/views/include.inc.jsp"%><!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd"><html lang="en">	<head>		<meta charset="utf-8" />		<title>文章内容</title>		<meta name="description" content="This is page-header (.page-header &gt; h1)" />				<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" />		<!-- basic styles -->			<link href="${contextPath}/resources/assets/css/bootstrap.min.css" rel="stylesheet" />			<link rel="stylesheet" href="${contextPath}/resources/assets/css/font-awesome.min.css" />			<link rel="stylesheet" href="${contextPath}/resources/assets/css/ace-skins.min.css" />			<link rel="stylesheet" href="${contextPath}/resources/assets/css/ace-rtl.min.css" />			<link rel="stylesheet" href="${contextPath}/resources/assets/css/ace.min.css" />			<script type="text/javascript" src="${contextPath}/resources/assets/js/jquery-2.0.3.min.js"></script>			<script type="text/javascript" src="${contextPath}/resources/assets/js/bootstrap.min.js"></script>				</head>	<body>	<div class="" id="content" style="width:">		<div class="navbar navbar-default" id="navbar" style="background: url(${contextPath}/resources/img/banner_bg.jpg);">			<img alt="" src="${contextPath}/resources/img/my_logo_01.png">		</div>		<div class="main-container" id="main-container">			<script type="text/javascript">				try{ace.settings.check('main-container' , 'fixed')}catch(e){}			</script>			<div class="container-inner">				<div class="content">					<div class="page-content">						<div class="row">							<div class="col-xs-12">								<div class="widget-box" >									<div class="widget-header widget-header-flat">										<h4>战线动态</h4>									</div>									<div class="widget-body">										<div class="widget-main">											<div id="content" >												<h2 id="title">${article.title }</h2>												<h6>${article.createTime }&nbsp;&nbsp;&nbsp;&nbsp; ${createOrg}&nbsp;&nbsp;&nbsp;&nbsp;${createUser }</h6>												<div id="box">													${article.content }												</div>												<a >(${article.commentAmount })条评论</a>												<span id="praises" name="praises"> &nbsp;&nbsp;&nbsp;&nbsp;												<a id="praise" name="praise" >赞(${article.praiseAmount })</a>												</span>												 &nbsp;&nbsp;&nbsp;&nbsp;已阅读(${article.readAmount })												<hr />												<c:forEach var="comment" items="${commentList }" varStatus="status">												<div class="profile-activity clearfix">													<div style="min-height: 50px;">														<img class="pull-left" alt="Alex Doe's avatar" src="${contextPath}/resources/assets/avatars/profile-pic.jpg">															<a class="user" href="#">${comment.policeID}</a>															<br />															<i class="icon-time bigger-110"></i>																	${comment.createTimeStr}													</div>													<div>															${comment.content}													</div>												</div>												</c:forEach>											</div>										</div>									</div>								</div>							</div><!-- /.col -->							<div class="col-xs-12 col-sm-12">								<div class="widget-box">									<div class="widget-header">										<h4>我要评论</h4>									</div>									<div class="widget-body">										<div class="widget-main">											<form action="${contextPath}/management/wechat/acticleComment" method="post" 											 id ="form" name="form">												<div>													<textarea class="form-control limited" id="contents" name="content" maxlength="100" rows="8"></textarea>													<input type="hidden" id="articleId" name="articleId" value="${article.id }"/>													<input type="hidden" id="type" name="type" value="2"/>												</div>												<div class="form-actions center">													<button type="button" class="btn btn-sm btn-success" id="btn">														评论														<i class="icon-arrow-right icon-on-right bigger-110"></i>													</button>												</div>											</form>										</div>									</div>								</div>							</div>						</div><!-- /.row -->					</div><!-- /.page-content -->				</div><!-- /.main-content -->			</div><!-- /.main-container-inner -->			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">				<i class="icon-double-angle-up icon-only bigger-110"></i>			</a>		</div><!-- /.main-container -->		<script type="text/javascript">				$(document).ready(function() {								//设置图片大小				(function(restWidth) {										$("#box img").each(function(){							$(this).load(function(){								var width  = $(this).width(),							        height = $(this).height();								console.log(width +","+ height);								 if (width > restWidth) {									   var b = restWidth / (width / height);									   $(this).css({"width": restWidth+"px", "height": b+"px"});								   }							});														$(this).error(function(){								this.remove();							});					});				})(270);				//点赞				$("#praise").click(function(){					$.ajax({url:"${contextPath}/management/wechat/acticlePraise?id=${article.id }",async:false,success: function(){						var praises =  "${article.praiseAmount}";						if(praises){							praises = parseInt(praises)+1; 						}else{							praises = 1;						}						$("#praises").html("&nbsp;&nbsp;&nbsp;&nbsp;已赞("+praises+")"); 				      }});				  });				//验证评论不能为空				 $("#btn").click(function () {			            var usercode = $("#contents").val();			            if ($.trim(usercode) == "") {			                alert("评论内容不能为空！");			                return false;			            }			            else {			                document.form.submit();			            }			        });							});								</script>		</div>	</body></html>