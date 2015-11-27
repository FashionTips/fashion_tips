var MainController = ['$rootScope', '$scope', '$http', '$location', function ($rootScope, $scope, $http, $location) {

    /* Define function which will grab credentials, if exist, and try to authenticate on server */
    var authenticate = function (credentials, callback) {

        var headers = credentials ? {
            authorization: "Basic "
            + btoa(credentials.username + ":" + credentials.password)
        } : {};

        $http.get("http://localhost:8080/me", {headers: headers}).success(function (data) {
            $rootScope.authenticated = data;
            callback && callback();
        }).error(function () {
            $rootScope.authenticated = false;
            callback && callback();
        });

    };

    /* Try authenticate and get auth principals while controller initializing */
    authenticate();

    /* Define variable for credential object which will be filled from inputs in Login form */
    $scope.credentials = {};

    /* Define variable for user object which will be loaded in login() function */
    $scope.user = {};

    /* Define function for login action through login form */
    $scope.login = function () {
        authenticate($scope.credentials, function () {
            if ($rootScope.authenticated) {
                $location.path("/profile");
                $http.get("http://localhost:8080/users/by?login="+$rootScope.authenticated).success(function(data) {
                    $scope.user = data;
                });
                $scope.error = false;
            } else {
                $location.path("/");
                $scope.error = true;
            }
        });
    };

    /* Define function for logout action */
    $scope.logout = function () {
        $http.post("http://localhost:8080/logout", {}).error(function() {
            /* Error will be returned in any case due to realization Spring Web CORS + Spring Security */
            $rootScope.authenticated = false;
            $location.path("/");
        });
    };

    $scope.clickMe = function () {
        $http.get("http://localhost:8080/users");
    };

}];
