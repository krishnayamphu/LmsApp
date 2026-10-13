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
        <table class="table">
            <tr>
                <th>Book Title</th>
                <td>${book.title}</td>
            </tr>
            <tr>
                <th>Book Publisher</th>
                <td>${book.publisher}</td>
            </tr>
            <tr>
                <th>Publication Year</th>
                <td>${book.publicationYear}</td>
            </tr>
            <tr>
                <th>Book Edition</th>
                <td>${book.edition}</td>
            </tr>
            <tr>
                <th>Total Books</th>
                <td>${book.quantity}</td>
            </tr>
            <tr>
                <th>Available Books</th>
                <td>${book.availableQuantity}</td>
            </tr>
            <tr>
                <th>Category</th>
                <td>${book.category.name}</td>
            </tr>
            <tr>
                <th>Writers/Authors</th>
                <td>-</td>
            </tr>
            <tr>
                <th>Cover Image</th>
                <th>
                    <img width="200" src="${pageContext.request.contextPath}/uploads/${book.image}"/>
                </th>
            </tr>
        </table>
    </section>
</div>
</body>
</html>
