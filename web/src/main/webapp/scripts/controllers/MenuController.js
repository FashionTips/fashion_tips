var MenuController = ['$scope', 'sessionService', 'authService',
    function ($scope, sessionService, authService) {

        /* Define variable for credential object which will be filled from inputs in Login and Register form */
        $scope.credentials = {};

        $scope.showLoginErrorMessage = false;
        $scope.showRegisterErrorMessage = false;
        $scope.showRegisterSuccessMessage = false;

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
                $scope.showLoginErrorMessage = false;
                $scope.credentials = {};
                $scope.$watch(function () {
                    $scope.username = sessionService.getUsername();
                });
                window.location.href = "/profile";
            }, function () {
                $scope.showLoginErrorMessage = true;
            });
        };

        /* Define function for logout action */
        $scope.logout = function () {
            authService.logout()
                .then(function () {
                    window.location.href = "/";
                });
        };

        $scope.register = function () {

            if(!$scope.credentials.username || !$scope.credentials.email || !$scope.credentials.password) {
                return;
            }

            authService.register($scope.credentials.username, $scope.credentials.email, $scope.credentials.password)
                .then(function () {
                    $scope.showRegisterErrorMessage = false;
                    $scope.showRegisterSuccessMessage = true;
                    $scope.credentials = {};
            }, function (data) {
                    $scope.showRegisterSuccessMessage = false;
                    $scope.showRegisterErrorMessage = true;
                    $scope.registerFormValidationErrors = data.message;
            });
        };

        $scope.isAvailable = function () {
            return true;
        };
    }];