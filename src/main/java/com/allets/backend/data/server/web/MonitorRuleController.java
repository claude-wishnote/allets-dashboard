package com.allets.backend.data.server.web;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by claude on 2015/8/19.
 */
@Controller
@RequestMapping()
public class MonitorRuleController {
    /**
     * The log.
     */
    final Logger log = LoggerFactory.getLogger(MonitorController.class);

    @RequiresAuthentication
    @RequestMapping(value = "/v1.0" + "/monitorRules", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void addMonitorRule(Model model, @RequestHeader(required = false, value = "X-ALLETS-LANG") String xAlletsLang,
                               @RequestHeader(required = false, value = "X-ALLETS-COUNTRY") String xAlletsContry,
                               @RequestHeader(required = false, value = "X-ALLETS-TOKEN") String xAlletsToken,
                               @RequestHeader(required = false, value = "X-ALLETS-CHANNEL") String xAlletsChannel,
                               @RequestHeader(required = false, value = "X-ALLETS-VERSION") String xAlletsVersion,
                               @RequestHeader(required = false, value = "X-ALLETS-TRACEUUID") String xAlletsTraceuuid
    ) {
        model.addAttribute("success", "addMonitorRule");
    }

    @RequiresAuthentication
    @RequestMapping(value = "/v1.0" + "/monitorRules/{monitorRuleId}", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void editMonitorRule(Model model,
                                @PathVariable(value = "monitorRuleId") Long monitorRuleId,
                                @RequestHeader(required = false, value = "X-ALLETS-LANG") String xAlletsLang,
                                @RequestHeader(required = false, value = "X-ALLETS-COUNTRY") String xAlletsContry,
                                @RequestHeader(required = false, value = "X-ALLETS-TOKEN") String xAlletsToken,
                                @RequestHeader(required = false, value = "X-ALLETS-CHANNEL") String xAlletsChannel,
                                @RequestHeader(required = false, value = "X-ALLETS-VERSION") String xAlletsVersion,
                                @RequestHeader(required = false, value = "X-ALLETS-TRACEUUID") String xAlletsTraceuuid
    ) {
        model.addAttribute("success", "editMonitorRule");
        model.addAttribute(monitorRuleId);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/v1.0" + "/monitorRules", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void getMonitorRules(Model model, @RequestHeader(required = false, value = "X-ALLETS-LANG") String xAlletsLang,
                                @RequestHeader(required = false, value = "X-ALLETS-COUNTRY") String xAlletsContry,
                                @RequestHeader(required = false, value = "X-ALLETS-TOKEN") String xAlletsToken,
                                @RequestHeader(required = false, value = "X-ALLETS-CHANNEL") String xAlletsChannel,
                                @RequestHeader(required = false, value = "X-ALLETS-VERSION") String xAlletsVersion,
                                @RequestHeader(required = false, value = "X-ALLETS-TRACEUUID") String xAlletsTraceuuid
    ) {
        model.addAttribute("success", "getMonitorRules");
    }

    @RequiresAuthentication
    @RequestMapping(value = "/v1.0" + "/monitorRules/{monitorRuleId}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteMonitorRule(Model model,
                                  @PathVariable(value = "monitorRuleId") Long monitorRuleId,
                                  @RequestHeader(required = false, value = "X-ALLETS-LANG") String xAlletsLang,
                                  @RequestHeader(required = false, value = "X-ALLETS-COUNTRY") String xAlletsContry,
                                  @RequestHeader(required = false, value = "X-ALLETS-TOKEN") String xAlletsToken,
                                  @RequestHeader(required = false, value = "X-ALLETS-CHANNEL") String xAlletsChannel,
                                  @RequestHeader(required = false, value = "X-ALLETS-VERSION") String xAlletsVersion,
                                  @RequestHeader(required = false, value = "X-ALLETS-TRACEUUID") String xAlletsTraceuuid
    ) {
        model.addAttribute("success", "deleteMonitorRule");
        model.addAttribute(monitorRuleId);
    }

}
