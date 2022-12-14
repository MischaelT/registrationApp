<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
    <head>
        <link rel="stylesheet" href="../../../../webjars/bootstrap/4.0.0-2/css/bootstrap.min.css"/>
        <title>Change data</title>
    </head>
    <body>
        <%@ include file="../includes/navbar.jsp"%>
        <main role="main" class="container" >
            <div align="center">
                    <br>
                    <br>
                    <br>
                    <br>
                    <br>
                    <h1> Change attendee data<h1>
                    <br>
                    <form:form method="POST"
                      action="/attendees/attendee/${id}/change_info" modelAttribute="attendee">
                         <table>
                            <tr>
                                <td><form:label path="name">Name</form:label></td>
                                <td><form:input path="name" value="${attendee.getName()}"/></td>
                            </tr>
                            <tr>
                                <td><form:label path="linkedInLink">Link</form:label></td>
                                <td><form:input path="linkedInLink" type="text" value="${attendee.getLinkedInLink()}"/></td>
                            </tr>
                            <tr>
                                <td><input type="submit" value="Submit"/></td>
                            </tr>
                        </table>
                    </form:form>

                    <form:form method="POST" action="/attendees/attendee/${id}/change_info/automatically" modelAttribute="form_content">
                      <p> Would you like to collect some information <a href="/attendees/attendee/${id}/change_info">automatically</a>? </p>
                          <table>
                             <tr>
                                <td><input type="submit" value="Lets do it!"/></td>
                             </tr>
                            </table>
                    </form:form>
            <div>
        </main>
        <script src="../../../../webjars/jquery/3.0.0/jquery.min.js"></script>
        <script src="../../../../webjars/popper.js/1.12.9-1/umd/popper.min.js"></script>
        <script src="../../../../webjars/bootstrap/4.0.0-2/js/bootstrap.min.js"></script>
    </body>
</html>