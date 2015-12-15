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
                window.location.href = $scope.newPostUrl;
            }, function () {
                $scope.showAddPostErrorMessage = true;
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
    }];