<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: valentyna.mala
  Date: 11/6/2019
  Time: 3:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign-in</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/index.css">
    <!-- Le styles -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <!--Bootstrap 4 CDN-->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

    <!--Fontawesome CDN-->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">

</head>
<body class="max-vh-100 overflow-hidden">

<div class="container d-flex align-items-center justify-content-center">
    <div>

    </div>
    <div class="d-flex justify-content-center">
        <div class="card">
            <div class="card-header">
                <h3 class="card-title text-center">Please, Sign In:</h3>
            </div>
            <div class="card-body">
                <form class="mb-0" method="POST" action="j_security_check">
                    <div class="form-group d-flex mb-4">
                        <label class="label d-flex align-items-center mr-2 mb-0" for="username">username: </label>
                        <input class="form-control" type="text" name="j_username" id="username" placeholder="Your Username" />
                    </div>
                    <div class="form-group d-flex mb-4">
                        <label class="label d-flex align-items-center mr-2 mb-0" for="password">password: </label>
                        <input class="form-control" type="password" name="j_password" id="password" placeholder="Your Password" />
                    </div>
                    <div class="form-group d-flex align-items-center mb-0 justify-content-end">
                        <input class="ml-auto btn clean-button" type="submit" value="Sign In" />
                    </div>
                </form>
            </div>
            <div class="card-footer">
                <div class="d-flex justify-content-center links">
                Don't you have an account?<a href="/eemessenger"> Sign up</a>
                </div>
            </div>
        </div>
    </div>
</div>
<div>

</div>
</body>
</html>
