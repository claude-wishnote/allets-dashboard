package com.allets.backend.data.server.web;

import com.allets.backend.data.server.data.dto.MonitorDTO;
import com.allets.backend.data.server.exception.NotFoundUserException;
import com.allets.backend.data.server.facade.MgContentsFacade;
import com.allets.backend.data.server.facade.SecurityFacade;
import com.allets.backend.data.server.data.result.MgContentsResult;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.jsondoc.core.annotation.*;
import org.jsondoc.core.pojo.ApiParamType;
import org.jsondoc.core.pojo.ApiVerb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by claude on 2016/1/24.
 */
@Api(name = "MgContentsController", description = "MgContents Controller")
@ApiVersion(since = "1.2")
@Controller
@RequestMapping()
public class MgContentsController {
    final Logger log = LoggerFactory.getLogger(MgContentsController.class);

    @Autowired
    MgContentsFacade mgContentsFacade;
    @Autowired
    SecurityFacade securityFacade;

    @ApiVersion(since = "1.2")
    @ApiAuthBasic(roles = {})
    @ApiMethod(
            path = "/v1.2/mgContents",
            verb = ApiVerb.GET,
            description = "get mgContents list",
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
    @RequestMapping(value = "/v1.2" + "/mgContents", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ApiResponseObject
    void getMgContents(@ApiBodyObject Model model,
                       @ApiParam(name = "q",
                               description = "ex:q=title=piki",
                               required = false,
                               paramType = ApiParamType.QUERY) @RequestParam(value = "q", required = false) String q,
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
        MonitorDTO monitorDTO = securityFacade.getAuthenticatedMonitor();
        if (monitorDTO == null) {
            throw new NotFoundUserException();
        }
        PageImpl<MgContentsResult> list = mgContentsFacade.findMgContents(q,Integer.valueOf(offset), Integer.valueOf(limit));
        model.addAttribute(list);
    }
}
