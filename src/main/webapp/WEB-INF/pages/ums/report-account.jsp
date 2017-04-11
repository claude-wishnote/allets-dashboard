<%@ page language="java" contentType="text/html; charset=utf-8" %>

<!-- Loading Container -->
<div class="loading-container">
    <div class="loader"></div>
</div>
<!--  /Loading Container -->
<!-- Navbar -->
<div class="navbar">
    <div class="navbar-inner">
        <div class="navbar-container">
            <!-- Navbar Barnd -->
            <div class="navbar-header pull-left">
                <a href="##" class="navbar-brand">
                    <small>
                        Allets UMS
                    </small>
                </a>
            </div>
            <!-- /Navbar Barnd -->
            <!-- Sidebar Collapse -->
            <div class="sidebar-collapse" id="sidebar-collapse">
                <i class="collapse-icon fa fa-bars"></i>
            </div>
            <div class="navbar-header pull-right" style="margin-right: 10px;margin-top: 5px;" >
                <select id="languageSelector">
                    <option value="en-US" data-i18n="common.language.en-US">en-US</option>
                    <option value="ko-KR" data-i18n="common.language.ko-KR">ko-KR</option>
                    <option value="zh-CN" data-i18n="common.language.zh-CN">zh-CN</option>
                    <option value="zh-TW" data-i18n="common.language.zh-TW">zh-TW</option>
                    <option value="ja-JP" data-i18n="common.language.ja-JP">ja-JP</option>
                </select>
            </div>
            <!-- /Sidebar Collapse -->
        </div>
    </div>
