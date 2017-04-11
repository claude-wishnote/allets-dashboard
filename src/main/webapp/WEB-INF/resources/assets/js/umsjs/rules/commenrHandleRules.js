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
                        '" disabled="disabled">' +
                        '<div class="widget-buttons">' +
                        '<a data-container="body" data-toggle="popover"><span class="glyphicon glyphicon-edit"></span></a>' +
                        '<a data-container="body" data-toggle="popover"><span class="glyphicon glyphicon-trash"></span></a>' +
                        '</div></div>';
                    parentRuleHtml = parentRuleHtml + childHtml;
                }
            }
            parentRuleHtml = parentRuleHtml + '<div class="add-btn-area">' +
                '<a class="btn-add" data-container="body" data-toggle="popover">' +
                '<span class="typcn typcn-plus"></span>' +
                '</a></div>';

            parentRuleHtml = parentRuleHtml + '</div></div></div>'

            totalHtml = totalHtml + parentRuleHtml;
        }
    $('.panel-group').html(totalHtml);
    bindTableButtonEvent();
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

function bindTableButtonEvent() {
    $('.panel-collapse a').each(function (index, html) {
            if ($(this).children()[0].className == "glyphicon glyphicon-edit") {
                var index = $(this).parents('.widget').attr('name');
                var parentIndex = $(this).parents('.panel').attr('name');
                var popoverObj = $(this);
                var selectHtml = "";
                if (tableData[parentIndex].commentHandleRules[index].level == 'A') {
                    selectHtml = '<option value="A" selected = "selected">'+$.i18n.prop("ums.handle.rule.a")+'</option>' +
                        '<option value="B" >'+$.i18n.prop("ums.handle.rule.b")+'</option>' +
                        '<option value="C">'+$.i18n.prop("ums.handle.rule.c")+'</option>';
                }
                else if (tableData[parentIndex].commentHandleRules[index].level == 'B') {
                    selectHtml = '<option value="A">'+$.i18n.prop("ums.handle.rule.a")+'</option>' +
                        '<option value="B" selected = "selected">'+$.i18n.prop("ums.handle.rule.b")+'</option>' +
                        '<option value="C">'+$.i18n.prop("ums.handle.rule.c")+'</option>';
                }
                else if (tableData[parentIndex].commentHandleRules[index].level == 'C') {
                    selectHtml = '<option value="A">'+$.i18n.prop("ums.handle.rule.a")+'</option>' +
                        '<option value="B">'+$.i18n.prop("ums.handle.rule.b")+'</option>' +
                        '<option value="C" selected = "selected">'+$.i18n.prop("ums.handle.rule.c")+'</option>';
                }
                $(this).popover({
                        placement: 'left',
                        trigger: 'click', //触发方式
                        html: true, // 为true的话
                        content: function () {
                            var text = tableData[parentIndex].commentHandleRules[index].text;
                            var leftNum = ruleStringLength - text.length;
                            var string = '';
                            if (leftNum < 0) {
                                string = '<div class="modal-content standard-add">' +
                                    '<div class="modal-body">' +
                                    '<textarea class="form-control danger" rows="3" placeholder="'+$.i18n.prop("ums.handle.rule.popover.placeholder")+'" >' +
                                    text +
                                    '</textarea>' +
                                    '<span class="form-control danger" id="charactersNumAlert">' +
                                    'You should delete ' + (0 - leftNum) + ' characters.' +
                                    '</span>' +
                                    '<select>' +
                                    selectHtml +
                                    '</select>' +
                                    '<div class="modal-footer">' +
                                    '<button type="button" class="btn btn-info" data-dismiss="modal">'+$.i18n.prop("ums.handle.rule.ok")+'</button>' +
                                    '<button type="button" class="btn btn-default" data-dismiss="modal">'+$.i18n.prop("ums.handle.rule.cancel")+'</button>' +
                                    '</div>' +
                                    '</div>';
                            } else {
                                string = '<div class="modal-content standard-add">' +
                                    '<div class="modal-body">' +
                                    '<textarea class="form-control " rows="3" placeholder="'+$.i18n.prop("ums.handle.rule.popover.placeholder")+'" >' +
                                    text +
                                    '</textarea>' +
                                    '<span class="form-control green" id="charactersNumAlert">' +
                                    'You should enter ' + leftNum + ' characters.' +
                                    '</span>' +
                                    '<select>' +
                                    selectHtml +
                                    '</select>' +
                                    '<div class="modal-footer">' +
                                    '<button type="button" class="btn btn-info" data-dismiss="modal">'+$.i18n.prop("ums.handle.rule.ok")+'</button>' +
                                    '<button type="button" class="btn btn-default" data-dismiss="modal">'+$.i18n.prop("ums.handle.rule.cancel")+'</button>' +
                                    '</div>' +
                                    '</div>';
                            }
                            return string;
                        },
                        title: ''
                    }
                );
                $(this).on(
                    'shown.bs.popover', function () {
                        $('.modal-content .btn.btn-info').on('click', function () {
                            var rule = tableData[parentIndex].commentHandleRules[index];
                            var level = $('.modal-content select').val();
                            var text = $('.modal-content textarea').val();
                            if (rule.ruleId == undefined || rule.ruleId == null || $.trim(rule.ruleId) == '') {
                                return;
                            }
                            else if (rule.parentRuleId == undefined || rule.parentRuleId == null || $.trim(rule.parentRuleId) == '') {
                                return;
                            }
                            else if (text == undefined || text == null || $.trim(text) == '') {
                                //$('.modal-content textarea').addClass('danger');
                                return;
                            }
                            else if (level == undefined || level == null || $.trim(level) == '') {
                                return;
                            }
                            var text = $('.modal-content textarea').val();
                            if (text.length > ruleStringLength) {
                                alert("the rule text is too long.");
                                return;
                            }
                            popoverObj.popover('hide');
                            updateRule(rule.ruleId, rule.parentRuleId, text, level);
                        });
                        $('.modal-content .btn.btn-default').on('click', function () {
                            popoverObj.popover('hide');
                        });
                        $('.modal-content textarea').on('input',
                            function () {
                                setCharactersNumAlert(ruleStringLength);
                            }
                        );
                    });
            }
            else if ($(this).children()[0].className == "glyphicon glyphicon-trash") {
                var index = $(this).parents('.widget').attr('name');
                var parentIndex = $(this).parents('.panel').attr('name');
                var popoverObj = $(this);
                popoverObj.popover({
                    placement: 'left',
                    trigger: 'click', //触发方式
                    html: true, // 为true的话
                    content: '<div class=\'layer-btn-center-area\'>' +
                    '<button type="button" class="btn btn-info" data-dismiss="modal">'+$.i18n.prop("ums.handle.rule.ok")+'</button>' +
                    '<button type="button" class="btn btn-default" data-dismiss="modal">'+$.i18n.prop("ums.handle.rule.cancel")+'</button>' +
                    '</div>',
                    title: ''
                });
                popoverObj.on(
                    'shown.bs.popover', function () {
                        $('.layer-btn-center-area .btn.btn-info').on('click', function () {
                            var rule = tableData[parentIndex].commentHandleRules[index];
                            popoverObj.popover('hide');
                            deleteRule(rule.ruleId);
                        });
                        $('.layer-btn-center-area .btn.btn-default').on('click', function () {
                            popoverObj.popover('hide');
                        });
                    });
            }
            else if ($(this).children()[0].className == "typcn typcn-plus") {
                var parentIndex = $(this).parents('.panel').attr('name');
                var popoverObj = $(this);
                $(this).popover({
                    placement: 'left',
                    trigger: 'click', //触发方式
                    html: true, // 为true的话
                    content: function () {
                        var string = '<div class="modal-content standard-add">' +
                            '<div class="modal-body">' +
                            '<textarea class="form-control" rows="3" placeholder="'+$.i18n.prop("ums.handle.rule.popover.placeholder")+'" >' +
                            '</textarea>' +
                            '<span class="form-control green" id="charactersNumAlert">' +
                            'You should delete ' + ruleStringLength + ' characters.' +
                            '</span>' +
                            '<select>' +
                            '<option value="A">'+$.i18n.prop("ums.handle.rule.a")+'</option>' +
                            '<option value="B">'+$.i18n.prop("ums.handle.rule.b")+'</option>' +
                            '<option value="C">'+$.i18n.prop("ums.handle.rule.c")+'</option>' +
                            '</select>' +
                            '<div class="modal-footer">' +
                            '<button type="button" class="btn btn-info" data-dismiss="modal">'+$.i18n.prop("ums.handle.rule.ok")+'</button>' +
                            '<button type="button" class="btn btn-default" data-dismiss="modal">'+$.i18n.prop("ums.handle.rule.cancel")+'</button>' +
                            '</div>' +
                            '</div>';
                        return string;
                    },
                    title: ''
                });
                $(this).on(
                    'shown.bs.popover', function () {
                        $('.modal-content .btn.btn-info').on('click', function () {
                            var rule = tableData[parentIndex];
                            var level = $('.modal-content select').val();
                            var text = $('.modal-content textarea').val();
                            if ($.trim(rule.parentRuleId) != '') {
                                return;
                            }
                            else if (rule.ruleId == undefined || rule.ruleId == null || $.trim(rule.ruleId) == '') {
                                return;
                            }
                            else if (text == undefined || text == null || $.trim(text) == '') {
                                //$('.modal-content textarea').addClass('danger');
                                return;
                            }
                            else if (level == undefined || level == null || $.trim(level) == '') {
                                return;
                            }
                            var text = $('.modal-content textarea').val();
                            if (text.length > ruleStringLength) {
                                alert("the rule text is too long.");
                                return;
                            }
                            popoverObj.popover('hide');
                            addRule(rule.ruleId, text, level);
                        });
                        $('.modal-content .btn.btn-default').on('click', function () {
                            popoverObj.popover('hide');
                        });
                        $('.modal-content textarea').on('input',
                            function () {
                                setCharactersNumAlert(ruleStringLength);
                            }
                        );
                    });
            }
        }
    );

    $('[data-toggle=popover]').on('click', function () {
        $('[data-toggle=popover]').not(this).popover('hide');
    });
    $('.accordion-toggle').on('click', function () {
        $('[data-toggle=popover]').popover('hide');
    });
}

