<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Book Issues | LMS</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<div class="admin-layout">
    <jsp:include page="/WEB-INF/Admin/side-nav.jsp"/>

    <section class="main-content">
        <h2 class="content-heading">Book Issues</h2>

        <a class="btn-primary"
           href="${pageContext.request.contextPath}/admin/book-issues-create">
            Issue New Book
        </a>

        <table class="table">
            <thead>
            <tr>
                <th>ID</th>
                <th>Book</th>
                <th>Member</th>
                <th>Issue Date</th>
                <th>Due Date</th>
                <th>Return Date</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach var="issue" items="${issues}">
                <tr>
                    <td><c:out value="${issue.id}"/></td>
                    <td><c:out value="${issue.bookTitle}"/></td>
                    <td><c:out value="${issue.memberName}"/></td>
                    <td><c:out value="${issue.issueAt}"/></td>
                    <td><c:out value="${issue.dueDate}"/></td>
                    <td>
                        <c:out value="${issue.returnAt}"/>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${issue.status == 'ISSUED'
                                and issue.dueDate lt today}">
                                Overdue
                            </c:when>
                            <c:otherwise>
                                <c:out value="${issue.status}"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <div class="input-group">
                            <c:if test="${issue.status == 'ISSUED'}">
                                <a class="btn-secondary"
                                   href="${pageContext.request.contextPath}/admin/book-issues-edit?id=${issue.id}">
                                    Edit Due Date
                                </a>

                                <form method="post" action="${pageContext.request.contextPath}/admin/book-issues">
                                    <input type="hidden" name="id" value="${issue.id}">
                                    <button class="btn-secondary" type="submit"
                                            onclick="return confirm('Confirm book return?')">
                                        Return
                                    </button>
                                </form>
                            </c:if>

                            <c:if test="${issue.status == 'RETURNED'}">
                                <form method="post"
                                      action="${pageContext.request.contextPath}/admin/book-issues-delete"
                                      style="display:inline">
                                    <input type="hidden" name="id"
                                           value="${issue.id}">
                                    <button class="btn-secondary" type="submit"
                                            onclick="return confirm('Delete this returned record?')">
                                        Delete
                                    </button>
                                </form>
                            </c:if>
                        </div>
                    </td>
                </tr>
            </c:forEach>

            <c:if test="${empty issues}">
                <tr>
                    <td colspan="8">No book issues found.</td>
                </tr>
            </c:if>
            </tbody>
        </table>
    </section>
</div>
</body>
</html>
