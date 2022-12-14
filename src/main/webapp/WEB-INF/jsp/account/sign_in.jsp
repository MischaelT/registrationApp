<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <link rel="stylesheet" href="../webjars/bootstrap/4.0.0-2/css/bootstrap.min.css"/>
        <title>Account</title>
    </head>
    <body>
        <%@ include file="../includes/navbar.jsp"%>
        <main role="main" class="container" >
            <div align="center">
                    <br>
                    <br>
                    <br>
                    <br>
                    <br>
                    <h1>Account page</h1>
                    <br>
                    <table>

                    <c:forEach var="user" items="${users}">
                        <tr>
                          <td><a href="">${user.getName()}</a></td>

                        </tr>
                    </c:forEach>

                    </table>
                    <br>
                    <br>
                    <h2> Statistics </h2>
                    <br>
                    <br>
            <div>
        </main>
        <script src="../webjars/jquery/3.0.0/jquery.min.js"></script>
        <script src="../webjars/popper.js/1.12.9-1/umd/popper.min.js"></script>
        <script src="../webjars/bootstrap/4.0.0-2/js/bootstrap.min.js"></script>
    </body>
</html>