var MenuController = ['$rootScope', '$scope', '$http', '$location', '$localStorage',
    function ($rootScope, $scope, $http, $location, $localStorage) {

        /* Define function which will grab credentials, if exist, and try to authenticate on server */
        var authenticate = function (credentials, callback) {
            var headers = credentials ? {
                authorization: "Basic "
                + btoa(credentials.username + ":" + credentials.password)
            } : {};

            $http.get(urlApi + "/me", {headers: headers}).
            success(function (data) {
                $rootScope.authenticated = true;
                callback && callback(data);
            }).
            error(function () {
                $rootScope.authenticated = false;
                callback && callback();
            });

        };

        /* Define function for login action through login form */
        $scope.login = function () {
            authenticate($scope.credentials, function (login) {
                if ($rootScope.authenticated) {
                    $location.path("/profile");
                    $http.get(urlApi + "/users/by?login=" + login).
                    success(function (data) {
                        $rootScope.$storage.user = data;
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
            /* Error will be returned in any case due to realization Spring Web CORS + Spring Security */
            $http.post(urlApi + "/logout", {}).error(function () {
                $rootScope.authenticated = false;
                delete $rootScope.$storage.user;
                $location.path("/");
            });
        };

        /* Try authenticate and get auth principals while controller initializing */
        authenticate();

        /* Define variable for credential object which will be filled from inputs in Login form */
        $scope.credentials = {};

        /* Define variable for user object which will be loaded in login() function */
        $rootScope.$storage = $localStorage.$default({
            user: null
        });

        $rootScope.authenticated = $rootScope.$storage.user != null;
    }];