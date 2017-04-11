<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
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
                <li id="itemRule">
                    <a href="##" class="menu-dropdown">
                        <i class="menu-icon fa fa-desktop"></i>
                        <span class="menu-text" data-i18n="ums.handle.rule.monitoring.criterion"></span>
                        <i class="menu-expand"></i>
                    </a>
                    <!--sub menu-->
                    <ul class="submenu" id="submenuItemRule">
                        <li id="subitemRule">
                            <a href="##">
                                <span class="menu-text" data-i18n="ums.handle.rule.monitoring.review.grounds"></span>
                            </a>
                        </li>
                    </ul>
                    <!--//sub menu-->
                </li>
                <!--//menu list-->

                <li id="itemAccountManagement">
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
                            <span data-i18n="ums.account.management"></span>
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
                    <div class="col-sm-12">
                        <select id="userInfoSearchField">
                            <option value="nickName" data-i18n="ums.account.management.nickname"></option>
                            <option value="email" data-i18n="ums.account.management.email"></option>
                            <option value="uid" data-i18n="ums.account.management.uid"></option>
                        </select>
                        <select id="searchSource">
                            <%--<option value="els" data-i18n="ums.account.management.fuzzy"></option>--%>
                            <option value="mysql" data-i18n="ums.account.management.exact"></option>
                        </select>
                       <span class="input-icon">
                            <input type="text" id="userInfoSearchKeyword" class="form-control input-smdrop down-toggle"
                                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <ul id="searchResultDropDown" class="dropdown-menu dropdown-defult col-lg-12">
                                <li id="searchTip" value="1"><a><p data-i18n="ums.report.comment.searchfield.click">click</p> <i class="btn btn-sm glyphicon glyphicon-search blue" ></i> <p data-i18n="ums.report.comment.searchfield.placeholder">button or press "ENTER" to search users.</p> </a></li>
                            </ul>
                            <button id="userInfoSearchButton"
                                    class="btn btn-sm glyphicon glyphicon-search blue"></button>
                        </span>
                    </div>
                </div>
                <!-- /search bar-->
                <div class="row">
                    <div class="col-sm-12">
                        <!-- user info-->
                        <div class="row" style="margin-left: 0px;margin-right: 0px">
                            <div class="col-sm-9 widget-header">
                            <span class="widget-caption"><span data-i18n="ums.account.management.info"></span>
                            <span class="danger" data-i18n="ums.account.management.duration"></span>
                            <span id="invalidFrom" class="danger">-</span>
                            <span class="danger"> ~ </span>
                            <span id="invalidTo" class="danger">-</span></span>
                            </div>
                        </div>

                        <!-- /user info-->
                        <div class="row searchhelpbar mg-grid">
                            <div class="col-sm-3"><span class="text-tit line-height220" data-i18n="ums.account.management.joinDate">Join date</span><span
                                    id="userInfoCdate" typr="text">-</span>
                            </div>
                            <div class="col-sm-2"><span class="text-tit line-height220" data-i18n="ums.account.management.uid">User id</span><span
                                    id="userInfoUid" typr="text">-</span>
                            </div>
                            <!-- button area -->
                            <div class="col-sm-7 text-right">
                                <a id="editUserInfoPopoverButton" class="btn btn-default" rel="popover">
                                    <span class="glyphicon glyphicon-edit"></span><span
                                        data-i18n="ums.account.management.info.edit"></span>
                                </a>
                                <a id="editUserInfoResetPasswordButton" class="btn btn-default">
                                    <span class="glyphicon glyphicon-asterisk"></span><span
                                        data-i18n="ums.account.management.info.reset.password"></span>
                                </a>
                            </div>
                            <!-- /button area -->
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-12">
                        <div class="widget">
                            <div class="widget-body">
                                <!-- user info table -->
                                <table class="table table-bordered text-center">
                                    <colgroup>
                                        <col width="14%"/>
                                        <col width="12%"/>
                                        <col width="15%"/>
                                        <col width="22%"/>
                                        <col width="15%"/>
                                        <col width="22%"/>
                                    </colgroup>
                                    <tbody>
                                    <tr>
                                        <th class="bg" colspan="2" data-i18n="ums.account.management.photo">
                                            Image Profile
                                        </th>
                                        <th class="bg" rowspan="2" data-i18n="ums.account.management.nickname">
                                            Nickname
                                        </th>
                                        <td id="name" rowspan="2">
                                            -
                                        </td>
                                        <th class="bg" rowspan="2" data-i18n="ums.account.management.intro">
                                            Greeting Comment
                                        </th>
                                        <td id="introMessage" rowspan="2">
                                            -
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2"><img src="/img/default_avatar.png" alt="" width=200 height=200
                                                             class="profile-img"/><!--profile images-->
                                        </td>
                                    </tr>
                                    <tr>
                                        <th class="bg" data-i18n="ums.account.management.gender">Gender</th>
                                        <td id="sex">-</td>
                                        <th class="bg" data-i18n="ums.account.management.birthday">Birthday</th>
                                        <td id="birthday">-</td>
                                        <th class="bg" data-i18n="ums.account.management.email">e-mail</th>
                                        <td id="email">-</td>
                                    </tr>
                                    </tbody>
                                </table>
                                <!-- /user info table -->
                                <!-- count table -->
                                <table class="table table-bordered mgt10 text-center">
                                    <colgroup>
                                        <col width="20%"/>
                                        <col width="13.3%"/>
                                        <col width="20%"/>
                                        <col width="13.3%"/>
                                        <col width="20%"/>
                                        <col width="13.3%"/>
                                    </colgroup>
                                    <tbody>
                                    <tr>
                                        <th class="bg" data-i18n="ums.account.management.BestCommentCount">
                                            Best Comment counting
                                        </th>
                                        <td id="bestCommentsCount">
                                            -
                                        </td>
                                        <th class="bg" data-i18n="ums.account.management.shareCount">
                                            Sharing Counting
                                        </th>
                                        <td id="shareCount">
                                            -
                                        </td>
                                        <th class="bg" data-i18n="ums.account.management.bookmarkCount">
                                            Content Bookmark Counting
                                        </th>
                                        <td id="bookMarkCount">
                                            -
                                        </td>
                                    </tr>
                                    <tr>
                                        <th class="bg" data-i18n="ums.account.management.subscribeToEditor">
                                            Subscribe Editor Counting
                                        </th>
                                        <td id="subscribeEditorCount">
                                            -
                                        </td>
                                        <th class="bg" data-i18n="ums.account.management.subscriber">
                                            Subscriber Counting
                                        </th>
                                        <td id="subscriberCount">
                                            -
                                        </td>
                                        <th class="bg" data-i18n="ums.account.management.subscribeToNormal">
                                            subscribe To Normal
                                        </th>
                                        <td id="subscribeNormalCount">
                                            -
                                        </td>
                                    </tr>
                                    <tr>
                                        <th class="bg" data-i18n="ums.account.management.certifyOver">
                                            Certify over 15
                                        </th>
                                        <td id="certifyOver15">
                                            -
                                        </td>
                                        <th class="bg" data-i18n="ums.account.management.loginType">
                                            Login Type
                                        </th>
                                        <td id="loginType">
                                            -
                                        </td>
                                        <th class="bg" data-i18n="ums.account.management.status">
                                            Status
                                        </th>
                                        <td id="userStatus">
                                            -
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                                <!-- /count table -->
                                <!-- alert table -->
                                <table class="table table-bordered mgt10 text-center">
                                    <colgroup>
                                        <col width="20%"/>
                                        <col width="13.3%"/>
                                        <col width="20%"/>
                                        <col width="13.3%"/>
                                        <col width="20%"/>
                                        <col width="13.3%"/>
                                     </colgroup>
                                    <tbody>
                                    <tr>
                                        <th class="bg">
                                            <a id="userInfoAccountReportTypeButton" class="dropdown-toggle"
                                               rel="popover" data-i18n="ums.account.management.accountReportTotalCounting">
                                                Account Report<br>
                                                Total Counting
                                                <b class="caret"></b>
                                            </a>
                                        </th>
                                        <br/>
                                        <td>
                                            <span id="userReportedCount">-</span><br>
                                        </td>
                                        <th class="bg">
                                            <a id="userInfoDeleteComentsTypeButton" class="dropdown-toggle"
                                               rel="popover" data-i18n="ums.account.management.deleteCommedntReportTotalCounting">
                                                Total Deleted<br>
                                                Comments Count
                                                <b class="caret"></b>
                                            </a>
                                        </th>
                                        <td>
                                            <span id="deleteCommentsCount">-</span>
                                        </td>
                                        <th class="bg" data-i18n="ums.account.management.warningAlert">
                                            Warning Alert
                                        </th>
                                        <td id="warningAlert">
                                            -
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                                <!-- /alert table -->
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
            <!-- /Page Body -->
        </div>
        <!-- /Page Content -->
    </div>
    <!-- /Page Container -->
    <!-- Main Container -->
