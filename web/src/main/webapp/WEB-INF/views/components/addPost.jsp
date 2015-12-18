<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container" data-ng-controller="PostController">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="alert alert-success" role="alert" data-ng-show="newPostUrl && !showAddPostErrorMessage">
                <strong>Well done!</strong> You have been successfully add the post, and now it sits
                <a href="{{ newPostUrl }}" target="_self">here</a>
            </div>
            <div class="alert alert-danger" role="alert" data-ng-show="showAddPostErrorMessage">
                Error occurred, post was not stored :-(
            </div>
            <div class="page-header">
                <h2>Post: {{ postForm.title }}</h2>
            </div>
            <form data-ng-submit="addPost()">
                <div class="form-group">
                    <label>Title</label>
                    <input type="text" class="form-control" placeholder="Title"
                           data-ng-model="postForm.title"
                           autofocus>
                </div>
                <div class="form-group">
                    <label>Your thoughts:</label>
                <textarea class="form-control" rows="3" data-ng-model="postForm.textMessage"
                          title="Textarea"></textarea>
                </div>
                <label class="radio-inline">
                    <input type="radio" name="category" value="POST" data-ng-model="postForm.category"> POST
                </label>
                <label class="radio-inline">
                    <input type="radio" name="category" value="QUESTION" data-ng-model="postForm.category">
                    QUESTION
                </label>
                <div class="form-group">
                    <label>Photos</label>
                    <div>
                        <input id="images-input" type="file" data-file-model="postImages" multiple/>
                        <button type="button" data-ng-click="uploadImages()">Upload</button>
                    </div>
                    <div class="row">
                        <div class="col-lg-2" data-ng-repeat="image in postForm.images">
                            <img class="img-thumbnail" data-ng-src="{{image.imgUrl}}"/>
                            <a href="#" data-ng-click="removeImage(image.id)"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <button type="submit" class="btn btn-default">Submit</button>
                </div>
            </form>
        </div>
    </div>
</div>