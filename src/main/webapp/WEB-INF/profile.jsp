<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile | LMS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<div class="admin-layout">
    <jsp:include page="/WEB-INF/sidebar.jsp"/>
    <section class="main-content">
        <h2 class="content-heading">Profile</h2>
        <form class="form" action="${pageContext.request.contextPath}/me/profile" method="post">
            <c:if test="${not empty error}">
                <p>${error}</p>
            </c:if>
            <input type="email" name="email" placeholder="Email" value="${member.email}" required readonly>
            <input type="text" name="name" placeholder="Full Name" value="${member.name}" required>
            <input type="text" name="mobile" placeholder="mobile" value="${member.mobile}" required>
            <input type="text" name="address" placeholder="Address" value="${member.address}" required>
            <button>Update</button>
        </form>
    </section>
</div>
</body>
</html>
