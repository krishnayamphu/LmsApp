<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign In</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<form class="form login-form" action="${pageContext.request.contextPath}/signin" method="post">
    <c:if test="${not empty error}">
        <p>${error}</p>
    </c:if>
    <input type="email" name="email" placeholder="Email" required>
    <input type="password" name="password" placeholder="Password" required>
    <button>Sign in</button>
</form>
</body>
</html>
