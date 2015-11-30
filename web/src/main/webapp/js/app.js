/* MODULE */
var app = angular.module('fashion-tips-web', ['ngRoute', 'ngMessages', 'ngResource', 'ngStorage']);

/* App constants */
var urlApi = "http://localhost:8080"; // URL

/* App Configuration */
app.config(['$routeProvider', '$httpProvider',
    function ($routeProvider, $httpProvider) {

        $routeProvider
            .when("/", {
                templateUrl: "components/main/main.html",
                controller: 'MainController'
            })
            .when("/profile", {
                templateUrl: "components/profile/profile.html",
                controller: 'ProfileController'
            })
            .when("/profile/post", {
                templateUrl: "components/post/addPost.html",
                controller: 'PostController'
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
app.controller('MenuController', MenuController);
app.controller('MainController', MainController);
app.controller('PostController', PostController);
app.controller('ProfileController', ProfileController);

app.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;

            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);

app.service('fileUpload', ['$http', function ($http) {
    this.uploadFileToUrl = function(file, uploadUrl){
        var fd = new FormData();
        fd.append('file', file);

        $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined},
        })

            .success(function(){
            })

            .error(function(){
            });
    }
}]);