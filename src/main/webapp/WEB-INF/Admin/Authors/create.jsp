<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Author | LMS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<div class="admin-layout">
    <jsp:include page="/WEB-INF/Admin/side-nav.jsp"/>
    <section class="main-content">
        <h2 class="content-heading">Author Details</h2>
        <a class="btn-primary" href="authors">All Authors</a>
        <form class="form" action="author-create" method="post">
            <input type="text" name="name" placeholder="Author Name" required>
            <textarea name="biography" cols="30" rows="5" placeholder="Biography"></textarea>
            <button class="btn-primary" type="submit">Create</button>
        </form>
    </section>
</div>
</body>
</html>
