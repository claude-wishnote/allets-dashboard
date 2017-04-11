/**
 * Created by claude on 2015/9/10.
 */

var selectUid;
var selectUser;
var selectIndex;
var uidForDeleteCommetsCounts;
var deleteCommetsCounts;
var uidForAccountReportCounts;
var accountReportCounts;

var searchResult = '';

var testData = undefined;

var editUserInfoPopover = false;
var resetUserInfoPopover = false;
var userInfoDeleteComentsTypePopover = false;
var userInfoAccountReportTypePopover = false;
var userInfoHandleReportedCommentPopover = false;
var userInfoRestoreCommentPopover = false;
var changePhoto = 'A00/default_avatar.png';
var changeNickname = '닉네임을_변경해주세요.';
var changeIntroMessage = '';
var changeSubscribe = '0';
var changeSubscriber = '0';
var changeSex = '';
var changeBirthday = '';
var changeEmail = '';
var checkedPhotoReset = false;
var checkedNicknameReset = false;
var checkedMessageReset = false;
var checkedSubscribeReset = false;
var checkedSubscriberReset = false;

var editUserInfoPopoverForModal = false;
var resetUserInfoPopoverForModal = false;

var handle = '';

$(window).resize(function () {
    $('.page-body').css('height', $(window).height() - 85);
});
$(document).ready(function () {
    $('.page-body').css('height', $(window).height() - 85);

    bindPopOverEvent();

    bindButtonEvent();

    var parseUid = GetArgsFromHref(window.location.href, 'uid');
    var temp = /^\d+(\.\d+)?$/;
    if (temp.test(parseUid)) {
        selectUid = parseUid;
        requestUserDataByUid(parseUid)
    }
});
function GetArgsFromHref(sHref, sArgName) {
    var args = sHref.split("?");
    var retval = "";
    if (args[0] == sHref) /*no args*/
    {
        return retval;
    }
    var str = args[1];
    args = str.split("&");
    for (var i = 0; i < args.length; i++) {
        str = args[i];
        var arg = str.split("=");
        if (arg.length <= 1) continue;
        if (arg[0] == sArgName)
            retval = arg[1];
    }
    return retval;
}
/**
 * refresh UI
 * @param searchResult
 */
