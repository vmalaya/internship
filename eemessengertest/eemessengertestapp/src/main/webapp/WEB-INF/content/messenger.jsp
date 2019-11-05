<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: valentyna.mala
  Date: 11/5/2019
  Time: 4:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Messenger</title>
</head>
<body>
    <s:form action="saveMessage" method="POST" namespace="/send-message">
        <s:textfield name="sender" label="from"/>
        <s:textfield name="recipient" label="to"/>
        <s:textfield name="body" label="body"/>
        <s:submit value="Press me to save message"/>
    </s:form>
    <a href="/eemessenger">Go back</a>

    <s:property value="messages"/>
</body>
</html>
