/* MODULE */
var app = angular.module('fashion-tips-web', ['ngRoute', 'ngMessages', 'ngResource', 'ngCookies', 'ngSanitize']);

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
app.service('userService', userService);
app.service('dictionaryService', dictionaryService);

/* Controllers */
app.controller('MenuController', MenuController);
app.controller('MainController', MainController);
app.controller('PostController', PostController);
app.controller('ProfileController', ProfileController);

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

/* Directives */
app.directive('ftPost', [function () {
    return {
        restrict: 'E',
        templateUrl: '/scripts/directives/post.html'
    };
}]);

/**
 * Directive for custom validation - check whether the given login is unique
 */
app.directive('username', ['$q', '$http', function($q, $http) {
    return {
        require: 'ngModel',
        link: function(scope, elm, attrs, ctrl) {
            ctrl.$asyncValidators.username = function (modelValue, viewValue) {

                if(ctrl.$isEmpty(modelValue)) {
                    // consider empty model valid
                    return $q.when();
                }

                var def = $q.defer();

                $http.get(urlApi + "/users/available", {params: {login: viewValue}})
                    .then(function (response) {
                        if(response.data) {
                            def.resolve();
                        } else {
                            def.reject();
                        }
                    });

                return def.promise;
            };
        }
    };
}]);

/**
 * Directive for custom validation - check whether the given email is unique
 */
app.directive('uniqueEmail', ['$q', '$http', function($q, $http) {
    return {
        require: 'ngModel',
        link: function(scope, elm, attrs, ctrl) {
            ctrl.$asyncValidators.uniqueEmail = function (modelValue, viewValue) {

                if(ctrl.$isEmpty(modelValue)) {
                    // consider empty model valid
                    return $q.when();
                }

                var def = $q.defer();

                $http.get(urlApi + "/users/available", {params: {email: viewValue}})
                    .then(function (response) {
                        if(response.data) {
                            def.resolve();
                        } else {
                            def.reject();
                        }
                    });

                return def.promise;
            };
        }
    };
}]);

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