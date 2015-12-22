<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container" data-ng-controller="ProfileController">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="jumbotron">
                <h1>Hello, {{ user.login }}!</h1>
            </div>

            <div class="page-header">
                <h2>{{ posts.length === 0 ? "You have no posts ;(" : "Recent posts"}}</h2>
            </div>

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
    </div>
</div>