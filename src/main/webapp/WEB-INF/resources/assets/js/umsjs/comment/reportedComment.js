/**
 * Created by claude on 2015/8/20.
 */
var reportedCommentsPageCount = 50;
var currentPage = 1;
var t = null;
var tableData;
var selectedItem;
var userSearchResult;
var selectUid = '';
var selectIndex = '';
var searchStyle = 'elsInitWithUserSearch';//'elsInit','mysqlInit','elsInitWithUserSearch'
/*
 * init method
 */
$(window).resize(function () {
    $('.page-body').css('height', $(window).height() - 85);
});
$(document).ready(function () {
    $('.page-body').css('height', $(window).height() - 85);
    //for test
    selectedItem = {};
    //refreshTable(0, $('#reportedCommentLimit').val(), '', '', fnInfoCallback, footerCallback, fnCreatedRow,fnInitComplete);
    //reportedCommentsPageCount = $('#reportedCommentLimit').val();
    $('#reportedCommentStartDatePicker').datepicker({
        format: 'yyyy-mm-dd',
    });
    $('#reportedCommentEndDatePicker').datepicker({
        format: 'yyyy-mm-dd',
    })
    $('#reportedCommentStartDatePicker').on(
        'focus', function () {
            $(this).blur();
        }
    );
    $('#reportedCommentEndDatePicker').on(
        'focus', function () {
            $(this).blur();
        }
    );
    var now = new Date();
    var beforeMil = now.getTime();
    var before = new Date(beforeMil - 86400000 * 29);
    $('#reportedCommentEndDatePicker').val(now.getFullYear() + '-' + (now.getMonth() + 1) + '-' + now.getDate());
    $('#reportedCommentStartDatePicker').val(before.getFullYear() + '-' + (before.getMonth() + 1) + '-' + before.getDate());
    $('#reportedCommentPeriodDays').val(30);
    $('#reportedCommentPeriodDays').change(function () {
        var now = new Date();
        var beforeMil = now.getTime() - ( $('#reportedCommentPeriodDays').val() - 1) * 86400000;
        var before = new Date(beforeMil);
        $('#reportedCommentEndDatePicker').val(now.getFullYear() + '-' + (now.getMonth() + 1) + '-' + now.getDate());
        $('#reportedCommentStartDatePicker').val(before.getFullYear() + '-' + (before.getMonth() + 1) + '-' + before.getDate());
    });
    $('#passAllComment').on('click', function (e) {
        totalHandleButtonClick('PASS');
    });
    $('#blindAllComment').on('click', function (e) {
        totalHandleButtonClick('HIDD');
    });
    $('#deleteAllComment').on('click', function (e) {
        totalHandleButtonClick('DEL');
    });
    if (searchStyle == 'elsInitWithUserSearch') {
        elsInitWithUserSearch();
    } else if (searchStyle == 'elsInit') {
        elsInit();
    } else {
        mysqlInit();
    }

})
function elsInitWithUserSearch() {
    searchStyle = 'elsInitWithUserSearch';
    $('#userSearchKeyword').show();
    $('#reportedCommentSearchKeyword').hide();
    $('#userSearchKeyword').parent().addClass('open');
    $('#userSearchKeyword').attr('aria-expanded', 'true');
    $('#searchSource').hide();
    $('#reportedCommentSearchField').change(
        function () {
            if ($('#reportedCommentSearchField').val() == 'nickName' || $('#reportedCommentSearchField').val() == 'email') {
                $('#userSearchKeyword').show();
                $('#reportedCommentSearchKeyword').hide();
                //$('#searchSource').attr('disabled', false);
                //$('#searchSource').show();
                $('#searchSource').hide();
            } else if ($('#reportedCommentSearchField').val() == 'keyword') {
                $('#userSearchKeyword').hide();
                $('#reportedCommentSearchKeyword').show();
                //$('#searchSource').attr('disabled', true);
                $('#searchSource').hide();
            }
        }
    );
    $('#initSearchButton').on(
        'click', function () {
            var now = new Date();
            var beforeMil = now.getTime();
            var before = new Date(beforeMil);
            //$('#allCommentEndDatePicker').val('');
            //$('#allCommentStartDatePicker').val('');
            userSearchResult = '';
            selectUid = '';
            selectIndex = '';
            $('#userSearchKeyword').val('');
            var dropDownHtml = '<li id=\'searchTip\' value=\'1\'><a>' + +$.i18n.prop("ums.report.comment.searchfield.click") + '<i class=\'btn btn-sm glyphicon glyphicon-search blue\' ></i>'
                + $.i18n.prop("ums.report.comment.searchfield.placeholder") + '</a></li>';
            $('#searchResultDropDown').html(dropDownHtml)
            //$('#reportedCommentSearchField').val('nickName');
            $('#reportedCommentSearchKeyword').val('');
            $('#reportedCommentPeriodDays').val(30);
        }
    );
    $('#reportedCommentLimit').change(function () {
        if (reportedCommentsPageCount != $('#reportedCommentLimit').val()) {
            reportedCommentsPageCount = $('#reportedCommentLimit').val();
            if ($('#reportedCommentSearchField').val() == 'nickName' || $('#reportedCommentSearchField').val() == 'email') {
                //if there is uid result.use uid!
                if (selectUid != '') {
                    getTableData("REPORTED", 0, $('#reportedCommentLimit').val(), 'uid', selectUid, '', '');
                } else {
                    getTableData("REPORTED", 0, $('#reportedCommentLimit').val(), '', '', '', '','els');
                }
            } else {
                getTableData("REPORTED", 0, $('#reportedCommentLimit').val(), $('#reportedCommentSearchField').val(), $('#reportedCommentSearchKeyword').val(), '', '','els');
            }
        }
    });
    $('#searchButton').on(
        'click', function () {
            if ($('#reportedCommentSearchField').val() == 'nickName' || $('#reportedCommentSearchField').val() == 'email') {
                //if there is uid result.use uid!
                if (selectUid != '') {
                    getTableData("REPORTED", 0, $('#reportedCommentLimit').val(), 'uid', selectUid, '', '');
                } else {
                    getTableData("REPORTED", 0, $('#reportedCommentLimit').val(), '', '', '', '','els');
                }
            } else {
                getTableData("REPORTED", 0, $('#reportedCommentLimit').val(), $('#reportedCommentSearchField').val(), $('#reportedCommentSearchKeyword').val(), '', '','els');
            }
        }
    );
    $('#reportedCommentSearchButton').on('click', function (e) {
        if ($('#reportedCommentSearchField').val() == 'nickName' || $('#reportedCommentSearchField').val() == 'email') {
            //if (selectUid == '') {
            if ($('#userSearchKeyword').val() == '') {
                getTableData("REPORTED", 0, $('#reportedCommentLimit').val(), '', '', '', '','els');
            } else {
                //requestSearchUser('els');
                requestSearchUser();
            }
        } else {
            getTableData("REPORTED", 0, $('#reportedCommentLimit').val(), $('#reportedCommentSearchField').val(), $('#reportedCommentSearchKeyword').val(), '', '','els');
        }
    });
    $('#reportedCommentSearchKeyword').keydown(function (a) {
        if (a.keyCode == 13) {
            if ($('#reportedCommentSearchField').val() == 'nickName' || $('#reportedCommentSearchField').val() == 'email') {
                //requestSearchUser('els');
                requestSearchUser();
            } else {
                getTableData("REPORTED", 0, $('#reportedCommentLimit').val(), $('#reportedCommentSearchField').val(), $('#reportedCommentSearchKeyword').val(), '', '','els');
            }
        }
    });
    $('#userSearchKeyword').keydown(function (a) {
        if (a.keyCode == 13) {
            if ($('#reportedCommentSearchField').val() == 'nickName' || $('#reportedCommentSearchField').val() == 'email') {
                //if (selectUid == '') {
                if ($('#userSearchKeyword').val() == '') {
                    getTableData("REPORTED", 0, $('#reportedCommentLimit').val(), '', '', '', '','els');
                } else {
                    //requestSearchUser('els');
                    requestSearchUser();
                }
            } else {
                getTableData("REPORTED", 0, $('#reportedCommentLimit').val(), '', '', '', '','els');
            }
            //}
        }
    });
    $('#userSearchKeyword').on('input propertychange',
        function () {
            selectUid = '';
            selectIndex = '';
            console.log('userSearchKeyword value change');
        }
    );
    $('#refreshButton').on('click', function () {
        if ($('#reportedCommentSearchField').val() == 'nickName' || $('#reportedCommentSearchField').val() == 'email') {
            //if there is uid result.use uid!
            if (selectUid != '') {
                getTableData("REPORTED", $('#reportedCommentLimit').val() * (currentPage - 1), $('#reportedCommentLimit').val(), 'uid',selectUid, '', '');
            } else {
                getTableData("REPORTED", $('#reportedCommentLimit').val() * (currentPage - 1), $('#reportedCommentLimit').val(), '', '', '', '','els');
            }
        } else {
            getTableData("REPORTED", $('#reportedCommentLimit').val() * (currentPage - 1), $('#reportedCommentLimit').val(), $('#reportedCommentSearchField').val(), $('#reportedCommentSearchKeyword').val(), '', '','els');
        }
    });
    if ($.cookie("searchSource") && ($.cookie("searchSource") == 'els' || $.cookie("searchSource") == 'mysql')) {
        $('#searchSource').val($.cookie("searchSource"));
    } else {
        $('#searchSource').val('mysql');
        $.cookie("searchSource", 'mysql');
    }
    $('#searchSource').on('change', function (e) {
        $.cookie("searchSource", $('#searchSource').val());
    });

    getTableData("REPORTED", 0, $('#reportedCommentLimit').val(), '', '', '', '','els');
}
function elsInit() {
    searchStyle = 'elsInit';
    $('#userSearchKeyword').hide();
    $('#searchResultDropDown').hide();
    $('#searchSource').hide();
    $('#reportedCommentSearchKeyword').show();
    $('#initSearchButton').on(
        'click', function () {
            var now = new Date();
            var beforeMil = now.getTime();
            var before = new Date(beforeMil);
            //$('#allCommentEndDatePicker').val('');
            //$('#allCommentStartDatePicker').val('');
            $('#reportedCommentSearchField').val('nickName');
            $('#reportedCommentSearchKeyword').val('');
            $('#reportedCommentPeriodDays').val(30);
        }
    );
    $('#reportedCommentLimit').change(function () {
        if (reportedCommentsPageCount != $('#reportedCommentLimit').val()) {
            reportedCommentsPageCount = $('#reportedCommentLimit').val();
            getTableData("REPORTED", 0, $('#reportedCommentLimit').val(), $('#reportedCommentSearchField').val(), $('#reportedCommentSearchKeyword').val(), '', '','els');
        }
    });
    $('#searchButton').on(
        'click', function () {
            getTableData("REPORTED", 0, $('#reportedCommentLimit').val(), $('#reportedCommentSearchField').val(), $('#reportedCommentSearchKeyword').val(), '', '','els');
        }
    );
    $('#reportedCommentSearchButton').on('click', function (e) {
        getTableData("REPORTED", 0, $('#reportedCommentLimit').val(), $('#reportedCommentSearchField').val(), $('#reportedCommentSearchKeyword').val(), '', '','els');
    });
    $('#reportedCommentSearchKeyword').keydown(function (a) {
        if (a.keyCode == 13) {
            getTableData("REPORTED", 0, $('#reportedCommentLimit').val(), $('#reportedCommentSearchField').val(), $('#reportedCommentSearchKeyword').val(), '', '','els');
        }
    });
    $('#refreshButton').on('click', function () {
        getTableData("REPORTED", $('#reportedCommentLimit').val() * (currentPage - 1), $('#reportedCommentLimit').val(), $('#reportedCommentSearchField').val(), $('#reportedCommentSearchKeyword').val(), '', '','els');

    });
    getTableData("REPORTED", 0, $('#reportedCommentLimit').val(), '', '', '', '','els');
}
function mysqlInit() {
    searchStyle = 'mysqlInit';
    $('#reportedCommentSearchKeyword').hide();
    $('#searchSource').hide();
    $('#reportedCommentSearchField').change(
        function () {
            if ($('#reportedCommentSearchField').val() == 'nickName' || $('#reportedCommentSearchField').val() == 'email') {
                $('#userSearchKeyword').show();
                $('#reportedCommentSearchKeyword').hide();
            } else if ($('#reportedCommentSearchField').val() == 'keyword') {
                $('#userSearchKeyword').hide();
                $('#reportedCommentSearchKeyword').show();
            }
        }
    );
    $('#initSearchButton').on(
        'click', function () {
            var now = new Date();
            var beforeMil = now.getTime();
            var before = new Date(beforeMil);
            //$('#allCommentEndDatePicker').val('');
            //$('#allCommentStartDatePicker').val('');
            userSearchResult = '';
            selectUid = '';
            selectIndex = '';
            $('#userSearchKeyword').val('');
            var dropDownHtml = '<li id=\'searchTip\' value=\'1\'><a>' + +$.i18n.prop("ums.report.comment.searchfield.click") + '<i class=\'btn btn-sm glyphicon glyphicon-search blue\' ></i>'
                + $.i18n.prop("ums.report.comment.searchfield.placeholder") + '</a></li>';
            $('#searchResultDropDown').html(dropDownHtml)
            $('#reportedCommentSearchField').val('nickName');
            $('#reportedCommentSearchKeyword').val('');
            $('#reportedCommentPeriodDays').val(30);
        }
    );
    $('#reportedCommentLimit').change(function () {
        if (reportedCommentsPageCount != $('#reportedCommentLimit').val()) {
            reportedCommentsPageCount = $('#reportedCommentLimit').val();
            if ($('#reportedCommentSearchField').val() == 'nickName' || $('#reportedCommentSearchField').val() == 'email') {
                //if there is uid result.use uid!
                console.log('searchButton 00000');
                if (selectUid != '') {
                    getTableData("REPORTED", 0, $('#reportedCommentLimit').val(), 'uid', selectUid, $('#reportedCommentStartDatePicker').val(), $('#reportedCommentEndDatePicker').val());
                } else {
                    getTableData("REPORTED", 0, $('#reportedCommentLimit').val(), '', '', $('#reportedCommentStartDatePicker').val(), $('#reportedCommentEndDatePicker').val());
                }
            } else {
                getTableData("REPORTED", 0, $('#reportedCommentLimit').val(), $('#reportedCommentSearchField').val(), $('#reportedCommentSearchKeyword').val(), $('#reportedCommentStartDatePicker').val(), $('#reportedCommentEndDatePicker').val());
            }
        }
    });
    $('#searchButton').on(
        'click', function () {
            if ($('#reportedCommentSearchField').val() == 'nickName' || $('#reportedCommentSearchField').val() == 'email') {
                //if there is uid result.use uid!
                if (selectUid != '') {
                    getTableData("REPORTED", 0, $('#reportedCommentLimit').val(), 'uid', selectUid, $('#reportedCommentStartDatePicker').val(), $('#reportedCommentEndDatePicker').val());
                } else {
                    getTableData("REPORTED", 0, $('#reportedCommentLimit').val(), '', '', $('#reportedCommentStartDatePicker').val(), $('#reportedCommentEndDatePicker').val());
                }
            } else {
                getTableData("REPORTED", 0, $('#reportedCommentLimit').val(), $('#reportedCommentSearchField').val(), $('#reportedCommentSearchKeyword').val(), $('#reportedCommentStartDatePicker').val(), $('#reportedCommentEndDatePicker').val());
            }
        }
    );
    $('#reportedCommentSearchButton').on('click', function (e) {
        if ($('#reportedCommentSearchField').val() == 'nickName' || $('#reportedCommentSearchField').val() == 'email') {
            //requestSearchUser('els');
            requestSearchUser();
        } else {
            getTableData("REPORTED", 0, $('#reportedCommentLimit').val(), $('#reportedCommentSearchField').val(), $('#reportedCommentSearchKeyword').val(), $('#reportedCommentStartDatePicker').val(), $('#reportedCommentEndDatePicker').val());
        }
    });
    $('#reportedCommentSearchKeyword').keydown(function (a) {
        if (a.keyCode == 13) {
            if ($('#reportedCommentSearchField').val() == 'nickName' || $('#reportedCommentSearchField').val() == 'email') {
                //requestSearchUser('els');
                requestSearchUser();
            } else {
                getTableData("REPORTED", 0, $('#reportedCommentLimit').val(), $('#reportedCommentSearchField').val(), $('#reportedCommentSearchKeyword').val(), $('#reportedCommentStartDatePicker').val(), $('#reportedCommentEndDatePicker').val());
            }
        }
    });
    $('#userSearchKeyword').keydown(function (a) {
        if (a.keyCode == 13) {
            if ($('#reportedCommentSearchField').val() == 'nickName' || $('#reportedCommentSearchField').val() == 'email') {
                //if (selectUid == '') {
                if ($('#userSearchKeyword').val() == '') {
                    getTableData("REPORTED", 0, $('#reportedCommentLimit').val(), '', '', $('#reportedCommentStartDatePicker').val(), $('#reportedCommentEndDatePicker').val());
                } else {
                    //requestSearchUser('els');
                    requestSearchUser();
                }
            } else {
                getTableData("REPORTED", 0, $('#reportedCommentLimit').val(), '', '', $('#reportedCommentStartDatePicker').val(), $('#reportedCommentEndDatePicker').val());
            }
            //}
        }
    });
    $('#userSearchKeyword').on('input propertychange',
        function () {
            selectUid = '';
            selectIndex = '';
        }
    );
    $('#refreshButton').on('click', function () {
        if ($('#reportedCommentSearchField').val() == 'nickName' || $('#reportedCommentSearchField').val() == 'email') {
            //if there is uid result.use uid!
            if (selectUid != '') {
                getTableData("REPORTED", $('#reportedCommentLimit').val() * (currentPage - 1), $('#reportedCommentLimit').val(), 'uid', selectUid, $('#reportedCommentStartDatePicker').val(), $('#reportedCommentEndDatePicker').val());
            } else {
                getTableData("REPORTED", $('#reportedCommentLimit').val() * (currentPage - 1), $('#reportedCommentLimit').val(), '', '', $('#reportedCommentStartDatePicker').val(), $('#reportedCommentEndDatePicker').val());
            }
        } else {
            getTableData("REPORTED", $('#reportedCommentLimit').val() * (currentPage - 1), $('#reportedCommentLimit').val(), $('#reportedCommentSearchField').val(), $('#reportedCommentSearchKeyword').val(), $('#reportedCommentStartDatePicker').val(), $('#reportedCommentEndDatePicker').val());
        }
    });
    getTableData("REPORTED", 0, $('#reportedCommentLimit').val(), '', '', $('#reportedCommentStartDatePicker').val(), $('#reportedCommentEndDatePicker').val());

}
/*
 search user before search comment,provide uid
 */
