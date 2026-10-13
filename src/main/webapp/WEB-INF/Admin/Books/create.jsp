<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Book | LMS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<div class="admin-layout">
    <jsp:include page="/WEB-INF/Admin/side-nav.jsp"/>
    <section class="main-content">
        <h2 class="content-heading">Category Details</h2>
        <a class="btn-primary" href="books">All books</a>
        <form class="form" action="books-create" method="post" enctype="multipart/form-data">
            <input type="text" name="title" placeholder="Book Name" required>
            <input type="text" name="publisher" placeholder="Book Publisher" required>
            <input type="text" name="publication_year" placeholder="Publication Year" required>
            <input type="text" name="edition" placeholder="Book Edition" required>
            <input type="text" name="quantity" placeholder="Quantity" required>
            <select name="category_id">
                <option value="">Select category</option>
                <c:forEach var="category" items="${categories}">
                    <option value="${category.id}"><c:out value="${category.name}"/></option>
                </c:forEach>
            </select>
            <label for="authors">Authors</label>
            <select id="authors" name="author_ids" multiple size="4">
                <c:forEach var="author" items="${authors}">
                    <option value="${author.id}"><c:out value="${author.name}"/></option>
                </c:forEach>
            </select>
            <small>Hold <kbd>Ctrl</kbd> to select multiple authors.</small>
            <hr>
            <label>Book Image</label>
            <input type="file" name="image">
            <button class="btn-primary" type="submit">Create</button>
        </form>
    </section>
</div>
</body>
</html>
