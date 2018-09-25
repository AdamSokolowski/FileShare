<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Book form</title>
</head>
<body>

<form:form method="post" modelAttribute="book">
    Title:<form:input path="title"/><br>
    Author:<form:input path="author"/><br>
    <input type="submit" value="Save"><br>
</form:form>

</body>
</html>
