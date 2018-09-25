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
<%@ include file="../jspf/basicHeader.jspf"%>
<form:form method="post" modelAttribute="user">
    ID:<form:label path="id"/><br>
    First Name:<form:input path="firstName"/><br>
    Last Name:<form:input path="lastName"/><br>
    E-Mail:<form:input path="email"/><br>
    Password:<form:password path="password"/><br>

    <input type="submit" value="Register"><br>
</form:form>

</body>
</html>