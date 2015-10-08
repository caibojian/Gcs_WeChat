<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>车辆信息查询</title>
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
		<h1 class="title">武汉交管局-车辆信息</h1>
	</header>
	<form class="input-group" style="">
	   <div class="input-row">
		<label>号牌种类:</label>
		<input name="hpzl" id="hpzl" type="text" class="span11" value="" disabled="true"/>
	  </div>
	  <div class="input-row">
		<label>号牌号码:</label>
		<input name="hphm" id="hphm" type="text" class="span11" value="" disabled="true"/>
	  </div>
	  <div class="input-row">
		<label>中文品牌:</label>
		<input name="clppl" id="clppl" type="text" class="span11" value="" disabled="true"/>
	  </div>
		<div class="input-row">
		<label>车辆型号:</label>
		<input name="clxh" id="clxh" type="text" class="span11" value="" disabled="true"/>
	  </div>
	  <div class="input-row">
		<label>国产/进口:</label>
		<input name="gcjk" id="gcjk" type="text" class="span11" value="" disabled="true"/>
	  </div>
	  <div class="input-row">
		<label>识别代号:</label>
		<input name="clsbdh" id="clsbdh" type="text" class="span11" value="" disabled="true"/>
	  </div>
	  <div class="input-row">
		<label>发动机号:</label>
		<input name="fdjh" id="fdjh" type="text" class="span11" value="" disabled="true"/>
	  </div>
	  <div class="input-row">
		<label>身份证号码:</label>
		<input name="sfzmhm" id="sfzmhm" type="text" class="span11" value="" disabled="true"/>
	  </div>
	  <div class="input-row">
		<label>身份证名称:</label>
		<input name="sfzmmc" id="sfzmmc" type="text" class="span11" value="" disabled="true"/>
	  </div>
	  <div class="input-row">
		<label>初次登记日期:</label>
		<input name="ccdjrq" id="ccdjrq" type="text" class="span11" value="" disabled="true"/>
	  </div>
	  <div class="input-row">
		<label>检验有效期止:</label>
		<input name="yxqz" id="yxqz" type="text" class="span11" value="" disabled="true"/>
	  </div>
	  <div class="input-row">
		<label>强制报废期止:</label>
		<input name="qzbfqz" id="qzbfqz" type="text" class="span11" value="" disabled="true"/>
	  </div>
	   <div class="input-row">
		<label>销售单位:</label>
		<input name="xsdw" id="xsdw" type="text" class="span11" value="" disabled="true"/>
	  </div>
	   <div class="input-row">
		<label>销售价格:</label>
		<input name="xsjg" id="xsjg" type="text" class="span11" value="" disabled="true"/>
	  </div>
	   <div class="input-row">
		<label>销售日期 :</label>
		<input name="xsrq" id="xsrq" type="text" class="span11" value="" disabled="true"/>
	  </div>
	   <div class="input-row">
		<label>保险凭证号:</label>
		<input name="bxpzh" id="bxpzh" type="text" class="span11" value="" disabled="true"/>
	  </div>
	   <div class="input-row">
		<label>强制报废期止:</label>
		<input name="qzbfqz" id="qzbfqz" type="text" class="span11" value="" disabled="true"/>
	  </div>
	   <div class="input-row">
		<label>保险金额:</label>
		<input name="bxje" id="bxje" type="text" class="span11" value="" disabled="true"/>
	  </div>
	   <div class="input-row">
		<label>生效日期:</label>
		<input name="sxrq" id="sxrq" type="text" class="span11" value="" disabled="true"/>
	  </div>
	   <div class="input-row">
		<label>终止日期:</label>
		<input name="zzrq" id="zzrq" type="text" class="span11" value="" disabled="true"/>
	  </div>
	   <div class="input-row">
		<label>保险公司:</label>
		<input name="bxgs" id="bxgs" type="text" class="span11" value="" disabled="true"/>
	  </div>
	   <div class="input-row">
		<label>机动车所有人:</label>
		<input name="syr" id="syr" type="text" class="span11" value="" disabled="true"/>
	  </div>
	   <div class="input-row">
		<label>手机号码:</label>
		<input name="sjhm" id="sjhm" type="text" class="span11" value="" disabled="true"/>
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
