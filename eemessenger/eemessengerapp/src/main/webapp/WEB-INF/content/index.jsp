<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>EE Messenger</title>
</head>
<body>
<h1>Hello, It is EE messenger implementation!</h1>
<s:form action="hello" method="GET" >
    <s:submit value="Press me, if you were here"/>
</s:form>
    <p> Current page was opened <s:property value="counter"/>  times!</p>
<%--    <a href="/eemessenger"> Update</a>--%>
</body>
</html>
