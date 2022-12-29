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
        <title>Statistics</title>
    </head>
    <body class="sb-nav-fixed">
        <%@ include file="includes/navbar.jsp"%>
        <div id="layoutSidenav">
            <%@ include file="includes/sidebar.jsp"%>
            <div id="layoutSidenav_content">
                <main>
                    <div align="center">
                        <br>
                        <br>
                        <br>
                        <h1> Statistics by all events </h1>
                        <br>
                        <br>
                        <div align="center">
                            <p> Events with the most number of attendees</p>
                            <table>
                                <tr>
                                  <th  style="width:40%" scope="col"></th>
                                  <th  style="width:20%" scope="col"></th>
                                </tr>
                                <tr>
                                    <td>
                                      <c:choose>
                                         <c:when test="${!(maxEvent.getIsPassed())}">
                                            <p><a href="/events/upcoming/${maxEvent.getId()}"> ${maxEvent.getName()} </a> </p>
                                         </c:when>
                                          <c:when test="${maxEvent.getIsPassed()}">
                                             <p><a href="/events/passed/${maxEvent.getId()}"> ${maxEvent.getName()} </a> </p>
                                          </c:when>
                                      </c:choose>
                                    </td>
                                    <td>
                                        <p>${maxQuantity}</p>
                                    </td>
                                </tr>
                            </table>
                            <p> Events with the least number of attendees</p>
                            <table>
                                <tr>
                                  <th  style="width:40%" scope="col"></th>
                                  <th  style="width:20%" scope="col"></th>
                                </tr>
                                <tr>
                                    <td>
                                      <c:choose>
                                         <c:when test="${!(minEvent.getIsPassed())}">
                                            <p><a href="/events/upcoming/${minEvent.getId()}"> ${minEvent.getName()} </a> </p>
                                         </c:when>
                                          <c:when test="${minEvent.getIsPassed()}">
                                             <p><a href="/events/passed/${minEvent.getId()}"> ${minEvent.getName()} </a> </p>
                                          </c:when>
                                      </c:choose>
                                    </td>
                                    <td>
                                        <p>${minQuantity}</p>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </main>
                <%@ include file="includes/footer.jsp"%>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="/scripts/scripts.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/simple-datatables@latest" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
    </body>
</html>
