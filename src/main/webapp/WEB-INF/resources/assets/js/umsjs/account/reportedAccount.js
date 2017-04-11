/**
 * reported account
 */
var pageCount = 50;
var currentPage = 1;
var t = null;
var tableData;
var selectedItem;
var monitorId;
var searchStyle = 'userPopupInit';//'normalInit','userPopupInit'
var selectUid = '';
var selectIndex = '';
var userSearchResult = '';
var order = '-reportCount';

/*
 * init method
 */
$(window).resize(function () {
    $('.page-body').css('height', $(window).height() - 85);
});
$(document).ready(function () {
    $('.page-body').css('height', $(window).height() - 85);
    //for test
    monitorId = 1;
    selectedItem = {};
    $('#regTime').on('click', function (e) {
        if (order == '-regTime') {
            order = '+regTime';
            $('#orderText').removeClass("label-danger");
            $('#orderText').addClass("label-green");
            $('#orderText').html($.i18n.prop('ums.report.account.registertime.asc'));
        } else {
            order = '-regTime';
            $('#orderText').removeClass("label-green");
            $('#orderText').addClass("label-danger");
            $('#orderText').html($.i18n.prop('ums.report.account.registertime.desc'));
        }
        reorderData();
    });
    $('#reportCount').on('click', function (e) {
        if (order == '-reportCount') {
            order = '+reportCount';
            $('#orderText').removeClass("label-danger");
            $('#orderText').addClass("label-green");
            $('#orderText').html($.i18n.prop('ums.report.account.reportcount.asc'));
        } else {
            order = '-reportCount';
            $('#orderText').removeClass("label-green");
            $('#orderText').addClass("label-danger");
            $('#orderText').html($.i18n.prop('ums.report.account.reportcount.desc'));
        }
        reorderData();
    });
    if (order == '-regTime') {
        $('#orderText').removeClass("label-green");
        $('#orderText').addClass("label-danger");
        $('#orderText').html($.i18n.prop('ums.report.account.registertime.desc'));
    } else if (order == '+regTime') {
        $('#orderText').removeClass("label-danger");
        $('#orderText').addClass("label-green");
        $('#orderText').html($.i18n.prop('ums.report.account.registertime.asc'));
    } else if (order == '-reportCount') {
        $('#orderText').removeClass("label-green");
        $('#orderText').addClass("label-danger");
        $('#orderText').html($.i18n.prop('ums.report.account.reportcount.desc'));
    } else if (order == '+reportCount') {
        $('#orderText').removeClass("label-danger");
        $('#orderText').addClass("label-green");
        $('#orderText').html($.i18n.prop('ums.report.account.reportcount.asc'));
    }
    if (searchStyle == 'userPopupInit') {
        userPopupInit();
    } else {
        normalInit();
    }
})
function reorderData() {
    if ($('#searchBy').val() == 'nickName' || $('#searchBy').val() == 'email') {
        //if there is uid result.use uid!
        if (selectUid != '') {
            getTableData("REPORTED", 0, $('#pageSize').val(), 'uid', selectUid, order);
        } else {
            getTableData("REPORTED", 0, $('#pageSize').val(), '', '', order);
        }
    } else {
        getTableData("REPORTED", 0, $('#pageSize').val(), $('#searchBy').val(), $('#keyword').val(), order);
    }
}
function userPopupInit() {
    searchStyle = 'userPopupInit';
    $('#userSearchKeyword').show();
    $('#keyword').hide();
    $('#userSearchKeyword').parent().addClass('open');
    $('#userSearchKeyword').attr('aria-expanded', 'true');
    $('#searchSource').hide();
    $('#searchBy').change(
        function () {
            if ($('#searchBy').val() == 'nickName' || $('#searchBy').val() == 'email') {
                $('#userSearchKeyword').show();
                $('#keyword').hide();
                //$('#searchSource').attr('disabled', false);
                //$('#searchSource').show();
                $('#searchSource').hide();
            } else if ($('#searchBy').val() == 'keyword') {
                $('#userSearchKeyword').hide();
                $('#keyword').show();
                //$('#searchSource').attr('disabled', true);
                $('#searchSource').hide();

            }
        }
    );

    $('#pageSize').change(function () {
        if (pageCount != $('#pageSize').val()) {
            pageCount = $('#pageSize').val();
            if ($('#searchBy').val() == 'nickName' || $('#searchBy').val() == 'email') {
                //if there is uid result.use uid!
                if (selectUid != '') {
                    getTableData("REPORTED", 0, $('#pageSize').val(), 'uid', selectUid, order);
                } else {
                    getTableData("REPORTED", 0, $('#pageSize').val(), '', '', order);
                }
            } else {
                getTableData("REPORTED", 0, $('#pageSize').val(), $('#searchBy').val(), $('#keyword').val(), order);
            }
        }
    });
    $('#searchButton').on('click', function (e) {
        if ($('#searchBy').val() == 'nickName' || $('#searchBy').val() == 'email') {
            if ($('#userSearchKeyword').val() == '') {
                getTableData("REPORTED", 0, $('#pageSize').val(), '', '', order);
            } else {
                //requestSearchUser('els');
                requestSearchUser();
            }
        } else {
            getTableData("REPORTED", 0, $('#pageSize').val(), $('#searchBy').val(), $('#keyword').val(), order);
        }
    });
    $('#userSearchKeyword').keydown(function (a) {
        if (a.keyCode == 13) {
            if ($('#searchBy').val() == 'nickName' || $('#searchBy').val() == 'email') {
                //if (selectUid != '') {
                //    getTableData("REPORTED", "REPORTED", 0, $('#pageSize').val(), 'uid', selectUid,order);
                //} else {
                if ($('#userSearchKeyword').val() == '') {
                    getTableData("REPORTED", 0, $('#pageSize').val(), '', '', order);

                } else {
                    //requestSearchUser('els');
                    requestSearchUser();
                }
                //}
            } else {
                getTableData("REPORTED", 0, $('#pageSize').val(), '', '', order);
            }
            //}
        }
    });
    $('#keyword').keydown(function (a) {
        if (a.keyCode == 13)
            getTableData("REPORTED", 0, $('#pageSize').val(), $('#searchBy').val(), $('#keyword').val(), order);
    });
    $('#refreshButton').on('click', function () {
        if ($('#searchBy').val() == 'nickName' || $('#searchBy').val() == 'email') {
            if (selectUid != '') {
                getTableData("REPORTED", (currentPage - 1) * $('#pageSize').val(), $('#pageSize').val(), 'uid', selectUid, order);
            } else {
                getTableData("REPORTED", (currentPage - 1) * $('#pageSize').val(), $('#pageSize').val(), '', '', order);
            }
        } else {
            getTableData("REPORTED", (currentPage - 1) * $('#pageSize').val(), $('#pageSize').val(), $('#searchBy').val(), $('#keyword').val(), order);
        }
    });
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
            //$('#searchBy').val('nickName');
            $('#keyword').val('');
        }
    );
    if ($.cookie("searchSource") && ($.cookie("searchSource") == 'els' || $.cookie("searchSource") == 'mysql')) {
        $('#searchSource').val($.cookie("searchSource"));
    } else {
        $('#searchSource').val('mysql');
        $.cookie("searchSource", 'mysql');
    }
    $('#searchSource').on('change', function (e) {
        $.cookie("searchSource", $('#searchSource').val());
    });
    getTableData("REPORTED", 0, $('#pageSize').val(), '', '', order);
}

