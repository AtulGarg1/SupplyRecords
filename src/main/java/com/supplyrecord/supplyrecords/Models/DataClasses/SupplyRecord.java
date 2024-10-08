package com.supplyrecord.supplyrecords.Models.DataClasses;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public record SupplyRecord(
        long recordId, String firmName, String partyName, double totalAmount,
        LocalDateTime date, double biltiCharge, double bardana, double labourCost,
        double commission, double postage, double bazaarCharges, double otherExpenses, boolean isInward
) implements Record {
    public record Filter(String partyName, LocalDateTime dateFrom, LocalDateTime dateTo) {}

    public static ArrayList<SupplyRecord> filterList(ArrayList<SupplyRecord> list, Filter filter) {
        return (ArrayList<SupplyRecord>) list.stream()
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

    public static ArrayList<SupplyRecord> generateDummyData(boolean isInward) {
        return new ArrayList<>(Arrays.asList(
                new SupplyRecord(
                        1, "ABC", "ATUL", 100000,
                        LocalDateTime.now(), 0, 1, 2,
                        3, 4, 5, 6, isInward
                ),
                new SupplyRecord(
                        2, "RAJENDAR ANIL", "ATUL1", 100000,
                        LocalDateTime.now(), 0, 1.0, 2,
                        3, 4.3, 5, 6, isInward
                ),
                new SupplyRecord(
                        3, "ABC", "ATUL", 100000,
                        LocalDateTime.now(), 0, 1, 2,
                        3, 4, 5, 6, isInward
                ),
                new SupplyRecord(
                        4, "ABC", "ATUL1", 100000,
                        LocalDateTime.now(), 0, 1, 2,
                        3, 4, 5, 6, isInward
                ),
                new SupplyRecord(
                        5, "ABC", "ATUL", 100000,
                        LocalDateTime.now(), 0, 1, 2,
                        3, 4, 5, 6, isInward
                ),
                new SupplyRecord(
                        6, "ABC", "ATUL", 100000,
                        LocalDateTime.now(), 0, 1, 2,
                        3, 4, 5, 6, isInward
                )
        ));
    }
}
