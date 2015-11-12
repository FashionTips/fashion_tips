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
                ${register ? 'Зарегистрировать нового пользователя' : user.name.concat(' профиль')}
            </h2>

            <div class="view-box">
                <form:form modelAttribute="user" class="form-horizontal" method="post"
                           action="${register ? 'register' : 'profile'}" charset="utf-8"
                           accept-charset="UTF-8">

                    <form:input path="id" type="hidden"/>
                    <div class="form-group">
                        <label class="control-label col-sm-2">Имя:</label>
                        <div class="col-sm-8">
                            <form:input type="text" class="form-control" path="name"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2">Email:</label>
                        <div class="col-sm-8">
                            <form:input type="text" class="form-control" path="email"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2">Пароль:</label>
                        <div class="col-sm-8">
                            <form:input type="password" class="form-control" path="password"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button type="submit" class="btn btn-primary">${register ? 'Зарегистрировать' : 'Обновить'}</button>
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