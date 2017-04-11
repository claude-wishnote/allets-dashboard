/**
 * Created by jack on 2015/9/1.
 */
$(document).ready(function () {
    $('.page-body').css('height', $(window).height() - 85);
    window.common.init();
    findSlangwordList();
})

$(window).resize(function () {
    $('.page-body').css('height', $(window).height() - 85);
});

var method = "ADD";
var currentSlangId = -1;
var ruleStringLength = 20;

var common = {
    init: function () {
        $('#btn-sm').on('click', function (e) {
            findSlangwordList($('#input-sm').val());
        });
        $('#input-sm').keydown(function (a) {
            if (a.keyCode == 13)
                findSlangwordList($('#input-sm').val());
        });
        $('#btn-save').on('click', function (e) {
            var text = $('#form-control').val();
            if (text.length > ruleStringLength) {
                alert("the slang word is too long.");
                return;
            }
            submitSlangWord();
        });

        $('#btn-cancel').on('click', function (e) {
            hideAddModal();
        });

        $("a[name='btn-add']").on('click', function (e) {
            showAddModal();
        });

        $('#form-control').on('input',
            function () {
                setCharactersNumAlert(ruleStringLength);
            }
        );
        $('#input-sm').attr('placeholder',$.i18n.prop("ums.authority.slang.popover.placeholder"));
        $('#form-control').attr('placeholder',$.i18n.prop("ums.authority.slang.popover.placeholder"));
    }
};

function setCharactersNumAlert(ruleStringLength) {
    var text = $('#form-control').val();
    var leftNum = ruleStringLength - text.length;
    if (leftNum < 0) {
        $('#form-control').addClass('danger');
        $('#charactersNumAlert').removeClass('green');
        $('#charactersNumAlert').addClass('danger');
        $('#charactersNumAlert').html('You should delete ' + (0 - leftNum) + ' characters.');
    } else {
        $('#form-control').removeClass('danger');
        $('#charactersNumAlert').removeClass('danger');
        $('#charactersNumAlert').addClass('green');
        $('#charactersNumAlert').html('You can enter ' + leftNum + ' characters.');
    }
}

function findSlangwordList(field) {
    showLoading();
    $.ajax({
        method: "GET",
        url: "/v1.0/slangs",
        headers: {
            "Accept-Language":$.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0,2),
            "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
        },
        data: {field: field},
        success: function (data) {
            hideLoading();
            initLayout(data);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            //TODO: handle other status
            hideLoading();
            if (XMLHttpRequest.status == "401") {
                window.location = "/login";
            } else {
                showConfirmModal("알 수 없는 문제발생!");
            }
        }
    });
}

function deleteSlangWord(slangId) {
    showLoading();
    $.ajax({
        method: "DELETE",
        url: "/v1.0/slangs/" + slangId,
        headers: {
            "Accept-Language":$.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0,2),
            "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
        },
        success: function (data) {
            findSlangwordList();
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            //TODO: handle other status
            hideLoading();
            if (XMLHttpRequest.status == "401") {
                window.location = "/login";
            } else {
                showConfirmModal("알 수 없는 문제발생!");
            }

        }
    });
}

function submitSlangWord() {
    var text = $("#form-control").val();
    var type = $.trim($("#type-select  option:selected").val());
    if (text != '') {
        var url = "/v1.0/slangs";
        var submitmethod = 'POST';
        switch (String(method).toUpperCase()) {
            case 'ADD':
                url = "/v1.0/slangs";
                submitmethod = 'POST';
                break;
            case 'UPDATE':
                url = "/v1.0/slangs/" + currentSlangId;
                submitmethod = 'PUT';
                break;
        }
        ;

        showLoading();
        $.ajax({
            method: submitmethod,
            url: url,
            headers: {
                "Accept-Language":$.cookie("umsLanguage"),
                "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0,2),
                "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
            },
            data: {
                slang: text,
                type: type
            },
            success: function (data) {
                findSlangwordList();
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                //TODO: handle other status
                hideLoading();
                if (XMLHttpRequest.status == "401") {
                    window.location = "/login";
                } else {
                    showConfirmModal($.i18n.prop("ums.monitor.all.comment.select"));
                }

            }
        });
        hideAddModal();
    }
}

function initLayout(data) {
    $("#prohibition-box").empty();
    $.each(data.results, function (i, item) {
        $("#prohibition-box").append(
            "<div class=\'widget\'>" +
            "<input type=\'text\' class=\'form-control input-sm\' value='" + $.trim(item.slang) + "' disabled=\'disabled\'>" +
            "<div class=\'widget-buttons\'>" +
            "<a name=\'btn-edit\' onclick=\'showAddModal(\"" + item.slangId + "\" ,\"" + item.slang + "\" , \"" + item.type + "\" )\'>" +
            "<span class=\'glyphicon glyphicon-edit\'></span>" +
            "</a>" +
            "<a name=\'btn-del\' onclick=\'deleteSlangWord(\"" + item.slangId + "\")\'>" +
            "<span class=\'glyphicon glyphicon-trash\'></span>" +
            "</a>" +
            "</div>" +
            "</div>"
        );
    });
}

function showAddModal(slangId, slang, type) {
    $("#bs-example-modal-sm").modal('show');
    var text = "";
    var select = "A";
    method = "ADD";
    currentSlangId = -1;
    if (slangId != null && slang != null && type != null) {
        text = slang;
        select = type;
        currentSlangId = slangId;
        method = "UPDATE";
    }
    $("#form-control").val(text);
    $("#type-select").val(select);
    setCharactersNumAlert(ruleStringLength);
}

function hideAddModal() {
    $("#bs-example-modal-sm").modal('hide');
    $("#form-control").val('');
    $("#type-select").val('A');
}

function showConfirmModal(message) {
    $("#bootbox-body").html(message);
    $("#bootbox-confirm").modal('show');
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