<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
    <head>
        <link rel="stylesheet" href="../../../../webjars/bootstrap/4.0.0-2/css/bootstrap.min.css"/>
        <title> Add user </title>
    </head>
    <body>
        <%@ include file="../includes/navbar.jsp"%>
        <main role="main" class="container" >
            <div align="center">
                <br>
                <br>
                <br>
                <h1> Add users automatically to the event with id ${id} </h1>
                <br>
                <br>
                <form:form method="POST" action="/events/upcoming/${id}/new_attendee/automatically"
                 modelAttribute="form_content">
                  <table>
                     <tr>
                        <td> <form:label path="content"> LinkedIn link </form:label> </td>
                        <td> <form:input path="content" type="text"/> </td>
                     </tr>
                     <tr>
                        <td><input type="submit" value="Submit"/></td>
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