<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>EE Messenger</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/index.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/font-awesome/5.11.2/css/all.min.css">
    <link rel="shortcut icon" href="https://sigma.software/sites/default/files/favicon.png" type="image/png" />
</head>
<body class="max-vh-100 overflow-hidden">

<div class="container d-flex align-items-center justify-content-center">
<div>

</div>
    <div class="d-flex justify-content-center">
        <div class="card">
            <div class="card-header">
                <h3 class="card-title text-center">Please, Sign Up:</h3>
            </div>
            <div class="card-body">
                    <s:form action="saveUsername" method="post" namespace="/sign-up">
                            <s:textfield onfocus="" class="form-control" name="userBean.username" label="username" placeholder="Your Username" />
                            <s:password class="form-control" name="userBean.password" label="password" placeholder="Your Password" />
                            <s:submit class="ml-auto btn clean-button" value="Sign Up"/>
                    </s:form>
            </div>
            <div class="card-footer">
                    <div class="d-flex justify-content-center links">
                        Do you have an account?<a href="/eemessenger/send-message/page">Sign In</a>
                    </div>
            </div>
            <div class="label justify-content-center bg-danger">
                <s:property value="errorMessage"/>
            </div>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/webjars/jquery/3.4.1/jquery.slim.min.js"></script>
<script src="${pageContext.request.contextPath}/webjars/popper.js/1.15.0/umd/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>
