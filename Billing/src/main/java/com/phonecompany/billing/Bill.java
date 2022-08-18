package com.phonecompany.billing;

import com.opencsv.CSVReader;
import com.phonecompany.billing.Utils.PhoneLogUtils;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Bill implements TelephoneBillCalculator {
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    @Override
    public BigDecimal calculate(String phoneLog) {
        CSVReader reader = new CSVReader(new StringReader(phoneLog));
        BigDecimal price = BigDecimal.ZERO;
        try {
            var pol = reader.readAll();
            var favouriteNumber = PhoneLogUtils.getFavouriteNumber(pol);
            for (var entry : pol) {
                if (favouriteNumber.toString().equals(entry[0])) {
                    continue;
                }
                var startDate = dateFormatter.parse(entry[1]);
                var endDate = dateFormatter.parse(entry[2]);
                Calendar startCal = Calendar.getInstance();
                Calendar endCal = Calendar.getInstance();
                startCal.setTime(startDate);
                endCal.setTime(endDate);
                price = price.add(PhoneLogUtils.getPrice(startCal, endCal));
            }

        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        return price;
    }
}
