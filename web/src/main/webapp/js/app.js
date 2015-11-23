var app = angular.module('fashion-tips-web', ['ngRoute', 'ngMessages']);

/** App Configuration **/
app.config(['$routeProvider', '$httpProvider', function($routeProvider, $httpProvider) {

    $routeProvider.when("/", {
        templateUrl : "components/main/main.html",
        controller : MainController
    }).when("/profile", {
        templateUrl : "components/main/main.html",
        controller : MainController
    }).otherwise({
        redirectTo : "/"
    });

    /* Set to all request's headers, that they are ajax */
    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

}]);

/** Controllers **/
app.controller('MainController', MainController);