function requestSearchUser(searchSource) {
    var field = $('#reportedCommentSearchField').val();
    var q = field + '=' + $('#userSearchKeyword').val();
    if ($('#searchSource').val() != undefined && $('#searchSource').val() != '') {
        if ($.trim(q) != '') {
            q = q + ",";
        }
        q = q + 'searchSource=' + $('#searchSource').val();
        $.cookie("searchSource", $('#searchSource').val());
    }

    if ($('#userSearchKeyword').val() == '') {
        $('#searchTip').html('<a>please input something</s>');
        return;
    }
    blockObj($('div.page-content'));

    $.ajax({
        method: 'GET',
        url: '/v1.0/users',
        headers: {
            "Accept-Language":$.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0,2),
            "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
        },
        data: {action: 'ALLSIMPLE', q: encodeURI(q)},
        success: function (data, status, jqXHR) {
            $('div.page-content').unblock();
            if (data.pageImpl.content != undefined && data.pageImpl.content != null) {
                userSearchResult = data.pageImpl.content;
            }
            else {
                userSearchResult = '';
            }
            refreshUserSearchResultDropDown(userSearchResult);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            //TODO: handle other status
            userSearchResult = '';
            selectUid = '';
            selectIndex = '';
            refreshUserSearchResultDropDown(userSearchResult);
            $('div.page-content').unblock();
            if (XMLHttpRequest.status == '401') {
                window.location = '/login';
            }
        }
    });

}

