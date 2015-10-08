<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>驾驶员信息查询</title>
		<meta charset="UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		
		 <link rel="stylesheet" type="text/css" href="${contextPath}/resources/wechat/wx_files/css11.css">
		<link rel="stylesheet" type="text/css" href="${contextPath}/resources/wechat/wx_files/css11_append_20.css">
		<script src="${contextPath}/resources/wechat/wx_files/cdn_djl.js" type="text/javascript" async=""></script>
		<script type="text/javascript" src="${contextPath}/resources/wechat/wx_files/zepto.min.js"></script>
		<script src="${contextPath}/resources/wechat/wx_files/cdn_dianjiliu.js"></script></head>
		<link rel="stylesheet" href="${contextPath}/resources/wechat/wx_files/mobiscroll.css">
		<script src="${contextPath}/resources/wechat/wx_files/mobiscroll.js"></script>
		
		<link rel="stylesheet" href="${contextPath}/resources/wechat/dist/css/ratchet.min.css">
		<link rel="stylesheet" href="${contextPath}/resources/wechat/dist/css/ratchet-theme-android.min.css">
		<link rel="stylesheet" href="${contextPath}/resources/wechat/css/app.css">
		<script src="${contextPath}/resources/wechat/dist/js/ratchet.min.js"></script>
	</head>
  
<body style="background: none repeat scroll 0 0 #eeeeee;">

<div class="">
  <header class="bar bar-nav">
  <h1 class="title">武汉交管局-驾驶员信息查询</h1> 
  </header>
<form class="input-group" style="padding-top:50px" action="${contextPath}/management/wechat/drvData" method="post">
  <div class="input-row">
    <label>身份证号码:</label>
    <input type="text"  id="sfzhm" name="sfzhm"/>
  </div>
  <!-- 驾驶员信息查询type参数 5 -->
  <input type="hidden" id="type" name="type" value="5"/>
<!--   <div class="input-row"> 
     <label>发动机号:</label> 
     <input type="text" placeholder="请输入发动机号后5位"> 
	  </div> -->
	<div class="bar bar-standard bar-footer-secondary" >
		<button class="btn btn-primary btn-block" > <span class="icon icon-search"></span>查询</button>
	</div>
</form>

<!-- Icons in standard bar fixed to the bottom of the screen -->


  
</div>
</body>
</html>