function updateRule(ruleid, parentRuleId, text, level) {
    blockObj($('div.page-content'));
    $.ajax({
        method: 'PUT',
        url: '/v1.0/commentHandleRules/' + ruleid,
        headers: {
            "Accept-Language":$.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0,2),
            "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
        },
        data: {
            parentRuleId: parentRuleId,
            text: text,
            level: level
        },
        success: function (data, status, jqXHR) {
            getTableData();
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            if (XMLHttpRequest.status == '401') {
                window.location = '/login';
            }
        }
    });
}

function deleteRule(ruleId) {
    blockObj($('div.page-content'));
    $.ajax({
        method: 'DELETE',
        url: '/v1.0/commentHandleRules/' + ruleId,
        headers: {
            "Accept-Language":$.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0,2),
            "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
        },
        success: function (data, status, jqXHR) {
            getTableData();
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            if (XMLHttpRequest.status == '401') {
                window.location = '/login';
            }
        }
    });

}

function addRule(parentRuleId, text, level) {
    blockObj($('div.page-content'));
    $.ajax({
        method: 'POST',
        url: '/v1.0/commentHandleRules',
        headers: {
            "Accept-Language":$.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0,2),
            "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
        },
        data: {
            parentRuleId: parentRuleId,
            text: text,
            level: level
        },
        success: function (data, status, jqXHR) {
            getTableData();
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            if (XMLHttpRequest.status == '401') {
                window.location = '/login';
            }
        }
    });
}