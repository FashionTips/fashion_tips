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

        $http.post("/login", data, {headers: {'Content-Type': 'application/x-www-form-urlencoded'}})
            .then(function (response) {
                var token = response.headers('Token');
                sessionService.setUserData(username, token);
                result.resolve();
            }, function () {
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
            .then(function () {
                result.resolve();
            }, function () {
                result.reject();
            });

        return result.promise;
    };

    /**
     * Register new user.
     *
     * @param username
     * @param email
     * @param password
     * @param gender
     * @returns {d.promise|*|promise}
     */
    this.register = function (username, email, password, gender) {

        var result = $q.defer();

        $http.post(urlApi + "/users", {login: username, email: email, password: password, gender: gender})
            .then(
                function () {
                    result.resolve();
                }, function (response) {
                    result.reject(response.data);
                }
            );

        return result.promise;
    };

}];
