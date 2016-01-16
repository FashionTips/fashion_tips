<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container" data-ng-controller="ProfileController">
    <div class="page-header">
        <h2>Edit your profile</h2>
    </div>
    <form class="form-horizontal" name="userForm" data-ng-submit="editProfile()">
        <div class="row">
            <div class="col-md-3">
                <img data-ng-src="{{ user.avatar.imgUrl || 'http://placehold.it/200x200' }}"
                     class="img-responsive img-rounded"/>
            </div>
            <div class="col-md-9">

                <%-- Avatar --%>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="inputAvatar">Picture</label>
                    <div class="col-sm-10">
                        <input type="file" id="inputAvatar" data-file-model="avatarFile">
                        <button type="button" data-ng-click="uploadAvatar()">Upload</button>
                        <p class="help-block">Set a new profile picture</p>
                    </div>
                </div>

                <%-- Username--%>
                <div class="form-group">
                    <label class="col-sm-2 control-label">Login</label>
                    <div class="col-sm-10">
                        <p class="form-control-static" data-ng-bind-html="user.login"></p>
                    </div>
                </div>

                <%-- First Name --%>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="inputFirstName">First Name</label>
                    <div class="col-sm-10">
                        <input name="firstName" type="text" class="form-control" id="inputFirstName"
                               data-ng-model="user.firstName">
                    </div>
                </div>

                <%-- Last Name --%>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="inputLastName">Last Name</label>
                    <div class="col-sm-10">
                        <input name="firstName" type="text" class="form-control" id="inputLastName"
                               data-ng-model="user.lastName">
                    </div>
                </div>

                <%-- Birthday --%>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="inputBirthday">Birthday</label>
                    <div class="col-sm-10">
                        <input name="birthday" value="{{user.birthday | date:'yyyy-MM-dd' }}" type="date"
                               class="form-control" id="inputBirthday"
                               data-ng-model="user.birthday">
                        <div class="checkbox">
                            <label>
                                <input name="hideAge" type="checkbox" data-ng-model="user.hideAge"> I want to hide my
                                age
                            </label>
                        </div>
                    </div>

                </div>

                <%-- Gender --%>
                <div class="form-group">
                    <label class="col-sm-2 control-label">Gender</label>
                    <div class="col-sm-10">
                        <label class="radio-inline">
                            <input type="radio" name="gender" value="GIRL" data-ng-model="user.gender"> Girl
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="gender" value="GUY" data-ng-model="user.gender"> Guy
                        </label>
                    </div>
                </div>

                <%-- Location --%>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="inputLocation">Location</label>
                    <div class="col-sm-10">
                        <input name="location" type="text" class="form-control" id="inputLocation"
                               data-ng-model="user.location">
                    </div>
                </div>

                <%-- Country --%>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="inputCountry">Country</label>
                    <div class="col-sm-10">
                        <select class="form-control" id="inputCountry" data-ng-model="user.country"
                                data-ng-options="country.name for country in countries track by country.id">
                        </select>
                    </div>
                </div>

                <%-- Occupation --%>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="inputOccupation">Occupation</label>
                    <div class="col-sm-10">
                        <input name="occupation" type="text" class="form-control" id="inputOccupation"
                               data-ng-model="user.occupation">
                    </div>
                </div>

                <%-- About Me --%>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="inputAboutMe">AboutMe</label>
                    <div class="col-sm-10">
                            <textarea name="aboutMe" class="form-control" rows="4" data-ng-model="user.aboutMe"
                                      id="inputAboutMe"></textarea>
                    </div>
                </div>

                <%-- Instagram --%>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="inputInstagram">Instagram</label>
                    <div class="col-sm-10">
                        <div class="input-group">
                            <span class="input-group-addon">@</span>
                            <input name="instagram" type="text" class="form-control" id="inputInstagram"
                                   data-ng-model="user.instagram">
                        </div>
                    </div>
                </div>

                <%-- Blog URL --%>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="inputBlogUrl">Blog URL</label>
                    <div class="col-sm-10">
                        <input name="blogUrl" type="url" class="form-control" id="inputBlogUrl"
                               data-ng-model="user.blogUrl">
                    </div>
                </div>

                <%-- Website URL --%>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="inputWebsiteUrl">Website URL</label>
                    <div class="col-sm-10">>
                        <input name="websiteUrl" type="url" class="form-control" id="inputWebsiteUrl"
                               data-ng-model="user.websiteUrl">
                    </div>
                </div>

                <%-- Submit --%>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-2">
                        <input type="submit" class="btn btn-info" value="Save Changes">
                    </div>
                    <a class="btn btn-default" href="{{ user.id }}" role="button">Cancel</a>
                </div>
            </div>
        </div>
    </form>
</div>