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
                        <span data-i18n="ums.authority"></span>
                        <small>
                            <i class="fa fa-angle-right"></i>
                            <span data-i18n="ums.authority.management"></span>
                        </small>
                    </h1>
                </div>
            </div>
            <!-- /Page Header -->
            <!-- Page Body -->
            <div class="page-body" style="overflow:auto">
                <!-- dialog -->
                <div class="modal-dialog modal-sm mgt100">
                    <!-- content -->
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title" id="mySmallModalLabel">Change Password</h4>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <input type="password" class="form-control input-sm"   placeholder="Current password" id="input-currentpassword">
                            </div>
                            <div class="form-group">
                                <input type="password" class="form-control input-sm"   placeholder="New password" id="input-password">
                                <input type="password" class="form-control input-sm mgt5"   placeholder="Re-type new password" id="input-repassword">
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-info" id="btn-save">Change Password</button>
                            <button type="button" class="btn btn-default" id="btn-cancel">Cancel</button>
                        </div>
                    </div>
                    <!-- /content -->
                </div>
                <!-- /dialog -->
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
<script src="/js/jquery.cookie.min.js"></script>
<script src="/js/umsjs/jquery.md5.js"></script>
<script src="/js/jquery.blockUI.js"></script>

<!--Beyond Scripts-->
<script src="/js/beyond.min.js"></script>
<!--i18n-->
<script src="/js/jquery.i18n.properties.js" charset=“UTF-8”></script>
<script src="/js/umsjs/i18n.js"></script>

<!--Page Related Scripts-->
<script src="/js/toastr/toastr.js"></script>
<script src="/js/umsjs/jumper/jumper.js" charset="UTF-8"></script>
<script src="/js/umsjs/authority/authority2.js" charset="UTF-8"></script>

