<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<body class="max-vh-100 overflow-hidden">
<div class="container d-flex align-items-center justify-content-center">
    <div class="card">
        <div class="card-header">
            <div class="label">
                You are login as <s:property value="currentUser"/>
                <c:forEach items="${contactsList}" var="contact">
                    <li>
                        ${contact.username}
                    </li>
                </c:forEach>
            </div>

        </div>
        <div class="card-body">
            <s:form action="saveMessage" method="POST" namespace="/send-message">
                <s:textfield class="form-control" name="recipientUsername" label="to"/>
                <s:textfield class="form-control" name="body" label="body"/>
                <s:submit class="ml-auto btn btn-warning" value="Press me to save message"/>
            </s:form>
        </div>
        <div class="card-footer">
            <s:form action="sign-out" method="POST" namespace="/send-message">
                <s:submit class="ml-auto btn btn-warning" value="Sign Out"/>
            </s:form>
        </div>
    </div>

</div>

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
