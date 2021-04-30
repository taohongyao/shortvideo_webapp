<%--
  Created by IntelliJ IDEA.
  User: declan
  Date: 4/30/2021
  Time: 1:57 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
    <title>VideoList</title>
</head>
<body>

<div class="video_list" id="video_list">
<%--    <div class="video_block">--%>
<%--        <img class="video_img" src="https://fsb.zobj.net/crop.php?r=gyWMu8lZi2FdHhYoEbXdy7BZrv4bj9jOtQ5_8pNHKdCotxtbb-d8zlYZ21JonBEvRw4-iO6CzAN_SgRQH2enMCJE8iJGCjM-Sd44yBThUi2nq4jiZTF4f9fW047fYKWtso8dA-W52ktsbbfj">--%>
<%--        <div class="video_img_cover"> </div>--%>
<%--        <div class="video_description"> <label>Hello World</label> </div>--%>
<%--        <div class="video_info">--%>
<%--            <div class="video_title">CSYE6220 J2EE web tools and dev</div>--%>
<%--            <div class="video_info_bottom">--%>

<%--                <div class="video_info_i_label_tag">--%>
<%--                    <i class="material-icons">face</i>--%>
<%--                    <label>Declan</label>--%>
<%--                </div>--%>
<%--                <div class="video_info_i_label_tag">--%>

<%--                    <i class="material-icons favorite">favorite</i>--%>
<%--                    <label>0</label>--%>
<%--                </div>--%>

<%--                <div class="video_info_i_label_tag comment">--%>
<%--                    <i class="material-icons">question_answer</i>--%>
<%--                    <label>0</label>--%>
<%--                </div>--%>

<%--            </div>--%>

<%--        </div>--%>
<%--    </div>--%>
</div>
</body>

<script>


    $(document).ready(() => {
        refresh();
    });

    function updateFavoriteIcon(data){
        let video_id=data['shortVideoId'];
        $("#i_favorite_"+video_id).unbind();
        if(data['favorite']){
            $("#i_favorite_"+video_id).html('favorite').removeClass("favorite").addClass("has_favorite");
            $("#i_favorite_"+video_id).click({video_id:video_id,ajax_type:'DELETE'},restFavorite);
        }else {
            $("#i_favorite_"+video_id).html('favorite').removeClass("has_favorite").addClass("favorite");
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
            $("#label_title_"+video_id).html(video_info['videoTitle']);
            $("#label_description_"+video_id).html(video_info['videoDescription']);
            $("#label_favorite_"+video_id).html(video_info['favoriteCounter']);
            $("#label_comment_"+video_id).html(video_info['commentCounter']);
        }
    }

    function refresh() {
        $.post('videos_list',{}, generateProductCallback);
    }

    const generateProductCallback = (data) => {
        let auth = $("#authority").val();
        $('#video_list').empty();
        if (data.length !== 0) {
            $.each(data, (key, value) => {

                $('<div/>',{
                    'class':'video_block',
                    'id':'video_block_'.concat(value['videoId'])
                }).appendTo("#video_list");

                video_a=$('<a/>',{
                    'href':'/video/{id}/watch'.replace("{id}",value['videoId'])
                });

                $('<img/>',{
                    'class':'video_img',
                    'src':'/video/{vid}/cover'.replace("{vid}",value['videoId'])
                }).appendTo(video_a);

                $('<div/>',{
                    'class':'video_img_cover',
                }).appendTo(video_a);

                $('<div/>',{
                    'class':'video_description',
                    'html':'<label id="label_description_{vid}">{description}</label>'.replace("{vid}",value['videoId']).replace("{description}",value['videoDescription'])
                }).appendTo(video_a);

                video_a.appendTo("#video_block_".concat(value['videoId']));

                video_info=$('<div/>',{
                    'class':'video_info'
                });
                video_a=$('<a/>',{
                    'href':'/video/{id}/watch'.replace("{id}",value['videoId'])
                });

                $('<div/>',{
                    'id':'label_title_'.concat(value['videoId']),
                    'class':'video_title',
                    'html':value['videoTitle']
                }).appendTo(video_a);
                video_a.appendTo(video_info);

                video_info_bottom=$('<div/>',{
                    'class':'video_info_bottom'
                });

                creator_a=$('<a/>',{
                    'href':'/account/{id}/page'.replace("{id}",value['userID'])
                });

                $('<div/>',{
                    'class':'video_info_i_label_tag',
                    'html':'<i class="material-icons">face</i><label>{account}</label>'.replace("{account}",value['displayName'])
                }).appendTo(creator_a);
                creator_a.appendTo(video_info_bottom);

                favorite_div=$('<div/>',{
                    'class':'video_info_i_label_tag'
                });

                $('<i/>',{
                        'class':'material-icons favorite',
                        'id':'i_favorite_'+value['videoId'],
                        'html':'favorite'
                    }
                ).click({video_id:value['videoId'],ajax_type:'POST'},restFavorite).appendTo(favorite_div);
                $('<label/>',{
                    'id':"label_favorite_".concat(value['videoId']),
                    'html':value['favoriteCounter']
                }).appendTo(favorite_div);
                favorite_div.appendTo(video_info_bottom);

                $('<div/>',{
                    'class':'video_info_i_label_tag',
                    'html':'<i class="material-icons comment">question_answer</i><label id="label_comment_{vid}" >{counter}</label>'.replace("{counter}",value['commentCounter']).replace("{vid}",value['videoId'])
                }).appendTo(video_info_bottom);
                video_info_bottom.appendTo(video_info);
                video_info.appendTo("#video_block_".concat(value['videoId']));


                if(user_id!=undefined&&user_id!=null){
                    restFavorite(
                        jQuery.Event( "getInfo", {data:{ video_id:value['videoId'],ajax_type:'GET' }} )
                    );
                }
                if (auth === "customer") {

                } else if (auth === "admin" || auth === "manager") {

                }
            });
        } else {
            $('<p/>', {
                'html': "No Such Video"
            }).appendTo($('#video_list'))
        }
    };
</script>

</html>
