<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container" data-ng-controller="MenuController">
    <div class="row">
        <div class="col-md-2 col-md-offset-5">
            <header>
                <h3>Log In</h3>
            </header>
            <section>
                <form name="signinForm" accept-charset="UTF-8" data-ng-submit="login()">
                    <p class="text-danger" data-ng-show="showLoginErrorMessage">Invalid login or password!</p>
                    <div class="form-group has-feedback"
                         data-ng-class="{ 'has-error': signinForm.username.$invalid }">
                        <input class="form-control" type="text" placeholder="Username" name="username"
                               data-ng-model="credentials.username" required autofocus>
                    </div>
                    <div class="form-group has-feedback"
                         data-ng-class="{ 'has-error': signinForm.password.$invalid }">
                        <input type="password" class="form-control" placeholder="Password" id="password" name="password"
                               data-ng-model="credentials.password" required>
                    </div>
                    <input class="btn btn-primary btn-block" type="submit" id="sign-in" value="Sign In">
                </form>
            </section>
        </div>
    </div>
</div>