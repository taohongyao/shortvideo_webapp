<%@ taglib prefix="Spring" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  UserAccount: lizhuohui
  Date: 12/14/20
  Time: 12:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <link href="<spring:url value="/css/style_new.css"/>" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <script src="https://code.jquery.com/jquery-3.5.0.js"></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://www.google.com/recaptcha/api.js?render=6LeswMIaAAAAAK4WSr52wkT5k0JlmhfykwxRkBse"></script>

    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <title>Registration</title>
    <script>
        grecaptcha.ready(function() {
            // do request for recaptcha token
            // response is promise with passed token
            grecaptcha.execute('6LeswMIaAAAAAK4WSr52wkT5k0JlmhfykwxRkBse', {action:'validate_captcha'})
                .then(function(token) {
                    // add token value to form
                    document.getElementById('g-recaptcha-response').value = token;
                });
        });
    </script>
</head>
<body>

<div class="relative"><div class="main_body">
</div>

    <div class="header">

        <div class="header_btn" onclick="location.href = '<spring:url value="/"/>';">
            <div>
                <i class="material-icons">home</i>
                <label class="header_text">HOME</label>
            </div>
        </div>

        <div class="search_div">
            <div>
                <input id="search_bar" type="text" placeholder="Search" value="${keyword}">
            </div>
        </div>
        <div id="search" class="search_btn">

            <i  class="material-icons"><strong>search</strong></i>
        </div>

        <div class="login_panel">
            <div class="login_btns logout_btns">
                <button onclick="location.href = '<spring:url value="/"/>';" >Sign in</button>
            </div>
        </div>

    </div>

</div>


<%--@elvariable id="useraccountinfo" type="com.edu.neu.project.model.UserAccountInfo"--%>
<div class="signup_panel">
    <div class="signup_panel_mid">


        <div>
            <h1>Sign up</h1>
        </div>

        <form:form modelAttribute="useraccountinfo" method="post" action="signup">


            <div class="sign_up_field">
                <i class="material-icons">person</i>
                <label>Account
                    <c:if test="${usernameexist}">
                        <label class="valid-error" >username exist</label>
                    </c:if>
                    <form:errors path="username" cssClass="valid-error"/>
                </label>
                <input id="account" type="text" name="username" value="${useraccount.username}"/>
            </div>
            <div class="sign_up_field">
                <i class="material-icons">face</i>
                <label>NickName
                    <form:errors path="displayName" cssClass="valid-error"/>
                </label>
                <input id="nick_name" type="text" name="displayName" value="${useraccount.displayName}"/>
            </div>
            <div class="sign_up_field">
                <i class="material-icons">lock</i>
                <label>Password
                    <form:errors path="password" cssClass="valid-error"/>
                </label>
                <input id="password" type="password" name="password" value="${useraccount.password}"/>
            </div>

            <div class="sign_up_field">

                <i class="material-icons">done_all</i>
                <label>Repeat password
                    <form:errors path="retypePassword" cssClass="valid-error"/>
                </label>
                <input id="retypePassword" type="password" name="retypePassword" value="${useraccount.retypePassword}" />
            </div>

            <div class="submit-container">
                <input type="hidden" id="g-recaptcha-response" name="gRecaptchaResponse">
                <input type="hidden" name="action" value="validate_captcha">
                <button id="submit" value="Submit" > Submit </button>
            </div>

        </form:form>
    </div>
</div>

</body>

<script>
    function checkName(name) {
        let regex = RegExp("^[a-zA-Z0-9_]{5,10}$");
        return regex.test(name);
    }

    function checkInvalidChars(string) {
        let regex = RegExp("^[a-zA-Z0-9_]*$");
        //console.log(string + " " + regex.test(string))
        return !regex.test(string);
    }

    function checkPassword(password) {
        return password.length >= 5
    }


    function canSubmit() {
        if (checkName($("#account").val()) && checkName($("#nick_name").val()) && checkPassword($("#password").val()) && matchPassword())
            $("#submit").attr("disabled", false)
    }

    function matchPassword() {
        return $("#password").val() === $("#retypePassword").val()
    }

    $("#account").on('keyup change', function (e) {
        $("#usernameExistError").empty();
        let message = "";
        let inp = $("#account").val();

        if (checkInvalidChars(inp)) message += "Invalid input; ";
        if (inp.length < 5) message += "Too Short; ";
        if (inp.length > 10) message += "Too Long; ";
        $("#usernameError").text(message);

    });

    $("#nick_name").on('keyup change', function (e) {
        let message = "";
        let inp = $("#nick_name").val();
        if (checkInvalidChars(inp)) message += "Invalid input(only digits, letters and '_'); ";
        if (inp.length < 5) message += "Too Short(at least 5); ";
        if (inp.length > 10) message += "Too Long(at most 10); ";
        $("#displayNameError").text(message);

    });

    $("#password").on('keyup change', function (e) {
        let message = "";
        let inp = $("#password").val();
        if (inp.length < 5) message += "Too Short(at least 5); ";
        $("#passwordError").text(message);

    });

    $("#retypePassword").on('keyup change', function (e) {
        let message = "";
        if (!matchPassword()) message += "password doesn't match";
        $("#retypePasswordError").text(message);

    });

    $("input").on('keyup change', function (e) {
        canSubmit()
    });

</script>
</html>
