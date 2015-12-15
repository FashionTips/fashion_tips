<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container" data-ng-controller="PostController">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="page-header">
                <h2>{{ post.title }}
                    <small>by {{ post.author.login }}</small>
                </h2>
                <h4 class="text-right">
                    Likes:{{post.likes}}
                    <span data-ng-show="post.isLikedByAuthUser !== null" class="glyphicon glyphicon-thumbs-up"
                          data-ng-class="{liked:post.isLikedByAuthUser}" data-ng-click="toggleLike()"></span>
                </h4>
            </div>
            <div id="post-gallery" class="carousel slide" data-ride="carousel">
                <!-- Indicators -->
                <ol class="carousel-indicators">
                    <li data-target="#post-gallery" data-slide-to="{{$index}}" ng-class='{active:$first}'
                        data-ng-repeat="num in post.images"></li>
                </ol>

                <!-- Wrapper for slides -->
                <div class="carousel-inner" role="listbox">
                    <div class="item" ng-class='{active:$first}' data-ng-repeat="image in post.images">
                        <img data-ng-src="{{image.imgUrl}}">
                    </div>
                </div>
            </div>

            <p>{{ post.textMessage }}</p>

            <hr/>

            <div data-ng-repeat="comment in post.comments">
                <p class="col-md-6 text-left">{{ comment.author.login }}</p>
                <p class="col-md-6 text-right">{{ comment.created }}</p>
                <p class="text-primary">{{ comment.text }}</p>
                <hr/>
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