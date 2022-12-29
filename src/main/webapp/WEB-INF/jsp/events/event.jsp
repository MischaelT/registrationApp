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
        <title>Event</title>
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
                                <h1> ${event.getName()} event</h1>
                                <br>

                                  <c:choose>
                                     <c:when test="${!(event.getIsPassed())}">
                                         <p><a href="/events/upcoming/${id}/change_data"><button type="button">Change name/status</button></a></p>
                                     </c:when>
                                  </c:choose>

                                <br>


                                <c:choose>

                                    <c:when test="${event.getAttendees().isEmpty()}">
                                        <p>Seems there is no attendees</p>
                                          <c:choose>
                                             <c:when test="${!(event.getIsPassed())}">
                                                <p>Would you like to add them? </p>
                                                <p> <a href="/events/upcoming/${id}/new_attendee/manually">Add manually</a> </p>
                                                <p> <a href="/events/upcoming/${id}/new_attendee/automatically">Add automatically</a> </p>
                                             </c:when>
                                          </c:choose>
                                    </c:when>

                                    <c:otherwise>
                                        <form method="POST" action="/events/upcoming/${id}">
                                            <table class="table table-hover">

                                              <thead>
                                                <tr>
                                                  <th scope="col">Name</th>
                                                  <th scope="col">Linked</th>
                                                  <c:choose>
                                                     <c:when test="${!(event.getIsPassed())}">
                                                          <th scope="col">Present</th>
                                                     </c:when>
                                                  </c:choose>
                                                </tr>
                                              </thead>

                                              <tbody>

                                                <c:forEach var="attendee" items="${event.getAttendees()}">
                                                    <tr>
                                                        <td><a href="../../attendees/attendee/${attendee.getId()}">${attendee.getName()}</a></td>
                                                        <td><a href="${attendee.getLinkedInLink()}">@linked</a></td>

                                                        <c:choose>
                                                           <c:when test="${!(event.getIsPassed())}">
                                                               <c:choose>
                                                                  <c:when test="${attended.contains(attendee.getId())}">
                                                                       <td><input type="checkbox" name="attendees" value="${attendee.getId()}" checked/></td>
                                                                  </c:when>
                                                                  <c:otherwise>
                                                                        <td><input type="checkbox" name="attendees" value="${attendee.getId()}"/></td>
                                                                  </c:otherwise>
                                                               </c:choose>
                                                           </c:when>
                                                        </c:choose>
                                                    </tr>
                                                </c:forEach>
                                              </tbody>
                                            </table>
                                            <c:choose>
                                               <c:when test="${!(event.getIsPassed())}">
                                                    <p><input type="submit" value="Submit"></p>
                                               </c:when>
                                            </c:choose>
                                        </form>
                                          <br>
                                          <br>
                                          <c:choose>
                                             <c:when test="${!(event.getIsPassed())}">
                                                <p>Would you like to add more? </p>
                                                <p> <a href="/events/upcoming/${id}/new_attendee/manually"> Add manually </a> </p>
                                                <p> <a href="/events/upcoming/${id}/new_attendee/automatically"> Add automatically </a> </p>
                                             </c:when>
                                          </c:choose>
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