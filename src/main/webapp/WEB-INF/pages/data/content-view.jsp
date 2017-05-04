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
            <div class="navbar-header pull-right" style="margin-right: 10px;margin-top: 5px;" >
                <select id="languageSelector">
                    <option value="en-US" data-i18n="common.language.en-US">en-US</option>
                </select>
            </div>
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
                        <span class="menu-text" data-i18n="data.content.view"></span>
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
                        <span class="menu-text" data-i18n="data.editor.view"></span>

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
                        <span class="menu-text" data-i18n="data.user.information"></span>

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
                <!-- Page Header -->
                <div class="page-header position-relative">
                    <div class="header-title">
                        <h1>
                            <i class="fa fa-angle-right"></i>
                            <span data-i18n="data.content.view"></span>
                            <%--<small>--%>
                            <%--<i class="fa fa-angle-right"></i>--%>
                            <%--<span data-i18n="data.content.view"></span>--%>
                            <%--</small>--%>
                        </h1>
                    </div>
                </div>
                <!-- /Page Header -->
                <!-- Page Body -->
                <div class="page-body" style="overflow:auto">
                    <div id="topSign"></div>
                    <!-- calendar-->
                    <div class="row calendar-bar">
                        <div class="col-sm-12">
                            <div class="calendar-section" >
                                <div class="calendar-box">
                                    <div class="input-group">
                                        <input class="form-control date-picker" id="contentViewStartDatePicker" type="text" value="">
                                        <span class="input-group-addon">
                                        <i class="fa fa-calendar"></i>
                                        </span>
                                    </div>
                                    <span class="icon-sub-txt" data-i18n="ums.handle.history.account.to" style="width:180px;"></span>
                                </div>
                                <div class="calendar-box"  style="margin-right: 40px">
                                    <div class="input-group">
                                        <input class="form-control date-picker"  id="contentViewEndDatePicker"  type="text"  value="">
                                        <span class="input-group-addon">
                                        <i class="fa fa-calendar"></i>
                                    </span>
                                    </div>
                                </div>
                            </div>
                            <div class="calendar-btn">
                                <button href="javascript:void(0);" id="searchButton" class="btn btn-info btn-sm" style="margin-top: 4px;" data-i18n="ums.handle.history.account.search"></button>
                                <button href="javascript:void(0);" id="initSearchButton" class="btn btn-info btn-sm" style="margin-top: 4px;" data-i18n="ums.handle.history.account.reset"></button>
                            </div>
                        </div>
                    </div>
                    <!-- /calendar-->
                    <!-- search bar-->
                    <div class="row searchbar">
                        <div class="col-sm-3">
                            <%--<select id="handledAccountFilter">--%>
                                <%--<option value="ALL" selected = "selected" data-i18n="ums.handle.history.account.all"></option>--%>
                                <%--<option value="BLOK" data-i18n="ums.handle.history.account.blind"></option>--%>
                                <%--<option value="DEL" data-i18n="ums.handle.history.account.delete"></option>--%>
                                <%--<option value="PASS" data-i18n="ums.handle.history.account.pass"></option>--%>
                                <%--<option value="monitorId" data-i18n="ums.handle.history.account.monitorid"></option>--%>
                            <%--</select>--%>
                                show
                            <select id="handledAccountLimit">
                                <option value="50">50</option>
                                <option value="100" selected="selected">100</option>
                                <option value="200">200</option>
                            </select>
                            entries
                        </div>
                        <div class="col-sm-9 text-right">
                            <%--<i style="width: 5%;" class="btn glyphicon glyphicon-refresh blue" id="refreshButton"></i>--%>
                            <%--<select style="width: 15%;" id="contentViewSearchField">--%>
                                <%--<option value="contentTitle" data-i18n="data.content.view.title"></option>--%>
                            <%--</select>--%>
                            <span style="width: 75%;" class="input-icon">
                            <input type="text" class="form-control input-sm" id="contentViewSearchKeyword" style="padding-left: 10px;" placeholder="search with content title">
                            <%--<i class="btn btn-sm glyphicon glyphicon-search blue" id="contentViewSearchButton"></i>--%>
                            </span>
                            <%--<button href="javascript:void(0);" id="excelButton" class="btn btn-info btn-sm">excel</button>--%>
                        </div>
                    </div>
                    <!-- /search bar-->

                    <div class="row">
                        <div class="col-sm-12">
                            <div class="widget">
                                <div class="widget-body">
                                    <!-- data table -->
                                    <table id="contentViewTable" class="table table-hover table-striped table-bordered text-center">
                                        <colgroup>
                                            <col width="10%" />
                                            <col width="10%" />
                                            <col width="10%" />
                                            <col width="10%" />
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
                                            <th>udate</th>
                                            <th>ID</th>
                                            <th>Name</th>
                                            <th>UID</th>
                                            <th>Title</th>
                                            <th>app-view</th>
                                            <th>web-view</th>
                                            <th>like</th>
                                            <th>share</th>
                                            <th>bookmark</th>
                                            <th>comment</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <!--loop-->

                                        <!--//loop-->

                                        </tbody>
                                    </table>
                                    <!-- /data table -->
                                </div>
                                <!-- paging -->
                                <div class="text-center mgt10">
                                    <ul class="pagination" id="pager">
                                        <!--<li class="disabled"><a href="#">«</a></li>-->
                                        <!--<li class="active"><a href="#">1 <span class="sr-only">(current)</span></a></li>-->
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
     </div>
</div>

<script src="/js/jquery.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/slimscroll/jquery.slimscroll.min.js"></script>
<script src="/js/jquery.cookie.min.js"></script>
<script src="/js/jquery.blockUI.js"></script>

<!--Beyond Scripts-->
<script src="/js/beyond.min.js"></script>

<!--Page Related Scripts-->
<script src="/js/toastr/toastr.js"></script>

<script src="/js/datatable/jquery.dataTables.min.js"></script>
<script src="/js/datatable/ZeroClipboard.js"></script>
<!--<script src="/js/datatable/dataTables.tableTools.min.js"></script>-->
<script src="/js/datatable/dataTables.bootstrap.min.js"></script>

<!--Bootstrap Date Picker-->
<script src="/js/datetime/bootstrap-datepicker.js"></script>
<!--i18n-->
<script src="/js/jquery.i18n.properties.js" charset=“UTF-8”></script>
<script src="/js/data/i18n.js?v=1"></script>

<script src="/js/data/const.js?v=1" charset=“UTF-8”></script>

<script src="/js/data/pager/pager.js?v=1" charset=“UTF-8”></script>
<script src="/js/data/jumper/allets-data-jumper.js?v=1" charset=“UTF-8”></script>

<script src="/js/data/content/content-view.js?v=1" charset=“UTF-8”></script>