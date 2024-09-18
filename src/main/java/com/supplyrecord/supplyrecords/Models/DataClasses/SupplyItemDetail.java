package com.supplyrecord.supplyrecords.Models.DataClasses;

import java.util.ArrayList;
import java.util.Arrays;

public record SupplyItemDetail(long recordId, String itemName, double qty, double price) {
    public static ArrayList<SupplyItemDetail> generateDummyData() {
        return new ArrayList<>(Arrays.asList(
                new SupplyItemDetail(1, "PISTA", 100, 250),
                new SupplyItemDetail(1, "KAJU", 50, 800),
                new SupplyItemDetail(1, "ALMOND", 125, 680)
        ));
    }
}
