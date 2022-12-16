<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <link rel="stylesheet" href="../../../webjars/bootstrap/4.0.0-2/css/bootstrap.min.css"/>
        <title> Users </title>
    </head>
    <body>
        <%@ include file="../includes/navbar.jsp"%>
        <main role="main" class="container" >
            <div align="center">
                    <br>
                    <br>
                    <br>
                    <h1>List of attendee from database</h1>
                    <br>
                    <p><input type="text" placeholder="Search by attendee..."> <button type="button">submit</button></p>
                    <br>
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
                                <c:forEach var="attendee" items="${attendees}">
                                    <tr>
                                      <td><a href="../../attendees/attendee/${attendee.getId()}">${attendee.getName()}</a></td>
                                      <td><a href="${attendee.getLinkedInLink()}">@linked</a></td>
                                      <td><a href="${attendee.getFacebookLink()}">@facebook</a></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                          </table>
                      <p>Would you like to add more? </p>
                      <p> <a href="/attendees/new_attendee/manually"> Add manually </a> </p>
                      <p> <a href="/attendees/new_attendee/automatically"> Add automatically </a> </p>
            <div>
        </main>
        <script src="../../../webjars/jquery/3.0.0/jquery.min.js"></script>
        <script src="../../../webjars/popper.js/1.12.9-1/umd/popper.min.js"></script>
        <script src="../../../webjars/bootstrap/4.0.0-2/js/bootstrap.min.js"></script>
    </body>
</html>