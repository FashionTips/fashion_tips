var MenuController = ['$scope', '$location', 'sessionService', 'authService',
    function ($scope, $location, sessionService, authService) {

        /* Define variable for credential object which will be filled from inputs in Login form */
        $scope.credentials = {};

        $scope.error = false;

        /* authorised user's name */
        $scope.username = sessionService.getUsername();

        /* is user logged in */
        $scope.loggedIn = function () {
            return sessionService.getToken() !== undefined;
        };

        /* Define function which will grab credentials, if exist, and try to authenticate on server */
        $scope.login = function () {

            var login = authService.login($scope.credentials.username, $scope.credentials.password);

            login.then(function () {
                $scope.error = false;
                $scope.credentials = {};
                $scope.$watch(function () {
                    $scope.username = sessionService.getUsername();
                });
                $location.path("/profile");
            }, function () {
                $scope.error = true;
            });
        };

        /* Define function for logout action */
        $scope.logout = function () {
            authService.logout()
                .then(function () {
                    $location.path("/")
                });
        };
    }];