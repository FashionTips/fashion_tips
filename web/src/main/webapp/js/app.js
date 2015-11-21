var app = angular.module('fashion-tips-web', ['ngRoute', 'ngMessages']);

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

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

}]);

/** Controllers **/
app.controller('MainController', MainController);