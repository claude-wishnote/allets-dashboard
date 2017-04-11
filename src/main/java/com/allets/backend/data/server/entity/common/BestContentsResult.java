package com.allets.backend.data.server.entity.common;// default package
// Generated 2015. 8. 15 오후 5:46:57 by Hibernate Tools 3.2.2.GA


import javax.persistence.*;
import java.util.Date;

/**
 * BestContentsResult generated by hbm2java
 */
@Entity
@Table(name="BEST_CONTENTS_RESULT"
    ,catalog="allets_common"
)
public class BestContentsResult  implements java.io.Serializable {


     private BestContentsResultId id;
     private MgContents mgContents;
     private Date bdate;
     private Float resultScore;
     private Float extraScore;

    public BestContentsResult() {
    }

	
    public BestContentsResult(BestContentsResultId id, MgContents mgContents, Date bdate) {
        this.id = id;
        this.mgContents = mgContents;
        this.bdate = bdate;
    }
    public BestContentsResult(BestContentsResultId id, MgContents mgContents, Date bdate, Float resultScore, Float extraScore) {
       this.id = id;
       this.mgContents = mgContents;
       this.bdate = bdate;
       this.resultScore = resultScore;
       this.extraScore = extraScore;
    }
   
     @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="batchCount", column=@Column(name="batch_count", nullable=false) ), 
        @AttributeOverride(name="contentsId", column=@Column(name="contents_id", nullable=false) ) } )
    public BestContentsResultId getId() {
        return this.id;
    }
    
    public void setId(BestContentsResultId id) {
        this.id = id;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="contents_id", nullable=false, insertable=false, updatable=false)
    public MgContents getMgContents() {
        return this.mgContents;
    }
    
    public void setMgContents(MgContents mgContents) {
        this.mgContents = mgContents;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="bdate", nullable=false, length=19)
    public Date getBdate() {
        return this.bdate;
    }
    
    public void setBdate(Date bdate) {
        this.bdate = bdate;
    }
    
    @Column(name="result_score", precision=12, scale=0)
    public Float getResultScore() {
        return this.resultScore;
    }
    
    public void setResultScore(Float resultScore) {
        this.resultScore = resultScore;
    }
    
    @Column(name="extra_score", precision=12, scale=0)
    public Float getExtraScore() {
        return this.extraScore;
    }
    
    public void setExtraScore(Float extraScore) {
        this.extraScore = extraScore;
    }




}

