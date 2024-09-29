package com.supplyrecord.supplyrecords.Models;

import com.supplyrecord.supplyrecords.Database.DatabaseApi;
import com.supplyrecord.supplyrecords.Database.DatabaseImpl;

import java.util.ArrayList;

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

    public static void fetchLists() {
        DatabaseApi db = new DatabaseImpl();

        BankNames.addAll(db.fetchBankNames());
        FirmNames.addAll(db.fetchFirmNames());
        ItemNames.addAll(db.fetchItemNames());
        PartyNames.addAll(db.fetchPartyNames());
    }
}
