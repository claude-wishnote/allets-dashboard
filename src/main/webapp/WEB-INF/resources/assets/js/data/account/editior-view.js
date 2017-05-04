/**
 * Created by pikicast on 17/5/3.
 */
$(document).ready(function () {
    $('#editorInfoSearchButton').on(
        'click', function () {
            //requestSearchUser('els');
            requestSearchUser();
        }
    );
    $('#editorInfoSearchKeyword').keydown(function (a) {
        if (a.keyCode == 13) {
            //requestSearchUser('els');
            requestSearchUser();
        }
    });
});
function requestSearchUser(searchSource) {
    var field = $('#editorInfoSearchField').val();
    var q = field + '=' + $('#editorInfoSearchKeyword').val();
    if ($('#searchSource').val() != undefined && $('#searchSource').val() != '') {
        if ($.trim(q) != '') {
            q = q + ",";
        }
        q = q + 'searchSource=' + $('#searchSource').val();
        $.cookie("searchSource", $('#searchSource').val());
    }
    if ($('#editorInfoSearchKeyword').val() == '') {
        $('#searchTip').html('<a>please input something</s>');
        return;
    }
    blockObj($('div.page-content'));

    $.ajax({
        method: 'GET',
        url: '/v1.0/users',
        headers: {
            "Accept-Language": $.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0, 2),
            "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
        },
        data: {action: 'ALLSIMPLE', q: encodeURI(q)},
        success: function (data, status, jqXHR) {
            $('div.page-content').unblock();
            if (data.pageImpl.content != undefined && data.pageImpl.content != null) {
                searchResult = data.pageImpl.content;
            }
            else {
                searchResult = '';
            }
            refreshSearchResultDropDown();
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            //TODO: handle other status
            searchResult = '';
            selectUid = '';
            selectIndex = '';
            refreshSearchResultDropDown();
            $('div.page-content').unblock();
            if (XMLHttpRequest.status == '401') {
                window.location = '/login';
            }
        }
    });
}

