/**
 * Created by claude on 2015/10/9.
 */

var handledAccountPageCount = 50;
var handledAccountTable=null;
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


    $('#handledAccountStartDatePicker').datepicker({
        format: 'yyyy-mm-dd',
    });
    $('#handledAccountEndDatePicker').datepicker({
        format: 'yyyy-mm-dd',
    });
    $('#handledAccountLimit').change(function () {
        if (handledAccountPageCount != $('#handledAccountLimit').val()) {
            handledAccountPageCount = $('#handledAccountLimit').val();
            getTableData(0);
        }
    });
    $('#handledAccountSearchButton').on('click', function (e) {
        getTableData(0);
    });
    $('#handledAccountSearchKeyword').keydown(function (a) {
        if (a.keyCode == 13)
            getTableData(0);
    });
    $('#handledAccountStartDatePicker').on(
        'focus', function () {
            $(this).blur();
        }
    );
    $('#handledAccountEndDatePicker').on(
        'focus', function () {
            $(this).blur();
        }
    );
    $('#searchButton').on(
        'click', function () {
            getTableData(0);
        }
    );
    $('#initSearchButton').on(
        'click', function () {
            $('#handledAccountStartDatePicker').val('');
            $('#handledAccountEndDatePicker').val('');
            $('#handledAccountSearchField').val('nickName');
            $('#handledAccountSearchKeyword').val('');
            $('#handledAccountPeriodDays').val('');
            $('#handledAccountFilter').val('ALL');
        }
    );
    $('#handledAccountPeriodDays').val('');
    $('#handledAccountPeriodDays').change(function () {
        var now = new Date();
        var beforeMil = now.getTime() - ( $('#handledAccountPeriodDays').val() - 1) * 86400000;
        var before = new Date(beforeMil);
        $('#handledAccountEndDatePicker').val(now.getFullYear() + '-' + (now.getMonth() + 1) + '-' + now.getDate());
        $('#handledAccountStartDatePicker').val(before.getFullYear() + '-' + (before.getMonth() + 1) + '-' + before.getDate());
    });
    $('#refreshButton').on('click',function()
    {
         getTableData((currentPage - 1) * $('#handledAccountLimit').val());
    });
    getTableData(0);
});


/*
 ajax request methods
 */
