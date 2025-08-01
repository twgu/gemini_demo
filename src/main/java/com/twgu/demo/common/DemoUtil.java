package com.twgu.demo.common;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DemoUtil {
    private DemoUtil() {
    }

    /****************************************************************************
     * String
     ****************************************************************************/

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static String nullToEmpty(String str) {
        return str == null ? "" : str;
    }

    /****************************************************************************
     * List
     ****************************************************************************/

    public static boolean isNullOrEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }

    public static <T> List<T> safeList(List<T> list) {
        return Optional.ofNullable(list).orElseGet(ArrayList::new);
    }

    /****************************************************************************
     * Time
     ****************************************************************************/

    public enum TimeUnit {
        SECOND,
        MILLIS
    }

    public enum TimeFormat {
        ISO_UTC,
        ISO_OFFSET,
        CUSTOM
    }

    public static long getUnixNow() {
        return getUnixNow(TimeUnit.MILLIS);
    }

    public static long getUnixNow(TimeUnit type) {
        return switch (type) {
            case SECOND -> Instant.now().getEpochSecond(); // 10자리 (초)
            case MILLIS -> Instant.now().toEpochMilli(); // 13자리 (밀리초)
        };
    }

    public static String getFormattedNow() {
        return getFormattedNow(TimeFormat.ISO_OFFSET, null);
    }

    public static String getFormattedNow(TimeFormat type) {
        return getFormattedNow(type, null);
    }

    public static String getFormattedNow(String format) {
        return getFormattedNow(TimeFormat.CUSTOM, format);
    }

    public static String getFormattedNow(TimeFormat type, String format) {
        return switch (type) {
            case ISO_UTC -> Instant.now().toString();
            case ISO_OFFSET -> ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            case CUSTOM -> {
                if (isNullOrEmpty(format)) {
                    throw new IllegalArgumentException("CUSTOM 포맷을 사용하려면 포맷 문자열을 반드시 제공해야 합니다.");
                }
                yield LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
            }
        };
    }
}
