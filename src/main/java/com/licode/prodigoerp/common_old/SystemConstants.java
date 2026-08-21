package com.licode.prodigoerp.common_old;

import java.util.List;

public class SystemConstants {

    // System Infos
    public static String SYSTEM_NAME = "PRODIGO_ERP_API";
    public static String SYSTEM_CURRENCY = "XAF";

    public static List<String> STATUS_LIST = List.of(
            "PENDING_ACTIVATION", "ACTIVE", "SUSPENDED", "PENDING_CLOSURE", "CLOSED", "EXPIRED", "DEACTIVATED");

    // Tenant Entitlements
    public static Integer DEFAULT_MAX_USERS = 5;
    public static Integer DEFAULT_MAX_STORAGE_GB = 5;
    public static Long DEFAULT_MAX_PRODUCTS = 20L;
}
