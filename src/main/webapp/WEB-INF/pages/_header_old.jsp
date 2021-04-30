<%--
  Created by IntelliJ IDEA.
  User: declan
  Date: 4/27/2021
  Time: 1:40 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <script src="https://code.jquery.com/jquery-3.5.0.js"></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

    <link href="<spring:url value="/css/style_new.css"/>" rel="stylesheet" type="text/css">

    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>
<body>

<security:authorize access="hasAnyAuthority('${AuthorityEnum.Customer.authority}')">
    <input id="authority" type="hidden" value="customer"/>
</security:authorize>
<security:authorize access="hasAnyAuthority('${AuthorityEnum.Manager.authority}')">
    <input id="authority" type="hidden" value="manager"/>
</security:authorize>
<security:authorize access="hasAnyAuthority('${AuthorityEnum.Admin.authority}')">
    <input id="authority" type="hidden" value="admin"/>
</security:authorize>


<c:choose>

    <c:when test="${pageContext.request.userPrincipal.name != null}">
        <table>
            <tr>
                <td><label> Welcom ${pageContext.request.userPrincipal.name}</label></td>
                <td><button onclick="location.href = '<spring:url value="/account/videos_manage"/>';" >ManageMyVideos</button></td>
                <td><button onclick="location.href = '<spring:url value="/video/upload"/>';" >UploadVideo</button></td>
                <td><button onclick="location.href = '<spring:url value="/logout"/>';" >Logout</button></td>
            </tr>
        </table>
        <input type="hidden" id="user_name" value="${pageContext.request.userPrincipal.name}">
    </c:when>

    <c:otherwise>

            <table>
                <form action="<spring:url value="/spring_security_check"/>" method="post">
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
                </form>
                    <td><button onclick="location.href = '<spring:url value="/account/signup"/>';" >Register</button></td>
                    <c:if test="${param.error == 'true'}"><td><label style="color: red">login error</label></td></c:if>
                </tr>
            </table>

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

    var user_id;
    var user_name;
    var user_display_name;

    function initUserInfo(){
        jQuery.ajax({
            url: '/account/info',
            type: 'GET',
            success: (data)=>{
                user_id=data['userID'];
                user_name=data['username'];
                user_display_name=data['displayName'];
            },
            async: false
        });
    }

    $(document).ready(() => {
        initUserInfo();
        $(document).on('click', '#search', function() {
            var keyword = $('#productName').val();
            if (keyword != undefined && keyword != null) {
                window.location = '/search?keyword=' + keyword;
            }
        });
    });

</script>
</html>
