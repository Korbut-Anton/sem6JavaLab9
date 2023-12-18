<%@ page import="com.example.helpers.ButtonNames" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<html>
<head>
    <title>Statistics</title>
    <link href="style.css" rel="stylesheet">
</head>
<body>
<form action="student-manager" method="post">
    <jsp:useBean id="statistics" scope="request" class="com.example.beans.Statistics"/>
    <div style="text-align: center">
        <h3>Failed students:
            <jsp:getProperty name="statistics" property="failed"/>
        </h3>
        <h3>Passed students:
            <jsp:getProperty name="statistics" property="passed"/>
        </h3>
        <h3>Excellent students:
            <jsp:getProperty name="statistics" property="excellent"/>
        </h3>
        <input class="gradient-button" type="submit" name="button" value="<%=ButtonNames.MAIN_PAGE_STR%>">
    </div>
</form>
</body>
</html>
