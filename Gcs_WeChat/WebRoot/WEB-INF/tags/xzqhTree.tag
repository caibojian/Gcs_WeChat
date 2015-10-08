<%@ tag language="java" pageEncoding="UTF-8" body-content="scriptless" trimDirectiveWhitespaces="true"%>
<%@ attribute name="id" type="java.lang.String"%>
<%@ attribute name="title" type="java.lang.String"%>
<%@ attribute name="contextPath" type="java.lang.String"%>
<%@ attribute name="style" type="java.lang.String"%>
<%@ taglib prefix="gcs" tagdir="/WEB-INF/tags"%>
<gcs:tree id="${id}" title="${title}" style="${style}" options="url:'${contextPath}/management/cjw/szy/xzqh/listData',method:'post'"></gcs:tree>