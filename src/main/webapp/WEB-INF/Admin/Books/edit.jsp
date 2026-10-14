<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Book Details | LMS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<div class="admin-layout">
    <jsp:include page="/WEB-INF/Admin/side-nav.jsp"/>
    <section class="main-content">
        <h2 class="content-heading">Book Details</h2>
        <a class="btn-primary" href="books">All books</a>
        <form class="form" action="book-update" method="post" enctype="multipart/form-data">
            <label>Title</label>
            <input type="text" name="title" placeholder="Book Name" value="${book.title}" required>
            <label>Publisher</label>
            <input type="text" name="publisher" placeholder="Book Publisher" value="${book.publisher}" required>
            <label>Publication Year</label>
            <input type="text" name="publication_year" placeholder="Publication Year" value="${book.publicationYear}"
                   required>
            <label>Edition</label>
            <input type="text" name="edition" placeholder="Book Edition" value="${book.edition}" required>
            <label>Quantity</label>
            <input type="text" name="quantity" placeholder="Quantity" value="${book.quantity}" required>
            <label>Category</label>
            <select name="category_id">
                <option value="">Select category</option>
                <c:forEach var="category" items="${categories}">
                    <option value="${category.id}"  ${category.id == book.categoryId ? 'selected' : ''}><c:out
                            value="${category.name}"/></option>
                </c:forEach>
            </select>
            <label for="authors">Authors</label>
            <select id="authors" name="author_ids" multiple size="4">
                <c:forEach var="author" items="${authors}">
                    <option value="${author.id}"
                            <c:forEach var="selectedAuthor" items="${book.authors}">
                                <c:if test="${selectedAuthor.id == author.id}">
                                    selected
                                </c:if>
                            </c:forEach>>
                        <c:out value="${author.name}"/>
                    </option>
                </c:forEach>
            </select>
            <small>Hold <kbd>Ctrl</kbd> to select multiple authors.</small>
            <hr>
            <label>Current Image</label>
            <c:if test="${not empty book.image}">
                <img src="${pageContext.request.contextPath}/uploads/${book.image}"
                     alt="Book cover" style="max-width:150px;display:block;">
            </c:if>
            <label>Replace Image (optional)</label>
            <input type="file" name="image" accept="image/*">
            <button class="btn-primary" type="submit">Update</button>
        </form>
    </section>
</div>
</body>
</html>
