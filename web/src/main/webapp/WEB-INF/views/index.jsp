<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><tiles:getAsString name="title"/> | Fashion Tips</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width">
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="/css/custom.css">

    <!-- JS scripts here to hide angular code shows on preload -->
    <!-- lib's js -->
    <script src="/vendor/jquery-1.11.3.min.js"></script>
    <script src="/vendor/bootstrap.min.js"></script>
    <script>$(function () {
        $('[data-toggle="tooltip"]').tooltip()  // initialize tooltips
    })</script>
    <script src="/vendor/angular.min.js"></script>
    <script src="/vendor/ui-bootstrap-tpls-1.1.0.min.js"></script>
    <script src="/vendor/angular-messages.min.js"></script>
    <script src="/vendor/angular-resource.min.js"></script>
    <script src="/vendor/angular-cookies.min.js"></script>
    <script src="/vendor/angular-sanitize.min.js"></script>

    <!-- Custom scripts-->
    <script src="/ng/common/validation/validation.js"></script>
    <script src="/ng/common/dictionary/dictionary.js"></script>
    <script src="/ng/common/security/session.js"></script>
    <script src="/ng/common/security/auth.js"></script>
    <script src="/ng/common/security/security.js"></script>
    <script src="/ng/app/posts/comments/comments.js"></script>
    <script src="/ng/app/posts/tags/tags.js"></script>
    <script src="/ng/app/posts/posts.js"></script>
    <script src="/ng/app/home/home.js"></script>
    <script src="/ng/app/login/login.js"></script>
    <script src="/ng/app/register/register.js"></script>
    <script src="/ng/app/users/users.js"></script>
    <script src="/ng/app/app.js"></script>
</head>
<body data-ng-app="ft" >

<header>
    <div class="container">
        <nav class="navbar navbar-default">
            <div class="container-fluid">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                            data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="/">Fashion Tips</a>
                </div>

                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1"
                     data-ng-controller="MenuController">
                    <ul class="nav navbar-nav navbar-right">
                        <li data-ng-show="loggedIn()">
                            <a href="/post" data-toggle="tooltip" data-placement="bottom" title="New Post">
                                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                            </a>
                        </li>
                        <!--Dropdown menu for authenticated users.-->
                        <li class="dropdown" data-ng-show="loggedIn()">
                            <a class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                               aria-expanded="false">{{ username }} <span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li>
                                    <div class="media">
                                        <div class="media-left">
                                            <img class="media-object" height="64px" data-ng-src="{{ avatarUrl || 'http://placehold.it/64x64' }}"
                                                 alt="Avatar {{ username }}">
                                        </div>
                                        <div class="media-body">
                                            <h4 class="media-heading">{{ username }}</h4>
                                            <a href="/user/{{ username }}" target="_self">Profile</a>
                                        </div>
                                    </div>
                                </li>
                                <li role="separator" class="divider"></li>
                                <li><a href="/logout" class="navbar-link" data-ng-click="logout()">Logout</a></li>
                            </ul>
                        </li>
                        <!--Dropdown menu for non-authenticated users.-->
                        <li id="login-li" class="dropdown" data-ng-show="!loggedIn()">
                            <a id="login-form-toggle" href="/login" class="dropdown-toggle" data-toggle="dropdown">Log
                                In
                            </a>
                            <div class="dropdown-menu">
                                <ft-login-form data-to-home="false"></ft-login-form>
                            </div>
                        </li>
                        <li data-ng-show="!loggedIn()">
                            <p class="navbar-btn">
                                <a href="/register" class="btn btn-default">Join</a>
                            </p>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    </div>
</header>

<tiles:insertAttribute name="body"/>

<footer>
    <div class="container">
        <hr/>
        <p class="text-center">Fashion Tips &copy; 2015 - 2016</p>
    </div>
</footer>
</body>
</html>