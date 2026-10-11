<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Books | LMS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
 <div class="admin-layout">
     <jsp:include page="/WEB-INF/Admin/side-nav.jsp"/>
     <section class="main-content">
         <h2 class="content-heading">Books</h2>
         <a class="btn-primary" href="books-create">Add New Book</a>
         <table class="table">
             <tr>
                 <th>#SN</th>
                 <th>Title</th>
                 <th>Publisher</th>
                 <th>Stock</th>
                 <th>Image</th>
                 <th>Created At</th>
                 <th>Action</th>
             </tr>
             <c:forEach var="book" items="${books}">
                 <tr>
                     <td>${book.id}</td>
                     <td>${book.title}</td>
                     <td>${book.publisher}</td>
                     <td>${book.availableQuantity}</td>
                     <td>${book.image}</td>
                     <td>${book.createdAt}</td>
                     <td>
                        <div class="input-group">
                            <a class="btn-secondary" href="book-view?id=${book.id}">View</a>
                            <a class="btn-secondary" href="book-edit?id=${book.id}">Edit</a>
                            <form action="books" method="post">
                                <input type="hidden" name="id" value="${book.id}">
                                <button class="btn-secondary">Remove</button>
                            </form>
                        </div>
                     </td>
                 </tr>
             </c:forEach>
         </table>
     </section>
 </div>
</body>
</html>
