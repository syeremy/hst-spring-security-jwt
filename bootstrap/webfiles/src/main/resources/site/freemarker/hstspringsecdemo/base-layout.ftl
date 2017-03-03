<!doctype html>
<#include "../include/imports.ftl">
<html lang="en">
<head>
    <meta charset="utf-8"/>


    <meta charset="UTF-8">
    <title>JWT Spring Security Demo</title>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
          integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css"
          integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">

    <style type="text/css">
        .text-hidden {
            text-overflow: ellipsis;
            overflow: hidden;
            white-space: nowrap;
        }

        .text-shown {
            word-wrap: break-word;
        }
    </style>

    <link rel="stylesheet" href="<@hst.webfile  path="/css/bootstrap.css"/>" type="text/css"/>
    <@hst.defineObjects/>
    <#if hstRequest.requestContext.cmsRequest>
        <link rel="stylesheet" href="<@hst.webfile  path="/css/cms-request.css"/>" type="text/css"/>
    </#if>
    <@hst.headContributions categoryExcludes="htmlBodyEnd, scripts" xhtml=true/>

</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
        <@hst.include ref="top"/>
        <@hst.include ref="menu"/>
        </div>
    </div>
    <div class="row">
        <@hst.include ref="main"/>
    </div>
    <div class="row">
        <@hst.include ref="footer"/>
    </div>
</div>
<@hst.headContributions categoryIncludes="htmlBodyEnd, scripts" xhtml=true/>

<script src="https://code.jquery.com/jquery-2.2.2.js" integrity="sha256-4/zUCqiq0kqxhZIyp4G0Gk+AOtCJsY1TA00k5ClsZYE="
        crossorigin="anonymous"></script>
<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"
        integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS"
        crossorigin="anonymous"></script>

<script src="<@hst.webfile  path="js/client.js"/>"></script>
</body>
</html>