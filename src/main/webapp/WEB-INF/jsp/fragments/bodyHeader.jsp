<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <a href="/requests" class="navbar-brand">Список запросов</a>

        <div class="collapse navbar-collapse">
            <c:url value="/logout" var="logout"/>
            <form:form class="navbar-form navbar-right" action="${logout}" method="post">
                <sec:authorize access="isAuthenticated()">
                    <a class="btn btn-warning" role="button" href="/requests/new">Спросить совет</a>
                    <a class="btn btn-info" role="button" href="<c:url value='/profile'/>">${user.name} профиль</a>
                    <input type="submit" class="btn btn-primary" value="Logout">
                </sec:authorize>
            </form:form>
        </div>
    </div>
</div>
