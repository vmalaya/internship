<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Basic Struts 2 Application - Welcome</title>
</head>
<body>
    <h1>Please input your username</h1>
    <s:form action="hello" method="post">
        <s:textfield name="username" label="E-mail" />
        <s:submit value="Hello" />
    </s:form>
</body>
</html>
