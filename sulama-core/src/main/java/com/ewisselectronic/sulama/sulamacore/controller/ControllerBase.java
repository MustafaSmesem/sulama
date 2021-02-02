package com.ewisselectronic.sulama.sulamacore.controller;

import javax.servlet.http.HttpServletRequest;

public abstract class ControllerBase {
    protected static String getClientIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }
}
