package com.allets.backend.data.server.web;

import com.allets.backend.data.server.consts.Const;
import com.allets.backend.data.server.data.dto.MonitorDTO;
import com.allets.backend.data.server.data.dto.UserDTO;
import com.allets.backend.data.server.data.result.AlertResult;
import com.allets.backend.data.server.data.result.UserBlackResult;
import com.allets.backend.data.server.data.result.UserResult;
import com.allets.backend.data.server.exception.NotFoundUserException;
import com.allets.backend.data.server.facade.CommentFacade;
import com.allets.backend.data.server.facade.SecurityFacade;
import com.allets.backend.data.server.facade.UserFacade;
import com.allets.backend.data.server.utils.MessageUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.jsondoc.core.annotation.*;
import org.jsondoc.core.pojo.ApiParamType;
import org.jsondoc.core.pojo.ApiVerb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Api(name = "UserController", description = "handle and list user controller")
@ApiVersion(since = "1.0")
@Controller
@RequestMapping()
public class UserController {

    /**
     * The log.
     */
    final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserFacade userFacade;

    @Autowired
    CommentFacade commentFacade;

    @Autowired
    private MessageUtil messageUtil;

    @Autowired
    SecurityFacade securityFacade;

    /**
     * get user report list
     *
     * @param model
     * @param action
     * @param status
     * @param field
     * @param q
     * @param sort
     * @param offset
     * @param limit
     * @param xAlletsLang
     * @param xAlletsContry
     * @param xAlletsToken
     * @param xAlletsChannel
     * @param xAlletsVersion
     * @param xAlletsTraceuuid
     * @throws Exception
     */
    @ApiVersion(since = "1.0")
    @ApiAuthBasic(roles = {})
    @ApiMethod(
            path = "/v1.0/users",
            verb = ApiVerb.GET,
            description = "get users list",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @RequiresAuthentication
    @RequestMapping(value = "/v1.0" + "/users", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ApiResponseObject
    void getAllUsers(@ApiBodyObject Model model,
                     @ApiParam(name = "action",
                             description = "action:\"EPORTED\",\"HANDLED\",\"ALL\",\"ALLSIMPLE\"",
                             required = false,
                             paramType = ApiParamType.QUERY) @RequestParam(value = "action", required = false) String action,
                     @ApiParam(name = "status",
                             description = "status",
                             required = false,
                             paramType = ApiParamType.QUERY) @RequestParam(value = "status", required = false) String status,
                     @ApiParam(name = "field",
                             description = "field",
                             required = false,
                             paramType = ApiParamType.QUERY) @RequestParam(value = "field", required = false) String field,
                     @ApiParam(name = "q",
                             description = "q",
                             required = false,
                             paramType = ApiParamType.QUERY) @RequestParam(value = "q", required = false) String q,
                     @ApiParam(name = "sort",
                             description = "sort",
                             required = false,
                             paramType = ApiParamType.QUERY) @RequestParam(value = "sort", required = false) String sort,
                     @ApiParam(name = "offset",
                             description = "offset",
                             required = false,
                             paramType = ApiParamType.QUERY) @RequestParam(value = "offset", required = false, defaultValue = "0") String offset,
                     @ApiParam(name = "limit",
                             description = "limit",
                             required = false,
                             paramType = ApiParamType.QUERY) @RequestParam(value = "limit", required = false, defaultValue = "50") String limit,
                     @RequestHeader(required = false, value = "X-ALLETS-LANG") String xAlletsLang,
                     @RequestHeader(required = false, value = "X-ALLETS-COUNTRY") String xAlletsContry,
                     @RequestHeader(required = false, value = "X-ALLETS-TOKEN") String xAlletsToken,
                     @RequestHeader(required = false, value = "X-ALLETS-CHANNEL") String xAlletsChannel,
                     @RequestHeader(required = false, value = "X-ALLETS-VERSION") String xAlletsVersion,
                     @RequestHeader(required = false, value = "X-ALLETS-TRACEUUID") String xAlletsTraceuuid
    ) throws Exception {
        MonitorDTO monitorDTO = securityFacade.getAuthenticatedMonitor();
        if (monitorDTO == null) {
            throw new NotFoundUserException();
        }
        Page<UserResult> pageResult = userFacade.findUserList(action, Integer.valueOf(offset), Integer.valueOf(limit), q, sort);
        if (Const.Action.REPORTED.equals(action) || Const.Action.HANDLED.equals(action)) {
            if (pageResult != null) {
                for (UserResult ur : pageResult.getContent()) {
                    String reportMessage = getReportMessage(action, ur.getReportType());
                    ur.setReportTypeMessage(reportMessage);
                }
            }
        }
        model.addAttribute(pageResult);

    }

    /**
     * get user report list
     *
     * @param model
     * @param action
     * @param xAlletsLang
     * @param xAlletsContry
     * @param xAlletsToken
     * @param xAlletsChannel
     * @param xAlletsVersion
     * @param xAlletsTraceuuid
     * @throws Exception
     */
    @ApiVersion(since = "1.0")
    @ApiAuthBasic(roles = {})
    @ApiMethod(
            path = "/v1.0/users/{uid}",
            verb = ApiVerb.PUT,
            description = "edit user",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @RequiresAuthentication
    @RequestMapping(value = "/v1.0" + "/users/{uid}", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ApiResponseObject
    void editUser(@ApiBodyObject Model model,
                  @ApiParam(name = "uid",
                          description = "uid",
                          required = true,
                          paramType = ApiParamType.PATH) @PathVariable(value = "uid") Long uid,
                  @ApiParam(name = "action",
                          description = "action:\"PASS\",\"OUT\",\"OUTF\",\"MODIFYPROFILES\",\"UNOUT\",\"UNDEL\",\"MODIFYPASSWORD\"",
                          required = false,
                          paramType = ApiParamType.QUERY) @RequestParam(value = "action", required = false) String action,
                  @ApiParam(name = "q",
                          description = "q",
                          required = false,
                          paramType = ApiParamType.QUERY) @RequestParam(value = "q", required = false) String q,
                  @RequestHeader(required = false, value = "X-ALLETS-LANG") String xAlletsLang,
                  @RequestHeader(required = false, value = "X-ALLETS-COUNTRY") String xAlletsContry,
                  @RequestHeader(required = false, value = "X-ALLETS-TOKEN") String xAlletsToken,
                  @RequestHeader(required = false, value = "X-ALLETS-CHANNEL") String xAlletsChannel,
                  @RequestHeader(required = false, value = "X-ALLETS-VERSION") String xAlletsVersion,
                  @RequestHeader(required = false, value = "X-ALLETS-TRACEUUID") String xAlletsTraceuuid
    ) throws Exception {
        MonitorDTO monitorDTO = securityFacade.getAuthenticatedMonitor();
        if (monitorDTO == null) {
            throw new NotFoundUserException();
        }
        long monitorId = monitorDTO.getMonitorId();
        model.addAttribute(userFacade.handleUser(action, uid, monitorId, q,
                messageUtil.getMessage("backend.ums.server.account.nickname.default"),
                messageUtil.getMessage("backend.ums.server.email.block.file.path"),
                messageUtil.getMessage("backend.ums.server.email.delete.file.path"),
                messageUtil.getMessage("backend.ums.server.email.block.title"),
                messageUtil.getMessage("backend.ums.server.email.delete.title"),
                messageUtil.getMessage("backend.ums.server.email.password.reset.file.path"),
                messageUtil.getMessage("backend.ums.server.email.password.reset.title")));
    }

    @ApiVersion(since = "1.0")
    @ApiAuthBasic(roles = {})
    @ApiMethod(
            path = "/v1.0/userInvalid/{uid}",
            verb = ApiVerb.GET,
            description = "get userInvalid from/to",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @RequiresAuthentication
    @RequestMapping(value = "/v1.0" + "/userInvalid/{uid}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ApiResponseObject
    void getUserInvalid(@ApiBodyObject Model model,
                        @ApiParam(name = "uid",
                                description = "uid",
                                required = true,
                                paramType = ApiParamType.PATH) @PathVariable(value = "uid") Long uid,
                        @RequestHeader(required = false, value = "X-ALLETS-LANG") String xAlletsLang,
                        @RequestHeader(required = false, value = "X-ALLETS-COUNTRY") String xAlletsContry,
                        @RequestHeader(required = false, value = "X-ALLETS-TOKEN") String xAlletsToken,
                        @RequestHeader(required = false, value = "X-ALLETS-CHANNEL") String xAlletsChannel,
                        @RequestHeader(required = false, value = "X-ALLETS-VERSION") String xAlletsVersion,
                        @RequestHeader(required = false, value = "X-ALLETS-TRACEUUID") String xAlletsTraceuuid
    ) throws Exception {
        model.addAttribute(userFacade.findUserInvalidByUid(uid));
    }

    @ApiVersion(since = "1.0")
    @ApiAuthBasic(roles = {})
    @ApiMethod(
            path = "/v1.0/users/black",
            verb = ApiVerb.GET,
            description = "get black list",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @RequiresAuthentication
    @RequestMapping(value = "/v1.0" + "/users/black", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ApiResponseObject
    void getBlackUsers(@ApiBodyObject Model model,
                       @ApiParam(name = "q",
                               description = "q",
                               required = false,
                               paramType = ApiParamType.QUERY) @RequestParam(value = "q", required = false) String q,
                       @ApiParam(name = "offset",
                               description = "offset",
                               required = false,
                               paramType = ApiParamType.QUERY) @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                       @ApiParam(name = "limit",
                               description = "limit",
                               required = false,
                               paramType = ApiParamType.QUERY) @RequestParam(value = "limit", required = false, defaultValue = "50") Integer limit,
                       @RequestHeader(required = false, value = "X-ALLETS-LANG") String xAlletsLang,
                       @RequestHeader(required = false, value = "X-ALLETS-COUNTRY") String xAlletsContry,
                       @RequestHeader(required = false, value = "X-ALLETS-TOKEN") String xAlletsToken,
                       @RequestHeader(required = false, value = "X-ALLETS-CHANNEL") String xAlletsChannel,
                       @RequestHeader(required = false, value = "X-ALLETS-VERSION") String xAlletsVersion,
                       @RequestHeader(required = false, value = "X-ALLETS-TRACEUUID") String xAlletsTraceuuid
    ) throws Exception {
        Page<UserBlackResult> results = userFacade.findAllBlackList(q, offset, limit);
        model.addAttribute("results", results);
    }

    @ApiVersion(since = "1.0")
    @ApiAuthBasic(roles = {})
    @ApiMethod(
            path = "/v1.0/users/black/{uid}",
            verb = ApiVerb.PUT,
            description = "add black user",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @RequiresAuthentication
    @RequestMapping(value = "/v1.0" + "/users/black/{uid}", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ApiResponseObject
    void insertBlackUser(@ApiBodyObject Model model,
                         @ApiParam(name = "uid",
                                 description = "uid",
                                 required = true,
                                 paramType = ApiParamType.PATH) @PathVariable(value = "uid") String uid,
                         @RequestHeader(required = false, value = "X-ALLETS-LANG") String xAlletsLang,
                         @RequestHeader(required = false, value = "X-ALLETS-COUNTRY") String xAlletsContry,
                         @RequestHeader(required = false, value = "X-ALLETS-TOKEN") String xAlletsToken,
                         @RequestHeader(required = false, value = "X-ALLETS-CHANNEL") String xAlletsChannel,
                         @RequestHeader(required = false, value = "X-ALLETS-VERSION") String xAlletsVersion,
                         @RequestHeader(required = false, value = "X-ALLETS-TRACEUUID") String xAlletsTraceuuid
    ) throws Exception {
        MonitorDTO monitorDTO = securityFacade.getAuthenticatedMonitor();
        if (monitorDTO == null) {
            throw new NotFoundUserException();
        }
        long monitorId = monitorDTO.getMonitorId();
        model.addAttribute(userFacade.createBlackUser(Long.valueOf(uid), monitorId));
    }

    @ApiVersion(since = "1.0")
    @ApiAuthBasic(roles = {})
    @ApiMethod(
            path = "/v1.0/users/black/{id}",
            verb = ApiVerb.DELETE,
            description = "delete black list",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @RequiresAuthentication
    @RequestMapping(value = "/v1.0" + "/users/black/{id}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ApiResponseObject
    void deletingSalngWord(@ApiBodyObject Model model,
                           @ApiParam(name = "id",
                                   description = "black list id",
                                   required = true,
                                   paramType = ApiParamType.PATH) @PathVariable(value = "id") long blackId,
                           @RequestHeader(required = false, value = "X-ALLETS-LANG") String xAlletsLang,
                           @RequestHeader(required = false, value = "X-ALLETS-COUNTRY") String xAlletsContry,
                           @RequestHeader(required = false, value = "X-ALLETS-TOKEN") String xAlletsToken,
                           @RequestHeader(required = false, value = "X-ALLETS-CHANNEL") String xAlletsChannel,
                           @RequestHeader(required = false, value = "X-ALLETS-VERSION") String xAlletsVersion,
                           @RequestHeader(required = false, value = "X-ALLETS-TRACEUUID") String xAlletsTraceuuid
    ) throws Exception {
        userFacade.removeBlackUser(blackId);
        model.addAttribute("results", "success");
    }

    private String getReportMessage(String action, String reportTypeString) {
        StringBuilder rtnSb = new StringBuilder();
        if (!action.contains("ALL") && (reportTypeString == null || reportTypeString.isEmpty())) {
            rtnSb.append("(g)");
            rtnSb.append(messageUtil.getMessage("backend.ums.server.slang.rt999"));
        } else if (action.contains("ALL") && (reportTypeString == null || reportTypeString.isEmpty())) {
            return "";
        } else {
            String[] reportTypeArray = reportTypeString.split(",");
            for (String reportType : reportTypeArray) {
                switch (reportType) {
                    case Const.ReportType.RT100:
                        rtnSb.append("(a)");
                        rtnSb.append(messageUtil.getMessage("backend.ums.server.slang.rt100"));
                        rtnSb.append("<br>");
                        break;
                    case Const.ReportType.RT200:
                        rtnSb.append("(b)");
                        rtnSb.append(messageUtil.getMessage("backend.ums.server.slang.rt200"));
                        rtnSb.append("<br>");
                        break;
                    case Const.ReportType.RT300:
                        rtnSb.append("(c)");
                        rtnSb.append(messageUtil.getMessage("backend.ums.server.slang.rt300"));
                        rtnSb.append("<br>");
                        break;
                    case Const.ReportType.RT400:
                        rtnSb.append("(d)");
                        rtnSb.append(messageUtil.getMessage("backend.ums.server.slang.rt400"));
                        rtnSb.append("<br>");
                        break;
                    case Const.ReportType.RT500:
                        rtnSb.append("(e)");
                        rtnSb.append(messageUtil.getMessage("backend.ums.server.slang.rt500"));
                        rtnSb.append("<br>");
                        break;
                    case Const.ReportType.RT600:
                        rtnSb.append("(f)");
                        rtnSb.append(messageUtil.getMessage("backend.ums.server.slang.rt600"));
                        rtnSb.append("<br>");
                        break;
                    case Const.ReportType.RT999:
                        rtnSb.append("(g)");
                        rtnSb.append(messageUtil.getMessage("backend.ums.server.slang.rt999"));
                        rtnSb.append("<br>");
                        break;
                    default:
                        break;
                }
            }
        }
        return rtnSb.toString();
    }

    /**
     * get user report list
     *
     * @param action
     * @param xAlletsLang
     * @param xAlletsContry
     * @param xAlletsToken
     * @param xAlletsChannel
     * @param xAlletsVersion
     * @param xAlletsTraceuuid
     * @throws Exception
     */
    @ApiVersion(since = "1.0")
    @ApiAuthBasic(roles = {})
    @ApiMethod(
            path = "/v1.0/users/{uid}",
            verb = ApiVerb.GET,
            description = "get user base info",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @RequiresAuthentication
    @RequestMapping(value = "/v1.0" + "/users/{uid}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public
    @ApiResponseObject
    UserDTO getUser(@ApiParam(name = "uid",
            description = "uid",
            required = true,
            paramType = ApiParamType.PATH) @PathVariable(value = "uid") Long uid,
                    @RequestParam(value = "action", required = false) String action,
                    @RequestHeader(required = false, value = "X-ALLETS-LANG") String xAlletsLang,
                    @RequestHeader(required = false, value = "X-ALLETS-COUNTRY") String xAlletsContry,
                    @RequestHeader(required = false, value = "X-ALLETS-TOKEN") String xAlletsToken,
                    @RequestHeader(required = false, value = "X-ALLETS-CHANNEL") String xAlletsChannel,
                    @RequestHeader(required = false, value = "X-ALLETS-VERSION") String xAlletsVersion,
                    @RequestHeader(required = false, value = "X-ALLETS-TRACEUUID") String xAlletsTraceuuid
    ) throws Exception {
        UserDTO userDTO = userFacade.findUser(uid);
        return userDTO;
    }

    @ApiVersion(since = "1.0")
    @ApiAuthBasic(roles = {})
    @ApiMethod(
            path = "/v1.0/users/{uid}/deleteCommentReportTypeCount",
            verb = ApiVerb.GET,
            description = "get user delete comment report count of each report type",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @RequiresAuthentication
    @RequestMapping(value = "/v1.0" + "/users/{uid}/deleteCommentReportTypeCount", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ApiResponseObject
    void getDeleteCommentReportTypeCount(@ApiBodyObject Model model,
                                         @ApiParam(name = "uid",
                                                 description = "uid",
                                                 required = true,
                                                 paramType = ApiParamType.PATH) @PathVariable(value = "uid") String uid,
                                         @RequestHeader(required = false, value = "X-ALLETS-LANG") String xAlletsLang,
                                         @RequestHeader(required = false, value = "X-ALLETS-COUNTRY") String xAlletsContry,
                                         @RequestHeader(required = false, value = "X-ALLETS-TOKEN") String xAlletsToken,
                                         @RequestHeader(required = false, value = "X-ALLETS-CHANNEL") String xAlletsChannel,
                                         @RequestHeader(required = false, value = "X-ALLETS-VERSION") String xAlletsVersion,
                                         @RequestHeader(required = false, value = "X-ALLETS-TRACEUUID") String xAlletsTraceuuid
    ) throws Exception {
        model.addAttribute("reportTypeCount", commentFacade.findReportTypeCountResult(Long.valueOf(uid)));
    }

    @ApiVersion(since = "1.2")
    @ApiAuthBasic(roles = {})
    @ApiMethod(
            path = "/v1.2/users/{uid}/accountReportTypeCount",
            verb = ApiVerb.GET,
            description = "get user report count of each report type",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @RequiresAuthentication
    @RequestMapping(value = "/v1.2" + "/users/{uid}/accountReportTypeCount", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ApiResponseObject
    void getAccountReportTypeCount(@ApiBodyObject Model model,
                                   @ApiParam(name = "uid",
                                           description = "uid",
                                           required = true,
                                           paramType = ApiParamType.PATH) @PathVariable(value = "uid") String uid,
                                   @RequestHeader(required = false, value = "X-ALLETS-LANG") String xAlletsLang,
                                   @RequestHeader(required = false, value = "X-ALLETS-COUNTRY") String xAlletsContry,
                                   @RequestHeader(required = false, value = "X-ALLETS-TOKEN") String xAlletsToken,
                                   @RequestHeader(required = false, value = "X-ALLETS-CHANNEL") String xAlletsChannel,
                                   @RequestHeader(required = false, value = "X-ALLETS-VERSION") String xAlletsVersion,
                                   @RequestHeader(required = false, value = "X-ALLETS-TRACEUUID") String xAlletsTraceuuid
    ) throws Exception {
        model.addAttribute("reportTypeCount", userFacade.findReportTypeCountResult(Long.valueOf(uid)));
    }

    @ApiVersion(since = "1.0")
    @ApiAuthBasic(roles = {})
    @ApiMethod(
            path = "/v1.0/users/{uid}/bestCommentCount",
            verb = ApiVerb.GET,
            description = "get user best comment count",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @RequiresAuthentication
    @ResponseBody
    @RequestMapping(value = "/v1.0" + "/users/{uid}/bestCommentCount", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ApiResponseObject
    Integer getBestCommentCount(
            @ApiParam(name = "uid",
                    description = "uid",
                    required = true,
                    paramType = ApiParamType.PATH) @PathVariable(value = "uid") String uid,
            @RequestHeader(required = false, value = "X-ALLETS-LANG") String xAlletsLang,
            @RequestHeader(required = false, value = "X-ALLETS-COUNTRY") String xAlletsContry,
            @RequestHeader(required = false, value = "X-ALLETS-TOKEN") String xAlletsToken,
            @RequestHeader(required = false, value = "X-ALLETS-CHANNEL") String xAlletsChannel,
            @RequestHeader(required = false, value = "X-ALLETS-VERSION") String xAlletsVersion,
            @RequestHeader(required = false, value = "X-ALLETS-TRACEUUID") String xAlletsTraceuuid
    ) throws Exception {
        return commentFacade.findBestCommentCount(Long.valueOf(uid));
    }


    @ApiVersion(since = "1.0")
    @ApiAuthBasic(roles = {})
    @ApiMethod(
            path = "/v1.0/users/{uid}/totalReportCount",
            verb = ApiVerb.GET,
            description = "get total reported count of user",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @RequiresAuthentication
    @ResponseBody
    @RequestMapping(value = "/v1.0" + "/users/{uid}/totalReportCount", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ApiResponseObject
    Integer getTotalReportCount(
            @ApiParam(name = "uid",
                    description = "uid",
                    required = true,
                    paramType = ApiParamType.PATH) @PathVariable(value = "uid") String uid,
            @RequestHeader(required = false, value = "X-ALLETS-LANG") String xAlletsLang,
            @RequestHeader(required = false, value = "X-ALLETS-COUNTRY") String xAlletsContry,
            @RequestHeader(required = false, value = "X-ALLETS-TOKEN") String xAlletsToken,
            @RequestHeader(required = false, value = "X-ALLETS-CHANNEL") String xAlletsChannel,
            @RequestHeader(required = false, value = "X-ALLETS-VERSION") String xAlletsVersion,
            @RequestHeader(required = false, value = "X-ALLETS-TRACEUUID") String xAlletsTraceuuid
    ) throws Exception {
        return userFacade.findTotalReportCount(Long.valueOf(uid));
    }

    @ApiVersion(since = "1.0")
    @ApiAuthBasic(roles = {})
    @ApiMethod(
            path = "/v1.0/users/{uid}/alert",
            verb = ApiVerb.PUT,
            description = "send alert to user",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @RequiresAuthentication
    @RequestMapping(value = "/v1.0" + "/users/{uid}/alert", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ApiResponseObject
    AlertResult putAlert(
            @ApiParam(name = "uid",
                    description = "uid",
                    required = true,
                    paramType = ApiParamType.PATH) @PathVariable(value = "uid") String uid,
            @ApiParam(name = "alertType",
                    description = "alertType",
                    required = false,
                    paramType = ApiParamType.QUERY) @RequestParam(value = "alertType", required = true) String alertType,
            @RequestHeader(required = false, value = "X-ALLETS-LANG") String xAlletsLang,
            @RequestHeader(required = false, value = "X-ALLETS-COUNTRY") String xAlletsContry,
            @RequestHeader(required = false, value = "X-ALLETS-TOKEN") String xAlletsToken,
            @RequestHeader(required = false, value = "X-ALLETS-CHANNEL") String xAlletsChannel,
            @RequestHeader(required = false, value = "X-ALLETS-VERSION") String xAlletsVersion,
            @RequestHeader(required = false, value = "X-ALLETS-TRACEUUID") String xAlletsTraceuuid
    ) throws Exception {
        MonitorDTO monitorDTO = securityFacade.getAuthenticatedMonitor();
        if (monitorDTO == null) {
            throw new NotFoundUserException();
        }
        return userFacade.editAlertCount(monitorDTO.getMonitorId(), Long.valueOf(uid), alertType);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/v1.2" + "/users/{uid}/handleHistory", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ApiResponseObject
    void getAccountHandleHistoryByUid(
            @ApiBodyObject Model model,
            @ApiParam(name = "uid",
                    description = "uid",
                    required = true,
                    paramType = ApiParamType.PATH) @PathVariable(value = "uid") String uid,
            @RequestHeader(required = false, value = "X-ALLETS-LANG") String xAlletsLang,
            @RequestHeader(required = false, value = "X-ALLETS-COUNTRY") String xAlletsContry,
            @RequestHeader(required = false, value = "X-ALLETS-TOKEN") String xAlletsToken,
            @RequestHeader(required = false, value = "X-ALLETS-CHANNEL") String xAlletsChannel,
            @RequestHeader(required = false, value = "X-ALLETS-VERSION") String xAlletsVersion,
            @RequestHeader(required = false, value = "X-ALLETS-TRACEUUID") String xAlletsTraceuuid
    ) throws Exception {
        MonitorDTO monitorDTO = securityFacade.getAuthenticatedMonitor();
        if (monitorDTO == null) {
            throw new NotFoundUserException();
        }
        model.addAttribute(userFacade.findUserHandleHistory(Long.valueOf(uid)));
    }

    @RequiresAuthentication
    @RequestMapping(value = "/v1.2" + "/users/{uid}/loginType", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ApiResponseObject
    void getUserLoginType(@ApiBodyObject Model model,
                          @ApiParam(name = "uid",
                                  description = "uid",
                                  required = true,
                                  paramType = ApiParamType.PATH) @PathVariable(value = "uid") String uid,
                          @RequestHeader(required = false, value = "X-ALLETS-LANG") String xAlletsLang,
                          @RequestHeader(required = false, value = "X-ALLETS-COUNTRY") String xAlletsContry,
                          @RequestHeader(required = false, value = "X-ALLETS-TOKEN") String xAlletsToken,
                          @RequestHeader(required = false, value = "X-ALLETS-CHANNEL") String xAlletsChannel,
                          @RequestHeader(required = false, value = "X-ALLETS-VERSION") String xAlletsVersion,
                          @RequestHeader(required = false, value = "X-ALLETS-TRACEUUID") String xAlletsTraceuuid)
            throws Exception {
        MonitorDTO monitorDTO = securityFacade.getAuthenticatedMonitor();
        if (monitorDTO == null) {
            throw new NotFoundUserException();
        }
        model.addAttribute("loginType", userFacade.findUserLoginType(Long.valueOf(uid)));

    }
}