function requestSearchUser(searchSource) {
    var field = $('#searchBy').val();
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
            "Accept-Language": $.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0, 2),
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
        if ($('#searchBy').val() == 'nickName') {
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
        } else if ($('#searchBy').val() == 'email') {
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
                if ($('#searchBy').val() == 'nickName') {
                    $('#userSearchKeyword').val(searchResult[$(this).val()].name);
                } else if ($('#searchBy').val() == 'email') {
                    $('#userSearchKeyword').val(searchResult[$(this).val()].email);
                }
                selectUid = searchResult[$(this).val()].uid;
                selectIndex = $(this).val();
                refreshUserSearchResultDropDown(searchResult);

                if (searchStyle == 'userPopupInit') {
                    if ($('#searchBy').val() == 'nickName' || $('#searchBy').val() == 'email') {
                        //if there is uid result.use uid!
                        getTableData("REPORTED", 0, $('#pageSize').val(), 'uid', selectUid, order);
                    } else {
                        getTableData("REPORTED", 0, $('#pageSize').val(), $('#searchBy').val(), $('#keyword').val(), order);
                    }
                } else {
                    getTableData("REPORTED", 0, $('#pageSize').val(), $('#searchBy').val(), $('#keyword').val(), order);
                }
            }
        }
    );

    $('#userSearchKeyword').parent().addClass('open');
    $('#userSearchKeyword').attr('aria-expanded', 'true');
}

