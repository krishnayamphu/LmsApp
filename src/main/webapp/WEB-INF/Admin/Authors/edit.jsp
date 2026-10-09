<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Category | LMS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<div class="admin-layout">
    <aside class="side-nav">
        <a href="dashboard">LMS</a>
        <hr>
        <nav>
            <a href="">Books</a>
            <a href="category">Categories</a>
            <a href="">Authors</a>
            <a href="">Users</a>
        </nav>
    </aside>
    <section class="main-content">
        <h2 class="content-heading">Category Details</h2>
        <a class="btn-primary" href="category">All Categories</a>
        <form class="form" action="category-edit" method="post">
            <c:if test="${not empty error}">
                <div class="error"><c:out value="${error}"/></div>
            </c:if>
            <c:if test="${not empty success}">
                <div class="success"><c:out value="${success}"/></div>
            </c:if>
            <input type="hidden" name="id" value="${category.id}">
            <input type="text" name="name" placeholder="Category Name" value="${category.name}" required>
            <textarea name="description" cols="30" rows="5" placeholder="Description">${category.description}</textarea>
            <button class="btn-primary" type="submit">Update</button>
        </form>
    </section>
</div>
</body>
</html>
