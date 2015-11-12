<%@ page import="com.bionic.edu.model.Request" %>
<%@ page import="com.bionic.edu.util.TimeUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="container">
    <h2>Дайте свой совет на следующие запросы:</h2>
    <table class="table table-striped">
        <thead>
            <tr>
                <th>Имя</th>
                <th>Дата</th>
                <th>Описание</th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${requests}" var="request">
                <tr>
                    <td><c:out value="${request.user.name}"/></td>
                    <td><%= ((Request)pageContext.getAttribute("request")).getDateTime().format(TimeUtil.DATE_TIME_FORMATTER)%></td>
                    <td><c:out value="${request.description}"/></td>
                    <td><a href="/requests/${request.id}"><button type="button" class="btn btn-primary">Открыть</button></a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<jsp:include page="fragments/footer.jsp"/>
</body>
</html>