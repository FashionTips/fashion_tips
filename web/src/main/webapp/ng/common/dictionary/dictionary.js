angular.module('ft.dictionary', [])

    .factory('dictionaryService', ['$http', '$q', function ($http, $q) {

        var service = {};

        /**
         * Asks and returns list of all available countries.
         *
         * @returns {d.promise|*|promise}
         */
        service.getAllCountries = function () {

            var result = $q.defer();

            $http.get(urlApi + "/dictionary/countries")
                .then(function (response) {
                        result.resolve(response.data);
                    }, function () {
                        result.reject();
                    }
                );

            return result.promise;
        };

        return service;
    }])

;