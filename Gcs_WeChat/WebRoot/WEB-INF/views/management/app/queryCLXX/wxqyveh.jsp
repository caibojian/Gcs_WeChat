<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>机动车信息查询</title>
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
  <h1 class="title">武汉交管局-机动车信息查询</h1> 
  </header>
<form class="input-group" style="padding-top:50px" action="${contextPath}/management/wechat/clData" method="post">
  <div class="input-row">
    <label>车辆类型:</label>
    <input type="text" id="car_dummy" class="" placeholder="" readonly="" >
	<select name="car" id="car" onchange="update();" class="dw-hsel" tabindex="-1">
	   <option value="01">  大型汽车  </option>
	   <option value="02" selected="selected">  小型汽车  </option>
	   <option value="03">  使馆汽车  </option>
	   <option value="04">  领馆汽车  </option>
	   <option value="05">  境外汽车  </option>
	   <option value="06">  外籍汽车  </option>
	   <option value="07">  两、三轮摩托车  </option>
	   <option value="08">  轻便摩托车  </option>
	   <option value="09">  使馆摩托车  </option>
	   <option value="10">  领馆摩托车  </option>
	   <option value="11">  境外摩托车  </option>
	   <option value="12">  外籍摩托车  </option>
	   <option value="13">  农用运输车  </option>
	   <option value="14">  拖拉机  </option>
	   <option value="15">  挂车  </option>
	   <option value="16">  教练汽车  </option>
	   <option value="17">  教练摩托车  </option>
	   <option value="18">  试验汽车  </option>
	   <option value="19">  临时入境汽车  </option>
	   <option value="20">  临时入境摩托车  </option>
	   <option value="21">  临时行驶车  </option>
	   <option value="22">  警用汽车  </option>
	   <option value="23">  警用摩托  </option>

	</select>
  </div>
  <div class="input-row">
    <label>车牌号码:</label>
    <input type="text" placeholder="鄂A xxxxxx" id="cphm" name="cphm">
  </div>
  
    <!-- 机动车信息查询type参数 1 -->
  <input type="hidden" id="type" name="type" value="1">
<!--   <div class="input-row"> 
     <label>发动机号:</label> 
     <input type="text" placeholder="请输入发动机号后5位"> 
	  </div> -->
	<div class="bar bar-standard bar-footer-secondary">
		<button class="btn btn-primary btn-block" > <span class="icon icon-search"></span>查询</button>
	</div>
</form>

<!-- Icons in standard bar fixed to the bottom of the screen -->


  
</div>
<script language="javascript">
	$('#car').mobiscroll().select({
				theme: 'ios7',
				lang: 'zh',
				display: 'bottom',
				mode: 'scroller',
				minWidth: 200
		});
	</script>
</body>
</html>
