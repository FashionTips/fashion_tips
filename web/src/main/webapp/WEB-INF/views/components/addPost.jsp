<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container" data-ng-controller="PostController">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="alert alert-success" role="alert" data-ng-show="newPostUrl && !showAddPostErrorMessage">
                <strong>Well done!</strong> You have been successfully add the post, and now it sits
                <a href="{{ newPostUrl }}" target="_self">here</a>
            </div>
            <div class="alert alert-danger" role="alert" data-ng-show="showAddPostErrorMessage">
                Errors occurred:
                <br/>
                {{ postFormErrors }}
            </div>
            <div class="page-header">
                <h2>Post: {{ postForm.title }}</h2>
            </div>
            <form name="addPostForm" accept-charset="UTF-8" data-ng-submit="addPost()"
                  data-ng-class="{'has-error': addPostForm.$invalid && addPostForm.$submitted}" novalidate>

                <%-- Title --%>
                <div class="form-group has-feedback"
                     data-ng-class="{ 'has-error': addPostForm.title.$invalid && addPostForm.title.$dirty,
                          'has-success': addPostForm.title.$valid && addPostForm.title.$dirty}">
                    <label>Title</label>
                    <input type="text" class="form-control" placeholder="Title" name="title"
                           data-ng-model="postForm.title" autofocus required maxlength="100">
                    <div data-ng-messages="addPostForm.title.$error" role="alert"
                         data-ng-show="addPostForm.title.$dirty">
                        <div data-ng-messages-include="messages.html"></div>
                    </div>
                </div>

                <%-- Text Message --%>
                <div class="form-group has-feedback" data-ng-class="{ 'has-error': addPostForm.textMessage.$invalid &&
                addPostForm.textMessage.$dirty,
                          'has-success': addPostForm.textMessage.$valid && addPostForm.textMessage.$dirty}">
                    <label>Your thoughts:</label>
                <textarea class="form-control" rows="3" data-ng-model="postForm.textMessage"
                          name="textMessage" title="Text Message" required maxlength="1000"></textarea>
                    <div data-ng-messages="addPostForm.textMessage.$error" role="alert"
                         data-ng-show="addPostForm.textMessage.$dirty">
                        <div data-ng-messages-include="messages.html"></div>
                    </div>
                </div>

                <%-- Category --%>
                <label class="radio-inline">
                    <input type="radio" name="category" required value="POST" data-ng-model="postForm.category"> POST
                </label>
                <label class="radio-inline">
                    <input type="radio" name="category" required value="QUESTION" data-ng-model="postForm.category">
                    QUESTION
                </label>

                    <%-- Block Comments --%>
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" data-ng-init="postForm.commentsAllowed = true"
                                   data-ng-model="postForm.commentsAllowed">Allow comments
                        </label>
                    </div>

                <%-- Pictures --%>
                <div class="form-group">
                    <label>Add Photos</label>
                    <div>
                        <span class="btn btn-info btn-sm btn-file">
                            Browse... <input type="file" data-custom-on-change="uploadImages" accept="image/*" multiple/>
                        </span>
                    </div>
                    <div class="alert alert-danger" data-ng-repeat="imageUploadError in imageUploadErrors">
                        {{imageUploadError}}
                    </div>
                    <div class="row">
                        <div class="col-sm-2" data-ng-repeat="image in postForm.images">
                            <img class="img-thumbnail" data-ng-src="{{image.imgUrl}}"/>
                            <a href="#" data-ng-click="removeImage(image.id)"><span class="glyphicon glyphicon-remove"
                                                                                    aria-hidden="true"></span></a>
                        </div>
                    </div>
                </div>

                <%-- Tags --%>
                <div class="form-group">
                    <label>Tags</label>
                    <div>
                        <p data-ng-hide="postForm.tagLines.length !== 0">No tags</p>
                        <span data-ng-repeat="tagLine in postForm.tagLines">
                            <strong>{{tagLine.clothes.name}}</strong> -
                            <span data-ng-repeat="tag in tagLine.tags">
                               <i>{{tag.tagType.type}}</i>:{{tag.value}}
                            </span>
                            <a href="#" data-ng-click="removeTagLine(tagLine)"><span class="glyphicon glyphicon-remove"
                                                                                    aria-hidden="true"></span></a>
                            <br/>
                        </span>
                    </div>
                </div>

                <%-- Add Tags section --%>
                <div data-ng-controller="TagController" class="form-group">
                    <button type="button" class="btn btn-info btn-sm" data-ng-hide="tagFormActive" data-ng-click="tagFormActive = true">Add tag</button>
                    <div data-ng-show="tagFormActive">
                        <label>Add Tag</label>
                        <div>
                            <select data-ng-model="currentTagLine.clothes" data-ng-options="clothesItem.name for clothesItem in clothes"></select>
                            <table>
                                <tr data-ng-repeat="tagType in tagTypes">
                                    <td><input type="checkbox" data-ng-model="isActive" data-ng-click="activateTag(this)" data-ng-disabled="!currentTagLine.clothes">{{tagType.type}}</td>
                                    <td><input type="text" data-ng-model="tagText" data-ng-change="setTagText(this)" data-ng-disabled="!isActive"></td>
                                </tr>
                            </table>
                        </div>
                        <button type="button" data-ng-click="addTagLineToPost()" data-ng-disabled="!currentTagLine.clothes" class="btn btn-info btn-sm">Add</button>
                    </div>
                </div>

                <input type="submit" class="btn btn-success" data-ng-disabled="addPostForm.$invalid || postForm.images.length === 0"
                           value="Create">
            </form>
        </div>
    </div>
</div>