<%@ page import="com.example.helpers.ButtonNames" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add student</title>
    <link href="style.css" rel="stylesheet">
</head>
<body>
<form action="student-manager" method="post">
    <table style="width: 100%; text-align: center">
        <tr>
            <td>
                <h2 style="display: inline">Name: </h2>
                &emsp;&ensp;
                <label>
                    <input type="text" name="name" value="">
                </label>
                <br><br>
                <h2 style="display: inline">Surname: </h2>
                <label>
                    <input type="text" name="surname" value="">
                </label>
                <br><br>
                <h2 style="display: inline">Group: </h2>
                &emsp;&ensp;
                <label>
                    <input type="text" name="group" value="">
                </label>
                <br><br>
                <h2>Write grades through space in order, which corresponds the table:</h2>
                <label>
                    <input type="text" name="grades" value="">
                </label>
                <br><br>
                <input class="gradient-button" type="submit" name="button" value="<%=ButtonNames.MAIN_PAGE_STR%>">
                &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                <input class="gradient-button" type="submit" name="button" value="<%=ButtonNames.ADD_STR%>">
            </td>
            <td>
                <div style="max-height: 400px; overflow-y: scroll">
                    <table class="b-table" style="width: 100%">
                        <tr class="b-tr">
                            <th class="b-th">Subjects</th>
                        </tr>
                        <c:forEach var="subject" items="${requestScope.subjects}">
                            <tr class="b-tr">
                                <td class="b-td">${subject}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </td>
        </tr>
    </table>
    <c:if test="${requestScope.invalidInput == true}">
        <h3 style="color: red; text-align: center">Incorrect input! Try again.</h3>
    </c:if>
</form>
</body>
</html>
