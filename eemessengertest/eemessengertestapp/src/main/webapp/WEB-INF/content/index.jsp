<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>EE Messenger</title>
</head>
<body>
<h1>Hello, It is EE messenger implementation!</h1>
Please, sign up.

    <s:form action="saveUsername" method="post" namespace="/sign-up">
        <s:textfield name="userBean.username" label="username" />
        <s:password name="userBean.password" label="password"/>
        <s:submit value="Press me to save username"/>
    </s:form>
Or
<a href="/eemessenger/send-message/page">Sing in</a>

</body>
</html>