</div>
<!-- /Navbar -->
<!-- Main Container -->
<div class="main-container container-fluid">
    <!-- Page Container -->
    <div class="page-container">
        <!-- Page Sidebar -->
        <div class="page-sidebar" id="sidebar">
            <!-- Sidebar Menu -->
            <ul id="layoutMenu" class="nav sidebar-menu">
                <!--menu list-->
                <li id="itemRule"  >
                    <a href="##" class="menu-dropdown">
                        <i class="menu-icon fa fa-desktop"></i>
                        <span class="menu-text" data-i18n="ums.handle.rule.monitoring.criterion"></span>
                        <i class="menu-expand"></i>
                    </a>
                    <!--sub menu-->
                    <ul class="submenu" id="submenuItemRule" >
                        <li id="subitemRule">
                            <a href="##">
                                <span class="menu-text" data-i18n="ums.handle.rule.monitoring.review.grounds"></span>
                            </a>
                        </li>
                    </ul>
                    <!--//sub menu-->
                </li>
                <!--//menu list-->
                <li id="itemReported"><!--as-is-->
                    <a href="##" class="menu-dropdown">
                        <i class="menu-icon fa fa-pencil-square-o"></i>
                        <span class="menu-text" data-i18n="ums.report"></span>

                        <i class="menu-expand"></i>
                    </a>
                    <!-- style="display: block;" add this,animation will be ok -->
                    <ul class="submenu" id="submenuItemReported" >
                        <li id="subitemReportedComments">
                            <a href="##">
                                <span class="menu-text" data-i18n="ums.report.comment"></span>
                            </a>
                        </li>
                        <li id="subitemReportedUser">
                            <a href="##">
                                <span class="menu-text" data-i18n="ums.report.account"></span>
                            </a>
                        </li>
                    </ul>
                </li>
                <li id="itemHandled">
                    <a href="##" class="menu-dropdown">
                        <i class="menu-icon glyphicon glyphicon-tasks"></i>
                        <span class="menu-text" data-i18n="ums.handle.history"></span>

                        <i class="menu-expand"></i>
                    </a>

                    <ul class="submenu" id="submenuItemHandled">
                        <li id="subitemHandledComments">
                            <a href="##">
                                <span class="menu-text" data-i18n="ums.handle.history.comment"></span>
                            </a>
                        </li>
                        <li id="subitemHandledUsers">
                            <a href="##">
                                <span class="menu-text" data-i18n="ums.handle.history.account"></span>
                            </a>
                        </li>
                    </ul>
                </li>
                <li id="itemCommentsMonitor">
                    <a href="##" class="menu-dropdown">
                        <i class="menu-icon fa fa-bar-chart-o"></i>
                        <span class="menu-text" data-i18n="ums.monitor"></span>

                        <i class="menu-expand"></i>
                    </a>

                    <ul class="submenu" id="submenuItemCommentsMonitor">
                        <li id="subitemCommentsMonitor">
                            <a href="##">
                                <span class="menu-text" data-i18n="ums.monitor.all.comment"></span>
                            </a>
                        </li>
                    </ul>
                </li>
                <li  id="itemAccountManagement">
                    <a href="##" class="menu-dropdown">
                        <i class="menu-icon fa fa-pencil-square-o"></i>
                        <span class="menu-text" data-i18n="ums.account"></span>

                        <i class="menu-expand"></i>
                    </a>

                    <ul class="submenu" id="submenuItemAccountManagement">
                        <li id="subitemAccountManagement">
                            <a href="##">
                                <span class="menu-text" data-i18n="ums.account.management"></span>
                            </a>
                        </li>
                        <li id="subitemBlackListManagement">
                            <a href="##">
                                <span class="menu-text" data-i18n="ums.account.blacklist"></span>
                            </a>
                        </li>
                        <li id="subitemAccountCSManagement">
                            <a href="##">
                                <span class="menu-text" data-i18n="ums.account.CSmanagement"></span>
                            </a>
                        </li>
                    </ul>
                </li>
                <li id="itemRightsManagement">
                    <a href="##" class="menu-dropdown">
                        <i class="menu-icon glyphicon glyphicon-link"></i>
                        <span class="menu-text" data-i18n="ums.authority"></span>

                        <i class="menu-expand"></i>
                    </a>

                    <ul class="submenu" id="submenuItemRightsManagement">
                        <li id="subitemItemRightsManagement">
                            <a href="##">
                                <span class="menu-text" data-i18n="ums.authority.management"></span>
                            </a>
                        </li>
                        <li id="subitemIllegalWordManagement">
                            <a href="##" >
                                <span class="menu-text" data-i18n="ums.authority.slang"></span>
                            </a>
                        </li>
                        <li id="subitemMonitorStatistics">
                            <a href="##">
                                <span class="menu-text" data-i18n="ums.authority.statistics"></span>
                            </a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
        <!-- /Page Sidebar -->
        <!-- Page Content -->
        <div class="page-content">
            <!-- Page Header -->
            <div class="page-header position-relative">
                <div class="header-title">
                    <h1>
                        <span data-i18n="ums.report"></span>
                        <small>
                            <i class="fa fa-angle-right"></i>
                            <span data-i18n="ums.report.account"></span>
                        </small>
                    </h1>
                </div>
            </div>
            <!-- /Page Header -->
            <!-- Page Body -->
            <div class="page-body"  style="overflow:auto">
                <div id="topSign"></div>
                <!-- search bar-->
                <div class="row searchbar">
                    <div class="col-sm-12">
                        <%--<select style="display: none;" id="handledAccountOrder">--%>
                        <%--<option value="regTime">Order By Register Time</option>--%>
                        <%--<option value="reportCount" selected = "selected">Order By Reported Count</option>--%>
                        <%--</select>--%>
                        <select style="width: 7%;" id="pageSize">
                            <option value="30">30</option>
                            <option value="50" selected="selected">50</option>
                            <option value="100">100</option>
                        </select>
                        <span class="label label-danger" style="width: 15%;" id="orderText">Order By Register Time Desc</span>
                        <select style="width: 12%;" id="searchBy">
                            <option value="nickName" data-i18n="ums.report.account.nickname"></option>
                            <option value="email" data-i18n="ums.report.account.email"></option>
                            <option value="keyword" data-i18n="ums.report.account.keyword"></option>
                        </select>
                        <select style="width: 15%;" id="searchSource">
                            <%--<option value="els" data-i18n="ums.account.management.fuzzy"></option>--%>
                            <option value="mysql" data-i18n="ums.account.management.exact"></option>
                        </select>
                        <i style="width: 5%;" class="btn glyphicon glyphicon-refresh blue" id="refreshButton"></i>
                    </div>
                    <br>
                    <div class="col-sm-12">
                        <span style="width:55%;" class="input-icon">
                            <input style="display: block;height: 30px;"  type="text" id="userSearchKeyword" class="form-control input-smdrop down-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <ul id="searchResultDropDown" class="dropdown-menu dropdown-defult col-lg-12">
                                <li id="searchTip" value="1"><a><p data-i18n="ums.report.comment.searchfield.click">click</p> <i class="btn btn-sm glyphicon glyphicon-search blue" ></i> <p data-i18n="ums.report.comment.searchfield.placeholder">button or press "ENTER" to search users.</p> </a></li>
                            </ul>
                            <input type="text" class="form-control input-sm" id="keyword">
                            <i class="btn btn-sm glyphicon glyphicon-search blue" id="searchButton"></i>
                        </span>
                        <button style="width: 8%;"  href="javascript:void(0);" id="initSearchButton" class="btn btn-info btn-sm" data-i18n="ums.handle.history.comment.reset"></button>
                    </div>
                </div>
                <!-- /search bar-->
                <div class="row">
                    <div class="col-sm-12">
                        <div class="widget">
                            <div class="widget-body">
                                <!-- data table -->
                                <table id="reportedUserTable" class="table table-hover table-striped table-bordered text-center">
                                    <colgroup>
                                        <col width="12%" />
                                        <col width="10%" />
                                        <col width="10%" />
                                        <col width="13%" />
                                        <col width="10%" />
                                        <col width="15%" />
                                        <col width="15%" />
                                        <col width="10%" />
                                    </colgroup>
                                    <thead>
                                    <tr>
                                        <th id="regTime"><a href="##">Register time <b class="caret"></b></a></th>
                                        <th id="reportCount"><a href="##">Report count <b class="caret"></b></a></th>
                                        <th><a href="##">Deleted  comment <b class="caret"></b></a></th>
                                        <th>Nickname</th>
                                        <th><a href="##">Report  type <b class="caret"></b></a></th>
                                        <th>Subscriber</th>
                                        <th>User id</th>
                                        <th>Condition</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <!--loop-->

                                    </tbody>
                                </table>
                                <!-- /data table -->
                            </div>
                            <!-- button -->
                            <div class="text-left mgt10">
                                <!--<a href="javascript:void(0);" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-ok"></span>선택 패스</a>-->
                                <!--<a href="javascript:void(0);" class="btn btn-info btn-sm"><span class="glyphicon glyphicon-minus"></span>선택 블라인드</a>-->
                                <!--<a href="javascript:void(0);" class="btn btn-danger btn-sm"><span class="glyphicon glyphicon-remove posi-t2"></span>선택 삭제</a>-->
                            </div>
                            <!-- /button -->
                            <!-- paging -->
                            <div class="text-center mgt10">
                                <ul class="pagination" id="pager">
                                    <!--<li class="disabled"><a href="##">«</a></li>-->
                                    <!--<li class="active"><a href="##">1 <span class="sr-only">(current)</span></a></li>-->
                                    <!--<li><a href="##">2</a></li>-->
                                    <!--<li><a href="##">3</a></li>-->
                                    <!--<li><a href="##">4</a></li>-->
                                    <!--<li><a href="##">5</a></li>-->
                                    <!--<li><a href="##">»</a></li>-->
                                </ul>
                            </div>
                            <!-- /paging -->
                            <div id="bottomSign"></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="right-side-bar">
                <i class="btn btn-lg glyphicon glyphicon-chevron-up blue"></i>
                <i class="btn btn-lg glyphicon glyphicon-chevron-down blue"></i>
            </div>
            <!-- /Page Body -->
        </div>
        <!-- /Page Content -->
    </div>
    <!-- /Page Container -->
    <!-- Main Container -->
</div>

<!--Basic Scripts-->
<script src="/js/jquery.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/slimscroll/jquery.slimscroll.min.js"></script>
<script src="/js/jquery.blockUI.js"></script>
<script src="/js/jquery.cookie.min.js"></script>

<!--Beyond Scripts-->
<script src="/js/beyond.min.js"></script>

<!--Page Related Scripts-->
<script src="/js/toastr/toastr.js"></script>


<script src="/js/datatable/jquery.dataTables.min.js"></script>
<script src="/js/datatable/ZeroClipboard.js"></script>
<!--<script src="/js/datatable/dataTables.tableTools.min.js"></script>-->
<script src="/js/datatable/dataTables.bootstrap.min.js"></script>
<!--i18n-->
<script src="/js/jquery.i18n.properties.js" charset=“UTF-8”></script>
<script src="/js/umsjs/i18n.js"></script>


<script src="/js/umsjs/account/reportedAccount.js" charset="UTF-8"></script>
<script src="/js/umsjs/pager/pager.js" charset="UTF-8"></script>
<script src="/js/umsjs/jumper/jumper.js" charset=“UTF-8”></script>

<!--  /Body -->