<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Authors | LMS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
 <div class="admin-layout">
     <jsp:include page="/WEB-INF/Admin/side-nav.jsp"/>
     <section class="main-content">
         <h2 class="content-heading">Authors</h2>
         <a class="btn-primary" href="author-create">Add New Author</a>
         <table class="table">
             <tr>
                 <th>#SN</th>
                 <th>Name</th>
                 <th>Created At</th>
                 <th>Action</th>
             </tr>
             <c:forEach var="author" items="${authors}">
                 <tr>
                     <td>${author.id}</td>
                     <td>${author.name}</td>
                     <td>${author.createdAt}</td>
                     <td>
                        <div class="input-group">
                            <a class="btn-secondary" href="author-edit?id=${author.id}">Edit</a>
                            <form action="author" method="post">
                                <input type="hidden" name="id" value="${author.id}">
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
