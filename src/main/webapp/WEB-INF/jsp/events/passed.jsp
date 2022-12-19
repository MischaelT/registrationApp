<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <link rel="stylesheet" href="/css/styles.css"/>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.1.0/js/all.js" crossorigin="anonymous"></script>
        <title>Passed events</title>
    </head>
    <body class="sb-nav-fixed">
        <%@ include file="../includes/navbar.jsp"%>
        <div id="layoutSidenav">
            <%@ include file="../includes/sidebar.jsp"%>
            <div id="layoutSidenav_content">
                <main>
                    <div align="center">
                        <br>
                        <br>
                        <br>
                        <h1>List of Passed Events</h1>
                        <br>
                        <c:choose>
                            <c:when test="${events.isEmpty()}">
                                <p>Oh, seems that there is no events</p>
                            </c:when>
                            <c:otherwise>
                                <table class="table table-hover">
                                    <thead>
                                        <tr>
                                          <th scope="col">Event</th>
                                            <th scope="col">Linked Link</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                      <c:forEach var="event" items="${events}">
                                          <tr>
                                            <td><a href="passed/${event.getId()}">${event.getName()}</a></td>
                                            <td><a href="https://${event.getLinkedInLink()}">@linked</a></td>
                                          </tr>
                                      </c:forEach>
                                    </tbody>
                                </table>
                            </c:otherwise>
                        </c:choose>
                    <div>
                </main>
                <%@ include file="../includes/footer.jsp"%>
            <div>
        <div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="/scripts/scripts.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/simple-datatables@latest" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
    </body>
</html>