function refreshUserSearchResultDropDown(searchResult) {
    var dropDownHtml = '';
    if (searchResult == '') {
        dropDownHtml = '<li id=\'searchTip\'><a>no result.please search again. </a></li>';
        $('#searchResultDropDown').html(dropDownHtml)
        return;
    }
    for (var x in searchResult) {
        if ($('#reportedCommentSearchField').val() == 'nickName') {
            if (searchResult[x].name == $('#userSearchKeyword').val()) {
                if (searchResult[x].uid == selectUid) {
                    dropDownHtml = dropDownHtml + '<li value=' + x + ' class=\'label label-green col-lg-12  bordered-top-2 bordered-bottom-2 bordered-right-2 bordered-left-2 bordered-red\' ><a class=\'col-md-12 \'>' +
                        '<i class=\'col-md-1 glyphicon glyphicon-star text-left white\'></i>' +
                        '<i class=\'col-md-3 danger\'>' + searchResult[x].name + '</i>' +
                        '<i class=\'col-md-5\'>' + searchResult[x].email + '</i>' +
                        '<i class=\'col-md-3\'>' + searchResult[x].uid + '</i>' +
                        '</a></li>';
                } else {
                    dropDownHtml = dropDownHtml + '<li value=' + x + ' class=\'label label-green col-lg-12\'><a class=\'col-md-12\'>' +
                        '<i class=\'col-md-3 danger\'>' + searchResult[x].name + '</i>' +
                        '<i class=\'col-md-6\'>' + searchResult[x].email + '</i>' +
                        '<i class=\'col-md-3\'>' + searchResult[x].uid + '</i>' +
                        '</a></li>';
                }
            } else {
                dropDownHtml = dropDownHtml + '<li value=' + x + ' class=\'col-lg-12\'><a class=\'col-md-12\'>' +
                    '<i class=\'col-md-3 danger\'>' + searchResult[x].name + '</i>' +
                    '<i class=\'col-md-6\'>' + searchResult[x].email + '</i>' +
                    '<i class=\'col-md-3\'>' + searchResult[x].uid + '</i>' +
                    '</a></li>';
            }
        } else if ($('#reportedCommentSearchField').val() == 'email') {
            if (searchResult[x].email == $('#userSearchKeyword').val()) {
                if (searchResult[x].uid == selectUid) {
                    dropDownHtml = dropDownHtml + '<li value=' + x + ' class=\'label label-green col-lg-12  bordered-top-2 bordered-bottom-2 bordered-right-2 bordered-left-2 bordered-red\'><a class=\'col-md-12\'>' +
                        '<i class=\'col-md-1 glyphicon glyphicon-star text-left white\'></i>' +
                        '<i class=\'col-md-5 danger\'>' + searchResult[x].email + '</i>' +
                        '<i class=\'col-md-3\'>' + searchResult[x].name + '</i>' +
                        '<i class=\'col-md-3\'>' + searchResult[x].uid + '</i>' +
                        '</a></li>';
                } else {
                    dropDownHtml = dropDownHtml + '<li value=' + x + ' class=\'label label-green col-lg-12\'><a class=\'col-md-12\'>' +
                        '<i class=\'col-md-6 danger\'>' + searchResult[x].email + '</i>' +
                        '<i class=\'col-md-3\'>' + searchResult[x].name + '</i>' +
                        '<i class=\'col-md-3\'>' + searchResult[x].uid + '</i>' +
                        '</a></li>';
                }
            } else {
                dropDownHtml = dropDownHtml + '<li value=' + x + ' class=\'col-lg-12\'><a class=\'col-md-12\'>' +
                    '<i class=\'col-md-6 danger\'>' + searchResult[x].email + '</i>' +
                    '<i class=\'col-md-3\'>' + searchResult[x].name + '</i>' +
                    '<i class=\'col-md-3\'>' + searchResult[x].uid + '</i>' +
                    '</a></li>';
            }
        }
    }
    $('#searchResultDropDown').html(dropDownHtml);
    $('#searchResultDropDown li').on('click', function () {
            if (searchResult != undefined && searchResult != null) {
                if ($('#reportedCommentSearchField').val() == 'nickName') {
                    $('#userSearchKeyword').val(searchResult[$(this).val()].name);
                } else if ($('#reportedCommentSearchField').val() == 'email') {
                    $('#userSearchKeyword').val(searchResult[$(this).val()].email);
                }
                selectUid = searchResult[$(this).val()].uid;
                selectIndex = $(this).val();
                refreshUserSearchResultDropDown(searchResult);
                if (searchStyle == 'elsInitWithUserSearch') {
                    if ($('#reportedCommentSearchField').val() == 'nickName' || $('#reportedCommentSearchField').val() == 'email') {
                        getTableData("REPORTED", 0, $('#reportedCommentLimit').val(), 'uid', selectUid, '', '');
                    } else {
                        getTableData("REPORTED", 0, $('#reportedCommentLimit').val(), $('#reportedCommentSearchField').val(), $('#reportedCommentSearchKeyword').val(), '', '','els');
                    }
                } else if (searchStyle == 'elsInit') {
                    getTableData("REPORTED", 0, $('#reportedCommentLimit').val(), $('#reportedCommentSearchField').val(), $('#reportedCommentSearchKeyword').val(), '', '','els');
                }
                else {
                    getTableData("REPORTED", 0, $('#reportedCommentLimit').val(), 'uid', selectUid, $('#reportedCommentStartDatePicker').val(), $('#reportedCommentEndDatePicker').val());
                }
            }
        }
    );

    $('#userSearchKeyword').parent().addClass('open');
    $('#userSearchKeyword').attr('aria-expanded', 'true');
}

