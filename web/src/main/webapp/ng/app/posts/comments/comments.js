angular.module('ft.posts.comments', [
        'ngResource'
    ])

    .factory('commentService', ['$resource', function ($resource) {

        var service = {};

        var Comments = $resource(urlApi + '/posts/:postId/comments/:commentId');

        /**
         * Sends comment to api.
         *
         * @param postId - post's id comment belongs to
         * @param text - comment's text
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
;