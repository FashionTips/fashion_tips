<%@ page import="com.bionic.edu.util.TimeUtil" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.bionic.edu.model.Request" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="dandelion" uri="http://github.com/dandelion" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="container">
    <div class="jumbotron">
        <div class="row bottomline">
            <h2 class="col-sm-6"><c:out value="${request.user.name}"/></h2>
            <h4 class="col-sm-6 text-right pull-down" ><%= ((Request)request.getAttribute("request")).getDateTime().format(TimeUtil.DATE_TIME_FORMATTER)%></h4>
        </div>
        <div class="col-sm-6" style="margin-top: 20px">
            <span><c:out value="${request.description}"/></span>
        </div>

    </div>
</div>
    <%--<c:out value="${request}"/>--%>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
