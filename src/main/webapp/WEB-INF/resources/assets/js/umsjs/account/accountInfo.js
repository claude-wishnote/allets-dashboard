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
var currentPage;
var totalPage;
var searchResult = '';

var commentTable = null;
var tableData;
var testData = undefined;

var editUserInfoPopover = false;
var resetUserInfoPopover = false;
var userInfoSendAlertA1Popover = false;
var userInfoSendAlertA2Popover = false;
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

    initReportedCommentTable(fnInfoCallback, footerCallback, fnCreatedRow, fnInitComplete);

    bindPopOverEvent();

    bindButtonEvent();

    var parseUid = GetArgsFromHref(window.location.href, 'uid');
    var temp = /^\d+(\.\d+)?$/;
    if (temp.test(parseUid)) {
        selectUid = parseUid;
        requestUserDataByUid(parseUid)
    }

    changeNickname = $.i18n.prop("ums.account.management.nickname.default");
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
    $('#userStatus').html(showUserStatusWithCorrectWord(user.status, user.previousStatus, user.invalidTo));
    $('#userReportedCount').html(user.userReportedCount);
    $('#deleteCommentsCount').html(user.deleteCommentsCount);
    if (window.location.hostname == 'ums.stage-allets.com') {
        $('.profile-img').attr('src', 'https://s3.ap-northeast-2.amazonaws.com/an2-prd-allets-contents/' + user.photo);
    } else {
        $('.profile-img').attr('src', 'https://s3.ap-northeast-2.amazonaws.com/an2-prd-allets-contents/' + user.photo);
    }

    $('#userInfoBlackListButton').attr('disabled', user.blackList);
    //$(".profile-img").animate({opacity:'toggle'},"slow",null,function(){
    //    $(".profile-img").attr("src", 'https://s3.ap-northeast-2.amazonaws.com/an2-prd-allets-contents/' + user.photo);
    //    $(".profile-img").animate({opacity:'toggle'},"slow");
    //});
    if (user.status == 'OUT') {
        $('#userInfoOutButton').attr('disabled', false);
        $('#userInfoUnOutButton').attr('disabled', false);
        if (user.invalidTo == '' || new Date(Date.parse(user.invalidTo.replace(/-/g, "/"))) > new Date()) {
            $('#userInfoOutButton').hide();
            $('#userInfoUnOutButton').show();
            $('#userInfoOutfButton').show();
            $('#userInfoUnOutfButton').hide();
            $('#invalidFrom').html(user.invalidFrom == '' ? 'forever' : user.invalidFrom);
            $('#invalidTo').html(user.invalidTo == '' ? 'forever' : user.invalidTo);
        }
        else if (new Date(Date.parse(user.invalidTo.replace(/-/g, "/"))) < new Date()) {
            $('#userInfoOutButton').show();
            $('#userInfoUnOutButton').hide();
            $('#userInfoOutfButton').show();
            $('#userInfoUnOutfButton').hide();
            $('#invalidFrom').html(user.invalidFrom == '' ? '-' : user.invalidFrom);
            $('#invalidTo').html(user.invalidTo == '' ? '-' : user.invalidTo);
        }
    } else if (user.status == 'DEL') {
        $('#userInfoOutButton').attr('disabled', false);
        $('#userInfoUnOutButton').attr('disabled', false);

        $('#userInfoOutButton').show();
        $('#userInfoUnOutButton').hide();
        $('#userInfoOutfButton').show();
        $('#userInfoUnOutfButton').hide();
        $('#invalidFrom').html('forever');
        $('#invalidTo').html('forever');
    } else {
        $('#userInfoOutButton').attr('disabled', false);
        $('#userInfoUnOutButton').attr('disabled', false);

        $('#userInfoOutButton').show();
        $('#userInfoUnOutButton').hide();
        $('#userInfoOutfButton').show();
        $('#userInfoUnOutfButton').hide();
        $('#invalidFrom').html('-');
        $('#invalidTo').html('-');
    }

    requestTotalReportCount(user.uid);
    requestBestCommentCount(user.uid);
    requestWarningAlert(user.uid);
    requestLoginType(user.uid);
    tableData = null;
    requestTableData(0);
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
        } else {
            //$('#searchSource').show();
            $('#searchSource').hide();
        }
    });
    $('#userInfoBlackListButton').on('click', function () {
        if (selectUid == undefined || selectUid == '')
            return;
        blackUser($(this));
    });
    $('#userInfoOutButton').on('click', function () {
        if (selectUid == undefined || selectUid == '')
            return;
        handle = 'OUT';
        $("#handleUserModel").modal('show');
    });
    $('#userInfoUnOutButton').on('click', function () {
        if (selectUid == undefined || selectUid == '')
            return;
        handle = 'UNOUT';
        $("#handleUserModel").modal('show');
        //handleUser('UNOUT', $(this));
    });
    $('#userInfoOutfButton').on('click', function () {
        if (selectUid == undefined || selectUid == '')
            return;
        handle = 'OUTF';
        $("#handleUserModel").modal('show');
        //handleUser('DEL', $(this));
    });
    $('#userInfoUnOutfButton').on('click', function () {
        if (selectUid == undefined || selectUid == '')
            return;
        handle = 'UNOUTF';
        $("#handleUserModel").modal('show');
        //handleUser('UNDEL', $(this));
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
}

function bindPopOverEvent() {
    bindEditUserInfoPopoverButton();
    bindResetUserInfoPopoverButton();
    bindEditUserinfoModel();
    bindAlertSendButton();
    bindReportTypeCountsButton();
    bindHandlerUserModel();
    $('.page-body').on('click', function () {
        //if(resetUserInfoPopover)
        //{
        //    $('#resetUserInfoPopoverButton').popover('hide');
        //}
        //if(userInfoSendAlertA1Popover)
        //{
        //    $('#userInfoSendAlertA1').popover('hide');
        //}
        //if(userInfoSendAlertA2Popover)
        //{
        //    $('#userInfoSendAlertA2').popover('hide');
        //}
        //if(userInfoDeleteComentsTypePopover)
        //{
        //    $('#userInfoDeleteComentsTypeButton').popover('hide');
        //}
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

function bindHandlerUserModel() {
    $('#handleUserModel').on('show.bs.modal', function () {
        if (handle == 'UNOUT' || handle == 'UNOUTF') {
            $('#handleUserSting').html($.i18n.prop("ums.account.management.unout.model.string") + $.trim(selectUser.email));
        } else if (handle == 'OUT') {
            $('#handleUserSting').html($.i18n.prop("ums.account.management.out.model.string") + $.trim(selectUser.email));
        } else if (handle == 'OUTF') {
            $('#handleUserSting').html($.i18n.prop("ums.account.management.outf.model.string") + $.trim(selectUser.email));
        }
    });
    $('#handleOK').on('click', function () {
        handleUser(handle, $(this));
    });
    $('#handleCancel').on('click', function () {
        handle = '';
    });
    $('#handleUserModel').on('hidden.bs.modal', function () {
        handle = '';
    })
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
        $('#userInfoSendAlertA1').popover('hide');
        $('#userInfoSendAlertA2').popover('hide');
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

function bindAlertSendButton() {
    $('#userInfoSendAlertA1').popover({
        placement: 'bottom',
        trigger: 'click',
        html: true,
        title: '',
        content: '<p>' + $.i18n.prop("ums.account.management.sendalert") + '</p><div class=\'btn-area text-center\'>' +
        '<a id=\'sendAlertA1\'class=\'btn btn-darkorange btn-sm\'>' + $.i18n.prop("ums.monitor.all.comment.confirm") + '</a>' +
        '<a id=\'closeA1\'class=\'btn btn-default btn-sm\'>' + $.i18n.prop("ums.monitor.all.comment.cancel") + '</a></div>'
    });
    $('#userInfoSendAlertA2').popover({
        placement: 'bottom',
        trigger: 'click',
        html: true,
        title: '',
        content: '<p>' + $.i18n.prop("ums.account.management.sendalert") + '</p><div class=\'btn-area text-center\'>' +
        '<a id=\'sendAlertA2\'class=\'btn btn-darkorange btn-sm\'>' + $.i18n.prop("ums.monitor.all.comment.confirm") + '</a>' +
        '<a id=\'closeA2\'class=\'btn btn-default btn-sm\'>' + $.i18n.prop("ums.monitor.all.comment.cancel") + '</a></div>'
    });
    $('#userInfoSendAlertA1').on(
        'shown.bs.popover', function () {
            userInfoSendAlertA1Popover = true;
            $('#sendAlertA1').on(
                'click', function () {
                    sendAlert(selectUid, 'R', objBlock);
                    $('#userInfoSendAlertA1').popover('hide');
                }
            );
            $('#closeA1').on(
                'click', function () {
                    $('#userInfoSendAlertA1').popover('hide');
                });
        }
    );
    $('#userInfoSendAlertA1').on(
        'hidden.bs.popover', function () {
            userInfoSendAlertA1Popover = false;
        }
    );
    $('#userInfoSendAlertA2').on(
        'shown.bs.popover', function () {
            userInfoSendAlertA2Popover = true;
            $('#sendAlertA2').on(
                'click', function () {
                    $('#userInfoSendAlertA2').popover('hide');
                }
            );
            $('#closeA2').on(
                'click', function () {
                    $('#userInfoSendAlertA2').popover('hide');
                });
        }
    );
    $('#userInfoSendAlertA2').on(
        'hidden.bs.popover', function () {
            userInfoSendAlertA2Popover = false;
        }
    );
}

function bindReportTypeCountsButton() {
    $('#userInfoDeleteComentsTypeButton').popover({
        placement: 'bottom',
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
        placement: 'bottom',
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
            //$('#userInfoOutButton').attr('disabled',false);
            //$('#userInfoUnOutButton').attr('disabled',false);
            searchResult[selectIndex].invalidFrom = data.userInvalid.invaildFrom;
            searchResult[selectIndex].invalidTo = data.userInvalid.invaildTo;
            if (data.userInvalid == '' || new Date(Date.parse(data.userInvalid.invaildTo.replace(/-/g, "/"))) > new Date()) {
                $('#userInfoOutButton').hide();
                $('#userInfoUnOutButton').show();
                $('#userInfoOutfButton').show();
                $('#userInfoUnOutfButton').hide();
                $('#invalidFrom').html(data.userInvalid.invaildFrom == '' ? 'forever' : data.userInvalid.invaildFrom);
                $('#invalidTo').html(data.userInvalid.invaildTo == '' ? 'forever' : data.userInvalid.invaildTo);
            }
            else if (new Date(Date.parse(data.userInvalid.invaildTo.replace(/-/g, "/"))) < new Date()) {
                $('#userInfoOutButton').show();
                $('#userInfoUnOutButton').hide();
                $('#userInfoOutfButton').show();
                $('#userInfoUnOutfButton').hide();
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
function handleUser(action, obj) {
    if (action == 'OUT') {
        obj = $('#userInfoOutButton');
    } else if (action == 'UNOUT') {
        obj = $('#userInfoUnOutButton');
    } else if (action == 'OUTF') {
        obj = $('#userInfoOutfButton');
    } else if (action == 'UOUTF') {
        obj = $('#userInfoUnOutfButton');
    }
    blockObj(obj);
    blockObj($('#userStatus'));
    $.ajax({
        method: 'PUT',
        url: '/v1.0/users/' + selectUid,
        headers: {
            "Accept-Language": $.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0, 2),
            "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
        },
        data: {action: action},
        success: function (data, status, jqXHR) {
            if (searchResult == '') {
                searchResult = {};
                searchResult[selectIndex] = data.userDTO;
            }
            if (data.userDTO.status == 'OUT') {
                obj.unblock();
                if (data.userDTO.invalidTo == '') {
                    $('#userInfoOutButton').attr('disabled', true);
                    $('#userInfoUnOutButton').attr('disabled', true);

                    $('#userInfoOutButton').show();
                    $('#userInfoUnOutButton').hide();
                    $('#userInfoOutfButton').hide();
                    $('#userInfoUnOutfButton').show();
                } else {
                    $('#userInfoOutButton').attr('disabled', false);
                    $('#userInfoUnOutButton').attr('disabled', false);

                    $('#userInfoOutButton').hide();
                    $('#userInfoUnOutButton').show();
                    $('#userInfoOutfButton').show();
                    $('#userInfoUnOutfButton').hide();
                }
                searchResult[selectIndex].status = data.userDTO.status;
                $('#invalidFrom').html(data.userDTO.invalidFrom == '' ? 'forever' : data.userDTO.invalidFrom);
                $('#invalidTo').html(data.userDTO.invalidTo == '' ? 'forever' : data.userDTO.invalidTo);

                $('#userStatus').html(showUserStatusWithCorrectWord(data.userDTO.status, data.userDTO.previousStatus, data.userDTO.invalidTo));
                $('#userStatus').unblock();
                getUserInvalidTime();
            } else if (data.userDTO.status == 'DEL') {
                obj.unblock();
                $('#userInfoOutButton').attr('disabled', true);
                $('#userInfoUnOutButton').attr('disabled', true);

                $('#userInfoOutButton').show();
                $('#userInfoUnOutButton').hide();
                $('#userInfoOutfButton').hide();
                $('#userInfoUnOutfButton').show();
                $('#invalidFrom').html('forever');
                $('#invalidTo').html('forever');
                searchResult[selectIndex].status = data.userDTO.status;
                $('#userStatus').html(showUserStatusWithCorrectWord(data.userDTO.status, data.userDTO.previousStatus, data.userDTO.invalidTo));
                $('#userStatus').unblock();
            } else {
                obj.unblock();
                $('#userInfoOutButton').attr('disabled', false);
                $('#userInfoUnOutButton').attr('disabled', false);

                $('#userInfoOutButton').show();
                $('#userInfoUnOutButton').hide();
                $('#userInfoOutfButton').show();
                $('#userInfoUnOutfButton').hide();
                $('#invalidFrom').html('-');
                $('#invalidTo').html('-');
                searchResult[selectIndex].status = data.userDTO.status;
                $('#userStatus').html(showUserStatusWithCorrectWord(data.userDTO.status, data.userDTO.previousStatus, data.userDTO.invalidTo));
                $('#userStatus').unblock();
            }
        },
        error: function () {
            obj.unblock();
            $('#userStatus').unblock();
            if (XMLHttpRequest.status == '401') {
                window.location = '/login';
            }
        }
    });
    if (action == 'OUTF') {
        $('#userInfoOutButton').attr('disabled', 'disabled');
        $('#userInfoUnOutButton').attr('disabled', 'disabled');
    } else if (action == 'UNOUTF') {
        $('#userInfoOutButton').attr('disabled', '');
        $('#userInfoUnOutButton').attr('disabled', '');
    }
    handle = '';
}
function blackUser(obj) {
    blockObj(obj);
    $.ajax({
        method: 'PUT',
        url: '/v1.0/users/black/' + selectUid,
        headers: {
            "Accept-Language": $.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0, 2),
            "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
        },
        success: function (data, status, jqXHR) {
            obj.unblock();
            $('#userInfoBlackListButton').attr('disabled', true);
            searchResult[selectIndex].blackList = true;
        },
        error: function () {
            obj.unblock();
            if (XMLHttpRequest.status == '401') {
                window.location = '/login';
            }
        }
    });
}

function requestTotalReportCount(uid) {
    blockObj($('#userReportedCount').parent());
    $('#userInfoSendAlertA1').popover('destroy');
    $('#userInfoSendAlertA2').popover('destroy');
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
            $('#userInfoSendAlertA1').popover({
                placement: 'bottom',
                trigger: 'click',
                html: true,
                title: '',
                content: '<p>' + $.i18n.prop("ums.account.management.sendalert") + '</p><div class=\'btn-area text-center\'>' +
                '<a id=\'sendAlertA1\'class=\'btn btn-darkorange btn-sm\'>' + $.i18n.prop("ums.monitor.all.comment.confirm") + '</a>' +
                '<a id=\'closeA1\'class=\'btn btn-default btn-sm\'>' + $.i18n.prop("ums.monitor.all.comment.cancel") + '</a></div>'
            });
            $('#userInfoSendAlertA2').popover({
                placement: 'bottom',
                trigger: 'click',
                html: true,
                title: '',
                content: '<p>' + $.i18n.prop("ums.account.management.sendalert") + '</p><div class=\'btn-area text-center\'>' +
                '<a id=\'sendAlertA2\'class=\'btn btn-darkorange btn-sm\'>' + $.i18n.prop("ums.monitor.all.comment.confirm") + '</a>' +
                '<a id=\'closeA2\'class=\'btn btn-default btn-sm\'>' + $.i18n.prop("ums.monitor.all.comment.cancel") + '</a></div>'
            });
            $('#userInfoSendAlertA1').on(
                'shown.bs.popover', function () {
                    userInfoSendAlertA1Popover = true;
                    $('#sendAlertA1').on(
                        'click', function () {
                            if (selectUid == undefined || selectUid == '')
                                return;
                            var objBlock = $(this).parents('td');
                            sendAlert(selectUid, 'R', objBlock);
                            $('#userInfoSendAlertA1').popover('hide');
                        }
                    );
                    $('#closeA1').on(
                        'click', function () {
                            $('#userInfoSendAlertA1').popover('hide');
                        });
                }
            );
            $('#userInfoSendAlertA1').on(
                'hidden.bs.popover', function () {
                    userInfoSendAlertA1Popover = false;
                }
            );
            $('#userInfoSendAlertA2').on(
                'shown.bs.popover', function () {
                    userInfoSendAlertA2Popover = true;
                    $('#sendAlertA2').on(
                        'click', function () {
                            if (selectUid == undefined || selectUid == '')
                                return;
                            var objBlock = $(this).parents('td');
                            sendAlert(selectUid, 'D', objBlock);
                            $('#userInfoSendAlertA2').popover('hide');
                        }
                    );
                    $('#closeA2').on(
                        'click', function () {
                            $('#userInfoSendAlertA2').popover('hide');
                        });
                }
            );
            $('#userInfoSendAlertA2').on(
                'hidden.bs.popover', function () {
                    userInfoSendAlertA2Popover = false;
                }
            );
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
            var alertInfo = '-';
            var alertCdate = '';
            var alertCdateString = '';
            if (data.userHandleHistoryResultList != undefined) {
                for (var index in data.userHandleHistoryResultList) {
                    if (data.userHandleHistoryResultList[index].handleResult == 'ALT1') {
                        alertInfo = alertInfo + 'R,';
                    } else {
                        alertInfo = alertInfo + 'D,';
                    }
                    var cdate = new Date(Date.parse(data.userHandleHistoryResultList[index].cdate.replace(/-/g, "/")));
                    if (alertCdate == '') {
                        alertCdate = new Date(Date.parse(data.userHandleHistoryResultList[index].cdate.replace(/-/g, "/")));
                        alertCdateString = data.userHandleHistoryResultList[index].cdate;
                    } else {
                        alertCdate = alertCdate > cdate ? alertCdate : cdate;
                        alertCdateString = alertCdate > cdate ? alertCdateString : data.userHandleHistoryResultList[index].cdate;
                    }
                }
                alertInfo = alertInfo + '<br>' + alertCdateString;
                $('#warningAlert').html('<span class="danger">' + alertInfo + '</sapn>');
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

/*
 ajax request methods
 */
function requestTableData(offset) {
    blockObj($('#userInfoReportedCommentTable_wrapper'));
    $.ajax({
        method: "GET",
        url: "/v1.0/comments",
        headers: {
            "Accept-Language": $.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0, 2),
            "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
        },
        data: {action: 'ALLSIMPLE', offset: offset, q: encodeURI('uid=' + selectUid)},
        success: function (data, status, jqXHR) {
            if (offset == 0) {
                tableData = data.pageImpl.content;
                totalPage = data.pageImpl.totalPages;
                if (commentTable != undefined && commentTable != null) {
                    //commentTable.fnDestroy();
                    commentTable.fnClearTable();
                }

                $('#userInfoReportedCommentTable').DataTable().rows.add(data.pageImpl.content).draw(true);
            } else {
                tableData = tableData.concat(data.pageImpl.content);
                //commentTable.fnUpdate( 'a' , 1 , 1 ,true);
                $('#userInfoReportedCommentTable').DataTable().rows.add(data.pageImpl.content).draw(false);
            }
            bindTableButtonEvent();
            currentPage = data.pageImpl.number + 1;
            $('#userInfoReportedCommentTable_wrapper').unblock();
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
 table refresh main method
 */
function initReportedCommentTable(fnInfoCallback, footerCallback, fnCreatedRow, fnInitComplete) {
    commentTable = $('#userInfoReportedCommentTable').dataTable({
        data: tableData,
        'aoColumns': [
            {'mDataProp': 'cDate'},
            {'mDataProp': 'reportCount'},
            {'mDataProp': 'nickName'},
            {'mDataProp': 'commentText'},
            {'mDataProp': 'likeCount'},
            {'mDataProp': 'recommentCount'},
            {'mDataProp': 'commentType'},
            {'mDataProp': 'monitorName'},
            {'mDataProp': 'status'},
            {'mDataProp': null}
        ],
        'aoColumnDefs': [
            {
                "aTargets": 0, orderable: false
            }
        ],
        //'sDom': 'Tflt<\'row DTTTFooter\'<\'col-sm-6\'i><\'col-sm-6\'p>>',
        "aaSorting": [[0, "desc"]],
        'bAutoWidth': false,                    //不自动计算列宽度
        'bInfo': false,//页脚信息
        'bSort': false, //排序功能
        'bFilter': false,  //不使用过滤功能 search
        'bPaginate': false,//是否分页。
        'fnInfoCallback': fnInfoCallback,
        'fnFooterCallback': footerCallback,
        'fnCreatedRow': fnCreatedRow,
        'fnInitComplete': fnInitComplete,
        scrollY: 400

    });
}

/*
 table call backs*************************************
 */
var fnInfoCallback = function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {

};


var fnCreatedRow = function (nRow, aData, iDataIndex) {

    //if (aData.slangType == "A") {
    //    nRow.className = "danger";
    //}
    //$('td:eq(0)', nRow).html('<label><input type="checkbox"><span class="text"></span></label>');
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
    $('td:eq(3)', nRow).html('<a  href="javascript:showModal(' + aData.commentId + ',' + aData.contentId + ',' + cardId + ',' + cardOrder + ',' + parentCommentId + ')">' + aData.commentText + '</a>');

    if (aData.likeCount == 0) {
        $('td:eq(4)', nRow).html("-");
    }
    if (aData.recommentCount == 0) {
        $('td:eq(5)', nRow).html("-");
    }
    if (aData.status == 'HIDD') {
        $('td:eq(8)', nRow).html('<a class=\'label label-info\' rel=\'popover\' data-container=\'#userInfoReportedCommentTable\'>' + $.i18n.prop("ums.report.comment.one.blind") + '</a>');
    } else if (aData.status == 'DEL') {
        $('td:eq(8)', nRow).html('<a class=\'label label-danger\' rel=\'popover\' data-container=\'#userInfoReportedCommentTable\'>' + $.i18n.prop("ums.report.comment.one.delete") + '</a>');
    } else if (aData.status == 'ACTV') {
        //if (aData.handleResult == 'PASS') {
        //    $('td:eq(8)', nRow).html('<a class=\'label label-default\' rel=\'popover\' data-container=\'#userInfoReportedCommentTable\'>무시</a>');
        //} else {
        $('td:eq(7)', nRow).html('');
        if (aData.handleResult != '') {
            $('td:eq(8)', nRow).html('Restored');
        } else {
            $('td:eq(8)', nRow).html('<a class=\'label label-default\' style="display: none" rel=\'popover\' data-container=\'#userInfoReportedCommentTable\'>-</a>');
        }
        //}
    }
    if (aData.handleResult == '' && (aData.status == 'ACTV')) {
        $('td:eq(9)', nRow).html('<a class=\'btn btn-blue btn-sm\' rel=\'popover\' data-container=\'#userInfoReportedCommentTable\'>' + $.i18n.prop("ums.monitor.all.comment.select") + '</a>');
    }
    else
        $('td:eq(9)', nRow).html('<a class=\'btn btn-blue btn-sm\' rel=\'popover\' data-container=\'#userInfoReportedCommentTable\' disabled=\'disabled\'>' + $.i18n.prop("ums.monitor.all.comment.select") + '</a>');
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
    var foothtml = '<a class="btn btn-sm col-sm-12"><span type="text"><b>Load More</b></span></a>';
    $('tfoot').html(foothtml);
    $('tfoot .btn').on(
        'click', function () {
            if (currentPage == totalPage)
                return;
            requestTableData(currentPage * 15);
        }
    );


};

var fnInitComplete = function (oSettings, json) {
    $('.loading-container')
        .addClass('loading-inactive');
};

function sendAlert(uid, alertType, obj) {
    blockObj(obj);
    $.ajax({
        method: 'PUT',
        url: '/v1.0/users/' + uid + '/alert',
        headers: {
            "Accept-Language": $.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0, 2),
            "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
        },
        data: {alertType: alertType},
        success: function (data, status, jqXHR) {
            if (data == undefined || data == null || data == '' || data.alertResult == undefined || data.alertResult == null || data.alertResult == '') {
                obj.unblock();
                return;
            }
            requestWarningAlert(uid);
            obj.unblock();
        },
        error: function () {
            obj.unblock();
            if (XMLHttpRequest.status == '401') {
                window.location = '/login';
            }
        }
    });
}
