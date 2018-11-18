<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: thyvador
  Date: 13/11/18
  Time: 19:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>LeaderBoard</title>
</head>
<body>

<h1>Leader board</h1>
<table>
    <thead>
    <tr>
        <th>Name</th>
        <th>Level</th>
        <th>Score</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${users}" var="user">
        <tr>
            <th>${user.getLogin()}</th>
            <td>${user.getLevel()}</td
            >
            <td>${user.getDataUploaded()}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
