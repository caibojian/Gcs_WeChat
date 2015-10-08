<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>驾驶员信息查询</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<link rel="stylesheet" href="${contextPath}/resources/wechat/dist/css/ratchet.min.css">
<link rel="stylesheet" href="${contextPath}/resources/wechat/dist/css/ratchet-theme-android.min.css"/>
<link rel="stylesheet" href="${contextPath}/resources/wechat/css/app.css"/>
<script src="${contextPath}/resources/wechat/dist/js/ratchet.min.js"></script>
</head>
<body style="background: none repeat scroll 0 0 #eeeeee;">

<div class="content"> 
	<header class="">
		<h1 class="title">武汉交管局-驾驶员信息</h1>
	</header>
	<form class="input-group" style="">
	  <div class="input-row">
		<label>姓名:</label>
		<input name="xm" id="xm" type="text" class="span11" value="" disabled="true"/>
	  </div>
	  <div class="input-row">
		<label>性别:</label>
		<input name="xb" id="xb" type="text" class="span11" value="" disabled="true"/>
	  </div>
	  <div class="input-row">
		<label>手机号码:</label>
		<input name="sjhm" id="sjhm" type="text" class="span11" value="" disabled="true"/>
	  </div>
		<div class="input-row">
		<label>档案编号:</label>
		<input name="dabh" id="dabh" type="text" class="span11" value="" disabled="true"/>
	  </div>
	  <div class="input-row">
		<label>累积积分:</label>
		<input name="ljjf" id="ljjf" type="text" class="span11" value="" disabled="true"/>
	  </div>
	  <div class="input-row">
		<label>状态:</label>
		<input name="zt" id="zt" type="text" class="span11" value="" disabled="true"/>
	  </div>
	  <div class="input-row">
		<label>初次领证:</label>
		<input name="cclzrq" id="cclzrq" type="text" class="span11" value="" disabled="true"/>
	  </div>
	  <div class="input-row">
		<label>下一体检:</label>
		<input name="syrq" id="syrq" type="text" class="span11" value="" disabled="true"/>
	  </div>
	  <div class="input-row">
		<label>有效期始:</label>
		<input name="yxqs" id="yxqs" type="text" class="span11" value="" disabled="true"/>
	  </div>
	  <div class="input-row">
		<label>有效期止:</label>
		<input name="yxqz" id="yxqz" type="text" class="span11" value="" disabled="true"/>
	  </div>
	  <div class="input-row">
		<label>更新时间:</label>
		<input name="gxsj" id="gxsj" type="text" class="span11" value="" disabled="true"/>
	  </div>
	  <div class="input-row">
		<label>证芯编号:</label>
		<input name="zxbh" id="zxbh" type="text" class="span11" value="" disabled="true"/>
	  </div>
	</form>
</div>
     

</body>
<script type="text/javascript">
	var result = eavl("${result}");
	var b ={"response":{"error":{"code":0,"message":"success"},"result":{"xszbh1":{"field1":"value1","field2":"value2","field3":"value3","field4":"value4"},"xszbh2":{"field1":"value1","field2":"value2","field3":"value3","field4":"value4"}}}};
 	console.log(b.response.result.xszbh1);
</script>
</html>
