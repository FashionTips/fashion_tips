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
        }
    });

    this.getAll = function (q) {

        var result = $q.defer();

        $http.get(urlApi + '/posts', {params: {hashtag: q}})
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
     * Parse string with tags, and add them to post with given id.
     *
     * @param id post's id
     * @param strTags tags as raw string
     * @returns {d.promise|*|promise}
     */
    this.addTags = function (id, strTags) {

        var result = $q.defer();

        /* split string to get array of tags */
        var tags = strTags.split(/\s*,\s*/);

        $http
            .post(urlApi + "/posts/" + id + "/tags", tags)
            .then(
                function (data) {
                    result.resolve(data);
                }, function () {
                    result.reject();
                }
            );

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
        newComment.$save(function(data) {result.resolve(data)});

        return result.promise;
    };
}];