package com.allets.backend.data.server.consts;


public interface Status {

    public interface UserStatus {
        String ACTV = "ACTV";
        String DEL = "DEL";//user del himself
        String OUT = "OUT"; //invalid for 3 month. or forever
        String EAUTH = "EAUTH";
    }

    public interface UserHandleStatus {
        String PASS = "PASS";
        String OUT = "OUT";
        String OUTF = "OUTF";
        String BLOK = "BLOK";
    }

    public interface UserHistoryHandleStatus {
        String PASS = "PASS";
        String OUT = "OUT";
        String REC = "REC";
        String OUTF = "OUTF";
        String BLOK = "BLOK";
    }

    public interface MonitorStatus {
        String ACTV = "ACTV";
        String HOLD = "HOLD";
    }
}
