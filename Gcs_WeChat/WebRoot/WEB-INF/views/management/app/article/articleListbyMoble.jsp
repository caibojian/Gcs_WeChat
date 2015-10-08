<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%><%@ include file="/WEB-INF/views/include.inc.jsp"%><!DOCTYPE html><html lang="zh-CN">	<head>		<meta charset="utf-8" />		<title>交管快讯</title>		<meta name="description" content="This is page-header (.page-header &gt; h1)" />		<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" />			<link href="${contextPath}/resources/assets/css/bootstrap.min.css" rel="stylesheet" />			<link rel="stylesheet" href="${contextPath}/resources/assets/css/font-awesome.min.css" />			<link rel="stylesheet" href="${contextPath}/resources/assets/css/ace-skins.min.css" />			<link rel="stylesheet" href="${contextPath}/resources/assets/css/ace-rtl.min.css" />			<link rel="stylesheet" href="${contextPath}/resources/assets/css/ace.min.css" />			<script type="text/javascript" src="${contextPath}/resources/assets/js/jquery-2.0.3.min.js"></script>			<script type="text/javascript" src="${contextPath}/resources/assets/js/bootstrap.min.js"></script>		<script type="text/javascript">		if('${msg}'){			alert('${msg}');		}		</script>	</head>	<body>		<div class="navbar navbar-default" id="navbar" style="background: url(${contextPath}/resources/img/banner_bg.jpg);">			<img alt="" src="${contextPath}/resources/img/my_logo_01.png">				</div>		<div class="main-container" id="main-container">			<script type="text/javascript">				try{ace.settings.check('main-container' , 'fixed')}catch(e){}			</script>			<div class="container-inner">				<div class="content">					<div class="page-content">						<div class="row">							<div class="col-xs-12">								<!-- PAGE CONTENT BEGINS -->								<div class="widget-box">									<div class="widget-header widget-header-flat">										<h4>交管快讯 </h4>									</div>									<div class="widget-body">										<div class="widget-main">										<c:forEach var="article" items="${articleList }" varStatus="status">											<div id="content">												<h3>${article.title }</h3>												<h6>${article.createTime } &nbsp; 评论(${article.commentAmount })&nbsp; 已阅读(${article.readAmount })</h6>												<h6>${article.createOrg }</h6>												<p >													${article.bak1 }												</p>												<button class="btn btn-xs btn-primary" onclick="wznr('${article.id }')">阅读内容<i class="icon-double-angle-right"></i></button>												<hr />											</div>											</c:forEach>										</div>									</div>									<div>										<ul class="pagination">											<li id="prev">												<a >													<i class="icon-double-angle-left"></i>												</a>											</li>											<li>												<a>文章总数:${total }&nbsp;当前页:${page }/${totalPage }</a>											</li>											<li id="next">												<a >													<i class="icon-double-angle-right"></i>												</a>											</li>										</ul>									</div>								</div>							</div><!-- /.col -->						</div><!-- /.row -->					</div><!-- /.page-content -->				</div><!-- /.main-content -->			</div><!-- /.main-container-inner -->			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">				<i class="icon-double-angle-up icon-only bigger-110"></i>			</a>		</div><!-- /.main-container -->		<script type="text/javascript">			jQuery(function($) {							window.prettyPrint && prettyPrint();				$('#id-check-horizontal').removeAttr('checked').on('click', function(){					$('#dt-list-1').toggleClass('dl-horizontal').prev().html(this.checked ? '&lt;dl class="dl-horizontal"&gt;' : '&lt;dl&gt;');				});								var prev  = $("#prev"),					next  = $("#next"),					total = "${totalPage}",					page  = "${page}";									if (page == "1")				    prev.addClass("disabled");				if (total == page||total == "0")					next.addClass("disabled");												prev.click(function(){					if (page != "1"){						page = parseInt(page)-1;						location.href="${contextPath}/management/wechat/acticleForMoblie?articleType=${articleType}&page="+page;					}									});								next.click(function(){					if (page != total && total != "0"){						page = parseInt(page)+1;						location.href="${contextPath}/management/wechat/acticleForMoblie?articleType=${articleType}&page="+page;					}				});								//设置图片大小				(function(restWidth) {										$("#content img").each(function(){							$(this).load(function(){								var width  = $(this).width(),							        height = $(this).height();								console.log(width +","+ height);								 if (width > restWidth) {									   var b = restWidth / (width / height);									   $(this).css({"width": restWidth+"px", "height": b+"px"});								   }							});														$(this).error(function(){								this.remove();							});					});				})(270);							});						function wznr(id) {  	 			 window.location.href='${contextPath}/management/wechat/acticle?id='+id;  			}		</script>	</body></html>