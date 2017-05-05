/*
 * FOR LOGIN PAGE
 */

$(document).ready(function () {
    autoLogin();
});

//bind click event to login button
$('.loginbox-submit').on('click', function (e) {
    login();
});

function autoLogin() {
    if ($.cookie("monitor") != undefined) {
        login(JSON.parse($.cookie("monitor")).name, JSON.parse($.cookie("monitor")).password);
    } else {
        return;
    }
}

//login by id and password
function login(name, password) {
    blockObj($('div.login-container'));
    var encyptedPwd = "";
    if (name == undefined && password == undefined) {
        name = $('#name').val();
        password = $('#password').val();

        if (name == "" || password == "") {
            alert("Please input ID and password.")
            return;
        }
        encyptedPwd = $.md5(password);
    } else {
        $('#name').val(name);
        $('#password').val(password);
        encyptedPwd = password;
    }
    //console.log("name:" + name);
    //console.log("password:" + encyptedPwd);
    $.ajax({
        type: 'POST',
        url: '/v1.0/sessions',
        headers: {
            "Accept-Language":$.cookie("dataLanguage"),
            "X-ALLETS-LANG": $.cookie("dataLanguage").substring(0,2),
            "X-ALLETS-COUNTRY": $.cookie("dataLanguage").substring(3)
        },
        data: {
            name: name,
            password: encyptedPwd
        },
        success: function (data, status, jqXHR) {
            $('div.login-container').unblock();
            data.password = encyptedPwd;
            $.cookie("monitor", JSON.stringify(data), {path: '/', expires: 7});
            //console.log( $.cookie('allets-data-location'));
            if($.cookie('allets-data-location')==undefined)
            {
                window.location ="/contentView";

            }else
            {
                window.location = $.cookie('allets-data-location');
            }
        },
        error: function
            (XMLHttpRequest, textStatus, errorThrown) {
            $('div.login-container').unblock();
            //var httpStatus = XMLHttpRequest.status;
            //var responseText = XMLHttpRequest.responseText;
            var responseJson = XMLHttpRequest.responseJSON;
            alert(responseJson.message);
        }
    });
}

//enter key pressed
$(document).keydown(function (event) {
    if (event.keyCode == 13) {
        login();
    }
});

function blockObj(obj) {
    obj.block({
        message: '<h6><span class=\'default-color0\'>login...</span></h6>',
        css: {
            backgroundColor: 'rgba(255,255,255,0)',
            color: 'rgba(255,255,255,0.8)',
            border: '0px'
        }
    });
}