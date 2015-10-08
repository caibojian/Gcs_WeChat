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
		<label>采集机关名称:</label>
		<input name="cjjgmc" id="cjjgmc" type="text" class="span11" value="" disabled="true"/>
	  </div>
	  <div class="input-row">
		<label>号牌种类:</label>
		<input name="hpzl" id="hpzl" type="text" class="span11" value="" disabled="true"/>
	  </div>
	  <div class="input-row">
		<label>号牌号码:</label>
		<input name="hphm" id="hphm" type="text" class="span11" value="" disabled="true"/>
	  </div>
		<div class="input-row">
		<label>机动车所有人:</label>
		<input name="jdcsyr" id="jdcsyr" type="text" class="span11" value="" disabled="true"/>
	  </div>
	  <div class="input-row">
		<label>违法时间:</label>
		<input name="wfsj" id="wfsj" type="text" class="span11" value="" disabled="true"/>
	  </div>
	  <div class="input-row">
		<label>违法地址:</label>
		<input name="wfdz" id="wfdz" type="text" class="span11" value="" disabled="true"/>
	  </div>
	  <div class="input-row">
		<label>违法行为:</label>
		<input name="wfxw" id="wfxw" type="text" class="span11" value="" disabled="true"/>
	  </div>
	  <div class="input-row">
		<label>违法内容:</label>
		<input name="wfnr" id="wfnr" type="text" class="span11" value="" disabled="true"/>
	  </div>
	  <div class="input-row">
		<label>罚款金额:</label>
		<input name="fkje" id="fkje" type="text" class="span11" value="" disabled="true"/>
	  </div>
	  <div class="input-row">
		<label>违法计分数:</label>
		<input name="wfjfs" id="wfjfs" type="text" class="span11" value="" disabled="true"/>
	  </div>
	  <div class="input-row">
		<label>处理机关名称:</label>
		<input name="cljgmc" id="cljgmc" type="text" class="span11" value="" disabled="true"/>
	  </div>
	  <div class="input-row">
		<label>处理时间:</label>
		<input name="clsj" id="clsj" type="text" class="span11" value="" disabled="true"/>
	  </div>
	  <div class="input-row">
		<label>处理标记:</label>
		<input name="clbj" id="clbj" type="text" class="span11" value="" disabled="true"/>
	  </div>
	  <div class="input-row">
		<label>交款标记:</label>
		<input name="jkbj" id="jkbj" type="text" class="span11" value="" disabled="true"/>
	  </div>
	  <div class="input-row">
		<label>缴款日期:</label>
		<input name="jkrq" id="jkrq" type="text" class="span11" value="" disabled="true"/>
	  </div>
	   <div class="input-row">
		<label>强制措施凭证编号:</label>
		<input name="pzbh" id="pzbh" type="text" class="span11" value="" disabled="true"/>
	  </div>
	   <div class="input-row">
		<label>决定书号:</label>
		<input name="jdsbh" id="jdsbh" type="text" class="span11" value="" disabled="true"/>
	  </div>
	   <div class="input-row">
		<label>当事人:</label>
		<input name="dsr" id="dsr" type="text" class="span11" value="" disabled="true"/>
	  </div>
	   <div class="input-row">
		<label>更新时间:</label>
		<input name="gxsj" id="gxsj" type="text" class="span11" value="" disabled="true"/>
	  </div>
	   <div class="input-row">
		<label>驾驶证号:</label>
		<input name="jszh" id="jszh" type="text" class="span11" value="" disabled="true"/>
	  </div>
	  <div class="input-row">
		<label>违法类型:</label>
		<input name="wflx" id="wflx" type="text" class="span11" value="现场1，电子警察2，强制措施3" disabled="true"/>
	  </div>
	  <div class="input-row">
		<label>违法类型:</label>
		<span>现场1，电子警察2，强制措施3现场1，电子警察2，强制措施3现场1，电子警察2，强制措施3现场1，电子警察2，强制措施3</span>
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
