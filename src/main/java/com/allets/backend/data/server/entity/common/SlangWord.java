package com.allets.backend.data.server.entity.common;

import javax.persistence.*;

/**
 * Created by jack on 2015/9/1.
 */
@Entity
@Table(name = "SLANG_WORD", schema = "", catalog = "allets_common")
public class SlangWord {
    private int slangId;
    private String slang;
    private String type;
    private String cdate;

    @Id
    @Column(name = "slang_id", nullable = false, insertable = true, updatable = true)
    public int getSlangId() {
        return slangId;
    }

    public void setSlangId(int slangId) {
        this.slangId = slangId;
    }

    @Basic
    @Column(name = "slang", nullable = false, insertable = true, updatable = true, length = 20)
    public String getSlang() {
        return slang;
    }

    public void setSlang(String slang) {
        this.slang = slang;
    }

    @Basic
    @Column(name = "type", nullable = false, insertable = true, updatable = true, length = 1)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "cdate", nullable = false, insertable = true, updatable = true)
    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SlangWord slangWord = (SlangWord) o;

        if (slangId != slangWord.slangId) return false;
        if (slang != null ? !slang.equals(slangWord.slang) : slangWord.slang != null) return false;
        if (type != null ? !type.equals(slangWord.type) : slangWord.type != null) return false;
        if (cdate != null ? !cdate.equals(slangWord.cdate) : slangWord.cdate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = slangId;
        result = 31 * result + (slang != null ? slang.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (cdate != null ? cdate.hashCode() : 0);
        return result;
    }
}
