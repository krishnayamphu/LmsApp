<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Categories | LMS</title>
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
         <h2>Categories</h2>
         <a href="category-create">Add New Category</a>
         <table>
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
                         <a href="">Edit</a>
                         <a href="">Remove</a>
                     </td>
                 </tr>
             </c:forEach>
         </table>

     </section>
 </div>
</body>
</html>
