package com.zspirytus.zspermission;

import android.Manifest;

/**
 * Created by ZSpirytus on 2018/6/22.
 */

public class PermissionGroup {

    private PermissionGroup(){
        throw new AssertionError();
    }

    public static String[] CAMERA_GROUP = new String[]{
            Manifest.permission.CAMERA
    };

    public static String[] CALENDAR_GROUP = new String[]{
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR
    };

    public static String[] CONTACTS_GROUP = new String[]{
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.GET_ACCOUNTS
    };

    public static String[] LOCATION_GROUP = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    public static String[] MICROPHONE_GROUP = new String[]{
            Manifest.permission.RECORD_AUDIO
    };

    public static String[] PHONE_GROUP = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.WRITE_CALL_LOG,
            Manifest.permission.ADD_VOICEMAIL,
            Manifest.permission.USE_SIP,
            Manifest.permission.PROCESS_OUTGOING_CALLS
    };

    public static String[] SENSORS_GROUP = new String[]{
            Manifest.permission.BODY_SENSORS
    };

    public static String[] SMS_GROUP = new String[]{
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.BROADCAST_WAP_PUSH,
            Manifest.permission.RECEIVE_MMS,
    };

    public static String[] STORAGE_GROUP = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

}
