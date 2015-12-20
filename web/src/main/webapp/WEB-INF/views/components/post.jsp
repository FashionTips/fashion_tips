<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container" data-ng-controller="PostController">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <data-ft-post></data-ft-post>

            <hr/>
            <h4 class="text-capitalize text-center">COMMENTS ({{ post.comments.length }})</h4>
            <hr/>

            <div class="list-group" data-ng-repeat="comment in post.comments">
                <div class="list-group-item">
                    <div class="media">
                        <div class="media-left">
                            <img class="media-object" src="http://placehold.it/64x64" alt="Avatar">
                        </div>
                        <div class="media-body">
                            <div class="media-heading">
                                <p class="col-md-6">{{ comment.author }}</p>
                                <p class="col-md-6 text-right">{{ comment.created | date:'medium' }}</p>
                            </div>
                            {{ comment.text }}
                        </div>
                    </div>
                </div>
            </div>

            <div data-ng-show="loggedIn()">
                <form data-ng-submit="addComment()">
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