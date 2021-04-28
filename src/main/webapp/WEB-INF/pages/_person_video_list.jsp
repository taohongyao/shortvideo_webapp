<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
    <link href='<spring:url value="/css/styles.css"/>' rel="stylesheet"/>
    <script src="https://code.jquery.com/jquery-3.5.0.js"></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
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


    // $.get("product/getproductnamelist", (res) => {
    //         productNames = res;
    //
    //         $("#productName").autocomplete({
    //             source: productNames
    //         })
    //     }
    // )

    // $('#search').click(() => {
    //     $.getJSON('product/searchbyproductname', {name: $("#productName").val()}, generateProductCallback);
    // });

    $(document).ready(() => {
        refresh();
    });

    function isAllDigit(s) {
        let regex = RegExp("^[0-9]+$")
        return regex.test(s)
    }

    // function addToCart(val) {
    //     let productID = $(val).attr('value');
    //     let quantity = $("#number_" + productID).val();
    //     if (!isAllDigit(quantity))
    //         alert("input error")
    //     else {
    //         $.ajax({
    //             url: "customer/addtocart",
    //             data: {
    //                 productID,
    //                 quantity
    //             },
    //             success: (res) => {
    //                 if (res) alert("add successfully")
    //                 else alert("add fail");
    //             },
    //
    //         });
    //     }
    //     $('#number_' + productID).val("");
    // }


    function refresh() {
        $.post('videos_list',{}, generateProductCallback);
    }

    const generateProductCallback = (data) => {
        let auth = $("#authority").val();
        console.log(auth)
        console.log(data)
        $('#output').empty();
        if (data.length !== 0) {
            $.each(data, (key, value) => {
                function addLi(prop, propdisplayname, value) {
                    $('<li/>', {
                        'id': 'li_' + prop + '_'.concat(value['videoId']),
                        'html': propdisplayname + ": " + value[prop]
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
                    'html': "creater: <a href='<spring:url value="/account/person/{id}/"/>'>{user_name}</a>".replace("{id}", value['userID']).replace("{user_name}", value['displayName'])
                }).appendTo("#" + 'ul_'.concat(value['videoId']));
                addLi('favoriteCounter', 'favorite', value);
                addLi('commentCounter', 'comment', value);
                $('<li/>', {
                    'html': "<a href='<spring:url value="/video/{id}/watch"/>'>watch</a>".replace("{id}", value['videoId'])
                }).appendTo("#" + 'ul_'.concat(value['videoId']));
                if (auth === "customer") {

                    // $('<li/>', {
                    //     'html': "<input required id='number_" + value['videoId'] + "' type=number min='0' step='1'/>" +
                    //         "<button onclick='addToCart(this)'  type='button' value=" + value['videoId'] + ">add</button>"
                    // }).appendTo("#" + 'ul_'.concat(value['videoId']));
                } else if (auth === "admin" || auth === "manager") {
                    addLi('lastModifiedDate', 'last modified time', value);
                    $('<li/>', {
                        'html': "<a href='<spring:url value="/video/cover/{id}"/>'>manage</a>".replace("{id}", value['videoId'])
                    }).appendTo("#" + 'ul_'.concat(value['videoId']));
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