function refreshSearchResultDropDown() {
    var dropDownHtml = '';
    if (searchResult == '') {
        dropDownHtml = '<li id=\'searchTip\'><a>no result.please search again. </a></li>';
        $('#searchResultDropDown').html(dropDownHtml)
        return;
    }
    for (var x in searchResult) {
        if ($('#editorInfoSearchField').val() == 'nickName') {
            if (searchResult[x].name == $('#editorInfoSearchKeyword').val()) {
                dropDownHtml = dropDownHtml + '<li value=' + x + ' class=\'label label-green col-lg-12\'><a class=\'col-md-12\'>' +
                    '<i class=\'col-md-3 danger\'>' + searchResult[x].name + '</i>' +
                    '<i class=\'col-md-6\'>' + searchResult[x].email + '</i>' +
                    '<i class=\'col-md-3\'>' + searchResult[x].uid + '</i>' +
                    '</a></li>';
            } else {
                dropDownHtml = dropDownHtml + '<li value=' + x + ' class=\'col-lg-12\'><a class=\'col-md-12\'>' +
                    '<i class=\'col-md-3 danger\'>' + searchResult[x].name + '</i>' +
                    '<i class=\'col-md-6\'>' + searchResult[x].email + '</i>' +
                    '<i class=\'col-md-3\'>' + searchResult[x].uid + '</i>' +
                    '</a></li>';
            }
        } else if ($('#editorInfoSearchField').val() == 'email') {
            if (searchResult[x].email == $('#editorInfoSearchKeyword').val()) {
                dropDownHtml = dropDownHtml + '<li value=' + x + ' class=\'label label-green col-lg-12\'><a class=\'col-md-12\'>' +
                    '<i class=\'col-md-6 danger\'>' + searchResult[x].email + '</i>' +
                    '<i class=\'col-md-3\'>' + searchResult[x].name + '</i>' +
                    '<i class=\'col-md-3\'>' + searchResult[x].uid + '</i>' +
                    '</a></li>';
            } else {
                dropDownHtml = dropDownHtml + '<li value=' + x + ' class=\'col-lg-12\'><a class=\'col-md-12\'>' +
                    '<i class=\'col-md-6 danger\'>' + searchResult[x].email + '</i>' +
                    '<i class=\'col-md-3\'>' + searchResult[x].name + '</i>' +
                    '<i class=\'col-md-3\'>' + searchResult[x].uid + '</i>' +
                    '</a></li>';
            }
        } else if ($('#editorInfoSearchField').val() == 'uid') {
            if (searchResult[x].uid == $('#editorInfoSearchKeyword').val()) {
                dropDownHtml = dropDownHtml + '<li value=' + x + ' class=\'label label-green col-lg-12\'><a class=\'col-md-12\'>' +
                    '<i class=\'col-md-3 danger\'>' + searchResult[x].uid + '</i>' +
                    '<i class=\'col-md-3\'>' + searchResult[x].name + '</i>' +
                    '<i class=\'col-md-6\'>' + searchResult[x].email + '</i>' +
                    '</a></li>';
            } else {
                dropDownHtml = dropDownHtml + '<li value=' + x + ' class=\'col-lg-12\'><a class=\'col-md-12\'>' +
                    '<i class=\'col-md-3 danger\'>' + searchResult[x].uid + '</i>' +
                    '<i class=\'col-md-3\'>' + searchResult[x].name + '</i>' +
                    '<i class=\'col-md-6\'>' + searchResult[x].email + '</i>' +
                    '</a></li>';
            }
        }
    }
    $('#searchResultDropDown').html(dropDownHtml);
    $('#searchResultDropDown li').on('click', function () {
            if (searchResult != undefined && searchResult != null) {
                selectUid = searchResult[$(this).val()].uid;
                selectIndex = $(this).val();
                if ($('#editorInfoSearchField').val() == 'nickName') {
                    $('#editorInfoSearchKeyword').val(searchResult[$(this).val()].name);
                } else if ($('#editorInfoSearchField').val() == 'email') {
                    $('#editorInfoSearchKeyword').val(searchResult[$(this).val()].email);
                } else if ($('#editorInfoSearchField').val() == 'uid') {
                    $('#editorInfoSearchKeyword').val(searchResult[$(this).val()].uid);
                }
                requestUserDataByUid(searchResult[$(this).val()].uid);
                refreshSearchResultDropDown();
            }
        }
    );

    $('#editorInfoSearchKeyword').parent().addClass('open');
    $('#editorInfoSearchKeyword').attr('aria-expanded', 'true');
}

function requestUserDataByUid(uid) {
    blockObj($('div.page-content'));
    var q = 'uid=' + uid;
    $.ajax({
        method: 'GET',
        url: '/v1.0/users',
        headers: {
            "Accept-Language": $.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0, 2),
            "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
        },
        data: {action: 'ALL', q: encodeURI(q)},
        success: function (data, status, jqXHR) {
            $('div.page-content').unblock();
            if (data.pageImpl.content != undefined && data.pageImpl.content != null) {
                freshEditorInfoData(data.pageImpl.content[0]);
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            //TODO: handle other status
            selectUid = '';
            selectIndex = '';
            selectUser = '';
            //fresheditorInfoData();
            $('div.page-content').unblock();
            if (XMLHttpRequest.status == '401') {
                window.location = '/login';
            }
        }
    });
}

function freshEditorInfoData(data)
{
    console.log(data);
    $('.profile-img').attr('src',"https://s3.ap-northeast-2.amazonaws.com/an2-prd-allets-contents/"+data.photo);
    $('#name').html(data.name);
    $('#introMessage').html(data.introMessage);
    $('#followerQuantity').html(data.subscriberCount);
    $('#commentQuantity').html(data.commentCount);
    $('#contentQuantity').html(data.contentsCount);
    $('#bookmarkQuantity').html(data.bookMarkCount);
    $('#likeQuantity').html(data.likeCount);
}