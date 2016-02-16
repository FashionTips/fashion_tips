angular.module('ft.security.auth', [
    'ft.security.session'
])

    .factory('authService', ['$http', 'sessionService', '$q', function ($http, sessionService, $q) {

        var service = {};
        /**
         * Performs login.
         *
         * @param username
         * @param password
         * @returns {d.promise|*|promise}
         */
        service.doLogin = function (credentials, success, error) {

            var data = "username=" + credentials.username + "&password=" + credentials.password;

            $http.post("/login", data, {headers: {'Content-Type': 'application/x-www-form-urlencoded'}})
                .then(function (response) {
                    var token = response.headers('Token');
                    sessionService.setUserData(credentials.username, token);
                    success && success(response);
                }, function (response) {
                    error && error(response);
                });
        };

        /**
         * Performs logout.
         *
         * @returns {d.promise|*|promise}
         */
        service.logout = function () {

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
        service.doRegister = function (credentials, token, success, error) {

            $http.post(urlApi + "/users", {
                login: credentials.username,
                email: credentials.email,
                password: credentials.password,
                gender: credentials.gender}, {params: {token: token}})
                //.then(
                //    function () {
                //        result.resolve();
                //    }, function (response) {
                //        result.reject(response.data);
                //    }
                //);
                .then(function(response) {
                    success && success(response);
                }, function(response) {
                    error && error(response);
                });
        };

        service.sendVerificationRequest = function (email, tokenType) {

            var result = $q.defer();

            $http.post(urlApi + "/users/tokens/create", {email: email, type: tokenType})
                .then(function () {
                    result.resolve();
                }, function (response) {
                    result.reject(response);
                });

            return result.promise;
        };

        service.getEmailByToken = function (token) {
            var result = $q.defer();

            $http.post(urlApi + "/users/tokens/check", {token: token})
                .then(function (response) {
                    result.resolve(response.data);
                }, function (response) {
                    result.reject(response.data);
                });

            return result.promise;
        };

        return service;
    }])

;
