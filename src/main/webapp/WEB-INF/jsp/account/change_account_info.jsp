<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <title>Change info</title>
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
                            <br>
                            <h1>change info</h1>
                            <br>
                            <form:form method="POST"
                              action="/account/settings" modelAttribute="user">
                                 <table>
                                    <tr>
                                        <td><form:label path="name">Name</form:label></td>
                                        <td><form:input path="name" value="${user.getName()}"/></td>
                                    </tr>
                                    <tr>
                                        <td><form:label path="linkedInLink">Login</form:label></td>
                                        <td><form:input path="linkedInLink" type="text" value="${user.getLinkedInLink()}"/></td>
                                    </tr>
                                    <tr>
                                        <td><form:label path="linkedPassword">Password</form:label></td>
                                        <td><form:input path="linkedPassword" type="text" value="${user.getLinkedPassword()}"/></td>
                                    </tr>
                                    <tr>
                                        <td><input type="submit" value="Submit"/></td>
                                    </tr>
                                </table>
                            </form:form>

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