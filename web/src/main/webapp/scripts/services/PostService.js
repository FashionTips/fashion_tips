var postService = ['$resource', '$http', '$q', 'sessionService', function ($resource, $http, $q) {

    /* Define Posts resource */
    var Posts = $resource(urlApi + '/posts/:id', {}, {
        save: {
            method: 'POST',
            transformResponse: function (data) {

                /* make response headers and data available after call */
                var response = {};
                response.data = JSON.parse(data);
                return response;
            }
        },
        update: {
            method: 'PUT'
        }
    });

    /**
     * Returns all post of user by given username and/or query.
     * @param q query
     * @param username
     * @param category
     * @returns {d.promise|*|promise}
     */
    this.getAll = function (q, username, category) {

        var result = $q.defer();

        $http.get(urlApi + '/posts', {params: {hashtag: q, author: username, category: category}})
            .then(function (data) {
                result.resolve(data.data);
            }, function () {
                result.reject();
            });

        return result.promise;
    };

    /**
     * Loads post with given id.
     *
     * @param id post's id
     * @returns {*} post
     */
    this.get = function (id) {
        return Posts.get({id: id})
    };

    /**
     * Sends post to api.
     *
     * @param data post's data
     * @returns {d.promise|*|promise}
     */
    this.save = function (data) {

        var result = $q.defer();

        Posts.save(data, function (data) {
            result.resolve(data);
        }, function () {
            result.reject();
        });

        return result.promise;
    };

    /**
     * Updates post with given id by given data.
     *
     * @param id
     * @param postData
     * @returns {d.promise|*|promise}
     */
    this.update = function (id, postData) {

        var result = $q.defer();

        Posts.update({id: id}, postData, function (response) {
            result.resolve(response);
        }, function (response) {
            result.reject(response.data);
        });

        return result.promise;
    };

    /**
     * Deletes post with given id.
     *
     * @param id
     * @returns {d.promise|*|promise}
     */
    this.delete = function (id) {

        var result = $q.defer();

        Posts.delete({id: id}, function () {
            result.resolve();
        }, function (response) {
            result.reject(response);
        });

        return result.promise;
    };

    /**
     * Upload given image to api.
     *
     * @param image
     * @returns {d.promise|*|promise}
     */
    this.uploadImage = function (image) {

        var result = $q.defer();

        var uploadUrl = urlApi + "/images/upload";
        var fd = new FormData();
        fd.append('file', image);

        $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        }).then(function (data) {
            result.resolve(data.data);
        }, function (response) {
            result.reject(response);
        });

        return result.promise;
    };

    /**
     * Sends comment to api.
     *
     * @param postId
     * @param text
     * @returns {d.promise|*|promise}
     */
    this.addComment = function (postId, text) {

        var result = $q.defer();

        /* Define resource for post comments */
        var Comment = $resource(urlApi + '/posts/:postId/comments', {postId: postId});

        var newComment = new Comment({text: text});
        newComment.$save(function (data) {
            result.resolve(data)
        });

        return result.promise;
    };

    /**
     * Toggles liked status of post on api
     *
     * @param postId
     * @returns {d.promise|Function|*|promise}
     */
    this.toggleLikedStatus = function (postId) {
        var result = $q.defer();

        var likedUrl = urlApi + "/posts/" + postId + "/liked";

        $http.post(likedUrl, {})
            .then(
                function () {
                    result.resolve();
                }, function () {
                    result.reject();
                }
             );

        return result.promise;
    };
}];