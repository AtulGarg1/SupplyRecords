package com.supplyrecord.supplyrecords.Models.DataClasses;

import com.supplyrecord.supplyrecords.Models.LocalData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public record LedgerRecord(String description, LocalDateTime date, double amount, Record reference) {
    public String formattedDate() {
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public boolean isCredit() {
        return description.equals("Inward");
    }

    public static ArrayList<LedgerRecord> generateDummyData() {
        return new ArrayList<>(Arrays.asList(
                new LedgerRecord("Inward", LocalDateTime.now(), 10000, LocalData.getInstance().getSupplyInwardRecordsList().get(0)),
                new LedgerRecord("Outward", LocalDateTime.now(), 5000, LocalData.getInstance().getSupplyOutwardRecordsList().get(0)),
                new LedgerRecord("Inward", LocalDateTime.now(), 200000, LocalData.getInstance().getPaymentsReceivedList().get(0)),
                new LedgerRecord("Outward", LocalDateTime.now(), 150000, LocalData.getInstance().getPaymentsMadeList().get(0))
        ));
    }
}
