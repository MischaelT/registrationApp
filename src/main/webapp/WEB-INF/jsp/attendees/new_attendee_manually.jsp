<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
        <title>New attendee</title>
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
                        <h1>Add new user</h1>
                        <br>

                        <form:form method="POST"
                          action="${postLink}" modelAttribute="attendee">
                             <table>
                                <p>

                                    <tr>
                                        <td><form:label path="name">Name   </form:label></td>
                                        <td><form:input path="name"/></td>
                                    </tr>
                                </p>
                                <p>
                                    <tr>
                                        <td><form:label path="linkedInLink">LinkedIn link   </form:label></td>
                                        <td><form:input path="linkedInLink"/></td>
                                    </tr>
                                </p>
                                <p>
                                    <tr>
                                        <td><form:label path="facebookLink">facebook Link   </form:label></td>
                                        <td><form:input path="facebookLink"/></td>
                                    </tr>
                                </p>

                              <c:choose>
                                 <c:when test="${!fromEvent}">
                                     <p>
                                     <tr>
                                         <td><form:label path="events">Event name</form:label></td>
                                         <td>
                                             <div class="input-group mb-3">
                                               <form:select path="events" name="selected_event" class="custom-select" id="inputGroupSelect02">
                                                 <option selected>Choose...</option>
                                                   <c:forEach var="event" items="${upcoming_events}">
                                                         <form:option value="${event.getId()}">${event.getName()}</form:option>
                                                   </c:forEach>
                                               </form:select>
                                             </div>
                                         </td>
                                     </tr>
                                 </p>
                                 </c:when>
                              </c:choose>
                                <p>
                                    <tr>
                                        <td><input type="submit" value="Submit"></input></td>
                                    </tr>
                                </p>
                            </table>
                        </form:form>
                          <c:choose>
                             <c:when test="${false}">
                                <form:form method="POST" action="" modelAttribute="form_content">
                                  <p> Would you like to collect some information automatically? </p>
                                      <table>
                                         <tr>
                                            <td><input type="submit" value="Lets do it!"/></td>
                                         </tr>
                                        </table>
                                </form:form>
                               </c:when>
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