function refreshSearchResultDropDown() {
    var dropDownHtml = '';
    if (searchResult == '') {
        dropDownHtml = '<li id=\'searchTip\'><a>no result.please search again. </a></li>';
        $('#searchResultDropDown').html(dropDownHtml)
        return;
    }
    for (var x in searchResult) {
        if ($('#userInfoSearchField').val() == 'nickName') {
            if (searchResult[x].name == $('#userInfoSearchKeyword').val()) {
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
        } else if ($('#userInfoSearchField').val() == 'email') {
            if (searchResult[x].email == $('#userInfoSearchKeyword').val()) {
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
        } else if ($('#userInfoSearchField').val() == 'uid') {
            if (searchResult[x].uid == $('#userInfoSearchKeyword').val()) {
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
                if ($('#userInfoSearchField').val() == 'nickName') {
                    $('#userInfoSearchKeyword').val(searchResult[$(this).val()].name);
                } else if ($('#userInfoSearchField').val() == 'email') {
                    $('#userInfoSearchKeyword').val(searchResult[$(this).val()].email);
                } else if ($('#userInfoSearchField').val() == 'uid') {
                    $('#userInfoSearchKeyword').val(searchResult[$(this).val()].uid);
                }
                requestUserDataByUid(searchResult[$(this).val()].uid);
                refreshSearchResultDropDown();
            }
        }
    );

    $('#userInfoSearchKeyword').parent().addClass('open');
    $('#userInfoSearchKeyword').attr('aria-expanded', 'true');
}

function freshUserInfoData(user) {
    selectUser = user;
    // $('.profile-img').attr('src', '/img/loading.gif');
    $('#userInfoCdate').html(user.cdate)
    $('#userInfoUid').html(user.uid);

    $('#sex').html(user.sex);
    if (user.name == undefined || user.name == null || user.name == '') {
        $('#name').html('');
    } else {
        $('#name').html(user.name);
    }
    $('#introMessage').html(user.introMessage);
    $('#birthday').html(user.birthday);
    $('#email').html(user.email);

    $('#bookMarkCount').html(user.bookMarkCount);
    $('#subscribeEditorCount').html(user.subscribeEditorCount);
    $('#subscribeNormalCount').html(user.subscribeCount - user.subscribeEditorCount);
    $('#subscriberCount').html(user.subscriberCount);
    $('#shareCount').html(user.shareCount);
    $('#warningAlert').html('-');
    if (user.age15plus == 0) {
        $('#certifyOver15').html('N');
    } else {
        $('#certifyOver15').html('Y');
    }
    $('#loginType').html('');
    $('#userStatus').html(showUserStatusWithCorrectWord(user.status, user.previousStatus,user.invalidTo));
    $('#userReportedCount').html(user.userReportedCount);
    $('#deleteCommentsCount').html(user.deleteCommentsCount);
    if (window.location.hostname == 'ums.allets.com') {
        $('.profile-img').attr('src', 'https://s3.ap-northeast-2.amazonaws.com/an2-prd-allets-contents/' + user.photo);
    } else {
        $('.profile-img').attr('src', 'https://s3.ap-northeast-2.amazonaws.com/an2-prd-allets-contents/' + user.photo);
    }

    //$(".profile-img").animate({opacity:'toggle'},"slow",null,function(){
    //    $(".profile-img").attr("src", 'https://s3.ap-northeast-2.amazonaws.com/an2-prd-allets-contents/' + user.photo);
    //    $(".profile-img").animate({opacity:'toggle'},"slow");
    //});
    if (user.status == 'OUT') {
        if (user.invalidTo == '' || new Date(Date.parse(user.invalidTo.replace(/-/g, "/"))) > new Date()) {
            $('#invalidFrom').html(user.invalidFrom == '' ? 'forever' : user.invalidFrom);
            $('#invalidTo').html(user.invalidTo == '' ? 'forever' : user.invalidTo);
        }
        else if (new Date(Date.parse(user.invalidTo.replace(/-/g, "/"))) < new Date()) {
            $('#invalidFrom').html(user.invalidFrom == '' ? '-' : user.invalidFrom);
            $('#invalidTo').html(user.invalidTo == '' ? '-' : user.invalidTo);
        }
    } else if (user.status == 'DEL') {
        $('#invalidFrom').html(user.invalidFrom == '' ? 'forever' : user.invalidFrom);
        $('#invalidTo').html('forever');
    } else {
        $('#invalidFrom').html('-');
        $('#invalidTo').html('-');
    }

    requestTotalReportCount(user.uid);
    requestBestCommentCount(user.uid);
    requestWarningAlert(user.uid);
    requestLoginType(user.uid);

}

/**
 * local fuctions
 */
function bindButtonEvent() {
    if ($.cookie("searchSource") && ($.cookie("searchSource") == 'els' || $.cookie("searchSource") == 'mysql')) {
        $('#searchSource').val($.cookie("searchSource"));
    } else {
        $('#searchSource').val('mysql');
        $.cookie("searchSource", 'mysql');
    }
    $('#searchSource').on('change', function (e) {
        $.cookie("searchSource", $('#searchSource').val());
    });
    $('#searchSource').hide();
    $('#userInfoSearchField').on('change', function (e) {
        if ($('#userInfoSearchField').val() == 'uid') {
            $('#searchSource').hide();
        } else{
            //$('#searchSource').show();
            $('#searchSource').hide();
        }
    });
    $('#userInfoSearchButton').on(
        'click', function () {
            //requestSearchUser('els');
            requestSearchUser();
        }
    );
    $('#userInfoSearchKeyword').keydown(function (a) {
        if (a.keyCode == 13) {
            //requestSearchUser('els');
            requestSearchUser();
        }
    });
    $('#editUserInfoResetPasswordButton').on('click', function () {
        if ((selectUser != undefined && selectUser != '')) {
            $("#resetPasswordModel").modal('show');
        }
    });
}

function bindPopOverEvent() {
    bindEditUserInfoPopoverButton();
    //bindResetUserInfoPopoverButton();
    bindEditUserinfoModel();
    bindReportTypeCountsButton();
    bindResetPasswordModel();
    $('.page-body').on('click', function () {
        if (userInfoHandleReportedCommentPopover) {
            $('#userInfoReportedCommentTable .btn').popover('hide');
        }
        if (userInfoRestoreCommentPopover) {
            $('#userInfoReportedCommentTable .label').popover('hide');
        }
    });
}

function bindEditUserInfoPopoverButton() {
    $('#editUserInfoPopoverButton').popover({
            placement: 'bottom',
            trigger: 'click',
            html: true,
            title: '',
            content: '<table class=\'table btm-line\'><tbody>' +
            '<tr><td id=\'userInfoGenderTitle\'><span class=\'text\'>' + $.i18n.prop("ums.account.management.gender") + '</span></td><td><select style="width: 150px"  id="userInfoGenderValue"><option value="M">M</option><option value="F">F</option></select></td></tr>' +
            '<tr><td id=\'userInfoBirthDayTitle\'><span class=\'text\'>' + $.i18n.prop("ums.account.management.birthday") + '</span></td><td><div style="width: 150px" class="input-group"><input class="form-control date-picker" id="userInfoBirthDayValue" type="text" value=""><span class="input-group-addon"><i class="fa fa-calendar"></i></span></div></td></tr>' +
            '<tr><td id=\'userInfoEmailTitle\'><span class=\'text\'>' + $.i18n.prop("ums.account.management.email") + '</span></td><td><input style="width: 150px" class="form-control"  id=\'userInfoEmailValue\' type=\'text\' ></td></tr>' +
            '</tbody></table>' +
            '<div class=\'btn-area text-center\'>' +
            '<a class=\'btn btn-darkorange btn-sm \' id=\'userInfoEditProfiles\'>' + $.i18n.prop("ums.monitor.all.comment.select") + '</a>' +
            '<a class=\'btn btn-default btn-sm\' id=\'userInfoCancelEditProfiles\'>' + $.i18n.prop("ums.monitor.all.comment.cancel") + '</a></div>',
        }
    );
    $('#editUserInfoPopoverButton').on(
        'shown.bs.popover', function () {
            checkedPhotoReset = false;
            checkedNicknameReset = false;
            checkedMessageReset = false;
            checkedSubscribeReset = false;
            checkedSubscriberReset = false;
            $('#userInfoBirthDayValue').datepicker({
                format: 'yyyy-mm-dd'
            });
            $('#userInfoBirthDayValue').on(
                'focus', function () {
                    $(this).blur();
                }
            );
            editUserInfoPopover = true;
            if (selectUser != '') {
                if (changeSex != '') {
                    $('#userInfoGenderValue').val(changeSex);
                }
                else if ($.trim(selectUser.sex) != '') {
                    $('#userInfoGenderValue').val(selectUser.sex);
                }
                if (changeBirthday != '') {
                    $('#userInfoBirthDayValue').val(changeBirthday);
                }
                else if ($.trim(selectUser.birthday) != '') {
                    $('#userInfoBirthDayValue').val(selectUser.birthday);
                }
                if (changeEmail != '') {
                    $('#userInfoEmailValue').val(changeEmail);
                }
                else if ($.trim(selectUser.email) != '') {
                    $('#userInfoEmailValue').val(selectUser.email);
                }
            }
            $('#userInfoEditProfiles').on(
                'click', function () {
                    changeSex = $('#userInfoGenderValue').val();
                    changeBirthday = $('#userInfoBirthDayValue').val();
                    changeEmail = $('#userInfoEmailValue').val();
                    if ((selectUid != undefined && selectUid != '') &&
                        ($.trim(selectUser.sex) != $.trim($('#userInfoGenderValue').val())
                        || $.trim(selectUser.birthday) != $.trim($('#userInfoBirthDayValue').val())
                        || $.trim(selectUser.email) != $.trim($('#userInfoEmailValue').val()))) {
                        editUserInfoPopoverForModal = true;
                        $("#editUserinfoModel").modal('show');
                    }
                }
            );
            $('#userInfoCancelEditProfiles').on(
                'click', function () {
                    checkedPhotoReset = false;
                    checkedNicknameReset = false;
                    checkedMessageReset = false;
                    checkedSubscribeReset = false;
                    checkedSubscriberReset = false;
                    if (selectUid != undefined && selectUid != '') {
                        changeSex = selectUser.sex;
                        changeBirthday = selectUser.birthday;
                        changeEmail = selectUser.email;
                    }
                    $('#editUserInfoPopoverButton').popover('hide');
                });
        }
    );
    $('#editUserInfoPopoverButton').on('click', function () {
        checkedPhotoReset = false;
        checkedNicknameReset = false;
        checkedMessageReset = false;
        checkedSubscribeReset = false;
        checkedSubscriberReset = false;
        if (selectUid != undefined && selectUid != '') {
            changeSex = selectUser.sex;
            changeBirthday = selectUser.birthday;
            changeEmail = selectUser.email;
        }
        $('#resetUserInfoPopoverButton').popover('hide');
    });
}

function bindResetUserInfoPopoverButton() {
    $('#resetUserInfoPopoverButton').popover({
            placement: 'bottom',
            trigger: 'click',
            html: true,
            title: '',
            content: '<table class=\'table btm-line\'><tbody>' +
            '<tr><td id=\'userInfoPhotoReset\'><label><input type=\'checkbox\' ><span class=\'text\'>' + $.i18n.prop("ums.account.management.photo") + '</span></label></td></tr>' +
            '<tr><td id=\'userInfoNicknameReset\'><label><input type=\'checkbox\' ><span class=\'text\'>' + $.i18n.prop("ums.account.management.nickname") + '</span></label></td></tr>' +
            '<tr><td id=\'userInfoIntroMessageReset\'><label><input type=\'checkbox\' ><span class=\'text\'>' + $.i18n.prop("ums.account.management.intro") + '</span></label></td></tr>' +
            '<tr><td id=\'userInfoSubscribeReset\'><label><input type=\'checkbox\' ><span class=\'text\'>' + $.i18n.prop("ums.account.management.subscribe") + '</span></label></td></tr>' +
            '<tr><td id=\'userInfoSubscriberReset\'><label><input type=\'checkbox\' ><span class=\'text\'>' + $.i18n.prop("ums.account.management.subscriber") + '</span></label></td></tr>' +
            '</tbody></table>' +
            '<div class=\'btn-area text-center\'>' +
            '<a class=\'btn btn-darkorange btn-sm \' id=\'userInfoResetProfiles\'>' + $.i18n.prop("ums.monitor.all.comment.select") + '</a>' +
            '<a class=\'btn btn-default btn-sm\' id=\'userInfoCancelResetProfiles\'>' + $.i18n.prop("ums.monitor.all.comment.cancel") + '</a></div>'
        }
    );
    $('#resetUserInfoPopoverButton').on(
        'shown.bs.popover', function () {
            resetUserInfoPopover = true;
            if (checkedPhotoReset) {
                $('#userInfoPhotoReset input').attr("checked", true);
            } else {
                $('#userInfoPhotoReset input').attr("checked", false);
            }
            if (checkedNicknameReset) {
                $('#userInfoNicknameReset input').attr("checked", true);
            } else {
                $('#userInfoNicknameReset input').attr("checked", false);
            }
            if (checkedMessageReset) {
                $('#userInfoIntroMessageReset input').attr("checked", true);
            } else {
                $('#userInfoIntroMessageReset input').attr("checked", false);
            }
            if (checkedSubscribeReset) {
                $('#userInfoSubscribeReset input').attr("checked", true);
            } else {
                $('#userInfoSubscribeReset input').attr("checked", false);
            }
            $('#userInfoResetProfiles').on(
                'click', function () {
                    checkedPhotoReset = $('#userInfoPhotoReset input').is(':checked');
                    checkedNicknameReset = $('#userInfoNicknameReset input').is(':checked');
                    checkedMessageReset = $('#userInfoIntroMessageReset input').is(':checked');
                    checkedSubscribeReset = $('#userInfoSubscribeReset input').is(':checked');
                    checkedSubscriberReset = $('#userInfoSubscriberReset input').is(':checked');
                    if ((selectUid != undefined && selectUid != '') && (checkedPhotoReset || checkedNicknameReset || checkedMessageReset || checkedSubscribeReset || checkedSubscriberReset)) {
                        resetUserInfoPopoverForModal = true;
                        $("#editUserinfoModel").modal('show');
                    }
                }
            );
            $('#userInfoCancelResetProfiles').on(
                'click', function () {
                    checkedPhotoReset = false;
                    checkedNicknameReset = false;
                    checkedMessageReset = false;
                    checkedSubscribeReset = false;
                    checkedSubscriberReset = false;
                    if (selectUid != undefined && selectUid != '') {
                        changeSex = selectUser.sex;
                        changeBirthday = selectUser.birthday;
                        changeEmail = selectUser.email;
                    }
                    $('#resetUserInfoPopoverButton').popover('hide');
                });
        }
    );
    $('#resetUserInfoPopoverButton').on(
        'hidden.bs.popover', function () {
            resetUserInfoPopover = false;
        }
    );
    $('#resetUserInfoPopoverButton').on('click', function () {
        checkedPhotoReset = false;
        checkedNicknameReset = false;
        checkedMessageReset = false;
        checkedSubscribeReset = false;
        checkedSubscriberReset = false;
        if (selectUid != undefined && selectUid != '') {
            changeSex = selectUser.sex;
            changeBirthday = selectUser.birthday;
            changeEmail = selectUser.email;
        }
        $('#editUserInfoPopoverButton').popover('hide');
    });
}

function bindEditUserinfoModel() {
    $('#editUserinfoModel').on('show.bs.modal', function () {
        if (editUserInfoPopoverForModal) {
            if ($.trim(selectUser.sex) != changeSex) {
                $('#changeSex').show();
                $('#changeSex').html("change Gender from \'" + $.trim(selectUser.sex) + "\' to \'" + changeSex + '\'');
            }
            if ($.trim(selectUser.birthday) != changeBirthday) {
                $('#changeBirthday').show();
                $('#changeBirthday').html("change Birthday from \'" + $.trim(selectUser.birthday) + "\' to " + changeBirthday + '\'');
            }
            if ($.trim(selectUser.email) != changeEmail) {
                $('#changeEmail').show();
                $('#changeEmail').html("change Email from \'" + $.trim(selectUser.email) + "\' to \'" + changeEmail + '\'');
            }
        }
        if (resetUserInfoPopoverForModal) {
            if (checkedPhotoReset) {
                $('#changePhoto').show();
                $('#changePhoto').html('change Photo from user\'s file to default image');
            }
            if (checkedNicknameReset) {
                $('#changeNickname').show();
                $('#changeNickname').html('change Nickname from \'' + $.trim(selectUser.name) + '\' to \'' + changeNickname + '\'');
            }
            if (checkedMessageReset) {
                $('#changeIntroMessage').show();
                $('#changeIntroMessage').html('change IntroMessage from \'' + $.trim(selectUser.introMessage) + '\' to \'' + changeIntroMessage + '\'');
            }
            if (checkedSubscribeReset) {
                $('#changeSubscribe').show();
                $('#changeSubscribe').html('change subscribe count from \'' + selectUser.subscribeCount + '\' to \'' + changeSubscribe + '\'');
            }
            if (checkedSubscriberReset) {
                $('#changeSubscriber').show();
                $('#changeSubscriber').html('change subscriber count from \'' + selectUser.subscriberCount + '\' to \'' + changeSubscriber + '\'');
            }

        }
        $('#resetUserInfoPopoverButton').popover('hide');
        $('#editUserInfoPopoverButton').popover('hide');
        $('#userInfoDeleteComentsTypeButton').popover('hide');
        $('#userInfoAccountReportTypeButton').popover('hide');
    })

    $('#editOK').on('click', function () {
        editUserInfoPopoverForModal = false;
        resetUserInfoPopoverForModal = false;
        editUserInfo();
    });
    $('#editCancel').on('click', function () {
    });
    $('#editUserinfoModel').on('hidden.bs.modal', function () {
        if (editUserInfoPopoverForModal) {
            $('#editUserInfoPopoverButton').popover('show');
            editUserInfoPopoverForModal = false;
        }
        if (resetUserInfoPopoverForModal) {
            $('#resetUserInfoPopoverButton').popover('show');
            resetUserInfoPopoverForModal = false;
        }
        $('#changePhoto').hide();
        $('#changeNickname').hide();
        $('#changeIntroMessage').hide();
        $('#changeSubscribe').hide();
        $('#changeSubscriber').hide();
        $('#changeSex').hide();
        $('#changeBirthday').hide();
        $('#changeEmail').hide();
    })
}


function bindResetPasswordModel() {
    $('#resetPasswordModel').on('show.bs.modal', function () {
        var html = $.i18n.prop("ums.account.management.info.reset.password.string") + '<br> email:' + selectUser.email;
        $('#resetPasswordSting').html(html);
    });

    $('#resetPasswordOK').on('click', function () {
        resetUserPassword();
    });
    $('#resetPasswordCancel').on('click', function () {
    });
    $('#resetPasswordModel').on('hidden.bs.modal', function () {
    })
}

function bindReportTypeCountsButton() {
    $('#userInfoDeleteComentsTypeButton').popover({
        placement: 'top',
        trigger: 'click',
        html: true,
        content: '<div id=\'totalDeletedCommentsCountPopover\'><span type=\'text\' style=\'display: none\'>-</span>' +
        '<p>Deleted comments count</p><table class=\'table btm-line\'><tbody>' +
        '<tr><td>' + $.i18n.prop("ums.monitor.all.comment.report.type.a.rt100") + '</td><td id=\'typeRT100D\'>0</td></tr> ' +
        '<tr><td>' + $.i18n.prop("ums.monitor.all.comment.report.type.b.rt200") + '</td><td id=\'typeRT200D\'>0</td></tr>' +
        '<tr><td>' + $.i18n.prop("ums.monitor.all.comment.report.type.c.rt300") + '</td><td id=\'typeRT300D\'>0</td></tr>' +
        '<tr><td>' + $.i18n.prop("ums.monitor.all.comment.report.type.d.rt400") + '</td><td id=\'typeRT400D\'>0</td></tr>' +
        '<tr><td>' + $.i18n.prop("ums.monitor.all.comment.report.type.e.rt500") + '</td><td id=\'typeRT500D\'>0</td></tr>' +
        '<tr><td>' + $.i18n.prop("ums.monitor.all.comment.report.type.f.rt600") + '</td><td id=\'typeRT600D\'>0</td> ' +
        '<tr><td>' + $.i18n.prop("ums.monitor.all.comment.report.type.g.rt999") + '</td><td id=\'typeRT999D\'>0</td> ' +
        '</tr></tbody></table>' +
        '<div class=\'btn-area text-center\'>' +
        '<a id=\'closeDeletedCommentsCountPopove\' class=\'btn btn-default btn-sm right\'>' + $.i18n.prop("ums.account.management.comment.close") + '</a>' +
        '</div></div>',
        title: ''
    });

    $('#userInfoDeleteComentsTypeButton').on(
        'shown.bs.popover', function () {
            userInfoDeleteComentsTypePopover = true;
            $('#closeDeletedCommentsCountPopove').on(
                'click', function () {
                    $('#userInfoDeleteComentsTypeButton').popover('hide');
                }
            );
            if (selectUid == undefined || selectUid == '')
                return;
            blockObj($('#totalDeletedCommentsCountPopover').parent());
            if (uidForDeleteCommetsCounts == undefined || uidForDeleteCommetsCounts == '') {
                uidForDeleteCommetsCounts = selectUid;
                requestDeleteReportTypeCount(uidForDeleteCommetsCounts);
            }
            else if (uidForDeleteCommetsCounts != selectUid) {
                //ajax请求
                $('#typeRT999D').html(0);
                $('#typeRT100D').html(0);
                $('#typeRT200D').html(0);
                $('#typeRT300D').html(0);
                $('#typeRT400D').html(0);
                $('#typeRT500D').html(0);
                $('#typeRT600D').html(0);
                uidForDeleteCommetsCounts = selectUid;
                requestDeleteReportTypeCount(uidForDeleteCommetsCounts);
            }
            else if (uidForDeleteCommetsCounts == selectUid) {
                $('#typeRT100D').html(deleteCommetsCounts.rt100Count);
                $('#typeRT200D').html(deleteCommetsCounts.rt200Count);
                $('#typeRT300D').html(deleteCommetsCounts.rt300Count);
                $('#typeRT400D').html(deleteCommetsCounts.rt400Count);
                $('#typeRT500D').html(deleteCommetsCounts.rt500Count);
                $('#typeRT600D').html(deleteCommetsCounts.rt600Count);
                $('#typeRT999D').html(deleteCommetsCounts.rt999Count);
                $('#totalDeletedCommentsCountPopover').parent().unblock();
            }
        }
    );

    $('#userInfoAccountReportTypeButton').popover({
        placement: 'top',
        trigger: 'click',
        html: true,
        content: '<div id=\'accountReportCountPopover\'><span type=\'text\' style=\'display: none\'>-</span>' +
        '<p>Account reported count</p><table class=\'table btm-line\'><tbody>' +
        '<tr><td>' + $.i18n.prop("ums.monitor.all.comment.report.type.a.rt100") + '</td><td id=\'typeRT100A\'>0</td></tr> ' +
        '<tr><td>' + $.i18n.prop("ums.monitor.all.comment.report.type.b.rt200") + '</td><td id=\'typeRT200A\'>0</td></tr>' +
        '<tr><td>' + $.i18n.prop("ums.monitor.all.comment.report.type.c.rt300") + '</td><td id=\'typeRT300A\'>0</td></tr>' +
        '<tr><td>' + $.i18n.prop("ums.monitor.all.comment.report.type.d.rt400") + '</td><td id=\'typeRT400A\'>0</td></tr>' +
        '<tr><td>' + $.i18n.prop("ums.monitor.all.comment.report.type.e.rt500") + '</td><td id=\'typeRT500A\'>0</td></tr>' +
        '<tr><td>' + $.i18n.prop("ums.monitor.all.comment.report.type.f.rt600") + '</td><td id=\'typeRT600A\'>0</td> ' +
        '<tr><td>' + $.i18n.prop("ums.monitor.all.comment.report.type.g.rt999") + '</td><td id=\'typeRT999A\'>0</td> ' +
        '</tr></tbody></table>' +
        '<div class=\'btn-area text-center\'>' +
        '<a id=\'closeAccountReportCountPopover\' class=\'btn btn-default btn-sm right\'>' + $.i18n.prop("ums.account.management.comment.close") + '</a>' +
        '</div></div>',
        title: ''
    });
    $('#userInfoAccountReportTypeButton').on(
        'shown.bs.popover', function () {
            userInfoAccountReportTypePopover = true;
            $('#closeAccountReportCountPopover').on(
                'click', function () {
                    $('#userInfoAccountReportTypeButton').popover('hide');
                }
            );
            if (selectUid == undefined || selectUid == '')
                return;
            blockObj($('#accountReportCountPopover').parent());

            if (uidForAccountReportCounts == undefined || uidForAccountReportCounts == '') {
                uidForAccountReportCounts = selectUid;
                requestAccountReportTypeCount(uidForAccountReportCounts);
            }
            else if (uidForAccountReportCounts != selectUid) {
                //ajax请求
                $('#typeRT100A').html(0);
                $('#typeRT200A').html(0);
                $('#typeRT300A').html(0);
                $('#typeRT400A').html(0);
                $('#typeRT500A').html(0);
                $('#typeRT600A').html(0);
                $('#typeRT999A').html(0);
                uidForAccountReportCounts = selectUid;
                requestAccountReportTypeCount(uidForAccountReportCounts);
            }
            else if (uidForAccountReportCounts == selectUid) {
                $('#typeRT100A').html(accountReportCounts.rt100Count);
                $('#typeRT200A').html(accountReportCounts.rt200Count);
                $('#typeRT300A').html(accountReportCounts.rt300Count);
                $('#typeRT400A').html(accountReportCounts.rt400Count);
                $('#typeRT500A').html(accountReportCounts.rt500Count);
                $('#typeRT600A').html(accountReportCounts.rt600Count);
                $('#typeRT999A').html(accountReportCounts.rt999Count);
                $('#accountReportCountPopover').parent().unblock();
            }
        }
    );

    $('#userInfoDeleteComentsTypeButton').on(
        'hidden.bs.popover', function () {
            userInfoDeleteComentsTypePopover = false;
        }
    );
}

function bindTableButtonEvent() {
    $('#userInfoReportedCommentTable .label').each(function (index, html) {
        $(this).popover({
            placement: 'left',
            //trigger: 'click', //触发方式
            html: true, // 为true的话
            //container: this,
            content: '<div class=\'layer-btn-center-area\'>'
            + '<button  class=\'btn btn-default btn-sm btn-success\'>' + $.i18n.prop("ums.account.management.comment.restore") + '</button><br/>'
            + '</div>',
            title: ''
        });
    });
    $('#userInfoReportedCommentTable .label').on('click', function () {
        if (userInfoRestoreCommentPopover) {
            $('#userInfoReportedCommentTable .label').not(this).popover('hide');
            //$(this).popover('show');
        }
        if (userInfoHandleReportedCommentPopover) {
            $('#userInfoReportedCommentTable .btn').popover('hide');
        }
    });
    $('#userInfoReportedCommentTable .label').on('shown.bs.popover', function () {
        userInfoRestoreCommentPopover = true;
        var obj = $(this).parents('tr');
        var objBlock = $(this).parents('td');
        var disableObj = $(objBlock.next().children().first());
        $('.layer-btn-center-area .btn-success').on('click', function (e) {
            retoreComment('RES', obj, objBlock, disableObj);
            $('#userInfoReportedCommentTable .label').popover('hide');
        });
    });
    $('#userInfoReportedCommentTable .label').on('hide.bs.popover', function () {
        userInfoRestoreCommentPopover = false;
    });

    $('#userInfoReportedCommentTable .btn').each(function (index, html) {
        $(this).popover({
            placement: 'left',
            //trigger: 'click', //触发方式
            html: true, // 为true的话
            //container: this,
            content: '<div class=\'layer-btn-center-area\'>'
                //+ '<button  class=\'btn btn-default btn-sm btn-block\'>패스</button><br/>'
            + '<button  class=\'btn btn-info btn-sm btn-block\'>' + $.i18n.prop("ums.report.comment.one.blind") + '</button><br/>'
            + '<button  class=\'btn btn-darkorange btn-sm btn-block\'>' + $.i18n.prop("ums.report.comment.one.delete") + '</button><br/>'
            + '<button  class=\'btn btn-gray btn-sm btn-block\'>' + $.i18n.prop("ums.monitor.all.comment.cancel") + '</button>'
            + '</div>',
            title: ''
        });
    });
    //$('#userInfoReportedCommentTable .btn').each(function (index, html) { $(this).popover({
    //    placement: 'left',
    //    trigger: 'click',
    //    html: true,
    //    title: '',
    //    content: '<table class=\'table btm-line\'><tbody><tr>' +
    //    '<td><label><input name=\'form-field-radio\' type=\'radio\'><span class=\'text\'>a)광고,홍보성</span></label></td>' +
    //    '<td><label><input name=\'form-field-radio\' type=\'radio\'><span class=\'text\'>b)음란,불법성</span></label></td>' +
    //    '</tr><tr>' +
    //    '<td><label><input name=\'form-field-radio\' type=\'radio\'><span class=\'text\'>c)청소년 유해</span></label></td>' +
    //    '<td><label><input name=\'form-field-radio\' type=\'radio\'><span class=\'text\'>d)비방,욕설</span></label></td>' +
    //    '</tr><tr>' +
    //    '<td><label><input name=\'form-field-radio\' type=\'radio\'><span class=\'text\'>e)도배</span></label></td>' +
    //    '<td><label><input name=\'form-field-radio\' type=\'radio\'><span class=\'text\'>f)기타</span></label></td>' +
    //    '</tr><tr>' +
    //    '<td><label><input name=\'form-field-radio\' type=\'radio\'><span class=\'text\'>g)블라이</span></label></td>' +
    //    '<td><label><input name=\'form-field-radio\' type=\'radio\'><span class=\'text\'>h)음란,불법성</span></label></td>' +
    //    '</tr></tbody></table>' +
    //    '<div class=\'btn-area text-center\'>' +
    //    '<a class=\'btn btn-darkorange btn-sm\'>확인</a>' +
    //    '<a class=\'btn btn-default btn-sm\'>취소</a></div>'
    //    });
    //});
    $('#userInfoReportedCommentTable .btn').on('click', function () {
        //console.log(userInfoHandleReportedCommentPopover);
        if (userInfoHandleReportedCommentPopover) {
            $('#userInfoReportedCommentTable .btn').not(this).popover('hide');
        }
        //console.log( $('#userInfoReportedCommentTable .popover-content'));
        if (userInfoRestoreCommentPopover) {
            $('#userInfoReportedCommentTable .label').popover('hide');
        }
    });
    $('#userInfoReportedCommentTable .btn').on('shown.bs.popover', function () {
        userInfoHandleReportedCommentPopover = true;
        var popoverobj = $(this);
        var obj = $(this).parents('tr');
        var objBlock = $(this).parents('td');
        var disableObj = $(this);
        //$('.layer-btn-center-area .btn-default').on('click', function (e) {
        //    popoverobj.popover('hide');
        //    handleComment('PASS', obj, objBlock, disableObj);
        //});
        $('.layer-btn-center-area .btn-info').on('click', function (e) {
            popoverobj.popover('hide');
            handleComment('HIDD', obj, objBlock, disableObj);
        });
        $('.layer-btn-center-area .btn-darkorange').on('click', function (e) {
            popoverobj.popover('hide');
            handleComment('DEL', obj, objBlock, disableObj);
        });
        $('.layer-btn-center-area .btn-gray').on('click', function (e) {
            popoverobj.popover('hide');
        });
    });
    $('#userInfoReportedCommentTable .btn').on('hide.bs.popover', function () {
        userInfoHandleReportedCommentPopover = false;
    });

    $('#userInfoReportedCommentTable').parents('.dataTables_scrollBody').scroll(function () {
        $('#userInfoReportedCommentTable .btn').popover('hide');
        $('#userInfoReportedCommentTable .label').popover('hide');
    });
}
function retoreComment(action, obj, objBlock, disableObj) {
    blockObj(objBlock);
    blockObj(objBlock.prev());
    blockObj(objBlock.next());
    var putCommentIds = '';
    var aData = commentTable.fnGetData(obj);
    if (aData != null) {
        putCommentIds = aData.commentId;
        putRestoreComment(action, putCommentIds, objBlock, disableObj)
    }
}
function handleComment(action, obj, objBlock, disableObj) {
    blockObj(objBlock);
    blockObj(objBlock.prev());
    blockObj(objBlock.prev().prev());
    var putCommentIds = '';
    var aData = commentTable.fnGetData(obj);
    if (aData != null) {
        putCommentIds = aData.commentId;
        putHandleComment(action, putCommentIds, objBlock, disableObj)
    }
}

/**
 * block UI
 */

function blockObj(obj) {
    obj.block({
        message: '<h6><span class=\'default-color0\'>loading...</span></h6>',
        css: {
            backgroundColor: 'rgba(255,255,255,0)',
            color: 'rgba(255,255,255,0.8)',
            border: '0px'
        }
    });
}
function unblockObj(obj) {
    obj.unblock();
}
/**
 *ajax
 */
function putRestoreComment(action, commentIds, objBlock, disableObj) {
    $.ajax({
        method: "PUT",
        url: "/v1.0/comments",
        headers: {
            "Accept-Language": $.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0, 2),
            "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
        },

        data: {action: action, commentIds: commentIds},
        success: function (data, status, jqXHR) {
            if (data.monitorName != undefined) {
                objBlock.prev().html('');
            }
            if (data.action == 'RES') {
                objBlock.prev().html('');
                //$(objBlock.children().first()).hide();
                //disableObj.attr('disabled', false);
                objBlock.html('Restore');
                disableObj.html($.i18n.prop("ums.monitor.all.comment.select"));
            }
            objBlock.prev().unblock();
            objBlock.unblock();
            objBlock.next().unblock();
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            //TODO: handle other status
            if (XMLHttpRequest.status == "401") {
                window.location = "/login";
                objBlock.next().unblock();
                objBlock.prev().unblock();
                objBlock.unblock();
            }
        }
    });
}
function putHandleComment(action, commentIds, objBlock, disableObj) {
    $.ajax({
        method: "PUT",
        url: "/v1.0/comments",
        headers: {
            "Accept-Language": $.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0, 2),
            "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
        },
        data: {action: action, commentIds: commentIds},
        success: function (data, status, jqXHR) {
            if (data.monitorName != undefined) {
                objBlock.prev().prev().html(data.monitorName);
            }
            if (data.action == 'DEL') {
                $(objBlock.prev().children().first()).show();
                $(objBlock.prev().children().first()).removeClass().addClass("label label-danger");
                $(objBlock.prev().children().first()).html($.i18n.prop("ums.report.comment.one.delete"));
                disableObj.attr('disabled', true);
            } else if (data.action == 'HIDD') {
                $(objBlock.prev().children().first()).show();
                $(objBlock.prev().children().first()).removeClass().addClass("label label-info");
                $(objBlock.prev().children().first()).html($.i18n.prop("ums.report.comment.one.blind"));
                disableObj.attr('disabled', true);
            }
            //else if (data.action == 'PASS') {
            //    $(objBlock.prev().children().first()).show();
            //    $(objBlock.prev().children().first()).removeClass().addClass("label label-default");
            //    $(objBlock.prev().children().first()).html("무시");
            //    disableObj.attr('disabled', true);
            //}
            objBlock.prev().prev().unblock();
            objBlock.prev().unblock();
            objBlock.unblock();
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            //TODO: handle other status
            if (XMLHttpRequest.status == "401") {
                window.location = "/login";
                objBlock.unblock();
            }
        }
    });
}
function getUserInvalidTime() {
    $.ajax({
        method: 'GET',
        url: '/v1.0/userInvalid/' + selectUid,
        headers: {
            "Accept-Language": $.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0, 2),
            "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
        },
        success: function (data, status, jqXHR) {
            searchResult[selectIndex].invalidFrom = data.userInvalid.invaildFrom;
            searchResult[selectIndex].invalidTo = data.userInvalid.invaildTo;
            if (data.userInvalid == '' || new Date(Date.parse(data.userInvalid.invaildTo.replace(/-/g, "/"))) > new Date()) {
                $('#invalidFrom').html(data.userInvalid.invaildFrom == '' ? 'forever' : data.userInvalid.invaildFrom);
                $('#invalidTo').html(data.userInvalid.invaildTo == '' ? 'forever' : data.userInvalid.invaildTo);
            }
            else if (new Date(Date.parse(data.userInvalid.invaildTo.replace(/-/g, "/"))) < new Date()) {
                $('#invalidFrom').html(data.userInvalid.invaildFrom == '' ? '-' : data.userInvalid.invaildFrom);
                $('#invalidTo').html(data.userInvalid.invaildTo == '' ? '-' : data.userInvalid.invaildTo);
            }
        }
    });
}
function showUserStatusWithCorrectWord(status, previousStatus, invalidTo) {
    if (status == 'DEL') {
        //if (previousStatus == '') {
        return 'DEL<br><span class="danger">delete by user</span>';
        //} else {
        //    return 'DEL<br><span class="danger">Removed by UMS</span>';
        //}
    } else if (status == 'OUT') {
        if (invalidTo == undefined || invalidTo == '') {
            return 'OUT<br><span class="danger"> forever</span>';
        } else {
            return 'OUT<br><span class="danger"> 3 months</span>';
        }
    } else {
        return status;
    }
}


