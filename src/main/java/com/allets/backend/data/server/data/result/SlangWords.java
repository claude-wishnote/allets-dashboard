package com.allets.backend.data.server.data.result;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import java.util.List;

/**
 * Created by claude on 2015/10/16.
 */
@ApiObject(name = "SlangWords", description = "model of slang_words.txt")
public class SlangWords {

    @ApiObjectField(description = "slang_words.txt version")
    Integer version;
    @ApiObjectField(description = "slang_words.txt generationDate")
    String generationDate;
    @ApiObjectField(description = "slang_words.txt slang words list")
    List<SlangWord> slangWords;
    @ApiObjectField(description = "slang_words.txt specialUsers(mobile app client will use it in \"slang alert\")")
    List<SpecialUser> specialUsers;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public List<SlangWord> getSlangWords() {
        return slangWords;
    }

    public void setSlangWords(List<SlangWord> slangWords) {
        this.slangWords = slangWords;
    }

    public List<SpecialUser> getSpecialUsers() {
        return specialUsers;
    }

    public void setSpecialUsers(List<SpecialUser> specialUsers) {
        this.specialUsers = specialUsers;
    }

    public String getGenerationDate() {
        return generationDate;
    }

    public void setGenerationDate(String generationDate) {
        this.generationDate = generationDate;
    }

    @Override
    public String toString() {
        return "SlangWords{" +
                "version=" + version +
                ", generationDate=" + generationDate +
                ", slangWords=" + slangWords +
                ", specialUsers=" + specialUsers +
                '}';
    }
}
