<%@ page import="com.example.helpers.ButtonNames" %>
<%
    String message = "Message: " + exception.getMessage();
    String excClass = "Exception class: " + exception.getClass().getSimpleName();
%>
<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" language="java" %>
<html>
<head>
    <title>Exception</title>
    <link href="style.css" rel="stylesheet">
</head>
<body>
<form action="student-manager" method="post">
    <div style="text-align: center">
        <h2 style="color: red">Exception occurred on server</h2>
        <p style="color: red"><%= message %>
        </p>
        <p style="color: red"><%=excClass %>
        </p>
        <br><br>
        <input class="gradient-button" type="submit" name="button"
               value="<%=ButtonNames.MAIN_PAGE_STR%>"/>
    </div>
</form>
</body>
</html>
