/**
 * Black list
 */

var resultTable = null;
var tableData;
var monitorId;
var currentPage = 1;
var searchType;
var keyword;
var startDate;
var endDate;
var offset = 0;
var limit;

$(document).ready(function () {
    $('.page-body').css('height', $(window).height() - 85);
    window.common.init();
    findBlackUser();
})

$(window).resize(function () {
    $('.page-body').css('height', $(window).height() - 85);
});

var common = {
    init: function () {

        initSearchParams();

        $('#startDatePicker').datepicker({
            format: 'yyyy-mm-dd',
        });
        $('#endDatePicker').datepicker({
            format: 'yyyy-mm-dd',
        });
        $('#searchType').change(function () {
            searchType = $("#searchType").val();
        });
        $('#pageSize').change(function () {
            limit = $('#pageSize').val();
            currentPage = 1;
            findBlackUser();
        });
        $('#kewordInput').keydown(function (a) {
            if (a.keyCode == 13) {
                $('#startDatePicker').val('');
                $('#endDatePicker').val('');
                currentPage = 1;
                findBlackUser();
            }
        });
        $('#searchButton').on('click', function () {
            findBlackUser();
        });
        $('#resetButton').on('click', function () {
            initSearchParams();
            findBlackUser();
        });

        $('#periodDays').change(function () {
            var now = new Date();
            var beforeMil = now.getTime() - $('#periodDays').val() * 86400000;
            var before = new Date(beforeMil);
            $('#endDatePicker').val(now.getFullYear() + '-' + (now.getMonth() + 1) + '-' + now.getDate());
            $('#startDatePicker').val(before.getFullYear() + '-' + (before.getMonth() + 1) + '-' + before.getDate());
            startDate = $('#startDatePicker').val();
            endDate = $('#endDatePicker').val();

            $('#kewordInput').val('');
            currentPage = 1;
            findBlackUser();
        });
    }
};


function findBlackUser() {
    showLoading();
    $.ajax({
        method: "GET",
        url: "/v1.0/users/black",
        headers: {
            "Accept-Language":$.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0,2),
            "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
        },
        data: getQueryData(),
        success: function (data) {
            hideLoading();
            initTable(data.results.content);
            initPagerFooter(data.results);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            hideLoading();
            //TODO: handle other status
            if (XMLHttpRequest.status == "401") {
                window.location = "/login";
            }
        }
    });
}

function removeBlackUser(blackId) {
    showLoading();
    $.ajax({
        method: "DELETE",
        url: "/v1.0/users/black/"+blackId,
        headers: {
            "Accept-Language":$.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0,2),
            "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
        },
        success: function (data) {
            findBlackUser();
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            hideLoading();
            //TODO: handle other status
            if (XMLHttpRequest.status == "401") {
                window.location = "/login";
            }
        }
    });
}

function initTable(data) {
    if (resultTable != null)
        resultTable.fnDestroy();
    tableData = data;
    resultTable = $("#blackUserTable").dataTable({
        data: data,
        'bPaginate': false,
        "bDestory": false,
        "bRetrieve": false,
        "bFilter": false,
        "bSort": false,
        "bProcessing": false,
        "bInfo": false,
        'aoColumns': [
            {'mDataProp': 'id'},
            {'mDataProp': 'nickName'},
            {'mDataProp': 'cdate'},
            {'mDataProp': 'reportedCount'},
            {'mDataProp': 'commentDeleteCount'},
            {'mDataProp': 'followerCount'},
            {'mDataProp': 'email'},
            {'mDataProp': 'uid'},
            {'mDataProp': 'monitorName'},
            {'mDataProp': null}
        ],
        "fnCreatedRow": function (nRow, aData, iDataIndex) {

            $('td:eq(9)', nRow).html("<a class=\'btn btn-blue btn-sm\' onclick='removeBlackUser(\"" + aData.id + "\")'>" + $.i18n.prop("ums.account.blacklist.deregistration") +"</a>");

            return nRow;
        }
    });
    return resultTable;
}

function initPagerFooter(data) {
    currentPage = (data.number + 1);
    initPager(
        {
            container: '#pager',
            totalPage: data.totalPages,
            currentPage: currentPage,
            pageButtonStyle: 0,
            pageButtonNum: 10,
            buttonCallback: function (pageIndex, newCurrentPage) {
                currentPage = newCurrentPage;
                findBlackUser();
            }
        }
    );
}

function initSearchParams() {
    $('#searchType')[0].selectedIndex = 0;
    $('#pageSize')[0].selectedIndex = 1;
    $('#periodDays')[0].selectedIndex = 0;

    $('#startDatePicker').val('');
    $('#endDatePicker').val('');
    $('#kewordInput').val('');

    searchType = $("#searchType").val();
    limit = $("#pageSize").val();
    startDate = '';
    endDate = '';
    keyword = '';
    offset = 0;
    currentPage = 1;

}
function getQueryData() {
    keyword = $('#kewordInput').val();
    startDate = $('#startDatePicker').val();
    endDate = $('#endDatePicker').val();
    var params = "";
    if ($.trim(keyword) != '') {
        if ($.trim(params) != '') {
            params = params + ",";
        }
        params = params + "" + searchType + "=" + $.trim(keyword) + "";
    }

    if ($.trim(startDate) != '') {
        if ($.trim(params) != '') {
            params = params + ",";
        }
        params = params + "startDate=" + startDate + "";
    }

    if ($.trim(endDate) != '') {
        if ($.trim(params) != '') {
            params = params + ",";
        }
        var endDateObj = new Date(new Date(endDate).getTime() + 86400000);
        var endString = endDateObj.getFullYear() + '-' + (endDateObj.getMonth() + 1) + '-' + endDateObj.getDate();
        params = params + "endDate=" + endString + "";
    }


    var data = {
        q: encodeURI(params),
        offset: limit * (currentPage - 1),
        limit: limit
    };
    return data;
}

function showLoading() {
    $('div.page-content').block({
        message: '<img src="/img/loading.gif"/>',
        baseZ: 1000,
        ignoreIfBlocked: true,
        css: {
            border: 'none',
            backgroundColor: '#00000000',
            color: '#fff',
            cursor: 'wait',
        }
    });
}

function hideLoading() {
    $('div.page-content').unblock();
}