package com.allets.backend.dashboard.server.consts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by claude on 2015/8/20.
 */
public interface Const {

    public final static String DEFAULT_PASSWORD = "1234";

    public interface Action {
        //for users
        String REPORT = "REPORT";
        String REPORTED = "REPORTED";
        String BlACKLIST = "BlACKLIST";
        String PASS = "PASS";
        String HIDD = "HIDD";
        String DEL = "DEL";
        String OUT = "OUT";
        String OUTF = "OUTF";
        String HANDLE = "HANDLE";
        String HANDLED = "HANDLED";
        String UNDEL = "UNDEL";
        String UNOUTF = "UNOUTF";
        String UNBlACKLIST = "UNBlACKLIST";
        String UNOUT = "UNOUT";
        String MODIFYPROFILES = "MODIFYPROFILES";
        String ALL = "ALL";
        String ALLSIMPLE = "ALLSIMPLE";
        String REPORTANDHANDLE = "REPORTANDHANDLE";

        //restore delete comment
        String RES = "RES";
        //reset or edit user info
        String EDIT = "EDIT";
        //reset password
        String RSPW = "RSPW";
        //send too many account report alert        ;
        String ALT1 = "ALT1";
        //send delete too manay comment alert
        String ALT2 = "ALT2";

        //for monitors or allets user
        String MODIFYPASSWORD = "MODIFYPASSWORD";

    }

    public interface Status {
        String PASS = "PASS";
        String DEL = "DEL";
        String HIDD = "HIDD";
        String OUT = "OUT";
        String INVD = "INVD"; //invalid 3 month.
        String BlACKLIST = "BlACKLIST";
        String ACTV = "ACTV";
    }

    public interface ReportType {
        String RT100 = "RT100";
        String RT200 = "RT200";
        String RT300 = "RT300";
        String RT400 = "RT400";
        String RT500 = "RT500";
        String RT600 = "RT600";
        String RT999 = "RT999";
    }

    public enum AlertType {
        A101, A102, A200
    }

    public interface SearchField {
        String EMAIL = "email";
        String NICKNAME = "nickName";
        String KEYWORD = "keyword";
    }

    interface CDN_PURGE {
        String CDN_PURGE_URL = "https://api.ccu.akamai.com/ccu/v2/queues/default";
        String CDN_PURGE_USER = "";
        String CDN_PURGE_PASS = "";
        String CDN_PURGE_AUTH = "Basic cGlraWNhc3RfcHVyZ2VAcGlraWNhc3QuY29tOnBpa2ljYXN0IUAz";
        String CDN_PURGE_TYPE = "arl";
        String CDN_PURGE_DOMAIN = "production"; //production, staging
        String CDN_PURGE_ACTION = "remove";  //
        String CDN_PURGE_PAD = "https://s3.ap-northeast-2.amazonaws.com/an2-prd-allets-contents";
    }

    public final static List<String> InHouseUsers = new ArrayList<String>() {{
        add("editor@allets.com");
        add("admin@allets.com");
    }};

}
