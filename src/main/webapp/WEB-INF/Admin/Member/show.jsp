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
        <table class="table">
            <tr>
                <th>Name</th>
                <td>${member.name}</td>
            </tr>
            <tr>
                <th>Email</th>
                <td>${member.email}</td>
            </tr>
            <tr>
                <th>Mobile</th>
                <td>${member.mobile}</td>
            </tr>
            <tr>
                <th>Address</th>
                <td>${member.address}</td>
            </tr>
            <tr>
                <th>Status</th>
                <td>
                    ${member.status ? 'Active' : 'Disabled'}
                </td>
            </tr>
            <tr>
                <th>Registered Date</th>
                <td>${member.createdAt}</td>
            </tr>
        </table>
    </section>
</div>
</body>
</html>
