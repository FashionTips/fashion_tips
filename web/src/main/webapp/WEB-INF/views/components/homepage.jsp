<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container">
    <div class="jumbotron">
        <h1>Welcome to World of Fashion!</h1>
    </div>
</div>
<div class="container" data-ng-controller="HomeController">
    <div class="row">
        <div class="col-md-8">
            <ul class="list-group" data-ng-repeat="post in posts">
                <ft-post data-post="post" data-feed="true"></ft-post>
            </ul>
        </div>
    </div>
</div>