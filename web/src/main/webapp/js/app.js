/* MODULE */
var app = angular.module('fashion-tips-web', ['ngRoute', 'ngMessages', 'ngResource', 'ngStorage']);

/* App constants */
var urlApi = "http://localhost:8080"; // URL

/* App Configuration */
app.config(['$routeProvider', '$httpProvider', '$locationProvider',
    function ($routeProvider, $httpProvider, $locationProvider) {

        $routeProvider
            .when("/", {
                templateUrl: "components/main/main.html",
                controller: 'MainController'
            })
            .when("/login", {
                templateUrl: "components/main/main.html",
                controller: 'MainController'
            })
            .when("/profile", {
                templateUrl: "components/profile/profile.html",
                controller: 'ProfileController'
            })
            .when("/profile/post", {
                templateUrl: "components/post/addPost.html",
                controller: "PostController"
            })
            .otherwise({
                redirectTo: "/"
            });

        /* Set to all request's headers, that they are ajax */
        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
        /* Sending auth credentials and cookies with CORS requests */
        $httpProvider.defaults.withCredentials = true;
    }]);

/* Controllers */
app.controller('MainController', MainController);
app.controller('ProfileController', ProfileController);
app.controller('PostController', PostController);