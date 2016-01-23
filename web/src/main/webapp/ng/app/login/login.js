angular.module('ft.login', [
    'ft.security.auth'
])

.directive('ftLoginForm', function () {
    return {
        restrict: 'E',
        templateUrl: '/ng/app/login/_loginForm.tpl.html',
        controller: function ($scope, authService) {

            /* Define variable for credential object which will be filled from inputs in Login and Register form */
            $scope.credentials = {};

            $scope.doLogin = function () {

                authService.doLogin($scope.credentials, function(data) {
                    $scope.showLoginErrorMessage = undefined;
                    $scope.credentials = {};

                    /* if user is on login page - redirect him to home*/
                    if ($scope.toHome === 'true') {
                        window.location.href = "/";
                    }
                }, function(data) {
                    $scope.showLoginErrorMessage = true;
                })
            };
        },
        scope: {
            'toHome': '@'
        }
    };
})

;
