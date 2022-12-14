<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <link rel="stylesheet" href="../../webjars/bootstrap/4.0.0-2/css/bootstrap.min.css"/>
        <title> Attendee </title>
    </head>
    <body>
        <%@ include file="../includes/navbar.jsp"%>
        <main role="main" class="container" >
            <div align="center">
                <br>
                <br>
                <br>
                <h1>User ${attendees.get(0).getName()}</h1>
                <br>
                <br>
                <p>${attendee.getLinkedInLink()}</p>
                <p>${attendee.getFacebookLink()}</p>
                <p>${attendee.getEmailAddress()}</p>
                <p>${attendee.getCurrentPosition()}</p>
                <p>${attendee.getCurrentCompany()}</p>
                <p>${attendee.getLocation()}</p>
                <br>
                <br>
                 <p><a href="/attendees/attendee/${id}/change_info"><button type="button">Change data</button></a></p>
                 <br>
                 <br>
                <p>This user attended such events:</p>
              <c:forEach var="event" items="${attendee.getEvents()}">
                  <tr>
                    <td>
                        <c:choose>
                            <c:when test="${event.getIsPassed() == true}">
                                <a href="/events/passed/${event.getId()}">${event.getName()}</a></td>
                            </c:when>
                            <c:otherwise>
                                <a href="/events/upcoming/${event.getId()}">${event.getName()}</a></td>
                           </c:otherwise>
                        </c:choose>
                    </td>
                  </tr>
              </c:forEach>
              <br>
              <br>
              <h2> Some other statistics </h2>
            <div>
        </main>
        <script src="../../webjars/jquery/3.0.0/jquery.min.js"></script>
        <script src="../../webjars/popper.js/1.12.9-1/umd/popper.min.js"></script>
        <script src="../../webjars/bootstrap/4.0.0-2/js/bootstrap.min.js"></script>
    </body>
</html>