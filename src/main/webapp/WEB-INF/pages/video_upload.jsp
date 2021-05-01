<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  UserAccount: hongyaotao
  Date: 11/26/20
  Time: 10:07 PM
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
    <title>Upload Product</title>
    <style>
        p {
            color: red;
        }

    </style>
</head>

<body>
<jsp:include page="_header.jsp"/>
<form:form action="upload" method="post" modelAttribute="videoInfo" enctype="multipart/form-data">
    <div class="upload_panel">
        <div class="upload_panel_mid">


            <div>
                <h1>Upload Video</h1>
            </div>

            <form:form action="upload" method="post" modelAttribute="videoInfo" enctype="multipart/form-data">

                <input id="short_video_id" name="short_video_id" type="hidden" value="${videoInfo.videoId}">

                <div class="upload_video_title">
                    <label>Title</label>
                    <input id="name" type="text" name="videoTitle" value="${videoInfo.videoTitle}"/> <form:errors path="videoTitle" cssClass="valid-error"/>
                </div>
                <div class="upload_video_title">
                    <label>Description</label>
                    <textarea id="videoDescription" rows="8" name="videoDescription">${videoInfo.videoDescription}</textarea><form:errors
                        path="videoDescription" cssClass="valid-error"/>
                </div>
                <div>
                    <label>Video Cover  <label id="label_cover_file"></label><form:errors path="videoCoverFile" cssClass="valid-error"/></label>
                    <button type="button" onclick="javascript:document.getElementById('imgFile').click()">Choose Cover</button>
                    <input type="file" accept="image/*" name="videoCoverFile" multiple="multiple"  id="imgFile" style="visibility: hidden; position: absolute; left:-40px;" onchange="javascript:document.getElementById('label_cover_file').innerText='('+this.value+')'">
                </div>

                <div>
                    <label>Video File <label id="label_video_file"></label><form:errors path="videoFile" cssClass="valid-error"/></label>
                    <button type="button" onclick="javascript:document.getElementById('videoFile').click()">Choose Video</button>
                    <input type="file" accept="video/*" name="videoFile" multiple="multiple" id="videoFile" style="visibility: hidden; position: absolute; left:-40px;" onchange="javascript:document.getElementById('label_video_file').innerText='('+this.value+')'">
                </div>

                <div>
                    <button id="submitButton" value="Submit"> Submit </button>
                </div>

            </form:form>
        </div>

    </div>

<%--    <input id="short_video_id" name="short_video_id" type="hidden" value="${videoInfo.videoId}">--%>
    <input id="isimagechanged" name="isimagechanged" type="hidden" value="false"/>
<%--    <div style="top:130px;position: absolute;">--%>
<%--    <table style="text-align: left">--%>
<%--        <tr>--%>
<%--            <td>Title</td>--%>
<%--            <td><input id="name" type="text" name="videoTitle" value="${videoInfo.videoTitle}"/> <form:errors--%>
<%--                    path="videoTitle" cssClass="valid-error"/></td>--%>
<%--            <td><p id="nameerror"></p></td>--%>

<%--        </tr>--%>
<%--        <tr>--%>
<%--            <td>Image</td>--%>
<%--            <td>--%>
<%--                <div id="imgpreview"></div>--%>
<%--            </td>--%>
<%--        </tr>--%>
<%--        <tr>--%>
<%--            <td>Video Cover</td>--%>
<%--            <td><input type="file" accept="image/*" name="videoCoverFile" multiple="multiple"--%>
<%--                       id="imgFile"><form:errors--%>
<%--                    path="videoCoverFile" cssClass="valid-error"/></td>--%>
<%--        </tr>--%>
<%--        <tr>--%>
<%--            <td>Video File</td>--%>
<%--            <td><input type="file" accept="video/*" name="videoFile" multiple="multiple"--%>
<%--                       id="imgFile"><form:errors--%>
<%--                    path="videoFile" cssClass="valid-error"/></td>--%>
<%--        </tr>--%>
<%--        <tr>--%>
<%--            <td>Description</td>--%>
<%--            <td><textarea id="videoDescription" rows="8" name="videoDescription">${videoInfo.videoDescription}</textarea><form:errors--%>
<%--                    path="videoDescription" cssClass="valid-error"/></td>--%>
<%--            <td><p id="videoDescription"></p></td>--%>
<%--        </tr>--%>
<%--        <tr>--%>
<%--            <td>&nbsp;</td>--%>
<%--            <td><button id="submitButton" value="Submit"/></td>--%>
<%--        </tr>--%>

<%--    </table>--%>
<%--    </div>--%>
</form:form>
</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<%--<script src='<spring:url value="/js/imgpreview.js"/>'></script>--%>
<script>

    <c:if test="${updatefail}">
    alert("upload fail")
    </c:if>

    <%--$(document).ready(() => {--%>
    <%--    let image_holder = $("#imgpreview");--%>
    <%--    $("<img/>", {--%>
    <%--        "src": "${pageContext.request.contextPath}/product/image/" + $("#productID").val(),--%>
    <%--        "class": "small-image"--%>
    <%--    }).appendTo(image_holder);--%>
    <%--})--%>

    // $("#imgFile").on('change', () => {
    //     $("#isimagechanged").attr({
    //         value: "true"
    //     })
    // })

    function findInvalidChars(string) {
        let regex = RegExp("^[a-zA-Z0-9_]*$");

        return !regex.test(string);
    }

    function checkPriceValue(p) {
        const regex = /^\d+(.\d{1,2})?$/gm;
        return regex.test(p)
    }

    function findXSSString(string) {
        const regex = /[\<]\w+[\>]/gm;
        return regex.test(string)
    }

    $("#videoTitle").on('keyup change', function (e) {
        let inp = $("#name").val();
        let message = "";
        // if (findInvalidChars(inp)) message += "Invalid input; ";
        if (inp.length < 3) message += "Too Short; ";
        // if (inp.length > 40) message += "Too Long; ";
        $("#nameerror").text(message);
    });

    // $("#price").on('keyup change', function (e) {
    //     let inp = $("#price").val();
    //     let message = "";
    //     if (!checkPriceValue(inp)) message += "Invalid price"
    //     $("#priceerror").text(message);
    // });

    $("#videoDescription").on('keyup change', function (e) {
        let inp = $("#videoDescription").val();
        let message = "";
        if (findXSSString(inp)) message += "Find XSS"
        $("#videoDescription").text(message);
    });

</script>
</html>
