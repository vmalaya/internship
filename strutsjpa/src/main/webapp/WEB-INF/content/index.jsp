<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="action" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Basic Struts 2 Application - Message</title>
</head>
<body>
<h1>Please input your message</h1>
    <s:form action="saveMessage" method="post" namespace="/">
        <s:textfield name="message" label="message"/>
        <s:submit value="Submit"/>
    </s:form>
    <c:forEach items="${messages}" var="message">
        ${message}<br>
    </c:forEach>
</body>
</html>
