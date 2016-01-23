<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container">
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
                <ft-register-form></ft-register-form>
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