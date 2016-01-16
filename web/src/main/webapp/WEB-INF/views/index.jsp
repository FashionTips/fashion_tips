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
    <script src="/scripts/lib/jquery-1.11.3.min.js"></script>
    <script src="/scripts/lib/bootstrap.min.js"></script>
    <script>$(function () {
        $('[data-toggle="tooltip"]').tooltip()  // initialize tooltips
    })</script>
    <script src="/scripts/lib/angular.min.js"></script>
    <script src="/scripts/lib/angular-route.min.js"></script>
    <script src="/scripts/lib/angular-messages.min.js"></script>
    <script src="/scripts/lib/angular-resource.min.js"></script>
    <script src="/scripts/lib/angular-cookies.min.js"></script>
    <script src="/scripts/lib/angular-sanitize.min.js"></script>

    <!-- Custom scripts-->
    <script src="/scripts/services/SessionService.js"></script>
    <script src="/scripts/services/AuthService.js"></script>
    <script src="/scripts/services/PostService.js"></script>
    <script src="/scripts/services/UserService.js"></script>
    <script src="/scripts/services/DictionaryService.js"></script>
    <script src="/scripts/services/TagService.js"></script>
    <script src="/scripts/controllers/MainController.js"></script>
    <script src="/scripts/controllers/ProfileController.js"></script>
    <script src="/scripts/controllers/PostController.js"></script>
    <script src="/scripts/controllers/MenuController.js"></script>
    <script src="/scripts/controllers/TagController.js"></script>
    <script src="/scripts/app.js"></script>
</head>
<body data-ng-app="fashion-tips-web" data-ng-strict-di>

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
                                <form name="signinForm" accept-charset="UTF-8" data-ng-submit="login()">
                                    <p class="text-danger" data-ng-show="showLoginErrorMessage">Invalid login or
                                        password!</p>
                                    <div class="form-group has-feedback"
                                         data-ng-class="{ 'has-error': signinForm.username.$invalid && signinForm.username.$dirty }">
                                        <input class="form-control" type="text" placeholder="Username" name="username"
                                               data-ng-model="credentials.username" required autofocus>
                                    </div>
                                    <div class="form-group has-feedback"
                                         data-ng-class="{ 'has-error': signinForm.password.$invalid && signinForm.password.$dirty }">
                                        <input type="password" class="form-control" placeholder="Password" id="password"
                                               name="password"
                                               data-ng-model="credentials.password" required>
                                    </div>
                                    <input class="btn btn-primary btn-block" type="submit" id="sign-in" value="Sign In"
                                    data-ng-disabled="signinForm.$invalid">
                                </form>
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