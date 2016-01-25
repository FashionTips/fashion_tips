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
                <div class="list-group-item">
                    <div data-ng-hide="comment.available">
                        <p>This comment was deleted by its author.</p>
                    </div>
                    <div data-ng-show="comment.available" class="media">
                        <div class="media-left">
                            <img data-ng-src="{{ comment.author.avatar || 'http://placehold.it/64x64' }}" height="64" class="media-object"/>
                        </div>
                        <div class="media-body">
                            <div class="media-heading">
                                <p class="col-md-5"><a href="/user/{{comment.author.login}}">{{ comment.author.login
                                    }}</a></p>
                                <p class="col-md-6 text-right">{{ comment.created | date:'medium' }}</p>
                                <p data-ng-show="comment.author.login === username()" class="col-md-1">
                                    <a href data-ng-click="deleteComment(comment.id)">
                                        <span class="text-danger glyphicon glyphicon-remove" aria-hidden="true"></span>
                                    </a>
                                </p>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <p>{{ comment.text }}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
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
                    <button type="submit" class="btn btn-default">Submit</button>
                </form>
            </div>
            </div>
        </div>
    </div>
</div>