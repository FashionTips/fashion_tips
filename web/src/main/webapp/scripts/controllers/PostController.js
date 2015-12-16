var PostController = ['$scope', '$routeParams', '$route', 'postService', 'sessionService', '$location',
    function ($scope, $routeParams, $route, postService, sessionService, $location) {

        /* if postId is present, then upload post by id */
        var absUrl = $location.absUrl();
        var postId = absUrl.slice(absUrl.lastIndexOf('/') + 1);

        if (!isNaN(postId)) {
            $scope.post = postService.get(postId);
        }

        /* username of authenticated user */
        $scope.username = sessionService.getUsername();

        /* data from post form */
        $scope.postForm = {};
        $scope.postForm.images = [];
        $scope.newPostUrl = undefined;
        $scope.showAddPostErrorMessage = false;

        /**
         * Saves post.
         */
        $scope.addPost = function () {

            var addPost = postService.save($scope.postForm);

            addPost.then(function (data) {
                $scope.newPostUrl = "post/" + data.data.id;
                $scope.showAddPostErrorMessage = false;
                $scope.postForm = {};
                $scope.postForm.images = [];
                angular.element("#images-input").val(null); // clear file form input
            }, function () {
                $scope.showAddPostErrorMessage = true;
            });
        };

        ///**
        // * Tags as raw user's input string, not split yet.
        // * @type {string}
        // */
        //$scope.strTags = "";
        //
        ///**
        // * Flag to define whether there is error while attempt to add new tags.
        // */
        //$scope.addTagsError = false;
        //
        ///**
        // * Add tags to post from user input via sending them to API.
        // */
        //$scope.addTags = function () {
        //
        //    /* send tags to remote api */
        //    var addTags = postService.addTags($routeParams.id, $scope.strTags);
        //
        //    addTags.then(function (data) {   /* success */
        //        $scope.addTagsError = false;
        //        $scope.post.tags = data.data;
        //        $route.reload();
        //    }, function () {  /* error */
        //        $scope.addTagsError = true;
        //    });
        //};

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

        /**
         * Check if user is logged in.
         * @returns {boolean}
         */
        $scope.loggedIn = function () {
            return sessionService.getToken() !== undefined;
        };

        /**
         * Stores data form add comment form.
         * @type {string}
         */
        $scope.commentText = "";

        /**
         * Adds comment to post.
         */
        $scope.addComment = function () {

            var result = postService.addComment(postId, $scope.commentText);

            result.then(function (data) {
                $scope.post.comments.push(data);
                $scope.commentText = "";
            });
        };

        /**
         * Change "liked" status of post
         */
        $scope.toggleLikedStatus = function () {
            var result = postService.toggleLikedStatus(postId);

            result.then(function () {
                if ($scope.post.isLikedByAuthUser) {
                    $scope.post.isLikedByAuthUser = false;
                    $scope.post.likes--;
                } else {
                    $scope.post.isLikedByAuthUser = true;
                    $scope.post.likes++;
                }
            }, function () {

            });
        }
    }];