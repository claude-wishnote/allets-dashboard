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
            "Accept-Language":$.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0,2),
            "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
        },
        data: {
            name: name,
            password: encyptedPwd
        },
        success: function (data, status, jqXHR) {
            //console.log(data);
            data.password = encyptedPwd;
            $.cookie("monitor", JSON.stringify(data), {path: '/', expires: 7});
            //console.log( $.cookie('umslocation'));
            if($.cookie('umslocation')==undefined)
            {
                window.location ="/reportedComments";

            }else
            {
                window.location = $.cookie('umslocation');
            }
        },
        error: function
            (XMLHttpRequest, textStatus, errorThrown) {
            //console.log(XMLHttpRequest);
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
