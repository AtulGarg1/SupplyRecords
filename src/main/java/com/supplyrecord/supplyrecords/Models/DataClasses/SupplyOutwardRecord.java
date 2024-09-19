package com.supplyrecord.supplyrecords.Models.DataClasses;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public record SupplyOutwardRecord(
        long recordId, String firmName, String partyName, double totalAmount,
        LocalDate date, double biltiCharge, double bardana, double labourCost,
        double commission, double postage, double bazaarCharges, double otherExpenses
) {
    public record Filter(String partyName, LocalDate dateFrom, LocalDate dateTo) {}

    public static ArrayList<SupplyOutwardRecord> filterList(ArrayList<SupplyOutwardRecord> list, Filter filter) {
        return (ArrayList<SupplyOutwardRecord>) list.stream()
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

    public static ArrayList<SupplyOutwardRecord> generateDummyData() {
        return new ArrayList<>(Arrays.asList(
                new SupplyOutwardRecord(
                        1, "ABC", "ATUL", 100000,
                        LocalDate.now(), 0, 1, 2,
                        3, 4, 5, 6
                ),
                new SupplyOutwardRecord(
                        2, "RAJENDAR ANIL", "ATUL1", 100000,
                        LocalDate.now(), 0, 1.0, 2,
                        3, 4.3, 5, 6
                ),
                new SupplyOutwardRecord(
                        3, "ABC", "ATUL", 100000,
                        LocalDate.now(), 0, 1, 2,
                        3, 4, 5, 6
                ),
                new SupplyOutwardRecord(
                        4, "ABC", "ATUL1", 100000,
                        LocalDate.now(), 0, 1, 2,
                        3, 4, 5, 6
                ),
                new SupplyOutwardRecord(
                        5, "ABC", "ATUL", 100000,
                        LocalDate.now(), 0, 1, 2,
                        3, 4, 5, 6
                ),
                new SupplyOutwardRecord(
                        6, "ABC", "ATUL", 100000,
                        LocalDate.now(), 0, 1, 2,
                        3, 4, 5, 6
                )
        ));
    }
}
