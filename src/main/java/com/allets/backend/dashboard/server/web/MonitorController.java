package com.allets.backend.dashboard.server.web;

import com.allets.backend.dashboard.server.consts.Status;
import com.allets.backend.dashboard.server.facade.MonitorFacade;
import com.allets.backend.dashboard.server.facade.SecurityFacade;
import com.allets.backend.dashboard.server.utils.HttpStatusCode;
import com.allets.backend.dashboard.server.utils.HttpStatusMessage;
import com.allets.backend.dashboard.server.consts.Const;
import com.allets.backend.dashboard.server.consts.Level;
import com.allets.backend.dashboard.server.data.dto.MonitorDTO;
import com.allets.backend.dashboard.server.entity.common.Monitor;
import com.allets.backend.dashboard.server.exception.FailureException;
import com.allets.backend.dashboard.server.utils.EncryptUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.jsondoc.core.annotation.*;
import org.jsondoc.core.pojo.ApiParamType;
import org.jsondoc.core.pojo.ApiVerb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by claude on 2015/8/19.
 */
@Api(name = "MonitorController", description = "monitor controller")
@ApiVersion(since = "1.0")
@Controller
@RequestMapping()
public class MonitorController {
    /**
     * The log.
     */
    final Logger log = LoggerFactory.getLogger(MonitorController.class);

    @Autowired
    SecurityFacade securityFacade;

    @Autowired
    MonitorFacade monitorFacade;

