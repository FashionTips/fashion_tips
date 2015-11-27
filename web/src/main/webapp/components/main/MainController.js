var MainController = ['$rootScope', '$scope', '$http', '$location', '$localStorage',
    function ($rootScope, $scope, $http, $location, $localStorage) {

        $scope.login = function () {

            if(mockLogin) {
                $rootScope.authenticated = true;
                $rootScope.$storage.user = user;
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
            delete $rootScope.$storage.user;
            $location.path("/");
        };

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

        //authenticate();
        $scope.credentials = {};

        $rootScope.$storage = $localStorage.$default({
            user: null
        });

        $rootScope.authenticated = $rootScope.$storage.user != null;

    }];
