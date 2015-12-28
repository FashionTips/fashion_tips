var MenuController = ['$scope', 'sessionService', 'authService', '$location', 'userService',
    function ($scope, sessionService, authService, $location, userService) {

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

                /* if user is on register or login page - redirect him */
                if (($location.absUrl().indexOf('/login') > -1 || $location.absUrl().indexOf('/register') > -1)
                    && $location.absUrl().indexOf('/user') === -1) {
                    window.location.href = "/";
                }

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

            authService.register($scope.credentials.username, $scope.credentials.email,
                $scope.credentials.password, $scope.credentials.gender)
                .then(function () {
                    $scope.showRegisterErrorMessage = false;
                    angular.element("#successRegistrationModal").modal();
                    $scope.credentials = {};
                }, function (data) {
                    $scope.showRegisterErrorMessage = true;
                    $scope.registerFormValidationErrors = data.message;
                });
        };

        /**
         * Redirects user to homepage, after that pop-up window about success registration has been dismissed.
         */
        $scope.processSuccessRegistration = function () {
            window.location.href = "/";
        };

        if($scope.loggedIn()) {
            userService.getByUsername($scope.username, function(response) {
                $scope.avatarUrl = response.avatar ? response.avatar.imgUrl : null;
            })
        }
    }];