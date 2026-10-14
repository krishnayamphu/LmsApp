<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Issue Book | LMS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<div class="admin-layout">
    <jsp:include page="/WEB-INF/Admin/side-nav.jsp"/>
    <section class="main-content">
        <h2 class="content-heading">Issue Book</h2>
        <form class="form" action="${pageContext.request.contextPath}/admin/book-issues-create" method="post">
            <label>Book</label>
            <select name="book_id" required>
                <option value="">Select book</option>
                <c:forEach var="book" items="${books}">
                    <c:if test="${book.availableQuantity > 0}">
                        <option value="${book.id}">
                            <c:out value="${book.title}"/>
                            (Available: ${book.availableQuantity})
                        </option>
                    </c:if>
                </c:forEach>
            </select>

            <label>Member</label>
            <select name="member_id" required>
                <option value="">Select member</option>
                <c:forEach var="member" items="${members}">
                    <option value="${member.id}">
                        <c:out value="${member.name}"/>
                    </option>
                </c:forEach>
            </select>

            <label>Issue Date</label>
            <input type="date" name="issue_date" value="<%= java.time.LocalDate.now() %>" required>

            <label>Due Date</label>
            <input type="date" name="due_date" value="<%= java.time.LocalDate.now().plusDays(14) %>" required>

            <textarea name="notes" rows="4" placeholder="Notes"></textarea>

            <button class="btn-primary" type="submit">
                Issue Book
            </button>
        </form>
    </section>
</div>
</body>
</html>
