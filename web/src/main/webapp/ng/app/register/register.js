angular.module('ft.register', [
        'ngMessages',
        'ft.security.auth'
    ])

    .directive('ftRegisterForm', function () {
        return {
            restrict: 'E',
            templateUrl: 'ng/app/register/_registerForm.tpl.html',
            scope: {
              token: '@'
            },
            controller: function ($scope, authService) {

                /* Define variable for credential object which will be filled from inputs in Login and Register form */
                $scope.credentials = {};

                var getEmailByVerificationToken = function () {
                    var result = authService.getEmailByVerificationToken($scope.token);

                    result.then(function (data) {
                        $scope.credentials.email = data.email;
                    }, function (data) {
                        $scope.verificationError = data.message;
                    });
                };
                getEmailByVerificationToken();

                $scope.doRegister = function () {

                    authService.doRegister($scope.credentials, $scope.token, function (data) {
                        $scope.error = undefined;
                        angular.element("#successRegistrationModal").modal();
                        $scope.credentials = {};
                    }, function (data) {
                        $scope.error = data.data.message;
                    });
                };

                /**
                 * Redirects user to homepage, after that pop-up window about success registration has been dismissed.
                 */
                $scope.processSuccessRegistration = function () {
                    window.location.href = "/";
                };
            }
        };
    })

    .directive('ftEmailVerificationForm', function () {
        return {
            restrict: 'E',
            templateUrl: 'ng/app/register/_emailVerificationForm.tpl.html',
            controller:  function ($scope, authService) {

                $scope.sendVerificationRequest = function () {
                    var result = authService.sendVerificationRequest($scope.email);

                    result.then(function () {
                        angular.element("#successEmailVerificationModal").modal();
                    }, function (data) {
                        if (data.status === 403) {
                            $scope.error = data.data.message
                        } else {
                            $scope.error = 'An error occurred while sending mail. Try again later.'
                        }
                    })
                };

                $scope.processSuccessEmailVerificationModal = function () {
                    window.location.href = "/";
                };
            }
        };
    })
;
