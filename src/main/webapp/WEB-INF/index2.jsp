<%--
  Created by IntelliJ IDEA.
  User: VectorBool
  Date: 03.09.2022
  Time: 21:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>Result table</h2>
<table>
    <tr>
        <th>id</th>
        <th>name</th>
        <th>profession</th>
        <th>banned</th>
        <th>experience</th>
    </tr>
    <c:forEach var="player" items="${playerList}">
        <tr>
            <td>${player.id}</td>
            <td>${player.name}</td>
            <td>${player.profession}</td>
            <td>${player.banned}</td>
            <td>${player.experience}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
