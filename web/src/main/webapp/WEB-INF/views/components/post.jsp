<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3" data-ng-controller="PostController">
            <ft-post data-id="${id}" data-post="post"></ft-post>

            <div data-ng-show="post.commentsAllowed">
            <hr/>
            <h4 class="text-capitalize text-center">COMMENTS ({{ post.comments.length }})</h4>
            <hr/>

            <div class="list-group" data-ng-repeat="comment in post.comments">
                <ft-comment comment="comment" post-id="{{ post.id }}"></ft-comment>
            </div>

            <div data-ng-show="loggedIn()">
                <form name="commentForm" data-ng-submit="commentForm.$valid && saveComment()" novalidate
                      data-ng-class="{'has-error': commentForm.$invalid && commentForm.$submitted}">
                    <div data-ng-show="commentFormError" class="alert alert-danger">
                        <p>Something went wrong:</p>
                        {{ commentFormError }}
                    </div>
                    <div class="form-group has-feedback"
                         data-ng-class="{ 'has-error': commentForm.commentText.$invalid && commentForm.commentText.$dirty,
                          'has-success': commentForm.commentText.$valid && commentForm.commentText.$dirty}">
                        <label for="inputCommentText">Have thoughts? Type them here:</label>
                        <textarea id="inputCommentText" name="commentText" class="form-control" rows="3"
                                  data-ng-model="commentText" required maxlength="255" ng-trim="false">
                        </textarea>
                        <span data-ng-show="(255 - commentText.length) < 100" class="help-block">{{ 255 - commentText.length }} symbols left</span>
                        <div data-ng-messages="commentForm.commentText.$error" role="alert"
                             data-ng-show="commentForm.commentText.$dirty">
                            <div data-ng-messages-include="/messages.html">
                            </div>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-info">Submit</button>
                </form>
            </div>
            </div>
        </div>
    </div>
</div>