<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Categories | LMS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
 <div class="admin-layout">
     <jsp:include page="/WEB-INF/Admin/side-nav.jsp"/>
     <section class="main-content">
         <h2 class="content-heading">Categories</h2>
         <a class="btn-primary" href="category-create">Add New Category</a>
         <table class="table">
             <tr>
                 <th>#SN</th>
                 <th>Name</th>
                 <th>Created At</th>
                 <th>Action</th>
             </tr>
             <c:forEach var="category" items="${categories}">
                 <tr>
                     <td>${category.id}</td>
                     <td>${category.name}</td>
                     <td>${category.createdAt}</td>
                     <td>
                        <div class="input-group">
                            <a class="btn-secondary" href="category-edit?id=${category.id}">Edit</a>
                            <form action="category" method="post">
                                <input type="hidden" name="id" value="${category.id}">
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