function normalInit() {
    searchStyle = 'normalInit';
    $('#userSearchKeyword').hide();
    $('#searchResultDropDown').hide();
    $('#searchSource').hide();
    $('#keyword').show();

    $('#pageSize').change(function () {
        if (pageCount != $('#pageSize').val()) {
            pageCount = $('#pageSize').val();
            getTableData("REPORTED", 0, $('#pageSize').val(), $('#searchBy').val(), $('#keyword').val(), order);
        }
    });
    $('#searchButton').on('click', function (e) {
        getTableData("REPORTED", 0, $('#pageSize').val(), $('#searchBy').val(), $('#keyword').val(), order);
    });
    $('#keyword').keydown(function (a) {
        if (a.keyCode == 13)
            getTableData("REPORTED", 0, $('#pageSize').val(), $('#searchBy').val(), $('#keyword').val(), order);
    });
    $('#refreshButton').on('click', function () {
        getTableData("REPORTED", (currentPage - 1) * $('#pageSize').val(), $('#pageSize').val(), $('#searchBy').val(), $('#keyword').val(), order);
    });

    getTableData("REPORTED", 0, $('#pageSize').val(), '', '', order);

}
function hidePopover() {
    $('[data-toggle=popover]').each(function (index, html) {
        $(this).popover('hide');
    });
}
/*
 ajax request methods
 */
function getTableData(action, offset, limit, searchBy, keyword) {
    hidePopover();
    $('div.page-body').block({
        message: '<h5>loading data</h5>'
    });
    var param =
    {
        action: action,
        offset: offset,
        limit: limit,
        sort: order
    };
    var q = '';
    if ($.trim(searchBy) != '' && $.trim(keyword) != '') {
        q = searchBy + '=' + keyword;
        param.q = encodeURI(q);
    }

    $.ajax({
        method: "GET",
        url: "/v1.0/users",
        headers: {
            "Accept-Language": $.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0, 2),
            "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
        },
        data: param,
        success: function (data, status, jqXHR) {
            tableData = data;
            //console.log(data.pageImpl.content);
            refreshTable(fnInfoCallback, footerCallback, fnCreatedRow, fnInitComplete);
            var currentPageNum = data.pageImpl.number + 1;
            initPager(
                {
                    container: '#pager',
                    totalPage: data.pageImpl.totalPages,
                    currentPage: currentPageNum,
                    pageButtonStyle: 0,
                    pageButtonNum: 10,
                    buttonCallback: function (pageIndex, newCurrentPage) {
                        getTableData('REPORTED', (newCurrentPage - 1) * $('#pageSize').val(), $('#pageSize').val(), $('#searchBy').val(), $('#keyword').val(), order);
                    }
                }
            );
            $('div.page-body').unblock();
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            //TODO: handle other status
            if (XMLHttpRequest.status == "401") {
                window.location = "/login";
            }
        }
    });
}

function handleAccount(action, uid) {
    $.ajax({
        method: 'PUT',
        url: '/v1.0/users/' + uid,
        headers: {
            "Accept-Language": $.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0, 2),
            "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
        },
        data: {
            action: action
        },
        success: function (data, status, jqXHR) {

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            //TODO: handle other status
            if (XMLHttpRequest.status == "401") {
                window.location = "/login";
            }
        }
    });
}

