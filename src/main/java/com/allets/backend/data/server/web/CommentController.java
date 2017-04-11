package com.allets.backend.data.server.web;

import com.allets.backend.data.server.facade.CommentFacade;
import com.allets.backend.data.server.consts.Const;
import com.allets.backend.data.server.data.dto.MonitorDTO;
import com.allets.backend.data.server.data.result.CommentResult;
import com.allets.backend.data.server.exception.NotFoundUserException;
import com.allets.backend.data.server.exception.NotSupportActionException;
import com.allets.backend.data.server.facade.SecurityFacade;
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

/**
 * Created by claude on 2015/8/19.
 */

@Api(name = "CommentController", description = "comment controller.")
@ApiVersion(since = "1.0")
@Controller
@RequestMapping()
public class CommentController {

    /**
     * The log.
     */
    final Logger log = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    CommentFacade commentFacade;
    @Autowired
    SecurityFacade securityFacade;
    @Autowired
    private MessageUtil messageUtil;

    /*
        get  comments
     charater/ keyfield / due date
     */
    @ApiVersion(since = "1.0")
    @ApiAuthBasic(roles = {})
    @ApiMethod(
            path = "/v1.0/comments",
            verb = ApiVerb.GET,
            description = "get comments list",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ApiHeaders(headers = {
            @ApiHeader(name = "X-ALLETS-LANG", description = ""),
            @ApiHeader(name = "X-ALLETS-COUNTRY", description = ""),
            @ApiHeader(name = "X-ALLETS-TOKEN", description = ""),
            @ApiHeader(name = "X-ALLETS-CHANNEL", description = ""),
            @ApiHeader(name = "X-ALLETS-VERSION", description = ""),
            @ApiHeader(name = "X-ALLETS-TRACEUUID", description = "")
    })
//    @ApiErrors(apierrors={
//            @ApiError(code="U400001", description="Bad Request Exception")
//    })

    @RequiresAuthentication
    @RequestMapping(value = "/v1.0" + "/comments", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ApiResponseObject
    void getAllComments(@ApiBodyObject Model model,
                        @ApiParam(name = "action",
                                description = "comment action:\"REPORTED\",\"HANDLED\",\"ALL\",\"ALLSIMPLE\"",
                                required = false,
                                paramType = ApiParamType.QUERY) @RequestParam(value = "action", required = false) String action,
                        @ApiParam(name = "q",
                                description = "ex:q=nickName='claude'",
                                required = false,
                                paramType = ApiParamType.QUERY) @RequestParam(value = "q", required = false) String q,
                        @ApiParam(name = "sort",
                                description = "sort",
                                required = false,
                                paramType = ApiParamType.QUERY) @RequestParam(value = "sort", required = false) String sort,
                        @ApiParam(name = "offset",
                                description = "defaultValue = 0",
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
        Page<CommentResult> page = commentFacade.findCommentsList(action, Integer.valueOf(offset), Integer.valueOf(limit), q);

        if (page != null & page.getContent() != null) {
            for (CommentResult cr : page.getContent()) {
                String reportMessage = getReportMessage(action, cr.getReportType());
                cr.setReportTypeMessage(reportMessage);
            }
        }
        model.addAttribute(page);
    }

    /**
     * @param model
     * @param commentId
     * @param action
     * @param status
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
            path = "/v1.0/comments/{commentId}",
            verb = ApiVerb.PUT,
            description = "handle one comment",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @RequiresAuthentication
    @RequestMapping(value = "/v1.0" + "/comments/{commentId}", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ApiResponseObject
    void editComment(@ApiBodyObject Model model,
                     @ApiParam(name = "commentId",
                             description = "commentId",
                             required = true,
                             paramType = ApiParamType.PATH) @PathVariable(value = "commentId") Long commentId,
                     @ApiParam(name = "action",
                             description = "comment action:\"REPORTANDHANDLE\"",
                             required = false,
                             paramType = ApiParamType.QUERY) @RequestParam(value = "action", required = false) String action,
                     @ApiParam(name = "status",
                             description = "comment status:\"HIDD\",\"DEL\"",
                             required = false,
                             paramType = ApiParamType.QUERY) @RequestParam(value = "status", required = false) String status,
                     @ApiParam(name = "reportType",
                             description = "reportType",
                             required = false,
                             paramType = ApiParamType.QUERY) @RequestParam(value = "reportType", required = false) String reportType,
                     @ApiParam(name = "reportTypeText",
                             description = "reportTypeText",
                             required = false,
                             paramType = ApiParamType.QUERY) @RequestParam(value = "reportTypeText", required = false) String reportTypeText,
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

        if (Const.Action.REPORTANDHANDLE.equals(action)) {
            model.addAttribute(commentFacade.reportAndHandleComment("", status, String.valueOf(commentId), monitorId));
        }

    }

    /**
     * @param model
     * @param action
     * @param commentIds
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
            path = "/v1.0/comments",
            verb = ApiVerb.PUT,
            description = "handle more then one comments",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @RequiresAuthentication
    @RequestMapping(value = "/v1.0" + "/comments", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ApiResponseObject
    void editComments(@ApiBodyObject Model model,
                      @ApiParam(name = "action",
                              description = "comment action:\"REPORTANDHANDLE\",\"PASS\",\"DEL\",\"HIDD\",\"RES\"",
                              required = false,
                              paramType = ApiParamType.QUERY) @RequestParam(value = "action", required = false) String action,
                      @ApiParam(name = "status",
                              description = "comment status:\"when action is REPORTANDHANDLE,\"status may be[\"DEL\",\"HIDD\"].",
                              required = false,
                              paramType = ApiParamType.QUERY) @RequestParam(value = "status", required = false) String status,
                      @ApiParam(name = "reportType",
                              description = "reportType",
                              required = false,
                              paramType = ApiParamType.QUERY) @RequestParam(value = "reportType", required = false) String reportType,
                      @ApiParam(name = "commentIds",
                              description = "commentIds:xxx,xxx,xxx",
                              required = true,
                              paramType = ApiParamType.PATH) @RequestParam(value = "commentIds", required = true) String commentIds,
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
        if (Const.Action.REPORTANDHANDLE.equals(action)) {
            if (Const.Action.PASS.equals(status) || Const.Status.DEL.equals(status) || Const.Status.HIDD.equals(status)) {
                model.addAttribute("result", commentFacade.reportAndHandleComment(reportType, status, commentIds, monitorId));
            }
            model.addAttribute("status", status);
        } else if (Const.Action.PASS.equals(action) || Const.Action.HIDD.equals(action) || Const.Action.DEL.equals(action) || Const.Action.RES.equals(action)) {
            model.addAttribute("result", commentFacade.modifyReportedComment(action, commentIds, Long.valueOf(monitorId)));
            model.addAttribute("status", action);
        } else {
            throw new NotSupportActionException();
        }
        model.addAttribute("action", action);
        model.addAttribute("monitorName", monitorDTO.getName());
        model.addAttribute("commentIds", commentIds);

    }

    /**
     * get report message text by report type
     *
     * @param reportType
     * @return
     */
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


    @ApiVersion(since = "1.0")
    @ApiAuthBasic(roles = {})
    @ApiMethod(
            path = "/v1.0/comments/{commentId}",
            verb = ApiVerb.GET,
            description = "get comments context ty commentId",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @RequiresAuthentication
    @RequestMapping(value = "/v1.0" + "/comments/{commentId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ApiResponseObject
    void getCommentsContextByCommentId
            (@ApiBodyObject Model model,
             @ApiParam(name = "commentId",
                     description = "commentId",
                     required = true,
                     paramType = ApiParamType.PATH) @PathVariable(value = "commentId") Long commentId,
             @ApiParam(name = "contentId",
                     description = "contentId",
                     required = false,
                     paramType = ApiParamType.QUERY) @RequestParam(value = "contentId", required = false) String contentId,
             @ApiParam(name = "cardId",
                     description = "cardId",
                     required = false,
                     paramType = ApiParamType.QUERY) @RequestParam(value = "cardId", required = false) String cardId,
             @ApiParam(name = "parentCommentId",
                     description = "parentCommentId",
                     required = false,
                     paramType = ApiParamType.QUERY) @RequestParam(value = "parentCommentId", required = false) String parentCommentId,
             @RequestHeader(required = false, value = "X-ALLETS-LANG") String xAlletsLang,
             @RequestHeader(required = false, value = "X-ALLETS-COUNTRY") String xAlletsContry,
             @RequestHeader(required = false, value = "X-ALLETS-TOKEN") String xAlletsToken,
             @RequestHeader(required = false, value = "X-ALLETS-CHANNEL") String xAlletsChannel,
             @RequestHeader(required = false, value = "X-ALLETS-VERSION") String xAlletsVersion,
             @RequestHeader(required = false, value = "X-ALLETS-TRACEUUID") String xAlletsTraceuuid
            ) throws Exception {
        model.addAttribute(commentFacade.findCommentsContextByCommentId(commentId, contentId, cardId, parentCommentId));

    }

}
