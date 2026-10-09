<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Author Details | LMS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<div class="admin-layout">
    <jsp:include page="/WEB-INF/Admin/side-nav.jsp"/>
    <section class="main-content">
        <h2 class="content-heading">Author Details</h2>
        <a class="btn-primary" href="authors">All Authors</a>
        <form class="form" action="author-edit" method="post">
            <c:if test="${not empty error}">
                <div class="error"><c:out value="${error}"/></div>
            </c:if>
            <c:if test="${not empty success}">
                <div class="success"><c:out value="${success}"/></div>
            </c:if>
            <input type="hidden" name="id" value="${author.id}">
            <input type="text" name="name" placeholder="Author Name" value="${author.name}" required>
            <textarea name="biography" cols="30" rows="5" placeholder="Description">${author.biography}</textarea>
            <button class="btn-primary" type="submit">Update</button>
        </form>
    </section>
</div>
</body>
</html>