function requestTotalReportCount(uid) {
    blockObj($('#userReportedCount').parent());
    $.ajax({
        method: 'GET',
        url: '/v1.0/users/' + uid + '/totalReportCount/',
        headers: {
            "Accept-Language": $.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0, 2),
            "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
        },
        success: function (data, status, jqXHR) {
            //$('#userReportedCount').html(data);
            $('#userReportedCount').parent().unblock();
        },
        error: function () {
            $('#userReportedCount').html('-');
            $('#userReportedCount').parent().unblock();
            if (XMLHttpRequest.status == '401') {
                window.location = '/login';
            }
        }
    });
}

function requestAccountReportTypeCount(uid) {
    $.ajax({
        method: 'GET',
        url: '/v1.2/users/' + uid + '/accountReportTypeCount/',
        headers: {
            "Accept-Language": $.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0, 2),
            "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
        },
        success: function (data, status, jqXHR) {
            accountReportCounts = data.reportTypeCount;
            $('#typeRT100A').html(data.reportTypeCount.rt100Count);
            $('#typeRT200A').html(data.reportTypeCount.rt200Count);
            $('#typeRT300A').html(data.reportTypeCount.rt300Count);
            $('#typeRT400A').html(data.reportTypeCount.rt400Count);
            $('#typeRT500A').html(data.reportTypeCount.rt500Count);
            $('#typeRT600A').html(data.reportTypeCount.rt600Count);
            $('#typeRT999A').html(data.reportTypeCount.rt999Count);
            $('#accountReportCountPopover').parent().unblock();
        },
        error: function () {
            uidForAccountReportCounts = '';
            $('#accountReportCountPopover').parent().unblock();
            if (XMLHttpRequest.status == '401') {
                window.location = '/login';
            }
        }
    });
}

