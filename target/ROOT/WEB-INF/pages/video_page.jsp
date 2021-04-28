<%--
  Created by IntelliJ IDEA.
  User: declan
  Date: 4/28/2021
  Time: 4:12 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Video</title>
</head>
<body>
<jsp:include page="_header.jsp"/>

<h2>${shortVideo.videoTitle}</h2>
<video width="400" controls>
    <source src="/video/${shortVideo.videoId}/file" type="video/mp4">
    Your browser does not support HTML video.
</video>
<br>
<br>
<label><strong>Description:</strong></label>
${shortVideo.videoDescription}
<br>
<label><strong>Creater:</strong></label>
<a href="<spring:url value='/account/person/${shortVideo.userID}/'/>">${shortVideo.displayName}</a>
<br>
<label><strong>Create Date:</strong></label>
${shortVideo.createDate}
<br>
<label><strong>Favorite Number:</strong></label>
${shortVideo.favoriteCounter}
<c:choose>
    <c:when test="${pageContext.request.userPrincipal.name != null}">
        <form method="post" action="/video/${shortVideo.videoId}/comment" >
            <table>
                <tr>
                    <td><h3>Comment(${shortVideo.commentCounter})</h3></td>
                </tr>
                <tr>
                    <td><label> User: ${pageContext.request.userPrincipal.name}</label></td>
                </tr>
                <tr>
                    <td><input type="text" name="commentContext"></td>
                </tr>
                <tr>
                    <td><input type="submit"></td>
                </tr>
            </table>
        </form>
    </c:when>
    <c:otherwise>
        <br>
        <label><strong>Comments</strong></label>
        (${shortVideo.favoriteCounter})
    </c:otherwise>
</c:choose>






</body>
</html>
