package com.allets.backend.data.server.web;

import com.allets.backend.data.server.facade.CommentHandleRuleFacade;
import com.allets.backend.data.server.entity.common.CommentHandleRule;
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
 * Created by claude on 2015/8/18.
 */
@Api(name = "CommentHandleRuleController", description = "comment handle rule controller")
@ApiVersion(since = "1.0")
@Controller
@RequestMapping()
public class CommentHandleRuleController {

    /**
     * The log.
     */
    final Logger log = LoggerFactory.getLogger(CommentHandleRuleController.class);

    @Autowired
    CommentHandleRuleFacade commentHandleRuleFacade;


    /**
     * @param model
     * @throws Exception
     */
    @ApiVersion(since = "1.0")
    @ApiAuthBasic(roles = {})
    @ApiMethod(
            path = "/v1.0/commentHandleRules",
            verb = ApiVerb.GET,
            description = "get comment handle rules",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @RequiresAuthentication
    @RequestMapping(value = "/v1.0" + "/commentHandleRules", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ApiResponseObject
    void listAllCommentHandleRules(@ApiBodyObject Model model) throws Exception {
        List<CommentHandleRule> parentRules = commentHandleRuleFacade.findAllByParentRuleIdIsNull();
        if (parentRules != null) {
            for (CommentHandleRule commentHandleRule : parentRules) {
                commentHandleRule.setCommentHandleRules(commentHandleRuleFacade.findAllByParentRuleIdOrderByLevelAsc(commentHandleRule.getRuleId()));
            }
        }
        model.addAttribute(parentRules);
    }


    @ApiVersion(since = "1.0")
    @ApiAuthBasic(roles = {})
    @ApiMethod(
            path = "/v1.0/commentHandleRules/{ruleId}",
            verb = ApiVerb.DELETE,
            description = "delete comment handle rules",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @RequiresAuthentication
    @RequestMapping(value = "/v1.0" + "/commentHandleRules/{ruleId}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteCommmentHandleRule(@ApiBodyObject Model model,
                                         @ApiParam(name = "ruleId",
                                                 description = "ruleId",
                                                 required = true,
                                                 paramType = ApiParamType.PATH) @PathVariable(value = "ruleId") Integer ruleId,
                                         @RequestHeader(required = false, value = "X-ALLETS-LANG") String xAlletsLang,
                                         @RequestHeader(required = false, value = "X-ALLETS-COUNTRY") String xAlletsContry,
                                         @RequestHeader(required = false, value = "X-ALLETS-TOKEN") String xAlletsToken,
                                         @RequestHeader(required = false, value = "X-ALLETS-CHANNEL") String xAlletsChannel,
                                         @RequestHeader(required = false, value = "X-ALLETS-VERSION") String xAlletsVersion,
                                         @RequestHeader(required = false, value = "X-ALLETS-TRACEUUID") String xAlletsTraceuuid
    ) throws Exception {
        commentHandleRuleFacade.removeCommentHandleRule(ruleId);
        model.addAttribute("success");
    }

    @ApiVersion(since = "1.0")
    @ApiAuthBasic(roles = {})
    @ApiMethod(
            path = "/v1.0/commentHandleRules",
            verb = ApiVerb.POST,
            description = "add comment handle rules",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @RequiresAuthentication
    @RequestMapping(value = "/v1.0" + "/commentHandleRules", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void addCommmentHandleRule(@ApiBodyObject Model model,
                                      @ApiParam(name = "parentRuleId",
                                              description = "parent ruleId",
                                              required = true,
                                              paramType = ApiParamType.QUERY) @RequestParam(value = "parentRuleId", required = true) Integer parentRuleId,
                                      @ApiParam(name = "text",
                                              description = "rule text",
                                              required = true,
                                              paramType = ApiParamType.QUERY) @RequestParam(value = "text", required = true) String text,
                                      @ApiParam(name = "level",
                                              description = "rule level ",
                                              required = true,
                                              paramType = ApiParamType.QUERY) @RequestParam(value = "level", required = true) String level,
                                      @RequestHeader(required = false, value = "X-ALLETS-LANG") String xAlletsLang,
                                      @RequestHeader(required = false, value = "X-ALLETS-COUNTRY") String xAlletsContry,
                                      @RequestHeader(required = false, value = "X-ALLETS-TOKEN") String xAlletsToken,
                                      @RequestHeader(required = false, value = "X-ALLETS-CHANNEL") String xAlletsChannel,
                                      @RequestHeader(required = false, value = "X-ALLETS-VERSION") String xAlletsVersion,
                                      @RequestHeader(required = false, value = "X-ALLETS-TRACEUUID") String xAlletsTraceuuid
    ) throws Exception {
        model.addAttribute(commentHandleRuleFacade.createCommentHandleRule(parentRuleId, text, level));
    }

    @ApiVersion(since = "1.0")
    @ApiAuthBasic(roles = {})
    @ApiMethod(
            path = "/v1.0/commentHandleRules/{ruleId}",
            verb = ApiVerb.PUT,
            description = "edit comment handle rules",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @RequiresAuthentication
    @RequestMapping(value = "/v1.0" + "/commentHandleRules/{ruleId}", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void editCommmentHandleRule(Model model,
                                       @ApiParam(name = "ruleId",
                                               description = "ruleId",
                                               required = true,
                                               paramType = ApiParamType.QUERY) @PathVariable(value = "ruleId") Integer ruleId,
                                       @ApiParam(name = "parentRuleId",
                                               description = "parent ruleId",
                                               required = true,
                                               paramType = ApiParamType.QUERY) @RequestParam(value = "parentRuleId", required = true) Integer parentRuleId,
                                       @ApiParam(name = "text",
                                               description = "rule text",
                                               required = true,
                                               paramType = ApiParamType.QUERY) @RequestParam(value = "text", required = true) String text,
                                       @ApiParam(name = "level",
                                               description = "rule level",
                                               required = true,
                                               paramType = ApiParamType.QUERY) @RequestParam(value = "level", required = true) String level,
                                       @RequestHeader(required = false, value = "X-ALLETS-LANG") String xAlletsLang,
                                       @RequestHeader(required = false, value = "X-ALLETS-COUNTRY") String xAlletsContry,
                                       @RequestHeader(required = false, value = "X-ALLETS-TOKEN") String xAlletsToken,
                                       @RequestHeader(required = false, value = "X-ALLETS-CHANNEL") String xAlletsChannel,
                                       @RequestHeader(required = false, value = "X-ALLETS-VERSION") String xAlletsVersion,
                                       @RequestHeader(required = false, value = "X-ALLETS-TRACEUUID") String xAlletsTraceuuid
    ) throws Exception {
        model.addAttribute(commentHandleRuleFacade.modifyCommentHandleRule(ruleId, parentRuleId, text, level));
    }
}