function requestDeleteReportTypeCount(uid) {
    $.ajax({
        method: 'GET',
        url: '/v1.0/users/' + uid + '/deleteCommentReportTypeCount/',
        headers: {
            "Accept-Language": $.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0, 2),
            "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
        },
        success: function (data, status, jqXHR) {
            deleteCommetsCounts = data.reportTypeCount;
            $('#typeRT100D').html(data.reportTypeCount.rt100Count);
            $('#typeRT200D').html(data.reportTypeCount.rt200Count);
            $('#typeRT300D').html(data.reportTypeCount.rt300Count);
            $('#typeRT400D').html(data.reportTypeCount.rt400Count);
            $('#typeRT500D').html(data.reportTypeCount.rt500Count);
            $('#typeRT600D').html(data.reportTypeCount.rt600Count);
            $('#typeRT999D').html(data.reportTypeCount.rt999Count);
            $('#totalDeletedCommentsCountPopover').parent().unblock();
        },
        error: function () {
            uidForDeleteCommetsCounts = '';
            $('#totalDeletedCommentsCountPopover').parent().unblock();
            if (XMLHttpRequest.status == '401') {
                window.location = '/login';
            }
        }
    });
}

function requestBestCommentCount(uid) {
    blockObj($('#bestCommentsCount'));
    $.ajax({
        method: 'GET',
        url: '/v1.0/users/' + uid + '/bestCommentCount/',
        headers: {
            "Accept-Language": $.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0, 2),
            "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
        },
        success: function (data, status, jqXHR) {
            $('#bestCommentsCount').html(data);
            $('#bestCommentsCount').unblock();
        },
        error: function () {
            $('#bestCommentsCount').html('-');
            $('#bestCommentsCount').unblock();
            if (XMLHttpRequest.status == '401') {
                window.location = '/login';
            }
        }
    });
}

