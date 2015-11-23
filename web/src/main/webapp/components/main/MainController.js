var MainController = ['$rootScope', '$scope', '$http', '$location', function ($rootScope, $scope, $http, $location) {

    var authenticate = function (credentials, callback) {

        var headers = credentials ? {
            authorization: "Basic "
            + btoa(credentials.username + ":" + credentials.password)
        } : {};

        $http.get("http://localhost:8080/users/1", {headers: headers}).success(function (data) {
            $rootScope.authenticated = data.login;
            callback && callback();
        }).error(function () {
            $rootScope.authenticated = false;
            callback && callback();
        });

    };

    authenticate();
    $scope.credentials = {};
    $scope.login = function () {
        authenticate($scope.credentials, function () {
            if ($rootScope.authenticated) {
                $location.path("/profile");
                $scope.error = false;
            } else {
                $location.path("/");
                $scope.error = true;
            }
        });
    };

    $scope.logout = function () {
        $rootScope.authenticated = false;
        $location.path("/");
    };

    $scope.clickMe = function () {
        $http.get("http://localhost:8080/users");
    };

}];
