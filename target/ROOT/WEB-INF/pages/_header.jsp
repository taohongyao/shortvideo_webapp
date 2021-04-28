<%--
  Created by IntelliJ IDEA.
  User: declan
  Date: 4/27/2021
  Time: 1:40 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <script src="https://code.jquery.com/jquery-3.5.0.js"></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>
<body>


<c:choose>

    <c:when test="${pageContext.request.userPrincipal.name != null}">
        <table>
            <tr>
                <td><label> Welcom ${pageContext.request.userPrincipal.name}</label></td>
                <td><button onclick="location.href = '<spring:url value="/video/upload"/>';" >UploadVideo</button></td>
            </tr>
        </table>
    </c:when>

    <c:otherwise>
        <form action="<spring:url value="/spring_security_check"/>" method="post">
            <table>
                <tr>
                    <td>User Name</td>
                    <td><input name="username"/></td>
                </tr>

                <tr>
                    <td>Password</td>
                    <td><input type="password" name="password"/></td>
                </tr>
                <tr>
                    <td><input type="submit" value="Login"/></td>
                    <td><button onclick="location.href = '<spring:url value="/account/signup"/>';" >Register</button></td>
                    <c:if test="${param.error == 'true'}"><td><label style="color: red">login error</label></td></c:if>
                </tr>
            </table>
        </form>
    </c:otherwise>
</c:choose>

<a href='<spring:url value="/"/>'>Home</a>

<div class="ui-widget">
        <label for="productName">Search Video</label>
        <input id="productName">
        <button id="search">search</button>
</div>
<br>


</body>
<script>
    $(document).on('click', '#search', function() {
        var keyword = $('#productName').val();
        if (keyword != undefined && keyword != null) {
            window.location = '/search?keyword=' + keyword;
        }
    });
</script>
</html>