    @ApiVersion(since = "1.0")
    @ApiAuthBasic(roles = {})
    @ApiMethod(
            path = "/v1.0/monitors",
            verb = ApiVerb.GET,
            description = "get all monitors list",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @RequiresAuthentication
    @RequestMapping(value = "/v1.0" + "/monitors", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ApiResponseObject
    void getMonitors(@ApiBodyObject Model model,
                     @RequestHeader(required = false, value = "X-ALLETS-LANG") String xAlletsLang,
                     @RequestHeader(required = false, value = "X-ALLETS-COUNTRY") String xAlletsContry,
                     @RequestHeader(required = false, value = "X-ALLETS-TOKEN") String xAlletsToken,
                     @RequestHeader(required = false, value = "X-ALLETS-CHANNEL") String xAlletsChannel,
                     @RequestHeader(required = false, value = "X-ALLETS-VERSION") String xAlletsVersion,
                     @RequestHeader(required = false, value = "X-ALLETS-TRACEUUID") String xAlletsTraceuuid
    ) throws Exception {
//        MonitorDTO monitorDTO = securityFacade.getAuthenticatedMonitor();
//        if (!monitorDTO.getLevel().equals(Level.ADMIN)) {
//            throw new FailureException(HttpStatusCode.SC_403, HttpStatusMessage.SC_403);
//        }
        model.addAttribute("results", monitorFacade.findMonitorAll());
    }

    @ApiVersion(since = "1.0")
    @ApiAuthBasic(roles = {})
    @ApiMethod(
            path = "/v1.0/monitors",
            verb = ApiVerb.POST,
            description = "add a monitor",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @RequiresAuthentication
    @RequestMapping(value = "/v1.0" + "/monitors", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ApiResponseObject
    void addMonitor(@ApiBodyObject Model model,
                    @ApiParam(name = "name",
                            description = "name",
                            required = true,
                            paramType = ApiParamType.QUERY) @RequestParam(value = "name", required = true) String name,
                    @RequestHeader(required = false, value = "X-ALLETS-LANG") String xAlletsLang,
                    @RequestHeader(required = false, value = "X-ALLETS-COUNTRY") String xAlletsContry,
                    @RequestHeader(required = false, value = "X-ALLETS-TOKEN") String xAlletsToken,
                    @RequestHeader(required = false, value = "X-ALLETS-CHANNEL") String xAlletsChannel,
                    @RequestHeader(required = false, value = "X-ALLETS-VERSION") String xAlletsVersion,
                    @RequestHeader(required = false, value = "X-ALLETS-TRACEUUID") String xAlletsTraceuuid
    ) throws Exception {
        MonitorDTO monitorDTO = securityFacade.getAuthenticatedMonitor();
        if (!monitorDTO.getLevel().equals(Level.ADMIN)) {
            throw new FailureException(HttpStatusCode.SC_403, HttpStatusMessage.SC_403);
        }
        Monitor monitor = new Monitor();
        monitor.setName(name);
        monitor.setLevel(Level.NORMAL);
        monitor.setStatus(Status.MonitorStatus.ACTV);
        monitor.setUdate(new Date());
        monitor.setPassword(EncryptUtil.encryptMd5String(Const.DEFAULT_PASSWORD));
        model.addAttribute("results", monitorFacade.createMonitor(monitor));
    }

    @ApiVersion(since = "1.0")
    @ApiAuthBasic(roles = {})
    @ApiMethod(
            path = "/v1.0/monitors/{monitorId}",
            verb = ApiVerb.PUT,
            description = "edit a monitor",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @RequiresAuthentication
    @RequestMapping(value = "/v1.0" + "/monitors/{monitorId}", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ApiResponseObject
    void editMonitor(@ApiBodyObject Model model,
                     @ApiParam(name = "monitorId",
                             description = "monitorId",
                             required = true,
                             paramType = ApiParamType.PATH) @PathVariable(value = "monitorId") Long monitorId,
                     @ApiParam(name = "level",
                             description = "level",
                             required = false,
                             paramType = ApiParamType.QUERY) @RequestParam(value = "level", required = false) String level,
                     @ApiParam(name = "password",
                             description = "password",
                             required = false,
                             paramType = ApiParamType.QUERY) @RequestParam(value = "password", required = false) String password,
                     @ApiParam(name = "oldPassword",
                             description = "oldPassword",
                             required = false,
                             paramType = ApiParamType.QUERY) @RequestParam(value = "oldPassword", required = false) String oldPassword,
                     @RequestHeader(required = false, value = "X-ALLETS-LANG") String xAlletsLang,
                     @RequestHeader(required = false, value = "X-ALLETS-COUNTRY") String xAlletsContry,
                     @RequestHeader(required = false, value = "X-ALLETS-TOKEN") String xAlletsToken,
                     @RequestHeader(required = false, value = "X-ALLETS-CHANNEL") String xAlletsChannel,
                     @RequestHeader(required = false, value = "X-ALLETS-VERSION") String xAlletsVersion,
                     @RequestHeader(required = false, value = "X-ALLETS-TRACEUUID") String xAlletsTraceuuid
    ) throws Exception {
        Monitor monitor = new Monitor();
        monitor.setMonitorId(monitorId);
        monitor.setLevel(level);
        monitor.setPassword(password);
        monitor.setOldPassword(oldPassword);
        monitorFacade.modifyMonitor(monitor);
        model.addAttribute("results", "Success");
    }


    @ApiVersion(since = "1.0")
    @ApiAuthBasic(roles = {})
    @ApiMethod(
            path = "/v1.0/monitors/{monitorId}",
            verb = ApiVerb.DELETE,
            description = "delete a monitor",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @RequiresAuthentication
    @RequestMapping(value = "/v1.0" + "/monitors/{monitorId}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ApiResponseObject
    void deleteMonitor(@ApiBodyObject Model model,
                       @ApiParam(name = "monitorId",
                               description = "monitorId",
                               required = true,
                               paramType = ApiParamType.PATH) @PathVariable(value = "monitorId") Long monitorId,
                       @RequestHeader(required = false, value = "X-ALLETS-LANG") String xAlletsLang,
                       @RequestHeader(required = false, value = "X-ALLETS-COUNTRY") String xAlletsContry,
                       @RequestHeader(required = false, value = "X-ALLETS-TOKEN") String xAlletsToken,
                       @RequestHeader(required = false, value = "X-ALLETS-CHANNEL") String xAlletsChannel,
                       @RequestHeader(required = false, value = "X-ALLETS-VERSION") String xAlletsVersion,
                       @RequestHeader(required = false, value = "X-ALLETS-TRACEUUID") String xAlletsTraceuuid
    ) throws Exception {
        MonitorDTO monitorDTO = securityFacade.getAuthenticatedMonitor();
        if (!monitorDTO.getLevel().equals(Level.ADMIN)) {
            throw new FailureException(HttpStatusCode.SC_403, HttpStatusMessage.SC_403);
        }
        Monitor monitor = new Monitor();
        monitor.setStatus(Status.MonitorStatus.HOLD);
        monitor.setMonitorId(monitorId);
        monitorFacade.modifyMonitor(monitor);
        model.addAttribute("results", "Success");
    }

    @ApiVersion(since = "1.3")
    @ApiAuthBasic(roles = {})
    @ApiMethod(
            path = "/v1.3/monitors/password/{monitorId}",
            verb = ApiVerb.PUT,
            description = "force reset a monitor",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @RequiresAuthentication
    @RequestMapping(value = "/v1.3" + "/monitors/password/{monitorId}", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ApiResponseObject
    void editMonitor(@ApiBodyObject Model model,
                     @ApiParam(name = "monitorId",
                             description = "monitorId",
                             required = true,
                             paramType = ApiParamType.PATH) @PathVariable(value = "monitorId") Long monitorId,
                     @ApiParam(name = "password",
                             description = "password",
                             required = false,
                             paramType = ApiParamType.QUERY) @RequestParam(value = "password", required = false) String password,
                     @RequestHeader(required = false, value = "X-ALLETS-LANG") String xAlletsLang,
                     @RequestHeader(required = false, value = "X-ALLETS-COUNTRY") String xAlletsContry,
                     @RequestHeader(required = false, value = "X-ALLETS-TOKEN") String xAlletsToken,
                     @RequestHeader(required = false, value = "X-ALLETS-CHANNEL") String xAlletsChannel,
                     @RequestHeader(required = false, value = "X-ALLETS-VERSION") String xAlletsVersion,
                     @RequestHeader(required = false, value = "X-ALLETS-TRACEUUID") String xAlletsTraceuuid
    ) throws Exception {
        MonitorDTO opMonitor = securityFacade.getAuthenticatedMonitor();
        if (!opMonitor.getLevel().equals(Level.ADMIN)) {
            throw new FailureException(HttpStatusCode.SC_403, HttpStatusMessage.SC_403);
        }
        Monitor monitor = new Monitor();
        monitor.setMonitorId(monitorId);
        monitor.setPassword(password);
        monitorFacade.modifyMonitorForceResetPassword(monitor);

        model.addAttribute("results", "Success");
    }
}
