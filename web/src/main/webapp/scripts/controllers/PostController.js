var PostController = ['$scope', '$routeParams', '$route', 'postService', 'sessionService', '$location',
    function ($scope, $routeParams, $route, postService, sessionService, $location) {

        /* if postId is present, then upload post by id */
        var absUrl = $location.absUrl();
        var postId = absUrl.slice(absUrl.lastIndexOf('/') + 1);

        if (!isNaN(postId)) {
            $scope.post = postService.get(postId);
        }

        /* username of authenticated user */
        $scope.username = function () {
            return sessionService.getUsername();
        };

        /* data from post form */
        $scope.postForm = {};
        $scope.postForm.images = [];
        $scope.postForm.tagLines = [];
        $scope.newPostUrl = undefined;
        $scope.showAddPostErrorMessage = false;
        $scope.imageUploadErrors = [];

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
            }, function (reason) {
                var messageError = reason.data !== null ? reason.data.message : "Cannot load file " + image.name + ", it is too big. Should be less than 5 MB";
                $scope.imageUploadErrors.push(messageError);
            });
        };

        /**
         * Upload images to api. Images selected by user.
         */
        $scope.uploadImages = function () {
            $scope.imageUploadErrors = [];
            var images = $scope.postImages;
            for (var i = 0; i < images.length; i++) {
                $scope.uploadImage(images[i]);
            }
        };

        /**
         * Remove image from post form.
         * @param id
         */
        $scope.removeImage = function (id) {
            var index = 0;

            for (var i = 0; i < $scope.postForm.images.length; i++) {
                if (id === $scope.postForm.images[i].id) {
                    index = i;
                    break;
                }
            }
                $scope.postForm.images.splice(index, 1);
        };

        $scope.removeTagLine = function (tagLine) {
            for (var i = 0; i < $scope.postForm.tagLines.length; i++) {
                if (tagLine === $scope.postForm.tagLines[i]) {
                    $scope.postForm.tagLines.splice(i, 1);
                    break;
                }
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
        };

        /**
         * Saves updated post.
         *
         * @param id post's id
         */
        $scope.editPost = function (id) {

            var result = postService.update(id, $scope.postForm);

            result
                .then(function (data) {
                    $scope.showAddPostErrorMessage = false;
                    $scope.postForm = {};
                    $scope.postForm.images = [];
                    angular.element("#images-input").val(null); // clear file form input
                    angular.element("#postEditModal").modal('hide');
                    $scope.post = data;
                }, function (data) {
                    $scope.postForm.errors = data.message;
                    $scope.showAddPostErrorMessage = true;
                });
        };

        /**
         * Deletes post.
         *
         * @param id
         */
        $scope.deletePost = function (id) {

            var result = postService.delete(id);

            result
                .then(function () {
                    $scope.post = {};
                    document.location.href = "/";
                }, function () {
                    alert('Error! Post was not deleted.');
                });
        };

        /**
         * Fills out post edit form with current post's data.
         */
        $scope.showEditPostForm = function () {
            $scope.postForm = angular.copy($scope.post);    //copy object without references
        };

        /**
         * Clears the post form.
         */
        $scope.clearPostForm = function () {
            $scope.postForm = {};
            $scope.showAddPostErrorMessage = false;
        };
    }];