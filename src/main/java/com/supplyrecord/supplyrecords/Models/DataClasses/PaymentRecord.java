package com.supplyrecord.supplyrecords.Models.DataClasses;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public record PaymentRecord(
        long recordId, String firmName, String partyName,
        double amount, String bankName, LocalDate date, boolean isCredit
) implements Record {
    public record Filter(String partyName, LocalDate dateFrom, LocalDate dateTo) {}

    public static ArrayList<PaymentRecord> filterList(ArrayList<PaymentRecord> list, Filter filter) {
        return (ArrayList<PaymentRecord>) list.stream()
                .filter(item ->
                        (filter.partyName.isEmpty() || item.partyName.equals(filter.partyName)) &&
                        (filter.dateFrom == null || (item.date.isEqual(filter.dateFrom) || item.date.isAfter(filter.dateFrom))) &&
                        (filter.dateTo == null || (item.date.isEqual(filter.dateTo) || item.date.isBefore(filter.dateTo)))
                )
                .collect(Collectors.toList());
    }

    public String formattedDate() {
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public static ArrayList<PaymentRecord> generateDummyData(boolean isCredit) {
        return new ArrayList<>(Arrays.asList(
                new PaymentRecord(
                        1, "ABC", "LT/CH", 100000,
                        "PNB", LocalDate.now(), isCredit
                ),
                new PaymentRecord(
                        2, "ABC", "LT/BLP", 150000,
                        "AXIS", LocalDate.now(), isCredit
                ),
                new PaymentRecord(
                        3, "ABC", "OD", 300000,
                        "BOB", LocalDate.now(), isCredit
                ),
                new PaymentRecord(
                        4, "ABC", "XYZ", 250000,
                        "PNB", LocalDate.now(), isCredit
                )
        ));
    }
}
