<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<title>武汉市公安局交通管理局微信管理系统</title>
		<meta http-equiv="X-UA-Compatible" content="IE=Edge">
		<meta charset="utf-8" />
		<meta name="description" content="微信管理系统" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<%@ include file="/WEB-INF/views/scripts.jsp"%>
	</head>

	<body>
		<div class="navbar navbar-default" id="navbar">
			<div class="navbar-container" id="navbar-container">
				<div class="navbar-header pull-left">
					<a href="#" class="navbar-brand">
						<small>
							<i class="icon-leaf"></i>
							武汉市公安局交通管理局微信管理系统
						</small>
					</a><!-- /.brand -->
				</div><!-- /.navbar-header -->

				<div class="navbar-header pull-right" role="navigation">
					<ul class="nav ace-nav">
						<li class="light-blue">
							<a data-toggle="dropdown" href="#" class="dropdown-toggle">
								
								<span class="user-info">
									<small>欢迎您</small>
									${login_user.realname}
								</span>

								<i class="icon-caret-down"></i>
							</a>

							<ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
								<li>
									<a href="javascript:void(0)" onclick="updatePass();">
										<i class="icon-cog"></i>
										修改密码
									</a>
								</li>

							

								<li class="divider"></li>

								<li>
									<a href="${contextPath}/logout">
										<i class="icon-off"></i>
										退出
									</a>
								</li>
							</ul>
						</li>
					</ul><!-- /.ace-nav -->
				</div><!-- /.navbar-header -->
			</div><!-- /.container -->
		</div>

		<div class="main-container" id="main-container">

			<div class="main-container-inner">
				<a class="menu-toggler" id="menu-toggler" href="#">
					<span class="">菜单</span>
				</a>

				<div class="sidebar" id="sidebar">

					<ul class="nav nav-list">
						<li class="active" onclick="changeMenu('主页','icon-home home-icon','','')">
							<a href="${contextPath}/login/home" target="mainIFrame">
								<i class="icon-home"></i>
								<span class="menu-text">主页 </span>
							</a>
						</li>
						
						<!--  动态配置菜单	-->
							<c:if test="${!empty menuModule}">
								<c:if test="${!empty menuModule.children}">
									<c:forEach var="item" items="${menuModule.children}" varStatus="idx">
										<li id="sysmanage${idx.count}Li"><a id="sysmanage${idx.count}"  class="dropdown-toggle" style="cursor:pointer;" ><i class="${item.icon}"></i><span class="menu-text"> ${item.name} </span><b class="arrow icon-angle-down"></b></a>
											<c:if test="${!empty menuModule}">
												<ul class="submenu">
													<c:forEach var="item1" items="${item.children}" varStatus="idx1">
														<li onclick="changeMenu('${item1.name}','${contextPath}${item1.url}','${item.name}','${item.icon}');"><a class=" " target="mainIFrame" href="${contextPath}${item1.url}"><i class="icon-double-angle-right"></i><span class="hidden-tablet">${item1.name}</span></a></li>
													</c:forEach>
												</ul>
											</c:if>
										</li>
									</c:forEach>
								</c:if>
							</c:if>
					</ul><!-- /.nav-list -->

					<div class="sidebar-collapse" id="sidebar-collapse">
						<i class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>
					</div>

				</div>

				<div class="main-content">
					<div class="breadcrumbs" id="breadcrumbs">
						<ul class="breadcrumb">
							<li onclick="changeMenu('主页','icon-home home-icon','','');">
								<i class="icon-home home-icon"></i>主页</li>
						</ul><!-- .breadcrumb -->

						<div class="nav-search" id="nav-search">
							<form class="form-search">
								<span class="input-icon">
									<input type="text" placeholder="查询 ..." class="nav-search-input" id="nav-search-input" autocomplete="off" />
									<i class="icon-search nav-search-icon"></i>
								</span>
							</form>
						</div><!-- #nav-search -->
					</div>

					<div style="margin: 5px;">
								<!-- PAGE CONTENT BEGINS -->
									<iframe name="mainIFrame" id="mainIFrame"
								src="${contextPath}/login/home"
								frameborder="0" scrolling="no"
								style="width: 100%; height: 1900px; border-top-left-radius: 8px; border-top-right-radius: 8px; border-bottom-right-radius: 8px; border-bottom-left-radius: 8px;"
								allowtransparency="true"></iframe>
								<!-- PAGE CONTENT ENDS -->
					</div><!-- /.page-content -->
				</div><!-- /.main-content -->

				<div class="ace-settings-container" id="ace-settings-container">
					<div class="btn btn-app btn-xs btn-warning ace-settings-btn" id="ace-settings-btn">
						<i class="icon-cog bigger-150"></i>
					</div>

					<div class="ace-settings-box" id="ace-settings-box">
						<div>
							<div class="pull-left">
								<select id="skin-colorpicker" class="hide">
									<option data-skin="default" value="#438EB9">#438EB9</option>
									<option data-skin="skin-1" value="#222A2D">#222A2D</option>
									<option data-skin="skin-2" value="#C6487E">#C6487E</option>
									<option data-skin="skin-3" value="#D0D0D0">#D0D0D0</option>
								</select>
							</div>
							<span>切换主题</span>
						</div>

						<div>
							<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-navbar" />
							<label class="lbl" for="ace-settings-navbar"> 固定导航栏</label>
						</div>

						<div>
							<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-sidebar" />
							<label class="lbl" for="ace-settings-sidebar"> 固定菜单栏</label>
						</div>

						<div>
							<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-rtl" />
							<label class="lbl" for="ace-settings-rtl"> 菜单左右互换</label>
						</div>
					</div>
				</div><!-- /#ace-settings-container -->
			</div><!-- /.main-container-inner -->

			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="icon-double-angle-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->
		
		<script type="text/javascript">
		function resetIframeHeight(){
			var height = $("#mainIFrame").contents().find("#content").height();
				//这样给以一个最小高度 
				$("#mainIFrame").height(height < 400 ? 400 : height);
		}
		//修改选中样式
			function changeSelect(dom){
				//移除其他选中css
				$(".active").removeClass("active");
				dom.attr("class","active");
			}
			
			$(".submenu li").each(function(index,obj){
				$(this).click(function(){
					changeSelect($(this));
				});
			});
			
			function changeHeight(height){
				$("#mainIFrame").height(height);
			}
			
			//改变标题栏内容
			function changeMenu(title,url,pTitle,picon){
				var html = "";
				if(pTitle){
					html = "<ul class='breadcrumb'>"
								+"<li><i class='"+picon+"'></i>"+pTitle+"</li>"
								+"<li class='active'><a target='mainIFrame' href='"+url+"?type=1'>"+title+"</a></li>"
							+"</ul>";
				}else{
						html = "<ul class='breadcrumb'>"
								+"<li class='active'><i class='"+url+"'></i>"+title+"</li>"
								+"</ul>";
				}
				$('#breadcrumbs').html(html);
			}
		
			$("#mainIFrame").load(function() {
				var height = $(this).contents().find("#content").height() + 30;
				//这样给以一个最小高度 
				$(this).height(height < 400 ? 400 : height);
			});
			
			function updatePass(){
				art.dialog.open('${contextPath}/management/index/updatepassword', {title: '修改密码',lock:true,width:400});
			}
			
			$(function(){
				//setInterval(resetIframeHeight,500);
			});
		</script>
	</body>
</html>
