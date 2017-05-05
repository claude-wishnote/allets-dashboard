/**
 * Created by claude on 2015/9/6.
 */
$(document).ready(function () {
    $.cookie('allets-data-location', window.location);
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
    if (window.location.pathname == '/contentView') {
        $('#itemContentView').addClass('active');
    }
    if (window.location.pathname == '/editorView') {
        $('#itemEditorView').addClass('active');
     }
    if (window.location.pathname == '/userInformation') {
        $('#itemUserInformation').addClass('active');
     }

    $('#itemContentView').on('click', function (e) {
        jumpToNewPage('/contentView')
    });
    $('#itemEditorView').on('click', function (e) {
        jumpToNewPage('/editorView')
    });
    $('#itemUserInformation').on('click', function (e) {
        jumpToNewPage('/userInformation')
    });

})
function hightLightForDevTest() {
    $('#itemContentView .menu-dropdown span').addClass('blue');

    $('#itemEditorView .menu-dropdown span').addClass('blue');

    $('#itemUserInformation .menu-dropdown span').addClass('blue');

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