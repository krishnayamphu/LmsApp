<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My Books | LMS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<div class="admin-layout">
    <jsp:include page="/WEB-INF/sidebar.jsp"/>
    <section class="main-content">
        <h2 class="content-heading">My Books</h2>
        <c:choose>
            <c:when test="${empty myBooks}">
                <p>You have no book issue records yet.</p>
            </c:when>
            <c:otherwise>
                <table class="table">
                    <thead>
                    <tr>
                        <th>Book</th>
                        <th>Issue Date</th>
                        <th>Due Date</th>
                        <th>Return Date</th>
                        <th>Fine</th>
                        <th>Status</th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach var="issue" items="${myBooks}">
                        <tr>
                            <td>
                                <c:out value="${issue.bookTitle}"/>
                            </td>

                            <td>
                                <c:out value="${issue.issueAt}"/>
                            </td>

                            <td>
                                <c:out value="${issue.dueDate}"/>
                            </td>

                            <td>
                                <c:choose>
                                    <c:when test="${not empty issue.returnAt}">
                                        <c:out value="${issue.returnAt}"/>
                                    </c:when>
                                    <c:otherwise>Not returned</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${issue.fine == 0}">
                                        -
                                    </c:when>
                                    <c:otherwise>
                                        <fmt:formatNumber
                                                value="${issue.fine}"
                                                minFractionDigits="2"
                                                maxFractionDigits="2"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>

                            <td>
                                <c:choose>
                                    <c:when test="${issue.status eq 'ISSUED'}">
                                        <c:choose>
                                            <c:when test="${issue.dueDate lt today}">
                                                Overdue
                                            </c:when>
                                            <c:otherwise>ISSUED</c:otherwise>
                                        </c:choose>
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${issue.status}"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </section>
</div>
</body>
</html>
