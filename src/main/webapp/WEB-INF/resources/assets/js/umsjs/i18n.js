var languages = ["zh-TW"
     ,"en-US"
     ,"ko-KR"
    //,"zh-CN"
    //,"ja-JP"
];

var mainLanguage = "ko-KR";
$(document).ready(function () {
    //notSupportI18n();
    supportI18n();
});
function supportI18n() {
    $('[value=zh-CN]').hide();
    $('[value=zh-TW]').hide();
    $('[value=ja-JP]').hide();
    $('[value=en-US]').hide();
    $('[value=ko-KR]').hide();
    for (var index in languages) {
        $('[value=' + languages[index] + ']').show();
    }
    var lang = navigator.language;
    if ($.cookie("umsLanguage")) {
        lang = $.cookie("umsLanguage");
    } else {
        $.cookie('umsLanguage', lang);
    }
    if (languages.indexOf($.cookie("umsLanguage")) == -1) {
        lang = mainLanguage;
        $.cookie('umsLanguage', lang);
    }

    $('#languageSelector').val($.cookie('umsLanguage'));
    console.log($.cookie("umsLanguage"));
    $('#languageSelector').on('change', function (e) {
        console.log($('#languageSelector').val());
        $.cookie("umsLanguage", $('#languageSelector').val());
        window.location = $.cookie('umslocation');
    });
    loadProperties('strings', '/i18n/', lang);
}
function notSupportI18n() {
    $('#languageSelector').hide();
    $.cookie('umsLanguage', mainLanguage);
    loadProperties('strings', '/i18n/', mainLanguage);
}
function loadProperties(name, path, lang) {
    jQuery.i18n.properties({
        name: name,
        path: path,
        mode: 'map',
        checkAvailableLanguages: true,
        language: lang,
        callback: function () {
            $("[data-i18n]").each(function () {
                var elem = $(this),
                    localizedValue = jQuery.i18n.map[elem.data("i18n")];
                //if (elem.is("input[type=text]") || elem.is("input[type=password]") || elem.is("input[type=email]")) {
                //    elem.attr("placeholder", localizedValue);
                //} else if (elem.is("input[type=button]") || elem.is("input[type=submit]")) {
                //    elem.attr("value", localizedValue);
                //} else {
                elem.text(localizedValue);
                //}
            });
        }
    });
}