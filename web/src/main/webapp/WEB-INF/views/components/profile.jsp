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
                <span class="label label-primary">{{ post.category }}</span>
                <div class="panel panel-default post-menu">
                    <div class="btn-group pull-right">
                        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"
                                aria-haspopup="true"
                                aria-expanded="false">
                            <span class="caret"></span>
                            <span class="sr-only">Toggle Dropdown</span>
                        </button>
                        <ul class="dropdown-menu">
                            <li><a href="/post/{{ post.id }}">To post</a></li>
                            <%--<li role="separator" class="divider"></li>--%>
                            <%--<li><a href="#">Delete</a></li>--%>
                        </ul>
                    </div>
                    <div class="panel-heading">
                        <h3 class="panel-title">{{ post.title }}</h3>
                    </div>
                    <div class="panel-body">
                        <p>{{ post.textMessage }}</p>
                        <a href="#" class="thumbnail" data-ng-repeat="image in post.images">
                            <img data-ng-src="{{ image.imgUrl }}" /></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>