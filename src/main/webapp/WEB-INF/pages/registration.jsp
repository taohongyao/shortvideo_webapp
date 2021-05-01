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
    <link href='<spring:url value="/css/styles.css"/>' rel="stylesheet"/>
    <script src="https://code.jquery.com/jquery-3.5.0.js"></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <title>Registration</title>
</head>
<body>

<%--@elvariable id="useraccountinfo" type="com.edu.neu.project.model.UserAccountInfo"--%>
<form:form modelAttribute="useraccountinfo" method="post" action="signup">
    <table style="text-align: left">
        <tr>
            <td>Username:</td>
            <td><input type="text" name="username" id="username" value="${useraccount.username}"/><form:errors
                    path="username" cssClass="valid-error"/>
            </td>
            <td><label class="valid-error" id="usernameError"></label></td>
            <c:if test="${usernameexist}">
                <td><label id="usernameExistError" class="valid-error">username exist</label></td>
            </c:if>
        </tr>

        <tr>
            <td>Password:</td>
            <td><input type="password" name="password" id="password" value="${useraccount.password}"/><form:errors
                    path="password"
                    cssClass="valid-error"/></td>
            <td><label class="valid-error" id="passwordError"></label></td>
        </tr>

        <tr>
            <td>Retype Password:</td>
            <td><input type="password" name="retypePassword" id="retypePassword" value="${useraccount.retypePassword}"/><form:errors
                    path=""
                    cssClass="valid-error"/>
            </td>
            <td><label class="valid-error" id="retypePasswordError"></label></td>
        </tr>

        <tr>
            <td>Display Name:</td>
            <td><input type="text" name="displayName" id="displayName" value="${useraccount.displayName}"/><form:errors
                    path="displayName"
                    cssClass="valid-error"/></td>
            <td><label class="valid-error" id="displayNameError"></label></td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td><input type="submit" value="Submit" id="submit" disabled/></td>
                <%--            <td><input type="submit" value="Submit" id="submit" /></td>--%>
        </tr>
    </table>
</form:form>
<a href="<spring:url value="/home"/>">go home</a>
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
        if (checkName($("#username").val()) && checkName($("#displayName").val()) && checkPassword($("#password").val()) && matchPassword())
            $("#submit").attr("disabled", false)
    }

    function matchPassword() {
        return $("#password").val() === $("#retypePassword").val()
    }

    $("#username").on('keyup change', function (e) {
        $("#usernameExistError").empty();
        let message = "";
        let inp = $("#username").val();


        if (checkInvalidChars(inp)) message += "Invalid input; ";
        if (inp.length < 5) message += "Too Short; ";
        if (inp.length > 10) message += "Too Long; ";
        $("#usernameError").text(message);

    });

    $("#displayName").on('keyup change', function (e) {
        let message = "";
        let inp = $("#displayName").val();
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
