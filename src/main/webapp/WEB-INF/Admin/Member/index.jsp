<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Members | LMS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
 <div class="admin-layout">
     <jsp:include page="/WEB-INF/Admin/side-nav.jsp"/>
     <section class="main-content">
         <h2 class="content-heading">Members</h2>
         <table class="table">
             <tr>
                 <th>#SN</th>
                 <th>Name</th>
                 <th>Email</th>
                 <th>Mobile</th>
                 <th>Status</th>
                 <th>Created At</th>
                 <th>Action</th>
             </tr>
             <c:forEach var="member" items="${members}">
                 <tr>
                     <td>${member.id}</td>
                     <td>${member.name}</td>
                     <td>${member.email}</td>
                     <td>${member.mobile}</td>
                     <td>${member.status ? 'Active' : 'Disabled'}</td>
                     <td>${member.createdAt}</td>
                     <td>
                        <div class="input-group">
                            <a class="btn-secondary" href="member-view?id=${member.id}">View</a>
                            <a class="btn-secondary" href="member-edit?id=${member.id}">Edit</a>
                        </div>
                     </td>
                 </tr>
             </c:forEach>
         </table>
     </section>
 </div>
</body>
</html>
