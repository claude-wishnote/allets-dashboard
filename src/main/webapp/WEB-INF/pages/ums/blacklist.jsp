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
                        <span data-i18n="ums.account"></span>
                        <small>
                            <i class="fa fa-angle-right"></i>
                            <span data-i18n="ums.account.blacklist"></span>
                        </small>
                    </h1>
                </div>
            </div>
            <!-- /Page Header -->
            <!-- Page Body -->
            <div class="page-body" style="overflow:auto">
                <div id="topSign"></div>
                <!-- search bar-->
                <div class="row searchbar">
                    <div class="col-sm-3">
                        <select id="pageSize">
                            <option value="30">30</option>
                            <option value="50" selected="selected">50</option>
                            <option value="100">100</option>
                        </select>
                    </div>
                    <div class="col-sm-9 text-right">
                        <select id="searchType">
                            <option value="nickName" data-i18n="ums.account.blacklist.nickname"></option>
                            <option value="email" data-i18n="ums.account.blacklist.email"></option>
                            <option value="keyword" data-i18n="ums.account.blacklist.keyword"></option>
                        </select>
                        <span class="input-icon">
                            <input type="text" class="form-control input-sm" id="kewordInput">
                            <i class="glyphicon glyphicon-search blue"></i>
                        </span>
                    </div>
                </div>
                <!-- /search bar-->
                <!-- calendar-->
                <div class="row calendar-bar">
                    <div class="col-sm-12">
                        <div class="calendar-section">
                            <div class="calendar-box">
                                <div class="input-group">
                                    <input class="form-control date-picker" id="startDatePicker" type="text" value="">
                                    <span class="input-group-addon">
                                        <i class="fa fa-calendar"></i>
                                    </span>
                                </div>
                                <span class="icon-sub-txt" data-i18n="ums.account.blacklist.from" style="width:190px;"></span>
                            </div>
                            <div class="calendar-box"  style="margin-right: 40px">
                                <div class="input-group">
                                    <input class="form-control date-picker" id="endDatePicker" type="text" value="">
                                    <span class="input-group-addon">
                                        <i class="fa fa-calendar"></i>
                                    </span>
                                </div>
                                <span class="icon-sub-txt" data-i18n="ums.account.blacklist.to" style="width:180px;"></span>
                            </div>
                        </div>
                        <div class="calendar-btn">
                            <a href="javascript:void(0);" id="searchButton" class="btn btn-info btn-sm" data-i18n="ums.account.blacklist.search"></a>
                            <a href="javascript:void(0);" id="resetButton" class="btn btn-info btn-sm" data-i18n="ums.account.blacklist.reset"></a>
                            <select id="periodDays">
                                <option value="1" data-i18n="ums.account.blacklist.1day"></option>
                                <option value="7" data-i18n="ums.account.blacklist.7day"></option>
                                <option value="30" data-i18n="ums.account.blacklist.1month"></option>
                            </select>
                        </div>
                    </div>
                </div>
                <!-- /calendar-->
                <div class="row">
                    <div class="col-sm-12">
                        <div class="widget">
                            <div class="widget-body">
                                <!-- data table -->
                                <table class="table table-bordered text-center table-hover" id="blackUserTable">
                                    <colgroup>
                                        <col width="5%" />
                                        <col width="10%" />
                                        <col width="15%" />
                                        <col width="10%" />
                                        <col width="10%" />
                                        <col width="10%" />
                                        <col width="10%" />
                                        <col width="10%" />
                                        <col width="10%" />
                                        <col width="10%" />
                                    </colgroup>
                                    <thead>
                                    <tr>
                                        <th>No.</th>
                                        <th>Nickname</th>
                                        <th>Join date</th>
                                        <th>Account  report</th>
                                        <th>Deleted  comments</th>
                                        <th>Subscriber  count</th>
                                        <th>e-mail</th>
                                        <th>User id</th>
                                        <th>Monitor id</th>
                                        <th>Reset</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <!--&lt;!&ndash;loop&ndash;&gt;
                                    <tr>
                                        <td>97</td>
                                        <td>너무나그리워서</td>
                                        <td>2015-03-05</td>
                                        <td>0</td>
                                        <td>7</td>
                                        <td>3</td>
                                        <td>doplaying@naver.com</td>
                                        <td>14690906</td>
                                        <td>Danny</td>
                                        <td>
                                            <a href="javascript:void(0);" class="btn btn-blue btn-sm">등록 해제</a>
                                        </td>
                                    </tr>
                                    &lt;!&ndash;/loop&ndash;&gt;
                                    <tr>
                                        <td>97</td>
                                        <td>너무나그리워서</td>
                                        <td>2015-03-05</td>
                                        <td>0</td>
                                        <td>7</td>
                                        <td>3</td>
                                        <td>doplaying@naver.com</td>
                                        <td>14690906</td>
                                        <td>Danny</td>
                                        <td>
                                            <a href="javascript:void(0);" class="btn btn-blue btn-sm">등록 해제</a>
                                        </td>
                                    </tr>-->
                                    </tbody>
                                </table>
                                <!-- /data table -->
                            </div>
                            <!-- paging -->
                            <div class="text-center mgt10">
                                <ul class="pagination" id="pager">
                                </ul>
                            </div>
                            <div id="bottomSign"></div>
                            <!-- /paging -->
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
<script src="/js/datatable/jquery.dataTables.min.js"></script>

<!--Beyond Scripts-->
<script src="/js/beyond.min.js"></script>

<!--Page Related Scripts-->
<!--Jquery Select2-->
<script src="/js/select2/select2.js"></script>
<!--i18n-->
<script src="/js/jquery.i18n.properties.js" charset="UTF-8"></script>
<script src="/js/umsjs/i18n.js"></script>

<!--Bootstrap Date Picker-->
<script src="/js/datetime/bootstrap-datepicker.js"></script>
<script src="/js/umsjs/jumper/jumper.js" charset="UTF-8"></script>
<script src="/js/jquery.cookie.min.js"></script>
<script src="/js/umsjs/pager/pager.js" charset="UTF-8"></script>
<script src="/js/umsjs/jumper/jumper.js" charset="UTF-8"></script>
<script src="/js/umsjs/account/blacklist.js" charset="UTF-8"></script>