</div>
<div class="modal fade text-center" id="myModal" tabindex="-1" role="dialog" aria-labelledby="commentContextLable"
     aria-hidden="false">
    <div class="modal-dialog bg-gray" style="display: inline-block; width: auto;">
        <div class="modal-content ">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="
                ">Comment Context</h4>
            </div>
            <div class="modal-body no-border col-sm-12" style="max-width:840px;max-height: 600px">
                <div class="col-sm-6"
                     style="width: 420px;height: 560px;margin-right:10px;overflow-y:auto;overflow-x: hidden">
                    <!--<iframe id="alletsHtml" hidden="true" frameBorder="0"-->
                    <!--class="bordered-top-1 bordered-bottom-1 bordered-right-1 bordered-left-1 bordered-black"-->
                    <!--style="width: 800px;height: 600px"></iframe>-->
                    <div class="widget-header bordered-bottom-1 bordered-top bordered-info" style="width: 390px">
                        <i class="widget-icon fa fa-tags info"></i>
                        <span id="alletsType" class="widget-caption info"> </span>
                    </div>
                    <a id="alletsUrl" target="_blank"> </a>
                </div>
                <div id="commentContext" class="col-sm-6" style="width: 380px;height: 560px;overflow:auto">
                </div>
            </div>

        </div>
        <!-- /.modal-content -->
    </div>
