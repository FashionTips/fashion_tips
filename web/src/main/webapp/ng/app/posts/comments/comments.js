angular.module('ft.posts.comments', [
        'ngMessages',
        'ngResource',
        'ft.security.session'
    ])

    .factory('commentService', ['$resource', function ($resource) {

        var service = {};

        var Comments = $resource(urlApi + '/posts/:postId/comments/:commentId', {}, {
            update: {
                method: 'PUT'
            }
        });

        /**
         * Sends comment to api.
         *
         * @param postId - post's id comment belongs to
         * @param comment - comment's text
         * @param success - success handler
         * @param error - error handler
         */
        service.saveComment = function (postId, comment, success, error) {

            Comments.save({postId: postId}, comment, function (response) {
                success && success(response);
            }, function (response) {
                error && error(response);
            })
        };

        /**
         * Updates comment - sends new data to api.
         *
         * @param postId
         * @param commentId
         * @param comment - comment's text
         * @param success - success handler
         * @param error - error handler
         */
        service.updateComment = function (postId, commentId, comment, success, error) {

            Comments.update({postId: postId, commentId: commentId}, comment, function (response) {
                success && success(response);
            }, function (response) {
                error && error(response);
            });
        };

        /**
         * Deletes comment.
         *
         * @param postId
         * @param commentId
         * @param success
         * @param error
         */
        service.deleteComment = function (postId, commentId, success, error) {

            Comments.delete({postId: postId, commentId: commentId}, function (response) {
                success && success(response);
            }, function (response) {
                error && error(response);
            });
        };

        return service;
    }])

    /* Show comment */
    .directive('ftComment', function () {
        return {
            restrict: 'E',
            templateUrl: '/ng/app/posts/comments/_comment.tpl.html',
            scope: {
                'comment': '=',
                'postId': '@'
            },
            controller: function ($scope, sessionService, commentService) {

                $scope.$watch(function () {
                    return sessionService.getUsername();
                }, function (value) {
                    $scope.username = value;
                });

                $scope.showForm = function() {
                    $scope.editMode = true;
                    $scope.commentData = angular.copy($scope.comment);
                };

                $scope.hideForm = function() {
                    $scope.editMode = false;
                    $scope.commentData = {};
                };

                /**
                 * Updates comment.
                 */
                $scope.updateComment = function() {

                    commentService.updateComment($scope.postId, $scope.comment.id, $scope.commentData, function(data) {
                        $scope.error = undefined;
                        $scope.editMode = false;
                        $scope.comment = data;
                    }, function(data) {
                        $scope.error = data.message;
                    });
                };

                /**
                 * Deletes comment.
                 */
                $scope.deleteComment = function () {

                    commentService.deleteComment($scope.postId, $scope.comment.id, function (data) {
                        $scope.comment.available = false;
                    }, function (data) {
                        // temporary do nothing
                        console.log("Error occurred: cannot delete comment");
                    });
                };
            }
        };
    })
;