package com.supplyrecord.supplyrecords.Models;

import com.supplyrecord.supplyrecords.Database.DatabaseApi;
import com.supplyrecord.supplyrecords.Database.DatabaseImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * <ul>
 *      <li>Used for storing lists for AutoCompleteTextFields.</li>
 *      <li>Can also be used to store general data common between firms.</li>
 *      <li>It is fetched at the very start of the program.</li>
 * </ul>
 */
public class AutoSuggestions {
    public static ArrayList<String> ItemNames = new ArrayList<>();
    public static ArrayList<String> PartyNames = new ArrayList<>();
    public static ArrayList<String> BankNames = new ArrayList<>();
    public static ArrayList<String> FirmNames = new ArrayList<>();
    private static Map<String, String> Units = new HashMap<>();

    public static void fetchLists() {
        DatabaseApi db = new DatabaseImpl();

        BankNames.addAll(db.fetchBankNames());
        FirmNames.addAll(db.fetchFirmNames());
        PartyNames.addAll(db.fetchPartyNames());

        ArrayList<Item> items = db.fetchItems();
        ItemNames.addAll(Item.getItemNamesList(items));
        Units = Item.getUnitsMap(items);
    }

    public static void addItem(String name, String unit) {
        ItemNames.add(name);
        Units.put(name, unit);
    }

    public static String getUnit(String itemName) {
        return Units.get(itemName) != null ? Units.get(itemName) : "";
    }

    public record Item(String name, String unit) {
        private static ArrayList<String> getItemNamesList(ArrayList<Item> items) {
            ArrayList<String> itemNames = new ArrayList<>();
            items.forEach(item -> itemNames.add(item.name));
            return itemNames;
        }

        private static Map<String, String> getUnitsMap(ArrayList<Item> items) {
            Map<String, String> units = new HashMap<>();
            items.forEach(item -> units.put(item.name(), item.unit()));
            return units;
        }
    }
}
