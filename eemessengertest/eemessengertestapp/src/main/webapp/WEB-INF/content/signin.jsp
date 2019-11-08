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
    <title>Sign in page</title>
</head>
<body>
<form method="POST" action="j_security_check">
    User Name: <input type="text" name="j_username" /><br />
    Password: <input type="password" name="j_password" /><br />
    <input type="submit" value="Login" />
</form>
<a href="/eemessenger/sign-up">Sing up</a>
</body>
</html>
