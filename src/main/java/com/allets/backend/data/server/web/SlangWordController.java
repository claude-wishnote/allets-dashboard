package com.allets.backend.data.server.web;

import com.allets.backend.data.server.entity.common.SlangWord;
import com.allets.backend.data.server.facade.ProhibitionFacade;
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

import java.util.List;

/**
 * Created by jack on 2015/9/1.
 */
@Api(name = "SlangWordController", description = "slangword controller")
@ApiVersion(since = "1.0")
@Controller
@RequestMapping()
public class SlangWordController {

    final Logger log = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    ProhibitionFacade prohibitionFacade;

    @ApiVersion(since = "1.0")
    @ApiAuthBasic(roles = {})
    @ApiMethod(
            path = "/v1.0/slangs",
            verb = ApiVerb.GET,
            description = "get slang words list",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @RequiresAuthentication
    @RequestMapping(value = "/v1.0" + "/slangs", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ApiResponseObject
    void getAllSlangWords(@ApiBodyObject Model model,
                          @ApiParam(name = "field",
                                  description = "field",
                                  required = false,
                                  paramType = ApiParamType.QUERY) @RequestParam(value = "field", required = false) String field,
                          @RequestHeader(required = false, value = "X-ALLETS-LANG") String xAlletsLang,
                          @RequestHeader(required = false, value = "X-ALLETS-COUNTRY") String xAlletsContry,
                          @RequestHeader(required = false, value = "X-ALLETS-TOKEN") String xAlletsToken,
                          @RequestHeader(required = false, value = "X-ALLETS-CHANNEL") String xAlletsChannel,
                          @RequestHeader(required = false, value = "X-ALLETS-VERSION") String xAlletsVersion,
                          @RequestHeader(required = false, value = "X-ALLETS-TRACEUUID") String xAlletsTraceuuid
    ) throws Exception {
        List<SlangWord> results = prohibitionFacade.findSlangWordAll(field);
        model.addAttribute("results", results);
    }

    @ApiVersion(since = "1.0")
    @ApiAuthBasic(roles = {})
    @ApiMethod(
            path = "/v1.0/slangs",
            verb = ApiVerb.POST,
            description = "add slang",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @RequiresAuthentication
    @RequestMapping(value = "/v1.0" + "/slangs", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ApiResponseObject
    void addingSalngWord(@ApiBodyObject Model model,
                         @ApiParam(name = "slang",
                                 description = "slang word text",
                                 required = true,
                                 paramType = ApiParamType.QUERY) @RequestParam(value = "slang", required = true) String slang,
                         @ApiParam(name = "type",
                                 description = "slang word type",
                                 required = true,
                                 paramType = ApiParamType.QUERY) @RequestParam(value = "type", required = true) String type,
                         @RequestHeader(required = false, value = "X-ALLETS-LANG") String xAlletsLang,
                         @RequestHeader(required = false, value = "X-ALLETS-COUNTRY") String xAlletsContry,
                         @RequestHeader(required = false, value = "X-ALLETS-TOKEN") String xAlletsToken,
                         @RequestHeader(required = false, value = "X-ALLETS-CHANNEL") String xAlletsChannel,
                         @RequestHeader(required = false, value = "X-ALLETS-VERSION") String xAlletsVersion,
                         @RequestHeader(required = false, value = "X-ALLETS-TRACEUUID") String xAlletsTraceuuid
    ) throws Exception {
        SlangWord slangWord = new SlangWord();
        slangWord.setSlang(slang);
        slangWord.setType(type);
        model.addAttribute("results", prohibitionFacade.createSlangWord(slangWord));
    }

    @ApiVersion(since = "1.0")
    @ApiAuthBasic(roles = {})
    @ApiMethod(
            path = "/v1.0/slangs/{slangId}",
            verb = ApiVerb.PUT,
            description = "edit slang",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @RequiresAuthentication
    @RequestMapping(value = "/v1.0" + "/slangs/{slangId}", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ApiResponseObject
    void editingSalngWord(@ApiBodyObject Model model,
                          @ApiParam(name = "slangId",
                                  description = "slangId",
                                  required = true,
                                  paramType = ApiParamType.PATH) @PathVariable(value = "slangId") Integer slangId,
                          @ApiParam(name = "slang",
                                  description = "slang words text",
                                  required = false,
                                  paramType = ApiParamType.PATH) @RequestParam(value = "slang", required = false) String slang,
                          @ApiParam(name = "type",
                                  description = "slang words type",
                                  required = false,
                                  paramType = ApiParamType.PATH) @RequestParam(value = "type", required = false) String type,
                          @RequestHeader(required = false, value = "X-ALLETS-LANG") String xAlletsLang,
                          @RequestHeader(required = false, value = "X-ALLETS-COUNTRY") String xAlletsContry,
                          @RequestHeader(required = false, value = "X-ALLETS-TOKEN") String xAlletsToken,
                          @RequestHeader(required = false, value = "X-ALLETS-CHANNEL") String xAlletsChannel,
                          @RequestHeader(required = false, value = "X-ALLETS-VERSION") String xAlletsVersion,
                          @RequestHeader(required = false, value = "X-ALLETS-TRACEUUID") String xAlletsTraceuuid
    ) throws Exception {
        SlangWord slangWord = new SlangWord();
        slangWord.setSlang(slang);
        slangWord.setType(type);
        slangWord.setSlangId(slangId);
        prohibitionFacade.modifySlangWord(slangWord);
        model.addAttribute("results", "success");
    }

    @ApiVersion(since = "1.0")
    @ApiAuthBasic(roles = {})
    @ApiMethod(
            path = "/v1.0/slangs/{slangId}",
            verb = ApiVerb.DELETE,
            description = "delete slang",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @RequiresAuthentication
    @RequestMapping(value = "/v1.0" + "/slangs/{slangId}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ApiResponseObject
    void deletingSalngWord(@ApiBodyObject Model model,
                           @ApiParam(name = "slangId",
                                   description = "slangId",
                                   required = true,
                                   paramType = ApiParamType.PATH) @PathVariable(value = "slangId") Integer slangId,
                           @RequestHeader(required = false, value = "X-ALLETS-LANG") String xAlletsLang,
                           @RequestHeader(required = false, value = "X-ALLETS-COUNTRY") String xAlletsContry,
                           @RequestHeader(required = false, value = "X-ALLETS-TOKEN") String xAlletsToken,
                           @RequestHeader(required = false, value = "X-ALLETS-CHANNEL") String xAlletsChannel,
                           @RequestHeader(required = false, value = "X-ALLETS-VERSION") String xAlletsVersion,
                           @RequestHeader(required = false, value = "X-ALLETS-TRACEUUID") String xAlletsTraceuuid
    ) throws Exception {
        prohibitionFacade.removeSlangWord(slangId);
        model.addAttribute("results", "success");
    }

}
