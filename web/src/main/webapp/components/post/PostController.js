var PostController = ['$scope', '$routeParams', '$route', 'postService', 'sessionService',
    function ($scope, $routeParams, $route, postService, sessionService) {

        /* get post id from route provider */
        /* if postId is present, then upload post by id */
        if ($routeParams.id) {
            $scope.post = postService.get($routeParams.id);
        }

        /* username of authenticated user */
        $scope.username = sessionService.getUsername();

        /* data from post form */
        $scope.postForm = {};
        $scope.postForm.images = [];
        $scope.newPostUrl = undefined;
        $scope.error = false;

        $scope.addPost = function () {

            var addPost = postService.save($scope.postForm);

            addPost.then(function (data) {
                $scope.newPostUrl = "#/post/" + data.data.id;
                $scope.error = false;
                $scope.postForm = {};
            }, function () {
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

            /* send tags to remote api */
            var addTags = postService.addTags($routeParams.id, $scope.strTags);

            addTags.then(function (data) {   /* success */
                $scope.addTagsError = false;
                $scope.post.tags = data.data;
                $route.reload();
            }, function () {  /* error */
                $scope.addTagsError = true;
            });
        };

        /**
         * Upload image to api.
         *
         * @param image
         */
        $scope.uploadImage = function (image) {

            var uploadImage = postService.uploadImage(image);

            uploadImage.then(function (data) {
                $scope.postForm.images.push(data);
            });
        };

        /**
         * Upload images to api. Images selected by user.
         */
        $scope.uploadImages = function () {
            var images = $scope.postImages;
            for (var i = 0; i < images.length; i++) {
                $scope.uploadImage(images[i]);
            }
        };
    }];