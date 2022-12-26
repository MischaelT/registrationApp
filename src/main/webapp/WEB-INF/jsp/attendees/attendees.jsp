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
        <title>Attendees</title>
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
                        <br>
                        <p><input type="text" placeholder="Search by attendee..."> <button type="button">submit</button></p>
                        <br>
                        <br>
                            <table class="table table-hover" >
                              <thead>
                                <tr>
                                  <th  style="width:20%" scope="col">Name</th>
                                  <th  style="width:20%" scope="col">Linked</th>
                                </tr>
                              </thead>
                              <tbody>
                                <c:forEach var="attendee" items="${attendees}">
                                    <tr>
                                      <td><a href="../../attendees/attendee/${attendee.getId()}">${attendee.getName()}</a></td>
                                      <td><a href="${attendee.getLinkedInLink()}">@linked</a></td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                              </table>
                          <p>Would you like to add more? </p>
                          <p> <a href="/attendees/new_attendee/manually"> Add manually </a> </p>
                          <p> <a href="/attendees/new_attendee/automatically"> Add automatically </a> </p>
                    <div>
                </main>
                <%@ include file="../includes/footer.jsp"%>
            <div>
        <div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="../scripts/scripts.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/simple-datatables@latest" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
    </body>
</html>