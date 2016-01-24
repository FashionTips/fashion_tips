angular.module('ft.posts', [
        'ngMessages',
        'ngResource',
        'ui.bootstrap',
        'ft.posts.comments',
        'ft.posts.tags',
        'ft.security.session'
    ])


    .factory('postService', ['$resource', '$http', '$q', 'sessionService', function ($resource, $http, $q) {

        var service = {};

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
         *
         * @param q
         * @param username
         * @param category
         * @param success
         * @param error
         */
        service.getAll = function (q, username, category, success, error) {

            Posts.query({hashtag: q, author: username, category: category}, function(response) {
                success && success(response);
            }, function(response) {
                error && error(response);
            });
        };

        /**
         * Loads post with given id.
         *
         * @param id post's id
         * @returns {*} post
         */
        service.get = function (id) {
            return Posts.get({id: id})
        };

        /**
         * Sends post to api.
         *
         * @param data - post's data
         * @param success - success handler
         * @param error - error handler
         */
        service.save = function (data, success, error) {

            Posts.save(data, function (data) {
                success && success(data);
            }, function (response) {
                error && error(response.data.data);
            });
        };

        /**
         * Updates post with given id by given data.
         *
         * @param id - post's id
         * @param postData - data for update
         * @param success - success handler
         * @param error - error handler
         */
        service.update = function (id, postData, success, error) {

            Posts.update({id: id}, postData, function (response) {
                success && success(response);
            }, function (response) {
                error && error(response);
            });
        };

        /**
         * Deletes post with given id.
         *
         * @param id - post's id
         * @param success - success handler
         * @param error - error handler
         */
        service.delete = function (id, success, error) {

            Posts.delete({id: id}, function (response) {
                success && success(response);
            }, function (response) {
                error && error(response);
            });
        };

        /**
         * Upload given image to api.
         *
         * @param image
         * @returns {d.promise|*|promise}
         */
        service.uploadImage = function (image) {

            var result = $q.defer();

            var uploadUrl = urlApi + "/upload";
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
         * Toggles liked status of post on api
         *
         * @param postId
         * @returns {d.promise|Function|*|promise}
         */
        service.toggleLikedStatus = function (postId) {
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

        return service;
    }])


    .controller('PostController', ['$scope', 'commentService', 'sessionService', '$filter',
        function ($scope, commentService, sessionService, $filter) {

            /* username of authenticated user */
            $scope.username = function () {
                return sessionService.getUsername();
            };


            /**
             * Check if user is logged in.
             * @returns {boolean}
             */
            $scope.loggedIn = function () {
                return sessionService.getToken() !== undefined;
            };


            /**
             * Adds comment to post.
             */
            $scope.saveComment = function () {

                commentService.saveComment($scope.post.id, {text: $scope.commentText}, function (data) {
                    $scope.commentFormError = undefined;
                    $scope.post.comments.push(data);
                    $scope.commentText = undefined;
                }, function (data) {
                    $scope.commentFormError = data.message;
                });
            };

            /**
             * Deletes comment with given id.
             *
             * @param id
             */
            $scope.deleteComment = function (id) {

                commentService.deleteComment($scope.post.id, id, function (data) {
                    var comment = $filter('filter')($scope.post.comments, {id: id})[0];
                    comment.available = false;
                }, function (data) {
                    // temporary do nothing
                    console.log("Error occurred: cannot delete comment");
                });
            };
        }])


    /* DIRECTIVES */


    .directive('ftPost', [function () {
        return {
            restrict: 'E',
            templateUrl: '/ng/app/posts/_post.tpl.html',
            scope: {
                'id': '@',
                'feed': '@',
                'post': '=',
                'loggedIn': '@'
            },
            controller: function ($scope, sessionService, postService, $uibModal) {

                if ($scope.id) {
                    $scope.post = postService.get($scope.id);
                }

                $scope.$watch(function () {
                    return sessionService.getUsername();
                }, function (value) {
                    $scope.username = value;
                });

                /**
                 * Check if user is logged in.
                 * @returns {boolean}
                 */
                $scope.loggedIn = function () {
                    return sessionService.getToken() !== undefined;
                };

                /**
                 * Change "liked" status of post
                 */
                $scope.toggleLikedStatus = function (postId) {

                    var result = postService.toggleLikedStatus(postId);

                    var post = $scope.post;

                    result.then(function () {
                        if (post.isLikedByAuthUser) {
                            post.isLikedByAuthUser = false;
                            post.likes--;
                        } else {
                            post.isLikedByAuthUser = true;
                            post.likes++;
                        }
                    }, function () {

                    });
                };

                /**
                 * Shows the confirmation modal dialog, and deletes the post after approve.
                 */
                $scope.delete = function () {

                    var ModalInstanceCtrl = function ($scope, $uibModalInstance) {

                        $scope.ok = function () {
                            $uibModalInstance.close();
                        };

                        $scope.cancel = function () {
                            $uibModalInstance.dismiss('cancel');
                        };
                    };

                    var modal = $uibModal.open({
                        templateUrl: '/ng/app/posts/_delete.tpl.html',
                        controller: ModalInstanceCtrl
                    });

                    modal.result.then(function () {
                        postService.delete($scope.post.id, function (data) {
                            $scope.post = {};
                            document.location.href = "/";
                        }, function (data) {
                            alert('Error! Post was not deleted.');
                        })
                    });
                };

                /**
                 * Shows the modal with edit post form.
                 */
                $scope.edit = function () {

                    var ModalInstanceCtrl = function ($scope, $uibModalInstance, post) {

                        $scope.post = post;

                        $scope.ok = function () {
                            $uibModalInstance.close($scope.post);
                        };

                        $scope.cancel = function () {
                            $uibModalInstance.dismiss('cancel');
                        };
                    };

                    var modal = $uibModal.open({
                        template: '<ft-post-form modal="true" post="post"></ft-post-form>',
                        controller: ModalInstanceCtrl,
                        resolve: {
                            post: function() {
                                return angular.copy($scope.post);
                            }
                        }
                    });

                    modal.result.then(function (post) {
                        postService.update($scope.post.id, post, function (data) {
                            $scope.post = data;
                        }, function (data) {
                            alert('Error: post was not updated');
                        });
                    });
                };

            }
        };
    }])


    .directive('ftPostForm', function () {
        return {
            restrict: 'E',
            templateUrl: '/ng/app/posts/_form.tpl.html',
            scope: {
                modal: '@',
                post: '='
            },
            controller: function ($scope, postService) {

                if (!$scope.post) {

                    $scope.post = {
                        images: [],
                        tagLines: []
                    };
                }

                /**
                 * Saves post.
                 */
                $scope.savePost = function () {

                    postService.save($scope.post, function (data) {
                        $scope.newPostUrl = "post/" + data.data.id;
                        $scope.post = {images: []};
                        $scope.error = undefined;
                        angular.element("#images-input").val(null); // clear file form input
                        window.location.href = $scope.newPostUrl;
                    }, function (data) {
                        $scope.error = data.message;
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
                        $scope.post.images.push(data);
                    }, function (reason) {
                        var messageError = reason.data !== null ? reason.data.message : "Cannot load file " + image.name + ", it is too big. Should be less than 5 MB";
                        $scope.imageUploadErrors.push(messageError);
                    });
                };

                /**
                 * Upload images to api. Images selected by user.
                 */
                $scope.uploadImages = function (event) {
                    $scope.imageUploadErrors = [];
                    var images = event.target.files;
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

                    for (var i = 0; i < $scope.post.images.length; i++) {
                        if (id === $scope.post.images[i].id) {
                            index = i;
                            break;
                        }
                    }
                    $scope.post.images.splice(index, 1);
                };


                $scope.removeTagLine = function (tagLine) {
                    for (var i = 0; i < $scope.post.tagLines.length; i++) {
                        if (tagLine === $scope.post.tagLines[i]) {
                            $scope.post.tagLines.splice(i, 1);
                            break;
                        }
                    }
                };

                /* MODAL */

                $scope.ok = function () {
                    $scope.$parent.ok();
                };

                $scope.cancel = function () {
                    $scope.$parent.cancel();
                };
            }
        };
    })
;