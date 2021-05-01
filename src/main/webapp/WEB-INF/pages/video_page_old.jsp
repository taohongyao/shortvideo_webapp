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
<!DOCTYPE html>
<html>
<head>
    <title>Video</title>
</head>
<body>
<jsp:include page="_header.jsp"/>

<h2 id="video_title">${shortVideo.videoTitle}</h2>
<video width="400" controls>
    <source src="/video/${shortVideo.videoId}/file" type="video/mp4">
    Your browser does not support HTML video.
</video>
<br>
<i class="material-icons" id="i_favorite_${shortVideo.videoId}" style="color:black;cursor:pointer"> favorite_border </i>
<br>
<label><strong id="video_description" >Description:</strong></label>
${shortVideo.videoDescription}
<br>
<label><strong>Creater:</strong></label>
<a href="<spring:url value='/account/person/${shortVideo.userID}/'/>">${shortVideo.displayName}</a>
<br>
<label><strong>Create Date:</strong></label>
${shortVideo.createDate}
<br>
<label id="favorite_counter"><strong>Favorite Number:</strong></label>
${shortVideo.favoriteCounter}
<br>
<label><strong>Comments</strong></label>
(<label id="comment_counter">0</label>)
<c:choose>

    <c:when test="${pageContext.request.userPrincipal.name != null}">
        <table>
            <tr>
                <td><label> User: ${pageContext.request.userPrincipal.name}</label></td>
            </tr>
            <tr>
                <td><textarea id="comment_text" rows="8" name="commentContext"></textarea></td>
            </tr>
            <tr>
                <td><button id="comment_btn">Comment</button></td>
            </tr>
        </table>

    </c:when>
</c:choose>

<div class="comment_div">
    <div id="comments" class="comments_list">
    </div>
</div>




</body>

<script>

    $(document).ready(() => {

        $("#comment_btn").click({ajax_type:'POST'},restComment);
        refreshComments();

        let video_id="${shortVideo.videoId}";
        $("#i_favorite_"+video_id).click({video_id:video_id,ajax_type:'POST'},restFavorite)
        if(user_id!=undefined&&user_id!=null){
            restFavorite(
                jQuery.Event( "getInfo", {data:{ video_id:video_id,ajax_type:'GET' }} )
            );
        }
    });

    function updateFavoriteIcon(data){
        let video_id=data['shortVideoId'];
        $("#i_favorite_"+video_id).unbind();
        if(data['favorite']){
            $("#i_favorite_"+video_id).html('favorite').css({color:"red"});
            $("#i_favorite_"+video_id).click({video_id:video_id,ajax_type:'DELETE'},restFavorite);
        }else {
            $("#i_favorite_"+video_id).html('favorite_border').css({color:"black"});
            $("#i_favorite_"+video_id).click({video_id:video_id,ajax_type:'POST'},restFavorite);
        }
    }

    function restFavorite(event){
        let video_id=event.data.video_id;
        let ajax_type=event.data.ajax_type;
        $.ajax({
            url:"/video/"+video_id+"/favorite",
            type:ajax_type,
            contentType: "application/json; charset=utf-8",
            dataType   : "json",
            success    : (data)=>{
                updateFavoriteIcon(data);
                updateFavoriteComponent(data);
            }
        });
    }


    function updateFavoriteComponent(data){
        let video_info=data['videoInfo'];
        if(video_info!=undefined && video_info!=null){
            let video_id=video_info['videoId'];
            $("#video_title").html(video_info['videoTitle']);
            $("#video_description").html(video_info['videoDescription']);
            $("#favorite_counter").html(video_info['favoriteCounter']);
            $("#label_comment_").html(video_info['commentCounter']);
        }
    }


    function refreshComments() {
        $.get('comments',commentsCallback);
    }

    function restComment(event){
        let comment_text=$("textarea#comment_text").val();
        let comment_id=event.data.comment_id;
        let ajax_type=event.data.ajax_type;
        $.ajax({
            url:"comments",
            type:ajax_type,
            data: JSON.stringify({commentId:comment_id,commentContext:comment_text}),
            contentType: "application/json; charset=utf-8",
            dataType   : "json",
            success    : (data)=>{
                refreshComments();
            }
        });
    }

    const commentsCallback = (data) => {
        $('#comments').empty();
        if (data.length !== 0) {
            $("#comment_counter").html(data['comment_counter']);

            $.each(data['list'], (key, value) => {
                $('<div/>', {
                    'class':'comment',
                    'id': 'comment_'.concat(value['commentId'])
                }).appendTo("#comments");

                $('<div/>', {
                    'class':'comment_user',
                    'id': 'user_'.concat(value['userID']),
                    'html':"<strong>{user_display_name}: </strong>".replace("{user_display_name}",value['displayName'])
                }).appendTo("#"+'comment_'.concat(value['commentId']));

                $('<div/>', {
                    'class':'content',
                    'html':value['commentContext']
                }).appendTo("#"+'comment_'.concat(value['commentId']));
                if(value['userID']==user_id){
                    $('<button/>', {
                        'class':'delete_div',
                        'html':'Delete'
                    }).click({comment_id:value['commentId'],ajax_type:'DELETE'},restComment).appendTo("#"+'comment_'.concat(value['commentId']));
                }

            });
        }
    }
</script>
</html>
