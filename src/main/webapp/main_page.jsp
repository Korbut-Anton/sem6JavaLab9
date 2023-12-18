<%@ page import="com.example.helpers.ButtonNames" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<html>
<head>
    <title>Main</title>
    <link href="style.css" rel="stylesheet">
</head>
<body>
<form action="student-manager" method="post">
    <div style="text-align: center">
        <h1>Welcome to student manager! Choose the option.</h1>
        <input class="gradient-button" type="submit" name="button" value="<%=ButtonNames.ADD_STUDENT_STR%>">
        <input class="gradient-button" type="submit" name="button" value="<%=ButtonNames.REMOVE_STUDENT_STR%>">
        <input class="gradient-button" type="submit" name="button" value="<%=ButtonNames.UPDATE_STATS_STR%>">
    </div>
</form>
</body>
</html>
