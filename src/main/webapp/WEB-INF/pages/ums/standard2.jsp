<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>

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
                        <span data-i18n="ums.handle.rule.monitoring.criterion"></span>
                        <small>
                            <i class="fa fa-angle-right"></i>
                            <span data-i18n="ums.handle.rule.monitoring.review.grounds"></span>
                        </small>
                    </h1>
                </div>
            </div>
            <!-- /Page Header -->
            <!-- Page Body -->
            <div class="page-body" style="overflow:auto">
                <div id="topSign"></div>
                <div class="row">
                    <div class="col-sm-12">
                        <div class="widget standard-box">
                            <div class="widget-body no-padding">
                                <div class="widget-main ">
                                    <div class="panel-group accordion" id="accordion">

                                    </div>
                                </div>
                            </div>
                        </div>
                        <div id="bottomSign"></div>
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

<!--&lt;!&ndash;register prohibition word layer&ndash;&gt;-->
<!--<div class="modal fade bs-example-modal-sm pw-layer" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">-->
    <!--<div class="modal-dialog modal-sm">-->
        <!--&lt;!&ndash; contents &ndash;&gt;-->
        <!--<div class="modal-content">-->
            <!--<div class="modal-header">-->
                <!--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>-->
                <!--<h4 class="modal-title" id="mySmallModalLabel">금칙어 추가</h4>-->
            <!--</div>-->
            <!--<div class="modal-body">-->
                <!--<textarea class="form-control" rows="3" placeholder="금칙어를 입력 하세요."></textarea>-->
                <!--<div class="mgt10 text-right">-->
                    <!--<select>-->
                        <!--<option>A등급</option>-->
                        <!--<option>B등급</option>-->
                        <!--<option>C등급</option>-->
                    <!--</select>-->
                <!--</div>-->
            <!--</div>-->
            <!--<div class="modal-footer">-->
                <!--<button type="button" class="btn btn-info" data-dismiss="modal">등록</button>-->
                <!--<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>-->
            <!--</div>-->
        <!--</div>-->
        <!--&lt;!&ndash; /contents &ndash;&gt;-->
    <!--</div>-->
<!--</div>-->
<!--&lt;!&ndash;/register prohibition word layer&ndash;&gt;-->

<!--&lt;!&ndash;nodata layer&ndash;&gt;-->
<!--<div class="bootbox modal fade bootbox-confirm in textbox-layer" tabindex="-1" role="dialog" aria-hidden="false">-->
    <!--<div class="modal-dialog modal-sm">-->
        <!--&lt;!&ndash; contents &ndash;&gt;-->
        <!--<div class="modal-content">-->
            <!--<div class="modal-body">-->
                <!--<button type="button" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button>-->
                <!--<div class="bootbox-body">검색하신 금칙어가 목록에 없습니다.</div>-->
            <!--</div>-->
            <!--<div class="modal-footer"><button data-bb-handler="confirm" type="button" class="btn btn-info btn-sm">확인</button></div>-->
        <!--</div>-->
        <!--&lt;!&ndash; /contents &ndash;&gt;-->
    <!--</div>-->
<!--</div>-->
<!--&lt;!&ndash;/nodata layer&ndash;&gt;-->

<!--Basic Scripts-->
<script src="/js/jquery.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/slimscroll/jquery.slimscroll.min.js"></script>
<script src="/js/jquery.cookie.min.js"></script>
<script src="/js/jquery.blockUI.js"></script>

<!--Beyond Scripts-->
<script src="/js/beyond.min.js"></script>
<!--i18n-->
<script src="/js/jquery.i18n.properties.js" charset=“UTF-8”></script>
<script src="/js/umsjs/i18n.js" charset=“UTF-8”></script>
<!--Page Related Scripts-->
<script src="/js/toastr/toastr.js"></script>
<script src="/js/umsjs/jumper/jumper.js" charset=“UTF-8”></script>
<script src="/js/umsjs/rules/commenrHandleRulesForCs.js"></script>


<!--  /Body -->