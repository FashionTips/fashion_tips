angular.module('ft.security.session', [
        'ngCookies'
    ])

    .factory('sessionService', ['$cookies', function ($cookies) {

        var service = {};

        service.token = undefined;
        service.username = undefined;

        /**
         * Getter for token.
         *
         * @returns {*}
         */
        service.getToken = function () {
            if (!$cookies.get('fashionTipsAppToken')) {
                if (!service.token) {
                    return undefined;
                }
                service.setUserData(service.username, service.token);
            }
            return $cookies.get('fashionTipsAppToken');
        };

        /**
         * Getter for username.
         *
         * @returns {*}
         */
        service.getUsername = function () {
            if (!$cookies.get('username')) {
                if (!service.username) {
                    return undefined;
                }
                service.setUserData(service.username, service.token);
            }
            return $cookies.get('username');
        };

        /**
         * Sets up the user data - saves them in cookies and stores in fields.
         *
         * @param username
         * @param token
         */
        service.setUserData = function (username, token) {
            service.token = token;
            service.username = username;
            $cookies.put('fashionTipsAppToken', token, {path: "/"});
            $cookies.put('username', username, {path: "/"});
        };

        /**
         * Deletes the user's data from cookies and fields.
         */
        service.deleteUserData = function () {
            service.token = undefined;
            service.username = undefined;
            document.cookie = "fashionTipsAppToken=; expires=Thu, 01 Jan 1970 00:00:00 UTC";
            document.cookie = "username=; expires=Thu, 01 Jan 1970 00:00:00 UTC";
        };

        return service;
    }])

;