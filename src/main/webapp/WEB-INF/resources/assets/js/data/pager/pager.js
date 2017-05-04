/**
 * Created by claude on 2015/8/27.
 */
function initPager(params) {
    var html = initPages(params.totalPage, params.currentPage, params.buttonCallback, params.container, params.pageButtonNum, params.pageButtonStyle);
    if (params.container != undefined && params.container != null && params.container != '') {
        $(params.container).html(html);
    }
}

function initPages(totalPage, currentPage, buttonCallback, container, pageButtonNum, pageButtonStyle) {
    var perPageHtml = initPrePage(totalPage, currentPage, buttonCallback, container);
    var normalPageHtml = initNormalPage(totalPage, currentPage, buttonCallback, container, pageButtonNum, pageButtonStyle);
    var nextPageHtml = initNextPage(totalPage, currentPage, buttonCallback, container);
    return perPageHtml + normalPageHtml + nextPageHtml;
}
function buttonclick(pageIndex, currentPage, totalPage, buttonCallback, container) {
    var newCurrentPage;
    if (pageIndex == 'pre'||totalPage==0) {
        if (currentPage == 1)
            return;
        else newCurrentPage = currentPage - 1;
    }
    else if (pageIndex == 'next'||totalPage==0) {
        if (currentPage == totalPage)
            return;
        else newCurrentPage = currentPage + 1;
    }
    else {
        newCurrentPage = pageIndex;
    }

    $('#pageButton' + currentPage).attr('class', 'btn btn-sm');
    $('#pageButton' + newCurrentPage).attr('class', 'btn btn-sm btn-blue');
    //console.log(container);
    $(container).children().attr('disabled', 'disabled');
    buttonCallback(pageIndex, newCurrentPage);
}
function initPrePage(totalPage, currentPage, buttonCallback, container) {
    return '<li onclick="buttonclick(\'pre\',' + currentPage + ',' + totalPage + ',' + buttonCallback + ',\'' + container + '\')"class="btn btn-sm">\<\<</li>';
}
function initNextPage(totalPage, currentPage, buttonCallback, container) {
    return '<li onclick="buttonclick(\'next\',' + currentPage + ',' + totalPage + ',' + buttonCallback + ',\'' + container + '\')"class="btn btn-sm">\>\></li>';
}

