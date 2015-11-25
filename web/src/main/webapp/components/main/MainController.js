var MainController = ['$rootScope', '$scope', '$http', '$location', '$location',
    function ($rootScope, $scope, $http, $location) {

        /* if this is login page - show login dropdown form */
        $scope.$watch(function () {
            $scope.showLogin = $location.url() === '/login';
        });

        var authenticate = function (credentials, callback) {

            var headers = credentials ? {
                authorization: "Basic "
                + btoa(credentials.username + ":" + credentials.password)
            } : {};

            $http.get(urlLogin, {headers: headers}).success(function (data) {
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

            if(mockLogin) {
                $rootScope.authenticated = true;
                $rootScope.user = user;
                $location.path("/profile");
                return;
            }

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
    }];
