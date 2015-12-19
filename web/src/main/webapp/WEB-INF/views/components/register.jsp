<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container" data-ng-controller="MenuController">
    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <div class="page-header">
                <h1 class="text-center">Join our community of fashion lovers!</h1>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <header>
                <h3>Create your account</h3>
            </header>
            <section>
                <form name="registerForm" accept-charset="UTF-8" data-ng-submit="register()">
                    <p class="text-danger" data-ng-show="showRegisterErrorMessage">{{ registerFormValidationErrors
                        }}</p>
                    <div class="form-group has-feedback"
                         data-ng-class="{ 'has-error': registerForm.username.$invalid }">
                        <input class="form-control" type="text" placeholder="Username" name="username"
                               data-ng-model="credentials.username" required autofocus data-minlength="4"
                               data-maxlength="32">
                        <div data-ng-messages="registerForm.username.$error" role="alert">
                            <span class="help-block" data-ng-message="minlength, maxlength">
                                Your login must be between 4 and 32 characters long
                            </span>
                        </div>
                    </div>
                    <div class="form-group has-feedback"
                         data-ng-class="{ 'has-error': registerForm.email.$invalid }">
                        <input class="form-control" type="email" placeholder="Email" name="email"
                               data-ng-model="credentials.email" required>
                        <div data-ng-messages="registerForm.email.$error" role="alert">
                            <span class="help-block" data-ng-message="email">
                                Please provide correct email
                            </span>
                        </div>
                    </div>
                    <div class="form-group has-feedback"
                         data-ng-class="{ 'has-error': registerForm.password.$invalid }">
                        <input type="password" class="form-control" placeholder="Password" id="password" name="password"
                               data-ng-model="credentials.password" required data-minlength="4" data-maxlength="32">
                        <div data-ng-messages="registerForm.password.$error" role="alert">
                            <span class="help-block" data-ng-message="minlength, maxlength">
                                 Your password must be between 4 and 32 characters long
                            </span>
                        </div>
                    </div>
                    <div class="form-group has-feedback"
                         data-ng-class="{ 'has-error': registerForm.confirmPassword.$invalid }">
                        <input type="password" class="form-control" placeholder="Password one more time"
                               id="confirmPassword"
                               name="confirmPassword"
                               data-ng-model="credentials.confirmPassword" required>
                        <div data-ng-messages="registerForm.confirmPassword.$error" role="alert">
                            <span class="help-block" data-ng-message="minlength, maxlength">
                                 Your password must be between 4 and 32 characters long
                            </span>
                        </div>
                    </div>
                    <p class="text-muted">
                        <small>By clicking "Create an account", you agree to the <a href="#" target="_blank">terms</a>.</small>
                    </p>
                    <input class="btn btn-primary btn-block" type="submit" id="sign-in" value="Create an account">
                </form>
            </section>
        </div>
    </div>
</div>

<%-- Success Registration Modal --%>
<div id="successRegistrationModal" class="modal fade" tabindex="-1" role="dialog" data-ng-controller="MenuController">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title text-center"><b>Congrats!</b></h4>
            </div>
            <div class="modal-body">
                <p> You have been successfully registered.</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success center-block" data-dismiss="modal"
                        data-ng-click="processSuccessRegistration()">Great!
                </button>
            </div>
        </div>
    </div>
</div>