function hidePopover() {
    $('[data-toggle=popover]').each(function (index, html) {
        $(this).popover('hide');
    });
}
/*
 ajax request methods
 */
function getTableData(action, offset, limit, qfield, q, startDate, endDate,searchSource) {
    hidePopover();
    var param = {
        action: action,
        offset: offset,
        limit: limit,
    };

    if (($.trim(qfield) != '' && $.trim(q) != '') || $.trim(startDate) != '' || $.trim(endDate) != ''||$.trim(searchSource) != '') {
       if ($.trim(qfield) != '' && $.trim(q) != '') {
            q = qfield + '=' + q;
        }
        if ($.trim(startDate) != '') {
            if ($.trim(q) != '') {
                q = q + ",";
            }
            q = q + "startDate=" + startDate;
        }
        if ($.trim(endDate) != '') {
            if ($.trim(q) != '') {
                q = q + ",";
            }
            var end = new Date(Date.parse(endDate.replace(/-/g, "/")));
            var endDateObj = new Date(end.getTime() + 86400000);
            var endString = endDateObj.getFullYear() + '-' + (endDateObj.getMonth() + 1) + '-' + endDateObj.getDate();
            q = q + "endDate=" + endString;
        }
        if ($.trim(searchSource) != '') {
            if ($.trim(q) != '') {
                q = q + ",";
            }
            q = q + 'searchSource=' + searchSource;
        }

        if (q != '') {
            param.q = encodeURI(q);
        }
    }
    blockObj($('div.page-content'));
    $.ajax({
        method: "GET",
        url: "/v1.0/comments",
        headers: {
            "Accept-Language":$.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0,2),
            "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
        },
        data: param,
        success: function (data, status, jqXHR) {
            tableData = data;
            console.log(data.pageImpl);
            refreshReportedCommentTable(fnInfoCallback, footerCallback, fnCreatedRow, fnInitComplete);
            currentPage = data.pageImpl.number + 1;
            initPager(
                {
                    container: '#pager',
                    totalPage: data.pageImpl.totalPages,
                    currentPage: currentPage,
                    pageButtonStyle: 0,//0是line样式 1是swing样式
                    pageButtonNum: 10,
                    buttonCallback: function (pageIndex, newCurrentPage) {
                        if (searchStyle == 'elsInitWithUserSearch') {
                            if ($('#reportedCommentSearchField').val() == 'nickName' || $('#reportedCommentSearchField').val() == 'email') {
                                //if there is uid result.use uid!
                                if (selectUid != '') {
                                    getTableData('REPORTED', $('#reportedCommentLimit').val() * (newCurrentPage - 1), $('#reportedCommentLimit').val(), 'uid', selectUid, '', '');
                                } else {
                                    getTableData('REPORTED', $('#reportedCommentLimit').val() * (newCurrentPage - 1), $('#reportedCommentLimit').val(), '', '', '', '','els');
                                }
                            } else {
                                getTableData('REPORTED', $('#reportedCommentLimit').val() * (newCurrentPage - 1), $('#reportedCommentLimit').val(), $('#reportedCommentSearchField').val(), $('#reportedCommentSearchKeyword').val(), '', '','els');
                            }
                        } else if (searchStyle == 'elsInit') {
                            getTableData('REPORTED', $('#reportedCommentLimit').val() * (newCurrentPage - 1), $('#reportedCommentLimit').val(), $('#reportedCommentSearchField').val(), $('#reportedCommentSearchKeyword').val(), '', '','els');
                        }
                        else {
                            if ($('#reportedCommentSearchField').val() == 'nickName' || $('#reportedCommentSearchField').val() == 'email') {
                                //if there is uid result.use uid!
                                if (selectUid != '') {
                                    getTableData('REPORTED', $('#reportedCommentLimit').val() * (newCurrentPage - 1), $('#reportedCommentLimit').val(), 'uid', selectUid, $('#reportedCommentStartDatePicker').val(), $('#reportedCommentEndDatePicker').val());
                                } else {
                                    getTableData('REPORTED', $('#reportedCommentLimit').val() * (newCurrentPage - 1), $('#reportedCommentLimit').val(), '', '', $('#reportedCommentStartDatePicker').val(), $('#reportedCommentEndDatePicker').val());
                                }
                            } else {
                                getTableData('REPORTED', $('#reportedCommentLimit').val() * (newCurrentPage - 1), $('#reportedCommentLimit').val(), $('#reportedCommentSearchField').val(), $('#reportedCommentSearchKeyword').val(), $('#reportedCommentStartDatePicker').val(), $('#reportedCommentEndDatePicker').val());
                            }
                        }
                    }
                }
            );
            $('div.page-content').unblock();
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            //TODO: handle other status
            if (XMLHttpRequest.status == "401") {
                window.location = "/login";
            }
        }
    });
}

