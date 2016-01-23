angular.module('ft.validation', [])

/**
 * Directive for custom validation - check whether the given login is unique
 */
.directive('username', ['$q', '$http', function($q, $http) {
    return {
        require: 'ngModel',
        link: function(scope, elm, attrs, ctrl) {
            ctrl.$asyncValidators.username = function (modelValue, viewValue) {

                if(ctrl.$isEmpty(modelValue)) {
                    // consider empty model valid
                    return $q.when();
                }

                var def = $q.defer();

                $http.get(urlApi + "/users/available", {params: {login: viewValue}})
                    .then(function (response) {
                        if(response.data) {
                            def.resolve();
                        } else {
                            def.reject();
                        }
                    });

                return def.promise;
            };
        }
    };
}])



/**
 * Directive for custom validation - check whether the given email is unique
 */
.directive('uniqueEmail', ['$q', '$http', function($q, $http) {
    return {
        require: 'ngModel',
        link: function(scope, elm, attrs, ctrl) {
            ctrl.$asyncValidators.uniqueEmail = function (modelValue, viewValue) {

                if(ctrl.$isEmpty(modelValue)) {
                    // consider empty model valid
                    return $q.when();
                }

                var def = $q.defer();

                $http.get(urlApi + "/users/available", {params: {email: viewValue}})
                    .then(function (response) {
                        if(response.data) {
                            def.resolve();
                        } else {
                            def.reject();
                        }
                    });

                return def.promise;
            };
        }
    };
}])


;

