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
        <title>Attendee</title>
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
                        <h1>Attendee  ${attendee.getName()}</h1>
                        <br>
                        <br>
                        <table>

                          <colgroup>
                            <col span="3" >
                          </colgroup>
                            <tr>
                                <td style="width:20%"> <b>LinkedIn:</b><td>
                                <td style="width:40%"><a href="${attendee.getLinkedInLink()}">@linked</a></td>
                                <td style="width:20%" rowspan="6"><p><a href="/attendees/attendee/${id}/update"><button type="button">Change data</button></a></p></td>
                            </tr>
                            <tr>
                                <td><b>Facebook:</b><td>
                                <td>${attendee.getFacebookLink()}</td>
                            </tr>
                            <tr>
                                <td><b>Email:</b><td>
                                <td>${attendee.getEmailAddress()}</td>
                            </tr>
                            <tr>
                                <td><b>Position:</b><td>
                                <td>${attendee.getCurrentPosition()}</td>
                            </tr>
                            <tr>
                                <td><b>Company:</b><td>
                                <td>${attendee.getCurrentCompany()}</td>
                            </tr>
                            <tr>
                                <td><b>Location:</b><td>
                                <td>${attendee.getLocation()}</td>
                            </tr>
                        </table>
                        <br>
                        <br>
                        <c:choose>
                            <c:when test="${attendee.getExperience()!=null}">
                                <div align="center" style="white-space: pre-line">
                                    <h3>Experience:</h3>
                                     <c:forEach var="exper" items="${experience}">
                                           ${exper}
                                     </c:forEach>
                                     <br>
                                </div>
                            </c:when>
                        </c:choose>
                        <h3>${attendee.getName()} attended such events:</h3>
                        <table>
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
                      </table>
                      <br>
                      <br>
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