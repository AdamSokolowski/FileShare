<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User form</title>
</head>
<body>
<h1>Login</h1>
${SPRING_SECURITY_LAST_EXCEPTION.message}
<form action="login" method="post">
    E-Mail:<input type="e-mail" name="username" value=""/><br>
    Password:<input type="password" name="password"/><br>

    <input name="submit" type="submit" value="Login"><br>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

</body>
</html>