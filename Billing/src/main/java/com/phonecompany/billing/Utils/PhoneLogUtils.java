package com.phonecompany.billing.Utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public class PhoneLogUtils {
    private static final BigDecimal highTariffInIntervalPrice = new BigDecimal("1");
    private static final BigDecimal highTariffOutIntervalPrice = new BigDecimal("0.5");
    private static final BigDecimal otherTariffPrice = new BigDecimal("0.2");
    private static final BigDecimal timeLimit = new BigDecimal("5"); // Time limit after which each minute costs "otherTariffPrice" [minutes]

    public static Long getFavouriteNumber(List<String[]> phoneLog) {
        var numberList = new ArrayList<>();
        phoneLog.forEach(entry -> numberList.add(entry[0]));
        var numberCount = numberList.stream().collect(Collectors.groupingBy(s -> s, Collectors.counting())).entrySet();
        var highestFrequency = numberCount.stream().mapToLong(Map.Entry::getValue).max();
        var favouriteNumber = numberCount.stream().filter(x -> x.getValue() == highestFrequency.getAsLong()).mapToLong(x -> Long.parseLong(x.getKey().toString())).max().orElseThrow();

        return favouriteNumber;
    }

    public static BigDecimal getCallLength(Date date1, Date date2) {
        var diffInMilliseconds = BigDecimal.valueOf(date2.getTime()).subtract(BigDecimal.valueOf(date1.getTime()));
        var timeDifferenceInMinutes = diffInMilliseconds.divide(BigDecimal.valueOf(60000), RoundingMode.UP);
        return timeDifferenceInMinutes;
    }

    public static BigDecimal getPrice(Calendar startCal, Calendar endCal) {
        var callLength = PhoneLogUtils.getCallLength(startCal.getTime(), endCal.getTime());
        var startTime = DateUtils.getCalendar(startCal.getTime(), startCal.get(Calendar.HOUR_OF_DAY), startCal.get(Calendar.MINUTE), startCal.get(Calendar.SECOND));
        var endTime = DateUtils.getCalendar(endCal.getTime(), endCal.get(Calendar.HOUR_OF_DAY), endCal.get(Calendar.MINUTE), endCal.get(Calendar.SECOND));
        BigDecimal price = BigDecimal.ZERO;
        if (callLength.compareTo(timeLimit) > 0) {
            price = price.add(getHighPrice(startTime, timeLimit));
            price = price.add((callLength.subtract(timeLimit).multiply(otherTariffPrice)));
        } else {
            price = price.add(getHighPrice(startTime, callLength));
        }
        return price;

    }


    private static BigDecimal getHighPrice(Calendar startCal, BigDecimal callLength) {
        BigDecimal price = BigDecimal.ZERO;
        for (int i = 0; i < callLength.doubleValue(); i++) {
            var inInterval = DateUtils.isInInterval(startCal);
            if (inInterval) {
                price = price.add(highTariffInIntervalPrice);
            } else {
                price = price.add(highTariffOutIntervalPrice);
            }
            startCal.add(Calendar.MINUTE, 1);
        }
        return price;
    }
}
