<%@ page language="java" contentType="text/html;  charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset="UTF-8">
    <title>Upload Page</title>
</head>
<body>
<%@ include file="../jspf/basicHeader.jspf"%>
<h2> ${message} </h2>
<form method="post" action="${pageContext.request.contextPath}/upload.do" enctype="multipart/form-data" >
    <input type="file" name="file" />
    <p>File Name: <input type="text" name="filename" /></p>
    <button type="submit">Upload</button>
</form>
</body>
</html>