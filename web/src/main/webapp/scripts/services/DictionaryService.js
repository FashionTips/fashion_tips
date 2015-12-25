var dictionaryService = ['$http', '$q', function ($http, $q) {

    /**
     * Asks and returns list of all available countries.
     *
     * @returns {d.promise|*|promise}
     */
    this.getAllCountries = function () {

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
}];