var statisticsResult = '';
var monitorsResult = '';
var statisticsTable = null;
var selectedItem = {};
var delay = 0;
var delayId = '';
$(document).ready(function () {
    $('.page-body').css('height', $(window).height() - 85);
    window.common.init();
    findMonitorAll();
    $('#download').click(function () {
        if (delay == 0) {
            delay = 15;
            delayId = setInterval("countDealy()", 1000);
            download();
        }
    });
})
function countDealy() {
    console.log(delay);
    delay = delay - 1;
    if (delay == 0) {
        clearInterval(delayId);
        $('#download').html('DOWNLOAD');
    } else {
        $('#download').html('(' + delay + ')DOWNLOAD')
    }
}
function download() {
    var startDate = $('#statisticsStartDatePicker').val();
    var endDate = $('#statisticsEndDatePicker').val();
    if ($.trim(endDate) != '') {
        var end = new Date(Date.parse(endDate.replace(/-/g, "/")));
        var endDateObj = new Date(end.getTime() + 86400000);
        endDate = endDateObj.getFullYear() + '-' + (endDateObj.getMonth() + 1) + '-' + endDateObj.getDate();
    }
    $('#downloadURL').attr("src", "/v1.2/monitors/statistics?" + encodeURI("q=startDate=" + startDate + "," + "endDate=" + endDate));
}
var common = {
    init: function () {
        $('#statisticsStartDatePicker').datepicker({
            format: 'yyyy-mm-dd'
        }).on('changeDate', function (ev) {
        }).on('hide', function (ev) {
        });
        $('#statisticsEndDatePicker').datepicker({
            format: 'yyyy-mm-dd',
        });
        $('#statisticsStartDatePicker').on(
            'focus', function () {
                $(this).blur();
            }
        );
        $('#statisticsEndDatePicker').on(
            'focus', function () {
                $(this).blur();
            }
        );
        $('#searchButton').on(
            'click', function () {
                if (!jQuery.isEmptyObject(selectedItem)) {
                    var monitorIds = '';
                    for (var i in selectedItem) {
                        monitorIds = monitorIds + selectedItem[i] + ',';
                    }
                    findMonitorHandleHistory(monitorIds, $('#statisticsStartDatePicker').val(), $('#statisticsEndDatePicker').val());
                }
            }
        );
        $('#initSearchButton').on(
            'click', function () {
                $('#statisticsStartDatePicker').val('');
                $('#statisticsEndDatePicker').val('');
                $('#statisticsPeriodDays').val('');
            }
        );
        $('#statisticsPeriodDays').val('');
        $('#statisticsPeriodDays').change(function () {
            var now = new Date();
            var beforeMil = now.getTime() - ( $('#statisticsPeriodDays').val() - 1) * 86400000;
            var before = new Date(beforeMil);
            $('#statisticsEndDatePicker').val(now.getFullYear() + '-' + (now.getMonth() + 1) + '-' + now.getDate());
            $('#statisticsStartDatePicker').val(before.getFullYear() + '-' + (before.getMonth() + 1) + '-' + before.getDate());
        });
    }

};
function checkBoxesClickFixed() {
    $('#statisticsTable thead th input[type=checkbox]').change(function () {
        var set = $("#statisticsTable tbody tr input[type=checkbox]");
        var checked = $(this).is(":checked");
        $(set).each(function () {
            var aData = statisticsTable.fnGetData($(this).parents('tr'));
            if (checked) {
                $(this).prop("checked", true);
                //$(this).parents('tr').addClass("active");
                selectedItem['m' + aData.monitorId] = aData.monitorId;
            } else {
                $(this).prop("checked", false);
                //$(this).parents('tr').removeClass("active");
                delete selectedItem['m' + aData.monitorId];
            }

        });
    });
    $('#statisticsTable tbody tr span[class=text]').on('click', function () {
        var aData = statisticsTable.fnGetData($(this).parents('tr'));
        var checked = $(this).prev('input').is(":checked");
        if (checked) {
            $(this).prev('input').prop("checked", false);
            delete selectedItem['m' + aData.monitorId];
        } else {
            $(this).prev('input').prop("checked", true);
            selectedItem['m' + aData.monitorId] = aData.monitorId;
        }
    });
}

