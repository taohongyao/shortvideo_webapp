<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
    <link href='<spring:url value="/css/styles.css"/>' rel="stylesheet"/>
<%--    <script src="https://code.jquery.com/jquery-3.5.0.js"></script>--%>
<%--    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">--%>
<%--    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>--%>
</head>
<body>

<div class="right">
    <div id="output" class="videos-previewer">
    </div>
</div>

</body>

<script>

    $("[data-pid]").click(function () {
        refresh();
    });

    <c:if test="${param.updatesuccessfully == true}">
    alert("update successfully")
    </c:if>

    $(document).ready(() => {
        refresh();
    });

    function isAllDigit(s) {
        let regex = RegExp("^[0-9]+$")
        return regex.test(s)
    }

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
        $('#output').empty();
        if (data.length !== 0) {
            $.each(data, (key, value) => {
                function addLi(prop, propdisplayname, value) {
                    $('<li/>', {
                        'id': 'li_' + prop + '_'.concat(value['videoId']),
                        'html': propdisplayname + ": <lable id='label_"+propdisplayname+'_'+value['videoId']+"'>" + value[prop]+"</lable>"
                    }).appendTo("#" + 'ul_'.concat(value['videoId']));
                }
                $('<ul/>', {
                    'id': 'ul_'.concat(value['videoId']),
                }).appendTo("#output");
                $('<li/>', {
                    'id': 'li_image_'.concat(value['videoId']),
                    'html': '<img class="product-image" src="/video/' + value["videoId"] + '/cover"/>'
                }).appendTo("#" + 'ul_'.concat(value['videoId']));
                addLi('videoTitle', 'title', value);
                addLi('videoDescription', 'description', value);
                // addLi('displayName', 'creater', value);
                $('<li/>', {
                    'html': "creater: <a href='<spring:url value="/account/{id}/page"/>'>{user_name}</a>".replace("{id}", value['userID']).replace("{user_name}", value['displayName'])
                }).appendTo("#" + 'ul_'.concat(value['videoId']));
                addLi('favoriteCounter', 'favorite', value);
                addLi('commentCounter', 'comment', value);
                $('<li/>', {
                    'html': "<a href='<spring:url value="/video/{id}/watch"/>'>watch</a>".replace("{id}", value['videoId'])
                }).appendTo("#" + 'ul_'.concat(value['videoId']));

                $('<i/>',{
                    'class':'material-icons',
                    'style':'color:black;cursor:pointer;',
                    'id':'i_favorite_'+value['videoId'],
                    'html':'favorite_border'
                    }
                ).click({video_id:value['videoId'],ajax_type:'POST'},restFavorite).appendTo("#" + 'ul_'.concat(value['videoId']));
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
            }).appendTo($('#output'))
        }
    };
</script>
</html>