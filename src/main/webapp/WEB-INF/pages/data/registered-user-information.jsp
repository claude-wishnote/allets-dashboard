<%--
  Created by IntelliJ IDEA.
  User: pikicast
  Date: 17/4/27
  Time: 10:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" %>
<!-- Loading Container -->
<div class="loading-container">
    <div class="loader"></div>
</div>
<!-- /Loading Container -->
<!-- Navbar -->
<div class="navbar">
    <div class="navbar-inner">
        <div class="navbar-container">
            <!-- Navbar Barnd -->
            <div class="navbar-header pull-left">
                <a href="##" class="navbar-brand">
                    <small>
                        Allets DATA
                    </small>
                </a>
            </div>
            <!-- /Navbar Barnd -->
            <!-- Sidebar Collapse -->
            <div class="sidebar-collapse" id="sidebar-collapse">
                <i class="collapse-icon fa fa-bars"></i>
            </div>
            <%--<div class="navbar-header pull-right" style="margin-right: 10px;margin-top: 5px;" >--%>
                <%--<select id="languageSelector">--%>
                    <%--<option value="en-US" data-i18n="common.language.en-US">en-US</option>--%>
                    <%--<option value="ko-KR" data-i18n="common.language.ko-KR">ko-KR</option>--%>
                    <%--<option value="zh-CN" data-i18n="common.language.zh-CN">zh-CN</option>--%>
                    <%--<option value="zh-TW" data-i18n="common.language.zh-TW">zh-TW</option>--%>
                    <%--<option value="ja-JP" data-i18n="common.language.ja-JP">ja-JP</option>--%>
                <%--</select>--%>
            <%--</div>--%>
            <!-- /Sidebar Collapse -->
        </div>
    </div>
</div>
<div class="main-container container-fluid">
    <div class="page-container">
        <div class="page-sidebar" id="sidebar">
            <!-- Sidebar Menu -->
            <ul id="layoutMenu" class="nav sidebar-menu">
                <!--menu list-->
                <li id="itemContentView">
                    <a href="##" class="menu-dropdown">
                        <i class="menu-icon fa fa-desktop"></i>
                        <span class="menu-text" data-i18n="data.content.view">Content View</span>
                        <i class="menu-expand"></i>
                    </a>
                    <!--sub menu-->
                    <%--<ul class="submenu" id="submenuContentView">--%>
                    <%--<li id="subitemContentView">--%>
                    <%--<a href="##">--%>
                    <%--<span class="menu-text" data-i18n="data.content.view"></span>--%>
                    <%--</a>--%>
                    <%--</li>--%>
                    <%--</ul>--%>
                    <!--//sub menu-->
                </li>
                <!--//menu list-->
                <li id="itemEditorView"><!--as-is-->
                    <a href="##" class="menu-dropdown">
                        <i class="menu-icon fa fa-pencil-square-o"></i>
                        <span class="menu-text" data-i18n="data.editor.view">Editor View</span>

                        <i class="menu-expand"></i>
                    </a>
                    <!-- style="display: block;" add this,animation will be ok -->
                    <%--<ul class="submenu" id="submenuItemReported">--%>
                    <%--<li id="subitemReportedComments">--%>
                    <%--<a href="##">--%>
                    <%--<span class="menu-text" data-i18n="data.editor.view"></span>--%>
                    <%--</a>--%>
                    <%--</li>--%>
                    <%--<li id="subitemReportedUser">--%>
                    <%--<a href="##">--%>
                    <%--<span class="menu-text" data-i18n="ums.report.account"></span>--%>
                    <%--</a>--%>
                    <%--</li>--%>
                    <%--</ul>--%>
                </li>
                <li id="itemUserInformation">
                    <a href="##" class="menu-dropdown">
                        <i class="menu-icon glyphicon glyphicon-tasks"></i>
                        <span class="menu-text" data-i18n="data.user.information">Users Information</span>

                        <i class="menu-expand"></i>
                    </a>

                    <%--<ul class="submenu" id="submenuItemHandled">--%>
                    <%--<li id="subitemHandledComments">--%>
                    <%--<a href="##">--%>
                    <%--<span class="menu-text" data-i18n="ums.handle.history.comment"></span>--%>
                    <%--</a>--%>
                    <%--</li>--%>
                    <%--<li id="subitemHandledUsers">--%>
                    <%--<a href="##">--%>
                    <%--<span class="menu-text" data-i18n="ums.handle.history.account"></span>--%>
                    <%--</a>--%>
                    <%--</li>--%>
                    <%--</ul>--%>
                </li>
            </ul>
        </div>
        <div class="page-content">
            <div class="page-header position-relative">
                <div class="header-title">
                    <h1>
                        <i class="fa fa-angle-right"></i>
                        <span data-i18n="data.user.information">Users Information</span>
                        <%--<small>--%>
                        <%--<i class="fa fa-angle-right"></i>--%>
                        <%--<span data-i18n="data.content.view"></span>--%>
                        <%--</small>--%>
                    </h1>
                </div>
            </div>
            <div class="page-body" style="overflow:auto">
                <div id="lineCharts" style="height:400px"></div>
                <div class="col-sm-12">
                    <div id="pieCharts1" class="col-sm-6" style="height:400px"></div>
                    <div id="pieCharts2" class="col-sm-6" style="height:400px"></div>
                </div>
            </div>
            </div>
    </div>
</div>

<script src="/js/jquery.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/slimscroll/jquery.slimscroll.min.js"></script>
<script src="/js/jquery.cookie.min.js"></script>
<script src="/js/jquery.blockUI.js"></script>
<script src="/js/charts/echarts/echarts-all.js"></script>
<%--<script src="/js/charts/echarts/echarts.js"></script>--%>

<!--Beyond Scripts-->
<script src="/js/beyond.min.js"></script>

<!--Page Related Scripts-->
<script src="/js/toastr/toastr.js"></script>
<script src="/js/data/jumper/allets-data-jumper.js" charset=“UTF-8”></script>

<script src="/js/datatable/jquery.dataTables.min.js"></script>
<script src="/js/datatable/ZeroClipboard.js"></script>
<!--<script src="/js/datatable/dataTables.tableTools.min.js"></script>-->
<script src="/js/datatable/dataTables.bootstrap.min.js"></script>

<!--Bootstrap Date Picker-->
<script src="/js/datetime/bootstrap-datepicker.js"></script>

<!--i18n-->
<script src="/js/jquery.i18n.properties.js" charset=“UTF-8”></script>
<script src="/js/data/i18n.js?v=1"></script>

<script src="/js/data/statistics/usersInformatin.js?v=1"></script>