function putHandleComment(action, commentIds) {
    for (var index in commentIds) {
        var commentId = commentIds[index];
        if (selectedItem['c' + commentId]) {
            delete selectedItem['c' + commentId];
        }
    }
    var commentIdString = commentIds.join(',');
    $.ajax({
        method: "PUT",
        url: "/v1.0/comments",
        headers: {
            "Accept-Language":$.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0,2),
            "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
        },
        data: {
            action: action,
            commentIds: commentIdString
        },
        success: function (data, status, jqXHR) {
            console.log(data);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            //TODO: handle other status
            if (XMLHttpRequest.status == "401") {
                window.location = "/login";
            }
        }
    });
}
/*
 local data/dom methods
 */
function disableAllButton() {
    //TODO
}
function handleButtonClick(action, obj) {
    var putCommentIds = new Array();
    var aData = t.fnGetData(obj);
    if (aData != null && putCommentIds.indexOf(aData.commentId) == -1) {
        putCommentIds.push(aData.commentId);
        t.fnDeleteRow(obj);
        putHandleComment(action, putCommentIds);
    }
}
function totalHandleButtonClick(action) {
    var putCommentIds = new Array();
    for (var itemId in selectedItem) {
        var aData = t.fnGetData(selectedItem[itemId]);
        if (aData != null && putCommentIds.indexOf(aData.commentId) == -1) {
            putCommentIds.push(aData.commentId);
        }
        t.fnDeleteRow(selectedItem[itemId]);
        delete selectedItem[itemId];
    }
    if (putCommentIds.length == 0 || action == '') {
        return;
    }
    putHandleComment(action, putCommentIds);
}

