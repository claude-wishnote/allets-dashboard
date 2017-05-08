/**
 * Created by pikicast on 17/5/3.
 */
var contentDataTable;
var contentsResult;
var currentPage = 1;


$(document).ready(function () {
    //getContents(0);

    $('#contentViewStartDatePicker').datepicker({
        format: 'yyyy-mm-dd',
    }).on('changeDate', function (ev) {
        //$('#allCommentEndDatePicker').val($('#allCommentStartDatePicker').val());
    });

    $('#contentViewEndDatePicker').datepicker({
        format: 'yyyy-mm-dd',
    }).on('changeDate', function (ev) {
        //$('#allCommentStartDatePicker').val($('#allCommentEndDatePicker').val());
    });

    $('#contentViewStartDatePicker').on(
        'focus', function () {
            $(this).blur();
        }
    );
    $('#contentViewEndDatePicker').on(
        'focus', function () {
            $(this).blur();
        }
    );

    $('#searchButton').on(
        'click', function () {
            getContents(0);
        }
    );

    $('#initSearchButton').on(
        'click', function () {
            $('#contentViewStartDatePicker').val('');
            $('#contentViewEndDatePicker').val('');
            $('#contentViewSearchKeyword').val('');
        }
    );
});

function getContents(offset) {
    blockObj($('div.page-content'));
    var startDate = $('#contentViewStartDatePicker').val();
    var endDate = $('#contentViewEndDatePicker').val();
    var title = $('#contentViewSearchKeyword').val();
    var q = "";
    if(!!title)
    {
        if ($.trim(q) != '') {
            q = q + ',';
        }
        q = q + 'title='+title;
    }
    if(!!startDate)
    {
        if ($.trim(q) != '') {
            q = q + ',';
        }
        q = q + 'startDate='+startDate;
    }
    if(!!endDate)
    {
        if ($.trim(q) != '') {
            q = q + ',';
        }
        // q = q + 'endDate='+endDate;
        var end = new Date(Date.parse(endDate.replace(/-/g, "/")));
        var endDateObj = new Date(end.getTime() + 86400000);
        var endString = endDateObj.getFullYear() + '-' + (endDateObj.getMonth() + 1) + '-' + endDateObj.getDate();
        q = q + "endDate=" + endString;
    }
    console.log(q);
    var param = {
        q: encodeURI(q),
        limit:$('#handledAccountLimit').val(),
        offset:offset
    };
    contentsResult = '';
    $.ajax({
        method: "GET",
        url: "/v1.2/mgContents",
        headers: {
            "Accept-Language":$.cookie("dataLanguage"),
            "X-ALLETS-LANG": $.cookie("dataLanguage").substring(0,2),
            "X-ALLETS-COUNTRY": $.cookie("dataLanguage").substring(3)
        },
        data: param,
        success: function (data, status, jqXHR) {
            if (data.pageImpl.content != undefined && data.pageImpl.content != null) {
                contentsResult = data;
                 for(index in contentsResult.pageImpl.content)
                {
                    var str=contentsResult.pageImpl.content[index].title.replace('\n','<b style="margin-left:-10px;color: transparent">\\n</b>');
                    contentsResult.pageImpl.content[index].title=str;
                }
                currentPage = data.pageImpl.number + 1;
                initPager(
                    {
                        container: '#pager',
                        totalPage: contentsResult.pageImpl.totalPages,
                        currentPage: currentPage,
                        pageButtonStyle: 0,
                        pageButtonNum: 10,
                        buttonCallback: function (pageIndex, newCurrentPage) {
                            getContents((newCurrentPage - 1) * $('#handledAccountLimit').val());
                        }
                    }
                );
            }
            else {
                contentsResult = '';
            }
            refreshDatatable();
            $('div.page-content').unblock();
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            if (XMLHttpRequest.status == "401") {
                window.location = "/login";
            }
            contentsResult = '';
            $('div.page-content').unblock();
        }
    });
}

function refreshDatatable() {
    if (contentDataTable != null)
        contentDataTable.fnDestroy();
    contentDataTable = $('#contentViewTable').dataTable({
        data: contentsResult.pageImpl.content,
        'aoColumns': [
            {'mDataProp': 'udate'},
            {'mDataProp': 'contentsId'},
            {'mDataProp': 'editorName'},
            {'mDataProp': 'uid'},
            {'mDataProp': 'title'},
            {'mDataProp': 'appViewCount'},
            {'mDataProp': 'webViewCount'},
            {'mDataProp': 'likeCount'},
            {'mDataProp': 'shareCount'},
            {'mDataProp': 'bookmarkCount'},
            {'mDataProp': 'commentCount'}
        ],
        'aoColumnDefs': [
            {'aTargets': 0, orderable: false},
            {'aTargets': 1, orderable: false},
            {'aTargets': 2, orderable: false},
            {'aTargets': 3, orderable: false},
            {'aTargets': 4, orderable: false},
            {'aTargets': 5, orderable: false},
            {'aTargets': 6, orderable: false},
            {'aTargets': 7, orderable: false},
            {'aTargets': 8, orderable: false},
            {'aTargets': 9, orderable: false},
            {'aTargets': 10, orderable: false}
        ],
            "aaSorting": [[1, "desc"]],
            'bAutoWidth': false,
            'bInfo': false,
            'bFilter': false,
            'bPaginate': false
            ,
            // 'fnInfoCallback': fnInfoCallback,
            // 'footerCallback': footerCallback,
            'fnCreatedRow': fnCreatedRow,
            scrollY: 500,
            "scrollCollapse": true,
            "paging":false
            // 'fnInitComplete': fnInitComplete
    }
    )
}
// var scrollY = function () {
//     console.log($(window).height()-$('.navbar-inner').offset().top);
//     return $(window).height()-$('.navbar-inner').offset().top;
// };

var fnCreatedRow = function (nRow, aData, iDataIndex) {

}