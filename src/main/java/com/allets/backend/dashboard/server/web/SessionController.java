package com.allets.backend.dashboard.server.web;

/**
 * Created by claude on 2015/8/19.
 */

import com.allets.backend.dashboard.server.facade.MonitorFacade;
import com.allets.backend.dashboard.server.facade.SecurityFacade;
import com.allets.backend.dashboard.server.security.ShiroUtils;
import com.allets.backend.dashboard.server.data.dto.MonitorDTO;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(name = "SessionController", description = "login")
@ApiVersion(since = "1.0")
@Controller
@RequestMapping()
public class SessionController {

    /**
     * The log.
     */
    final Logger log = LoggerFactory.getLogger(SessionController.class);

    @Autowired
    SecurityFacade securityFacade;
    @Autowired
    MonitorFacade monitorFacade;
    /*
        login
     */
    @ApiVersion(since = "1.0")
    @ApiAuthBasic(roles = {})
    @ApiMethod(
            path = "/v1.0/sessions",
            verb = ApiVerb.POST,
            description = "login",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @RequestMapping(value = "/v1.0" + "/sessions", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public
    @ApiResponseObject
    MonitorDTO login(@ApiBodyObject Model model,
                     @ApiParam(name = "name",
                             description = "name",
                             required = true,
                             paramType = ApiParamType.QUERY) @RequestParam(value = "name", required = true) String name,
                     @ApiParam(name = "password",
                             description = "password",
                             required = true,
                             paramType = ApiParamType.QUERY) @RequestParam(value = "password", required = true) String password,
                     @RequestHeader(required = false, value = "X-ALLETS-LANG") String xAlletsLang,
                     @RequestHeader(required = false, value = "X-ALLETS-COUNTRY") String xAlletsContry,
                     @RequestHeader(required = false, value = "X-ALLETS-TOKEN") String xAlletsToken,
                     @RequestHeader(required = false, value = "X-ALLETS-CHANNEL") String xAlletsChannel,
                     @RequestHeader(required = false, value = "X-ALLETS-VERSION") String xAlletsVersion,
                     @RequestHeader(required = false, value = "X-ALLETS-TRACEUUID") String xAlletsTraceuuid,
                     HttpServletRequest request,
                     HttpServletResponse response
    ) throws Exception {
        ShiroUtils.login(response, name, password);
        MonitorDTO monitorDTO = securityFacade.getAuthenticatedMonitor();
        if(monitorDTO.getMonitorId()!= null){
            monitorFacade.modifyMonitorLastLoginTime(monitorDTO.getMonitorId());
        }
//      model.addAttribute(monitorDTO);
        return monitorDTO;
    }

    @RequiresAuthentication
    @RequestMapping(value = "/v1.0" + "/sessions", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void logout(Model model) {
        model.addAttribute("success", "logout");
    }
}
