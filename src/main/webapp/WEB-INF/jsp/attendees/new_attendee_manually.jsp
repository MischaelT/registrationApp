<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

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
        <title>Welcome</title>
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
                          action="/events/upcoming/${id}/new_attendee/manually" modelAttribute="attendee">
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
                                <p>
                                    <tr>
                                        <td>Event name   </td>
                                        <td>
                                            <div class="input-group mb-3">
                                              <select class="custom-select" id="inputGroupSelect02">
                                                <option selected>Choose...</option>
                                                  <c:forEach var="event" items="${upcoming_events}">
                                                        <option value="${event.getName()}">${event.getName()}</option>
                                                  </c:forEach>
                                              </select>
                                              <div class="input-group-append">
                                                <label class="input-group-text" for="inputGroupSelect02">Options</label>
                                              </div>
                                            </div>
                                        <td>
                                    </tr>
                                </p>
                                <p>
                                    <tr>
                                        <td><input type="submit" value="Submit"></input></td>
                                    </tr>
                                </p>
                            </table>
                        </form:form>

                        <form:form method="POST" action="" modelAttribute="form_content">
                          <p> Would you like to collect some information automatically? </p>
                              <table>
                                 <tr>
                                    <td><input type="submit" value="Lets do it!"/></td>
                                 </tr>
                                </table>
                        </form:form>
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
