package com.lovegodsinleisuresuits.website.utils;

import jakarta.servlet.http.HttpServletRequest;

public class ServletUtils {

	public static String getClientIp(HttpServletRequest request) {
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

}

