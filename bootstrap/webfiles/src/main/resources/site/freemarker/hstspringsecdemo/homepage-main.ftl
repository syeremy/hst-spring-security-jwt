<#include "../include/imports.ftl">

<@hst.setBundle basename="essentials.homepage"/>
<div>
  <h1><@fmt.message key="homepage.title" var="title"/>${title?html}</h1>
  <p><@fmt.message key="homepage.text" var="text"/>${text?html}</p>
    <#if !hstRequest.requestContext.cmsRequest>
        <p>
            [This text can be edited <a href="http://localhost:8080/cms/?1&path=/content/documents/administration/labels/homepage" target="_blank">here</a>.]
        </p>
    </#if>
</div>


<div class="container">
    <h1>JWT Spring Security Demo</h1>

    <div class="alert alert-danger" id="notLoggedIn">Not logged in!</div>

    <div class="alert alert-info text-hidden" id="loggedIn"></div>

    <div class="row">
        <div class="col-md-6">
            <div class="panel panel-default" id="login">
                <div class="panel-heading">
                    <h3 class="panel-title">Login</h3>
                </div>
                <div class="panel-body">
                    <form id="loginForm">
                        <div class="form-group">
                            <input type="text" class="form-control" id="exampleInputEmail1" placeholder="username"
                                   required name="username">
                        </div>
                        <div class="form-group">
                            <input type="password" class="form-control" id="exampleInputPassword1"
                                   placeholder="password" required name="password">
                        </div>
                        <div class="well">
                            Try one of the following logins
                            <ul>
                                <li>admin & admin</li>
                                <li>user & password</li>
                                <li>disabled & password</li>
                            </ul>
                        </div>
                        <button type="submit" class="btn btn-default">login</button>
                    </form>
                </div>
            </div>

            <div id="userInfo">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Authenticated user</h3>
                    </div>
                    <div class="panel-body">
                        <div id="userInfoBody"></div>
                        <button type="button" class="btn btn-default" id="logoutButton">logout</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-md-6">
            <div class="btn-group" role="group" aria-label="..." style="margin-bottom: 16px;">
                <button type="button" class="btn btn-default" id="exampleServiceBtn">call example service</button>
                <button type="button" class="btn btn-default" id="adminServiceBtn">call admin protected service</button>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Response:</h3>
                </div>
                <div class="panel-body">
                    <pre id="response"></pre>
                </div>
            </div>
        </div>
    </div>
</div>

<@hst.include ref="container"/>
