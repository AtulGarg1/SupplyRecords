package com.supplyrecord.supplyrecords.Models.DataClasses;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public record SupplyInwardRecord(
        long recordId, String firmName, String partyName, double totalAmount,
        LocalDate date, double biltiCharge, double bardana, double labourCost,
        double commission, double postage, double bazaarCharges, double otherExpenses
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
                        1, "ABC", "ATUL", 100000,
                        LocalDate.now(), 0, 1, 2,
                        3, 4, 5, 6
                ),
                new SupplyInwardRecord(
                        2, "RAJENDAR ANIL", "ATUL1", 100000,
                        LocalDate.now(), 0, 1.0, 2,
                        3, 4.3, 5, 6
                ),
                new SupplyInwardRecord(
                        3, "ABC", "ATUL", 100000,
                        LocalDate.now(), 0, 1, 2,
                        3, 4, 5, 6
                ),
                new SupplyInwardRecord(
                        4, "ABC", "ATUL1", 100000,
                        LocalDate.now(), 0, 1, 2,
                        3, 4, 5, 6
                ),
                new SupplyInwardRecord(
                        5, "ABC", "ATUL", 100000,
                        LocalDate.now(), 0, 1, 2,
                        3, 4, 5, 6
                ),
                new SupplyInwardRecord(
                        6, "ABC", "ATUL", 100000,
                        LocalDate.now(), 0, 1, 2,
                        3, 4, 5, 6
                )
        ));
    }
}
