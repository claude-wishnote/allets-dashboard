package com.allets.backend.data.server.web;

import com.allets.backend.data.server.exception.NotFoundUserException;
import com.allets.backend.data.server.data.result.MgCardResult;
import com.allets.backend.data.server.facade.SecurityFacade;
import com.allets.backend.data.server.data.dto.MonitorDTO;
import com.allets.backend.data.server.facade.MgCardFacade;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.jsondoc.core.annotation.*;
import org.jsondoc.core.pojo.ApiParamType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by claude on 2016/1/24.
 */
@Api(name = "MgCardController", description = "MgCard Controller")
@ApiVersion(since = "1.2")
@Controller
@RequestMapping()
public class MgCardController {
    final Logger log = LoggerFactory.getLogger(MgCardController.class);

    @Autowired
    MgCardFacade mgCardFacade;
    @Autowired
    SecurityFacade securityFacade;

    @RequiresAuthentication
    @RequestMapping(value = "/v1.2" + "/mgCards", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ApiResponseObject
    void getMgCards(@ApiBodyObject Model model,
                    @ApiParam(name = "q",
                            description = "ex:q=contentsId=1",
                            required = false,
                            paramType = ApiParamType.QUERY) @RequestParam(value = "q", required = false) String q)throws Exception{
        MonitorDTO monitorDTO = securityFacade.getAuthenticatedMonitor();
        if (monitorDTO == null) {
            throw new NotFoundUserException();
        }
        List<MgCardResult> list = mgCardFacade.findMgCards(q);
        model.addAttribute(list);

    }
}
