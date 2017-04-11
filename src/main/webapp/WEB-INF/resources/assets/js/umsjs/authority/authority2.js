$(document).ready(function () {
    $('.page-body').css('height', $(window).height() - 85);
    window.common.init();
})

$(window).resize(function () {
    $('.page-body').css('height', $(window).height() - 85);
});

var common = {
    init: function () {
        userInfo = JSON.parse($.cookie("monitor"));
        $('#btn-save').on('click', function (e) {
            changeOwnPassword();
        });
        $('#btn-cancel').on('click', function (e) {
            $('.modal-dialog').hide();
        });

    }
};

/**
 * modify
 * @returns {boolean}
 */
function changeOwnPassword() {
    var currentPassword = $("#input-currentpassword").val();
    var newPassword = $("#input-password").val();
    var reNewPassword = $("#input-repassword").val();
    if ($.trim(currentPassword) == '' || $.trim(newPassword) == '' || $.trim(reNewPassword) == '') {
        return false;
    }
    if (newPassword != reNewPassword) {
        alert("Password Conflict");
        return false;
    }
    if ((newPassword.length > 9 && newPassword.length < 17) && !(/^[a-z]+$/.test(newPassword) || /^[0-9]+$/.test(newPassword) || /^[A-Z]+$/.test(newPassword)) && (/^[a-zA-Z0-9]/.test(newPassword))) {

    } else {
        alert("Password should be composed with number and english character as 10-16 digits .");
        return false;
    }
    resetChangePasswordForm();
    showLoading();
    $.ajax({
        method: "PUT",
        url: "/v1.0/monitors/" + userInfo.monitorId,
        headers: {
            "Accept-Language":$.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0,2),
            "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
        },
        data: {
            password: $.md5(newPassword),
            oldPassword: $.md5(currentPassword)
        },
        success: function (data) {
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

function resetChangePasswordForm() {
    $('#input-currentpassword').val('');
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