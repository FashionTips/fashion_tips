angular.module('ft.restore.password', [
        'ft.security.auth'
])

    .factory('restorePasswordService', ['$http', '$q', function($http, $q) {
        var service = {};

        service.restorePassword = function (credentials, token, success, error) {

            $http.put(urlApi + "/users/reset/password", {password: credentials.password}, {params: {token: token}})
                .then(function(response) {
                    success && success(response);
                }, function(response) {
                    error && error(response);
                });

        };
        return service;
    }])


    .directive('ftEmailRestorePasswordRequest', function () {
        return {
            restrict: 'E',
            templateUrl: 'ng/app/restore_password/_restorePasswordRequest.tpl.html',
            controller:  function ($scope, authService) {

                $scope.sendRestorePasswordRequest = function () {
                    var result = authService.sendVerificationRequest($scope.email, "PASSWORD_RESET");

                    result.then(function () {
                        angular.element("#successRestorePasswordRequestModal").modal();
                    }, function (data) {
                        if (data.status === 403 || data.status === 404) {
                            $scope.error = data.data.message
                        } else {
                            $scope.error = 'An error occurred while sending mail. Try again later.'
                        }
                    })
                };

                /**
                 * Redirects user to homepage, after that pop-up window about success sending of restore password request
                 * has been dismissed.
                 */
                $scope.processSuccessRestorePasswordRequestModal = function () {
                    window.location.href = "/";
                };
            }
        };
    })

    .directive('ftRestorePasswordForm', function () {
        return {
            restrict: 'E',
            templateUrl: 'ng/app/restore_password/_restorePassword.tpl.html',
            scope: {
                token: '@'
            },
            controller:  function ($scope, authService, restorePasswordService) {
                /* Define variable for credential object which will be filled from inputs in Login, Register
                and Restore Password form */
                $scope.credentials = {};

                var getEmailByToken = function () {
                    var result = authService.getEmailByToken($scope.token);

                    result.then(function (data) {
                        $scope.credentials.email = data.email;
                    }, function (data) {
                        $scope.verificationError = data.message;
                    });
                };
                getEmailByToken();

                $scope.restorePassword = function () {

                    restorePasswordService.restorePassword($scope.credentials, $scope.token, function (data) {
                        $scope.error = undefined;
                        angular.element("#successResetPasswordModal").modal();
                        $scope.credentials = {};
                    }, function (data) {
                        $scope.error = data.data.message;
                    });
                };

                /**
                 * Redirects user to login form, after that pop-up window about success password change has been dismissed.
                 */
                $scope.processSuccessResetPasswordModal = function () {
                    window.location.href = "/login";
                };
            }
        };
    })
;