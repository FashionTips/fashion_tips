var sessionService = ['$cookies', function ($cookies) {

    this.token = undefined;
    this.username = undefined;

    /**
     * Getter for token.
     *
     * @returns {*}
     */
    this.getToken = function () {
        if (!$cookies.get('fashionTipsAppToken')) {
            if (!this.token) {
                return undefined;
            }
            this.setUserData(this.username, this.token);
        }
        return $cookies.get('fashionTipsAppToken');
    };

    /**
     * Getter for username.
     *
     * @returns {*}
     */
    this.getUsername = function () {
        if (!$cookies.get('username')) {
            if (!this.username) {
                return undefined;
            }
            this.setUserData(this.username, this.token);
        }
        return $cookies.get('username');
    };

    /**
     * Sets up the user data - saves them in cookies and stores in fields.
     *
     * @param username
     * @param token
     */
    this.setUserData = function (username, token) {
        this.token = token;
        this.username = username;
        $cookies.put('fashionTipsAppToken', token, {path: "/"});
        $cookies.put('username', username, {path: "/"});
    };

    /**
     * Deletes the user's data from cookies and fields.
     */
    this.deleteUserData = function () {
        this.token = undefined;
        this.username = undefined;
        document.cookie = "fashionTipsAppToken=; expires=Thu, 01 Jan 1970 00:00:00 UTC";
        document.cookie = "username=; expires=Thu, 01 Jan 1970 00:00:00 UTC";
    };
}];