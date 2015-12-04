var PostController = ['$rootScope', '$scope', '$resource', '$http', '$location', '$routeParams', '$route',
    function ($rootScope, $scope, $resource, $http, $location, $routeParams, $route) {

        /* get post id from route provider */
        var postId = $routeParams.id;

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

        if (postId) {    //if postId is present
            /* upload post by id */
            $scope.post = Posts.get({id: postId});
        }

        $scope.authUser = $rootScope.$storage.user;

        $scope.newPostUrl;
        $scope.error = false;

        /* Function to upload file from post */
        $scope.uploadFile = function (files) {
            var fd = new FormData();
            //Take the first selected file
            fd.append("file", files[0]);

            $http.post("some upload url", fd, {
                withCredentials: true,
                headers: {'Content-Type': undefined},
                transformRequest: angular.identity
            }).success(/*...all right!... */).
            error(/*..damn!  ... */);
        };

        /* data from post form */
        $scope.postForm = {};

        $scope.submit = function () {
            Posts.save($scope.postForm, function (data) {

                /* success */
                $scope.newPostUrl = "#/post/" + data.data.id;
                $scope.error = false;
                $scope.postForm = {};
            }, function () {

                /* error occurred */
                $scope.error = true;
            });
        };

        /**
         * Tags as raw user's input string, not split yet.
         * @type {string}
         */
        $scope.strTags = "";

        /**
         * Flag to define whether there is error while attempt to add new tags.
         */
        $scope.addTagsError = false;

        /**
         * Add tags to post from user input via sending them to API.
         */
        $scope.addTags = function () {

            /* split string to get array of tags */
            var tags = $scope.strTags.split(/\s*,\s*/);

            /* send tags to remote api */
            $http
                .post(urlApi + "/posts/" + postId + "/tags", tags)
                .then(
                    function (data) {

                        /* success */
                        $scope.addTagsError = false;
                        $scope.post.tags = data.data;
                        $route.reload();
                    }, function () {

                        /* error */
                        $scope.addTagsError = true;
                    }
                );
        };
    }];