function getTableData(offset) {
    var param =
    {
        action: 'HANDLED',
        offset: offset,
        limit: $('#handledAccountLimit').val(),
    };

    var q = '';
    var searchStandard = $('#handledAccountFilter').val();
    var startDate = $('#handledAccountStartDatePicker').val();
    var endDate = $('#handledAccountEndDatePicker').val();

    var keywordField = $('#handledAccountSearchField').val();
    var keyword = $('#handledAccountSearchKeyword').val();

    var sort = '';


    if (($.trim(keywordField) != '' && $.trim(keyword) != '') || $.trim(searchStandard) != 'ALL' || $.trim(startDate) != '' || $.trim(endDate) != '') {
        if ($.trim(keywordField) != '' && $.trim(keyword) != '') {
            q = keywordField + '=' + keyword;
        }
        if ($.trim(searchStandard) != 'ALL') {
            if ($.trim(searchStandard) == 'monitorId') {
                sort = '+monitorId';
                param.sort = sort;
            } else {
                if ($.trim(q) != '') {
                    q = q + ',';
                }
                q = q + 'handleResult=' + searchStandard;
            }
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


    //handle_result
    $.ajax({
        method: 'GET',
        url: '/v1.0/users',
        headers: {
            "Accept-Language":$.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0,2),
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
            refreshhandledAccountTable(null, fnCreatedRow);
            currentPage = data.pageImpl.number + 1;
            initPager(
                {
                    container: '#pager',
                    totalPage: data.pageImpl.totalPages,
                    currentPage: currentPage,
                    pageButtonStyle: 0,
                    pageButtonNum: 10,
                    buttonCallback: function (pageIndex, newCurrentPage) {
                        getTableData((newCurrentPage - 1) * $('#handledAccountLimit').val());
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

function refreshhandledAccountTable(footerCallback, fnCreatedRow, fnInitComplete) {
    if (handledAccountTable != null)
        handledAccountTable.fnDestroy();
    handledAccountTable = $('#handledAccountTable').dataTable({
        data: tableData.pageImpl.content,
        'aoColumns': [
            {'mDataProp': 'uid'},
            {'mDataProp': 'name'},
            {'mDataProp': 'subscriberCount'},
            {'mDataProp': 'userReportedCount'},
            {'mDataProp': 'reportTypeMessage'},
            {'mDataProp': 'cdate'},
            {'mDataProp': 'invalidTo'},
            {'mDataProp': 'monitorName'},
            {'mDataProp': 'handleResult'}
        ],
        'aoColumnDefs': [
            {'aTargets': 0, orderable: false},
            {'aTargets': 1, orderable: false},
            {'aTargets': 2, orderable: false},
            {'aTargets': 3, orderable: false},
            {'aTargets': 4, orderable: false},
            {'aTargets': 6, orderable: false},
            {'aTargets': 7, orderable: false},
            {'aTargets': 8, orderable: false}
        ],
        "aaSorting": [[5, "desc"]],
        'bAutoWidth': false,
        'bInfo': false,
        'bFilter': false,
        'bPaginate': false,
        'footerCallback': footerCallback,
        'fnCreatedRow': fnCreatedRow,
        'fnInitComplete': fnInitComplete
    });
}

var fnCreatedRow = function (nRow, aData, iDataIndex) {

    $('td:eq(0)', nRow).html('<a target="_blank" href="accountInfo?uid=' + aData.uid + '">' + aData.uid + '</a>');
    if (aData.name == '' || aData.name == undefined) {
        $('td:eq(1)', nRow).html('<a class="danger" target="_blank" href="accountInfo?uid=' + aData.uid + '">No Name</a>');
    } else {
        $('td:eq(1)', nRow).html('<a target="_blank" href="accountInfo?uid=' + aData.uid + '">' + aData.name + '</a>');
    }

    if (aData.subscriberCount == 0) {
        $('td:eq(2)', nRow).html('-');
    }
    if (aData.reportedCount == 0) {
        $('td:eq(3)', nRow).html('-');
    }
    if (aData.handleResult == 'BLOK') {
        $('td:eq(8)', nRow).html('<span class=\'label label-info\'>' + $.i18n.prop('ums.handle.history.account.blind') +'</span>');
    } else if (aData.handleResult == 'DEL') {
        $('td:eq(8)', nRow).html('<span class=\'label label-danger\'>' + $.i18n.prop('ums.handle.history.account.delete') +'</span>');
    } else if (aData.handleResult == 'PASS') {
        $('td:eq(8)', nRow).html('<span class=\'label label-default\'>' + $.i18n.prop('ums.handle.history.account.pass') +'</span>');
    }else if (aData.handleResult == 'EDIT') {
        $('td:eq(8)', nRow).html('<span class=\'label label-pink\'>EDIT USER</span>');
    }else if (aData.handleResult == 'ALT1') {
        $('td:eq(8)', nRow).html('<span class=\'label label-yellow\'>R ALERT<span class="glyphicon glyphicon-alert black"></span></span>');
    }else if (aData.handleResult == 'ALT2') {
        $('td:eq(8)', nRow).html('<span class=\'label label-yellow\'>D ALERT<span class="glyphicon glyphicon-alert black"></span></span>');
    }else if (aData.handleResult == 'RSPW') {
        $('td:eq(8)', nRow).html('<span class=\'label label-darkpink\'>RESET PASSWORD</span>');
    }else if (aData.handleResult == 'OUTF') {
        $('td:eq(8)', nRow).html('<span class=\'label label-danger\'>' + $.i18n.prop('ums.handle.history.account.delete') +'</span>');
    }else if (aData.handleResult == 'REC') {
        $('td:eq(8)', nRow).html('<span class=\'label label-green\'>' + $.i18n.prop('ums.handle.history.account.restore') +'</span>');
    }
};