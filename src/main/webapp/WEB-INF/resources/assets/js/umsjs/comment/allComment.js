/**
 * Created by claude on 2015/9/29.
 */
var allCommentsPageCount = 50;
var allCommentsTable = null;
var currentPage = 1;
var tableData;
var selectedItem;
var startDate;
var endDate;
var contentsResult = '';
var selectContentsId = '';
var selectIndex = '';
var selectCardId = '';
var cardResult = '';
var cardPopover = false;

$(window).resize(function () {
    $('.page-body').css('height', $(window).height() - 85);
});
$(document).ready(function () {
    $('.page-body').css('height', $(window).height() - 85);
    selectedItem = {};
    $('#allCommentSearchButton').on('click', function (e) {
        getTableData('ALL', 0, $('#allCommentLimit').val(), $('#allCommentSearchField').val(), $('#allCommentSearchKeyword').val(), $('#allCommentStartDatePicker').val(), $('#allCommentEndDatePicker').val());
    });
    $('#allCommentSearchKeyword').keydown(function (a) {
        if (a.keyCode == 13) {
            getTableData('ALL', 0, $('#allCommentLimit').val(), $('#allCommentSearchField').val(), $('#allCommentSearchKeyword').val(), $('#allCommentStartDatePicker').val(), $('#allCommentEndDatePicker').val());
        }
    });
    $('#allCommentSearchKeyword').on('click', function (e) {
        $('#allCommentSearchCardButton').popover('hide');
        $('#reportAndHandleComments').popover('hide');
        $('[rel=popover]').popover('hide');
    });
    $('#allCommentStartDatePicker').datepicker({
        format: 'yyyy-mm-dd',
    }).on('changeDate', function (ev) {
        //$('#allCommentEndDatePicker').val($('#allCommentStartDatePicker').val());
    });

    $('#allCommentEndDatePicker').datepicker({
        format: 'yyyy-mm-dd',
    }).on('changeDate', function (ev) {
        //$('#allCommentStartDatePicker').val($('#allCommentEndDatePicker').val());
    });

    $('#allCommentStartDatePicker').on(
        'focus', function () {
            $(this).blur();
        }
    );
    $('#allCommentEndDatePicker').on(
        'focus', function () {
            $(this).blur();
        }
    );
    $('#allCommentPeriodDays').change(function () {
        var now = new Date();
        var beforeMil = now.getTime() - ( $('#allCommentPeriodDays').val() - 1) * 86400000;
        var before = new Date(beforeMil);
        $('#allCommentEndDatePicker').val(now.getFullYear() + '-' + (now.getMonth() + 1) + '-' + now.getDate());
        $('#allCommentStartDatePicker').val(before.getFullYear() + '-' + (before.getMonth() + 1) + '-' + before.getDate());
    });
    $('#allCommentLimit').change(function () {
        if (allCommentsPageCount != $('#allCommentLimit').val()) {
            allCommentsPageCount = $('#allCommentLimit').val();
            getTableData('ALL', 0, $('#allCommentLimit').val(), $('#allCommentSearchField').val(), $('#allCommentSearchKeyword').val(), $('#allCommentStartDatePicker').val(), $('#allCommentEndDatePicker').val());
        }
    });
    $('#searchButton').on(
        'click', function () {
            getTableData('ALL', 0, $('#allCommentLimit').val(), $('#allCommentSearchField').val(), $('#allCommentSearchKeyword').val(), $('#allCommentStartDatePicker').val(), $('#allCommentEndDatePicker').val());
        }
    );
    $('#initSearchButton').on(
        'click', function () {

            $('#allCommentSearchCardButton').popover('hide');
            $('#reportAndHandleComments').popover('hide');
            $('[rel=popover]').popover('hide');

            var now = new Date();
            var beforeMil = now.getTime();
            var before = new Date(beforeMil);
            //$('#allCommentEndDatePicker').val('');
            //$('#allCommentStartDatePicker').val('');
            $('#allCommentSearchField').val('nickName');
            $('#allCommentSearchKeyword').val('');
            $('#allCommentPeriodDays').val(1);
            $('#allCommentContentsSearchKeyword').val('');

            contentsResult = '';
            selectContentsId = '';
            selectIndex = '';
            cardResult = '';
            selectCardId = '';
            refreshContentsDropDown();
            $('#allCommentSearchCardButton').popover('destroy');
        }
    );
    //
    var now = new Date();
    var beforeMil = now.getTime();
    var before = new Date(beforeMil - 86400000 * 29);
    $('#allCommentEndDatePicker').val(now.getFullYear() + '-' + (now.getMonth() + 1) + '-' + now.getDate());
    $('#allCommentStartDatePicker').val(before.getFullYear() + '-' + (before.getMonth() + 1) + '-' + before.getDate());
    $('#allCommentPeriodDays').val(30);

    $('#reportAndHandleCommentsHidd').popover({
        placement: 'top',
        trigger: 'click',
        html: true,
        title: '',
        content: '<table class=\'table btm-line\'><tbody><tr>' +
        '<td><label><input name=\'form-field-radio-reportType\' type=\'radio\' value="RT100"><span class=\'text\'>'+$.i18n.prop("ums.monitor.all.comment.report.type.a.rt100")+'</span></label></td>' +
        '<td><label><input name=\'form-field-radio-reportType\' type=\'radio\'value="RT200"><span class=\'text\'>'+$.i18n.prop("ums.monitor.all.comment.report.type.b.rt200")+'</span></label></td>' +
        '</tr><tr>' +
        '<td><label><input name=\'form-field-radio-reportType\' type=\'radio\'value="RT300"><span class=\'text\'>'+$.i18n.prop("ums.monitor.all.comment.report.type.c.rt300")+'</span></label></td>' +
        '<td><label><input name=\'form-field-radio-reportType\' type=\'radio\'value="RT400"><span class=\'text\'>'+$.i18n.prop("ums.monitor.all.comment.report.type.d.rt400")+'</span></label></td>' +
        '</tr><tr>' +
        '<td><label><input name=\'form-field-radio-reportType\' type=\'radio\'value="RT500"><span class=\'text\'>'+$.i18n.prop("ums.monitor.all.comment.report.type.e.rt500")+'</span></label></td>' +
        '<td><label><input name=\'form-field-radio-reportType\' type=\'radio\'value="RT600"><span class=\'text\'>'+$.i18n.prop("ums.monitor.all.comment.report.type.f.rt600")+'</span></label></td>' +
        '</tr><tr>' +
        '<td><label><input name=\'form-field-radio-reportHandleType\' type=\'radio\'value="HIDD" checked><span class=\'text\'>'+$.i18n.prop("ums.monitor.all.comment.report.type.blind")+'</span></label></td>' +
        '</tr></tbody></table>' +
        '<div class=\'btn-area text-center\'>' +
        '<a class=\'btn btn-darkorange btn-sm\'>'+$.i18n.prop("ums.monitor.all.comment.confirm")+'</a>' +
        '<a class=\'btn btn-default btn-sm\'>'+$.i18n.prop("ums.monitor.all.comment.cancel")+'</a></div>'
    });
    $('#reportAndHandleCommentsHidd').on('click', function () {
        $('#reportAndHandleCommentsDel').popover('hide');
        $('#reportAndHandleCommentsPass').popover('hide');
        $('#allCommentSearchCardButton').popover('hide');
        $('[rel=popover]').not(this).popover('hide');
    });
    $('#reportAndHandleCommentsHidd').on('shown.bs.popover', function () {
        $('.btn-area  .btn-default').on('click', function (e) {
            $('#reportAndHandleCommentsHidd').popover('hide');
        });
        $('.btn-area  .btn-darkorange').on('click', function (e) {
            var reportType = $('input[name=\'form-field-radio-reportType\']:checked').val();
            var reportHandleType = $('input[name=\'form-field-radio-reportHandleType\']:checked').val();

            if (reportType == undefined) {
                alert('chose a report type in a) to f).');
                return;
            } else if (reportHandleType == undefined) {
                alert('chose a report handle type in 5) and 7).');
                return;
            } else {
                $('#reportAndHandleCommentsHidd').popover('hide');
                totalReportAndHandleButtonClick('REPORTANDHANDLE', reportHandleType, reportType);
            }
        });
    });
    $('#reportAndHandleCommentsDel').popover({
        placement: 'top',
        trigger: 'click',
        html: true,
        title: '',
        content: '<table class=\'table btm-line\'><tbody><tr>' +
        '<td><label><input name=\'form-field-radio-reportType\' type=\'radio\' value="RT100"><span class=\'text\'>'+$.i18n.prop("ums.monitor.all.comment.report.type.a.rt100")+'</span></label></td>' +
        '<td><label><input name=\'form-field-radio-reportType\' type=\'radio\'value="RT200"><span class=\'text\'>'+$.i18n.prop("ums.monitor.all.comment.report.type.b.rt200")+'</span></label></td>' +
        '</tr><tr>' +
        '<td><label><input name=\'form-field-radio-reportType\' type=\'radio\'value="RT300"><span class=\'text\'>'+$.i18n.prop("ums.monitor.all.comment.report.type.c.rt300")+'</span></label></td>' +
        '<td><label><input name=\'form-field-radio-reportType\' type=\'radio\'value="RT400"><span class=\'text\'>'+$.i18n.prop("ums.monitor.all.comment.report.type.d.rt400")+'</span></label></td>' +
        '</tr><tr>' +
        '<td><label><input name=\'form-field-radio-reportType\' type=\'radio\'value="RT500"><span class=\'text\'>'+$.i18n.prop("ums.monitor.all.comment.report.type.e.rt500")+'</span></label></td>' +
        '<td><label><input name=\'form-field-radio-reportType\' type=\'radio\'value="RT600"><span class=\'text\'>'+$.i18n.prop("ums.monitor.all.comment.report.type.f.rt600")+'</span></label></td>' +
        '</tr><tr>' +
        '<td><label><input name=\'form-field-radio-reportHandleType\' type=\'radio\'value="DEL" checked><span class=\'text\'>'+$.i18n.prop("ums.monitor.all.comment.report.type.delete")+'</span></label></td>' +
        '</tr></tbody></table>' +
        '<div class=\'btn-area text-center\'>' +
        '<a class=\'btn btn-darkorange btn-sm\'>'+$.i18n.prop("ums.monitor.all.comment.confirm")+'</a>' +
        '<a class=\'btn btn-default btn-sm\'>'+$.i18n.prop("ums.monitor.all.comment.cancel")+'</a></div>'
    });

    $('#reportAndHandleCommentsDel').on('click', function () {
        $('#reportAndHandleCommentsHidd').popover('hide');
        $('#reportAndHandleCommentsPass').popover('hide');
        $('#allCommentSearchCardButton').popover('hide');
        $('[rel=popover]').popover('hide');
    });
    $('#reportAndHandleCommentsDel').on('shown.bs.popover', function () {
        $('.btn-area  .btn-default').on('click', function (e) {
            $('#reportAndHandleCommentsDel').popover('hide');
        });
        $('.btn-area  .btn-darkorange').on('click', function (e) {
            var reportType = $('input[name=\'form-field-radio-reportType\']:checked').val();
            var reportHandleType = $('input[name=\'form-field-radio-reportHandleType\']:checked').val();

            if (reportType == undefined) {
                alert('chose a report type in a) to f).');
                return;
            } else if (reportHandleType == undefined) {
                alert('chose a report handle type in 5) and 7).');
                return;
            } else {
                $('#reportAndHandleCommentsDel').popover('hide');
                totalReportAndHandleButtonClick('REPORTANDHANDLE', reportHandleType, reportType);
            }
        });
    });

    $('#reportAndHandleCommentsPass').popover({
        placement: 'top',
        trigger: 'click',
        html: true,
        title: '',
        content: '<div class=\'btn-area text-center\'>' +
        '<a class=\'btn btn-darkorange btn-sm\' id="passConfirm">'+$.i18n.prop("ums.monitor.all.comment.confirm")+'</a>' +
        '<a class=\'btn btn-default btn-sm\' id="passCancel">'+$.i18n.prop("ums.monitor.all.comment.cancel")+'</a></div>'
    });
    $('#reportAndHandleCommentsPass').on('click', function () {
        $('#reportAndHandleCommentsHidd').popover('hide');
        $('#reportAndHandleCommentsDel').popover('hide');
        $('#allCommentSearchCardButton').popover('hide');
        $('[rel=popover]').popover('hide');
    });
    $('#reportAndHandleCommentsPass').on('shown.bs.popover', function () {
        $('.btn-area  .btn-default').on('click', function (e) {
            $('#reportAndHandleCommentsPass').popover('hide');
        });
        $('.btn-area  .btn-darkorange').on('click', function (e) {
            $('#reportAndHandleCommentsPass').popover('hide');
            totalReportAndHandleButtonClick('REPORTANDHANDLE', 'PASS', null);
        });
    });

    $('#refreshButton').on('click', function () {
        getTableData('ALL', $('#allCommentLimit').val() * (currentPage - 1), $('#allCommentLimit').val(), $('#allCommentSearchField').val(), $('#allCommentSearchKeyword').val(), $('#allCommentStartDatePicker').val(), $('#allCommentEndDatePicker').val());
    });

    $('.page-body').on('click', function () {
        if (cardPopover) {
            $('#allCommentSearchCardButton').popover('hide');
        }
    });

    getTableData("ALL", 0, allCommentsPageCount, "", "", $('#allCommentStartDatePicker').val(), $('#allCommentEndDatePicker').val());

    $('#allCommentContentsSearchKeyword').on('click', function (e) {
        $('[rel=popover]').popover('hide');
        $('#reportAndHandleComments').popover('hide');
        $('#allCommentSearchCardButton').popover('hide');
    });
    $('#allCommentSearchCardButton').on('click', function (e) {
        $('[rel=popover]').popover('hide');
        $('#reportAndHandleComments').popover('hide');
    });
    $('#allCommentContentsSearchKeyword').keydown(function (a) {
        if (a.keyCode == 13 && $.trim($('#allCommentContentsSearchKeyword').val()) != '') {
            getContents();
        }
    });
    $('#allCommentContentsSearchButton').on('click', function (e) {
        if ($.trim($('#allCommentContentsSearchKeyword').val()) != '') {
            getContents();
        }
    });
    $('#allCommentContentsSearchKeyword').on('input propertychange',
        function () {
            if($('#allCommentContentsSearchKeyword').val()=="")
            {
                contentsResult = '';
                selectContentsId = '';
                selectIndex = '';
                cardResult = '';
                selectCardId = '';
                refreshContentsDropDown();
                $('#allCommentSearchCardButton').popover('destroy');
            }
        }
    );

});

