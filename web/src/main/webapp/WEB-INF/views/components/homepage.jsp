<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container">
    <div class="jumbotron">
        <h1>Welcome to World of Fashion!</h1>
    </div>
</div>
<div class="container" data-ng-controller="MainController">
    <div class="row">
        <div class="col-md-8">
            <ul class="list-group" data-ng-repeat="post in posts">
                <li class="list-group-item">
                    <data-ft-post></data-ft-post>
                </li>
            </ul>
        </div>
    </div>
</div>