function checkBoxesClickFixed() {
    $('#reportedCommentTable tbody tr span[class=text]').on('click', function () {
        var aData = t.fnGetData($(this).parents('tr'));
        var checked = $(this).prev('input').is(":checked");
        if (checked) {
            $(this).prev('input').prop("checked", false);
            delete selectedItem['c' + aData.commentId];
        } else {
            $(this).prev('input').prop("checked", true);
            selectedItem['c' + aData.commentId] = $(this).parents('tr');
        }
    });
    $('#reportedCommentTable thead th input[type=checkbox]').change(function () {
        var set = $("#reportedCommentTable tbody tr input[type=checkbox]");
        var checked = $(this).is(":checked");
        $(set).each(function () {
            var aData = t.fnGetData($(this).parents('tr'));
            if (checked) {
                $(this).prop("checked", true);
                //$(this).parents('tr').addClass("active");
                selectedItem['c' + aData.commentId] = $(this).parents('tr');
            } else {
                $(this).prop("checked", false);
                //$(this).parents('tr').removeClass("active");
                delete selectedItem['c' + aData.commentId];
            }
        });
    });
    //$('#reportedCommentTable tbody tr input[type=checkbox]').change(function() {
    //    //$(this).parents('tr').toggleClass("active");
    //});

}
/**
 * no used
 */
