package com.allets.backend.data.server.data.result;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

/**
 * Created by claude on 2015/10/16.
 */
@ApiObject(name = "SlangWord", description = "this model is only for write data in slang_words.txt,not real SlangWord model")
public class SlangWord {
    @ApiObjectField(description = "slang text")
    private String slang;
    @ApiObjectField(description = "slang type")
    private String type;

    public String getSlang() {
        return slang;
    }

    public void setSlang(String slang) {
        this.slang = slang;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SlangWord{" +
                "slang='" + slang + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
