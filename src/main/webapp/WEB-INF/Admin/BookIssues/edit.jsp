<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Book Issue | LMS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<div class="admin-layout">
    <jsp:include page="/WEB-INF/Admin/side-nav.jsp"/>

    <section class="main-content">
        <h2 class="content-heading">Edit Book Issue</h2>

        <p>Book: <c:out value="${issue.bookTitle}"/></p>
        <p>Member: <c:out value="${issue.memberName}"/></p>
        <p>Issue Date: <c:out value="${issue.issueAt}"/></p>

        <form class="form"
              action="${pageContext.request.contextPath}/admin/book-issues-edit"
              method="post">

            <input type="hidden" name="id" value="${issue.id}">

            <label>Due Date</label>
            <input type="date" name="due_date" value="${issue.dueDate}" required>

            <button class="btn-primary" type="submit">
                Update Due Date
            </button>
        </form>
    </section>
</div>
</body>
</html>
