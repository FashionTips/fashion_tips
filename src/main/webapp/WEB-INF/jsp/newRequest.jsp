<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>

<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <h2>
                Спросить совет у участников Fashion Tips
            </h2>

            <div class="view-box">
                <form:form modelAttribute="newRequest" class="form-horizontal" method="post"
                           action="/requests" charset="utf-8"
                           accept-charset="UTF-8" id="form">

                    <form:input path="id" type="hidden"/>
                    <div class="form-group">
                        <label class="control-label col-sm-3">Ссылка на картинку:</label>
                        <div class="col-sm-7">
                            <form:input type="text" class="form-control" path="picUrl"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">Описание:</label>
                        <div class="col-sm-7">
                            <form:textarea path="description" cssclass="form-control" cols="73" rows="5"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button type="submit" class="btn btn-primary">Отправить</button>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>


<jsp:include page="fragments/footer.jsp"/>
</body>
</html>