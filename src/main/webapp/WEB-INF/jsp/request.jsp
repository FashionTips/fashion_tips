<%@ page import="com.bionic.edu.util.TimeUtil" %>
<%@ page import="com.bionic.edu.model.Request" %>
<%@ page import="com.bionic.edu.model.Comment" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="dandelion" uri="http://github.com/dandelion" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="container">
    <div class="row">
        <h4 class="col-sm-6"><c:out value="${request.user.name}"/></h4>
        <h4 class="col-sm-6 text-right"><%= ((Request)request.getAttribute("request")).getDateTime().format(TimeUtil.DATE_TIME_FORMATTER)%></h4>
    </div>
    <div class="well">
        <p><c:out value="${request.description}"/></p>
        <img class="img-responsive" src="${request.picUrl}" width="400"/>
    </div>
    <h3>Комментарии:</h3>
    <c:forEach items="${comments}" var="comment">
        <c:set var="dateTime" value="${comment.dateTime}"/>
        <p><span class="bg-info">(<%=
        ((Comment)pageContext.getAttribute("comment")).getDateTime().format(TimeUtil.DATE_TIME_FORMATTER)
        %>)&nbsp<c:out value="${comment.user.name}"/></span>:&nbsp<c:out value="${comment.commentText}"/></p>
    </c:forEach>
    <p>
        <form:form modelAttribute="newComment" method="post" action="${request.id}/comments" acceptcharset="UTF-8" charset="utf-8">
            <div class="input-group">
                <form:input path="id" type="hidden"/>
                <form:input type="text" class="form-control" path="commentText"/>
                <span class="input-group-btn">
                    <button type="submit" class="btn btn-primary">Добавить комментарий</button>
                </span>
            </div>
        </form:form>
    </p>
</div>

<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