function printJson() {
    for (var js2 in selectedItem) {
        console.log(js2 + '=' + selectedItem[js2]);
    }
}
/*
 table call backs*************************************
 */
var fnInfoCallback = function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {

    $('[data-toggle=popover]').each(function (index, html) {
        $(this).popover({
            placement: 'left',
            trigger: 'click', //触发方式
            html: true, // 为true的话
            content: '<div class=\'layer-btn-center-area\'>'
            + '<button  class=\'btn btn-default btn-sm btn-block\'>'+$.i18n.prop("ums.report.comment.one.pass")+'</button><br/>'
            + '<button  class=\'btn btn-info btn-sm btn-block\'>'+$.i18n.prop("ums.report.comment.one.blind")+'</button><br/>'
            + '<button  class=\'btn btn-darkorange btn-sm btn-block\'>'+$.i18n.prop("ums.report.comment.one.delete")+'</button>'
            + '</div>',
            title: ''
        });
    });

    $('[data-toggle=popover]').on('click', function () {
        $('[data-toggle=popover]').not(this).popover('hide');
    });
    $('[data-toggle=popover]').on('shown.bs.popover', function () {
        var obj = $(this).parents('tr');
        $('.layer-btn-center-area .btn-default').on('click', function (e) {
            handleButtonClick('PASS', obj);
        });
        $('.layer-btn-center-area .btn-info').on('click', function (e) {
            handleButtonClick('HIDD', obj);
        });
        $('.layer-btn-center-area .btn-darkorange').on('click', function (e) {
            handleButtonClick('DEL', obj);
        });
    });
};

