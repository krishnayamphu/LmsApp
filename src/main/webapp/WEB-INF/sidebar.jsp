<aside class="side-nav">
    <h3 class="app-title">LMS</h3>
    <hr>
    <nav>
        <a href="my-books">My Books</a>
        <a href="profile">Profile</a>
        <hr>
        <form action="${pageContext.request.contextPath}/me/logout"
              method="post">
            <button type="submit" class="btn-primary">
                Logout
            </button>
        </form>
    </nav>
</aside>