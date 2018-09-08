<%@ page import="com.sun.org.apache.xpath.internal.operations.Bool" %><%--
  Created by IntelliJ IDEA.
  User: jadzeineddine
  Date: 4/29/18
  Time: 7:00 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
    <link rel="stylesheet" href="/css/main.css">
    <link rel="stylesheet" href="/css/user.css">
</head>
<body>
    <%@ include file = "navbar.jsp" %>
    <% if(request.getSession().getAttribute("user") != null){ %>
    <div class = "profile">
        <div class ="identification">
            <p id = "username"> ${user.getName()}</p>
            <p id = "fullName">${user.getFirstName()} ${user.getLastName()}</p>
        </div>
        <div class = "profilePicture">
            <img src = "${user.getPictureURL()}"/>
        </div>
        <div class = "additional">
            <p>Age : ${user.getAge()} </p>
            <p>E-Mail : ${user.getEmail()} </p>
            <p>Phone : ${user.getPhoneNum()}</p>
            <p>Bio : ${user.getBio()} </p>
        </div>
        <% if( (Boolean) request.getAttribute("allowEdit")){ %>
        <a href = "/useredit"><button style = "margin-left: 45%; margin-bottom: 10px">Edit</button></a>
        <%}%>
    </div>
    <% } else{ %>
    <p>Not logged in!</p>
    <% } %>
</body>
</html>
