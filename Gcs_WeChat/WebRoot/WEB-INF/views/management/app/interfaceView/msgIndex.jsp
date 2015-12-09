<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>消息内容</title>
    <link rel="stylesheet" href="${contextPath}/resources/weui/dist/style/weui.css"/>
    <link rel="stylesheet" href="${contextPath}/resources/weui/dist/example/example.css"/>
</head>
<body ontouchstart>

        <div class="page">
            <div class="hd">
                <h1 class="page_title">${msg.title }</h1>
            </div>
            <div class="bd">
                <article class="weui_article">
                    <h1>发送时间：${msgBean.createTime }作者： ${msgBean.token }</h1>
                    <section>
                        <h2 class="title">摘要：${msg.description }</h2>
                        <section>
                            <p>&nbsp;&nbsp;&nbsp;&nbsp;${msg.content }</p>
                        </section>
                    </section>
                </article>
            </div>
        </div>

    <script src="${contextPath}/resources/weui/dist/example/zepto.min.js"></script>
    <script src="${contextPath}/resources/weui/dist/example/example.js"></script>
</body>
</html>
