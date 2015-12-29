var userService = ['$resource', '$http', '$q', '$filter', function ($resource, $http, $q, $filter) {

    /* Define resource to deal with users */
    var Users = $resource(urlApi + "/users/:id", {}, {
        update: {
            method: 'PUT'
        }
    });

    /**
     * Requests user by given id.
     *
     * @param id
     * @returns {*}
     */
    this.get = function (id, success, failure) {
        return Users.get({id: id}, function () {
            success && success();
        }, function () {
            failure && failure();
        });
    };

    /**
     * Updates user - set to him the given data.
     *
     * @param user
     * @returns {d.promise|*|promise}
     */
    this.update = function (user) {

        var result = $q.defer();

        user.birthday = $filter('date')(user.birthday, "yyyy-MM-dd");

        Users.update({id: user.id}, user, function () {
            result.resolve();
        }, function (response) {
            result.reject(response);
        });

        return result.promise;
    };

    /**
     * Returns user by given username.
     *
     * @param username
     * @param success
     * @param failure
     * @returns {*}
     */
    this.getByUsername = function (username, success, failure) {

        return $resource(urlApi + "/users/by")
            .get({login: username}, function (response) {
                success && success(response);
            }, function () {
                failure && failure();
            });
    };

    /**
     * Uploads avatar image to server.
     *
     * @param image
     * @returns {d.promise|*|promise}
     */
    this.uploadAvatar = function (image) {

        var result = $q.defer();

        var uploadUrl = urlApi + "/images/upload";
        var fd = new FormData();
        fd.append('file', image);

        $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        }).then(function (data) {
            result.resolve(data.data);
        });

        return result.promise;
    };
}];