/**
 * Created by claude on 2015/8/31.
 */

/*
 vars
 */

var handledCommentsPageCount = 50;
var handledCommentsTable = null;
var currentPage = 1;
var tableData;
var startDate;
var endDate;
/*
 * init method
 */
$(window).resize(function () {
    $('.page-body').css('height', $(window).height() - 85);
});
$(document).ready(function () {
    $('.page-body').css('height', $(window).height() - 85);

    $('#handledCommentStartDatePicker').datepicker({
        format: 'yyyy-mm-dd',
    });
    $('#handledCommentEndDatePicker').datepicker({
        format: 'yyyy-mm-dd',
    });

    $('#handledCommentLimit').change(function () {
        if (handledCommentsPageCount != $('#handledCommentLimit').val()) {
            handledCommentsPageCount = $('#handledCommentLimit').val();
            getTableData('HANDLED', 0, $('#handledCommentLimit').val(), $('#handledCommentSearchField').val(), $('#handledCommentSearchKeyword').val(), $('#handledCommentStartDatePicker').val(), $('#handledCommentEndDatePicker').val(), $('#handledCommentFilter').val());
        }
    });
    $('#handledCommentSearchButton').on('click', function (e) {
        getTableData('HANDLED', 0, $('#handledCommentLimit').val(), $('#handledCommentSearchField').val(), $('#handledCommentSearchKeyword').val(), $('#handledCommentStartDatePicker').val(), $('#handledCommentEndDatePicker').val(), $('#handledCommentFilter').val());
    });
    $('#handledCommentSearchKeyword').keydown(function (a) {
        if (a.keyCode == 13)
            getTableData('HANDLED', 0, $('#handledCommentLimit').val(), $('#handledCommentSearchField').val(), $('#handledCommentSearchKeyword').val(), $('#handledCommentStartDatePicker').val(), $('#handledCommentEndDatePicker').val(), $('#handledCommentFilter').val());
    });

    $('#handledCommentStartDatePicker').on(
        'focus', function () {
            $(this).blur();
        }
    );
    $('#handledCommentEndDatePicker').on(
        'focus', function () {
            $(this).blur();
        }
    );
    $('#searchButton').on(
        'click', function () {
            getTableData('HANDLED', 0, $('#handledCommentLimit').val(), $('#handledCommentSearchField').val(), $('#handledCommentSearchKeyword').val(), $('#handledCommentStartDatePicker').val(), $('#handledCommentEndDatePicker').val(), $('#handledCommentFilter').val());
        }
    );
    $('#initSearchButton').on(
        'click', function () {
            $('#handledCommentStartDatePicker').val('');
            $('#handledCommentEndDatePicker').val('');
            $('#handledCommentSearchField').val('nickName');
            $('#handledCommentSearchKeyword').val('');
            $('#handledCommentPeriodDays').val('');
            $('#handledCommentFilter').val('ALL');
        }
    );
    $('#handledCommentPeriodDays').val('');
    $('#handledCommentPeriodDays').change(function () {
        var now = new Date();
        var beforeMil = now.getTime() - ( $('#handledCommentPeriodDays').val() - 1) * 86400000;
        var before = new Date(beforeMil);
        $('#handledCommentEndDatePicker').val(now.getFullYear() + '-' + (now.getMonth() + 1) + '-' + now.getDate());
        $('#handledCommentStartDatePicker').val(before.getFullYear() + '-' + (before.getMonth() + 1) + '-' + before.getDate());
    });
    $('#refreshButton').on('click', function () {
        getTableData('HANDLED', $('#handledCommentLimit').val() * (currentPage - 1), $('#handledCommentLimit').val(), $('#handledCommentSearchField').val(), $('#handledCommentSearchKeyword').val(), $('#handledCommentStartDatePicker').val(), $('#handledCommentEndDatePicker').val(), $('#handledCommentFilter').val());
    });
    getTableData('HANDLED', 0, $('#handledCommentLimit').val(), $('#handledCommentSearchField').val(), $('#handledCommentSearchKeyword').val(), $('#handledCommentStartDatePicker').val(), $('#handledCommentEndDatePicker').val(), $('#handledCommentFilter').val());

})

/*
 ajax request methods
 */