function getCards() {
    var param = {
        q: encodeURI('contentsId=' + selectContentsId)
    };
    blockObj($('#allCommentSearchCardButton'));
    $('#allCommentSearchCardButton').popover('hide');
    $('#allCommentSearchCardButton').popover('destroy');
    $.ajax({
        method: "GET",
        url: "/v1.2/mgCards",
        headers: {
            "Accept-Language":$.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0,2),
            "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
        },
        data: param,
        success: function (data, status, jqXHR) {
            if (data.mgCardResultList != undefined && data.mgCardResultList != null) {
                cardResult = data.mgCardResultList;
            }
            else {
                cardResult = '';
            }
            refreshCardsPopover();
            $('#allCommentSearchCardButton').unblock();
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $('#allCommentSearchCardButton').unblock();
            if (XMLHttpRequest.status == "401") {
                window.location = "/login";
            }
            cardResult = '';
            selectCardId = '';
            $('#allCommentSearchCardButton').unblock();

        }
    });
}

function showCardsForEveryTypes(row, col) {
    var cdnUrl = '';
    if (window.location.hostname == 'ums.allets.com') {
        cdnUrl = 'https://s3.ap-northeast-2.amazonaws.com/an2-prd-allets-contents/';
    } else {
        cdnUrl = 'https://s3.ap-northeast-2.amazonaws.com/an2-prd-allets-contents/';
    }
    var html = '';
    if (cardResult[row * 3 + col].cardType == 'PHOTO') {
        html = html + '<div style="width: 74px; height: 74px;margin-left: ' + 78 * col + 'px;margin-top: ' + ((col == 0) ? 0 : (-74)) + 'px">' +
            '<img id="' + cardResult[row * 3 + col].cardId + '" style="width: 74px; height: 74px;" src="' + cdnUrl + cardResult[row * 3 + col].url + '"/>' +
            '<div style="width: 74px; margin-top:-74px;overflow:hidden;white-space:nowrap;text-overflow:ellipsis;" ><span class="label label-info">' + cardResult[row * 3 + col].cardType + '</span></div>' +
            '</div>';
    } else if (cardResult[row * 3 + col].cardType == 'DYNAMIC') {
        html = html + '<div style="width: 74px; height: 74px;margin-left: ' + 78 * col + 'px;margin-top: ' + ((col == 0) ? 0 : (-74)) + 'px">' +
            '<img id="' + cardResult[row * 3 + col].cardId + '" style="width: 74px; height: 74px;" src="' + cdnUrl + cardResult[row * 3 + col].url + '"/>' +
            '<div style="width: 74px; margin-top:-74px;overflow:hidden;white-space:nowrap;text-overflow:ellipsis;" ><span class="label label-info">' + cardResult[row * 3 + col].cardType + '</span></div>' +
            '</div>';
    } else if (cardResult[row * 3 + col].cardType == 'LANDING') {
        html = html + '<div style="width: 74px; height: 74px;margin-left: ' + 78 * col + 'px;margin-top: ' + ((col == 0) ? 0 : (-74)) + 'px">' +
            '<img id="' + cardResult[row * 3 + col].cardId + '" style="width: 74px; height: 74px;" src="' + cdnUrl + cardResult[row * 3 + col].url + '"/>' +
            '<div style="width: 74px; margin-top:-74px;overflow:hidden;white-space:nowrap;text-overflow:ellipsis;" ><span class="label label-info">' + cardResult[row * 3 + col].cardType + '</span></div>' +
            '</div>';
    } else if (cardResult[row * 3 + col].cardType == 'PANORAMA') {
        html = html + '<div style="width: 74px; height: 74px;margin-left: ' + 78 * col + 'px;margin-top: ' + ((col == 0) ? 0 : (-74)) + 'px">' +
            '<img id="' + cardResult[row * 3 + col].cardId + '" style="width: 74px; height: 74px;" src="' + cdnUrl + cardResult[row * 3 + col].videoThumbnailUrl + '"/>' +
            '<div style="width: 74px; margin-top:-74px;overflow:hidden;white-space:nowrap;text-overflow:ellipsis;" ><span class="label label-info">' + cardResult[row * 3 + col].cardType + '</span></div>' +
            '</div>';
    } else if (cardResult[row * 3 + col].cardType == 'SHOPPING') {
        html = html + '<div style="width: 74px; height: 74px;margin-left: ' + 78 * col + 'px;margin-top: ' + ((col == 0) ? 0 : (-74)) + 'px">' +
            '<img id="' + cardResult[i * 3].cardId + '" style="width: 74px; height: 74px;" src="' + cdnUrl + cardResult[i * 3].url + '"/>' +
            '<div style="width: 74px; margin-top:-74px;overflow:hidden;white-space:nowrap;text-overflow:ellipsis;" ><span class="label label-info">' + cardResult[row * 3 + col].cardType + '</span></div>' +
            '</div>';
    } else if (cardResult[row * 3 + col].cardType == 'TEXT') {
        html = html + '<div style="width: 74px; height: 74px;margin-left: ' + 78 * col + 'px;margin-top: ' + ((col == 0) ? 0 : (-74)) + 'px">' +
            '<img id="' + cardResult[row * 3 + col].cardId + '" style="width: 74px; height: 74px;" src="img/wight-background.jpg"/>' +
            '<div style="width: 74px; margin-top:-74px;overflow:hidden;white-space:nowrap;text-overflow:ellipsis;" ><span class="label label-info">' + cardResult[row * 3 + col].cardType + '</span></div>' +
            '<div style="width: 80px; margin-top:0px;overflow:hidden;white-space:nowrap;text-overflow:ellipsis;" >' + cardResult[row * 3 + col].description + '</div>' +
            '</div>';
    } else if (cardResult[row * 3 + col].cardType == 'VIDEO') {
        html = html + '<div style="width: 74px; height: 74px;margin-left: ' + 78 * col + 'px;margin-top: ' + ((col == 0) ? 0 : (-74)) + 'px">' +
            '<img id="' + cardResult[row * 3 + col].cardId + '" style="width: 74px; height: 74px;" src="' + cdnUrl + cardResult[row * 3 + col].videoThumbnailUrl + '"/>' +
            '<div style="width: 74px; margin-top:-74px;overflow:hidden;white-space:nowrap;text-overflow:ellipsis;" ><span class="label label-info">' + cardResult[row * 3 + col].cardType + '</span></div>' +
            '</div>';
    } else if (cardResult[row * 3 + col].cardType == 'YOUTUBE') {
        html = html + '<div style="width: 74px; height: 74px;margin-left: ' + 78 * col + 'px;margin-top: ' + ((col == 0) ? 0 : (-74)) + 'px">' +
            '<img id="' + cardResult[row * 3 + col].cardId + '" style="width: 74px; height: 74px;" src="' + cdnUrl + cardResult[row * 3 + col].videoThumbnailUrl + '"/>' +
            '<div style="width: 74px; margin-top:-74px;overflow:hidden;white-space:nowrap;text-overflow:ellipsis;" ><span class="label label-info">' + cardResult[row * 3 + col].cardType + '</span></div>' +
            '</div>';
    } else if (cardResult[row * 3 + col].cardType == 'SNS_TW'
        || cardResult[row * 3 + col].cardType == 'SNS_TB'
        || cardResult[row * 3 + col].cardType == 'SNS_IS'
        || cardResult[row * 3 + col].cardType == 'SNSEMBED'
        || cardResult[row * 3 + col].cardType == 'QUIZ'
        || cardResult[row * 3 + col].cardType == 'INTR'
        || cardResult[row * 3 + col].cardType == 'SNS_FB') {
        html = html + '<div style="width: 74px; height: 74px;margin-left: ' + 78 * col + 'px;margin-top: ' + ((col == 0) ? 0 : (-74)) + 'px">' +
            '<img id="' + cardResult[row * 3 + col].cardId + '" style="width: 74px; height: 74px;" src="img/wight-background.jpg"/>' +
            '<div style="width: 74px; margin-top:-74px;overflow:hidden;white-space:nowrap;text-overflow:ellipsis;" ><span class="label label-info">' + cardResult[row * 3 + col].cardType + '</span></div>' +
            '<div style="width: 80px; margin-top:0px;overflow:hidden;white-space:nowrap;text-overflow:ellipsis;" >' + cardResult[row * 3 + col].url + '</div>' +
            '</div>';
    }
    return html;
}
function refreshCardsPopover() {
    $('#allCommentSearchCardButton').popover('destroy');
    if (cardResult != '') {
        var rownum = parseInt(cardResult.length / 3) + 1;
        if (rownum > 5) {
            var html = '<div id="cards" style="margin-top:6px;width:260px;height: ' + 395 + 'px;overflow-y:scroll;overflow-x:hidden">';
        } else {
            var html = '<div id="cards" style="margin-top:6px;width:230px;height: ' + 78 * rownum + 'px;">';
        }
//TODO shouw cards
        for (var i = 0; i < rownum; i++) {
            html = html + '<div style="width: 240px; height: 77px;margin-top:4px">';
            if (cardResult[i * 3] != undefined && cardResult[i * 3] != null) {
                html = html + showCardsForEveryTypes(i, 0);
            }
            if (cardResult[i * 3 + 1] != undefined && cardResult[i * 3 + 1] != null) {
                html = html + showCardsForEveryTypes(i, 1);
            }
            if (cardResult[i * 3 + 2] != undefined && cardResult[i * 3 + 2] != null) {
                html = html + showCardsForEveryTypes(i, 2);
            }
            html = html + '</div>';
        }
        html = html + '</div>';
        $('#allCommentSearchCardButton').popover({
            placement: 'bottom',
            trigger: 'click',
            html: true,
            title: '',
            content: html
        });
        $('#allCommentSearchCardButton').on('hidden.bs.popover', function () {
            cardPopover = false;
        });
        $('#allCommentSearchCardButton').on('shown.bs.popover', function () {
                cardPopover = true;
                $('img').each(
                    function () {
                        $(this).on('click', function () {
                            selectCardId = $(this).attr('id');
                            getTableData('ALL', 0, $('#allCommentLimit').val(), $('#allCommentSearchField').val(), $('#allCommentSearchKeyword').val(), $('#allCommentStartDatePicker').val(), $('#allCommentEndDatePicker').val());
                        });
                        if ($(this).next().length > 0) {
                            $(this).next().on('click', function () {
                                selectCardId = $(this).attr('id');
                                getTableData('ALL', 0, $('#allCommentLimit').val(), $('#allCommentSearchField').val(), $('#allCommentSearchKeyword').val(), $('#allCommentStartDatePicker').val(), $('#allCommentEndDatePicker').val());
                            });
                        }
                        if ($(this).next().next().length > 0) {
                            $(this).next().next().on('click', function () {
                                selectCardId = $(this).attr('id');
                                getTableData('ALL', 0, $('#allCommentLimit').val(), $('#allCommentSearchField').val(), $('#allCommentSearchKeyword').val(), $('#allCommentStartDatePicker').val(), $('#allCommentEndDatePicker').val());
                            });
                        }
                    }
                );
            }
        );
    }
}
function getContents() {
    $('#allCommentSearchCardButton').popover('hide');
    $('#reportAndHandleComments').popover('hide');
    $('[rel=popover]').popover('hide');
    blockObj($('div.page-content'));
    var param = {
        q: encodeURI('title=' + $('#allCommentContentsSearchKeyword').val())
    };
    contentsResult = '';
    $.ajax({
        method: "GET",
        url: "/v1.2/mgContents",
        headers: {
            "Accept-Language":$.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0,2),
            "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
        },
        data: param,
        success: function (data, status, jqXHR) {
            if (data.mgContentsResultList != undefined && data.mgContentsResultList != null) {
                contentsResult = data.mgContentsResultList;
            }
            else {
                contentsResult = '';
            }
            refreshContentsDropDown();
            $('div.page-content').unblock();
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            if (XMLHttpRequest.status == "401") {
                window.location = "/login";
            }
            contentsResult = '';
            selectContentsId = '';
            selectIndex = '';
            refreshContentsDropDown();
            $('div.page-content').unblock();
        }
    });
}

