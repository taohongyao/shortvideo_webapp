<%--
  Created by IntelliJ IDEA.
  User: declan
  Date: 4/27/2021
  Time: 1:44 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Page</title>
</head>
<body>
<c:if test="${param.upload_video_successfully == 'true'}"><label style="color: red">Video Upload Successfully</label></c:if>
<jsp:include page="_header.jsp"/>
<jsp:include page="_user_info_header.jsp"/>
<jsp:include page="_person_video_list.jsp"/>
</body>
</html>
