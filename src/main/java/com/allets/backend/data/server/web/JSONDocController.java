package com.allets.backend.data.server.web;


import com.allets.backend.data.server.JSONDoc.JSONDocProperty;
import com.allets.backend.data.server.facade.SecurityFacade;
import com.google.gson.Gson;
import com.allets.backend.data.server.data.dto.MonitorDTO;
import org.jsondoc.core.util.JSONDocUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/jsondoc")
public class JSONDocController {

    @Autowired
    JSONDocProperty jsonDocProperty;
    @Autowired
    SecurityFacade securityFacade;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    String getApi(@RequestParam String password) {
        if (jsonDocProperty.isJsondocSwitch()) {
            if ("pikidevops".equals(password)) {
                return new Gson().toJson(JSONDocUtils.getApiDoc(jsonDocProperty.getVersion(), jsonDocProperty.getBasePath(), jsonDocProperty.getPackages()));
            } else {
                return "permission denied";
            }
        } else {
            return null;
        }
    }

    @RequestMapping(value = "docs", method = RequestMethod.GET)
    public ModelAndView getDocs() throws Exception {
        MonitorDTO monitorDTO = securityFacade.getAuthenticatedMonitor();
        if (monitorDTO == null) {
            return new ModelAndView("/ums/login");
        }
        if (jsonDocProperty.isJsondocSwitch()) {
            Map model = new HashMap();
            model.put("docsUrl", jsonDocProperty.getBasePath() + "jsondoc");
            return new ModelAndView("/ums/jsondoc", model);
        } else {
            return null;
        }
    }
}