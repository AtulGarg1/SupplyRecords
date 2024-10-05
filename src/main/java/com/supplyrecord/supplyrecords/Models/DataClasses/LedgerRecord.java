package com.supplyrecord.supplyrecords.Models.DataClasses;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public record LedgerRecord(String description, LocalDate date, double amount) {
    public String formattedDate() {
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public boolean isCredit() {
        return description.equals("Inward");
    }

    public static ArrayList<LedgerRecord> generateDummyData() {
        return new ArrayList<>(Arrays.asList(
                new LedgerRecord("Inward", LocalDate.now(), 10000),
                new LedgerRecord("Outward", LocalDate.now(), 5000),
                new LedgerRecord("Inward", LocalDate.now(), 200000),
                new LedgerRecord("Outward", LocalDate.now(), 150000)
        ));
    }
}
