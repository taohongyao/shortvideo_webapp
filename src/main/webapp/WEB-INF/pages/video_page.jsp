<%--
  Created by IntelliJ IDEA.
  User: declan
  Date: 4/30/2021
  Time: 6:25 AM
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

<link rel="stylesheet" href="https://cdn.plyr.io/3.6.7/plyr.css" />
<script src="https://cdn.plyr.io/3.6.7/plyr.polyfilled.js"></script>
<div class="video_page_container">

    <div class="player_container">
        <div class="player_video_title">
            <h1>${shortVideo.videoTitle}</h1>
        </div>
        <div class="player_video_info">
            <div class="player_video_info_item">
                <i class="material-icons">favorite</i>
                <lable>Favorite:${shortVideo.favoriteCounter} </lable>
            </div>
            <div class="player_video_info_item">
                <i class="material-icons">comment</i>
                <lable >Comment: <lable id="comment_counter">${shortVideo.commentCounter}</lable></lable>
            </div>

        </div>
        <div class="player_outter">
            <video id="player" class="player">
                <source src="/video/${shortVideo.videoId}/file" type="video/mp4" size="720">
            </video>
        </div>
    </div>

    <div class="cover_info">
        <div class="cover_video_block">
            <img class="cover_video_img" src="/video/${shortVideo.videoId}/cover">
            <div class="cover_video_mid">
                <div class="cover_video_description"> <label>${shortVideo.videoTitle}</label> </div>
                <div class="video_info">
                    <div class="video_title">${shortVideo.videoDescription}</div>
                    <div class="video_info_bottom">

                        <c:choose>
                            <c:when test="${pageContext.request.userPrincipal.name != null}">

                                <div class="video_info_i_label_tag">
                                    <i class="material-icons">face</i>
                                    <label>${pageContext.request.userPrincipal.name}</label>
                                </div>

                            </c:when>
                        </c:choose>


                    </div>
                </div>
                <div class="comment_panel">
                    <div class="comment_area">
                        <textarea id="comment_text" class="comment_send_text" style="width: 100%;" ></textarea>
                    </div>
                    <button id="comment_btn" class="comment_btn">Comment</button>
                </div>
            </div>
        </div>

        <!--		End-->
    </div>
    <div id="comments" class="comments">

    </div>
</div>

</body>
<script>
    $(document).ready(() => {


        refreshComments();

        let video_id="${shortVideo.videoId}";
        $("#i_favorite_"+video_id).click({video_id:video_id,ajax_type:'POST'},restFavorite)
        if(user_id!=undefined&&user_id!=null){
            $("#comment_btn").click({ajax_type:'POST'},restComment);
            restFavorite(
                jQuery.Event( "getInfo", {data:{ video_id:video_id,ajax_type:'GET' }} )
            );
        }else {
            $("#comment_btn").removeClass("comment_btn").addClass("comment_btn_not_login");
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
                    'class':'comment_item',
                    'id': 'comment_'.concat(value['commentId'])
                }).appendTo("#comments");

                div_info= $('<div/>', {
                    'class':'comment_user_info_bar',
                });
                $('<i/>', {
                    'class':'material-icons account_avt_i',
                    'style':'font-size: 50px;',
                    'html':'face'
                }).appendTo(div_info);

                $('<label/>', {
                    'class':'comment_user_info_bar_lable',
                    'html':value['displayName']
                }).appendTo(div_info);

                $('<label/>', {
                    'class':'comment_user_info_bar_lable',
                    'html':value['createDate']
                }).appendTo(div_info);
                if(value['userID']==user_id){
                    $('<i/>', {
                        'class':'material-icons delete_i',
                        'html':'remove_circle'
                    }).click({comment_id:value['commentId'],ajax_type:'DELETE'},restComment).appendTo(div_info);

                }
                div_info.appendTo('#comment_'.concat(value['commentId']));

                $('<div/>', {
                    'class':'comment_text',
                    'html':'<span>{context}</span>'.replace("{context}",value['commentContext'])
                }).appendTo('#comment_'.concat(value['commentId']));

            });
        }
    }

    const player = new Plyr('#player');
    <%--player.source = {--%>
    <%--    type: 'video',--%>
    <%--    title: 'Example title',--%>
    <%--    sources: [--%>
    <%--        {--%>
    <%--            src: '/video/${shortVideo.videoId}/file',--%>
    <%--            type: 'video/mp4',--%>
    <%--            size: 720,--%>
    <%--        }--%>
    <%--    ]--%>
    <%--};--%>

</script>
</html>
