/**
 * Created by claude on 2015/10/15.
 */
function showModal(commentId, contentId, cardId, cardOrder, parentCommentId) {

    $("#myModal").modal('show');
    getCommentContext(commentId, contentId, cardId, cardOrder, parentCommentId);

}

function hideModal() {

    $("#myModal").modal('hide');

}

function getCommentContext(commentId, contentId, cardId, cardOrder, parentCommentId) {
    console.log(commentId);
    console.log(contentId);
    console.log(cardId);
    console.log(parentCommentId);
    $('#commentContext').html('');
    blockObj($('.modal-body'));
    //var src = '';
    //if (cardId == null) {
    //    src = 'http://allets.com/share/' + contentId;
     //} else {
    //    src = 'http://allets.com/share/' + contentId + '/' + cardOrder;
     //}
    //$('#pikicastHtml').attr('src', src);

    $.ajax({
        method: "GET",
        url: "/v1.0/comments/" + commentId,
        headers: {
            "Accept-Language":$.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0,2),
            "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
        },
        data: {
            contentId: contentId,
            cardId: cardId,
            parentCommentId: parentCommentId
        },
        success: function (data, status, jqXHR) {
            showCommentContext(commentId, data.commentResult, contentId, cardOrder, cardId);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $('#commentContext').unblock();
            if (XMLHttpRequest.status == "401") {
                window.location = "/login";
            }
        }
    });
}

function showCommentContext(commentId, commentResult, contentId, cardOrder, cardId) {
    var commentContextHtml = '';

    //contentCardType
    if (cardId != null) {
        $('#alletsUrl').attr('href', 'http://allets.com/share/' + contentId + '/' + cardOrder);
        $('#alletsType').html('CARD  ' + commentResult.contentCardType);
    } else {
        $('#alletsUrl').attr('href', 'http://allets.com/share/' + contentId);
        $('#alletsType').html('CONTENT  ' + commentResult.contentCardType);
    }
    if (commentResult.contentCardType == "TEXT"
        || commentResult.contentCardType == "INTR"
        || commentResult.contentCardType == "SNS_FB"
        || commentResult.contentCardType == "SNS_IS"
        || commentResult.contentCardType == "SNS_TB"
        || commentResult.contentCardType == "SNS_TW") {
        $('#alletsUrl').html('<h5>' + commentResult.contentCardImage +
            '</h5>');
    } else {
        if (window.location.hostname == 'ums.allets.com') {
            $('#alletsUrl').html('<img id="alletsImage" style="width: 380px" src="' +
                'https://s3.ap-northeast-2.amazonaws.com/an2-prd-allets-contents/' + commentResult.contentCardImage +
                '">');
        } else {
            $('#alletsUrl').html('<img id="alletsImage" style="width: 380px" src="' +
                'https://s3.ap-northeast-2.amazonaws.com/an2-prd-allets-contents/' + commentResult.contentCardImage +
                '">');
        }

    }
    if (commentResult.commentId == '') {
        for (var index in commentResult.commentResultList) {
            var commentHtml = '<div class="widget-header';
            if (commentId == commentResult.commentResultList[index].commentId) {
                commentHtml = commentHtml + ' bordered-top-3 bordered-bottom-3 bordered-right-3 bordered-left-3 bordered-red';
            } else {
                commentHtml = commentHtml + ' bordered-top-1 bordered-bottom-1 bordered-right-1 bordered-left-5 bordered-gray';

            }
            commentHtml = commentHtml + '">';
            commentHtml = commentHtml + '<i class="widget-icon fa fa-comment';
            if (commentResult.commentResultList[index].status == 'DEL') {
                commentHtml = commentHtml + ' danger';
            } else if (commentResult.commentResultList[index].status == 'HIDD') {
                commentHtml = commentHtml + ' info';
            }
            commentHtml = commentHtml +
                '"></i>' +
                '<span class="text"><p class="text-left">' +
                commentResult.commentResultList[index].commentText +
                '</p></span></div>';
            commentContextHtml = commentContextHtml + commentHtml;
        }
    } else {
        commentContextHtml = commentContextHtml +
            '<div class="widget-header bordered-top-1 bordered-bottom-1 bordered-right-1 bordered-left-5 bordered-gray">' +
            '<i class="widget-icon fa fa-comment';
        if (commentResult.status == 'DEL') {
            commentContextHtml = commentContextHtml + ' danger';
        } else if (commentResult.status == 'HIDD') {
            commentContextHtml = commentContextHtml + ' info';
        }
        commentContextHtml = commentContextHtml + '"></i>' +
            '<span class="text"><p class="text-left">' +
            commentResult.commentText +
            '</p></span></div>';


        for (var index in commentResult.commentResultList) {
            var commentHtml = '<div class="widget-header ';
            if (commentId == commentResult.commentResultList[index].commentId) {
                commentHtml = commentHtml + ' bordered-top-3 bordered-bottom-3 bordered-right-3 bordered-left-3 bordered-red';
            } else {
                commentHtml = commentHtml + ' bordered-top-1 bordered-bottom-1 bordered-right-1 bordered-left-2 bordered-gray';
            }
            commentHtml = commentHtml + ' margin-left-20"><i class="widget-icon fa fa-comments';
            if (commentResult.commentResultList[index].status == 'DEL') {
                commentHtml = commentHtml + ' danger';
            } else if (commentResult.commentResultList[index].status == 'HIDD') {
                commentHtml = commentHtml + ' info';
            }
            commentHtml = commentHtml + '"></i>' +
                '<span class="text"><p class="text-left">' +
                commentResult.commentResultList[index].commentText +
                '</p></span></div>';
            commentContextHtml = commentContextHtml + commentHtml;
        }

    }
    $('#commentContext').html(commentContextHtml);
    $('.modal-body').unblock();
}