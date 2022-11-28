<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <link rel="stylesheet" href="../../webjars/bootstrap/4.0.0-2/css/bootstrap.min.css"/>
        <title> Passed Events </title>
    </head>
    <body>
        <%@ include file="includes/navbar.jsp"%>
        <main role="main" class="container" >
            <div align="center">
                <br>
                <br>
                <br>
                <h1>Event ${events.get(0).getName()}</h1>
                <br>
                <br>
                <h2>List of users</h2>

                <c:choose>
                    <c:when test="${events.get(0).getUsers().isEmpty()}">
                        <p>Seems there is no users</p>
                          <c:choose>
                             <c:when test="${!(events.get(0).getIsPassed())}">
                                <p>Would you like to add them? </p>
                                <p> <a href="../../users/new_user">Add new</a> </p>
                             </c:when>
                          </c:choose>
                    </c:when>
                    <c:otherwise>

                        <table class="table table-hover">
                          <thead>
                            <tr>
                              <th scope="col">Name</th>
                              <th scope="col">Linked</th>
                              <th scope="col">Facebook</th>
                            </tr>
                          </thead>
                          <tbody>
                                <c:forEach var="user" items="${events.get(0).getUsers()}">
                                    <tr>
                                      <td><a href="../../users/user/${user.getId()}">${user.getName()}</a></td>
                                      <td><a href="${user.getLinkedInLink()}">@linked</a></td>
                                      <td><a href="${user.getFacebookLink()}">@facebook</a></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                          </table>

                          <c:choose>
                             <c:when test="${!(events.get(0).getIsPassed())}">
                                <p>Would you like to add more? </p>
                                <p> <a href="../../users/new_user">Add new</a> </p>
                             </c:when>
                          </c:choose>

                    </c:otherwise>
                </c:choose>
            <div>
        </main>
        <script src="../../webjars/jquery/3.0.0/jquery.min.js"></script>
        <script src="../../webjars/popper.js/1.12.9-1/umd/popper.min.js"></script>
        <script src="../../webjars/bootstrap/4.0.0-2/js/bootstrap.min.js"></script>
    </body>
</html>