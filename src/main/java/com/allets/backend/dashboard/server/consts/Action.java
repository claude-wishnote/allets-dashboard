package com.allets.backend.dashboard.server.consts;


public interface Action {

    interface UserReport{
        /*
        List reported user
         */
        String LIST = "LIST";

        /*
        Handle reported user
         */
        String HANDLE_PASS="PASS";
        String HANDLE_BLOCK="BLOCK"; //block for 3 month.
        String HANDLE_DELETE="DELETE";
    }
}
