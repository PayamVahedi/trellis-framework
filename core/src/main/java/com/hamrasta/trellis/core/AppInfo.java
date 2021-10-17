package com.hamrasta.trellis.core;

import com.hamrasta.trellis.core.metadata.Calendar;
import com.hamrasta.trellis.core.metadata.Country;
import com.hamrasta.trellis.core.metadata.Language;

public class AppInfo {
    public static Country DEFAULT_COUNTRY = Country.IR;

    public static Language DEFAULT_LANGUAGE = Language.FA;

    public static Calendar DEFAULT_CALENDAR = Calendar.JALALI;

    public static String DEFAULT_TIME_ZONE = "Asia/Tehran";

    public static Language getLanguage() {
        return DEFAULT_LANGUAGE;
    }
}