function getTableData(action, offset, limit, qfield, q, startDate, endDate, searchStandard) {
    var param = {
        action: action,
        offset: offset,
        limit: limit,
    };
    if (($.trim(qfield) != '' && $.trim(q) != '') || $.trim(searchStandard) != 'ALL' || $.trim(startDate) != '' || $.trim(endDate) != '') {
        if ($.trim(qfield) != '' && $.trim(q) != '') {
            q = qfield + '=' + q;
        }
        if ($.trim(searchStandard) != 'ALL') {
            if ($.trim(q) != '') {
                q = q + ',';
            }
            q = q + 'handleResult=' + searchStandard;
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
        if (q != '') {
            param.q = encodeURI(q);
        }
    }

    blockObj($('div.page-content'));

    $.ajax({
        method: 'GET',
        url: '/v1.0/comments',
        headers: {
            "Accept-Language": $.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0, 2),
            "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
        },
        data: param,
        beforeSend: function (reqObj, settings) {
            //reqObj.setRequestHeader('X-ALLETS-COUNTRY', countryCode);
            //reqObj.setRequestHeader('X-ALLETS-LANG', languageCode);
            reqObj.setRequestHeader('X-ALLETS-CHANNEL', channel);
        },
        success: function (data, status, jqXHR) {
            tableData = data;
            console.log(data);
            refreshhandledCommentTable(null, null, fnCreatedRow);
            currentPage = data.pageImpl.number + 1;
            initPager(
                {
                    container: '#pager',
                    totalPage: data.pageImpl.totalPages,
                    currentPage: currentPage,
                    pageButtonStyle: 0,
                    pageButtonNum: 10,
                    buttonCallback: function (pageIndex, newCurrentPage) {
                        getTableData('HANDLED', (newCurrentPage - 1) * $('#handledCommentLimit').val(), $('#handledCommentLimit').val(), $('#handledCommentSearchField').val(), $('#handledCommentSearchKeyword').val(), $('#handledCommentStartDatePicker').val(), $('#handledCommentEndDatePicker').val(), $('#handledCommentFilter').val());
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

/*
 local data/dom methods
 */

/*
 table call backs*************************************
 */
var fnCreatedRow = function (nRow, aData, iDataIndex) {

    if (aData.slangType == 'A') {
        nRow.className = 'danger';
    }
    if (aData.uid == '' || aData.uid == undefined) {
        $('td:eq(2)', nRow).html('<a class="gray"  target="_blank">No User</a>');
    } else if (aData.nickName == '' || aData.nickName == undefined) {
        $('td:eq(2)', nRow).html('<a class="danger"  target="_blank" href="accountInfo?uid=' + aData.uid + '">No Name</a>');
    } else {
        $('td:eq(2)', nRow).html('<a target="_blank" href="accountInfo?uid=' + aData.uid + '">' + aData.nickName + '</a>');
    }
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
    $('td:eq(3)', nRow).html('<a href="javascript:showModal(' + aData.commentId + ',' + aData.contentId + ',' + cardId + ',' + cardOrder + ',' + parentCommentId + ')">' + aData.commentText + '</a>');
    if (aData.likeCount == 0) {
        $('td:eq(4)', nRow).html('-');
    }
    if (aData.recommentCount == 0) {
        $('td:eq(5)', nRow).html('-');
    }
    if (aData.handleResult == 'HIDD') {
        $('td:eq(8)', nRow).html('<span class=\'label label-info\'>' + $.i18n.prop("ums.handle.history.comment.blind") + '</span>');
    } else if (aData.handleResult == 'DEL') {
        $('td:eq(8)', nRow).html('<span class=\'label label-danger\'>' + $.i18n.prop("ums.handle.history.comment.delete") + '</span>');
    } else if (aData.handleResult == 'PASS') {
        $('td:eq(8)', nRow).html('<span class=\'label label-default\'>' + $.i18n.prop("ums.handle.history.comment.pass") + '</span>');
    }
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
function clearDuplicateComment(commentsHistorys) {
    if (commentsHistorys.length == undefined && commentsHistorys.length == 0) {
        return commentsHistorys;
    }
    var num = commentsHistorys.length;
    var i = 0;
    while (num > 0) {
        for (var j = i + 1; j < num; j++) {
            if (commentsHistorys[i].commentId == commentsHistorys[j].commentId) {
                commentsHistorys.splice(j, 1);
                num = num - 1;
            }
        }
        num = num - 1;
        i = i + 1;
    }
    return commentsHistorys;
}
function refreshhandledCommentTable(fnInfoCallback, footerCallback, fnCreatedRow, fnInitComplete) {
    if (handledCommentsTable != null)
        handledCommentsTable.fnDestroy();
    handledCommentsTable = $('#handledCommentTable').dataTable({
        //'ajax': {
        //    'url': '/v1.0/comments',
        //    'type': 'GET',
        //    'data': {action: 'REPORTED', status: 'REPORTED', offset: offset, limit: limit, field: field, q: q},
        //    'dataSrc': 'pageImpl.content'
        //},
        data: clearDuplicateComment(tableData.pageImpl.content),
        'aoColumns': [
            {'mDataProp': 'cDate'},
            {'mDataProp': 'reportCount'},
            {'mDataProp': 'nickName'},
            {'mDataProp': 'commentText'},
            {'mDataProp': 'likeCount'},
            {'mDataProp': 'recommentCount'},
            {'mDataProp': 'commentType'},
            {'mDataProp': 'reportTypeMessage'},
            {'mDataProp': 'handleResult'},
            {'mDataProp': 'slangType'},
            {'mDataProp': 'monitorName'}
        ],
        'aoColumnDefs': [
            {'aTargets': 1, orderable: false},
            {'aTargets': 2, orderable: false},
            {'aTargets': 3, orderable: false},
            {'aTargets': 4, orderable: false},
            {'aTargets': 5, orderable: false},
            {'aTargets': 6, orderable: false},
            {'aTargets': 7, orderable: false}
        ],
        "aaSorting": [[0, "desc"]],
        'bAutoWidth': false,
        'bInfo': false,
        'bFilter': false,
        'bPaginate': false,
        'footerCallback': footerCallback,
        'fnCreatedRow': fnCreatedRow,
        'fnInitComplete': fnInitComplete
    });
}