var fnCreatedRow = function (nRow, aData, iDataIndex) {

    if (aData.slangType == "A") {
        nRow.className = "danger";
    }
    $('td:eq(0)', nRow).html('<lable><input type="checkbox"><span class="text"></span></lable>');
    if (aData.uid == '' || aData.uid == undefined) {
        $('td:eq(4)', nRow).html('<a class="gray"  target="_blank">No User</a>');
    } else if (aData.nickName == '' || aData.nickName == undefined) {
        $('td:eq(4)', nRow).html('<a class="danger"  target="_blank" href="accountInfo?uid=' + aData.uid + '">No Name</a>');
    } else {
        $('td:eq(4)', nRow).html('<a target="_blank" href="accountInfo?uid=' + aData.uid + '">' + aData.nickName + '</a>');
    }
    //if (aData.cardId == "") {
    //    $('td:eq(4)', nRow).html('<a target="_blank" href="http://allets.com/share/' + aData.contentId + '">' + aData.commentText + '</a>');
    //} else {
    //    $('td:eq(4)', nRow).html('<a target="_blank" href="http://allets.com/share/' + aData.contentId + '/' + aData.cardOrdering + '">' + aData.commentText + '</a>');
    //}
    var cardId = null;
    var parentCommentId = null;
    var cardOrder = null;
    if (aData.cardId != '') {
        cardId = aData.cardId;
        cardOrder = aData.cardOrdering;
    }
    if (aData.parentCommentId != '') {
        parentCommentId = aData.parentCommentId;
    }
    $('td:eq(5)', nRow).html('<a href="javascript:showModal(' + aData.commentId + ',' + aData.contentId + ',' + cardId + ',' + cardOrder + ',' + parentCommentId + ')">' + aData.commentText + '</a>');


    if (aData.likeCount == 0) {
        $('td:eq(6)', nRow).html("-");
    }
    if (aData.recommentCount == 0) {
        $('td:eq(7)', nRow).html("-");
    }

    $('td:eq(12)', nRow).html('<a class="btn btn-blue btn-sm" data-toggle="popover">'+$.i18n.prop("ums.report.comment.select")+'</a>');

    //$('td:eq(10)', nRow).html(iDataIndex+'A'+data.slangTest);
    //if(aData.parentId=="")
    //{
    //}
    // $.ajax(
    //    {
    //        url:"/v1.0/comments/slang",
    //        success:function(data)
    //        {
    //
    //        }
    //    }
    //)

};
var footerCallback = function (row, data, start, end, display) {
};
var fnInitComplete = function (oSettings, json) {
    checkBoxesClickFixed();
    $('.loading-container')
        .addClass('loading-inactive');
};

/*
 table refresh main method
 */
function refreshReportedCommentTable(fnInfoCallback, footerCallback, fnCreatedRow, fnInitComplete) {
    if (t != null)
        t.fnDestroy();
    t = $('#reportedCommentTable').dataTable({
        //'ajax': {
        //    'url': '/v1.0/comments',
        //    'type': 'GET',
        //    'data': {action: 'REPORTED', status: 'REPORTED', offset: offset, limit: limit, field: field, q: q},
        //    'dataSrc': 'pageImpl.content'
        //},
        data: tableData.pageImpl.content,
        'aoColumns': [
            {'mDataProp': null},
            {'mDataProp': 'cDate'},
            {'mDataProp': 'reportCount'},
            {'mDataProp': 'inHouseReportCount'},
            {'mDataProp': 'nickName'},
            {'mDataProp': 'commentText'},
            {'mDataProp': 'likeCount'},
            {'mDataProp': 'recommentCount'},
            {'mDataProp': 'commentType'},
            {'mDataProp': 'reportTypeMessage'},
            {'mDataProp': 'commentId'},
            {'mDataProp': 'slangType'},
            {'mDataProp': null}
        ],
        'aoColumnDefs': [
            {
                "aTargets": 0,
                orderable: false
            },
            {
                "aTargets": 12,
                orderable: false
                //"mRender": function (data, type, full) {
                //    var string =
                //        '<a class="btn btn-blue btn-sm" data-toggle="popover">선택</a>';
                //    return string
                //}
            }
        ],
        //"aaSorting": [[1, "desc"]],
        //'sDom': 'Tflt<\'row DTTTFooter\'<\'col-sm-6\'i><\'col-sm-6\'p>>',
        //'sPaginationType': 'two_button',//分页样式 full_numbers \ bootstrap
        'bAutoWidth': false,                    //不自动计算列宽度
        // 'bProcessing': true,//当datatable获取数据时候是否显示正在处理提示信息
        'bInfo': true,//页脚信息
        'bFilter': false,  //不使用过滤功能 search
        'bPaginate': false,//是否分页。
        //'bServerSide': true,//服务端处理分页

        //'bLengthChange': true,////是否允许自定义每页显示条数.
        //'iDisplayLength': 15, //每页显示15条记录
        //'aLengthMenu': [[15, 30, 50], [15, 30, 50]],
        'fnInfoCallback': fnInfoCallback,
        //'oLanguage': {
        //    'sSearch': 'table filter: ',
        //
        //    //'sProcessing': 'loading...',
        //
        //    'oPaginate': {
        //        'sFirst': 'First',
        //        'sPrevious': '«',
        //        'sNext': '»',
        //        'sLast': 'Last'
        //    }
        //},
        //scrollY: 400,
        'footerCallback': footerCallback,
        'fnCreatedRow': fnCreatedRow,
        'fnInitComplete': fnInitComplete
    });
}

