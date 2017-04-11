package com.allets.backend.data.server.data.result;

import com.allets.backend.data.server.entity.common.CommentHandleRule;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import java.util.List;

/**
 * Created by jack on 2015/9/10.
 */
@ApiObject(name = "CommentHandleRuleResult", description = "comment handle rules list")
public class CommentHandleRuleResult {

    @ApiObjectField(description = "comment handle rules list")
    private List<CommentHandleRule> commentHandleRules;

    public List<CommentHandleRule> getCommentHandleRules() {
        return commentHandleRules;
    }

    public void setCommentHandleRules(List<CommentHandleRule> commentHandleRules) {
        this.commentHandleRules = commentHandleRules;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CommentHandleRuleResult{");
        sb.append("commentHandleRules=").append(commentHandleRules);
        sb.append('}');
        return sb.toString();
    }
}