function refreshContentsDropDown() {
    var dropDownHtml = '';
    if (contentsResult == '') {
        dropDownHtml = '<li id=\'searchTip\'><a>no result.please search again. </a></li>';
        $('#searchResultDropDown').html(dropDownHtml)
        $('#allCommentContentsSearchKeyword').parent().addClass('open');
        $('#allCommentContentsSearchKeyword').attr('aria-expanded', 'true');
        return;
    }
    for (var x in contentsResult) {
        if (contentsResult[x].contentsId == selectContentsId) {
            dropDownHtml = dropDownHtml + '<li value=' + x + ' class=\'label label-green col-lg-12\'><a class=\'col-md-12\'>' +
                '<i class=\'col-md-9\'>' + contentsResult[x].title + '</i>' +
                '<i class=\'col-md-3\'>' + contentsResult[x].contentsId + '</i>' +
                '</a></li>';
        } else {
            dropDownHtml = dropDownHtml + '<li value=' + x + ' class=\'col-lg-12\'><a class=\'col-md-12\'>' +
                '<i class=\'col-md-9\'>' + contentsResult[x].title + '</i>' +
                '<i class=\'col-md-3\'>' + contentsResult[x].contentsId + '</i>' +
                '</a></li>';
        }
    }
    $('#searchResultDropDown').html(dropDownHtml);
    $('#searchResultDropDown li').on('click', function () {
            if (contentsResult != undefined && contentsResult != null) {
                selectContentsId = contentsResult[$(this).val()].contentsId;
                selectIndex = $(this).val();
                selectCardId = '';
                cardResult = '';
                $('#allCommentContentsSearchKeyword').val(contentsResult[$(this).val()].title);
                //$('#allCommentContentsSearchKeyword').val(contentsResult[$(this).val()].title.replace('\n','\\n'));
                refreshContentsDropDown();
                getCards();
                getTableData('ALL', 0, $('#allCommentLimit').val(), $('#allCommentSearchField').val(), $('#allCommentSearchKeyword').val(), $('#allCommentStartDatePicker').val(), $('#allCommentEndDatePicker').val());
            }
        }
    );
    $('#allCommentContentsSearchKeyword').parent().addClass('open');
    $('#allCommentContentsSearchKeyword').attr('aria-expanded', 'true');
}
function getTableData(action, offset, limit, qfield, q, startDate, endDate) {
    $('#allCommentSearchCardButton').popover('hide');
    $('#reportAndHandleComments').popover('hide');
    $('[rel=popover]').popover('hide');
    var param = {
        action: action,
        offset: offset,
        limit: limit
    };

    //var date = new Date(Date.parse(endDate.replace(/-/g, "/")));
    //var endTime = date.getTime();
    //var startDateObj = new Date(endTime);
    //var endDateObj = new Date(endTime + 86400000);
    //var startString = startDateObj.getFullYear() + '-' + (startDateObj.getMonth() + 1) + '-' + startDateObj.getDate();
    //var endString = endDateObj.getFullYear() + '-' + (endDateObj.getMonth() + 1) + '-' + endDateObj.getDate();

    if (($.trim(qfield) != '' && $.trim(q) != '') ||
            //$.trim(startDate) != '' || $.trim(endDate) != ''||
        selectCardId != '' || selectContentsId != '') {
        if ($.trim(qfield) != '' && $.trim(q) != '') {
            q = qfield + '=' + q;
        }
        //if ($.trim(startDate) != '') {
        //    if ($.trim(q) != '') {
        //        q = q + ",";
        //    }
        //    q = q + "startDate=" + startDate;
        //}
        //if ($.trim(endDate) != '') {
        //    if ($.trim(q) != '') {
        //        q = q + ",";
        //    }
        //    var end = new Date(Date.parse(endDate.replace(/-/g, "/")));
        //    var endDateObj = new Date(end.getTime() + 86400000);
        //    var endString = endDateObj.getFullYear() + '-' + (endDateObj.getMonth() + 1) + '-' + endDateObj.getDate();
        //    q = q + "endDate=" + endString;
        //}
        if (selectCardId != '') {
            if ($.trim(q) != '') {
                q = q + ",";
            }
            q = q + "cardId=" + selectCardId;
        }
        if (selectContentsId != '' && selectCardId == '') {
            if ($.trim(q) != '') {
                q = q + ",";
            }
            q = q + "contentsId=" + selectContentsId;
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
            refreshAllCommentTable(fnInfoCallback, footerCallback, fnCreatedRow, fnInitComplete);
            currentPage = data.pageImpl.number + 1;
            initPager(
                {
                    container: '#pager',
                    totalPage: data.pageImpl.totalPages,
                    currentPage: currentPage,
                    pageButtonStyle: 0,//0是line样式 1是swing样式
                    pageButtonNum: 10,
                    buttonCallback: function (pageIndex, newCurrentPage) {
                        getTableData('ALL', (newCurrentPage - 1) * $('#allCommentLimit').val(), $('#allCommentLimit').val(), $('#allCommentSearchField').val(), $('#allCommentSearchKeyword').val(), $('#allCommentStartDatePicker').val(), $('#allCommentEndDatePicker').val());
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
            $('div.page-content').unblock();
        }
    });
}

function refreshAllCommentTable(fnInfoCallback, footerCallback, fnCreatedRow, fnInitComplete) {
    if (allCommentsTable != null)
        allCommentsTable.fnDestroy();
    allCommentsTable = $('#allCommentTable').dataTable({
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
            {'mDataProp': 'slangType'},
            {'mDataProp': 'commentId'},
            {'mDataProp': 'uid'},
            {'mDataProp': 'monitorName'},
            {'mDataProp': null}
        ],
        'aoColumnDefs': [
            {'aTargets': 2, orderable: false},
            {'aTargets': 3, orderable: false},
            {'aTargets': 4, orderable: false},
            {'aTargets': 5, orderable: false},
            {'aTargets': 6, orderable: false},
            {'aTargets': 7, orderable: false},
            {'aTargets': 8, orderable: false},
            {'aTargets': 9, orderable: false},
            {'aTargets': 10, orderable: false},
            {'aTargets': 11, orderable: false},
            {'aTargets': 12, orderable: false}
        ],
        "aaSorting": [[1, "desc"]],
        'bAutoWidth': false,
        'bInfo': false,
        'bFilter': false,
        'bPaginate': false,
        'fnInfoCallback': fnInfoCallback,
        'footerCallback': footerCallback,
        'fnCreatedRow': fnCreatedRow,
        'fnInitComplete': fnInitComplete
    });
}
var fnCreatedRow = function (nRow, aData, iDataIndex) {

    if (aData.slangType == 'A') {
        nRow.className = 'danger';
    }
    if (aData.status == 'DEL')
        $('td:eq(0)', nRow).html('<lable><span class="text">-</span></lable>');
    else if (aData.status == 'HIDD')
        $('td:eq(0)', nRow).html('<lable><span class="text">-</span></lable>');
    else
        $('td:eq(0)', nRow).html('<lable><input type="checkbox"><span class="text"></span></lable>');
    if (aData.uid == '' || aData.uid == undefined) {
        $('td:eq(4)', nRow).html('<a class="gray"  target="_blank">No User</a>');
    } else if (aData.nickName == '' || aData.nickName == undefined) {
        $('td:eq(4)', nRow).html('<a class="danger"  target="_blank" href="accountInfo?uid=' + aData.uid + '">No Name</a>');
    } else {
        $('td:eq(4)', nRow).html('<a target="_blank" href="accountInfo?uid=' + aData.uid + '">' + aData.nickName + '</a>');
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
    $('td:eq(5)', nRow).html('<a href="javascript:showModal(' + aData.commentId + ',' + aData.contentId + ',' + cardId + ',' + cardOrder + ',' + parentCommentId + ')">' + aData.commentText + '</a>');

    if (aData.likeCount == 0) {
        $('td:eq(6)', nRow).html('-');
    }
    if (aData.recommentCount == 0) {
        $('td:eq(7)', nRow).html('-');
    }

    //if (aData.status == 'DEL') {
    //    $('td:eq(13)', nRow).html('<span class=\'label label-danger\'>삭제</span>');
    //}
    //else if (aData.status == 'HIDD') {
    //    $('td:eq(13)', nRow).html('<span class=\'label label-info\'>블라인드</span>');
    //}
    //else {
    //    $('td:eq(12)', nRow).html('');
    $('td:eq(13)', nRow).html('<a class=\'btn btn-blue btn-sm\' rel=\'popover\'>'+$.i18n.prop("ums.monitor.all.comment.select")+'</a>');
    //}

};
var fnInfoCallback = function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
};

function checkBoxesClickFixed() {
    $('#allCommentTable tbody tr span[class=text]').on('click', function () {
        var aData = allCommentsTable.fnGetData($(this).parents('tr'));
        var checked = $(this).prev('input').is(":checked");
        if (checked) {
            $(this).prev('input').prop("checked", false);
            delete selectedItem['c' + aData.commentId];
        } else {
            $(this).prev('input').prop("checked", true);
            selectedItem['c' + aData.commentId] = $(this).parents('tr');
        }
    });
    $('#allCommentTable thead th input[type=checkbox]').change(function () {
        var set = $("#allCommentTable tbody tr input[type=checkbox]");
        var checked = $(this).is(":checked");
        $(set).each(function () {
            var aData = allCommentsTable.fnGetData($(this).parents('tr'));
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

};

var footerCallback = function (row, data, start, end, display) {
};
var fnInitComplete = function (oSettings, json) {
    $('.loading-container')
        .addClass('loading-inactive');
    checkBoxesClickFixed();
    $('[rel=popover]').each(function (index, html) {
        $(this).popover({
            placement: 'left',
            trigger: 'click',
            html: true,
            title: '',
            content: '<table class=\'table btm-line\'><tbody><tr>' +
            '<td><label><input name=\'form-field-radio-reportType\' type=\'radio\' value="RT100"><span class=\'text\'>'+$.i18n.prop("ums.monitor.all.comment.report.type.a.rt100")+'</span></label></td>' +
            '<td><label><input name=\'form-field-radio-reportType\' type=\'radio\'value="RT200"><span class=\'text\'>'+$.i18n.prop("ums.monitor.all.comment.report.type.b.rt200")+'</span></label></td>' +
            '</tr><tr>' +
            '<td><label><input name=\'form-field-radio-reportType\' type=\'radio\'value="RT300"><span class=\'text\'>'+$.i18n.prop("ums.monitor.all.comment.report.type.c.rt300")+'</span></label></td>' +
            '<td><label><input name=\'form-field-radio-reportType\' type=\'radio\'value="RT400"><span class=\'text\'>'+$.i18n.prop("ums.monitor.all.comment.report.type.d.rt400")+'</span></label></td>' +
            '</tr><tr>' +
            '<td><label><input name=\'form-field-radio-reportType\' type=\'radio\'value="RT500"><span class=\'text\'>'+$.i18n.prop("ums.monitor.all.comment.report.type.e.rt500")+'</span></label></td>' +
            '<td><label><input name=\'form-field-radio-reportType\' type=\'radio\'value="RT600"><span class=\'text\'>'+$.i18n.prop("ums.monitor.all.comment.report.type.f.rt600")+'</span></label></td>' +
            '</tr><tr>' +
            '<td><label><input name=\'form-field-radio-reportHandleType\' type=\'radio\'value="HIDD" checked><span class=\'text\'>'+$.i18n.prop("ums.monitor.all.comment.report.type.blind")+'</span></label></td>' +
            '<td><label><input name=\'form-field-radio-reportHandleType\' type=\'radio\'value="DEL" checked><span class=\'text\'>'+$.i18n.prop("ums.monitor.all.comment.report.type.delete")+'</span></label></td>' +
            '</tr></tbody></table>' +
            '<div class=\'btn-area text-center\'>' +
            '<a class=\'btn btn-darkorange btn-sm\'>'+$.i18n.prop("ums.monitor.all.comment.confirm")+'</a>' +
            '<a class=\'btn btn-default btn-sm\'>'+$.i18n.prop("ums.monitor.all.comment.cancel")+'</a></div>'
        });
    });

    $('[rel=popover]').on('click', function () {
        $('[rel=popover]').not(this).popover('hide');
        $('#reportAndHandleComments').popover('hide');
        $('#allCommentSearchCardButton').popover('hide');
    });
    $('[rel=popover]').on('shown.bs.popover', function () {
        var obj = $(this).parents('tr');
        $('.btn-area  .btn-default').on('click', function (e) {
            $('[rel=popover]').popover('hide');
            $('#reportAndHandleComments').popover('hide');
        });
        $('.btn-area  .btn-darkorange').on('click', function (e) {
            var reportType = $('input[name=\'form-field-radio-reportType\']:checked').val();
            var reportHandleType = $('input[name=\'form-field-radio-reportHandleType\']:checked').val();
            if (reportType == undefined) {
                alert('chose a report type in a) to f).');
                return;
            } else if (reportHandleType == undefined) {
                alert('chose a report handle type in 5) and 7).');
                return;
            } else {
                $('#allCommentSearchCardButton').popover('hide');
                $('#reportAndHandleComments').popover('hide');
                $('[rel=popover]').popover('hide');
                reportAndHandleButtonClick('REPORTANDHANDLE', reportHandleType, reportType, obj);
            }
        });
    });

};

function reportAndHandleButtonClick(action, status, reportType, obj) {
    var putCommentIds = new Array();
    var aData = allCommentsTable.fnGetData(obj);
    if (aData != null) {
        putCommentIds.push(aData.commentId);
        //var parseItem = {};
        //parseItem['c' + aData.commentId] = obj;
        allCommentsTable.fnDeleteRow(obj);
        putReportAndHandleComment(action, status, reportType, putCommentIds, undefined);
    }
}
function totalReportAndHandleButtonClick(action, status, reportType) {
    var putCommentIds = new Array();
    for (var itemId in selectedItem) {
        var aData = allCommentsTable.fnGetData(selectedItem[itemId]);
        if (aData != null && putCommentIds.indexOf(aData.commentId) == -1) {
            putCommentIds.push(aData.commentId);
        }
        allCommentsTable.fnDeleteRow(selectedItem[itemId]);
        delete selectedItem[itemId];
    }
    if (putCommentIds.length == 0 || action == '') {
        return;
    }

    putReportAndHandleComment(action, status, reportType, putCommentIds, undefined);
}
$(document).keyup(function (event) {
    if (!$('input[name=\'form-field-radio-reportType\']')[0]) {
        return;
    }
    switch (event.keyCode) {
        case 65:
            $('input[name=\'form-field-radio-reportType\']')[0].checked = true;
            break;
        case 66:
            $('input[name=\'form-field-radio-reportType\']')[1].checked = true;
            break;
        case 67:
            $('input[name=\'form-field-radio-reportType\']')[2].checked = true;
            break;
        case 68:
            $('input[name=\'form-field-radio-reportType\']')[3].checked = true;
            break;
        case 69:
            $('input[name=\'form-field-radio-reportType\']')[4].checked = true;
            break;
        case 70:
            $('input[name=\'form-field-radio-reportType\']')[5].checked = true;
            break;
        case 53:
            $('input[name=\'form-field-radio-reportHandleType\']')[0].checked = true;
            break;
        case 55:
            $('input[name=\'form-field-radio-reportHandleType\']')[1].checked = true;
            break;
        case 13:
            $('.btn-area  .btn-darkorange').click();
            break;
        case 27:
            $('#allCommentSearchCardButton').popover('hide');
            $('#reportAndHandleComments').popover('hide');
            $('[rel=popover]').popover('hide');
            break;
    }
});

function putReportAndHandleComment(action, status, reportType, commentIds, objs) {
    var commentIdString = commentIds.join(',');
    var param = {
        action: action,
        status: status,
        reportType: reportType,
        commentIds: commentIdString
    };
    blockObj($('div.page-content'));
    $.ajax({
        method: "PUT",
        url: "/v1.0/comments",
        headers: {
            "Accept-Language":$.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0,2),
            "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
        },
        data: param,
        success: function (data, status, jqXHR) {
            $('div.page-content').unblock();
            var commentIdArray = data.commentIds.split(',');
            var monitorName = data.monitorName;
            if (commentIdArray) {
                if (data.commentIds.indexOf(',') != -1) {
                    for (var index in commentIdArray) {
                        var commentId = commentIdArray[index];
                        if (objs != undefined) {
                            objs['c' + commentId].find('input').remove();
                            objs['c' + commentId].find('span').html('-');
                            objs['c' + commentId].find('.btn').parents('td').prev().html(monitorName);
                            if (data.status == 'HIDD')
                                objs['c' + commentId].find('.btn').replaceWith('<span class=\'label label-info\'>'+$.i18n.prop("ums.report.comment.one.blind")+'</span>');
                            else if (data.status == 'DEL')
                                objs['c' + commentId].find('.btn').replaceWith('<span class=\'label label-danger\'>'+$.i18n.prop("ums.report.comment.one.delete")+'</span>');
                            delete objs['c' + commentId];
                        }
                        if (selectedItem['c' + commentId]) {
                            delete selectedItem['c' + commentId];
                        }
                    }
                } else if (data.commentIds.indexOf(',') == -1) {
                    if (objs != undefined) {
                        objs['c' + commentIdArray].find('input').remove();
                        objs['c' + commentIdArray].find('span').html('-');
                        objs['c' + commentIdArray].find('.btn').parents('td').prev().html(monitorName);
                        if (data.status == 'HIDD')
                            objs['c' + commentIdArray].find('.btn').replaceWith('<span class=\'label label-info\'>'+$.i18n.prop("ums.report.comment.one.blind")+'</span>');
                        else if (data.status == 'DEL')
                            objs['c' + commentIdArray].find('.btn').replaceWith('<span class=\'label label-danger\'>'+$.i18n.prop("ums.report.comment.one.delete")+'</span>');
                        delete objs['c' + commentIdArray];
                    }
                    if (selectedItem['c' + commentIdArray]) {
                        delete selectedItem['c' + commentIdArray];
                    }
                }
            }
        },

        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $('div.page-content').unblock();
            if (XMLHttpRequest.status == "401") {
                window.location = "/login";
            }
        }
    });
}
