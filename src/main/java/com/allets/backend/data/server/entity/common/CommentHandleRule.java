package com.allets.backend.data.server.entity.common;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by jack on 2015/9/10.
 */
@ApiObject(name = "CommentHandleRule", description = "comment handle rule")
@Entity
@Table(name = "COMMENT_HANDLE_RULE", schema = "", catalog = "allets_common")
public class CommentHandleRule {
    @ApiObjectField(description = "ruleId")
    private Integer ruleId;
    @ApiObjectField(description = "parentRuleId")
    private Integer parentRuleId;
    @ApiObjectField(description = "text")
    private String text;
    @ApiObjectField(description = "level: A B C")
    private String level;
    @ApiObjectField(description = "update date")
    private Date udate;
    @ApiObjectField(description = "create date")
    private Date cdate;
    @ApiObjectField(description = "sub rules list of this rule")
    private List<CommentHandleRule> commentHandleRules;

    @Id
    @GeneratedValue(strategy=IDENTITY)
    @Column(name = "rule_id", nullable = false, insertable = false, updatable = false)
    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    @Basic
    @Column(name = "parent_rule_id", nullable = true, insertable = true, updatable = true)
    public Integer getParentRuleId() {
        return parentRuleId;
    }

    public void setParentRuleId(Integer parentRuleId) {
        this.parentRuleId = parentRuleId;
    }

    @Basic
    @Column(name = "text", nullable = false, insertable = true, updatable = true, length = 45)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Basic
    @Column(name = "level", nullable = true, insertable = true, updatable = true, length = 45)
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Basic
    @Column(name = "udate", nullable = true, insertable = true, updatable = true)
    public Date getUdate() {
        return udate;
    }

    public void setUdate(Date udate) {
        this.udate = udate;
    }

    @Basic
    @Column(name = "cdate", nullable = false, insertable = true, updatable = true)
    public Date getCdate() {
        return cdate;
    }

    public void setCdate(Date cdate) {
        this.cdate = cdate;
    }

    @Transient
    public List<CommentHandleRule> getCommentHandleRules() {
        return commentHandleRules;
    }

    public void setCommentHandleRules(List<CommentHandleRule> commentHandleRules) {
        this.commentHandleRules = commentHandleRules;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommentHandleRule that = (CommentHandleRule) o;

        if (ruleId != null ? !ruleId.equals(that.ruleId) : that.ruleId != null) return false;
        if (parentRuleId != null ? !parentRuleId.equals(that.parentRuleId) : that.parentRuleId != null) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;
        if (level != null ? !level.equals(that.level) : that.level != null) return false;
        if (udate != null ? !udate.equals(that.udate) : that.udate != null) return false;
        if (cdate != null ? !cdate.equals(that.cdate) : that.cdate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ruleId != null ? ruleId.hashCode() : 0;
        result = 31 * result + (parentRuleId != null ? parentRuleId.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        result = 31 * result + (udate != null ? udate.hashCode() : 0);
        result = 31 * result + (cdate != null ? cdate.hashCode() : 0);
        return result;
    }
}
