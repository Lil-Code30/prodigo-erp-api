package com.licode.prodigoerp.common;

import java.util.List;

public class SystemConstants {

    public static String SYSTEM_NAME = "PRODIGO_ERP_API";

    public static List<String> STATUS_LIST = List.of(
            "PENDING_ACTIVATION", "ACTIVE", "SUSPENDED", "PENDING_CLOSURE", "CLOSED", "EXPIRED", "DEACTIVATED");
}
