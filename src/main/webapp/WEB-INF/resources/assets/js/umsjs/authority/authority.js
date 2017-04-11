$(document).ready(function () {
    $('.page-body').css('height', $(window).height() - 85);
    window.common.init();
    if (user) {
        if (user.level == LEVEL.ADMIN) {
            findMonitorAll();
        }else{
            window.location = "/monitor";
        }
    }
})

$(window).resize(function () {
    $('.page-body').css('height', $(window).height() - 85);
});

var resultTable = null;
var tableData;
var userInfo;
var monitorId;

var common = {
    init: function () {
        userInfo = JSON.parse($.cookie("monitor"));
        $('#btn-registration').on('click', function (e) {
            addMonitor();
        });

        $('#btn-del').on('click', function (e) {
            hideDeleteModel();
            deleteMonitor();
        });

        $('#btn-save').on('click', function (e) {
            changeMonitorPassword();
        });

    }
};

function findMonitorAll() {
    showLoading();
    $.ajax({
        method: "GET",
        url: "/v1.0/monitors",
        headers: {
            "Accept-Language":$.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0,2),
            "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
        },
        success: function (data) {
            hideLoading();
            initTable(data.results);
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

function addMonitor() {
    var text = $("#input-id").val();
    if ($.trim(text) != '') {
        showLoading();
        $.ajax({
            method: "POST",
            url: "/v1.0/monitors",
            headers: {
                "Accept-Language":$.cookie("umsLanguage"),
                "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0,2),
                "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
            },
            data: {
                name: text
            },
            success: function (data) {
                resetAddForm();
                findMonitorAll();
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                hideLoading();
                alert("error");
                //TODO: handle other status
                if (XMLHttpRequest.status == "401") {
                    window.location = "/login";
                }
            }
        });
    }
}

function deleteMonitor() {
    showLoading();
    $.ajax({
        method: "DELETE",
        url: "/v1.0/monitors/" + monitorId,
        headers: {
            "Accept-Language":$.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0,2),
            "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
        },
        success: function (data) {
            findMonitorAll();
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

/**
 * modify monitor password
 * @returns {boolean}
 */
function changeMonitorPassword() {
    //var currentPassword = $("#input-currentpassword").val();
    var newPassword = $("#input-password").val();
    var reNewPassword = $("#input-repassword").val();
    if ($.trim(newPassword) == '' || $.trim(reNewPassword) == '') {
        return false;
    }
    if (newPassword != reNewPassword) {
        alert("Password Conflict.");
        return false;
    }
    if ((newPassword.length > 7 && newPassword.length < 17) && !(/^[a-z]+$/.test(newPassword) || /^[0-9]+$/.test(newPassword) || /^[A-Z]+$/.test(newPassword)) && (/^[a-zA-Z0-9]/.test(newPassword))) {

    } else {
        alert("Password should be composed with number and english character as 8-16 digits .");
        return false;
    }
    resetChangePasswordForm();
    showLoading();
    $.ajax({
        method: "PUT",
        url: "/v1.3/monitors/password/" + monitorId,
        headers: {
            "Accept-Language":$.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0,2),
            "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
        },
        data: {
            password: $.md5(newPassword)
        },
        success: function (data) {
            hidePasswordModal();
            hideLoading();
            alert("success");
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            hideLoading();
            alert("failed");
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
    resultTable = $("#result-panel").dataTable({
        data: data,
        'bPaginate': false,
        "bDestory": false,
        "bRetrieve": false,
        "bFilter": false,
        "bSort": false,
        "bProcessing": false,
        "bInfo": false,
        'aoColumns': [
            {'mDataProp': 'name'},
            {'mDataProp': 'level'},
            {'mDataProp': 'cdate'},
            {'mDataProp': null},
            {'mDataProp': null},
            {'mDataProp': 'status'}
        ],
        "fnCreatedRow": function (nRow, aData, iDataIndex) {

            if (userInfo.monitorId == aData.monitorId) {
                $('td:eq(1)', nRow).html("<label><input type=\'checkbox\' onclick=\'return false;\' checked><span class=\'text\'></span></label>");
                $('td:eq(4)', nRow).html('');
            } else {
                if (aData.level == LEVEL.ADMIN) {
                    $('td:eq(1)', nRow).html("<label><input type=\'checkbox\' onclick=\'return false;\' checked><span class=\'text\'></span></label>");
                } else {
                    $('td:eq(1)', nRow).html("<label><input type=\'checkbox\' onclick=\'return false;\'><span class=\'text\'></span></label>");
                }
                if (aData.status == STATUS.ACTV) {
                    $('td:eq(4)', nRow).html("<a class=\'btn btn-default btn-sm\' onclick='showDeleteModel(\"" + aData.monitorId + "\")'>"+$.i18n.prop("ums.authority.management.delete") +"</a>");
                } else {
                    $('td:eq(4)', nRow).html("");
                }
            }
            $('td:eq(3)', nRow).html("<a class=\'btn btn-info btn-sm\' onclick='showPasswordModal(\"" + aData.monitorId + "\")'>"+$.i18n.prop("ums.authority.management.change") +"</a>");
            if (aData.status == STATUS.ACTV) {
                $('td:eq(5)', nRow).html("<span class=\'label label-info\'>"+$.i18n.prop("ums.authority.management.inuse") +"</span>");
            } else {
                $('td:eq(5)', nRow).html("<span class=\'label label-danger\'>"+$.i18n.prop("ums.authority.management.delete.account") +"</span>");
            }
            return nRow;
        }
    });
    return resultTable;
}

function showPasswordModal(date) {
    monitorId = date;
    $(".bs-example-modal-sm").modal('show');
}

function hidePasswordModal() {
    $(".bs-example-modal-sm").modal('hide');
}

function showDeleteModel(date) {
    monitorId = date;
    $(".bootbox-confirm").modal('show');
}

function hideDeleteModel() {
    $(".bootbox-confirm").modal('hide');
}

function resetAddForm() {
    $('#input-id').val('');
}

function resetChangePasswordForm() {
    //$('#input-currentpassword').val('');
    $('#input-password').val('');
    $('#input-repassword').val('');
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