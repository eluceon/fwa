<%@ page import="edu.school21.cinema.models.User" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="edu.school21.cinema.models.Authentication" %>
<%@ page import="edu.school21.cinema.models.Image" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <title>Profile</title>
</head>
<body>
<a href="/logout">Logout</a>
<center>
    <h1>Profile</h1>
</center>
<jsp:useBean id="user" class="edu.school21.cinema.models.User" scope="session" />
<%
    int qtyImages = user.getImages().size();
    String path = qtyImages != 0 ? user.getImages().get(qtyImages - 1).getPath() : "images/ava.png";
%>
<div class="about">
    <div class="avatar">
        <image src=<%= path %>></image>
        <form action="/images" method="post" enctype="multipart/form-data">
            <label>
                Select
                <input type="file" name="file" multiple>
            </label>
            <input type="submit" value="Upload">
        </form>
    </div>
    <p>
        First Name:
        <jsp:getProperty name="user" property="firstName"/>
    </p>
    <p>
        Last Name:
        <jsp:getProperty name="user" property="lastName"/>
    </p>
    <p>
        Phone:
        <jsp:getProperty name="user" property="phoneNumber"/>
    </p>
    <p>
        Email:
        <jsp:getProperty name="user" property="email"/>
    </p>
</div>
<table class="auth">
    <tr>
        <th>DATE</th>
        <th>IP</th>
    </tr>

    <c:forEach var="authentication" items="${user.authentications}"  >
        <tr>
            <td>
                <c:out value="${authentication.date}" />
            </td>
            <td>
                <c:out value="${authentication.ip}" />
            </td>
        </tr>
    </c:forEach>
</table>

<table>
    <tr>
        <th>FILE NAME</th>
        <th>SIZE</th>
        <th>MIME</th>
    </tr>
    <c:forEach var="image" items="${user.images}" >
        <tr>
            <td>
                <a href="/images?id=<c:out value="${image.id}"/>">
                    <c:out value="${image.name}"/>
                </a>
            </td>
            <td>
                <c:out value="${image.size}"/>
            </td>
            <td>
                <c:out value="${image.mime}"/>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