function requestWarningAlert(uid) {
    blockObj($('#warningAlert'));
    $.ajax({
        method: 'GET',
        url: '/v1.2/users/' + uid + '/handleHistory/',
        headers: {
            "Accept-Language": $.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0, 2),
            "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
        },
        success: function (data, status, jqXHR) {
            if (data.userHandleHistoryResultList != undefined) {
                if (data.userHandleHistoryResultList[0].handleResult == 'ALT1') {
                    $('#warningAlert').html('<span class="danger">R<br>' + data.userHandleHistoryResultList[0].cdate + '</sapn>');
                } else {
                    $('#warningAlert').html('<span class="danger">D<br>' + data.userHandleHistoryResultList[0].cdate + '</sapn>');
                }
            } else {
                $('#warningAlert').html('-');
            }
            $('#warningAlert').unblock();
        },
        error: function () {
            $('#warningAlert').html('-');
            $('#warningAlert').unblock();
            if (XMLHttpRequest.status == '401') {
                window.location = '/login';
            }
        }
    });
}

function requestLoginType(uid) {
    blockObj($('#loginType'));
    $.ajax({
        method: 'GET',
        url: '/v1.2/users/' + uid + '/loginType/',
        headers: {
            "Accept-Language": $.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0, 2),
            "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
        },
        success: function (data, status, jqXHR) {
            $('#loginType').html(data.loginType);
            $('#loginType').unblock();
        },
        error: function () {
            $('#loginType').html('-');
            $('#loginType').unblock();
            if (XMLHttpRequest.status == '401') {
                window.location = '/login';
            }
        }
    });
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
                freshUserInfoData(data.pageImpl.content[0]);
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            //TODO: handle other status
            selectUid = '';
            selectIndex = '';
            selectUser = '';
            //freshUserInfoData();
            $('div.page-content').unblock();
            if (XMLHttpRequest.status == '401') {
                window.location = '/login';
            }
        }
    });
}

