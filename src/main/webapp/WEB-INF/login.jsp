<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Login</title>
</head>
<body>
<form action="login" method="post">
    <%
        if(session.getAttribute("error")!=null){
            out.print(session.getAttribute("error"));
        }
    %>
    <input type="email" name="email" placeholder="Email" required>
    <input type="password" name="password" placeholder="Password" required>
    <button>Login</button>
</form>
</body>
</html>
