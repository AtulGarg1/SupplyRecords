package com.supplyrecord.supplyrecords.Models.DataClasses;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public record SupplyInwardRecord(
        long recordId, String firmName, String partyName, double totalAmount, LocalDate date
) {
    public record Filter(String partyName, LocalDate dateFrom, LocalDate dateTo) {}

    public static ArrayList<SupplyInwardRecord> filterList(ArrayList<SupplyInwardRecord> list, Filter filter) {
        return (ArrayList<SupplyInwardRecord>) list.stream()
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

    public static ArrayList<SupplyInwardRecord> generateDummyData() {
        return new ArrayList<>(Arrays.asList(
                new SupplyInwardRecord(
                        1, "ABC", "ATUL", 100000, LocalDate.now()
                ),
                new SupplyInwardRecord(
                        1, "ABC", "ATUL1", 100000, LocalDate.now()
                ),
                new SupplyInwardRecord(
                        1, "ABC", "ATUL", 100000, LocalDate.now()
                ),
                new SupplyInwardRecord(
                        1, "ABC", "ATUL1", 100000, LocalDate.now()
                ),
                new SupplyInwardRecord(
                        1, "ABC", "ATUL", 100000, LocalDate.now()
                ),
                new SupplyInwardRecord(
                        1, "ABC", "ATUL2", 100000, LocalDate.now()
                )
        ));
    }
}
