<%--
  Created by IntelliJ IDEA.
  User: declan
  Date: 4/30/2021
  Time: 1:22 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

  <head>
    <title>Login</title>


    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <link href="<spring:url value="/css/style_new.css"/>" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">


    <script src="https://code.jquery.com/jquery-3.5.0.js"></script>
    <script src="<spring:url value="/js/style_new.js"/>"></script>
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

<div class="relative"><div class="main_body">
</div>

  <div class="header">

    <div class="header_btn" onclick="location.href = '<spring:url value="/"/>';">
      <div>
        <i class="material-icons">home</i>
        <label class="header_text">HOME</label>
      </div>
    </div>

    <c:choose>
      <c:when test="${pageContext.request.userPrincipal.name != null}">
        <div class="header_btn" id="my_page">
          <div>
            <i class="material-icons">person</i>
            <label class="header_text">MY VIDEO</label>
          </div>
        </div>
        <div class="header_btn" onclick="location.href = '<spring:url value="/account/videos_manage"/>';">
          <div>
            <i class="material-icons">build</i>
            <label class="header_text">MANAGE</label>
          </div>
        </div>
        <div class="header_btn" onclick="location.href = '<spring:url value="/video/upload"/>';">
          <div>
            <i class="material-icons">file_upload</i>
            <label class="header_text">UPLOAD</label>
          </div>
        </div>
      </c:when>
      <c:otherwise>

      </c:otherwise>
    </c:choose>

    <div class="search_div">
      <div>
        <input id="search_bar" type="text" placeholder="Search" value="${keyword}">
      </div>
    </div>
    <div id="search" class="search_btn">

      <i  class="material-icons"><strong>search</strong></i>
    </div>

  </div>
  <div class="login_panel">
    <c:choose>
      <c:when test="${pageContext.request.userPrincipal.name != null}">
        <div class="login_btns">
          <button onclick="location.href = '<spring:url value="/logout"/>';" >Logout</button>
        </div>
      </c:when>
      <c:otherwise>
      <form action="<spring:url value="/spring_security_check"/>" method="post">
        <div class="login_line">
          <i class="material-icons">account_circle</i>
          <input class="login_line_input"  type="text" name="username" placeholder="Account">
        </div>
        <div class="login_line">
          <i class="material-icons">lock</i>
          <input class="login_line_input" type="password" name="password" placeholder="Password">
        </div>
        <div class="login_btns">
          <button value="Login">Login</button>
          <button type="button" onclick="location.href = '<spring:url value="/account/signup"/>';" >Sigup</button>
        </div>
        <c:if test="${param.error == 'true'}"><label style="color: red">login error</label></c:if>
      </form>
      </c:otherwise>
    </c:choose>
  </div>

</div>

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
    $(document).on('click', '#search', search);

    $("#my_page").click(()=>{
      window.location = '/account/{id}/page'.replace("{id}",user_id);
    });

    $('#search_bar').keypress(function(e){
      if (e.which == 13){
        search();
      }
    });

  });

  function search(){
    var keyword = $('#search_bar').val();
    if (keyword != undefined && keyword != null) {
      window.location = '/search?keyword=' + keyword;
    }
  }
</script>
</html>
