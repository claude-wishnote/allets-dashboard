/**
 * Created by claude on 2015/9/6.
 */
$(document).ready(function () {
    $.cookie('umslocation', window.location);
    if ($.cookie("monitor")) {
        user = JSON.parse($.cookie("monitor"));
    }
    hightLightForDevTest();
    if (user.level == 'CS') {
        $('#itemReported').hide();
        $('#itemHandled').hide();
        $('#itemCommentsMonitor').hide();
        $('#subitemBlackListManagement').hide();
        $('#itemRightsManagement').hide();
    }
    if (user.level == 'NORMAL') {
        $('#subitemAccountCSManagement').hide();
    }
    if (window.location.pathname == '/handleRules') {
        $('#itemRule').addClass('active open');
        $('#submenuItemRule').css('display', 'block');
        $('#subitemRule').addClass('active');
    }
    if (window.location.pathname == '/reportedComments') {
        $('#itemReported').addClass('active open danger');
        $('#submenuItemReported').css('display', 'block');
        $('#subitemReportedComments').addClass('active');
    }
    if (window.location.pathname == '/reportedAccounts') {
        $('#itemReported').addClass('active open');
        $('#submenuItemReported').css('display', 'block');
        $('#subitemReportedUser').addClass('active');
    }
    if (window.location.pathname == '/handledComments') {
        $('#itemHandled').addClass('active open');
        $('#submenuItemHandled').css('display', 'block');
        $('#subitemHandledComments').addClass('active');
    }
    if (window.location.pathname == '/handledAccounts') {
        $('#itemHandled').addClass('active open');
        $('#submenuItemHandled').css('display', 'block');
        $('#subitemHandledUsers').addClass('active');
    }
    if (window.location.pathname == '/allComments') {
        $('#itemCommentsMonitor').addClass('active open');
        $('#submenuItemCommentsMonitor').css('display', 'block');
        $('#subitemCommentsMonitor').addClass('active');
    }
    if (window.location.pathname == '/accountInfo') {
        $('#itemAccountManagement').addClass('active open');
        $('#submenuItemAccountManagement').css('display', 'block');
        $('#subitemAccountManagement').addClass('active');
    }
    if (window.location.pathname == '/accountInfoCS') {
        $('#itemAccountManagement').addClass('active open');
        $('#submenuItemAccountManagement').css('display', 'block');
        $('#subitemAccountCSManagement').addClass('active');
    }
    if (window.location.pathname == '/blacklist') {
        $('#itemAccountManagement').addClass('active open');
        $('#submenuItemAccountManagement').css('display', 'block');
        $('#subitemBlackListManagement').addClass('active');
    }
    if (window.location.pathname == '/monitors' || window.location.pathname == '/monitor') {
        $('#itemRightsManagement').addClass('active open');
        $('#submenuItemRightsManagement').css('display', 'block');
        $('#subitemItemRightsManagement').addClass('active');
    }
    if (window.location.pathname == '/slangWords') {
        $('#itemRightsManagement').addClass('active open');
        $('#submenuItemRightsManagement').css('display', 'block');
        $('#subitemIllegalWordManagement').addClass('active');
    }
    if (window.location.pathname == '/monitorStatistics') {
        $('#itemRightsManagement').addClass('active open');
        $('#submenuItemRightsManagement').css('display', 'block');
        $('#subitemMonitorStatistics').addClass('active');
    }

    $('#subitemRule').on('click', function (e) {
        jumpToNewPage('/handleRules')
    });
    $('#subitemReportedComments').on('click', function (e) {
        jumpToNewPage('/reportedComments')
    });
    $('#subitemReportedUser').on('click', function (e) {
        jumpToNewPage('/reportedAccounts')
    });
    $('#subitemHandledComments').on('click', function (e) {
        jumpToNewPage('/handledComments')
    });
    $('#subitemHandledUsers').on('click', function (e) {
        jumpToNewPage('/handledAccounts')
    });
    $('#subitemCommentsMonitor').on('click', function (e) {
        jumpToNewPage('/allComments')
    });
    $('#subitemAccountManagement').on('click', function (e) {
        jumpToNewPage('/accountInfo')
    });
    $('#subitemAccountCSManagement').on('click', function (e) {
        jumpToNewPage('/accountInfoCS')
    });
    $('#subitemBlackListManagement').on('click', function (e) {
        jumpToNewPage('/blacklist')
    });
    $('#subitemItemRightsManagement').on('click', function (e) {
        var url = "/monitor";
        if (user) {
            if (user.level == 'ADMIN') {
                url = "/monitors";
            } else {
                url = "/monitor";
            }
        }
        jumpToNewPage(url)
    });
    $('#subitemIllegalWordManagement').on('click', function (e) {
        jumpToNewPage('/slangWords')
    });
    $('#subitemMonitorStatistics').on('click', function (e) {
        jumpToNewPage('/monitorStatistics')
    });

})
function hightLightForDevTest() {
    $('#itemReported .menu-dropdown span').addClass('blue');
    $('#subitemReportedComments span').addClass('blue');
    $('#subitemReportedUser span').addClass('blue');

    $('#itemHandled .menu-dropdown span').addClass('blue');
    $('#subitemHandledComments span').addClass('blue');
    $('#subitemHandledUsers span').addClass('blue');

    $('#itemCommentsMonitor .menu-dropdown span').addClass('blue');
    $('#submenuItemCommentsMonitor span').addClass('blue');

    $('#itemAccountManagement .menu-dropdown span').addClass('blue');
    $('#subitemAccountManagement span').addClass('blue');
    $('#subitemAccountCSManagement span').addClass('blue');
    $('#subitemBlackListManagement span').addClass('blue');

    $('#itemRightsManagement .menu-dropdown span').addClass('blue');
    $('#subitemIllegalWordManagement span').addClass('blue');
    $('#subitemItemRightsManagement span').addClass('blue');
    $('#subitemMonitorStatistics span').addClass('blue');

    $('#itemRule .menu-dropdown span').addClass('blue');
    $('#submenuItemRule span').addClass('blue');

    $('.glyphicon-chevron-up').on('click', function (e) {
        $('.page-body').animate({scrollTop: $('.navbar-inner').offset().top}, 200);
    });
    $('.glyphicon-chevron-down').on('click', function (e) {
        $('.page-body').animate({scrollTop: $('#bottomSign').offset().top - $('#topSign').offset().top}, 200);
    });


}
function jumpToNewPage(newPageUrl) {
    if (window.location == newPageUrl) {
        return;
    }
    window.location = newPageUrl;

}

var user = null;
var LEVEL = {
    ADMIN: "ADMIN",
    NORMAL: "NORMAL"
};
var STATUS = {
    ACTV: "ACTV",
    HOLD: "HOLD"
};

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