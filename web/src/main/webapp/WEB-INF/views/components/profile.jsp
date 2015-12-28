<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container" data-ng-controller="ProfileController">
    <div class="row">
        <div class="col-md-8">
            <div class="row">
                <div class="col-md-4">
                    <img data-ng-src="{{ user.avatar.imgUrl || 'http://placehold.it/200x200' }}" class="img-responsive"
                         alt="Responsive image">
                </div>
                <div class="col-md-8">
                    <div class="page-header">
                        <h2>{{ user.firstName }} {{ user.lastName }}</h2>
                        <p>by {{ user.login }}, <span data-ng-hide="user.birthday === null || user.hideAge">{{ age() }} year old</span>
                            {{ user.gender }} from <span data-ng-show="user.location">{{ user.location}},</span>
                            <span data-ng-show="user.country">{{ user.country.name }}</span>.</p>
                    </div>
                    <a href="/user/edit" data-ng-show="user.login === username()" class="btn btn-default"><span
                            class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Edit profile</a>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="panel panel-info">
                <div class="panel-heading">
                    <h3 class="panel-title text-center">About me</h3>
                </div>
                <div class="panel-body">
                    {{ user.aboutMe }}
                </div>
                <div class="panel-footer">
                    <table class="table">
                        <tbody>
                        <tr>
                            <th>FT</th>
                            <td><a href="/user/{{ user.id }}">http://localhost:8081/user/{{ user.id }}</a></td>
                        </tr>
                        <tr>
                            <th>Blog</th>
                            <td><a href="{{ user.blogUrl }}">{{ user.blogUrl }}</a></td>
                        </tr>
                        <tr>
                            <th>Instagram</th>
                            <td><a href="https://www.instagram.com/{{ user.instagram }}">{{ user.instagram }}</a></td>
                        </tr>
                        <tr>
                            <th>Site</th>
                            <td><a href="{{ user.websiteUrl }}">{{ user.websiteUrl }}</a></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <ul class="nav nav-tabs">
        <li class="active"><a href="#">Recent Posts</a></li>
    </ul>
    <div class="list-group">
        <a href="/post/{{post.id}}" class="list-group-item" data-ng-repeat="post in userPosts">{{post.title}} - {{post.created | date:"dd MMM yyyy 'at' hh:mm"}}</a>
    </div>
</div>