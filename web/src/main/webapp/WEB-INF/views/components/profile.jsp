<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container" data-ng-controller="ProfileController">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="jumbotron">
                <h1>Hello, {{ user.login }}!</h1>

                <p>Email: {{ user.email }}</p>

                <p><a class="btn btn-primary btn-lg" href="/post" target="_self" role="button">Add post</a></p>
            </div>

            <div class="page-header">
                <h2>{{ posts.length == 0 ? "You have no posts ;(" : "Recent posts"}}</h2>
            </div>

            <div data-ng-repeat="post in posts">
                <data-ft-post></data-ft-post>
            </div>
        </div>
    </div>
</div>