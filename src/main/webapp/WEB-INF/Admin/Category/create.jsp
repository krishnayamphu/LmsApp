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
        <h2>Category Details</h2>
        <a href="category">All Categories</a>
        <form action="category-create" method="post">
            <input type="text" name="name" placeholder="Category Name" required>
            <textarea name="description" cols="30" rows="5" placeholder="Description"></textarea>
            <button type="submit">Create</button>
        </form>
    </section>
</div>
</body>
</html>
