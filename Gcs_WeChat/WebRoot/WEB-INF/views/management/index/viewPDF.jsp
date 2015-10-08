<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.net.URLEncoder"%>
<%@ include file="/WEB-INF/views/include.inc.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="${contextPath}/resources/js/pdfobject.js"></script>
  </head>
  <%
  out.clearBuffer();
  
  String fjpath = "";
  if(request.getAttribute("fjlj")!=null){
	  fjpath = request.getAttribute("fjlj").toString();
	  fjpath = URLEncoder.encode(URLEncoder.encode(fjpath,"utf-8"),"utf-8");
  }
%>
  <body>
  	<c:if test="${fjlj == null}"> 
  		<br>暂无附件 
  	</c:if>
  	<c:if test="${not empty fjlj}">
  		 <object data="${contextPath}/management/cjw/szy/fjgl/viewFjContent?fjlj=<%=fjpath%>" type="application/pdf" width="100%" height="100%"></object>
  	</c:if>
  </body>
</html>
