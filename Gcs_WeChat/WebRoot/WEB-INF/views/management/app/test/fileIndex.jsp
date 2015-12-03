<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>组织管理</title>
<meta name="description" content="人员添加" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
</head>
<body>
<form id="form" name="form" method="post" action="">
<table>
	<tr>
	<td>文件：</td><td><input type="file" name="file" id="file"></td>
	</tr>
	<tr>
	<td>文件下载：</td><td><a href='${contextPath}/doc/README.doc'>README.zip
	</a></td>
	</tr>
</table>
</form>
</body>
</html>
