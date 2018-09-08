<%--
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
<form action="/useredit" method="POST">
    <div class = "profile">
        <div class ="identification">
            <p id = "fullName">First Last : </p> <input type="text" name="first" value = "${user.getFirstName()}" id="first"> <input value = "${user.getLastName()}" type="text" name="last" id="last">
        </div>
        <div class = "profilePicture">
            <p>Profile Pic URL:</p> <input type="text" name="pictureurl" value = "${user.getPictureURL()}" id="pictureurl">
        </div>
        <div class = "additional">
            <p>Age : </p> <input type = "number" name = "age" id = "age" value = "${user.getAge()}" >
            <p>E-Mail : </p> <input type = "text" name = "email" id = "email" value = "${user.getEmail()}" >
            <p>Phone : </p> <input type = "number" name = "phone" id = "phone" value = "${user.getPhoneNum()}" >
            <p>Bio : </p> <input type = "text" name = "bio" id = "bio" value = "${user.getBio()}" >
        </div>
        <button type="submit" style = "margin-left: 45%; margin-bottom: 10px">Submit</button>
    </div>
</form>
<% } else{ %>
<p>Not logged in!</p>
<% } %>
</body>
</html>
