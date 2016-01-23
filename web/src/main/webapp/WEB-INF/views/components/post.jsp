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
                <form data-ng-submit="saveComment()">
                    <div data-ng-show="commentFormError" class="alert alert-danger">
                        <p>Something went wrong:</p>
                        {{ commentFormError }}
                    </div>
                    <div class="form-group">
                        <label for="inputCommentText">Have thoughts? Type them here:</label>
                        <textarea id="inputCommentText" class="form-control" rows="3"
                                  data-ng-model="commentText"></textarea>
                    </div>
                    <button type="submit" class="btn btn-info">Submit</button>
                </form>
            </div>
            </div>
        </div>
    </div>
</div>