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
                <form name="registerForm" accept-charset="UTF-8" data-ng-submit="register()"
                      data-ng-class="{'has-error': registerForm.$invalid && registerForm.$submitted}" novalidate>
                    <p class="text-danger" data-ng-show="showRegisterErrorMessage">{{ registerFormValidationErrors
                        }}</p>

                    <%-- Username --%>
                    <div class="form-group has-feedback"
                         data-ng-class="{ 'has-error': registerForm.username.$invalid && registerForm.username.$dirty,
                          'has-success': registerForm.username.$valid && registerForm.username.$dirty}">
                        <input class="form-control" type="text" placeholder="Username" name="username"
                               data-ng-model="credentials.username" required autofocus data-minlength="4"
                               data-maxlength="32" data-username>
                        <span class="glyphicon form-control-feedback" aria-hidden="true"
                              data-ng-class="{'glyphicon-ok': registerForm.username.$valid && registerForm.username.$dirty,
                              'glyphicon-remove': registerForm.username.$invalid && registerForm.username.$dirty}"></span>
                        <div data-ng-messages="registerForm.username.$error" role="alert"
                             data-ng-show="registerForm.username.$dirty">
                            <div data-ng-messages-include="messages.html"></div>
                            <span class="help-block" data-ng-show="registerForm.username.$pending.username">
                                Checking if this name is available...
                            </span>
                        </div>
                    </div>

                    <%-- Email --%>
                    <div class="form-group has-feedback"
                         data-ng-class="{ 'has-error': registerForm.email.$invalid && registerForm.email.$dirty,
                          'has-success': registerForm.email.$valid && registerForm.email.$dirty}">
                        <input class="form-control" type="email" placeholder="Email" name="email"
                               data-ng-model="credentials.email" required data-unique-email>
                        <span class="glyphicon form-control-feedback" aria-hidden="true"
                              data-ng-class="{'glyphicon-ok': registerForm.email.$valid && registerForm.email.$dirty,
                              'glyphicon-remove': registerForm.email.$invalid && registerForm.email.$dirty}"></span>
                        <div data-ng-messages="registerForm.email.$error" role="alert"
                             data-ng-show="registerForm.email.$dirty">
                            <div data-ng-messages-include="messages.html"></div>
                            <span class="help-block" data-ng-show="registerForm.email.$pending.email">
                                Checking if this email is available...
                            </span>
                        </div>
                    </div>

                    <%-- Gender --%>
                    <div class="form-group" data-ng-class="{ 'has-error': registerForm.gender.$invalid && registerForm.gender.$dirty,
                          'has-success': registerForm.gender.$valid && registerForm.gender.$dirty}">
                        <div class="radio">
                            <label class="radio-inline">
                                <input type="radio" name="gender" value="GIRL" data-ng-model="credentials.gender"
                                       required>Female
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="gender" value="GUY" data-ng-model="credentials.gender"
                                       required>Male
                            </label>
                            <div data-ng-messages="registerForm.gender.$error" role="alert"
                                 data-ng-show="registerForm.gender.$dirty">
                                <div data-ng-messages-include="messages.html"></div>
                            </div>
                        </div>
                    </div>

                    <%-- Password --%>
                    <div class="form-group has-feedback"
                         data-ng-class="{ 'has-error': registerForm.password.$invalid && registerForm.password.$dirty,
                          'has-success': registerForm.password.$valid && registerForm.password.$dirty}">
                        <input type="password" class="form-control" placeholder="Password" id="password" name="password"
                               data-ng-model="credentials.password" required data-minlength="4" data-maxlength="32">
                        <div data-ng-messages="registerForm.password.$error" role="alert"
                             data-ng-show="registerForm.password.$dirty">
                            <div data-ng-messages-include="messages.html"></div>
                        </div>
                    </div>

                    <%-- Confirm Password --%>
                    <div class="form-group has-feedback"
                         data-ng-class="{ 'has-error': registerForm.confirmPassword.$invalid && registerForm.confirmPassword.$dirty,
                           'has-success': registerForm.confirmPassword.$valid && registerForm.confirmPassword.$dirty}">
                        <input type="password" class="form-control" placeholder="Password one more time"
                               id="confirmPassword"
                               name="confirmPassword"
                               data-ng-model="credentials.confirmPassword" required
                               data-ng-pattern="{{ credentials.password }}">
                        <div data-ng-messages="registerForm.confirmPassword.$error" role="alert"
                             data-ng-show="registerForm.confirmPassword.$dirty">
                            <div data-ng-messages-include="messages.html"></div>
                        </div>
                    </div>
                    <p class="text-muted">
                        <small>By clicking "Create an account", you agree to the <a href="#" target="_blank">terms</a>.
                        </small>
                    </p>
                    <input class="btn btn-primary btn-block" type="submit" id="sign-in" value="Create an account"
                           data-ng-disabled="registerForm.$invalid">
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