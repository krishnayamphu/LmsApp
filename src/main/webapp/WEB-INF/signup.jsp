<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Signup Member</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<form class="form login-form" action="${pageContext.request.contextPath}/signup" method="post">
    <c:if test="${not empty error}">
        <p>${error}</p>
    </c:if>
    <input type="text" name="name" placeholder="Full Name" required>
    <input type="email" name="email" placeholder="Email" required>
    <input type="password" name="password" placeholder="Password" required>
    <input type="text" name="mobile" placeholder="mobile" required>
    <input type="text" name="address" placeholder="Address" required>
    <button>Signup</button>
</form>
</body>
</html>
