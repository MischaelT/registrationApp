<html>
    <head>
        <link rel="stylesheet" href="../../../../webjars/bootstrap/4.0.0-2/css/bootstrap.min.css"/>
        <title> Add user </title>
    </head>
    <body>
        <%@ include file="includes/navbar.jsp"%>
        <main role="main" class="container" >
            <div align="center">
                <br>
                <br>
                <br>
                <h1> Add users automatically to the event with id ${id} </h1>
                <br>
                <br>
                <form:form method="POST" action="/events/upcoming/{id}/add_user/automatically">
                    <p> LinkedIn link <input type="text">  </input> </p>
                    <p><input type="submit" value="Submit"/></input></p>
                </form>
            <div>
        </main>
        <script src="../../../../webjars/jquery/3.0.0/jquery.min.js"></script>
        <script src="../../../../webjars/popper.js/1.12.9-1/umd/popper.min.js"></script>
        <script src="../../../../webjars/bootstrap/4.0.0-2/js/bootstrap.min.js"></script>
    </body>
</html>