var authService = ['$http', 'sessionService', '$q', function ($http, sessionService, $q) {

    /**
     * Performs login.
     *
     * @param username
     * @param password
     * @returns {d.promise|*|promise}
     */
    this.login = function (username, password) {

        var result = $q.defer();

        var data = "username=" + username + "&password=" + password;

        $http.post("/login", data, {headers: {'Content-Type': 'application/x-www-form-urlencoded'}}).
        success(function (data, status, headers) {
            var token = headers('Token');
            sessionService.setUserData(username, token);
            result.resolve();
        }).
        error(function () {
            result.reject();
        });

        return result.promise;
    };

    /**
     * Performs logout.
     *
     * @returns {d.promise|*|promise}
     */
    this.logout = function () {

        var result = $q.defer();

        sessionService.deleteUserData();
        $http.post("/logout", {})
            .success(function () {
                result.resolve();
            })
            .error(function () {
                result.reject();
            });

        return result.promise;
    };

}];
