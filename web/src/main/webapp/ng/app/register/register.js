angular.module('ft.register', [
        'ngMessages',
        'ft.security.auth'
    ])

    .directive('ftRegisterForm', function () {
        return {
            restrict: 'E',
            templateUrl: 'ng/app/register/_registerForm.tpl.html',
            controller: function ($scope, authService) {

                /* Define variable for credential object which will be filled from inputs in Login and Register form */
                $scope.credentials = {};

                $scope.doRegister = function () {

                    authService.doRegister($scope.credentials, function (data) {
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
;
