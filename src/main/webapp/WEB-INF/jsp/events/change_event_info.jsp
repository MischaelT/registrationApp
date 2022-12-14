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
                    <h1> Change event data<h1>
                    <br>
                    <form:form method="POST"
                      action="/events/upcoming/${id}/change_data" modelAttribute="event">
                         <table>
                            <tr>
                                <td><form:label path="name">Name</form:label></td>
                                <td><form:input path="name" value="${event.getName()}"/></td>
                            </tr>
                            <tr>
                                <td><form:label path="date">Date</form:label></td>
                                <td><form:input path="date" value="${event.getDate()}"/></td>
                            </tr>
                            <tr>
                                <td><form:label path="linkedInLink">linkedInLink</form:label></td>
                                <td><form:input path="linkedInLink" type="text" value="${event.getLinkedInLink()}"/></td>
                            </tr>
                            <tr>
                                <td><form:label path="isPassed">IsPassed</form:label></td>
                                <td><form:input path="isPassed" type="text" value="${event.getIsPassed()}"/></td>
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