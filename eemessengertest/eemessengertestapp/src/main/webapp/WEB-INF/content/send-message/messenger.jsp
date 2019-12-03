<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Messenger</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/index.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/font-awesome/5.11.2/css/all.min.css">
    <link rel="shortcut icon" href="https://sigma.software/sites/default/files/favicon.png" type="image/png" />
</head>
<body class="max-vh-100 overflow-hidden">
<div class="container d-flex align-items-center justify-content-center">
    <div class="card-msg d-flex vw-80 flex-wrap">
        <div class="card-header w-25 d-flex flex-column border-right">
            <div class="text-light">
                <label>You are logged in as</label>
            </div>
            <h4 class="d-flex text-white mb-0 p-1 border-light">
                <s:property value="currentUser.username"/>
            </h4>
            <ul class="label d-flex flex-column-reverse border-top pt-4 pl-0 mb-0">
                <c:forEach items="${contactsList}" var="contact">
                    <li class="w-100 pt-2 pb-2">
                        <a class="d-flex w-100 p-2 text-white" href="${pageContext.request.contextPath}/send-message/chat?username=${contact.username}">
                            <h6 class="m-0">${contact.username}</h6>
                        </a>
                    </li>
                </c:forEach>
            </ul>
            <div class="card-footer d-flex w-100 mt-auto p-0 pt-4">
                <s:form action="sign-out" method="POST" namespace="/send-message">
                    <s:submit class="btn mb-1 w-100 clean-button" value="Sign Out"/>
                </s:form>
            </div>
        </div>
        <div class="card-body w-75 d-flex flex-column align-items-stretch ">
            <div class="card-chat-field flex-column-reverse overflow-auto">
                <c:forEach items="${chat}" var="message">
                    <c:if test="${message.sender.username == currentUser.username}">
                        <div class="d-flex justify-content-end pr-2 text-white">
                            <div class="p-2 m-1 border border-light rounded-left bg-light text-dark">
                                <div class="d-flex">
                                        ${message.body}
                                </div>
                                <div class="d-flex text-muted small">
                                        ${message.dateTime.getHour()}:${message.dateTime.getMinute()}, ${message.dateTime.getDayOfWeek()}
                                </div>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${message.sender.username != currentUser.username}">
                        <div class="d-flex text-white">
                            <div class="p-2 m-1 border border-light rounded-right bg-light text-dark">
                                <div class="d-flex">
                                        ${message.body}
                                </div>
                                <div class="d-flex text-muted small">
                                        ${message.dateTime.getHour()}:${message.dateTime.getMinute()}, ${message.dateTime.getDayOfWeek()}
                                </div>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
            <div class="card-msg-field d-flex w-100 mt-1 align-self-end">
                <s:form action="saveMessage" method="POST" namespace="/send-message">
                    <s:textfield onfocus="" class="form-control" name="recipientUsername" label="to" placeholder="username"/>
                    <s:textfield class="form-control" name="body" label="body" placeholder="type in here"/>
                    <s:submit class="ml-auto btn clean-button" value="Send"/>
                </s:form>
            </div>
        </div>
        <div class="label d-flex w-100 justify-content-center height-24px ${errorMessage != null && !errorMessage.isEmpty() ? 'bg-danger' : ''}">
            <s:property value="errorMessage"/>
        </div>
    </div>
</div>
</body>
</html>