function initNormalPage(totalPage, currentPage, buttonCallback, container, pageButtonNum, pageButtonStyle) {
    if (totalPage == undefined||totalPage==0) {
        totalPage = 1;
    }
    var pagesHtml = '';
    if (pageButtonNum == undefined) {
        pageButtonNum = 10;
    } else if (pageButtonNum < 3) {
        pageButtonNum = 3;
    }
    /*
     pageButtonStyle
     0 line style
     1 swing style
     */
    if (pageButtonStyle == 0) {
        if (totalPage <= pageButtonNum) {
            for (var i = 1; i <= totalPage; i++) {
                if (i == currentPage) {
                    pagesHtml = pagesHtml + '<li class="btn btn-sm btn-blue" id="pageButton' + i + '"  value="' + i + '">' + i + '</li>';
                } else {
                    pagesHtml = pagesHtml + '<li class="btn btn-sm" id="pageButton' + i + '" onclick="buttonclick(' + i + ',' + currentPage + ',' + totalPage + ',' + buttonCallback + ',\'' + container + '\')" value="' + i + '">' + i + '</li>';
                }
            }
        } else {
            for (var i = 1; i <= pageButtonNum; i++) {
                if(currentPage<=(pageButtonNum / 2))
                {
                    if (i == currentPage) {
                        pagesHtml = pagesHtml + '<li class="btn btn-sm btn-blue" id="pageButton' + i + '"  value="' + i + '">' + i + '</li>';
                    } else {
                        pagesHtml = pagesHtml + '<li class="btn btn-sm" id="pageButton' + i + '" onclick="buttonclick(' + i + ',' + currentPage + ',' + totalPage + ',' + buttonCallback + ',\'' + container + '\')" value="' + i + '">' + i + '</li>';
                    }
                }
                else if(currentPage>(pageButtonNum / 2)&&currentPage<=(totalPage-(pageButtonNum / 2))){
                    if (i == Math.ceil(pageButtonNum / 2)) {
                        pagesHtml = pagesHtml + '<li class="btn btn-sm btn-blue" id="pageButton' + currentPage + '"  value="' + currentPage + '">' + currentPage + '</li>';
                    }
                    else  {
                        var page = currentPage -  (Math.ceil(pageButtonNum / 2)-i);
                        pagesHtml = pagesHtml + '<li class="btn btn-sm" id="pageButton' + page + '" onclick="buttonclick(' + page + ',' + page + ',' + totalPage + ',' + buttonCallback + ',\'' + container + '\')" value="' + page + '">' + page + '</li>';
                    }
                }else if(currentPage>(totalPage-(pageButtonNum / 2))){
                    var page =  totalPage-pageButtonNum+i;
                    if (page == currentPage) {
                        pagesHtml = pagesHtml + '<li class="btn btn-sm btn-blue" id="pageButton' + page + '"  value="' + page + '">' + page + '</li>';
                    } else {
                        pagesHtml = pagesHtml + '<li class="btn btn-sm" id="pageButton' + page + '" onclick="buttonclick(' + page + ',' + currentPage + ',' + totalPage + ',' + buttonCallback + ',\'' + container + '\')" value="' +page + '">' + page + '</li>';
                    }
                }

            }
        }
    }
    //swing style (default stylr)
    else {
        if (totalPage <= pageButtonNum + 2) {
            for (var i = 1; i <= totalPage; i++) {
                if (i == currentPage) {
                    pagesHtml = pagesHtml + '<li class="btn btn-sm btn-blue" id="pageButton' + i + '"  value="' + i + '">' + i + '</li>';
                } else {
                    pagesHtml = pagesHtml + '<li class="btn btn-sm" id="pageButton' + i + '" onclick="buttonclick(' + i + ',' + currentPage + ',' + totalPage + ',' + buttonCallback + ',\'' + container + '\')" value="' + i + '">' + i + '</li>';
                }
            }
        }
        else if (totalPage == pageButtonNum + 3) {
            for (var i = 1; i <= totalPage; i++) {
                if (currentPage <= parseInt(totalPage / 2)) {
                    if (i == totalPage - 1) {
                        pagesHtml = pagesHtml + '......';
                    } else {
                        if (i == currentPage) {
                            pagesHtml = pagesHtml + '<li class="btn btn-sm btn-blue" id="pageButton' + i + '"  value="' + i + '">' + i + '</li>';
                        } else {
                            pagesHtml = pagesHtml + '<li class="btn btn-sm" id="pageButton' + i + '" onclick="buttonclick(' + i + ',' + currentPage + ',' + totalPage + ',' + buttonCallback + ',\'' + container + '\')" value="' + i + '">' + i + '</li>';
                        }
                    }
                } else {
                    if (i == 2) {
                        pagesHtml = pagesHtml + '......';
                    } else {
                        if (i == currentPage) {
                            pagesHtml = pagesHtml + '<li class="btn btn-sm btn-blue" id="pageButton' + i + '"  value="' + i + '">' + i + '</li>';
                        } else {
                            pagesHtml = pagesHtml + '<li class="btn btn-sm" id="pageButton' + i + '" onclick="buttonclick(' + i + ',' + currentPage + ',' + totalPage + ',' + buttonCallback + ',\'' + container + '\')" value="' + i + '">' + i + '</li>';
                        }
                    }
                }
            }
        }
        else if (totalPage > pageButtonNum + 3) {
            for (var i = 1; i <= totalPage; i++) {
                if (currentPage <= Math.ceil((pageButtonNum) / 2) + 2) {
                    if (i <= pageButtonNum + 2) {
                        if (i == currentPage) {
                            pagesHtml = pagesHtml + '<li class="btn btn-sm btn-blue" id="pageButton' + i + '"  value="' + i + '">' + i + '</li>';
                        } else {
                            pagesHtml = pagesHtml + '<li class="btn btn-sm" id="pageButton' + i + '" onclick="buttonclick(' + i + ',' + currentPage + ',' + totalPage + ',' + buttonCallback + ',\'' + container + '\')" value="' + i + '">' + i + '</li>';
                        }
                    }
                    else if (i == totalPage - 1) {
                        pagesHtml = pagesHtml + '......';
                    } else if (i == totalPage) {
                        pagesHtml = pagesHtml + '<li class="btn btn-sm" id="pageButton' + i + '" onclick="buttonclick(' + i + ',' + currentPage + ',' + totalPage + ',' + buttonCallback + ',\'' + container + '\')" value="' + i + '">' + i + '</li>';
                    }
                }
                else if (currentPage > (Math.ceil((pageButtonNum) / 2) + 2) && currentPage <= totalPage - (Math.ceil((pageButtonNum) / 2) + 2)) {
                    if (i > currentPage - (Math.ceil(pageButtonNum) / 2) && i <= currentPage + (Math.ceil(pageButtonNum) / 2)) {
                        if (i == currentPage) {
                            pagesHtml = pagesHtml + '<li class="btn btn-sm btn-blue" id="pageButton' + i + '"  value="' + i + '">' + i + '</li>';
                        } else {
                            pagesHtml = pagesHtml + '<li class="btn btn-sm" id="pageButton' + i + '" onclick="buttonclick(' + i + ',' + currentPage + ',' + totalPage + ',' + buttonCallback + ',\'' + container + '\')" value="' + i + '">' + i + '</li>';
                        }
                    } else if (i == totalPage - 1) {
                        pagesHtml = pagesHtml + '......';
                    } else if (i == totalPage) {
                        pagesHtml = pagesHtml + '<li class="btn btn-sm" id="pageButton' + i + '" onclick="buttonclick(' + i + ',' + currentPage + ',' + totalPage + ',' + buttonCallback + ',\'' + container + '\')" value="' + i + '">' + i + '</li>';
                    } else if (i == 2) {
                        pagesHtml = pagesHtml + '......';
                    } else if (i == 1) {
                        pagesHtml = pagesHtml + '<li class="btn btn-sm" id="pageButton' + i + '" onclick="buttonclick(' + i + ',' + currentPage + ',' + totalPage + ',' + buttonCallback + ',\'' + container + '\')" value="' + i + '">' + i + '</li>';
                    }
                }
                else if (currentPage > totalPage - (Math.ceil((pageButtonNum) / 2) + 2)) {
                    if (i > (totalPage - (pageButtonNum + 2))) {
                        if (i == currentPage) {
                            pagesHtml = pagesHtml + '<li class="btn btn-sm btn-blue" id="pageButton' + i + '"  value="' + i + '">' + i + '</li>';
                        } else {
                            pagesHtml = pagesHtml + '<li class="btn btn-sm" id="pageButton' + i + '" onclick="buttonclick(' + i + ',' + currentPage + ',' + totalPage + ',' + buttonCallback + ',\'' + container + '\')" value="' + i + '">' + i + '</li>';
                        }
                    } else if (i == 2) {
                        pagesHtml = pagesHtml + '......';
                    } else if (i == 1) {
                        pagesHtml = pagesHtml + '<li class="btn btn-sm" id="pageButton' + i + '" onclick="buttonclick(' + i + ',' + currentPage + ',' + totalPage + ',' + buttonCallback + ',\'' + container + '\')" value="' + i + '">' + i + '</li>';
                    }
                }
            }
        }
    }
    return pagesHtml;
}