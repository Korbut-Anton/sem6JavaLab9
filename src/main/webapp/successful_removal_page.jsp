<%@ page import="com.example.helpers.ButtonNames" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Successful removal</title>
    <link href="style.css" rel="stylesheet">
</head>
<body>
<form action="student-manager" method="post">
    <div style="text-align: center">
        <h1>Student has been successfully removed!</h1>
        <input class="gradient-button" type="submit" name="button" value="<%=ButtonNames.MAIN_PAGE_STR%>">
    </div>
</form>
</body>
</html>
