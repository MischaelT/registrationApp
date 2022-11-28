<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <link rel="stylesheet" href="../../../webjars/bootstrap/4.0.0-2/css/bootstrap.min.css"/>
        <title> Users </title>
    </head>
    <body>
        <%@ include file="includes/navbar.jsp"%>
        <main role="main" class="container" >
            <div align="center">
                    <br>
                    <br>
                    <br>
                    <h1>List of users from database</h1>
                    <br>
                        <table class="table table-hover">
                          <thead>
                            <tr>
                              <th scope="col">Name</th>
                              <th scope="col">Linked</th>
                              <th scope="col">Facebook</th>
                            </tr>
                          </thead>
                          <tbody>
                                <c:forEach var="user" items="${users}">
                                    <tr>
                                      <td><a href="../../users/user/${user.getId()}">${user.getName()}</a></td>
                                      <td><a href="${user.getLinkedInLink()}">@linked</a></td>
                                      <td><a href="${user.getFacebookLink()}">@facebook</a></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                          </table>


            <div>
        </main>
        <script src="../../../webjars/jquery/3.0.0/jquery.min.js"></script>
        <script src="../../../webjars/popper.js/1.12.9-1/umd/popper.min.js"></script>
        <script src="../../../webjars/bootstrap/4.0.0-2/js/bootstrap.min.js"></script>
    </body>
</html>