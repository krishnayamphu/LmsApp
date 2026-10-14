<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Member Details | LMS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<div class="admin-layout">
    <jsp:include page="/WEB-INF/Admin/side-nav.jsp"/>
    <section class="main-content">
        <h2 class="content-heading">Member Details</h2>
        <a class="btn-primary" href="members">All Members</a>
        <form class="form" action="member-edit" method="post">
            <c:if test="${not empty error}">
                <div class="error"><c:out value="${error}"/></div>
            </c:if>
            <c:if test="${not empty success}">
                <div class="success"><c:out value="${success}"/></div>
            </c:if>
            <input type="hidden" name="id" value="${member.id}">
            <input type="text" name="name" placeholder="Full Name" value="${member.name}" required>
            <input type="email" name="email" placeholder="Email" value="${member.email}" required>
            <input type="text" name="mobile" placeholder="mobile" value="${member.mobile}" required>
            <input type="text" name="address" placeholder="Address" value="${member.address}" required>
            <div class="input-group">
                <label>Status</label>
                <label>
                    <input type="radio" name="status" value="true" ${member.status ? 'checked' : ''}> Active
                </label>
                <label>
                    <input type="radio" name="status" value="false" ${!member.status ? 'checked' : ''}> Disabled
                </label>
            </div>
            <button class="btn-primary" type="submit">Update</button>
        </form>


    </section>
</div>
</body>
</html>
