/* MODULE */
var app = angular.module('ft', [
    'ngSanitize',
    'ft.security',
    'ft.home',
    'ft.login',
    'ft.register',
    'ft.users',
    'ft.posts',
    'ft.posts.tags',
    'ft.validation'
]);



/* App constants */
var urlApi = "http://localhost:8080"; // URL



/* App Configuration */
app.config(['$httpProvider',
    function ($httpProvider) {

        /* Set to all request's headers, that they are ajax */
        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
        /* Sending auth credentials and cookies with CORS requests */
        $httpProvider.defaults.withCredentials = true;
    }]);



app.controller('MenuController', ['$scope', 'sessionService', 'authService', 'userService',
    function ($scope, sessionService, authService, userService) {

        /* authorised user's name */
        $scope.username = sessionService.getUsername();

        $scope.$watch(function () {
            return sessionService.getUsername();
        }, function (value) {
            $scope.username = value;
        });

        /* is user logged in */
        $scope.loggedIn = function () {
            return sessionService.getToken() !== undefined;
        };


        /**
         * Process user's logout.
         */
        $scope.logout = function () {
            authService.logout()
                .then(function () {
                    window.location.href = "/";
                });
        };

        if($scope.loggedIn()) {
            userService.getByUsername($scope.username, function(response) {
                $scope.avatarUrl = response.avatar ? response.avatar.imgUrl : null;
            })
        }
    }]);



/* Filters */
app.filter("hashtag", [function () {
    return function (text) {

        if (!text) {
            return;
        }

        return text.replace(/(\B|^)#\w+/g, function (tag) {
            return '<a href="/?q=' + tag.replace('#', '%23') + '">' + tag + '</a>';
        });
    };
}]);

/* Directive for input file object */
/* It launches custom function after element changing */
app.directive('customOnChange', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            var onChangeHandler = scope.$eval(attrs.customOnChange);
            element.bind('change', onChangeHandler);
        }
    };
}]);