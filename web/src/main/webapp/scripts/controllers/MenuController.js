var MenuController = ['$scope', 'sessionService', 'authService',
    function ($scope, sessionService, authService) {

        /* Define variable for credential object which will be filled from inputs in Login and Register form */
        $scope.credentials = {};

        $scope.showLoginErrorMessage = false;
        $scope.showRegisterErrorMessage = false;

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

        /**
         * Process user's logout.
         */
        $scope.logout = function () {
            authService.logout()
                .then(function () {
                    window.location.href = "/";
                });
        };

        /**
         * Process user's registration in the system.
         */
        $scope.register = function () {

            if(!$scope.credentials.username || !$scope.credentials.email || !$scope.credentials.password) {
                return;
            }

            authService.register($scope.credentials.username, $scope.credentials.email, $scope.credentials.password)
                .then(function () {
                    $scope.showRegisterErrorMessage = false;
                    angular.element("#successRegistrationModal").modal();
                    $scope.credentials = {};
            }, function (data) {
                    $scope.showRegisterErrorMessage = true;
                    $scope.registerFormValidationErrors = data.message;
            });
        };


        $scope.isAvailable = function () {
            return true;
        };

        /**
         * Redirects user to homepage, after that pop-up window about success registration has been dismissed.
         */
        $scope.processSuccessRegistration = function () {
            window.location.href = "/";
        };
    }];