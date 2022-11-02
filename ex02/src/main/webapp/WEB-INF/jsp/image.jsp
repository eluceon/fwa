<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <title>Image</title>
</head>
<body>
<a href="/logout">Logout</a>
<% String image = (String) request.getSession().getAttribute("path"); %>
<div class="image">
    <image src="<%= image %>"></image>
</div>
</body>
</html>
