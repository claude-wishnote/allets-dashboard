package com.allets.backend.data.server.JSONDoc;

import java.util.List;

/**
 * Created by claude on 2015/11/17.
 */
public class JSONDocProperty {

    private boolean jsondocSwitch;
    private String version;
    private String basePath;
    private List<String> packages;

    public boolean isJsondocSwitch() {
        return jsondocSwitch;
    }

    public void setJsondocSwitch(boolean jsondocSwitch) {
        this.jsondocSwitch = jsondocSwitch;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public List<String> getPackages() {
        return packages;
    }

    public void setPackages(List<String> packages) {
        this.packages = packages;
    }
}
