/**
 * Created by claude on 2015/10/12.
 */

var tableData;
var ruleStringLength = 100;

$(window).resize(function () {
    $('.page-body').css('height', $(window).height() - 85);
});
$(document).ready(function () {
    $('.page-body').css('height', $(window).height() - 85);
    blockObj($('div.page-content'));

    getTableData();
});


function getTableData() {
    $.ajax({
        method: "GET",
        url: "/v1.0/commentHandleRules",
        headers: {
            "Accept-Language":$.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0,2),
            "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
        },
        success: function (data, status, jqXHR) {
            tableData = data.commentHandleRuleList;
            refreshAllRules();
            $('div.page-content').unblock();
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            //TODO: handle other status
            if (XMLHttpRequest.status == "401") {
                window.location = "/login";
            }
            $('div.page-content').unblock();
        }
    });
}

function refreshAllRules() {
    var totalHtml = '';
    if (tableData != undefined && tableData != null && tableData != '')
        for (var parentRule in tableData) {
            var parentRuleHtml =
                '<div class="panel panel-default" name="' + parentRule + '">' +
                '<div class="panel-heading ">' +
                '<h4 class="panel-title">' +
                '<a class="accordion-toggle" data-toggle="collapse"  href="#childrenRules' +
                parentRule + '">' + tableData[parentRule].text +
                '</a></h4></div>';
            if ($('#childrenRules' + parentRule).attr('class') == 'panel-collapse collapse in' || $('#childrenRules' + parentRule).attr('class') == undefined) {
                parentRuleHtml = parentRuleHtml + '<div id="childrenRules' + parentRule + '" class="panel-collapse collapse in">';
            } else {
                parentRuleHtml = parentRuleHtml + '<div id="childrenRules' + parentRule + '" class="panel-collapse collapse">';
            }
            parentRuleHtml = parentRuleHtml + '<div class="standard-list">';

            if (tableData[parentRule].commentHandleRules.length > 0) {
                for (var childRule in tableData[parentRule].commentHandleRules) {
                    var childHtml = '<div class="widget" name="' +
                        childRule + '">';
                    if (tableData[parentRule].commentHandleRules[childRule].level == "A") {
                        childHtml = childHtml + '<span class="label label-danger grade-tit">A</span>';
                    } else if (tableData[parentRule].commentHandleRules[childRule].level == "B") {
                        childHtml = childHtml + '<span class="label label-warning grade-tit">B</span>';
                    } else if (tableData[parentRule].commentHandleRules[childRule].level == "C") {
                        childHtml = childHtml + '<span class="label label-default grade-tit">C</span>';
                    }
                    childHtml = childHtml +
                        '<input type="text" class="form-control input-sm"   placeholder="' +
                        tableData[parentRule].commentHandleRules[childRule].text +
                        '" disabled="disabled"></div>';
                    parentRuleHtml = parentRuleHtml + childHtml;
                }
            }
            parentRuleHtml = parentRuleHtml + '</div></div></div>'

            totalHtml = totalHtml + parentRuleHtml;
        }
    $('.panel-group').html(totalHtml);
}

function setCharactersNumAlert(ruleStringLength) {
    var text = $('.modal-content textarea').val()
    var leftNum = ruleStringLength - text.length;
    if (leftNum < 0) {
        $('.modal-content textarea').addClass('danger');
        $('#charactersNumAlert').removeClass('green');
        $('#charactersNumAlert').addClass('danger');
        $('#charactersNumAlert').html('You should delete ' + (0 - leftNum) + ' characters.');
    } else {
        $('.modal-content textarea').removeClass('danger');
        $('#charactersNumAlert').removeClass('danger');
        $('#charactersNumAlert').addClass('green');
        $('#charactersNumAlert').html('You can enter ' + leftNum + ' characters.');
    }
}







