<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>EE Messenger</title>
</head>
<body>
<h1>Hello, It is EE messenger implementation!</h1>

    <s:form action="saveUsername" method="post" namespace="/">
        <s:textfield name="username" label="username"/>
        <s:submit value="Press me to save username"/>
    </s:form>
</body>
</html>
