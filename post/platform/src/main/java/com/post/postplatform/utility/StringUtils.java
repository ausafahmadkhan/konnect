package com.post.postplatform.utility;

public class StringUtils {

    public static boolean isNotNullOrEmpty(String s) {
        return !isNullOrEmpty(s);
    }

    public static boolean isNullOrEmpty(String s) {
        return null == s || s.trim().isEmpty();
    }
}
