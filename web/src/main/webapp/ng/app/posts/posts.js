angular.module('ft.posts', [
        'ngMessages',
        'ngResource',
        'ui.bootstrap',
        'ui.bootstrap.datetimepicker',
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
         * @param clothes
         * @param tag
         * @param success
         * @param error
         */
        service.getAll = function (q, username, category, clothes, tagType, tagValue, success, error) {

            Posts.query({hashtag: q, author: username, category: category, clothes: clothes,
                tagType: tagType, tag: tagValue }, function(response) {
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


    .controller('PostController', ['$scope', 'commentService', 'sessionService',
        function ($scope, commentService, sessionService) {

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
                    $scope.commentForm.$setPristine();
                }, function (data) {
                    $scope.commentFormError = data.message;
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
                    var result = postService.get($scope.id);
                    result.$promise.then(function (data) {
                        $scope.post = data;
                        $scope.activeImage = $scope.post.images[0];
                    });
                } else {
                    $scope.activeImage = $scope.post.images[0];
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

                /* Make particular image active */
                $scope.showImage = function (image) {
                    $scope.activeImage = image;
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
            controller: function ($scope, $uibModal, postService, tagService) {

                if (!$scope.post) {

                    $scope.post = {
                        images: [],
                        status: 'PUBLISHED'
                    };
                } else {
                    $scope.statusOfPost = $scope.post.status;
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

                /* Make particular image active */
                $scope.showImage = function (image) {
                    $scope.activeImage = image;
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
                    if ($scope.activeImage.id === $scope.post.images[index].id) $scope.activeImage = null;
                    $scope.post.images.splice(index, 1);
                };


                $scope.openModalRemoveTagLine = function (tagLineId) {

                    var ModelController = function ($scope, $uibModalInstance) {

                        $scope.ok = function () {
                            $uibModalInstance.close();
                        };

                        $scope.cancel = function () {
                            $uibModalInstance.dismiss('cancel');
                        };
                    };

                    var modal = $uibModal.open({
                        templateUrl: '/ng/app/posts/tags/_deleteTag.tpl.html',
                        controller: ModelController
                    });

                    modal.result.then(function () {
                        var result = tagService.delete(tagLineId);

                        result.then(function () {
                            for (var i = 0; i < $scope.activeImage.tagLines.length; i++) {
                                if (tagLineId === $scope.activeImage.tagLines[i].id) {
                                    $scope.activeImage.tagLines.splice(i, 1);
                                    break;
                                }
                            }
                        }, function () {
                            alert('Error: TagLine has not been deleted.');
                        });
                    });
                };

                $scope.openModalAddTag = function () {

                    var modal = $uibModal.open({
                        templateUrl: '/ng/app/posts/tags/_addTag.tpl.html',
                        controller: 'TagController',
                        resolve: {
                            imageId: function () {
                                return $scope.activeImage.id;
                            }
                        }
                    });

                    modal.result.then(function (data) {
                        $scope.activeImage.tagLines.push(data);
                        });
                };

                $scope.setPublicationTime = function () {
                    var date = moment($scope.formPublicationTime);
                    $scope.post.publicationTime = date.format("YYYY-MM-DDTHH:mm");
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