function handleButtonClick(action, obj) {
    var aData = t.fnGetData(obj);
    if (aData != null) {
        deleteRow(obj);
        handleAccount(action, aData.uid)
    }
}


function deleteRow(obj) {
    var aData = t.fnGetData(obj);
    if (aData != null) {
        t.fnDeleteRow(obj);
    }
}

/*
 table call backs*************************************
 */
var fnInfoCallback = function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {

    $('[data-toggle=popover]').each(function (index, html) {
        $(this).popover({
            placement: 'left',
            trigger: 'click',
            html: true,
            content: '<div class=\'layer-btn-center-area\'>'
            + '<button  class=\'btn btn-default btn-sm btn-block\'>' + $.i18n.prop("ums.report.comment.one.pass") + '</button><br/>'
            + '<button  class=\'btn btn-info btn-sm btn-block\'>' + $.i18n.prop("ums.report.comment.one.blind") + '</button><br/>'
            + '<button  class=\'btn btn-darkorange btn-sm btn-block\'>' + $.i18n.prop("ums.report.comment.one.delete") + '</button>'
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
            handleButtonClick('OUT', obj);
        });
        $('.layer-btn-center-area .btn-darkorange').on('click', function (e) {
            handleButtonClick('OUTF', obj);
        });
    });

};

var fnCreatedRow = function (nRow, aData, iDataIndex) {
    if (aData.name == '' || aData.name == undefined) {
        $('td:eq(3)', nRow).html('<a class="danger" target="_blank" href="accountInfo?uid=' + aData.uid + '">No Name</a>');
    } else {
        $('td:eq(3)', nRow).html('<a target="_blank" href="accountInfo?uid=' + aData.uid + '">' + aData.name + '</a>');
    }
    if (aData.likeCount == 0) {
        $('td:eq(4)', nRow).html("-");
    }
    if (aData.recommentCount == 0) {
        $('td:eq(5)', nRow).html("-");
    }

    $('td:eq(7)', nRow).html('<a class="btn btn-blue btn-sm" data-toggle="popover">' + $.i18n.prop("ums.report.comment.select") + '</a>');
};

var footerCallback = function (row, data, start, end, display) {
};

var fnInitComplete = function (oSettings, json) {
    $('.loading-container')
        .addClass('loading-inactive');
};

function getOrder() {
    if (order == '-regTime') {
        return [[0, "desc"]];
    } else if (order == '+regTime') {
        return [[0, "asc"]];
    } else if (order == '-reportCount') {
        return [[1, "desc"]];
    } else {
        return [[1, "asc"]];
    }
}
/**
 * refresh table data
 * @param fnInfoCallback
 * @param footerCallback
 * @param fnCreatedRow
 * @param fnInitComplete
 */
function refreshTable(fnInfoCallback, footerCallback, fnCreatedRow, fnInitComplete) {
    if (t != null)
        t.fnDestroy();
    var orderParam = getOrder();
    t = $('#reportedUserTable').dataTable({
        data: tableData.pageImpl.content,
        'aoColumns': [
            {'mDataProp': 'latestReportTime'},
            {'mDataProp': 'reportedCount'},
            {'mDataProp': 'deleteCommentsCount'},
            {'mDataProp': 'name'},
            {'mDataProp': 'reportTypeMessage'},
            {'mDataProp': 'subscriberCount'},
            {'mDataProp': 'uid'},
            {'mDataProp': null}
        ],
        'aoColumnDefs': [
            {
                "aTargets": 7, orderable: false
            },
            {
                "aTargets": 0, orderable: false
            },
            {
                "aTargets": 1, orderable: false
            }
        ],
        'aaSorting': getOrder(),
        'bAutoWidth': false,
        'bInfo': true,
        'bFilter': false,
        'bPaginate': false,
        'fnInfoCallback': fnInfoCallback,
        'footerCallback': footerCallback,
        'fnCreatedRow': fnCreatedRow,
        'fnInitComplete': fnInitComplete
    });
}