</div>
<div class="modal fade bs-example-modal-sm pw-layer in" id="editUserinfoModel">
    <div class="modal-dialog">
        <div class="modal-content" style="border: solid;border-color: #cd2929">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">×</span></button>
                <h4 class="danger modal-title"><b>IMPORTANT WARNING!!!</b></h4>
            </div>
            <div class="modal-body">
                <p>You want to change user's info:</p>

                <p id="changeSex" style="display: none;">changeSex</p>

                <p id="changeBirthday" style="display: none;">changeBirthday</p>

                <p id="changeEmail" style="display: none;">changeEmail</p>

                <p id="changePhoto" style="display: none;">changePhoto</p>

                <p id="changeNickname" style="display: none;">changeNickname</p>

                <p id="changeIntroMessage" style="display: none;">changeIntroMessage</p>

                <p id="changeSubscribe" style="display: none;">changeSubscribe</p>

                <p id="changeSubscriber" style="display: none;">changeSubscriber</p>
            </div>
            <div class="modal-footer">
                <input type="hidden" id="url"/>
                <button type="button" class="btn btn-default" data-dismiss="modal" id="editCancel">cancel</button>
                <a class="btn btn-danger" data-dismiss="modal" id="editOK">ok</a>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<div class="modal fade bs-example-modal-sm pw-layer in" id="resetPasswordModel">
    <div class="modal-dialog">
        <div class="modal-content" style="border: solid;border-color: #cd2929">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">×</span></button>
                <h4 class="danger modal-title"><b>IMPORTANT WARNING!!!</b></h4>
            </div>
            <div class="modal-body">
                <p id="resetPasswordSting">Do You want to reset user's Password? UMS will send Password to user with email.</p>
            </div>
            <div class="modal-footer">
                <input type="hidden" id="resetPasswordUrl"/>
                <button type="button" class="btn btn-default" data-dismiss="modal" id="resetPasswordCancel">cancel</button>
                <a class="btn btn-danger" data-dismiss="modal" id="resetPasswordOK">ok</a>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<!--Basic Scripts-->
<script src="/js/jquery.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/slimscroll/jquery.slimscroll.min.js"></script>
<script src="/js/jquery.cookie.min.js"></script>
<script src="/js/jquery.blockUI.js"></script>

<!--Beyond Scripts-->
<script src="/js/beyond.min.js"></script>

<!--Page Related Scripts-->
<script src="/js/toastr/toastr.js"></script>
<script src="/js/umsjs/jumper/jumper.js" charset=“UTF-8”></script>

<script src="/js/datatable/jquery.dataTables.min.js"></script>
<script src="/js/datatable/ZeroClipboard.js"></script>
<!--<script src="/js/datatable/dataTables.tableTools.min.js"></script>-->
<script src="/js/datatable/dataTables.bootstrap.min.js"></script>

<!--Bootstrap Date Picker-->
<script src="/js/datetime/bootstrap-datepicker.js"></script>
<!--i18n-->
<script src="/js/jquery.i18n.properties.js" charset=“UTF-8”></script>
<script src="/js/umsjs/i18n.js"></script>

<script src="/js/umsjs/account/accountInfoForCs.js" charset=“UTF-8”></script>

<!-- /Body -->

