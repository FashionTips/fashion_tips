/* MODULE */
var app = angular.module('fashion-tips-web', ['ngRoute', 'ngMessages', 'ngResource']);

/* App constants */
var mockLogin = false;
var user = {name: 'John', username: 'john_doe', email: 'john_doe@gamil.com'};

/* URLs */
var urlApi = "http://localhost:8080";
var urlLogin = urlApi + "/users/1";
var urlPost = urlApi + "/users/1/posts";

/** App Configuration **/
app.config(['$routeProvider', '$httpProvider',
    function ($routeProvider, $httpProvider) {

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
                redirectTo: "/login"
            });

        /* Set to all request's headers, that they are ajax */
        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

    }]);

/** Controllers **/
app.controller('MainController', MainController);
app.controller('ProfileController', ProfileController);
app.controller('PostController', PostController);