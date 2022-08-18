package com.phonecompany.billing.Utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static Boolean isInInterval(Calendar startCal) {
        Calendar startIntervalCal = getCalendar(startCal.getTime(), 8, 0, 0);
        Calendar endIntervalCal = getCalendar(startCal.getTime(), 16, 0, 0);
        return startCal.getTime().compareTo(startIntervalCal.getTime()) >= 0 && startCal.getTime().compareTo(endIntervalCal.getTime()) < 0;
    }

    public static Calendar getCalendar(Date date, int hours, int minutes, int seconds) {
        Calendar helpCal = Calendar.getInstance();
        helpCal.setTime(date);
        helpCal.set(Calendar.HOUR_OF_DAY, hours);
        helpCal.set(Calendar.MINUTE, minutes);
        helpCal.set(Calendar.SECOND, seconds);
        return helpCal;
    }
}
