<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Messenger</title>
</head>
<body>
You are log in as <s:property value="currentUser.username"/>
    <s:form action="saveMessage" method="POST" namespace="/send-message">
        <s:textfield name="sender" label="from"/>
        <s:textfield name="recipient" label="to"/>
        <s:textfield name="body" label="body"/>
        <s:submit value="Press me to save message"/>
    </s:form>

    <s:property value="messages"/>

    <s:form action="sign-out" method="POST" namespace="/send-message">
        <s:submit value="Sign Out"/>
    </s:form>

</body>
</html>
