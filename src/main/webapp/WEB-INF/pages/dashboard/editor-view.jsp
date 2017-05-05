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
            <div class="page-header position-relative">
                <div class="header-title">
                    <h1>
                        <i class="fa fa-angle-right"></i>
                        <span data-i18n="data.editor.view"></span>
                        <%--<small>--%>
                        <%--<i class="fa fa-angle-right"></i>--%>
                        <%--<span data-i18n="data.content.view"></span>--%>
                        <%--</small>--%>
                    </h1>
                </div>
            </div>
            <div class="page-body" style="overflow:auto">
                <div class="row searchbar">
                    <div class="col-sm-12">
                        <select id="editorInfoSearchField">
                            <option value="nickName" data-i18n="ums.account.management.nickname"></option>
                            <option value="email" data-i18n="ums.account.management.email"></option>
                            <option value="uid" data-i18n="ums.account.management.uid"></option>
                        </select>
                        <%--<select id="searchSource">--%>
                            <%--&lt;%&ndash;<option value="els" data-i18n="ums.account.management.fuzzy"></option>&ndash;%&gt;--%>
                            <%--<option value="mysql" data-i18n="ums.account.management.exact"></option>--%>
                        <%--</select>--%>
                        <span class="input-icon">
                            <input type="text" id="editorInfoSearchKeyword" class="form-control input-smdrop down-toggle"
                                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <ul id="searchResultDropDown" class="dropdown-menu dropdown-defult col-lg-12">
                                <li id="searchTip" value="1"><a><p data-i18n="ums.report.comment.searchfield.click">click</p> <i class="btn btn-sm glyphicon glyphicon-search blue" ></i> <p data-i18n="ums.report.comment.searchfield.placeholder">button or press "ENTER" to search editors.</p> </a></li>
                            </ul>
                            <button id="editorInfoSearchButton"
                                    class="btn btn-sm glyphicon glyphicon-search blue"></button>
                        </span>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-12">
                        <div class="widget">
                            <div class="widget-body">
                                <!-- user info table -->
                                <table class="table table-bordered text-center">
                                    <colgroup>
                                        <col width="26%"/>
                                        <col width="15%"/>
                                        <col width="22%"/>
                                        <col width="15%"/>
                                        <col width="22%"/>
                                    </colgroup>
                                    <tbody>
                                    <tr>
                                        <td rowspan="5"><img src="/img/default_avatar.png" alt="" width=200 height=200
                                                             class="profile-img"/><!--profile images-->
                                        </td>
                                        <th class="bg" rowspan="1" >
                                            Nickname
                                        </th>
                                        <td id="name" rowspan="1">
                                            -
                                        </td>
                                        <th class="bg" rowspan="1" >
                                            ContentQuantity
                                        </th>
                                        <td id="contentQuantity" rowspan="1">
                                            -
                                        </td>
                                    </tr>
                                    <tr>
                                        <th class="bg" rowspan="1">
                                            Greeting Comment
                                        </th>
                                        <td id="introMessage" rowspan="1">
                                            -
                                        </td>
                                        <th class="bg" rowspan="1" >
                                            FollowerQuantity
                                        </th>
                                        <td id="followerQuantity" rowspan="1">
                                            -
                                        </td>
                                    </tr>
                                    <tr>
                                        <th class="bg" rowspan="3">
                                        </th>
                                        <td rowspan="3">
                                         </td>
                                        <th class="bg" rowspan="1" >
                                            BookmarkQuantity
                                        </th>
                                        <td id="bookmarkQuantity" rowspan="1">
                                            -
                                        </td>
                                    </tr>
                                    <tr>
                                         <th class="bg" rowspan="1" >
                                            CommentQuantity
                                        </th>
                                        <td id="commentQuantity" rowspan="1">
                                            -
                                        </td>
                                    </tr>
                                    <tr>
                                        <th class="bg" rowspan="1" >
                                            LikeQuantity
                                        </th>
                                        <td id="likeQuantity" rowspan="1">
                                            -
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                                <!-- /user info table -->
                            </div>
                        </div>
                    </div>
                </div>
                <div id="bottomSign"></div>
            </div>
            <div class="right-side-bar">
                <i class="btn btn-lg glyphicon glyphicon-chevron-up blue"></i>
                <i class="btn btn-lg glyphicon glyphicon-chevron-down blue"></i>
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

<!--Beyond Scripts-->
<script src="/js/beyond.min.js"></script>

<!--Page Related Scripts-->
<script src="/js/toastr/toastr.js"></script>
<script src="/js/dashboard/jumper/allets-data-jumper.js" charset=“UTF-8”></script>

<script src="/js/datatable/jquery.dataTables.min.js"></script>
<script src="/js/datatable/ZeroClipboard.js"></script>
<!--<script src="/js/datatable/dataTables.tableTools.min.js"></script>-->
<script src="/js/datatable/dataTables.bootstrap.min.js"></script>

<!--Bootstrap Date Picker-->
<script src="/js/datetime/bootstrap-datepicker.js"></script>
<!--i18n-->
<script src="/js/jquery.i18n.properties.js" charset=“UTF-8”></script>
<script src="/js/dashboard/i18n.js?v=1"></script>

<script src="/js/dashboard/account/editior-view.js?v=1"></script>

