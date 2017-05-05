package com.allets.backend.dashboard.server.web;

import com.allets.backend.dashboard.server.data.result.UserResult;
import com.allets.backend.dashboard.server.facade.SecurityFacade;
import com.allets.backend.dashboard.server.facade.UserFacade;
import com.allets.backend.dashboard.server.data.dto.MonitorDTO;
import com.allets.backend.dashboard.server.exception.NotFoundUserException;
import com.allets.backend.dashboard.server.utils.MessageUtil;
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
//        if (Const.Action.REPORTED.equals(action) || Const.Action.HANDLED.equals(action)) {
//            if (pageResult != null) {
//                for (UserResult ur : pageResult.getContent()) {
//                    String reportMessage = getReportMessage(action, ur.getReportType());
//                    ur.setReportTypeMessage(reportMessage);
//                }
//            }
//        }
        model.addAttribute(pageResult);

    }

    @RequiresAuthentication
    @RequestMapping(value = "/v1.2" + "/users/statics", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void getUserStaticsResult(Model model,
                                     @RequestHeader(required = false, value = "X-ALLETS-LANG") String xAlletsLang,
                                     @RequestHeader(required = false, value = "X-ALLETS-COUNTRY") String xAlletsContry,
                                     @RequestHeader(required = false, value = "X-ALLETS-TOKEN") String xAlletsToken,
                                     @RequestHeader(required = false, value = "X-ALLETS-CHANNEL") String xAlletsChannel,
                                     @RequestHeader(required = false, value = "X-ALLETS-VERSION") String xAlletsVersion,
                                     @RequestHeader(required = false, value = "X-ALLETS-TRACEUUID") String xAlletsTraceuuid,
                                     @RequestParam(value = "", required = false) String q)
            throws Exception {
        MonitorDTO monitorDTO = securityFacade.getAuthenticatedMonitor();
        if (monitorDTO == null) {
            throw new NotFoundUserException();
        }
        model.addAttribute("userSatisticsResult", userFacade.findUserStatisticsResult(q));
    }
}
