<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Category | LMS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<div class="admin-layout">
    <jsp:include page="/WEB-INF/Admin/side-nav.jsp"/>
    <section class="main-content">
        <h2 class="content-heading">Category Details</h2>
        <a class="btn-primary" href="category">All Categories</a>
        <form class="form" action="category-create" method="post">
            <input type="text" name="name" placeholder="Category Name" required>
            <textarea name="description" cols="30" rows="5" placeholder="Description"></textarea>
            <button class="btn-primary" type="submit">Create</button>
        </form>
    </section>
</div>
</body>
</html>