function initTable(footerCallback, fnCreatedRow, fnInitComplete) {
    if (statisticsTable != null)
        statisticsTable.fnDestroy();
    statisticsTable = $('#statisticsTable').dataTable({
        data: statisticsResult,
        'aoColumns': [
            {'mDataProp': 'monitorId'},
            {'mDataProp': 'name'},
            {'mDataProp': 'deleteCommentsCount'},
            {'mDataProp': 'sendAlertCount'},
            {'mDataProp': 'accountHoldCount'},
            {'mDataProp': 'accountDeleteCount'},
            {'mDataProp': 'accountResetCount'},
            {'mDataProp': 'lastAccessDate'}
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
        //"aaSorting": [[5, "desc"]],
        'bAutoWidth': false,
        'bInfo': false,
        'bFilter': false,
        'bPaginate': false,
        'footerCallback': footerCallback,
        'fnCreatedRow': fnCreatedRow,
        'fnInitComplete': fnInitComplete
    });
}
function initStatisticsResult(results) {
    statisticsResult = new Array();
    for (var i in monitorsResult) {
        var arr = {
            monitorId: monitorsResult[i].monitorId,
            name: monitorsResult[i].name,
            deleteCommentsCount: '-',
            sendAlertCount: '-',
            accountHoldCount: '-',
            accountDeleteCount: '-',
            accountResetCount: '-',
            lastAccessDate: '-'
        }
        if (results != null || results != undefined) {
            for (var j in results) {
                if (monitorsResult[i].monitorId == results[j].monitorId) {
                    arr = results[j];
                }
            }
        }

        statisticsResult.push(arr);
    }
}
function findMonitorHandleHistory(monitorIds, startDate, endDate) {
    blockObj($('div.page-content'));
    var param = {
        monitorIds: monitorIds
    };
    var q = '';
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
    $.ajax({
        method: "GET",
        url: "/v1.2/monitors/handleHistory",
        data: param,
        success: function (data) {
            $('div.page-content').unblock();
            initStatisticsResult(data.monitorStatisticsList);
            initTable(null, fnCreatedRow, fnInitComplete);
            $('#statisticsTable tbody tr span[class=text]').each(
                function (index, html) {
                    for (var i in selectedItem) {
                        if (selectedItem[i] == statisticsTable.fnGetData($(this).parents('tr')).monitorId) {
                            $(this).prev('input').prop("checked", true);
                        }
                    }
                }
            )
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $('div.page-content').unblock();
            //TODO: handle other status
            if (XMLHttpRequest.status == "401") {
                window.location = "/login";
            }
        }
    });
}
function findMonitorAll() {
    blockObj($('div.page-content'));
    $.ajax({
        method: "GET",
        url: "/v1.0/monitors",
        success: function (data) {
            $('div.page-content').unblock();
            monitorsResult = data.results;
            initStatisticsResult(null);
            initTable(null, fnCreatedRow, fnInitComplete);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $('div.page-content').unblock();
            //TODO: handle other status
            if (XMLHttpRequest.status == "401") {
                window.location = "/login";
            }
        }
    });
}
var fnInitComplete = function (oSettings, json) {
    checkBoxesClickFixed();
};
var fnCreatedRow = function (nRow, aData, iDataIndex) {
    $('td:eq(0)', nRow).html('<lable><input type="checkbox"><span class="text"></span></lable>');
};

function JSONToCSVConvertor(JSONData, ReportTitle, ShowLabel) {
    //If JSONData is not an object then JSON.parse will parse the JSON string in an Object
    var arrData = typeof JSONData != 'object' ? JSON.parse(JSONData) : JSONData;

    var CSV = '';
    //Set Report title in first row or line

    CSV += ReportTitle + '\r\n\n';

    //This condition will generate the Label/Header
    if (ShowLabel) {
        var row = "";

        //This loop will extract the label from 1st index of on array
        for (var index in arrData[0]) {

            //Now convert each value to string and comma-seprated
            row += index + ',';
        }

        row = row.slice(0, -1);

        //append Label row with line break
        CSV += row + '\r\n';
    }

    //1st loop is to extract each row
    for (var i = 0; i < arrData.length; i++) {
        var row = "";

        //2nd loop will extract each column and convert it in string comma-seprated
        for (var index in arrData[i]) {
            row += '"' + arrData[i][index] + '",';
        }

        row.slice(0, row.length - 1);

        //add a line break after each row
        CSV += row + '\r\n';
    }

    if (CSV == '') {
        alert("Invalid data");
        return;
    }

    //Generate a file name
    var fileName = "MyReport_";
    //this will remove the blank-spaces from the title and replace it with an underscore
    fileName += ReportTitle.replace(/ /g, "_");

    //Initialize file format you want csv or xls
    var uri = 'data:text/csv;charset=utf-8,' + escape(CSV);

    // Now the little tricky part.
    // you can use either>> window.open(uri);
    // but this will not work in some browsers
    // or you will not get the correct file extension

    //this trick will generate a temp <a /> tag
    var link = document.createElement("a");
    link.href = uri;

    //set the visibility hidden so it will not effect on your web-layout
    link.style = "visibility:hidden";
    link.download = fileName + ".csv";

    //this part will append the anchor tag and remove it after automatic click
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
}