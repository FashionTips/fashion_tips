<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="dandelion" uri="http://github.com/dandelion" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<dandelion:asset cssExcludes="jumbotron_narrowCSS"/>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header navbar-brand">Fashion Tips</div>
        <div class="navbar-collapse collapse">
            <form:form class="navbar-form navbar-right" role="form" action="spring_security_check" method="post">
                <div class="form-group">
                    <input type="text" placeholder="Email" class="form-control" name='username'>
                </div>
                <div class="form-group">
                    <input type="password" placeholder="Password" class="form-control" name='password'>
                </div>
                <button type="submit" class="btn btn-success">Войти</button>
            </form:form>
        </div>
    </div>
</nav>
<div class="jumbotron">
    <div class="container">
        <p></p>
        <img src="http://savepic.su/6451085.png" class="img-responsive" alt="Fashion Tips"/>
    </div>
</div>

<jsp:include page="fragments/footer.jsp"/>
</body>
</html>