function requestSearchUser(searchSource) {

    var field = $('#userInfoSearchField').val();
    var q = field + '=' + $('#userInfoSearchKeyword').val();
    if ($('#searchSource').val() != undefined && $('#searchSource').val() != '') {
        if ($.trim(q) != '') {
            q = q + ",";
        }
        q = q + 'searchSource=' + $('#searchSource').val();
        $.cookie("searchSource", $('#searchSource').val());
    }

    if ($('#userInfoSearchKeyword').val() == '') {
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

function resetUserPassword() {
    blockObj($('#editUserInfoResetPasswordButton'));
    console.log(selectUser);
    $.ajax({
        method: 'PUT',
        url: '/v1.0/users/' + selectUser.uid,
        headers: {
            "Accept-Language": $.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0, 2),
            "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
        },
        data: {action: 'MODIFYPASSWORD'},
        success: function (data, status, jqXHR) {
            var user = data.userDTO;
            console.log(user);
            $('#editUserInfoResetPasswordButton').unblock();
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $('#editUserInfoResetPasswordButton').unblock();
            if (XMLHttpRequest.status == '401') {
                window.location = '/login';
            }
        }
    });
}
function editUserInfo() {
    var q = '';
    if (checkedPhotoReset) {
        q = q + 'photo=';
        blockObj($('.profile-img').parent());
    }
    if (checkedNicknameReset) {
        if (q != '')
            q = q + ',';
        q = q + 'nickName=';
        blockObj($('#name'));
    }
    if (checkedMessageReset) {
        if (q != '')
            q = q + ',';
        q = q + 'introMessage=';
        blockObj($('#introMessage'));
    }
    if (checkedSubscribeReset) {
        if (q != '')
            q = q + ',';
        q = q + 'subscribe=';
        blockObj($('#subscribeEditorCount'));
        blockObj($('#subscribeNormalCount'));
    }
    if (checkedSubscriberReset) {
        if (q != '')
            q = q + ',';
        q = q + 'subscriber=';
        blockObj($('#subscriberCount'));
    }
    if ($.trim(selectUser.sex) != changeSex) {
        if (q != '')
            q = q + ',';
        q = q + 'sex=' + changeSex;
        blockObj($('#sex'));
    }
    if ($.trim(selectUser.birthday) != changeBirthday) {
        if (q != '')
            q = q + ',';
        q = q + 'birthday=' + changeBirthday;
        blockObj($('#birthday'));
    }
    if ($.trim(selectUser.email) != changeEmail) {
        if (q != '')
            q = q + ',';
        q = q + 'email=' + changeEmail;
        blockObj($('#email'));
    }

    if (q == '') {
        $('#resetUserInfoPopoverButton').popover('hide');
        $('#editUserInfoPopoverButton').popover('hide');
        return;
    }

    $.ajax({
        method: 'PUT',
        url: '/v1.0/users/' + selectUid,
        headers: {
            "Accept-Language": $.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0, 2),
            "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
        },
        data: {action: 'MODIFYPROFILES', q: encodeURI(q)},
        success: function (data, status, jqXHR) {
            var user = data.userDTO;
            if (window.location.hostname == 'ums.allets.com') {
                $('.profile-img').attr('src', 'https://s3.ap-northeast-2.amazonaws.com/an2-prd-allets-contents/' + user.photo);
            } else {
                $('.profile-img').attr('src', 'https://s3.ap-northeast-2.amazonaws.com/an2-prd-allets-contents/' + user.photo);
            }
            if (user.name == undefined || user.name == null) {
                $('#name').html('');
            } else {
                $('#name').html(user.name);
            }
            $('#introMessage').html(user.introMessage);
            $('#subscribeEditorCount').html(user.subscribeEditorCount);
            $('#subscribeNormalCount').html(user.subscribeCount - user.subscribeEditorCount);
            $('#subscriberCount').html(user.subscriberCount);
            if (user.age15plus == 0) {
                $('#certifyOver15').html('N');
            } else {
                $('#certifyOver15').html('Y');
            }
            $('#loginType').html('');
            $('#userStatus').html(showUserStatusWithCorrectWord(user.status, user.previousStatus, user.invalidTo));
            $('#sex').html(user.sex);
            $('#birthday').html(user.birthday);
            $('#email').html(user.email);

            selectUser.email = user.email;
            selectUser.birthday = user.birthday;
            selectUser.sex = user.sex;
            selectUser.subscribeCount = user.subscribeCount;
            selectUser.subscribeEditorCount = user.subscribeEditorCount;
            selectUser.subscriberCount = user.subscriberCount;
            $('.profile-img').parent().unblock();
            $('#name').unblock();
            $('#introMessage').unblock();
            $('#subscribeEditorCount').unblock();
            $('#subscribeNormalCount').unblock();
            $('#subscriberCount').unblock();
            $('#sex').unblock();
            $('#birthday').unblock();
            $('#email').unblock();
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $('.profile-img').parent().unblock();
            $('#name').unblock();
            $('#introMessage').unblock();
            $('#subscribeEditorCount').unblock();
            $('#subscribeNormalCount').unblock();
            $('#subscriberCount').unblock();
            $('#sex').unblock();
            $('#birthday').unblock();
            $('#email').unblock();
            if (XMLHttpRequest.status == '401') {
                window.location = '/login';
            }
        }
    });
}
