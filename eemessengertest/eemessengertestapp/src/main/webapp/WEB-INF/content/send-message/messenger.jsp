<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<body class="max-vh-100 overflow-hidden">
<div class="container d-flex align-items-center justify-content-center">
    <div class="card-msg d-flex vw-80 flex-wrap">
        <div class="card-header w-25 flex-column border-right">
            <div class="text-light">
                <label>You are logged in as</label>
            </div>
            <h4 class="d-flex text-white mb-0 p-1 border-light">
                <s:property value="currentUser.username"/>
            </h4>
            <ul class="label d-flex flex-column-reverse border-top pl-0 mb-0">
                <c:forEach items="${contactsList}" var="contact">
                    <li class="w-100">
                        <a class="d-flex w-100 pt-2 pb-2 text-white" href="chat">
                                ${contact.username}
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </div>
        <div class="card-body w-75 d-flex flex-column align-items-stretch">
            <div class="card-chat-field flex-column-reverse">
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
                    <s:textfield class="form-control" name="recipientUsername" label="to" placeholder="username"/>
                    <s:textfield class="form-control" name="body" label="body" placeholder="type in here"/>
                    <s:submit class="ml-auto btn btn-warning" value="Send"/>
                </s:form>
            </div>
        </div>
        <div class="card-footer w-100">
            <s:form action="sign-out" method="POST" namespace="/send-message">
                <s:submit class="ml-auto btn btn-warning" value="Sign Out"/>
            </s:form>
        </div>
    </div>

</div>
<script>
    var contacts = document.querySelectorAll('.label li');
    contacts.forEach(function (item) {

        item.addEventListener('click', function () {
            var date = new Date();
            var DAYS = 30;
            date.setTime(date.getTime() + (DAYS * 24 * 60 * 60 * 1000));
            var expires = "; expires=" + date.toGMTString();
            document.cookie = "clickedUser=" + item.innerText + expires;
        });
    });
</script>

</body>
<head>
    <title>Messenger</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/index.css">
    <!-- Le styles -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <!--Bootstrap 4 CDN-->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

    <!--Fontawesome CDN-->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css"
          integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">

</head>
</html>
