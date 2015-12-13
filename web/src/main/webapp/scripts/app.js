/* MODULE */
var app = angular.module('fashion-tips-web', ['ngRoute', 'ngMessages', 'ngResource', 'ngCookies']);

/* App constants */
var urlApi = "http://localhost:8080"; // URL

/* App Configuration */
app.config(['$routeProvider', '$httpProvider',
    function ($routeProvider, $httpProvider) {

        /* Set to all request's headers, that they are ajax */
        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
        /* Sending auth credentials and cookies with CORS requests */
        $httpProvider.defaults.withCredentials = true;
    }]);

/* Services */
app.service('sessionService', sessionService);
app.service('authService', authService);
app.service('postService', postService);

/* Controllers */
app.controller('MenuController', MenuController);
app.controller('MainController', MainController);
app.controller('PostController', PostController);
app.controller('ProfileController', ProfileController);

/* Directives */

/* Directive for input file object */
/* It maps chosen file to angular model object */
app.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;

            element.bind('change', function () {
                scope.$apply(function () {
                    modelSetter(scope, element[0].files);
                });
            });
        }
    };
}]);
