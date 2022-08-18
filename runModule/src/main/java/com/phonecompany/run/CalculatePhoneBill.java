package com.phonecompany.run;

import com.phonecompany.billing.Bill;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class CalculatePhoneBill {
    public static void main (String[] args){
        String phoneLog;
        try {
            Path filePath = Path.of("src/main/resources/mockup.csv");

            phoneLog = Files.readString(filePath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        var phoneBill = new Bill().calculate(phoneLog);
        System.out.printf("Price to pay for the given Bill is: %f Kč", phoneBill);
    }
}
